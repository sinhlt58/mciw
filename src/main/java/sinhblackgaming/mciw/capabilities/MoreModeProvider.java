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
    public static  Capability<IMoreMode> MORE_MODE_CAPABILITY = null;

    /**
     IMPORTANT HERE: We use only ONE instance of IMoreMode cap
     for all the worlds object (over world, nether world, end world)
     so we don't create a new cap instance for each world
    * */
    private static LazyOptional<IMoreMode> instance = null;

    @CapabilityInject(IMoreMode.class)
    private static void onRegisteredCap(Capability<IMoreMode> cap){
        MORE_MODE_CAPABILITY = cap;
        instance = LazyOptional.of(MORE_MODE_CAPABILITY::getDefaultInstance);
    }

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
