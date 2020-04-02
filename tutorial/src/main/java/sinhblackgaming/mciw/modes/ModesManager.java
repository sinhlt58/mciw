package sinhblackgaming.mciw.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModesManager {

    public static String MODE_SILVER_FISH = "silverfish";
    public static String MODE_MULTIPLY = "multiply";
    public static String MODE_ALL = "all";

    public static String STATUS_RUNNING = "running";
    public static String STATUS_STOPPED = "stopped";

    // used modes list
    public static String[] USED_MODES = {
        MODE_SILVER_FISH,
        MODE_MULTIPLY,
        MODE_ALL,
    };
    // mode status
    public static HashMap<String, String> modeStatus = new HashMap<>();

    public static void initUsedModes() {
        for(String m : USED_MODES) {
            modeStatus.put(m, STATUS_STOPPED);
        }
    }

    public static boolean startMode(String mode){
        if (!modeStatus.containsKey(mode)){
            return false;
        }
        modeStatus.put(mode, STATUS_RUNNING);
        return true;
    }

    public static boolean stopMode(String mode){
        if (!modeStatus.containsKey(mode)){
            return false;
        }
        modeStatus.put(mode, STATUS_STOPPED);
        return true;
    }

    public static String checkModeStatus(String mode){
        if (!modeStatus.containsKey(mode)){
            return "";
        }
        return modeStatus.get(mode);
    }

    public static void startAllModes() {
        for(Map.Entry<String, String> e : modeStatus.entrySet()) {
            startMode(e.getKey());
        }
    }

    public static void stopAllModes() {
        for(Map.Entry<String, String> e : modeStatus.entrySet()) {
            stopMode(e.getKey());
        }
    }

    public static List<String> getRunningModes(){
        ArrayList<String> res = new ArrayList<>();
        for(Map.Entry<String, String> e : modeStatus.entrySet()) {
            String mode = e.getKey();
            String status = e.getValue();

            if (status.equals(STATUS_RUNNING)) {
                res.add(mode);
            }
        }
        return res;
    }

    public static boolean hasMode(String mode) {
        return modeStatus.containsKey(mode);
    }
}
