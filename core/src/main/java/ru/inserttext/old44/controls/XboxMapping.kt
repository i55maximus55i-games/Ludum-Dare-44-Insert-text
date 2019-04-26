package ru.inserttext.old44.controls

import com.badlogic.gdx.utils.SharedLibraryLoader

class XboxMapping {
    companion object {
        // Buttons
        val A: Int
        val B: Int
        val X: Int
        val Y: Int
        val GUIDE: Int
        val L_BUMPER: Int
        val R_BUMPER: Int
        val BACK: Int
        val START: Int
        val DPAD_UP: Int
        val DPAD_DOWN: Int
        val DPAD_LEFT: Int
        val DPAD_RIGHT: Int
        val L_STICK: Int
        val R_STICK: Int

        // Axes
        val L_TRIGGER_AXIS: Int
        val R_TRIGGER_AXIS: Int
        val L_STICK_VERTICAL_AXIS: Int
        val L_STICK_HORIZONTAL_AXIS: Int
        val R_STICK_VERTICAL_AXIS: Int
        val R_STICK_HORIZONTAL_AXIS: Int

        init {
            when {
                SharedLibraryLoader.isWindows -> {
                    A = 0
                    B = 1
                    X = 2
                    Y = 3
                    GUIDE = -1
                    L_BUMPER = 4
                    R_BUMPER = 5
                    BACK = 6
                    START = 7
                    DPAD_UP = -1
                    DPAD_DOWN = -1
                    DPAD_LEFT = -1
                    DPAD_RIGHT = -1
                    L_TRIGGER_AXIS = 4
                    R_TRIGGER_AXIS = 4
                    L_STICK_VERTICAL_AXIS = 0
                    L_STICK_HORIZONTAL_AXIS = 1
                    R_STICK_VERTICAL_AXIS = 2
                    R_STICK_HORIZONTAL_AXIS = 3
                    L_STICK = 8
                    R_STICK = 9
                }
                SharedLibraryLoader.isLinux -> {
                    A = 0
                    B = 1
                    X = 2
                    Y = 3
                    GUIDE = 8
                    L_BUMPER = 4
                    R_BUMPER = 5
                    BACK = 6
                    START = 7
                    DPAD_UP = 13
                    DPAD_DOWN = 14
                    DPAD_LEFT = 11
                    DPAD_RIGHT = 12
                    L_TRIGGER_AXIS = 2
                    R_TRIGGER_AXIS = 5
                    L_STICK_VERTICAL_AXIS = 1
                    L_STICK_HORIZONTAL_AXIS = 0
                    R_STICK_VERTICAL_AXIS = 4
                    R_STICK_HORIZONTAL_AXIS = 3
                    L_STICK = 9
                    R_STICK = 10
                }
                else -> {
                    A = -1
                    B = -1
                    X = -1
                    Y = -1
                    GUIDE = -1
                    L_BUMPER = -1
                    R_BUMPER = -1
                    BACK = -1
                    START = -1
                    DPAD_UP = -1
                    DPAD_DOWN = -1
                    DPAD_LEFT = -1
                    DPAD_RIGHT = -1
                    L_TRIGGER_AXIS = -1
                    R_TRIGGER_AXIS = -1
                    L_STICK_VERTICAL_AXIS = -1
                    L_STICK_HORIZONTAL_AXIS = -1
                    R_STICK_VERTICAL_AXIS = -1
                    R_STICK_HORIZONTAL_AXIS = -1
                    L_STICK = -1
                    R_STICK = -1
                }
            }
        }
    }
}