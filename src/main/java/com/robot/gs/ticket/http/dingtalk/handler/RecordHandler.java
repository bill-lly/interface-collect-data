package com.robot.gs.ticket.http.dingtalk.handler;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.robot.gs.dingtalk.bean.Department;
import com.robot.gs.dingtalk.bean.Employee;
import com.robot.gs.ticket.http.dingtalk.config.ServiceGoConfig;
import com.robot.gs.ticket.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;


@Slf4j
public class RecordHandler {
    protected final ServiceGoConfig serviceGoConfig;
    private String token;
    //存放所有的dept
    private HashSet<Department> departments = new HashSet<>();
    private ArrayList<Integer> allDeptIds = new ArrayList<>();

    //组织架构层级从1开始,1为上海高仙,
    int deptLevel = 2;

    public RecordHandler(ServiceGoConfig serviceGoConfig) {
        this.serviceGoConfig = serviceGoConfig;
        this.token = getToken();
        this.departments = postDept(new Integer[]{1});
    }


    /**
     * 获取Token
     *
     * @return
     */
    public String getToken() {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(serviceGoConfig.getUrl())
                .addPathSegment("REST_REST")
                .addPathSegment("original")
                .addQueryParameter("servId", serviceGoConfig.getTokenServId())
                .addQueryParameter("appkey", serviceGoConfig.getAPPkey())
                .addQueryParameter("appsecret", serviceGoConfig.getAPPSercret())
                .addQueryParameter("sysId", serviceGoConfig.getSysId())
                .addQueryParameter("serialNo", UUID.randomUUID().toString())
                .build();

        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        String resp = HttpUtil.get(request);
        String accessToken = JSONObject.parseObject(resp).get("access_token").toString();
        if (accessToken == null) {
            throw new RuntimeException("access_token获取失败，请检查url或参数");
        }
        return accessToken;
    }

    public HashSet<Department> postDept(Integer[] deptIds) {

        //记录下级组织dept_id
        ArrayList<Integer> resDeptIds = new ArrayList<>();
        for (int deptId : deptIds) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dept_id", deptId);
            //post dept
            String resp = HttpUtil.post(jsonObject.toString(),
                    String.valueOf(getDingTalkHttpUrl(serviceGoConfig.getDeptServId(),UUID.randomUUID())));
            JSONArray result = null;
            try {
                result = JSON.parseArray(JSONObject.parseObject(resp).get("result").toString());
            } catch (RuntimeException e) {
                log.info("获取Dept:" + deptId + "的result失败");
            }

            if (result != null) {
                for (Object o : result) {
                    JSONObject dept = JSONObject.parseObject(o.toString());
                    Department department = new Department();
                    department.setDeptId(dept.getInteger("dept_id"));
                    department.setName(dept.getString("name"));
                    department.setDeptLevel(deptLevel);
                    department.setParentId(dept.getInteger("parent_id"));
                    department.setAutoAddUser(dept.getBoolean("auto_add_user"));
                    department.setCreateDeptGroup(dept.getBoolean("create_dept_group"));
                    department.setExt(dept.getString("ext"));
                    //将结果存储下来
                    resDeptIds.add(dept.getInteger("dept_id"));
                    allDeptIds.add(dept.getInteger("dept_id"));
                    departments.add(department);
                }
            }
        }
        deptLevel++;
        int size = resDeptIds.size();
        Integer[] deptId = resDeptIds.toArray(new Integer[size]);

