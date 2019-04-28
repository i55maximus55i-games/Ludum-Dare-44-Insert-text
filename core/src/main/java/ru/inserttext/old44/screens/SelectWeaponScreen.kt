package ru.inserttext.old44.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.onClick
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ru.inserttext.old44.Main

class SelectWeaponScreen(val score: Int) : KtxScreen {

    var a = false

    val labelStyle = Label.LabelStyle().apply {
        font = Main.assets.font24
        fontColor = Color.WHITE
    }

    val textButtonStyle = TextButton.TextButtonStyle().apply {
        font = Main.assets.font24
        fontColor = Color.WHITE
    }


    val workButton = TextButton(">Дробовик", textButtonStyle).apply {
        onClick {
            Main.setScreen(GameScreen(true, score), 0.25f, Color.WHITE)
        }
    }
    val casinoButton = TextButton("Фламинго", textButtonStyle).apply {
        onClick {
            Main.setScreen(GameScreen(false, score), 0.25f, Color.WHITE)
        }
    }

    val stage = Stage(ScreenViewport()).apply {

        addActor(Table().apply {
            setFillParent(true)
            center()

            add(Label("Выберите оружие", labelStyle)).pad(48f).row()
            add(workButton).pad(8f).row()
            add(casinoButton)
        })
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        clearScreen(0f, 0f, 0f)

        stage.apply {
            act()
            draw()
        }
        if (Main.controls.up() || Main.controls.down()) {
            a = !a
            if (a) {
                workButton.setText("Дробовик")
                casinoButton.setText(">Фламинго")
            } else {
                workButton.setText(">Дробовик")
                casinoButton.setText("Фламинго")
            }
        }
        if (Main.controls.menuEnter()) {
            if (a) {
                Main.setScreen(GameScreen(false, score), 0.25f, Color.WHITE)
            } else {
                Main.setScreen(GameScreen(true, score), 0.25f, Color.WHITE)
            }
        }
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        stage.dispose()
    }

}