package ru.inserttext.old44.screens

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.app.use
import ru.inserttext.old44.Main
import ru.inserttext.old44.Main.Companion.scale
import ru.inserttext.old44.entities.EnemyShooter
import ru.inserttext.old44.entities.Player

class GameScreen : KtxScreen {

    val batch = SpriteBatch()
    val camera = OrthographicCamera()

    val map = Main.assets.getMap("map1.tmx")
    val world = World(Vector2(), false)
    val box2DDebugRenderer = Box2DDebugRenderer()
    val orthogonalTiledMapRenderer = OrthogonalTiledMapRenderer(map)


    lateinit var player : Player
    var enemyShooter = EnemyShooter(world, Vector2(0f, 0f), Main.scale)

    override fun show() {
        camera.apply {
            setToOrtho(false)
            update()
        }
        player = Player(world, getPlayerStartPos(),  Main.scale)
        createWalls()
    }

    override fun render(delta: Float) {
        clearScreen(0f, 0f, 0f)

        update(delta)
        draw()
    }

    override fun resize(width: Int, height: Int) {
        camera.apply {
            val a = 1200f
            viewportWidth = a * width / height
            viewportHeight = a
            update()
        }
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {

    }

    fun update(delta: Float) {
        player.update(delta)
        enemyShooter.update(delta, player)
        world.step(delta, 10, 10)

        camera.apply {
            camera.position.x = player.body.position.x / scale
            camera.position.y = player.body.position.y / scale
            camera.update()
        }
    }

    fun draw() {
        orthogonalTiledMapRenderer.setView(camera)
        orthogonalTiledMapRenderer.render()
        batch.projectionMatrix = camera.combined
        batch.use {
            enemyShooter.draw(batch)
            player.draw(batch)
        }

        if (Main.debug) {
            camera.zoom *= scale
            camera.position.x *= scale
            camera.position.y *= scale
            camera.update()
            box2DDebugRenderer.render(world, camera.combined)
            camera.zoom /= scale
            camera.position.x /= scale
            camera.position.y /= scale
            camera.update()
        }
    }

    fun createWalls() {
        val bDef = BodyDef()
        val shape = PolygonShape()
        val fDef = FixtureDef()
        var body: Body

        for (i in map!!.layers.get("walls").objects.getByType(RectangleMapObject::class.java)) {
            val rect = i.rectangle
            bDef.type = BodyDef.BodyType.StaticBody
            bDef.position.set((rect.getX() + rect.getWidth() / 2) * Main.scale, (rect.getY() + rect.getHeight() / 2) * Main.scale)

            body = world.createBody(bDef)

            shape.setAsBox(rect.getWidth() / 2 * Main.scale, rect.getHeight() / 2 * Main.scale)
            fDef.shape = shape
            fDef.friction = 0f
            fDef.restitution = 0f
            fDef.density = 1f
            body.createFixture(fDef)
            body.userData = "wall"
        }
    }

    fun getPlayerStartPos() : Vector2 {
        val vector2 = Vector2()
        for (i in map!!.layers.get("player").objects.getByType(RectangleMapObject::class.java)) {
            val rect = i.rectangle
            vector2.apply {
                x = rect.x
                y = rect.y
            }
        }
        return vector2
    }

}