        if (deptId.length != 0) {
            postDept(deptId);
        }
        //上海高仙,一级组织，手动补数据
        Department ShGs = new Department();
        ShGs.setDeptId(1);
        ShGs.setName("上海高仙自动化科技发展有限公司");
        ShGs.setDeptLevel(1);
        ShGs.setParentId(-1);
        ShGs.setAutoAddUser(true);
        ShGs.setCreateDeptGroup(true);
        ShGs.setExt("{\"faceCount\":\"-9999\"}");
        departments.add(ShGs);
        return departments;
    }


    /**
     * @param allDeptIds 所有的部门id
     * @param size       必传参数-分页大小，暂时用不到,直接最大值100
     * @param cursor     必传参数-分页 从0开始
     * @return
     */
    public HashSet<Employee> postEmp(int[] allDeptIds, int size, int cursor) {
        //存放所有的emp
        HashSet<Employee> employees = new HashSet<>();
        JSONObject body = new JSONObject();
        for (Integer deptId : allDeptIds) {
            boolean flag = true;
            while (flag) {
                body.put("dept_id", deptId);
                body.put("size", size);
                body.put("cursor", cursor);
                String resp = HttpUtil.post(body.toString(), String.valueOf(getDingTalkHttpUrl(serviceGoConfig.getEmpServId(),UUID.randomUUID())));
                mappingAndSaveEmp(employees, deptId, resp);
                Boolean hasMore = JSONObject.parseObject(JSONObject.parseObject(resp).get("result").toString()).getBoolean(
                        "has_more");
                if (hasMore){
                    cursor++;
                }else {
                    flag = false;
                }
            }
        }
        return employees;
    }




    private HttpUrl getDingTalkHttpUrl(String servId,UUID serialNo) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(serviceGoConfig.getUrl())
                .addPathSegment("REST_REST")
                .addPathSegment("original")
                .addQueryParameter("servId", servId)
                .addQueryParameter("sysId", serviceGoConfig.getSysId())
                .addQueryParameter("access_token", token)
                .addQueryParameter("serialNo", serialNo.toString())
                .build();
        return url;

    }
    /**
     * @param allDeptIds            所有的部门id
     * @param size                  必传参数-分页大小，暂时用不到,直接最大值100
     * @param cursor                必传参数-分页 从0开始
     * @param containAccessLimit    非必传参数-分页 是否获取隐藏员工信息
     * @return
     */
    public HashSet<Employee> postEmp(int[] allDeptIds, int size, int cursor, boolean containAccessLimit) {
        //存放所有的emp
        HashSet<Employee> employees = new HashSet<>();
        JSONObject body = new JSONObject();
        for (Integer deptId : allDeptIds) {
            boolean flag = true;
            while (flag) {
                body.put("dept_id", deptId);
                body.put("size", size);
                body.put("cursor", cursor);
                body.put("contain_access_limit", containAccessLimit);
                String resp = HttpUtil.post(body.toString(), String.valueOf(getDingTalkHttpUrl(serviceGoConfig.getEmpServId(),UUID.randomUUID())));
                mappingAndSaveEmp(employees, deptId, resp);
                Boolean hasMore = JSONObject.parseObject(JSONObject.parseObject(resp).get("result").toString()).getBoolean(
                        "has_more");
                if (hasMore){
                    cursor++;
                }else {
                    flag = false;
                }
            }
        }
        return employees;
    }

    private void mappingAndSaveEmp(HashSet<Employee> employees,Integer deptId,String resp){
        try {
            String empListStr = JSONObject.parseObject(JSONObject.parseObject(resp).get("result").toString()).get(
                    "list").toString();

            JSONArray empArray = JSONArray.parseArray(empListStr);

            for (Object o : empArray) {
                JSONObject json = JSONObject.parseObject(o.toString());
                Employee employee = new Employee();
                employee.setUserId(json.getString("userid"));
                employee.setName(json.getString("name"));
                employee.setUnionId(json.getString("unionid"));
                employee.setTitle(json.getString("title"));
                employee.setJobNumber(json.getString("job_number"));
                employee.setTelephone(json.getString("telephone"));
                employee.setEmail(json.getString("email"));
                employee.setOrgEmail(json.getString("org_email"));
                employee.setWorkPlace(json.getString("work_place"));
                employee.setRemark(json.getString("remark"));
                employee.setExtension(json.getString("extension"));
                employee.setLeader(json.getBoolean("leader"));
                employee.setDeptId(deptId.longValue());
                employee.setDeptIdList(json.getList("dept_id_list", Long.class));
                employee.setActive(json.getBoolean("active"));
                employee.setAdmin(json.getBoolean("admin"));
                employee.setAvatar(json.getString("avatar"));
                employee.setBoss(json.getBoolean("boss"));
                employee.setDeptOrder(json.getLong("dept_order"));
                employee.setExclusiveAccount(json.getBoolean("exclusive_account"));
                employee.setHideMobile(json.getBoolean("hide_mobile"));
                employee.setHiredDate(json.getLong("hired_date"));
                //将结果存储下来
                employees.add(employee);
            }
        } catch (RuntimeException e) {
            log.info("获取Dept:" + deptId + "的result失败");
        }
    }

    //修复分页bug
}
