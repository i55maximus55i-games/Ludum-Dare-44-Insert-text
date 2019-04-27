package ru.inserttext.old44

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold

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
                contact.isEnabled = false
            }
        }
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {

    }
}