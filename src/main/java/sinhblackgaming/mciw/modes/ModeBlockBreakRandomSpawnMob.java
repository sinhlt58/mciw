package sinhblackgaming.mciw.modes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class ModeBlockBreakRandomSpawnMob extends Mode {
    private ArrayList<EntityType<?>> mobs = new ArrayList<>();

    private float radiusBound = 20f;
    private int maxNearMob = 100;

    public ModeBlockBreakRandomSpawnMob(String name) {
        super(name);

        // get all registered entities to random later
        mobs.addAll(ForgeRegistries.ENTITIES.getValues());
    }

    public void onBlockBreak(BlockEvent.BreakEvent event){
        World world = event.getPlayer().world;

        if (world.isRemote || !this.isRunning()) return;

        BlockPos pos = event.getPos();

        // calculate bounding box around the position
        AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - radiusBound, pos.getY() - 5, pos.getZ() - radiusBound,
                pos.getX() + radiusBound, pos.getY() + 20, pos.getZ() + radiusBound);
        int m = world.getEntitiesWithinAABB(MobEntity.class, bb).size();

        // not spawn if there are too many mobs around
        if (m > maxNearMob){
            return;
        }

        int randIndex = (int)(Math.random() * (this.mobs.size() - 1));

        Entity entity = mobs.get(randIndex).create(world);
        if (entity != null){
            entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
            world.addEntity(entity);
        }
    }
}
