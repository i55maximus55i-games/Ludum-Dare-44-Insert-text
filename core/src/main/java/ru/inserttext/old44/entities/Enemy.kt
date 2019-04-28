package ru.inserttext.old44.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ru.inserttext.old44.Main

open class Enemy(world: World, position: Vector2, val scale: Float) {
    val size = 64
    val body : Body

    var hp = 2
    var hitEffect = 0f

    init {
        val bDef = BodyDef()
        val shape = CircleShape()
        val fDef = FixtureDef()

        bDef.type = BodyDef.BodyType.DynamicBody
        bDef.position.set((position.x + size / 2) * scale, (position.y + size / 2) * scale)

        body = world.createBody(bDef)

        shape.radius = size / 2f * Main.scale
        fDef.shape = shape
        fDef.friction = 0f
        fDef.restitution = 0f
        body.createFixture(fDef)
        body.userData = "enemy"
    }

    open fun draw(batch: SpriteBatch) {

    }

    open fun update(delta: Float, player: Player) {

    }
}