package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;

public class MobCapability implements IMobCapability {
    GroupGoalAttack groupGoalAttackPlayer = new GroupGoalAttack();
    GroupGoalAttackAll groupGoalAttackAll = new GroupGoalAttackAll();
    GroupGoalAttackAllDiffKind groupGoalAttackAllDiffKind = new GroupGoalAttackAllDiffKind();

    @Override
    public GroupGoalAttack getGroupGoalAttackPlayer() {
        return this.groupGoalAttackPlayer;
    }

    @Override
    public GroupGoalAttackAll getGroupGoalAttackAll() {
        return this.groupGoalAttackAll;
    }

    @Override
    public GroupGoalAttackAllDiffKind getGroupGoalAttackAllDiffKind() {
        return this.groupGoalAttackAllDiffKind;
    }

    @Override
    public CompoundNBT writeNBT() {
        return new CompoundNBT();
    }

    @Override
    public void readNBT(CompoundNBT nbt) {

    }
}
