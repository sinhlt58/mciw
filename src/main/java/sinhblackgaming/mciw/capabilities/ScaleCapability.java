package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;

public class ScaleCapability implements IScaleCapability {
    private boolean isActivate = false;
    private double scaleFactor = 1.0D;

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
        return new CompoundNBT();
    }

    @Override
    public void readNBT(CompoundNBT nbt) {

    }
}
