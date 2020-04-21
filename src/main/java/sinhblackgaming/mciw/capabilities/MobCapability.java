package sinhblackgaming.mciw.capabilities;

import net.minecraft.nbt.CompoundNBT;

public class MobCapability implements IMobCapability {
    public boolean attackAll = false;
    public boolean attackAllDiffKind = false;
    public boolean attackPlayer = false;

    @Override
    public boolean doesAttackAll() {
        return attackAll;
    }

    @Override
    public boolean doesAttackAllDiffKind() {
        return attackAllDiffKind;
    }

    @Override
    public boolean doesAttackPlayer() {
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
        return null;
    }

    @Override
    public void readNBT(CompoundNBT nbt) {

    }
}
