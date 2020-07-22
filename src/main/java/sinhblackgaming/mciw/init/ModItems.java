package sinhblackgaming.mciw.init;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sinhblackgaming.mciw.MCIWMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MCIWMod.MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
            () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

    public static final RegistryObject<Item> CHAN_GIO_SONG = ITEMS.register("chan_gio_song",
            () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)
                    .food(new Food.Builder().hunger(1).saturation(0.1f)
                            .effect(new EffectInstance(Effects.HUNGER, 1000, 0), 0.8F).setAlwaysEdible().build())));

    public static final RegistryObject<Item> CHAN_GIO_UOP = ITEMS.register("chan_gio_uop",
            () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP).food(
                    new Food.Builder().hunger(2).saturation(0.1f).effect(new EffectInstance(Effects.HUNGER, 1000, 0), 0.8F).meat().build())));

    public static final RegistryObject<Item> CHAN_GIO_CHIN = ITEMS.register("chan_gio_chin",
            () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP).food(new Food.Builder().hunger(10).saturation(2.0f)
                    .effect(new EffectInstance(Effects.REGENERATION, 1000, 2), 1.0F)
                    .effect(new EffectInstance(Effects.ABSORPTION, 1000, 2), 1.0F)
                    .effect(new EffectInstance(Effects.SPEED, 1000, 2), 1.0F)
                    .effect(new EffectInstance(Effects.HASTE, 1000, 2), 1.0F)
                    .effect(new EffectInstance(Effects.STRENGTH, 1000, 2), 1.0F)
                    .effect(new EffectInstance(Effects.JUMP_BOOST, 1000, 2), 1.0F)
                    .effect(new EffectInstance(Effects.SLOW_FALLING, 1000, 2), 1.0F)
                    .effect(new EffectInstance(Effects.LEVITATION, 100, 2), 1.0F).setAlwaysEdible().build())));

    public static final RegistryObject<Item> CU_XA = ITEMS.register("cu_xa",
            () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP).food(
                    new Food.Builder().hunger(2).saturation(0.3f).setAlwaysEdible().build())));
}
