package com.robot.gs.command;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandLineParser {

  private Options options;

  public CommandLineParser() {
    options = new Options().addRequiredOption("m", "module",true, "module")
        .addOption("i", true, "interface")
        .addOption("d", true, "bizDate");
  }

  public CommandArgs parse(String[] args) throws ParseException {
      CommandLine line = new DefaultParser().parse(options, args);
      String module = line.getOptionValue("m");
      String intf = line.getOptionValue("i");
      String bizDate = line.getOptionValue("d");
      return new CommandArgs(module, intf, bizDate);
  }
}
