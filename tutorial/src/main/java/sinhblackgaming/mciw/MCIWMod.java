package sinhblackgaming.mciw;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.capabilities.IMoreMode;
import sinhblackgaming.mciw.capabilities.MoreMode;
import sinhblackgaming.mciw.capabilities.MoreModeStorage;
import sinhblackgaming.mciw.network.MoreModeSyncHandler;

@Mod(MCIWMod.MODID)
public class MCIWMod {

    public static final String MODID = "mciw";
    public static final Logger LOGGER = LogManager.getLogger();

    public MCIWMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IMoreMode.class, new MoreModeStorage(), MoreMode::new);
        MoreModeSyncHandler.init();
    }
}
