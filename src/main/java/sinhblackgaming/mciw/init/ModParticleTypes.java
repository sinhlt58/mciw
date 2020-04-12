package sinhblackgaming.mciw.init;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sinhblackgaming.mciw.MCIWMod;

public class ModParticleTypes {
//    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = new DeferredRegister<>(
//            ForgeRegistries.PARTICLE_TYPES,
//            MCIWMod.MODID
//    );

    public static void init() {
//        PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static BasicParticleType RAIN_LAVA = null;
}
