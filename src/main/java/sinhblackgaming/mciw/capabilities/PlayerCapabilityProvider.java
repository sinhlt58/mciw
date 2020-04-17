package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerCapabilityProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IPlayerCapability.class)
    public static Capability<IPlayerCapability> PLAYER_CAPABILITY = null;

    private LazyOptional<IPlayerCapability> instance = LazyOptional.of(PLAYER_CAPABILITY::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return PLAYER_CAPABILITY.orEmpty(cap, instance);
    }


    @Override
    public INBT serializeNBT() {
        return PLAYER_CAPABILITY.getStorage()
                .writeNBT(PLAYER_CAPABILITY,
                        instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty")),
                        null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        PLAYER_CAPABILITY.getStorage()
                .readNBT(PLAYER_CAPABILITY,
                        instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty")),
                        null, nbt);
    }
}
