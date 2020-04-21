package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class MobCapabilityStorage implements Capability.IStorage<IMobCapability> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IMobCapability> capability, IMobCapability instance, Direction side) {
        return instance.writeNBT();
    }

    @Override
    public void readNBT(Capability<IMobCapability> capability, IMobCapability instance, Direction side, INBT nbt) {
        instance.readNBT((CompoundNBT) nbt);
    }
}
