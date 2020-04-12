package sinhblackgaming.mciw.events;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.init.ModParticleTypes;
import sinhblackgaming.mciw.init.ModItemGroups;

@Mod.EventBusSubscriber(modid = MCIWMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {
    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(
                setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "example_item")
        );
    }

    public static void onRegisterBlocks(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(
                setup(new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F)),
                        "example_ore")
        );
    }

    @SubscribeEvent
    public static void onParticleTypeRegistration(RegistryEvent.Register<ParticleType<?>> event){
        LOGGER.info("inside onParticleTypeRegistration");
        ModParticleTypes.RAIN_LAVA = new BasicParticleType(true);
        ModParticleTypes.RAIN_LAVA.setRegistryName(MCIWMod.MODID + ":" + "rain_lava");
        event.getRegistry().register(ModParticleTypes.RAIN_LAVA);

//        PARTICLE_TYPES.register(
//                "rain_lava", () -> new BasicParticleType(true)
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
        return setup(entry, new ResourceLocation(MCIWMod.MODID, name));
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
        entry.setRegistryName(registryName);
        return entry;
    }
}
