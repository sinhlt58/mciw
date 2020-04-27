package sinhblackgaming.mciw.modes;

import net.minecraft.block.AbstractCoralPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CoralBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class ModeBlockBreakRandomDrop extends Mode {

    private ArrayList<IItemProvider> blocks = new ArrayList<>();
    private ArrayList<IItemProvider> items = new ArrayList<>();

    private static int maxNum = 20;
    private static double itemChance = 0.7;

    public ModeBlockBreakRandomDrop(String name) {
        super(name);

        // get all registered blocks to random later
        ForgeRegistries.BLOCKS.getValues().forEach(block -> {
            if (!(block instanceof AbstractCoralPlantBlock) && !(block instanceof CoralBlock)){
                blocks.add(block);
            }
        });

        // get all registered items to random later
        items.addAll(ForgeRegistries.ITEMS.getValues());
    }

    public void onBlockBreak(BlockEvent.BreakEvent event){
        World world = event.getPlayer().world;

        if (world.isRemote || !this.isRunning()) return;

        // item or block
        ArrayList<IItemProvider> arr = blocks;
        if (Math.random() < itemChance){
            arr = items;
        }

        int n = (int)(Math.random() * maxNum);
        int randIndex = (int)(Math.random() * (arr.size() - 1));
        IItemProvider e = arr.get(randIndex);
        BlockPos pos = event.getPos();

        ItemStack stack = new ItemStack(e, n);
        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);

        // cancel the event first, so it does not drop
        // the default block item
        event.setCanceled(true);
        // we destroy instead
        world.destroyBlock(pos, false);

        world.addEntity(itemEntity);
    }
}
