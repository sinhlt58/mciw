package sinhblackgaming.mciw.modes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import sinhblackgaming.mciw.MCIWMod;

public class ModeBlockBreakSilverFish extends Mode {
    private int countSilverSilverFish = 0;

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
            increaseCountSilverFish(1);
        }
    }

    public void setCountSilverSilverFish(int n){
        this.countSilverSilverFish = n;
    }

    public void increaseCountSilverFish(int amount){
        this.countSilverSilverFish += amount;
    }

    public int getCountSilverSilverFish(){return this.countSilverSilverFish;}

    @Override
    public void writeNBT(CompoundNBT tag) {
        super.writeNBT(tag);
        tag.putInt(MCIWMod.MODID + ".modedata.blockbreaksilverfish.count", countSilverSilverFish);
    }

    @Override
    public void readNBT(CompoundNBT nbt) {
        super.readNBT(nbt);
        setCountSilverSilverFish(nbt.getInt(MCIWMod.MODID + ".modedata.blockbreaksilverfish.count"));
    }
}
