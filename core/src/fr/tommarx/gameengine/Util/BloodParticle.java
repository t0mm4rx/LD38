package fr.tommarx.gameengine.Util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.Body;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.Draw;

public class BloodParticle extends Particle{

    Body body;

    public BloodParticle(Transform transform) {
        super(transform, 1000);

        body = new CircleBody(this, 0.05f, BodyDef.BodyType.DynamicBody, false);
        addComponent(body);

        body.getBody().setLinearVelocity(Math.randomVector2(1));
        setLayout(10);
    }

    protected void update(float delta) {

    }

    public void render() {
        System.out.println("Draw");
        Draw.circle(getTransform().getPosition().x, getTransform().getPosition().y, 0.05f, Color.RED);
    }
}
