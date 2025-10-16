package com.p1nero.pipa;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import yesman.epicfight.world.item.EpicFightCreativeTabs;
import yesman.epicfight.world.item.WeaponItem;

@Mod(EpicPiPaMod.MOD_ID)
public class EpicPiPaMod {
    public static final String MOD_ID = "ef_pipa";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<Item> PIPA = ITEMS.register("pipa", ()->new WeaponItem(Tiers.WOOD, 3, -3F, new Item.Properties().rarity(Rarity.RARE).stacksTo(1).defaultDurability(256)) {
        @Override
        public boolean isCorrectToolForDrops(BlockState blockIn) {
            return super.isCorrectToolForDrops(blockIn);
        }
    });
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EpicPiPaMod.MOD_ID);
    public static final RegistryObject<SoundEvent> SONIC_BOOM = SOUNDS.register("sonic_boom", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(EpicPiPaMod.MOD_ID, "sonic_boom")));
    public EpicPiPaMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        ITEMS.register(modEventBus);
        SOUNDS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        context.registerConfig(ModConfig.Type.COMMON, PiPaConfig.SPEC);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event){
        if (event.getTab() == EpicFightCreativeTabs.ITEMS.get()){
            event.accept(PIPA.get());
        }
    }

}
