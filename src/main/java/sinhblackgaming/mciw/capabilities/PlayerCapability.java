package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.MCIWMod;

public class PlayerCapability implements IPlayerCapability {
    public BlockPos previousLookBlockPos = new BlockPos(0, 1000, 0);
    private String dataKey = MCIWMod.MODID + ".playercap.prelookblockpos";


    @Override
    public BlockPos getPreviousLookBlockPos() {
        return previousLookBlockPos;
    }

    @Override
    public void setPreviousLookBlockPos(BlockPos pos) {
        this.previousLookBlockPos = pos;
    }

    @Override
    public CompoundNBT writeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt(dataKey + ".x", previousLookBlockPos.getX());
        tag.putInt(dataKey + ".y", previousLookBlockPos.getY());
        tag.putInt(dataKey + ".z", previousLookBlockPos.getZ());
        return tag;
    }

    @Override
    public void readNBT(CompoundNBT nbt) {
        previousLookBlockPos = new BlockPos(
                nbt.getInt(dataKey + ".x"),
                nbt.getInt(dataKey + ".y"),
                nbt.getInt(dataKey + ".z")
        );
    }
}
