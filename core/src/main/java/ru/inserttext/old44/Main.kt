package ru.inserttext.old44

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ru.inserttext.old44.controls.Controls
import ru.inserttext.old44.screens.GameScreen
import ru.inserttext.old44.screens.SelectWeaponScreen

class Main(private val isDebug : Boolean) : Game() {

    private lateinit var shapeRenderer: ShapeRenderer
    private var blickTimer = 0f
    private var blickTime = 0f
    private var blink = false
    private var blinkColor = Color.BLACK
    private lateinit var changeScreen : Screen
    private var isChangeScreen = false
    private var changed = false

    companion object {
        private lateinit var instance: Main
        lateinit var assets: Assets
        lateinit var controls: Controls
        var debug = false
        const val scale = 1 / 64f

        fun blink(time: Float, color: Color) {
            instance.blink(time, color)
        }

        fun setScreen(screen: Screen, time: Float, color: Color) {
            instance.changeScreen(screen, time, color)
        }

        fun setScreen(screen: Screen) {
            instance.setScreen(screen)
        }
    }

    override fun create() {
        instance = this
        debug = isDebug
        assets = Assets()
        controls = Controls()
        shapeRenderer = ShapeRenderer()

        //TODO убрать обратно
//        setScreen(MainMenuScreen())
        setScreen(SelectWeaponScreen(0))
    }

    override fun render() {
        super.render()
        if (blink) {
            blickTimer += Gdx.graphics.deltaTime
            if (changed) {
                changed = false
                blickTimer -= Gdx.graphics.deltaTime
            }
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
            shapeRenderer.color = blinkColor
            if (blickTimer < blickTime) {
                shapeRenderer.rect(0f, 0f,
                        Gdx.graphics.width.toFloat(),Gdx.graphics.height.toFloat() * 5 / 8 * blickTimer / blickTime)
                shapeRenderer.rect(0f, Gdx.graphics.height.toFloat(),
                        Gdx.graphics.width.toFloat(), - Gdx.graphics.height.toFloat() * 5 / 8 * blickTimer / blickTime)
            }
            else {
                shapeRenderer.rect(0f, 0f,
                        Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat() * 5 / 8 * (1 - ((blickTimer - blickTime) / blickTime)))
                shapeRenderer.rect(0f, Gdx.graphics.height.toFloat(),
                        Gdx.graphics.width.toFloat(), -Gdx.graphics.height.toFloat() * 5 / 8 * (1 - ((blickTimer - blickTime) / blickTime)))
            }
            shapeRenderer.end()
            if (blickTimer > blickTime && isChangeScreen) {
                screen.dispose()
                screen = changeScreen
                screen.show()
                screen.resize(Gdx.graphics.width, Gdx.graphics.height)
                isChangeScreen = false
                changed = true
            }
            if (blickTimer > blickTime * 2)
                blink = false
        }
        controls.update()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        shapeRenderer.dispose()
        shapeRenderer = ShapeRenderer()
    }

    fun changeScreen(screen: Screen, time: Float, color: Color) {
        changeScreen = screen
        isChangeScreen = true
        blink(time, color)
    }

    fun blink(time: Float, color: Color) {
        blickTimer = 0f
        blickTime = time
        blink = true
        blinkColor = color
    }
}