package fr.tommarx.ld38;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.Body;
import fr.tommarx.gameengine.Components.BoxBody;
import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.ParticleManager;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.AbstractGameObject;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Util.BloodParticle;

public class ET extends AbstractGameObject {

    Body body;
    Planet p;
    ParticleManager pm;

    public ET(Planet p, float angle) {
        super(new Transform(Game.center.cpy().add(new Vector2(2.5f + 1.5f * 1.2f / 2, 0).rotate(angle))));

        this.p = p;

        body = new BoxBody(this, 0.4f, 0.2f, BodyDef.BodyType.DynamicBody, false);
        addComponent(body);

        addComponent(new BoxRenderer(this, 0.4f, 0.2f, Color.RED));

        pm = new ParticleManager(this);
        addComponent(pm);

        Game.waitAndDo(1000, () -> {
            die();
            return false;
        });

    }

    public void die() {
        for (int i = 0; i < 20; i++) {
            pm.addParticle(new BloodParticle(getTransform().cpy()));
        }
        ((BoxRenderer)getComponentByClass("BoxRenderer")).setColor(new Color(0, 0, 0, 0));
    }

    protected void update(float delta) {
        body.getBody().setAwake(true);

        float deg = p.body.getBody().getPosition().cpy().sub(body.getBody().getPosition().cpy()).angle();
        body.getBody().setTransform(body.getBody().getPosition(), fr.tommarx.gameengine.Util.Math.DegreeToRadian(deg + 90));
    }
}
