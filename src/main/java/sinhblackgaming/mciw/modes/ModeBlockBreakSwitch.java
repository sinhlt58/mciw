package sinhblackgaming.mciw.modes;

import net.minecraft.block.AbstractCoralPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CoralBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class ModeBlockBreakSwitch extends Mode {

    private ArrayList<BlockState> blockStates = new ArrayList<>();
    public int encodeSecret = 0;

    public ModeBlockBreakSwitch(String name) {
        super(name);

        // get all registered blocks to random later
        ForgeRegistries.BLOCKS.getValues().stream().forEach(block -> {
            BlockState bs = block.getDefaultState();
            if (bs.getMaterial() == Material.ROCK &&
                    !(block instanceof AbstractCoralPlantBlock) &&
                    !(block instanceof CoralBlock)){
                blockStates.add(block.getDefaultState());
            }
        });
    }

    public void onBlockBreak(BlockEvent.BreakEvent event){
        World world = event.getPlayer().world;

        if (world.isRemote || !this.isRunning()) return;
    }

    @Override
    public void start() {
        super.start();

        // we generate a new encode secret for each start
        this.encodeSecret = getRandomEncodeSecret(blockStates.size());
    }

    public int getRandomEncodeSecret(int n){
        return 1 + (int)(Math.random()*n);
    }
}
