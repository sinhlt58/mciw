package sinhblackgaming.mciw.modes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;

public class ModeBlockBreakSilverFish extends Mode {
    public ModeBlockBreakSilverFish(String name){
        super(name);
    }

    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!event.getPlayer().world.isRemote && this.isRunning()) {
            PlayerEntity player = event.getPlayer();
            BlockPos pos = event.getPos();

            SilverfishEntity silverFish = EntityType.SILVERFISH.create(player.world);
            silverFish.setPosition(pos.getX(), pos.getY(), pos.getZ());
            player.world.addEntity(silverFish);
        }
    }
}
