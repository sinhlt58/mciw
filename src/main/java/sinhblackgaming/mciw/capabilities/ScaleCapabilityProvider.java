package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ScaleCapabilityProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IScaleCapability.class)
    public static Capability<IScaleCapability> SCALE_CAPABILITY = null;

    private LazyOptional<IScaleCapability> instance = LazyOptional.of(SCALE_CAPABILITY::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return SCALE_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public INBT serializeNBT() {
        return SCALE_CAPABILITY.getStorage().writeNBT(SCALE_CAPABILITY, instance.orElseThrow(RuntimeException::new), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        SCALE_CAPABILITY.getStorage().readNBT(SCALE_CAPABILITY, instance.orElseThrow(RuntimeException::new), null, nbt);
    }
}
