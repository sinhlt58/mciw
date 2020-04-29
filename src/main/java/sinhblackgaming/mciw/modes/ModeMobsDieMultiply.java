package sinhblackgaming.mciw.modes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import sinhblackgaming.mciw.utils.Utils;

public class ModeMobsDieMultiply extends Mode {
    private float chance = 0.5f;
    private int maxNum = 5;
    private float radiusBound = 20f;
    private int maxNearMob = 100;

    public ModeMobsDieMultiply(String name) {
        super(name);
    }

    public void onMobDeath(MobEntity mob){
        World world = mob.getEntityWorld();
        if (world.isRemote || !this.isRunning()) return;

        Vec3d pos = mob.getPositionVec();

        // calculate bounding box around the position
        AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - radiusBound, pos.getY() - 5, pos.getZ() - radiusBound,
                pos.getX() + radiusBound, pos.getY() + 20, pos.getZ() + radiusBound);
        int m = world.getEntitiesWithinAABB(MobEntity.class, bb).size();

        // not spawn if there are too many mobs around
        if (m > maxNearMob){
            return;
        }

        if (Math.random() < chance){
            // random number to spawn
            int n = (int)(Math.random()*maxNum) + 1;
            for (int i=0; i<n; i++){
                Vec3d spawnPos = pos.add(
                        Utils.randomDir(0.5f) + Utils.randomDir(0.5f),
                        1, Utils.randomDir(0.5f) + Utils.randomDir(0.5f));
                // spawn the entity
                Entity entity = mob.getType().create(world);
                if (entity != null){
                    entity.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                    world.addEntity(entity);
                }
            }
        }
    }
}
