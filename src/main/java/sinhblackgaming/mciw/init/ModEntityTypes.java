package sinhblackgaming.mciw.init;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.entities.ChanGioPigEntity;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES,
            MCIWMod.MODID);

    public static final RegistryObject<EntityType<ChanGioPigEntity>> CHAN_GIO_PIG = ENTITY_TYPES
            .register("chan_gio_pig",
                    () -> EntityType.Builder.<ChanGioPigEntity>create(ChanGioPigEntity::new, EntityClassification.CREATURE)
                            .size(0.9f, 0.9f)
                            .build(new ResourceLocation(MCIWMod.MODID, "chan_gio_pig").toString()));
}
