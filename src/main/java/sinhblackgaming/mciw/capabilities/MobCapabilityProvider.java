package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MobCapabilityProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IMobCapability.class)
    public static Capability<IMobCapability> MOB_CAPABILITY = null;

    private LazyOptional<IMobCapability> instance = LazyOptional.of(MOB_CAPABILITY::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return MOB_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public INBT serializeNBT() {
        return MOB_CAPABILITY.getStorage().writeNBT(MOB_CAPABILITY,
                instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty")),
                null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        MOB_CAPABILITY.getStorage().readNBT(MOB_CAPABILITY,
                instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty")),
                null, nbt);
    }
}
