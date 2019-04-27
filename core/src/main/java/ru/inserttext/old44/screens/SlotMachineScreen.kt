package ru.inserttext.old44.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
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
    var slotpos1 = 32f
    var slotpos2 = 32f
    var slotpos3 = 32f

    var slotTimer = 0f
    var i = -1
    var r1 = 0
    var r2 = 1
    var r3 = 2
    val r2Chance = 0.6f
    val r3Chance = 0.8f

    override fun show() {
//        var c = 0
//        val n = 1000
//        for (i in 0 .. n) {
//            r1 = MathUtils.random(0, 2)
//            r2 = if (MathUtils.random(0f, 1f) > r2Chance) {
//                MathUtils.random(0, 2)
//            } else {
//                r1
//            }
//            r3 = if (MathUtils.random(0f, 1f) > r3Chance) {
//                MathUtils.random(0, 2)
//            } else {
//                r2
//            }
//            if (r1 == r2 && r2 == r3)
//                c++
//        }
//        println(c.toFloat() / n.toFloat())
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
        slotTimer -= delta

        if (i == 0) {
            if (slotTimer >= 0) {
                slotpos1 += delta * 512
                slotpos1 %= 192
            }
            else {
                if (Math.abs(slotpos1 - (32f + r1 * 64f)) <= delta * 1.2f * 512) {
                    i++
                    slotpos1 = 32f + r1 * 64f
                    slotTimer = 0.5f
                }
                else {
                    slotpos1 += delta * 512
                    slotpos1 %= 192
                }
            }
            slotpos2 = slotpos1
            slotpos3 = slotpos1
        }
        else if (i == 1) {
            if (slotTimer >= 0) {
                slotpos2 += delta * 512
                slotpos2 %= 192
            }
            else {
                if (Math.abs(slotpos2 - (32f + r2 * 64f)) <= delta * 1.2f * 512) {
                    i++
                    slotpos2 = 32f + r2 * 64f
                    slotTimer = 0.5f
                }
                else {
                    slotpos2 += delta * 512
                    slotpos2 %= 192
                }
            }
            slotpos3 = slotpos2
        }
        else if (i == 2) {
            if (slotTimer >= 0) {
                slotpos3 += delta * 512
                slotpos3 %= 192
            }
            else {
                if (Math.abs(slotpos3 - (32f + r3 * 64f)) <= delta * 1.2f * 512) {
                    i = -1
                    slotpos3 = 32f + r3 * 64f
                    slotTimer = 0.5f
                }
                else {
                    slotpos3 += delta * 512
                    slotpos3 %= 192
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && i == -1) {
            i = 0
            slotTimer = 1f

            r1 = MathUtils.random(0, 2)
            r2 = if (MathUtils.random(0f, 1f) > r2Chance) {
                MathUtils.random(0, 2)
            } else {
                r1
            }
            r3 = if (MathUtils.random(0f, 1f) > r3Chance) {
                MathUtils.random(0, 2)
            } else {
                r2
            }
        }
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