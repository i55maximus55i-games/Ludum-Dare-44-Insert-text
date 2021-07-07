package ru.inserttext.old44.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.app.use
import ru.inserttext.old44.Main

class SlotMachineScreen : KtxScreen {

    val batch = SpriteBatch()
    val camera = OrthographicCamera()

    val viewport = FitViewport(1024f, 768f, camera)

    var change = false
    val mDur = 1.2f
    var mTimer = mDur

    val betList = ArrayList<Int>().apply {
        add(10)
        add(50)
        add(100)
        add(200)
        add(500)
        add(1000)
        add(2000)
        add(5000)
    }
    var bet = 2
    var score = 0
    val goal = 15000
    val min = -2500

    val labelStyle = Label.LabelStyle().apply {
        font = Main.assets.font24
        fontColor = Color.WHITE
    }
    val labelScore = Label("score = $score", labelStyle).apply {
        setPosition(336f, 282f)
    }
    val labelBet = Label("bet = ${betList[bet]}", labelStyle).apply {
        setPosition(336f, 239f)
    }
    val stage = Stage(viewport).apply {
        addActor(labelBet)
        addActor(labelScore)
    }

    val texture = Main.assets.getTexture("slots.png")
    val textureMachine = Main.assets.getTexture("m.png")
    val textureRegion = TextureRegion(textureMachine)
    var slotpos1 = 32f
    var slotpos2 = 32f
    var slotpos3 = 32f

    var slotTimer = 0f
    var i = -1
    var r1 = 0
    var r2 = 1
    var r3 = 2
    val r2Chance = 0.3f
    val r3Chance = 0.4f
//    val r2Chance = 0.0f
//    val r3Chance = 0.0f

    override fun show() {
//        var c = 0
//        val n = 10000
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
        stage.apply {
            act()
            draw()
        }
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
        mTimer += delta

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

                    if (r1 == r2 && r2 == r3) {
                        score += betList[bet] * 2
                    } else {
                        score -= betList[bet]
                    }
                    labelScore.setText("score = $score")
                    if (!change) {
                        if (score < min) {
                            Main.setScreen(CasinoLoseScreen(score), 0.25f, Color.WHITE)
                            change = true
                        }
                        if (score > goal) {
                            Main.setScreen(CasinoWinScreen(), 0.25f, Color.WHITE)
                            change = true
                        }
                    }
                }
                else {
                    slotpos3 += delta * 512
                    slotpos3 %= 192
                }
            }
        }

        if (i == -1) {
            if (Main.controls.up()) {
                bet++
                if (bet >= betList.size)
                    bet = betList.lastIndex
            }
            if (Main.controls.down()) {
                bet--
                if (bet < 0)
                    bet = 0
            }
            labelBet.setText("bet = ${betList[bet]}")

            if (Main.controls.menuEnter()) {
                Main.assets.getSound("slot.wav")!!.play()
                mTimer = 0f
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
    }

    fun draw() {
        batch.projectionMatrix = camera.combined
        batch.use {
            batch.draw(texture, 365f, slotpos1 + 351)
            batch.draw(texture, 365f, slotpos1 - 192 + 351)
            batch.draw(texture, 458f, slotpos2 + 351)
            batch.draw(texture, 458f, slotpos2 - 192 + 351)
            batch.draw(texture, 552f, slotpos3 + 351)
            batch.draw(texture, 552f, slotpos3 - 192 + 351)

            val a = if (mTimer > mDur) 0 else (mTimer / (mDur / 7)).toInt()
            textureRegion.setRegion(1024 * a, 0, 1024, 768)
            batch.draw(textureRegion, 0f, 0f)
        }

        if (Main.debug) {

        }
    }

}