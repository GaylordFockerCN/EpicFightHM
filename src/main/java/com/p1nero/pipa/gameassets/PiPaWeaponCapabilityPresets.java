package com.p1nero.pipa.gameassets;

import com.p1nero.pipa.EpicPiPaMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = EpicPiPaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PiPaWeaponCapabilityPresets {

    public static final Function<Item, CapabilityItem.Builder> PI_PA = (item) ->
            (CapabilityItem.Builder) WeaponCapability.builder().category(PiPaWeaponCategories.PI_PA)
            .styleProvider((playerPatch) -> CapabilityItem.Styles.ONE_HAND).collider(PiPaColliders.PI_PA)
            .hitSound(EpicFightSounds.BLUNT_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLUNT.get())
            .canBePlacedOffhand(false)
            .newStyleCombo(
                    CapabilityItem.Styles.ONE_HAND,
                    PiPaAnimations.AUTO1,
                    PiPaAnimations.AUTO2,
                    PiPaAnimations.AUTO3,
                    Animations.GREATSWORD_DASH,
                    Animations.LONGSWORD_AIR_SLASH)
            .innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> PiPaSkills.SONIC_BOOM)
            .comboCancel((style) -> false);

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(EpicPiPaMod.MOD_ID, "pi_pa"), PI_PA);
    }

}
