package sinhblackgaming.mciw.modes;

import net.minecraft.block.AbstractCoralPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CoralBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;
import sinhblackgaming.mciw.MCIWMod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModeBlockBreakSwitch extends Mode {

    private ArrayList<BlockState> blockStates = new ArrayList<>();
    private HashMap<String, Integer> name2Index = new HashMap<>();
    public int encodeSecret = 0;

    public ModeBlockBreakSwitch(String name) {
        super(name);

        // get all registered blocks to random later
        ForgeRegistries.BLOCKS.getValues().stream().forEach(block -> {
            if (!(block instanceof AbstractCoralPlantBlock) && !(block instanceof CoralBlock)){
                String regName = block.getRegistryName().toString();
                blockStates.add(block.getDefaultState());
                name2Index.put(regName, blockStates.size() - 1);
            }
        });
    }

    public void onBlockBreak(BlockEvent.BreakEvent event){
        World world = event.getPlayer().world;

        if (world.isRemote || !this.isRunning()) return;

        BlockState state = event.getState();
        String blockName = state.getBlock().getRegistryName().toString();
        BlockPos pos = event.getPos();

        // only update if it is contained in the dict
        if (name2Index.containsKey(blockName)){
            Integer encodedIndex = name2Index.get(blockName);
            BlockState switchedState = blockStates.get(encodedIndex);
            // cancel the event first, so it does not drop
            // the default block item
            event.setCanceled(true);
            // we destroy instead
            world.destroyBlock(pos, false);

            // spawn the switched block
            Block.spawnDrops(switchedState, world, pos);
        }
    }

    @Override
    public void start() {
        super.start();

        // we generate a new encode secret for each start
        this.encodeSecret = getRandomEncodeSecret(blockStates.size());

        this.updateName2Index();
    }

    public void updateName2Index(){
        // update name2Index
        for(Map.Entry<String, Integer> e : name2Index.entrySet()){
            Integer oldIndex = e.getValue();
            // simple encode
            Integer newIndex = (oldIndex + encodeSecret) % blockStates.size();
            // update the new value for the key
            e.setValue(newIndex);
        }
    }

    public int getRandomEncodeSecret(int n){
        return 1 + (int)(Math.random()*n);
    }

    @Override
    public void writeNBT(CompoundNBT tag) {
        super.writeNBT(tag);

        tag.putInt(MCIWMod.MODID + ".blockbreakswitch.secret", this.encodeSecret);
    }

    @Override
    public void readNBT(CompoundNBT nbt) {
        super.readNBT(nbt);

        this.encodeSecret = nbt.getInt(MCIWMod.MODID + ".blockbreakswitch.secret");

        this.updateName2Index();
    }
}
