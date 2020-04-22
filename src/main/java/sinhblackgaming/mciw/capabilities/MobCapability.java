package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;
import sinhblackgaming.mciw.MCIWMod;

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
        CompoundNBT tag = new CompoundNBT();

        tag.putBoolean(MCIWMod.MODID + ".mobcap.attack_player", groupGoalAttackPlayer.isActive());
        tag.putBoolean(MCIWMod.MODID + ".mobcap.attack_all", groupGoalAttackAll.isActive());
        tag.putBoolean(MCIWMod.MODID + ".mobcap.attack_all_diff_kind", groupGoalAttackAllDiffKind.isActive());

        return tag;
    }

    @Override
    public void readNBT(CompoundNBT nbt) {
        groupGoalAttackPlayer.setActive(nbt.getBoolean(MCIWMod.MODID + ".mobcap.attack_player"));
        groupGoalAttackAll.setActive(nbt.getBoolean(MCIWMod.MODID + ".mobcap.attack_all"));
        groupGoalAttackAllDiffKind.setActive(nbt.getBoolean(MCIWMod.MODID + ".mobcap.attack_all_diff_kind"));
    }
}
