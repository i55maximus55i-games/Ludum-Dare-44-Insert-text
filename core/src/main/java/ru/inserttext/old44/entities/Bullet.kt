package ru.inserttext.old44.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ru.inserttext.old44.Main
import ru.inserttext.old44.Main.Companion.scale

class Bullet(val world: World, val pos: Vector2, val dir: Vector2, val size: Float, val speed: Float) {

    val body: Body
    val texture = Main.assets.getTexture("bullet.png")
    val textureRegion = TextureRegion(texture)
    var contacted = false
    var wall = false
    var timer = 0f
    val duration = 0.4f

    init {
        val bDef = BodyDef()
        val shape = CircleShape()
        val fDef = FixtureDef()

        bDef.type = BodyDef.BodyType.DynamicBody
        bDef.position.set(pos)

        body = world.createBody(bDef)

        shape.radius = size / 2f * Main.scale
        fDef.shape = shape
        fDef.friction = 0f
        fDef.restitution = 0f
        body.createFixture(fDef)
        body.fixtureList.last().userData = this
        body.userData = "bullet"

        body.linearVelocity = dir
    }

    fun update(delta: Float) {
        if (contacted) {
            timer += delta
        }
    }

    fun draw(batch: SpriteBatch) {
        val t = (timer / (duration / 6)).toInt()
        val a = if (wall) 1 else 0
        textureRegion.setRegion(64 * t, 64 * a, 64, 64)
        batch.draw(textureRegion, body.position.x / scale - textureRegion.regionWidth / 2, body.position.y / scale - textureRegion.regionHeight / 2)
    }

}