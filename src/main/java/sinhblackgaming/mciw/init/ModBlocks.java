package sinhblackgaming.mciw.init;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.blocks.NetherBed;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MCIWMod.MODID);

    public static final RegistryObject<Block> EXAMPLE_ORE = BLOCKS.register("example_block", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F)));

    public static final RegistryObject<Block> NETHER_BED = BLOCKS.register("nether_bed",
            () -> new NetherBed(DyeColor.RED, Block.Properties.create(Material.WOOL).sound(SoundType.WOOD).hardnessAndResistance(0.2F)));
}
