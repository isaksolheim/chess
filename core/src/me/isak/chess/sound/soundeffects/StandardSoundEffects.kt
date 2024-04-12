package me.isak.chess.sound.soundeffects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import me.isak.chess.sound.SoundController
import me.isak.chess.sound.SoundEffectsMap

private val StandardSounds by lazy {
    hashMapOf<SoundController.GameSounds, Sound>().apply {
        put(SoundController.GameSounds.Click, Gdx.audio.newSound(Gdx.files.internal("sound/game/standard/click.wav")))
        put(SoundController.GameSounds.Move, Gdx.audio.newSound(Gdx.files.internal("sound/game/standard/move.mp3")))
        put(SoundController.GameSounds.Capture, Gdx.audio.newSound(Gdx.files.internal("sound/game/standard/capture.mp3")))
        put(SoundController.GameSounds.Check, Gdx.audio.newSound(Gdx.files.internal("sound/game/standard/check.mp3")))
        put(SoundController.GameSounds.Win, Gdx.audio.newSound(Gdx.files.internal("sound/game/standard/win.mp3")))
    }
}
val StandardSoundEffectsMap : SoundEffectsMap = SoundEffectsMap(null,StandardSounds)


private val GoofySounds by lazy {
    hashMapOf<SoundController.GameSounds, Sound>().apply {
        put(SoundController.GameSounds.Move, Gdx.audio.newSound(Gdx.files.internal("sound/game/goofy/move.mp3")))
        put(SoundController.GameSounds.Capture, Gdx.audio.newSound(Gdx.files.internal("sound/game/goofy/overtake.mp3")))
        put(SoundController.GameSounds.Check, Gdx.audio.newSound(Gdx.files.internal("sound/game/goofy/check.mp3")))
        put(SoundController.GameSounds.Win, Gdx.audio.newSound(Gdx.files.internal("sound/game/goofy/win.mp3")))
    }
}

val GoofySoundEffectsMap : SoundEffectsMap = SoundEffectsMap(null, GoofySounds)