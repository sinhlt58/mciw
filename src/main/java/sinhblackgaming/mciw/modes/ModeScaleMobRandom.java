package sinhblackgaming.mciw.modes;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import sinhblackgaming.mciw.capabilities.ScaleCapabilityProvider;

public class ModeScaleMobRandom extends Mode {
    public ModeScaleMobRandom(String name) {
        super(name);
    }

    public void onLivingUpdate(CreatureEntity mob){
        mob.getCapability(ScaleCapabilityProvider.SCALE_CAPABILITY).ifPresent(cap -> {
            if (this.isRunning()){
                if (!cap.isActivate()){
                    cap.setScaleFactor(randomScaleFactor());
                    cap.setActivate(true);
                    System.out.println("inside !cap.isActivate()");
                }
                // send to clients

                // update mob axis aligned bounding box
                Vec3d pos = mob.getPositionVec();
                double f = mob.getWidth()/2*10;
                double h = mob.getHeight()*10;

                AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - f, pos.getY(), pos.getZ() - f,
                        pos.getX() + f, pos.getY() + h, pos.getZ() + f);
                mob.setBoundingBox(bb);
                System.out.println("set bounding box");
            }
            if (!this.isRunning() && cap.isActivate()) {
                cap.setScaleFactor(1.0D);
                cap.setActivate(false);

                // sent to clients
            }
        });
    }

    public static double randomScaleFactor(){
        return Math.random() * 10 + 0.5;
    }
}
