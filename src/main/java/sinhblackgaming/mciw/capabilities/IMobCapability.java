package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;

public interface IMobCapability {
    GroupGoalAttack getGroupGoalAttackPlayer();
    GroupGoalAttackAll getGroupGoalAttackAll();
    GroupGoalAttackAllDiffKind getGroupGoalAttackAllDiffKind();

    CompoundNBT writeNBT();
    void readNBT(CompoundNBT nbt);
}
