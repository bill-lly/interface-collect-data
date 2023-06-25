package com.robot.gs;

import com.robot.gs.command.CommandArgs;
import com.robot.gs.command.CommandLineParser;
import com.robot.gs.dingtalk.DingTalkApp;
import com.robot.gs.kingdee.KingdeeApp;
import com.robot.gs.udesk.WriteUdesk.WriteUdeskApp;
import com.robot.gs.udesk.integration.UdeskApp;
import com.robot.gs.mes.MesApp;
import lombok.extern.slf4j.Slf4j;
import com.robot.gs.jira.integration.JiraApp;

@Slf4j
public class Application {

  public static void main(String[] args) {
    Integer exitCode = 0;

    try {
      CommandLineParser parser = new CommandLineParser();
      CommandArgs params = parser.parse(args);

      switch (params.getModule()) {
        case UDESK:
          UdeskApp udeskApp = new UdeskApp(params);
          exitCode = udeskApp.process();
          break;
        case WRITEUDESK:
          WriteUdeskApp writeUdeskApp = new WriteUdeskApp(params);
          exitCode = writeUdeskApp.process();
          break;
        case JIRA:
          JiraApp jiraApp = new JiraApp(params);
          exitCode = jiraApp.process();
          break;
        case DINGTALK:
          DingTalkApp dingTalkApp = new DingTalkApp(params);
          exitCode = dingTalkApp.process();
          break;
        case MES:
          MesApp mesApp = new MesApp(params);
          exitCode = mesApp.process();
          break;
        case KINGDEE:
          KingdeeApp kingdeeApp = new KingdeeApp(params);
          exitCode = kingdeeApp.process();
          break;
        default:
          break;
      }
    } catch (Exception e) {
      log.error("failed to do process", e);
      exitCode = 1;
    }
    System.exit(exitCode);
  }

}