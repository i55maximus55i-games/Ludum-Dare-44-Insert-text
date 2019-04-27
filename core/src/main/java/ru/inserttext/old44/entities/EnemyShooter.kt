package ru.inserttext.old44.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import ru.inserttext.old44.Main

class EnemyShooter(world: World, position: Vector2, scale: Float) : Enemy(world, position, scale) {

    private val texture = Main.assets.getTexture("enemyshoot.png")

    val deltaShootMin = 0.14f
    val deltaShootMax = 0.5f
    val distanceMin = 6f
    val distanceMax = 8f
    val speedMin = 2f
    val speedMax = 4f

    override fun draw(batch: SpriteBatch) {
        if (texture != null) {
            batch.draw(texture, body.position.x / scale - texture.width / 2, body.position.y / scale - texture.height / 2)
        }
    }

    override fun update(delta: Float, player: Player) {
        if (body.position.dst(player.body.position) >= distanceMax) {
            body.linearVelocity = body.position.sub(player.body.position).setLength(MathUtils.random(speedMin, speedMax)).scl(-1f)
        }
        if (body.position.dst(player.body.position) <= distanceMin) {
            body.linearVelocity = body.position.sub(player.body.position).setLength(MathUtils.random(speedMin, speedMax))
        }
    }
}