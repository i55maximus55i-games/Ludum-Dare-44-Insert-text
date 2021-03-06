package ru.inserttext.old44

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import ru.inserttext.old44.entities.Bullet
import ru.inserttext.old44.entities.Enemy
import ru.inserttext.old44.entities.Player
import ru.inserttext.old44.screens.GameScreen

class ContactHandler : ContactListener {

    override fun beginContact(contact: Contact?) {
        if (contact != null) {

        }
    }

    override fun endContact(contact: Contact?) {

    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
        if (contact != null) {
            if (contact.fixtureA.body.userData == "bullet" || contact.fixtureB.body.userData == "bullet") {
                if (contact.fixtureA.body.userData == "bullet" && contact.fixtureB.body.userData == "bullet") {
                    contact.isEnabled = false
                }
                else {
                    apply {
                        val b = if (contact.fixtureA.body.userData == "bullet") (contact.fixtureA.userData as Bullet)
                        else (contact.fixtureB.userData as Bullet)
                        if (!b.contacted)
                            Main.assets.getSound("hit.wav")!!.play()
                    }
                    if (contact.fixtureA.body.userData == "player" || contact.fixtureB.body.userData == "player") {
                        val p = if (contact.fixtureA.body.userData == "player") (contact.fixtureA.userData as Player)
                        else (contact.fixtureB.userData as Player)
                        val b = if (contact.fixtureA.body.userData == "bullet") (contact.fixtureA.userData as Bullet)
                        else (contact.fixtureB.userData as Bullet)
                        if (p.hp > 0 && p.hitEffect <= 0f && !b.contacted) {
                            p.hp--
                            p.hitEffect = 0.5f

                            b.contacted = true
                            b.wall = false
                            contact.isEnabled = false
                        }
                        else
                            contact.isEnabled = false
                    }
                    if (contact.fixtureA.body.userData == "wall" || contact.fixtureB.body.userData == "wall") {
                        val b = if (contact.fixtureA.body.userData == "bullet") (contact.fixtureA.userData as Bullet)
                        else (contact.fixtureB.userData as Bullet)
                        if (!b.contacted) {
                            b.contacted = true
                            b.wall = true
                            contact.isEnabled = false
                        }
                        else
                            contact.isEnabled = false
                    }
                    if (contact.fixtureA.body.userData == "enemy" || contact.fixtureB.body.userData == "enemy") {
                        val p = if (contact.fixtureA.body.userData == "enemy") (contact.fixtureA.userData as Enemy)
                        else (contact.fixtureB.userData as Enemy)
                        val b = if (contact.fixtureA.body.userData == "bullet") (contact.fixtureA.userData as Bullet)
                        else (contact.fixtureB.userData as Bullet)
                        if (p.hp > 0 && p.hitEffect <= 0f && !b.contacted) {
                            p.hp--
                            p.hitEffect = 0.5f
                            if (p.hp == 0)
                                GameScreen.sc += 400

                            b.contacted = true
                            b.wall = false
                            contact.isEnabled = false
                        }
                        else
                            contact.isEnabled = false
                    }
                }
            } else if (contact.fixtureA.body.userData == "player" || contact.fixtureB.body.userData == "player") {
                if (contact.fixtureA.body.userData == "enemy" || contact.fixtureB.body.userData == "enemy") {
                    contact.isEnabled = false
                }
            } else if (contact.fixtureA.body.userData == "player" || contact.fixtureB.body.userData == "player") {
                if (contact.fixtureA.body.userData == "enemy" && contact.fixtureB.body.userData == "enemy") {
                    contact.isEnabled = false
                }
            }
        }
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {

    }
}