package ru.inserttext.old44.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ru.inserttext.old44.Main

class EnemyShooter(val world: World, position: Vector2, scale: Float, val bulletList: ArrayList<Bullet>) : Enemy(world, position, scale) {

    private val texture = Main.assets.getTexture("enemy1.png")
    val textureRegion = TextureRegion(texture)
    val animDuration = 0.3f
    val animShootDuration = 0.64f
    var animTimer = 0f
    var animShootTimer = 0f

    val deltaShootMin = 1f
    val deltaShootMax = 3f
    val distanceMin = 6f
    val distanceMax = 8f
    val speedMin = 2f
    val speedMax = 4f
    val bulletSize = 16
    val bulletSpeed = 16f

    var shootTimer = MathUtils.random(deltaShootMin, deltaShootMax)

    init {
        body.fixtureList.last().userData = this
    }

    override fun draw(batch: SpriteBatch) {
        if (texture != null) {
            textureRegion.texture = texture
            textureRegion.setRegion(0, 0, 64, 64)

            if (hp <= 0) {
                textureRegion.setRegion(0, 128, 64, 64)
                batch.draw(textureRegion, body.position.x / scale - textureRegion.regionWidth / 2, body.position.y / scale - textureRegion.regionHeight / 2)
            } else if (hitEffect < 0 || hitEffect % 0.1f > 0.05f) {
                if (animShootTimer <= animShootDuration) {
                    val t = (animShootTimer / (animShootDuration / 4)).toInt() + 2
                    textureRegion.setRegion(64 * t, 64 * (2 - hp), 64, 64)
                } else {
                    val t = (animTimer / (animDuration / 2)).toInt()
                    textureRegion.setRegion(64 * t, 64 * (2 - hp), 64, 64)
                }
                if (body.linearVelocity.x < 0)
                    textureRegion.flip(false, false)
                else
                    textureRegion.flip(true, false)
                batch.draw(textureRegion, body.position.x / scale - textureRegion.regionWidth / 2, body.position.y / scale - textureRegion.regionHeight / 2)
            }
        }
    }

    override fun update(delta: Float, player: Player) {
        if (hp > 0) {
            shootTimer -= delta
            animTimer += delta
            animTimer %= animDuration
            animShootTimer += delta
            hitEffect -= delta

            if (body.position.dst(player.body.position) >= distanceMax) {
                body.linearVelocity = player.body.position.sub(body.position).setLength(MathUtils.random(speedMin, speedMax))
                shootTimer = MathUtils.random(deltaShootMin, deltaShootMax)
            }
            if (body.position.dst(player.body.position) <= distanceMin) {
                body.linearVelocity = body.position.sub(player.body.position).setLength(MathUtils.random(speedMin, speedMax))
            }
            if (shootTimer <= 0) {
                shootTimer = MathUtils.random(deltaShootMin, deltaShootMax)
                shoot(player)
                animShootTimer = 0f
            }
        }
        else {
            body.linearVelocity = Vector2.Zero
        }
    }

    fun shoot(player: Player) {
        Main.assets.getSound("shot.wav")!!.play()
        bulletList.add(Bullet(world,
                body.position.cpy().add(player.body.position.cpy().sub(body.position).setLength(1f)),
                player.body.position.sub(body.position).setLength(bulletSpeed),
                bulletSize.toFloat(),
                bulletSpeed))
    }
}