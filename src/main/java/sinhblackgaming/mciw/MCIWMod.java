package sinhblackgaming.mciw;

import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.capabilities.*;
import sinhblackgaming.mciw.init.ModParticleTypes;
import sinhblackgaming.mciw.network.MoreModeSyncHandler;

@Mod(MCIWMod.MODID)
public class MCIWMod {

    public static final String MODID = "mciw";
    public static final Logger LOGGER = LogManager.getLogger();

    public MCIWMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::setup);
        modBus.addListener(this::clientSetUp);

        ModParticleTypes.init();
    }

    private void clientSetUp(final FMLClientSetupEvent event) {
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IMoreMode.class, new MoreModeStorage(), MoreMode::new);
        CapabilityManager.INSTANCE.register(IPlayerCapability.class, new PlayerCapabilityStorage(), PlayerCapability::new);
        CapabilityManager.INSTANCE.register(IMobCapability.class, new MobCapabilityStorage(), MobCapability::new);
        CapabilityManager.INSTANCE.register(IScaleCapability.class, new ScaleCapabilityStorage(), ScaleCapability::new);
        MoreModeSyncHandler.init();
    }
}
