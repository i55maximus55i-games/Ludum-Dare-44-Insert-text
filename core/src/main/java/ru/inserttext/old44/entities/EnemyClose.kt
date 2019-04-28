package ru.inserttext.old44.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ru.inserttext.old44.Main

class EnemyClose(val world: World, position: Vector2, scale: Float) : Enemy(world, position, scale) {

    private val texture = Main.assets.getTexture("enemy2.png")
    val textureRegion = TextureRegion(texture)
    val animDuration = 0.3f
    var animTimer = 0f
    val attackAnimDuration = 1.2f
    var attackAnimTimer = 0f

    val distanceMin = 0.9f
    val distanceMax = 2f
    val speedMin = 3.5f
    val speedMax = 4.5f
    val attackDst = 1f
    val gobackDuration = 0.3f

    var goBackTimer = 0f

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
                if (attackAnimTimer <= attackAnimDuration) {
                    val t = (animTimer / (animDuration / 4)).toInt() + 2
                    textureRegion.setRegion(64 * t, 64 * (2 - hp), 64, 64)
                } else {
                    val t = (animTimer / (animDuration / 2)).toInt()
                    textureRegion.setRegion(64 * t, 64 * (2 - hp), 64, 64)
                }
                if (body.linearVelocity.x < 0)
                    textureRegion.flip(true, false)
                else
                    textureRegion.flip(false, false)
                batch.draw(textureRegion, body.position.x / scale - textureRegion.regionWidth / 2, body.position.y / scale - textureRegion.regionHeight / 2)
            }
        }
    }

    override fun update(delta: Float, player: Player) {
        if (hp > 0) {
            goBackTimer -= delta
            animTimer += delta
            animTimer %= animDuration
            attackAnimTimer += delta
            hitEffect -= delta

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
                attackAnimTimer = 0f
                attatck(player)
            }
        }
        else {
            body.linearVelocity = Vector2.Zero
        }
    }

    fun attatck(player: Player) {
        Main.assets.getSound("hit.wav")!!.play()
        if (player.hp > 0 && player.hitEffect <= 0f) {
            player.hp--
            player.hitEffect = 0.5f
        }
    }

}