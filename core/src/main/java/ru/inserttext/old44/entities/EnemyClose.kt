package ru.inserttext.old44.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ru.inserttext.old44.Main

class EnemyClose(val world: World, position: Vector2, scale: Float) : Enemy(world, position, scale) {

    private val texture = Main.assets.getTexture("enemyclose.png")

    val distanceMin = 1.2f
    val distanceMax = 2f
    val speedMin = 4f
    val speedMax = 5f
    val attackDst = 1.5f
    val attackAnimDuration = 0.2f
    val gobackDuration = 0.4f

    var goBackTimer = 0f

    override fun draw(batch: SpriteBatch) {
        if (texture != null) {
            batch.draw(texture, body.position.x / scale - texture.width / 2, body.position.y / scale - texture.height / 2)
        }
    }

    override fun update(delta: Float, player: Player) {
        goBackTimer -= delta
        if (body.position.dst(player.body.position) >= distanceMax) {
            body.linearVelocity = player.body.position.sub(body.position).setLength(MathUtils.random(speedMin, speedMax))
        }
        if (body.position.dst(player.body.position) <= distanceMin) {
            body.linearVelocity = body.position.sub(player.body.position).setLength(MathUtils.random(speedMin, speedMax))
        }
        if (goBackTimer >= 0) {
            body.linearVelocity = body.position.sub(player.body.position).setLength(MathUtils.random(speedMin, speedMax) * 1.8f)
        }
        if (body.position.dst(player.body.position) <= attackDst) {
            goBackTimer = gobackDuration
        }
    }

}