package ru.inserttext.old44.controls

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2

class Controls {

    val gamepads = ArrayList<GamePad>()

    init {
        for (controller in Controllers.getControllers()) {
            gamepads.add(GamePad())
            controller.addListener(gamepads.last())
        }
    }

    fun update() {
        for (gamepad in gamepads)
            gamepad.update()
    }

    fun start(): Boolean {
        var t = false
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            t = true
        for (i in gamepads)
            if (i.isButtonJustPressed(XboxMapping.START))
                t = true
        return t
    }

    fun playerMove(): Vector2 {
        var x = 0f
        var y = 0f

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            x -= 1f
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            x -= 1f
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            x += 1f
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            x += 1f
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            y += 1f
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            y += 1f
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            y -= 1f
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            y -= 1f

        for (i in gamepads) {
            x += i.axis(XboxMapping.L_STICK_HORIZONTAL_AXIS)
            y -= i.axis(XboxMapping.L_STICK_VERTICAL_AXIS)
        }
        return Vector2(x, y).clamp(0f, 1f)
    }

    fun playerRun(): Boolean {
        var t = false
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            t = true
        for (i in gamepads)
            if (i.isButtonPressed(XboxMapping.L_BUMPER))
                t = true
        return t
    }

    fun playerJump(): Boolean {
        var t = false
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            t = true
        for (i in gamepads)
            if (i.isButtonPressed(XboxMapping.A))
                t = true
        return t
    }

    fun attack(): Boolean {
        var t = false
        if (Gdx.input.isKeyJustPressed(Input.Keys.F))
            t = true
        for (i in gamepads)
            if (i.isButtonJustPressed(XboxMapping.X))
                t = true
        return t
    }

}