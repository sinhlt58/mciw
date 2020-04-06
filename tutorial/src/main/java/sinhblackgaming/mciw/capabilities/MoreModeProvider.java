package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MoreModeProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IMoreMode.class)
    public static final Capability<IMoreMode> MORE_MODE_CAPABILITY = null;

    private LazyOptional<IMoreMode> instance = LazyOptional.of(MORE_MODE_CAPABILITY::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return MORE_MODE_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public INBT serializeNBT() {
        return MORE_MODE_CAPABILITY.getStorage()
                .writeNBT(MORE_MODE_CAPABILITY,
                        this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty")),
                        null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        MORE_MODE_CAPABILITY.getStorage()
                .readNBT(MORE_MODE_CAPABILITY,
                        this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty")),
                        null, nbt);
    }
}
