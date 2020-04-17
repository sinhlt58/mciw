package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.world.BlockEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.modes.Mode;
import sinhblackgaming.mciw.modes.ModeBlockBreakSilverFish;
import sinhblackgaming.mciw.modes.ModeRainLava;
import sinhblackgaming.mciw.modes.ModeRandomBLockLook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoreMode implements IMoreMode {
    public static final Logger LOGGER = LogManager.getLogger();
    public static String MODE_ALL = "all";
    // register mode here
    public static String MODE_BLOCK_BREAK_SILVER_FISH = "block_break_silver_fish";
    public static String MODE_RAIN_LAVA = "rain_lava";
    public static String MODE_RANDOM_BLOCK_LOOK = "random_block_look";

    // register mode here
    public static String[] USED_MODE_NAMES = {
            MODE_BLOCK_BREAK_SILVER_FISH,
            MODE_RAIN_LAVA,
            MODE_RANDOM_BLOCK_LOOK,
            MODE_ALL,
    };

    public HashMap<String, Mode> modesMap = new HashMap<>();

    public MoreMode(){
        // register mode here
        modesMap.put(MODE_BLOCK_BREAK_SILVER_FISH, new ModeBlockBreakSilverFish(MODE_BLOCK_BREAK_SILVER_FISH));
        modesMap.put(MODE_RAIN_LAVA, new ModeRainLava(MODE_RAIN_LAVA));
        modesMap.put(MODE_RANDOM_BLOCK_LOOK, new ModeRandomBLockLook(MODE_RANDOM_BLOCK_LOOK));
    }

    @Override
    public boolean startMode(String mode) {
        if (!modesMap.containsKey(mode)){
            return false;
        }
        modesMap.get(mode).start();
        return true;
    }

    @Override
    public boolean stopMode(String mode) {
        if (!modesMap.containsKey(mode)){
            return false;
        }
        modesMap.get(mode).stop();
        return true;
    }

    @Override
    public boolean pauseMode(String mode) {
        if (!modesMap.containsKey(mode)){
            return false;
        }
        modesMap.get(mode).pause();
        return true;
    }

    @Override
    public boolean unPauseMode(String mode) {
        if (!modesMap.containsKey(mode)){
            return false;
        }
        modesMap.get(mode).unPause();
        return true;
    }

    @Override
    public String checkModeStatus(String mode) {
        if (!modesMap.containsKey(mode)){
            return "";
        }
        return modesMap.get(mode).getState();
    }

    @Override
    public void startAllModes() {
        for(Map.Entry<String, Mode> e : modesMap.entrySet()) {
            startMode(e.getKey());
        }
    }

    @Override
    public void stopAllModes() {
        for(Map.Entry<String, Mode> e : modesMap.entrySet()) {
            stopMode(e.getKey());
        }
    }

    @Override
    public void pauseAllModes() {
        for(Map.Entry<String, Mode> e : modesMap.entrySet()) {
            pauseMode(e.getKey());
        }
    }

    @Override
    public void unPauseAllModes() {
        for(Map.Entry<String, Mode> e : modesMap.entrySet()) {
            unPauseMode(e.getKey());
        }
    }

    @Override
    public List<String> getRunningModeNames() {
        List<String> res = new ArrayList<>();
        for(Map.Entry<String, Mode> e : modesMap.entrySet()) {
            Mode mode = e.getValue();
            if (mode.isRunning()){
                res.add(mode.name);
            }
        }
        return res;
    }

    @Override
    public boolean hasMode(String mode) {
        return mode.equals(MODE_ALL) || modesMap.containsKey(mode);
    }

    @Override
    public String getModeAll() {
        return MODE_ALL;
    }

    @Override
    public void setState(String mode, String state) {
        if (modesMap.containsKey(mode)){
            modesMap.get(mode).setState(state);
        }
    }

    @Override
    public CompoundNBT writeNBT() {
        CompoundNBT tag = new CompoundNBT();
        // write each mode's data
        for(Map.Entry<String, Mode> e : modesMap.entrySet()){
            e.getValue().writeNBT(tag);
        }
        return tag;
    }

    @Override
    public void readNBT(CompoundNBT nbt) {
        // read each mode's data
        for(Map.Entry<String, Mode> e : modesMap.entrySet()){
            e.getValue().readNBT(nbt);
        }
    }

    @Override
    public <T extends Mode> T getMode(String mode) {
        if (!this.modesMap.containsKey(mode)) return null;
        return (T) this.modesMap.get(mode);
    }
}
