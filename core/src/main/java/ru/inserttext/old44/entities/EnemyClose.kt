package ru.inserttext.old44.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ru.inserttext.old44.Main

class EnemyClose(val world: World, position: Vector2, scale: Float) : Enemy(world, position, scale) {

    //TODO сделать здесь анимацию

    private val texture = Main.assets.getTexture("enemy2.png")
    val textureRegion = TextureRegion(texture)
    val animDuration = 0.3f
    var animTimer = 0f
    val attackAnimDuration = 0.2f
    val attackAnimTimer = 0f

    val distanceMin = 1.2f
    val distanceMax = 2f
    val speedMin = 4f
    val speedMax = 5f
    val attackDst = 1.5f
    val gobackDuration = 0.4f

    var goBackTimer = 0f

    var hp = 2
    var hitEffect = 0f

    override fun draw(batch: SpriteBatch) {
        if (texture != null) {
            textureRegion.texture = texture
            textureRegion.setRegion(0, 0, 64, 64)

            if (hp <= 0) {
                textureRegion.setRegion(0, 128, 64, 64)
                batch.draw(textureRegion, body.position.x / scale - textureRegion.regionWidth / 2, body.position.y / scale - textureRegion.regionHeight / 2)
            } else if (hitEffect < 0 || hitEffect % 0.1f > 0.05f) {
                if (attackAnimTimer <= attackAnimDuration) {
                    val t = (animTimer / (animDuration / 4)).toInt() + 2
                    textureRegion.setRegion(64 * t, 64 * (2 - hp), 64, 64)
                } else {
                    val t = (animTimer / (animDuration / 2)).toInt()
                    textureRegion.setRegion(64 * t, 64 * (2 - hp), 64, 64)
                }
                batch.draw(textureRegion, body.position.x / scale - textureRegion.regionWidth / 2, body.position.y / scale - textureRegion.regionHeight / 2)
            }
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