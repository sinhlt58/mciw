package sinhblackgaming.mciw.init;

import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.tileentity.NetherBedTileEntity;

public class ModTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, MCIWMod.MODID);

    public static final RegistryObject<TileEntityType<NetherBedTileEntity>> NETHER_BED = TILE_ENTITY_TYPES.register("nether_bed",
            () -> TileEntityType.Builder.create(NetherBedTileEntity::new, ModBlocks.NETHER_BED.get()).build(null));
}

