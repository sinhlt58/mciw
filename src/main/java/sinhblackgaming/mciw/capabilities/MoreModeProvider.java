package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public class MoreModeProvider implements ICapabilitySerializable<INBT> {
    public static final Logger LOGGER = LogManager.getLogger();

    @CapabilityInject(IMoreMode.class)
    public static  Capability<IMoreMode> MORE_MODE_CAPABILITY = null;

    /**
     IMPORTANT HERE: We use only ONE instance of IMoreMode for
     world objects with the same seed. We want the mode commands to effect
     all across worlds (over-world, nether, end world, ...).
    * */
    public static HashMap<Long, LazyOptional<IMoreMode>> capMap = new HashMap<>();
    public long worldSeed;

    public MoreModeProvider(long worldSeed){
        super();
        this.worldSeed = worldSeed;
    }

    // get the corresponding instance for the seed
    public LazyOptional<IMoreMode> getCapabilityInstanceBySeed(long seed){
        if (!capMap.containsKey(seed)){
            capMap.put(seed, LazyOptional.of(MORE_MODE_CAPABILITY::getDefaultInstance));
        }
        return capMap.get(seed);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        LazyOptional<IMoreMode> instance = getCapabilityInstanceBySeed(worldSeed);
        return MORE_MODE_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public INBT serializeNBT() {
        LazyOptional<IMoreMode> instance = getCapabilityInstanceBySeed(worldSeed);
        return MORE_MODE_CAPABILITY.getStorage()
                .writeNBT(MORE_MODE_CAPABILITY,
                        instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty")),
                        null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        LazyOptional<IMoreMode> instance = getCapabilityInstanceBySeed(worldSeed);
        MORE_MODE_CAPABILITY.getStorage()
                .readNBT(MORE_MODE_CAPABILITY,
                        instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty")),
                        null, nbt);
    }
}
