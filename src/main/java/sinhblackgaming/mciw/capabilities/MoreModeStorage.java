package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public class MoreModeStorage implements Capability.IStorage<IMoreMode> {
    public static final Logger LOGGER = LogManager.getLogger();

    @Nullable
    @Override
    public INBT writeNBT(Capability<IMoreMode> capability, IMoreMode instance, Direction side) {
        return instance.writeNBT();
    }

    @Override
    public void readNBT(Capability<IMoreMode> capability, IMoreMode instance, Direction side, INBT nbt) {
        instance.readNBT((CompoundNBT) nbt);
    }
}
