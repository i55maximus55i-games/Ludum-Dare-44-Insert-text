package ru.inserttext.old44

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen

class Main(private val isDebug : Boolean) : Game() {

    companion object {
        private lateinit var instance: Main
        lateinit var assets: Assets
        lateinit var controls: Controls
        var debug = false

        fun setScreen(screen: Screen) {
            instance.setScreen(screen)
        }
    }

    override fun create() {
        instance = this
        debug = isDebug
        assets = Assets()
        controls = Controls()

        setScreen(MainMenuScreen())
    }

    override fun render() {
        super.render()
        controls.update()
    }
}