package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;

public interface IScaleCapability {

    double getScaleFactor();
    void setScaleFactor(double v);

    boolean isActivate();
    void setActivate(boolean v);

    CompoundNBT writeNBT();
    void readNBT(CompoundNBT nbt);
}
