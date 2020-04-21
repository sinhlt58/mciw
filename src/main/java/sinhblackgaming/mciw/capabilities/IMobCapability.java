package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;

public interface IMobCapability {
    boolean doesAttackAll();
    boolean doesAttackAllDiffKind();
    boolean doesAttackPlayer();

    void setAttackAll(boolean v);
    void setAttackAllDiffKind(boolean v);
    void setAttackPlayer(boolean v);

    CompoundNBT writeNBT();
    void readNBT(CompoundNBT nbt);
}
