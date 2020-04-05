package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.world.BlockEvent;
import sinhblackgaming.mciw.modes.Mode;
import sinhblackgaming.mciw.modes.ModeBlockBreakSilverFish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoreMode implements IMoreMode {
    public static String MODE_ALL = "all";
    public static String MODE_BLOCK_BREAK_SILVER_FISH = "block_break_silver_fish";

    public static String[] USED_MODE_NAMES = {
            MODE_BLOCK_BREAK_SILVER_FISH,
            MODE_ALL,
    };

    // register mode here
    public ModeBlockBreakSilverFish modeBlockBreakSilverFish =
            new ModeBlockBreakSilverFish(MODE_BLOCK_BREAK_SILVER_FISH);

    public HashMap<String, Mode> modesMap = new HashMap<>();

    public MoreMode(){
        modesMap.put(MODE_BLOCK_BREAK_SILVER_FISH, modeBlockBreakSilverFish);
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
    public ModeBlockBreakSilverFish getModeBlockBreakSilverFish() {
        return modeBlockBreakSilverFish;
    }
}
