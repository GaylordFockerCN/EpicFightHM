package com.p1nero.pipa.data.sound;

import com.p1nero.pipa.EpicPiPaMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.registries.RegistryObject;

public abstract class SoundProvider extends SoundDefinitionsProvider {

    protected SoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, EpicPiPaMod.MOD_ID, helper);
    }

    public void generateNewSoundWithSubtitle(RegistryObject<SoundEvent> event, String baseSoundDirectory, int numberOfSounds) {
        generateNewSound(event, baseSoundDirectory, numberOfSounds, true);
    }

    public void generateNewSound(RegistryObject<SoundEvent> event, String baseSoundDirectory, int numberOfSounds, boolean subtitle) {
        SoundDefinition definition = SoundDefinition.definition();
        if (subtitle) {
            String[] splitSoundName = event.getId().getPath().split("\\.", 3);
            definition.subtitle("subtitles."+ EpicPiPaMod.MOD_ID+"." + splitSoundName[0]);
        }
        for (int i = 1; i <= numberOfSounds; i++) {
            definition.with(SoundDefinition.Sound.sound(new ResourceLocation(EpicPiPaMod.MOD_ID, baseSoundDirectory + (numberOfSounds > 1 ? "_"+i : "")), SoundDefinition.SoundType.SOUND));
        }
        this.add(event, definition);
    }

}
