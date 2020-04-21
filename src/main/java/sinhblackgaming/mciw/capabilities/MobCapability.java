package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;
import sinhblackgaming.mciw.MCIWMod;

public class MobCapability implements IMobCapability {
    public boolean attackAll = false;
    public boolean attackAllDiffKind = false;
    public boolean attackPlayer = false;

    @Override
    public boolean isAttackAll() {
        return attackAll;
    }

    @Override
    public boolean isAttackAllDiffKind() {
        return attackAllDiffKind;
    }

    @Override
    public boolean isAttackPlayers() {
        return attackPlayer;
    }

    @Override
    public void setAttackAll(boolean v) {
        this.attackAll = v;
    }

    @Override
    public void setAttackAllDiffKind(boolean v) {
        this.attackAllDiffKind = v;
    }

    @Override
    public void setAttackPlayer(boolean v) {
        this.attackPlayer = v;
    }

    @Override
    public CompoundNBT writeNBT() {
        CompoundNBT tag = new CompoundNBT();

        tag.putBoolean(MCIWMod.MODID + ".mobcap.attack_all", attackAll);
        tag.putBoolean(MCIWMod.MODID + ".mobcap.attack_all_diff_kind", attackAllDiffKind);
        tag.putBoolean(MCIWMod.MODID + ".mobcap.attack_player", attackPlayer);

        return tag;
    }

    @Override
    public void readNBT(CompoundNBT nbt) {
        attackAll = nbt.getBoolean(MCIWMod.MODID + ".mobcap.attack_all");
        attackAllDiffKind = nbt.getBoolean(MCIWMod.MODID + ".mobcap.attack_all_diff_kind");
        attackPlayer = nbt.getBoolean(MCIWMod.MODID + ".mobcap.attack_player");
    }
}
