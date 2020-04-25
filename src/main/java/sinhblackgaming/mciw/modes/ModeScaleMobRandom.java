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
                    cap.setScaleFactor(randomScaleFactor());
                    cap.setActivate(true);
                    // send to clients
                    MoreModeSyncHandler.sendScaleMobRandomToClients(mob.getEntityId(), cap);
                }

//                // update mob axis aligned bounding box
                Vec3d pos = mob.getPositionVec();
                double f = mob.getWidth()/2*3;
                double h = mob.getHeight()*3;

                AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - f, pos.getY(), pos.getZ() - f,
                        pos.getX() + f, pos.getY() + h, pos.getZ() + f);
                mob.setBoundingBox(bb);
            }
            if (!this.isRunning() && cap.isActivate()) {
                cap.setScaleFactor(1.0D);
                cap.setActivate(false);

                // sent to clients
                MoreModeSyncHandler.sendScaleMobRandomToClients(mob.getEntityId(), cap);
            }
        });
    }

    public static double randomScaleFactor(){
        return Math.random() * 5 + 0.2;
    }
}
