package sinhblackgaming.mciw.blocks;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeDimension;
import sinhblackgaming.mciw.tileentity.NetherBedTileEntity;

public class NetherBed extends BedBlock {
    private final DyeColor childColor;

    public NetherBed(DyeColor colorIn, Properties properties) {
        super(colorIn, properties);
        childColor = colorIn;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new NetherBedTileEntity(this.childColor);
    }

    @Override
    public ActionResultType func_225533_a_(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        if (p_225533_2_.isRemote) {
            return ActionResultType.CONSUME;
        } else {
            if (p_225533_1_.get(PART) != BedPart.HEAD) {
                p_225533_3_ = p_225533_3_.offset(p_225533_1_.get(HORIZONTAL_FACING));
                p_225533_1_ = p_225533_2_.getBlockState(p_225533_3_);
                if (p_225533_1_.getBlock() != this) {
                    return ActionResultType.CONSUME;
                }
            }

            p_225533_4_.startSleeping(p_225533_3_);
//            p_225533_4_.sleepTimer = 0;
            if (p_225533_4_.world instanceof ServerWorld) {
                ((ServerWorld)p_225533_4_.world).updateAllPlayersSleepingFlag();
            }

//            net.minecraftforge.common.extensions.IForgeDimension.SleepResult sleepResult = IForgeDimension.SleepResult.ALLOW;
//            if (!p_225533_1_.get(OCCUPIED)) {
//                p_225533_4_.trySleep(p_225533_3_).ifLeft((p_220173_1_) -> {
//                    if (p_220173_1_ != null) {
//                        p_225533_4_.sendStatusMessage(p_220173_1_.getMessage(), true);
//                    }
//
//                });
//            }
            return ActionResultType.SUCCESS;

        }
    }
}
