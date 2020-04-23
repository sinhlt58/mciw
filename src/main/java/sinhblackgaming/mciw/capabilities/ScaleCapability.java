package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;

public class ScaleCapability implements IScaleCapability {
    private float scaleFactor = 1.0f;

    @Override
    public float getScaleFactor() {
        return scaleFactor;
    }

    @Override
    public void setScaleFactor(float v) {
        this.scaleFactor = v;
    }

    @Override
    public CompoundNBT writeNBT() {
        return null;
    }

    @Override
    public void readNBT(CompoundNBT nbt) {

    }
}
