package com.robot.gs.jira.integration;

import com.atlassian.jira.rest.client.api.domain.*;
import com.google.gson.reflect.TypeToken;
import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.CommonDataConverter;
import com.robot.gs.ticket.http.jira.handler.RecordHandler;
import com.robot.gs.ticket.http.jira.result.ChangeLogRecordFiles.ChangelogRecord;
import com.robot.gs.ticket.http.jira.result.ChangeLogRecordFiles.ClRChangelogHistories;
import com.robot.gs.ticket.http.jira.result.ChangeLogRecordFiles.ClRChangelogHistoriesItems;
import com.robot.gs.ticket.http.jira.result.StatusStatisticDTOS;
import com.robot.gs.ticket.http.jira.result.WorkFlow;
import com.robot.gs.udesk.integration.DataConverter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

import static com.robot.gs.common.DataUtil.dealDateFormat;

@Slf4j
public class JiraConverter extends DataConverter<JiraInfo> {

    public JiraInfo from(Issue field, ApiNameEnum apiName, RecordHandler recordHandler, Map<String, String> statusMap){
    JiraInfo jiraInfo = new JiraInfo();
      try {
          map(jiraInfo, field, apiName, recordHandler, statusMap);
      } catch (ParseException | IOException e) {
          e.printStackTrace();
      }
      return jiraInfo;
  }

