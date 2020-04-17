package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

public interface IPlayerCapability {
    BlockPos getPreviousLookBlockPos();
    void setPreviousLookBlockPos(BlockPos pos);

    CompoundNBT writeNBT();
    void readNBT(CompoundNBT nbt);
}
