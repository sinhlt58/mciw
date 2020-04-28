package sinhblackgaming.mciw.modes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class ModeBlockBreakRandomSpawnMob extends Mode {
    private ArrayList<EntityType<?>> mobs = new ArrayList<>();

    public ModeBlockBreakRandomSpawnMob(String name) {
        super(name);

        // get all registered entities to random later
        mobs.addAll(ForgeRegistries.ENTITIES.getValues());
    }

    public void onBlockBreak(BlockEvent.BreakEvent event){
        World world = event.getPlayer().world;

        if (world.isRemote || !this.isRunning()) return;

        BlockPos pos = event.getPos();

        int randIndex = (int)(Math.random() * (this.mobs.size() - 1));

        Entity entity = mobs.get(randIndex).create(world);
        if (entity != null){
            entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
            world.addEntity(entity);
        }
    }
}
