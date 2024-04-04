package me.isak.chess.sound.soundeffects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import me.isak.chess.sound.SoundController
import me.isak.chess.sound.SoundEffectsMap

private val Sounds by lazy {
    hashMapOf<SoundController.GameSounds, Sound>().apply {
        put(SoundController.GameSounds.ClickBoard, Gdx.audio.newSound(Gdx.files.internal("sound/game/standard/click.wav")))
        put(SoundController.GameSounds.MovePiece, Gdx.audio.newSound(Gdx.files.internal("sound/game/standard/check.wav")))
        put(SoundController.GameSounds.TakeOver, Gdx.audio.newSound(Gdx.files.internal("sound/game/standard/overtake.wav")))
        put(SoundController.GameSounds.Win, Gdx.audio.newSound(Gdx.files.internal("sound/game/standard/win.mp3")))
    }
}
val StandardSoundEffectsMap : SoundEffectsMap = SoundEffectsMap(null,Sounds)
