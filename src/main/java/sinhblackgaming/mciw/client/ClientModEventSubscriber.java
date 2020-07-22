package sinhblackgaming.mciw.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.client.entity.render.ChanGioPigRenderer;
import sinhblackgaming.mciw.client.particles.RainLavaParticle;
import sinhblackgaming.mciw.client.tileentity.renderer.NetherBedTileEntityRenderer;
import sinhblackgaming.mciw.init.ModEntityTypes;
import sinhblackgaming.mciw.init.ModParticleTypes;
import sinhblackgaming.mciw.init.ModTileEntityTypes;

@Mod.EventBusSubscriber(modid = MCIWMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {
    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.NETHER_BED.get(), NetherBedTileEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.CHAN_GIO_PIG.get(), ChanGioPigRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event){
        Minecraft mc = Minecraft.getInstance();
        mc.particles.registerFactory(ModParticleTypes.RAIN_LAVA, RainLavaParticle.Factory::new);
    }
}
