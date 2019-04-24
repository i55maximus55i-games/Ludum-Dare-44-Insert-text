package ru.inserttext.old44

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen

class Main : Game() {

    companion object {
        private lateinit var instance: Main
        lateinit var assets: Assets

        fun setScreen(screen: Screen) {
            instance.setScreen(screen)
        }
    }

    override fun create() {
        instance = this
        assets = Assets()

        setScreen(MainMenuScreen())
    }

}