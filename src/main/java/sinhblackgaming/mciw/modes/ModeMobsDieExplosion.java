package sinhblackgaming.mciw.modes;

import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ModeMobsDieExplosion extends Mode {
    private float chance = 0.5f;
    private float maxRadius = 2.5f;

    public ModeMobsDieExplosion(String name) {
        super(name);
    }

    public void onMobDeath(MobEntity mob){
        World world = mob.getEntityWorld();
        if (world.isRemote || !this.isRunning()) return;

        Vec3d pos = mob.getPositionVec();

        if (Math.random() < chance){
            float radius = (float)(Math.random() * maxRadius);
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), radius, Explosion.Mode.BREAK);
        }
    }
}
