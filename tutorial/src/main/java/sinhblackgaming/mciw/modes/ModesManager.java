package sinhblackgaming.mciw.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModesManager {

    public static String MODE_ALL = "all";

    public static ModeBlockBreakSilverFish blockBreakSilverFish = new ModeBlockBreakSilverFish("block_break_silver_fish");

    // register mode here
    // used modes list
    public static Mode[] USED_MODES = {
            blockBreakSilverFish
    };
    // modes map
    public static HashMap<String, Mode> modesMap = new HashMap<>();
    // running mode names
    public static List<String> USED_MODE_NAMES = new ArrayList<>();

    public static void initUsedModes() {
        for(Mode m : USED_MODES) {
            modesMap.put(m.name, m);
            USED_MODE_NAMES.add(m.name);
        }
    }

    public static boolean startMode(String mode){
        if (!modesMap.containsKey(mode)){
            return false;
        }
        modesMap.get(mode).start();
        return true;
    }

    public static boolean stopMode(String mode){
        if (!modesMap.containsKey(mode)){
            return false;
        }
        modesMap.get(mode).stop();
        return true;
    }

    public static String checkModeStatus(String mode){
        if (!modesMap.containsKey(mode)){
            return "";
        }
        return modesMap.get(mode).state;
    }

    public static void startAllModes() {
        for(Map.Entry<String, Mode> e : modesMap.entrySet()) {
            startMode(e.getKey());
        }
    }

    public static void stopAllModes() {
        for(Map.Entry<String, Mode> e : modesMap.entrySet()) {
            stopMode(e.getKey());
        }
    }

    public static List<String> getRunningModeNames(){
        ArrayList<String> res = new ArrayList<>();
        for(Map.Entry<String, Mode> e : modesMap.entrySet()) {
            Mode mode = e.getValue();
            if (mode.isRunning()){
                res.add(mode.name);
            }
        }
        return res;
    }

    public static boolean hasMode(String mode) {
        return modesMap.containsKey(mode);
    }
}
