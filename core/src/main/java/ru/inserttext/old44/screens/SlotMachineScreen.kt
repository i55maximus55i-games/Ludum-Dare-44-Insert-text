package ru.inserttext.old44.screens

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.app.use
import ru.inserttext.old44.Main

class SlotMachineScreen : KtxScreen {

    val batch = SpriteBatch()
    val shapeRenderer = ShapeRenderer()
    val camera = OrthographicCamera()

    val viewport = FitViewport(1024f, 768f, camera)

    val texture = Main.assets.getTexture("slots.png")
    val textureMachine = Main.assets.getTexture("m.png")
    var slotpos1 = 0f
    var slotpos2 = 0f
    var slotpos3 = 0f

    override fun show() {

    }

    override fun render(delta: Float) {
        clearScreen(0f, 0f, 0f)

        update(delta)
        draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {

    }

    fun update(delta: Float) {
        slotpos1 += delta * 512
        slotpos1 %= 192
        slotpos2 += delta * 512
        slotpos2 %= 192
        slotpos3 += delta * 512
        slotpos3 %= 192
    }

    fun draw() {
        batch.projectionMatrix = camera.combined
        shapeRenderer.projectionMatrix = camera.combined
        batch.use {
            batch.draw(texture, 338f, slotpos1 + 412)
            batch.draw(texture, 338f, slotpos1 - 192 + 412)
            batch.draw(texture, 457f, slotpos2 + 412)
            batch.draw(texture, 457f, slotpos2 - 192 + 412)
            batch.draw(texture, 573f, slotpos3 + 412)
            batch.draw(texture, 573f, slotpos3 - 192 + 412)
            batch.draw(textureMachine, 0f, 0f)
        }

        if (Main.debug) {

        }
    }

}