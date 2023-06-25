package com.robot.gs.udesk.WriteUdesk.processor;

import com.alibaba.fastjson2.JSONObject;
import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.ticket.http.udesk.handler.RecordHandler;
import com.robot.gs.ticket.http.udesk.model.common.FieldData;
import com.robot.gs.udesk.WriteUdesk.common.CommonDataProcessor;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Slf4j
public class WorkTicketDataProcessor<T> extends CommonDataProcessor {
    private ApiNameEnum apiNameEnum;

    public WorkTicketDataProcessor(ApiNameEnum apiNameEnum) {
        super(apiNameEnum);
    }


    @Override
    public List<FieldData> mapping(ResultSet resultSet, ApiNameEnum apiNameEnum) throws SQLException {
        Map<String, String> from = from(resultSet);
        List<FieldData> dataList = new ArrayList<>();
        log.info("mapping ticket_no : "+from.get("ticket_no")+"");
        Iterator<String> iterator = from.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = from.get(key);
            switch (key) {
                case "deploy_start_time":
                    dataList.add(new FieldData("kaishibushuyindaoshijian", value, ""));
                    break;
                case "deploy_end_time":
                    dataList.add(new FieldData("bushuwanchengshijian", value, ""));
                    break;
                case "total_duration":
                    dataList.add(new FieldData("shiyongbushuyindaozongshijian", value, ""));
                    break;
                case "origin_module_duration":
                    JSONObject modlueDuration = JSONObject.parseObject(String.valueOf(value));
                    Set<String> keySet = modlueDuration.keySet();
                    Iterator<String> keyIterator = keySet.iterator();
                    while (keyIterator.hasNext()) {
                        String primaryKey = keyIterator.next();
                        JSONObject primaryValue = modlueDuration.getJSONObject(primaryKey);
                        // 一级模块
                        switch (primaryKey) {
                            // 拆箱及部署准备总时长
                            case "unpacking_n_deployment":
                                dataList.add(new FieldData("chaixiangjibushuzhunbeizongshichang",
                                        String.valueOf(primaryValue.get("module_duration")), ""));
                                break;
                            // 环境创建总时间
                            case "create_map":
                                dataList.add(new FieldData("huanjingchuangjianzongshijian",
                                        String.valueOf(primaryValue.get("module_duration")), ""));
                                break;
                            // 地图管理总时间
                            case "map_management":
                                dataList.add(new FieldData("dituguanlizongshijian", String.valueOf(primaryValue.get(
                                        "module_duration")), ""));
                                break;
                            // 调试模式总时间
                            case "test_pile_mode":
                                dataList.add(new FieldData("diaoshimoshizongshijian",
                                        String.valueOf(primaryValue.get("module_duration")), ""));
                                break;
                            // 试跑模式总时长
                            case "test_mode":
                                dataList.add(new FieldData("shipaomoshizongshichang",
                                        String.valueOf(primaryValue.get("module_duration")), ""));
                                break;
                            // 客户培训总时间
                            case "customer_train":
                                dataList.add(new FieldData("kehupeixunzongshijian", String.valueOf(primaryValue.get(
                                        "module_duration")), ""));
                                break;
                            // 梯控管理总时间
                            case "elevator_management":
                                dataList.add(new FieldData("tikongguanlizongshijian",
                                        String.valueOf(primaryValue.get("module_duration")), ""));
                                break;
                            // 清洁配置总时间
                            case "clean_config":
                                dataList.add(new FieldData("qingjiepeizhizongshijian",
                                        String.valueOf(primaryValue.get("module_duration")), ""));
                                break;
                            // 任务规划总时间
                            case "task_planning":
                                dataList.add(new FieldData("renwuguihuazongshijian", String.valueOf(primaryValue.get(
                                        "module_duration")), ""));
                                break;
                            // 机器人设置总时间
                            case "robot_settings":
                                dataList.add(new FieldData("jiqishezhi", String.valueOf(primaryValue.get("module_duration")), ""));
                                break;
                            // 站点管理总时间
                            case "site_management":
                                dataList.add(new FieldData("zhandianguanli", String.valueOf(primaryValue.get("module_duration"))
                                        , ""));
                                break;
                            // 以下模块无需同步udesk
                            // 外设管理
                            case "peripheral_management":
                                break;
                            // 楼宇管理
                            case "building_management":
                                break;
                            // 任务排班
                            case "task_scheduling":
                                break;
                            // 其他(引导程序页面停)
                            case "other":
                                break;
                            default:
                                break;
                        }
                        // 二级模块
                        if (!"other".equals(primaryKey)) {
                            JSONObject secondaryModule = primaryValue.getJSONObject("secondary_module");
                            Set<String> secondaryModuleName = secondaryModule.keySet();
                            Iterator<String> secondaryModuleIterator = secondaryModuleName.iterator();
                            while (secondaryModuleIterator.hasNext()) {
                                String moduleName = secondaryModuleIterator.next();
                                switch (moduleName) {
                                    // 扫图时间
                                    case "create_map-add_map_btn":
                                        dataList.add(new FieldData("saotushijian",
                                                secondaryModule.getString(moduleName), ""));
                                        break;
                                    // 扩展地图时间
                                    case "create_map-extend_map_btn":
                                        dataList.add(new FieldData("kuozhanditushijian",
                                                secondaryModule.getString(moduleName), ""));
                                        break;
                                    // 外设管理
                                    case "equipment_management":
                                        dataList.add(new FieldData("waisheguanli",
                                                secondaryModule.getString(moduleName), ""));
                                        break;
                                    // 地图编辑
                                    case "map_edit":
                                        dataList.add(new FieldData("ditubianji", secondaryModule.getString(
                                                moduleName), ""));
                                        break;
                                    // 点管理
                                    case "point_management":
                                        dataList.add(new FieldData("dianguanli",
                                                secondaryModule.getString(moduleName), ""));
                                        break;
                                    // 路径管理
                                    case "path_management":
                                        dataList.add(new FieldData("lujingguanli",
                                                secondaryModule.getString(moduleName), ""));
                                        break;
                                    // 组合任务
                                    case "combined_task":
                                        dataList.add(new FieldData("zuherenwu", secondaryModule.getString(
                                                moduleName), ""));
                                        break;
                                    // 清洁区域 任务分区（原：清洁区域）
                                    case "task_subarea":
                                        dataList.add(new FieldData("qingjiequyu", secondaryModule.getString(
                                                moduleName), ""));
                                        break;
                                    // 交付培训
                                    case "delivery_train":
                                        dataList.add(new FieldData("jiaofupeixun",
                                                secondaryModule.getString(moduleName), ""));
                                        break;
                                    // 客户答题
                                    case "customer_answer":
                                        dataList.add(new FieldData("kehudati", secondaryModule.getString(
                                                moduleName), ""));
                                        break;
                                    // 产品交付评
                                    case "customer_evaluation":
                                        dataList.add(new FieldData("chanpinjiaofuping",
                                                secondaryModule.getString(moduleName), ""));
                                        break;
                                    // 楼层配置（楼层管理）
                                    case "floor_config":
                                        dataList.add(new FieldData("loucengpeizhi",
                                                secondaryModule.getString(moduleName), ""));
                                        break;
                                    // 绑定地图
                                    case "bind_floor_map":
                                        dataList.add(new FieldData("bangdingditu",
                                                secondaryModule.getString(moduleName), ""));
                                        break;
                                    // 梯控任务
                                    case "elevator_task":
                                        dataList.add(new FieldData("tikongrenwu", secondaryModule.getString(
                                                moduleName), ""));
                                        break;
                                    // 清洁模式/设备等级
                                    case "clean_n_device":
                                        dataList.add(new FieldData("qingjiemoshishebeidengji",
                                                secondaryModule.getString(moduleName), ""));
                                        break;
                                    // 绑定清洁模式
                                    case "bind_clean_config":
                                        dataList.add(new FieldData("bangdingqingjiemoshi",
                                                secondaryModule.getString(moduleName), ""));
                                        break;
                                    // 电梯管理(已删除)
                                    case "电梯管理":
                                        break;
                                    // 对桩测试（暂不支持）
                                    case "对桩测试":
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return dataList;
    }


    @Override
    public void save(Statement stmt, List<Map<String, Object>> dataList, Boolean isFirst) throws SQLException {

    }
}
