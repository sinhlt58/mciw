package sinhblackgaming.mciw.modes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.registries.ForgeRegistries;
import sinhblackgaming.mciw.utils.Utils;

import java.util.ArrayList;

public class ModeMobsAppearNearPlayers extends Mode {
    private int rate = 1*60*20; // ticks
    private int maxNum = 5;
    private int minDistance = 2;
    private int maxDistance = 10;
    private float radiusBound = 20f;
    private int maxNearMob = 100;

    private ArrayList<EntityType<?>> mobs = new ArrayList<>();

    public ModeMobsAppearNearPlayers(String name) {
        super(name);

        // get all registered entities to random later
        ForgeRegistries.ENTITIES.getValues().forEach(entityType -> {
            if (entityType.getClassification() == EntityClassification.MONSTER){
                mobs.add(entityType);
            }
        });
    }

    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        World world = event.player.world;
        if (world.isRemote || !this.isRunning()) return;

        if (rate-- > 0) return;
        rate = 100;

        Vec3d pos = event.player.getPositionVec();

        // calculate bounding box around the player
        AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - radiusBound, pos.getY() - 5, pos.getZ() - radiusBound,
                pos.getX() + radiusBound, pos.getY() + 20, pos.getZ() + radiusBound);
        int m = world.getEntitiesWithinAABB(MonsterEntity.class, bb).size();

        // not spawn if there are too many mobs
        if (m > maxNearMob){
            return;
        }

        // choose number of monster to spawn
        int n = (int)(Math.random() * maxNum);

        // random monster type index
        int randomIndex = (int)(Math.random()*(mobs.size() - 1));

        // for each monster choose random distance
        for (int i=0; i<n; i++){
            int d = (int)(Math.random()*(maxDistance - minDistance)) + minDistance;
            // calculate spawn position
            Vec3d spawnPos = pos.add(Utils.randomDir(0.5f)*d, 1,Utils.randomDir(0.5f)*d);
            // spawn the entity
            Entity entity = mobs.get(randomIndex).create(world);
            if (entity != null){
                entity.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                world.addEntity(entity);
            }
        }

    }
}
