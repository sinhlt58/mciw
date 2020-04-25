package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;
import sinhblackgaming.mciw.MCIWMod;

public class ScaleCapability implements IScaleCapability {
    private boolean isActivate = false;
    private double scaleFactor = 1D;

    @Override
    public double getScaleFactor() {
        return scaleFactor;
    }

    @Override
    public void setScaleFactor(double v) {
        this.scaleFactor = v;
    }

    @Override
    public boolean isActivate() {
        return this.isActivate;
    }

    @Override
    public void setActivate(boolean v) {
        this.isActivate = v;
    }

    @Override
    public CompoundNBT writeNBT() {
        CompoundNBT tag = new CompoundNBT();

        tag.putDouble(MCIWMod.MODID + ".scale_cap." + "factor", scaleFactor);

        return tag;
    }

    @Override
    public void readNBT(CompoundNBT nbt) {
        double v = nbt.getDouble(MCIWMod.MODID + ".scale_cap." + "factor");
        if (v == 0){
            v = 1;
        }
        this.setScaleFactor(v);
    }
}
