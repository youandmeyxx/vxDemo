package com.soecode.wxDemo.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

public class Log4jV2Util {
    public static final String layoutPattern = "%d %-5p [%t] %C (%F:%L) - %m%n";
    public static final String filePatternPostFix = ".%d{yyyy-MM-dd}.gz";

    public Log4jV2Util() {
    }

    public static Level getLevelByTag(String levelTag) {
        Level level = null;
        switch(levelTag.hashCode()) {
            case 3237038:
                if (levelTag.equals("info")) {
                    level = Level.INFO;
                }
                break;
            case 3641990:
                if (levelTag.equals("warn")) {
                    level = Level.WARN;
                }
                break;
            case 95458899:
                if (levelTag.equals("debug")) {
                    level = Level.DEBUG;
                }
                break;
            case 96784904:
                if (levelTag.equals("error")) {
                    level = Level.ERROR;
                }
                break;
            case 97203460:
                if (levelTag.equals("fatal")) {
                    level = Level.FATAL;
                }
        }

        return level;
    }

    public static void initLog4jV2TestEnv() {
        String userHome = System.getProperty("user.home");
        System.setProperty("catalina.home", userHome);
    }

    public static void adjustLoggerForMonitorConnectionPool() {
    }
}
