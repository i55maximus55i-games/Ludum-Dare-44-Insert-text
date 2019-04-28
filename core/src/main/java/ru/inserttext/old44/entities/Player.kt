package ru.inserttext.old44.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ru.inserttext.old44.Main
import ru.inserttext.old44.screens.GameScreen

class Player(val world: World, position: Vector2, val scale: Float, val shooter: Boolean, val bulletList: ArrayList<Bullet>, val enemyList: ArrayList<Enemy>) {

    private val size = 48
    private val speed = 5f
    val body : Body

    val texture = if (shooter) Main.assets.getTexture("hero2.png") else Main.assets.getTexture("hero1.png")
    val textureRegion = TextureRegion(texture)

    val animDuration = 0.3f
    val animShootDuration = 0.6f
    var animTimer = 0f
    var animShootTimer = 0f

    val attackdst = 1.5f

    val bulletSize = 16
    val bulletSpeed = 16f

    var hp = 4
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
        body.fixtureList.last().userData = this
        body.userData = "player"
    }

    fun draw(batch: SpriteBatch) {
        if (texture != null) {
            textureRegion.setRegion(0, 0, 64, 64)

            if (hp <= 0) {
                textureRegion.setRegion(0, 128, 64, 64)
                batch.draw(textureRegion, body.position.x / scale - textureRegion.regionWidth / 2, body.position.y / scale - textureRegion.regionHeight / 2)
            } else if (hitEffect < 0 || hitEffect % 0.1f > 0.05f) {
                val h = if (hp == 1 || hp == 2) 1 else 0
                if (animShootTimer <= animShootDuration) {
                    val t = (animShootTimer / (animShootDuration / 4)).toInt() + 2
                    textureRegion.setRegion(64 * t, 64 * h, 64, 64)
                } else {
                    val t = (animTimer / (animDuration / 2)).toInt()
                    textureRegion.setRegion(64 * t, 64 * h, 64, 64)
                }
                if (body.linearVelocity.x < 0)
                    textureRegion.flip(true, false)
                else
                    textureRegion.flip(false, false)
                batch.draw(textureRegion, body.position.x / scale - textureRegion.regionWidth / 2, body.position.y / scale - textureRegion.regionHeight / 2)
            }
        }
    }

    fun update(delta: Float) {
        val m = Main.controls.playerMove()

        if (hp > 0) {
            animTimer += delta
            animTimer %= animDuration
            animShootTimer += delta
            hitEffect -= delta

            body.linearVelocity = m.scl(speed)
            if (animShootTimer > animShootDuration && Main.controls.attack()) {
                shoot()
                animShootTimer = 0f
            }
        }
        else {
            body.linearVelocity = Vector2.Zero
        }
    }

    fun shoot() {
        if (shooter) {
            Main.assets.getSound("shot.wav")!!.play()

            bulletList.add(Bullet(world,
                    body.position.cpy().add(Main.controls.playerShoot().setLength(1f)),
                    Main.controls.playerShoot().setLength(bulletSpeed),
                    bulletSize.toFloat(),
                    bulletSpeed))
            bulletList.add(Bullet(world,
                    body.position.cpy().add(Main.controls.playerShoot().setLength(1f)),
                    Main.controls.playerShoot().setLength(bulletSpeed).rotate(7f),
                    bulletSize.toFloat(),
                    bulletSpeed))
            bulletList.add(Bullet(world,
                    body.position.cpy().add(Main.controls.playerShoot().setLength(1f)),
                    Main.controls.playerShoot().setLength(bulletSpeed).rotate(-7f),
                    bulletSize.toFloat(),
                    bulletSpeed))
        }
        else {
            for (i in enemyList)
                if (i.body.position.dst(body.position) <= attackdst) {
                    if (i.hp > 0 && i.hitEffect <= 0f) {
                        i.hp--
                        i.hitEffect = 0.5f

                        if (i.hp == 0)
                            GameScreen.sc += 666
                    }
                    Main.assets.getSound("hit.wav")!!.play()
                }
        }
    }

}