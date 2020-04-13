package sinhblackgaming.mciw.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.client.particles.RainLavaParticle;
import sinhblackgaming.mciw.init.ModParticleTypes;

@Mod.EventBusSubscriber(modid = MCIWMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {
    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event){
        Minecraft mc = Minecraft.getInstance();
        mc.particles.registerFactory(ModParticleTypes.RAIN_LAVA, RainLavaParticle.Factory::new);
    }
}
