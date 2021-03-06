package ru.inserttext.old44.controls

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.math.Vector3
import ru.inserttext.old44.Main

class GamePad : ControllerListener {

    val buttonsPressed = HashMap<Int, Boolean>()
    val buttonsJustPressed = HashMap<Int, Boolean>()
    val axis = HashMap<Int, Float>()
    val deathZone = 0.2f

    var povUp = false
    var povDown = false
    var povLeft = false
    var povRight = false

    fun update() {
        for (btn in buttonsJustPressed.keys) {
            buttonsJustPressed[btn] = false
            povUp = false
            povDown = false
            povLeft = false
            povRight = false
        }
    }

    fun axis(axisCode: Int): Float {
        return axis.getOrDefault(axisCode, 0f)
    }

    fun isButtonPressed(buttonCode: Int): Boolean {
        return buttonsPressed.getOrDefault(buttonCode, false)
    }

    fun isButtonJustPressed(buttonCode: Int): Boolean {
        val btn = buttonsJustPressed.getOrDefault(buttonCode, false)
        if (buttonsJustPressed.containsKey(buttonCode))
            buttonsJustPressed[buttonCode] = false
        return btn
    }

    override fun connected(controller: Controller?) {
        Main.controls.isGamePad = true
        if (Main.debug)
            Gdx.app.log(controller!!.name, "connected")
    }

    override fun disconnected(controller: Controller?) {
        Main.controls.isGamePad = true
        if (Main.debug)
            Gdx.app.log(controller!!.name, "disconnected")
    }

    override fun povMoved(controller: Controller?, povCode: Int, value: PovDirection?): Boolean {
        Main.controls.isGamePad = true
        if (value == PovDirection.north)
            povUp = true
        if (value == PovDirection.south)
            povDown = true
        if (Main.debug)
            Gdx.app.log(controller!!.name + " pov " + povCode, value.toString())
        return false
    }

    override fun buttonUp(controller: Controller?, buttonCode: Int): Boolean {
        Main.controls.isGamePad = true
        buttonsPressed[buttonCode] = false
        buttonsJustPressed[buttonCode] = false
        if (Main.debug)
            Gdx.app.log(controller!!.name + " button " + buttonCode, "unpressed")
        return false
    }

    override fun buttonDown(controller: Controller?, buttonCode: Int): Boolean {
        Main.controls.isGamePad = true
        buttonsPressed[buttonCode] = true
        buttonsJustPressed[buttonCode] = true
        if (Main.debug)
            Gdx.app.log(controller!!.name + " " + buttonCode, "pressed")
        return false
    }

    override fun axisMoved(controller: Controller?, axisCode: Int, value: Float): Boolean {
        if (Math.abs(value) < deathZone)
            axis[axisCode] = 0f
        else {
            Main.controls.isGamePad = true
            axis[axisCode] = value
        }
        if (Main.debug)
            Gdx.app.log(controller!!.name + " axis " + axisCode, value.toString())
        return false
    }

    override fun xSliderMoved(controller: Controller?, sliderCode: Int, value: Boolean): Boolean {
        return false
    }

    override fun ySliderMoved(controller: Controller?, sliderCode: Int, value: Boolean): Boolean {
        return false
    }

    override fun accelerometerMoved(controller: Controller?, accelerometerCode: Int, value: Vector3?): Boolean {
        return false
    }

}