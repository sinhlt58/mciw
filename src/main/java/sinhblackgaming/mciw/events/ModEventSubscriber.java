package sinhblackgaming.mciw.events;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.init.ModBlocks;
import sinhblackgaming.mciw.init.ModParticleTypes;
import sinhblackgaming.mciw.init.ModItemGroups;

@Mod.EventBusSubscriber(modid = MCIWMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {
    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event){
        final IForgeRegistry<Item> registry = event.getRegistry();

        ModBlocks.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .forEach(block -> {
                    final Item.Properties properties = new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP);
                    final BlockItem blockItem = new BlockItem(block, properties);
                    blockItem.setRegistryName(block.getRegistryName());
                    registry.register(blockItem);
                });
    }

    @SubscribeEvent
    public static void onParticleTypeRegistration(RegistryEvent.Register<ParticleType<?>> event){
        ModParticleTypes.RAIN_LAVA = new BasicParticleType(true);
        ModParticleTypes.RAIN_LAVA.setRegistryName(MCIWMod.MODID + ":" + "rain_lava");
        event.getRegistry().register(ModParticleTypes.RAIN_LAVA);
    }
}
