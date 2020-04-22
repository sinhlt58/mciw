package sinhblackgaming.mciw.capabilities;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;

public class GroupGoalAttack {
    public boolean isActive = false;

    public MobEntity goalOwner = null;
    public MeleeAttackGoal meleeAttackGoal = null;
    public LeapAtTargetGoal leapAtTargetGoal = null;
    public NearestAttackableTargetGoal<PlayerEntity> nearestPlayers = null;

    public boolean isActive(){return this.isActive;}
    public void  setActive(boolean v){this.isActive = v;}

    public void addGoals(CreatureEntity mob){
        if (this.meleeAttackGoal == null) {
            meleeAttackGoal = new MeleeAttackGoal(mob, 1.2D, true);
        }
        if (this.leapAtTargetGoal == null){
            leapAtTargetGoal = new LeapAtTargetGoal(mob, 0.6f);
        }
        if (this.nearestPlayers == null){
            nearestPlayers = new NearestAttackableTargetGoal<>(mob, PlayerEntity.class, true);
            this.goalOwner = mob;
        }
        mob.goalSelector.addGoal(2, this.meleeAttackGoal);
        mob.goalSelector.addGoal(3, this.leapAtTargetGoal);
        mob.targetSelector.addGoal(1, this.nearestPlayers);
    }

    public void removeGoals(CreatureEntity mob){
        if (this.goalOwner == mob){
            mob.goalSelector.removeGoal(this.meleeAttackGoal);
            mob.goalSelector.removeGoal(this.leapAtTargetGoal);
            mob.targetSelector.removeGoal(this.nearestPlayers);
            this.goalOwner = null;
        }
    }
}
