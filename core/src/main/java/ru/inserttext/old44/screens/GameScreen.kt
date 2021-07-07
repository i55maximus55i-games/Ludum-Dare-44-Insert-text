package ru.inserttext.old44.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.app.use
import ru.inserttext.old44.ContactHandler
import ru.inserttext.old44.Main
import ru.inserttext.old44.Main.Companion.scale
import ru.inserttext.old44.entities.*

class GameScreen(val shoot: Boolean, val score: Int) : KtxScreen {

    companion object {
        var sc = 0
    }

    var changeScreenTimer = 0.75f
    var isChangeScreen = false

    val batch = SpriteBatch()
    var batch2 = SpriteBatch()
    val camera = OrthographicCamera()

    val labelStyle = Label.LabelStyle().apply {
        font = Main.assets.font24
        fontColor = Color.WHITE
    }

    val scoreLabel = Label("Score = $score", labelStyle)

    val stage = Stage(ScreenViewport()).apply {

        addActor(Table().apply {
            setFillParent(true)
            top()
            left()

            add(scoreLabel).pad(16f)
        })
    }

    val background = Main.assets.getTexture("plitka.png")
    val map = Main.assets.getMap("map1.tmx")
    val world = World(Vector2(), false)
    val box2DDebugRenderer = Box2DDebugRenderer()
    val orthogonalTiledMapRenderer = OrthogonalTiledMapRenderer(map)

    val bulletList = ArrayList<Bullet>()
    val enemyList = ArrayList<Enemy>()

    val hp = Main.assets.getTexture("hp.png")
    val hpR = TextureRegion(hp)
    var a = 0f

    var spawnTimer = 6f
    var spawnId = 0

    lateinit var player : Player

    override fun show() {
        enemyList.add(EnemyShooter(world, Vector2(72f, 72f), Main.scale, bulletList))

        world.setContactListener(ContactHandler())
        camera.apply {
            setToOrtho(false)
            update()
        }
        player = Player(world, getPlayerStartPos(),  Main.scale, shoot, bulletList, enemyList)
        createWalls()

        Gdx.input.inputProcessor = stage

        sc = score
    }

    override fun render(delta: Float) {
        clearScreen(0f, 0f, 0f)

        update(delta)
        draw()
    }

    override fun resize(width: Int, height: Int) {
        camera.apply {
            val a = 600f
            viewportWidth = a * width / height
            viewportHeight = a
            update()
        }

        batch2.dispose()
        batch2 = SpriteBatch()

        stage.viewport.update(width, height, true)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        stage.dispose()
    }

    fun update(delta: Float) {
        a += delta
        spawnTimer += delta

        if (spawnTimer > 6f) {
            spawnTimer = 0f

            for (i in map!!.layers.get("spawn").objects.getByType(RectangleMapObject::class.java)) {
                val rect = i.rectangle
                val vector2 = Vector2(rect.x, rect.y)
                spawnId++
                if (spawnId % 2 == 0)
                    enemyList.add(EnemyShooter(world, vector2, Main.scale, bulletList))
                else
                    enemyList.add(EnemyClose(world, vector2, Main.scale))
            }
        }

        player.update(delta)
        for (i in enemyList)
            i.update(delta, player)
        for (i in bulletList)
            i.update(delta)
        world.step(delta, 10, 10)

        camera.apply {
            camera.position.x = player.body.position.x / scale
            camera.position.y = player.body.position.y / scale
            camera.update()
        }

        if (changeScreenTimer <= 0f) {
            changeScreenTimer = 9999f
            if (sc >= 0) {
                Main.setScreen(ArenaWinScreen(), 0.24f, Color.WHITE)
                isChangeScreen = true
            }

            else if (player.hp <= 0) {
                Main.setScreen(ArenaLoseScreen(), 0.24f, Color.WHITE)
                isChangeScreen = true
            }
        }
        if (sc >= 0 || player.hp <= 0) {
            changeScreenTimer -= delta
        }
    }

    fun draw() {
        batch.use {
            batch.draw(background, 0f, 0f, 2048f, 2048f)
        }
        batch.projectionMatrix = camera.combined
        batch.use {
            for (i in enemyList)
                i.draw(batch)
            player.draw(batch)
        }

        orthogonalTiledMapRenderer.setView(camera)
        orthogonalTiledMapRenderer.render()

        batch.use {
            for (i in bulletList)
                i.draw(batch)
        }

        batch2.use {
            val t = 4 - player.hp
            when {
                player.hp > 0 -> hpR.setRegion(64 * t, 0, 64, 64)
                a % 1f > 0.5f -> hpR.setRegion(64 * 4, 0, 64, 64)
                else -> hpR.setRegion(64 * 5, 0, 64, 64)
            }
            batch2.draw(hpR, 16f, (Gdx.graphics.height - hpR.regionHeight * 2).toFloat(), 128f, 128f)
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

        scoreLabel.setText("Score = $sc")
        stage.apply {
            act()
            draw()
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