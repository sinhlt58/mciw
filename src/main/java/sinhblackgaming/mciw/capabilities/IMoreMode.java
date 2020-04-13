package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.world.BlockEvent;
import sinhblackgaming.mciw.modes.ModeBlockBreakSilverFish;
import sinhblackgaming.mciw.modes.ModeRainLava;

import java.util.List;

public interface IMoreMode {
    boolean startMode(String mode);
    boolean stopMode(String mode);
    boolean pauseMode(String mode);
    boolean unPauseMode(String mode);
    String checkModeStatus(String mode);

    void startAllModes();
    void stopAllModes();
    void pauseAllModes();
    void unPauseAllModes();

    List<String> getRunningModeNames();
    boolean hasMode(String mode);
    String getModeAll();

    void setState(String mode, String state);
    CompoundNBT writeNBT();
    void readNBT(CompoundNBT nbt);

    // mode specific functions here
    ModeBlockBreakSilverFish getModeBlockBreakSilverFish();
    ModeRainLava getModeRainLava();
}
