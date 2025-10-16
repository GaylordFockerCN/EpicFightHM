package com.p1nero.pipa.gameassets;

import com.p1nero.pipa.PiPaConfig;
import com.p1nero.pipa.EpicPiPaMod;
import com.p1nero.pipa.util.EntityUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;

@Mod.EventBusSubscriber(modid = EpicPiPaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PiPaAnimations {

    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SONIC_BOOM;

    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(EpicPiPaMod.MOD_ID, PiPaAnimations::build);
    }

    private static void build(AnimationManager.AnimationBuilder builder) {
        AUTO1 = builder.nextAccessor("biped/auto_1", (accessor) ->
                new BasicAttackAnimation(0.15F, 0.15F, 0.5667F, 0.5667F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.98F))
        );
        AUTO2 = builder.nextAccessor("biped/auto_2", (accessor) ->
                new BasicAttackAnimation(0.01F, 0.1F, 0.4333F, 0.4333F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.97F))
        );
        AUTO3 = builder.nextAccessor("biped/auto_3", (accessor) ->
                new BasicAttackAnimation(0.15F, 1.2F, 1.5F, 1.9833F,PiPaColliders.PI_PA_PLUS, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
        );
        SONIC_BOOM = builder.nextAccessor("biped/sonic_boom", (accessor) ->
                new BasicAttackAnimation(0.15F, 0, 0, 2.533F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.0F))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(AnimationEvent.InTimeEvent.create(0.8833F, ((livingEntityPatch, staticAnimation, objects) -> {
                            LivingEntity livingEntity = livingEntityPatch.getOriginal();
                            ServerLevel serverLevel = ((ServerLevel) livingEntity.level());
                            ItemStack itemStack = livingEntity.getMainHandItem();
                            if(itemStack.is(EpicPiPaMod.PIPA.get()) && itemStack.getDamageValue() < itemStack.getMaxDamage()-1){
                                Vec3 attacker = livingEntity.getEyePosition();
                                Vec3 target = livingEntity.getViewVector(1.0F).normalize();
                                for(int i = 1; i < Mth.floor(target.length()) + 7; ++i) {
                                    Vec3 pos = attacker.add(target.scale(i));
                                    serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
                                }
                                itemStack.setDamageValue(itemStack.getDamageValue() + 1);

                                serverLevel.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), EpicPiPaMod.SONIC_BOOM.get(), SoundSource.BLOCKS, 1, 1);
                                for(LivingEntity entity : EntityUtil.getNearByEntities(serverLevel, livingEntity, 20)){
                                    if(EntityUtil.isInFront(entity, livingEntity, 20) && entity.distanceTo(livingEntity) < 10){
                                        entity.hurt(livingEntity.damageSources().sonicBoom(livingEntity), PiPaConfig.SONIC_BOOM_DAMAGE.get().floatValue());
                                    }
                                }

                            }
                        }), AnimationEvent.Side.SERVER))
        );
               ;
    }

}
