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

class CasinoLoseScreen(val score: Int) : KtxScreen {

    val stage = Stage(ScreenViewport()).apply {

        val labelStyle = Label.LabelStyle().apply {
            font = Main.assets.font24
            fontColor = Color.WHITE
        }

        val textButtonStyle = TextButton.TextButtonStyle().apply {
            font = Main.assets.font24
            fontColor = Color.WHITE
        }

        addActor(Table().apply {
            setFillParent(true)
            center()

            add(Label("После игры в казино\n" +
                    "вы остались в долгах.\n" +
                    "Вам придётся участвовать\nв смертельной схватке на арене!", labelStyle)).pad(32f).row()
            add(TextButton(">ЕЕЕ", textButtonStyle).apply {
                onClick {
                    Main.setScreen(SelectWeaponScreen(score), 0.25f, Color.WHITE)
                }
            })
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

        if (Main.controls.menuEnter())
            Main.setScreen(SelectWeaponScreen(score), 0.25f, Color.WHITE)
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