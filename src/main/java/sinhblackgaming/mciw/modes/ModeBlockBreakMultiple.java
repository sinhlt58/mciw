package sinhblackgaming.mciw.modes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
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

        int n = getRandomDrop();

        BlockState state = event.getState();
        BlockPos pos = event.getPos();
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
