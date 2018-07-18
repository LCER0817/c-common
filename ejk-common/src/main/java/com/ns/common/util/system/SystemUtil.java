package com.ns.common.util.system;

public class SystemUtil {
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    public static boolean isWindows() {
        if (getOsName().toLowerCase().indexOf("windows") != -1) {
            return true;
        }
        return false;
    }

    public static boolean isLinux() {
        if (getOsName().toLowerCase().indexOf("linux") != -1) {
            return true;
        }
        return false;
    }
}
