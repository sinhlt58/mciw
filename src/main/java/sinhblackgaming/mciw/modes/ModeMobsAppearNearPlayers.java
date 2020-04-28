package sinhblackgaming.mciw.modes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class ModeMobsAppearNearPlayers extends Mode {
    private int rate = 100; // ticks
    private int maxNum = 5;
    private int minDistance = 2;
    private int maxDistance = 10;

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

        // choose number of monster to spawn
        int n = (int)(Math.random() * maxNum);

        // random monster type index
        int randomIndex = (int)(Math.random()*(mobs.size() - 1));

        Vec3d pos = event.player.getPositionVec();
        // for each monster choose random distance
        for (int i=0; i<n; i++){
            int d = (int)(Math.random()*(maxDistance - minDistance)) + minDistance;
            // calculate spawn position
            Vec3d spawnPos = pos.add(randomDir()*d, 1,randomDir()*d);
            // spawn the entity
            Entity entity = mobs.get(randomIndex).create(world);
            if (entity != null){
                entity.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                world.addEntity(entity);
            }
        }

    }

    public int randomDir(){
        return Math.random() < 0.5 ? 1 : -1;
    }
}
