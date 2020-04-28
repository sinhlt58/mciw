package sinhblackgaming.mciw.utils;

public class Utils {
    public static int randomDir(float chance){
        return Math.random() < chance ? 1 : -1;
    }
}
