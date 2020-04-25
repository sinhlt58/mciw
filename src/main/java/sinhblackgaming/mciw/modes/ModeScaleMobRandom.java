package sinhblackgaming.mciw.modes;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import sinhblackgaming.mciw.capabilities.ScaleCapabilityProvider;
import sinhblackgaming.mciw.network.MoreModeSyncHandler;

public class ModeScaleMobRandom extends Mode {
    public ModeScaleMobRandom(String name) {
        super(name);
    }

    public void onLivingUpdate(MobEntity mob){
        mob.getCapability(ScaleCapabilityProvider.SCALE_CAPABILITY).ifPresent(cap -> {
            if (this.isRunning()){
                if (!cap.isActivate()){
                    double oldFactor = cap.getScaleFactor();
                    // only set scale factor when oldFactor is 1
                    // scale factor is different than 1
                    // which means we already random for this mob
                    if (oldFactor == 1D){
                        cap.setScaleFactor(randomScaleFactor());
                    }
                    cap.setActivate(true);

                    // send to clients
                    MoreModeSyncHandler.sendScaleMobRandomToClients(mob.getEntityId(), cap);
                }
            }
            if (!this.isRunning() && cap.isActivate()) {
                cap.setScaleFactor(1D);
                cap.setActivate(false);

                // sent to clients
                MoreModeSyncHandler.sendScaleMobRandomToClients(mob.getEntityId(), cap);
            }
        });
    }

    public static double randomScaleFactor(){
        return Math.random() * 4 + 0.2;
    }
}
