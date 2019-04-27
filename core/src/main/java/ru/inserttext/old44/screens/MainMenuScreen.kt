package ru.inserttext.old44.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.onClick
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.app.use
import ru.inserttext.old44.Main

class MainMenuScreen : KtxScreen {

    val batch = SpriteBatch()
    val camera = OrthographicCamera()

    val ipega = Main.assets.getTexture("ipega.png")
    val enter = Main.assets.getTexture("enter.png")

    val viewport = FitViewport(1024f, 768f, camera)
    val stage = Stage(ScreenViewport()).apply {

        val textButtonStyle = TextButton.TextButtonStyle().apply {
            font = Main.assets.font24
            fontColor = Color.WHITE
        }

        addActor(Table().apply {
            setFillParent(true)
            center()

        })
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        clearScreen(0f, 0f, 0f)

        batch.projectionMatrix = camera.combined
        batch.use {
            if (Main.controls.isGamePad) {
                if (ipega != null) {
                    batch.draw(ipega, 512f - ipega.width / 2, 384f - ipega.height / 2)
                }
            }
            else {
                if (enter != null) {
                    batch.draw(enter, 512f - enter.width / 2, 384f - enter.height / 2)
                }
            }
        }

        stage.apply {
            act()
            draw()
        }
        if (Main.controls.start()) {
            Main.setScreen(IntroScreen(3), 0.25f, Color.WHITE)
        }
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
        viewport.update(width, height, true)
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