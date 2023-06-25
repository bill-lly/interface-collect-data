package com.robot.gs.dingtalk;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.dingtalk.bean.Department;
import com.robot.gs.dingtalk.bean.Employee;
import com.robot.gs.dingtalk.dateProcessor.DeptEmpDateProcessor;
import com.robot.gs.ticket.http.dingtalk.config.ServiceGoConfig;
import com.robot.gs.ticket.http.dingtalk.handler.RecordHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

@Slf4j
public class DingTalkClient {
    private final ServiceGoConfig serviceGoConfig;
    private final DeptEmpDateProcessor deptEmpDateProcessor;
    private final ApiNameEnum apiObjectName;

    public DingTalkClient(Properties properties, ApiNameEnum apiObjectName, DeptEmpDateProcessor deptEmpDateProcessor) {
        serviceGoConfig = new ServiceGoConfig();
        serviceGoConfig.setUrl(properties.getProperty("url"));
        serviceGoConfig.setAPPkey(properties.getProperty("appkey"));
        serviceGoConfig.setAPPSercret(properties.getProperty("appsecret"));
        serviceGoConfig.setTokenServId(properties.getProperty("TokenServId"));
        serviceGoConfig.setDeptServId(properties.getProperty("DeptServId"));
        serviceGoConfig.setEmpServId(properties.getProperty("EmpServId"));
        serviceGoConfig.setSysId(properties.getProperty("sysId"));

        this.deptEmpDateProcessor = deptEmpDateProcessor;
        this.apiObjectName = apiObjectName;
    }

    public void getAndSaveData(Statement stmt) throws SQLException, IllegalAccessException {
        RecordHandler recordHandler = new RecordHandler(serviceGoConfig);
        //查询dept,得到dept结果集,创建 RecordHandler 时已获取，直接获取结果集
        //拿到所有的deptIds  &  departments
        Field deptDeclaredField = null;
        Field empDeclaredField = null;
        try {
            deptDeclaredField=  recordHandler.getClass().getDeclaredField("departments");
            empDeclaredField = recordHandler.getClass().getDeclaredField("allDeptIds");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        deptDeclaredField.setAccessible(true);
        empDeclaredField.setAccessible(true);
        HashSet<Department> deptQueryList = (HashSet<Department>) deptDeclaredField.get(recordHandler);
        ArrayList<Integer> allDeptIds = (ArrayList<Integer>) empDeclaredField.get(recordHandler);
        //获取所有的dept_id
        int[] deptIds = allDeptIds.stream().mapToInt(Integer::valueOf).toArray();
        //遍历deptIds调用emp接口，获得emp结果集
        HashSet<Employee> empQueryList = recordHandler.postEmp(deptIds, 100, 0,true);

        if (null == deptQueryList) {
            log.error("Query result is null");
            throw new RuntimeException("failed to query the data from esb.dept client");
        }
        if (null == empQueryList) {
            log.error("Query result is null");
            throw new RuntimeException("failed to query the data from esb.emp client");
        }


        if (deptQueryList != null || deptQueryList.size() != 0) {
            deptEmpDateProcessor.saveDept(deptQueryList,stmt);
            log.info("Download departments:" + deptQueryList.size() + " rows " + "data");
        }else {
            log.info("Can not download departments data");
            return;
        }

        if (empQueryList != null || empQueryList.size() != 0) {
            deptEmpDateProcessor.saveEmp(empQueryList,stmt);
            log.info("Download employees:" + empQueryList.size() + " rows data");
        }else {
            log.info("Can not download employees data");
        }
    }
}
