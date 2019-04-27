package ru.inserttext.old44.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ru.inserttext.old44.Main

class Player(world: World, position: Vector2, val scale: Float) {

    private val size = 64
    private val speed = 5f
    val body : Body

    val texture = Main.assets.getTexture("player.png")
    val textureRegion = TextureRegion()
    var animDuration = 0.4f
    var animTimer = 0f

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
        body.userData = "player"
    }

    fun draw(batch: SpriteBatch) {
        if (texture != null) {
            batch.draw(texture, body.position.x / scale - texture.width / 2, body.position.y / scale - texture.height / 2)
        }
    }

    fun update(delta: Float) {
        val m = Main.controls.playerMove()
        body.linearVelocity = m.scl(speed)
    }

}