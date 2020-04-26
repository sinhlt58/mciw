package sinhblackgaming.mciw.modes;

import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class ModeBlockBreakSwitch extends Mode {
    public ModeBlockBreakSwitch(String name) {
        super(name);
    }

    public void onBlockBreak(BlockEvent.BreakEvent event){
        World world = event.getPlayer().world;

        if (world.isRemote || !this.isRunning()) return;
    }
}
