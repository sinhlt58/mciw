package sinhblackgaming.mciw.entities.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public class NearestAttackableDiffKindTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public NearestAttackableDiffKindTargetGoal(MobEntity p_i50313_1_, Class<T> p_i50313_2_, boolean p_i50313_3_) {
        super(p_i50313_1_, p_i50313_2_, p_i50313_3_);
    }

    // UPDATING FORGE ATTENTION: We change a little from the parent class function
    protected void findNearestTarget() {
        if (this.targetClass != PlayerEntity.class && this.targetClass != ServerPlayerEntity.class) {
            // sinh.luutruong changed
            LivingEntity target = this.goalOwner.world.<T>func_225318_b(this.targetClass, this.targetEntitySelector, this.goalOwner, this.goalOwner.func_226277_ct_(), this.goalOwner.func_226280_cw_(), this.goalOwner.func_226281_cx_(), this.getTargetableArea(this.getTargetDistance()));
            // only attack different kind of mob
            if (target != null && this.goalOwner.getClass() != target.getClass()){
                this.nearestTarget = target;
            }
        } else {
            this.nearestTarget = this.goalOwner.world.getClosestPlayer(this.targetEntitySelector, this.goalOwner, this.goalOwner.func_226277_ct_(), this.goalOwner.func_226280_cw_(), this.goalOwner.func_226281_cx_());
        }

    }
}
