package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;

public interface IScaleCapability {

    float getScaleFactor();
    void setScaleFactor(float v);

    CompoundNBT writeNBT();
    void readNBT(CompoundNBT nbt);
}
