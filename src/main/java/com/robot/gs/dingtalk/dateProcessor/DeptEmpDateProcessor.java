package com.robot.gs.dingtalk.dateProcessor;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.CommonDataConverter;
import com.robot.gs.common.CommonDataProcessor;
import com.robot.gs.dingtalk.bean.Department;
import com.robot.gs.dingtalk.bean.Employee;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.robot.gs.udesk.integration.Helper.value;

@Slf4j
public class DeptEmpDateProcessor extends CommonDataProcessor {
    private static final String dbName ="gs_ods";
    private static final String deptTableName ="ods_f_dingtalk_department";
    private static final String empTableName ="ods_f_dingtalk_employee";
    public DeptEmpDateProcessor(ApiNameEnum apiNameEnum, String dbName, String tbName, LocalDate startDate,
                                boolean hasPartition) {
        super(apiNameEnum, dbName, tbName, startDate, hasPartition);
    }



    @Override
    protected CommonDataConverter createConverter(ApiNameEnum apiNameEnum) {
        return null;
    }

    @Override
    public void save(Statement stmt, List<Map<String, Object>> dataList, Boolean isFirst) throws SQLException {

    }

    public void saveDept(HashSet<Department> result, Statement stmt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        StringBuilder builder = new StringBuilder(10240);
        builder.append(String.format("INSERT OVERWRITE TABLE %s.%s\n", this.dbName, this.deptTableName));
                builder.append("PARTITION (pt)\n")
                .append("SELECT INLINE (ARRAY ( ");
        for (Department department : result) {
            builder.append("struct('");
            builder.append(department.getDeptId()).append("','");
            builder.append(department.getName()).append("','");
            builder.append(department.getDeptLevel()).append("','");
            builder.append(department.getParentId()).append("','");
            builder.append(department.isAutoAddUser()).append("','");
            builder.append(department.isCreateDeptGroup()).append("','");
            builder.append(department.getExt()==null?"":department.getExt()).append("'");
            if (hasPartition == true) {
                builder.append(",'" + formatter.format(startDate)+"'");
            }
            builder.append(")").append(",");
        }
        if (builder.toString().endsWith(",")) {
            builder.delete(builder.length() - 1, builder.length())
                    .append("))")
            ;
            try {
                log.info(builder.toString());
                stmt.execute(builder.toString());
                log.info("save department:"+result.size()+" rows data");
            } catch (SQLException throwables) {
                log.error("save department date failed");
                throwables.printStackTrace();
                throw new RuntimeException("insert department data failed");
            }
        }
    }

    public void saveEmp(HashSet<Employee> result, Statement stmt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        StringBuilder builder = new StringBuilder(10240);
        builder.append(String.format("INSERT OVERWRITE TABLE %s.%s\n", this.dbName, this.empTableName));
        builder.append("PARTITION (pt)\n")
                .append("SELECT INLINE (ARRAY ( ");
        for (Employee employee : result) {
            builder.append("struct('");
            builder.append(value(employee.getUserId())).append("','");
            builder.append(value(employee.getName())).append("','");
            builder.append(value(employee.getUnionId())).append("','");
            builder.append(value(employee.getTitle())).append("','");
            builder.append(value(employee.getJobNumber())).append("','");
            builder.append(value(employee.getTelephone())).append("','");
            builder.append(value(employee.getEmail())).append("','");
            builder.append(value(employee.getOrgEmail())).append("','");
            builder.append(value(employee.getWorkPlace())).append("','");
            builder.append(value(employee.getRemark())).append("','");
            builder.append(value(employee.getExtension())).append("','");
            builder.append(employee.isLeader()).append("','");
            builder.append(employee.getDeptId()).append("','");
            builder.append(value(employee.getDeptIdList().toString())).append("','");
            builder.append(employee.isActive()).append("','");
            builder.append(employee.isAdmin()).append("','");
            builder.append(value(employee.getAvatar())).append("','");
            builder.append(employee.isBoss()).append("','");
            builder.append(employee.getDeptOrder()).append("','");
            builder.append(employee.isExclusiveAccount()).append("','");
            builder.append(employee.isHideMobile()).append("','");
            builder.append(employee.getHiredDate()==null?0:employee.getHiredDate()).append("'");
            if (hasPartition == true) {
                builder.append(",'" + formatter.format(startDate) + "'");
            }
            builder.append(")").append(",");
        }
        if (builder.toString().endsWith(",")) {
            builder.delete(builder.length() - 1, builder.length())
                    .append("))");

            try {
                log.info(builder.toString());
                stmt.execute(builder.toString());
                log.info("save employee:" + result.size() + " rows data");
            } catch (SQLException throwables) {
                log.error("save employee date failed");
                throwables.printStackTrace();
                throw new RuntimeException("insert employee data failed");
            }
        }
    }
}
