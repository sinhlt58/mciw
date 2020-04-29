package sinhblackgaming.mciw.modes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class ModeBlockBreakMultiple extends Mode {
    public ModeBlockBreakMultiple(String name) {
        super(name);
    }

    public void onBlockBreak(BlockEvent.BreakEvent event){
        World world = event.getPlayer().world;

        if (world.isRemote || !this.isRunning()) return;

        // get position
        BlockPos pos = event.getPos();

        // get random number of multiplied mobs
        int n = getRandomDrop();
        BlockState state = event.getState();
        // get tile entity
        TileEntity te = world.getTileEntity(pos);

        // spawn drops
        for (int i=0; i<n; i++){
            Block.spawnDrops(state, world, pos, te);
        }

    }

    public static int getRandomDrop(){
        return (int) (Math.random() * 64);
    }
}
