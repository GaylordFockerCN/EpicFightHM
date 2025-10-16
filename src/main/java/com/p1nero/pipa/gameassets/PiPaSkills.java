package com.p1nero.pipa.gameassets;

import com.p1nero.pipa.EpicPiPaMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = EpicPiPaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PiPaSkills {
    public static Skill SONIC_BOOM;
    @SubscribeEvent
    public static void BuildSkills(SkillBuildEvent event) {
        SkillBuildEvent.ModRegistryWorker registryWorker = event.createRegistryWorker(EpicPiPaMod.MOD_ID);
        WeaponInnateSkill sonicBoom = registryWorker.build("sonic_boom", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(PiPaAnimations.SONIC_BOOM));
        sonicBoom.newProperty()
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(0.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(0.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(0.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE);
        SONIC_BOOM = sonicBoom;
    }
}
