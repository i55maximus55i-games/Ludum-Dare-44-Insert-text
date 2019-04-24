package ru.inserttext.old44

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.Disposable

class Assets : Disposable {

    val font24 = BitmapFont(Gdx.files.internal("fonts/press-start-24.fnt"), Gdx.files.internal("fonts/press-start-24.png"), false)
    val font32 = BitmapFont(Gdx.files.internal("fonts/press-start-32.fnt"), Gdx.files.internal("fonts/press-start-32.png"), false)

    private val textures = HashMap<String, Texture>()
    private val sounds = HashMap<String, Sound>()
    private val musics = HashMap<String, Music>()

    fun getTexture(name: String) : Texture? {
        if (textures.containsKey(name))
            return textures[name]
        if (Gdx.files.internal("texture/$name").exists()) {
            textures[name] = Texture(Gdx.files.internal("texture/$name"))
            return textures[name]
        }
        return null
    }

    fun getSound(name: String) : Sound? {
        if (sounds.containsKey(name))
            return sounds[name]
        if (Gdx.files.internal("sound/$name").exists()) {
            sounds[name] = Gdx.audio.newSound(Gdx.files.internal("sound/$name"))
            return sounds[name]
        }
        return null
    }

    fun getMusic(name: String) : Music? {
        if (musics.containsKey(name))
            return musics[name]
        if (Gdx.files.internal("music/$name").exists()) {
            musics[name] = Gdx.audio.newMusic(Gdx.files.internal("music/$name"))
            return musics[name]
        }
        return null
    }

    override fun dispose() {
        font24.dispose()
        font32.dispose()
        for (i in textures) {
            i.value.dispose()
        }
        for (i in sounds) {
            i.value.dispose()
        }
        for (i in musics) {
            i.value.dispose()
        }
    }

}