  private void map(JiraInfo jiraInfo, Issue field, ApiNameEnum apiName,RecordHandler recordHandler, Map<String, String> statusMap) throws ParseException, IOException {
        //workflow
        String workFlowResult = recordHandler.queryWorkFlow(field.getKey());
        List<WorkFlow> workFlowList = gson.fromJson(workFlowResult, new TypeToken<List<WorkFlow>>() {}.getType());
        List<WorkFlowOpen> workFlowOpenList = new ArrayList<>();
        for (int workFlowListIndex = 0; workFlowListIndex < workFlowList.size(); workFlowListIndex++){
            WorkFlow workFlow = workFlowList.get(workFlowListIndex);
            List<StatusStatisticDTOS> statusStatisticDTOSList = workFlow.getStatusStatisticDTOS();
            for(int statusStatisticDTOSListIndex = 0; statusStatisticDTOSListIndex < statusStatisticDTOSList.size(); statusStatisticDTOSListIndex++){
                StatusStatisticDTOS statusStatisticDTOS = statusStatisticDTOSList.get(statusStatisticDTOSListIndex);
                WorkFlowOpen wfoe = new WorkFlowOpen();
                wfoe.setIssueKey(workFlow.getIssueKey());
                wfoe.setAssigneeId(workFlow.getAssigneeId());
                wfoe.setUserName(workFlow.getUserName());
                String statusId = statusStatisticDTOS.getStatusId();
                wfoe.setStatusId(statusId);
                wfoe.setStatusName(statusMap.get(statusId));
                wfoe.setTimeHistoryStr(statusStatisticDTOS.getTimeHistoryStr());
                wfoe.setStartDate(statusStatisticDTOS.getStartDate());
                wfoe.setEndDate(statusStatisticDTOS.getEndDate());
                wfoe.setDuration(statusStatisticDTOS.getDuration());
                wfoe.setTimeSpentStr(statusStatisticDTOS.getTimeSpentStr());
                wfoe.setTimeSpentDuration(statusStatisticDTOS.getTimeSpentDuration());
                workFlowOpenList.add(wfoe);
            }
        }
        String gsonStr = gson.toJson(workFlowOpenList);
        jiraInfo.setWorkFlow(value(gsonStr));

      //changelogRecord
      String changelogRecordResult = recordHandler.queryChangelogRecord(field.getKey());
      ChangelogRecord changelogRecord = gson.fromJson(changelogRecordResult, new TypeToken<ChangelogRecord>() {}.getType());
      List<ChangelogRecordOpen> changelogRecordOpenList = new ArrayList<>();
      ChangelogRecordOpen clro = null;
      List<ClRChangelogHistories> clRChangelogHistoriesList = changelogRecord.getChangelog().getHistories();
      for (int clRChangelogHistoriesListIndex = 0; clRChangelogHistoriesListIndex < clRChangelogHistoriesList.size(); clRChangelogHistoriesListIndex++) {
          ClRChangelogHistories clRChangelogHistories = clRChangelogHistoriesList.get(clRChangelogHistoriesListIndex);
          List<ClRChangelogHistoriesItems> clRChangelogHistoriesItemsList = clRChangelogHistories.getItems();
          for (int clRChangelogHistoriesItemsListIndex = 0; clRChangelogHistoriesItemsListIndex < clRChangelogHistoriesItemsList.size(); clRChangelogHistoriesItemsListIndex++) {
              ClRChangelogHistoriesItems clRChangelogHistoriesItems = clRChangelogHistoriesItemsList.get(clRChangelogHistoriesItemsListIndex);
              clro = new ChangelogRecordOpen();
              clro.setId(changelogRecord.getId());
              clro.setKey(changelogRecord.getKey());
              clro.setHistoryId(clRChangelogHistories.getId() != null ? clRChangelogHistories.getId() : "");
              clro.setHistoryCreated(clRChangelogHistories.getCreated() != null ? clRChangelogHistories.getCreated() : "");
              clro.setHistoryAuthorName(clRChangelogHistories.getAuthor().getName() != null ? clRChangelogHistories.getAuthor().getName() : "");
              clro.setHistoryAuthorKey(clRChangelogHistories.getAuthor().getKey() != null ? clRChangelogHistories.getAuthor().getKey() : "");
              clro.setHistoryAuthorEmailAddress(clRChangelogHistories.getAuthor().getEmailAddress() != null ? clRChangelogHistories.getAuthor().getEmailAddress() : "");
              clro.setHistoryAuthorDisplayName(clRChangelogHistories.getAuthor().getDisplayName() != null ? clRChangelogHistories.getAuthor().getDisplayName() : "");
              clro.setHistoryAuthorActive(clRChangelogHistories.getAuthor().getActive() != null ? clRChangelogHistories.getAuthor().getActive() : "" );
              clro.setHistoryAuthorTimeZone(clRChangelogHistories.getAuthor().getTimeZone() != null ? clRChangelogHistories.getAuthor().getTimeZone() : "");
              //修复字段value含有\导致Json解析失败，替换为/
              //修复字段中value含有"导致Json解析失败，替换为?
              //修复字段中value含有;导致Json解析失败，替换为?
              clro.setHistoryItemsField((clRChangelogHistoriesItems.getField() != null ? clRChangelogHistoriesItems.getField() : "").replaceAll("\\\\","/").replaceAll("\"", "\\?").replaceAll("\\;", "\\?"));
              clro.setHistoryItemsFieldtype((clRChangelogHistoriesItems.getFieldtype() != null ? clRChangelogHistoriesItems.getFieldtype() : "").replaceAll("\\\\","/").replaceAll("\"", "\\?").replaceAll("\\;", "\\?"));
              clro.setHistoryItemsFrom((clRChangelogHistoriesItems.getFrom() != null ? clRChangelogHistoriesItems.getFrom() : "").replaceAll("\\\\","/").replaceAll("\"", "\\?"));
              clro.setHistoryItemsFromString((clRChangelogHistoriesItems.getFromString() != null ? clRChangelogHistoriesItems.getFromString() : "").replaceAll("\\\\","/").replaceAll("\"", "\\?").replaceAll("\\;", "\\?"));
              clro.setHistoryItemsTo((clRChangelogHistoriesItems.getTo() != null ? clRChangelogHistoriesItems.getTo() : "").replaceAll("\\\\","/").replaceAll("\"", "\\?").replaceAll("\\;", "\\?"));
              clro.setHistoryItemsToString((clRChangelogHistoriesItems.getToString() != null ? clRChangelogHistoriesItems.getToString() : "").replaceAll("\\\\","/").replaceAll("\"", "\\?").replaceAll("\\;", "\\?"));
              changelogRecordOpenList.add(clro);
          }
      }
      String gsonStrCl = gson.toJson(changelogRecordOpenList);
      jiraInfo.setChangelogRecord(value(gsonStrCl));

        jiraInfo.setId(Long.parseLong(value(field.getId())));
        jiraInfo.setKey(value(field.getKey()));
        jiraInfo.setIssueType(value((field.getIssueType() != null) ? field.getIssueType().getName() : ""));
        jiraInfo.setLabels(value(field.getLabels()));
        jiraInfo.setStatus(value((field.getStatus() != null) ? field.getStatus().getName() : ""));
        jiraInfo.setProjectName(value((field.getProject() != null) ? field.getProject().getName() : ""));
        jiraInfo.setProjectKey(value((field.getProject() != null) ? field.getProject().getKey() : ""));
        //jiraInfo.setExpandos(value(field.getExpandos()));
        //Components
        List<String> componentsNameList = new ArrayList<String>();
        Iterable<BasicComponent> components = field.getComponents();
        components.forEach(basicComponent -> {
            componentsNameList.add(basicComponent.getName());
        });
        jiraInfo.setComponents(value(componentsNameList));

        jiraInfo.setSummary(value(field.getSummary()).replace("\'", "\""));
        jiraInfo.setDescription(value(field.getDescription()).replace("\'", "\""));
        jiraInfo.setReporter(value((field.getReporter() != null) ? field.getReporter().getDisplayName(): ""));
        jiraInfo.setAssignee(value((field.getAssignee() != null) ? field.getAssignee().getDisplayName() : ""));
        jiraInfo.setResolution(value((field.getResolution() != null) ? field.getResolution().getName() : ""));
        //IssueField
        ArrayList<IssueField> issueFieldList = (ArrayList<IssueField>) field.getFields();
        issueFieldList.sort(
                Comparator.comparing(issueField -> issueField.getId())
        );
        Map<String, Object> issueFieldMap = null;
        switch (apiName) {
            case JIRA_SELLSERVIC:
                issueFieldMap = setJiraSellServic(issueFieldList);
                break;
            case JIRA_SOFTREQ:
                issueFieldMap = setJiraSoftReq(issueFieldList);
                break;
//            case JIRA_ZC:
//                break;
            case JIRA_QD:
                issueFieldMap = setJiraQd(issueFieldList);
                break;
            default:
                issueFieldMap = new HashMap<>();
                Map<String, Object> finalIssueFieldMap = issueFieldMap;
                issueFieldList.forEach(issueField -> {
                    finalIssueFieldMap.put(issueField.getName(), issueField.getValue());
                });
                break;
        }

//      //IssueField
//      ArrayList<IssueField> issueFieldList = (ArrayList<IssueField>) field.getFields();
//      issueFieldList.sort(
//              Comparator.comparing(issueField -> issueField.getId())
//      );
//
//      Map<String, Object> issueFieldMap = new HashMap<>();
//      issueFieldList.forEach(issueField -> {
//          issueFieldMap.put(issueField.getName(), issueField.getValue());
//      });


        String jsonStr = gson.toJson(issueFieldMap);
        jiraInfo.setIssueFields(value(jsonStr));

        jiraInfo.setCreationDate((field.getCreationDate() != null) ? LocalDateTime.parse(dealDateFormat(value(field.getCreationDate())), dateTimeFormatter) : null);
        jiraInfo.setUpdateDate((field.getUpdateDate() != null) ? LocalDateTime.parse(dealDateFormat(value(field.getUpdateDate())), dateTimeFormatter) : null);
        //jiraInfo.setDueDate((field.getDueDate() != null) ? LocalDateTime.parse(dealDateFormat(value(field.getDueDate())), dateTimeFormatter) : null);
        jiraInfo.setPriority(value((field.getPriority() != null) ? field.getPriority().getName() : ""));
        jiraInfo.setVotes((long) ((field.getVotes() != null) ? field.getVotes().getVotes() : 0));
        //fixVersions
        List<String> fixVersionsNameList = new ArrayList<String>();
        Iterable<Version> fixVersions = field.getFixVersions();
        fixVersions.forEach(version -> {
            fixVersionsNameList.add(version.getName());
        });
        jiraInfo.setFixVersions(value(fixVersionsNameList));

        //affectedVersions
        List<String> affectedVersionsNameList = new ArrayList<String>();
        Iterable<Version> affectedVersions = field.getAffectedVersions();
        affectedVersions.forEach(version -> {
            affectedVersionsNameList.add(version.getName());
        });
        jiraInfo.setAffectedVersions(value(affectedVersionsNameList));

        //jiraInfo.setComments(value(field.getComments()));//待验证

        Iterable<IssueLink> issueLinks = field.getIssueLinks();
        List<String> issueLinkList = new ArrayList<String>();
        issueLinks.forEach(issueLink -> {
            issueLinkList.add(issueLink.getTargetIssueKey());
        });
        jiraInfo.setIssueLinks(value(issueLinkList));

        //jiraInfo.setAttachments(value(field.getAttachments()));//待验证
        //jiraInfo.setWorklogs(value(field.getWorklogs()));//待验证
        jiraInfo.setWatchers((long) ((field.getWatchers() != null) ? field.getWatchers().getNumWatchers() : 0));
        //jiraInfo.setTimeTracking((long) ((field.getTimeTracking() != null) ? field.getTimeTracking().getTimeSpentMinutes() : 0));//待验证
        //jiraInfo.setSubtasks(value(field.getSubtasks()));//待验证
        //jiraInfo.setOperations(value(field.getOperations())); //待验证
        jiraInfo.setReporterEmail(field.getReporter() != null ?
              field.getReporter().getEmailAddress() : "");
        jiraInfo.setAssigneeEmail(field.getAssignee() != null ?
              field.getAssignee().getEmailAddress() : "");
  }
    //要那个字段传哪个字段根据doris建表语句
    private Map<String, Object> setJiraQd(ArrayList<IssueField> issueFields) {
        Map<String, Object> result = new HashMap<>();
        for(IssueField issueField:issueFields){
            switch (issueField.getName()){
                case "QD立项人":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "严重等级":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "临时改善措施导入日期":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "临时改善措施导入车号":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "客户分类":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "归属模块":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "故障现象分类":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "故障现象分类二级":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "整机版本":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "是否有临时措施":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "是否有市场解决方案":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "是否有长期措施":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "模块DQE":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "立项日期":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "立项问题名称":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "立项问题来源":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "立项问题类别":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "责任部门":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "长期措施生产导入日期":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "长期措施生产导入车号":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "问题负责人":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "机型":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "已解决":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "创建者":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "归属产品":
                    result.put(issueField.getName(),issueField.getValue());break;
                default:
                    break;

            }
        }
        return result;
    }

    private Map<String, Object> setJiraSoftReq(ArrayList<IssueField> issueFields) {
        Map<String, Object> result = new HashMap<>();
        for(IssueField issueField:issueFields){
            switch (issueField.getName()){
                case "归属产品":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "创建者":
                    result.put(issueField.getName(),issueField.getValue());break;
                default:
                    break;

            }
        }
        return result;
    }

    private Map<String, Object> setJiraSellServic(ArrayList<IssueField> issueFields) {
        Map<String, Object> result = new HashMap<>();
        for(IssueField issueField:issueFields){
            switch (issueField.getName()){
                case "创建者":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "上位机固件版本":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "归属产品":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "产品ID":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "客户所在区域":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "责任部门":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "产生原因分类":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "漏出/放行原因分类":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "重点问题":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "研发回复是否合规":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "漏出/放行改进闭环结果":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "漏出/放行责任部门":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "发现build/提测":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "健康告警":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "开箱阶段问题":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "需求特性":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "是否更换配件":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "问题原因分类":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "Udesk服务工单":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "故障现象分类":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "DQE模块":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "jira提交规范记录":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "问题性质":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "客户感知分类":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "售后问题来源":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "售后责任问题记录":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "失效场景分类":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "KA合同客户":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "需要总部给临时方案":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "种子客户验证":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "问题等级":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "是否立项":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "是否提供了临时方案":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "故障现象分类一级":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "故障现象分类二级":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "故障现象分类三级":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "是否外场测试问题":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "方案提供时限（小时）":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "二线FAE处理人":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "二线FAE处理结果":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "原因细分":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "一级故障现象分类":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "云平台-事件编码":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "云平台-事件名称":
                    result.put(issueField.getName(),issueField.getValue());break;
                case "远程是否可以解决":
                    result.put(issueField.getName(),issueField.getValue());break;
                default:
                    break;

            }
        }
        return result;
    }

    @Override
  public JiraInfo from(Map<String, Object> data) {
    return null;
  }
}
