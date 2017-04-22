package fr.tommarx.ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.Body;
import fr.tommarx.gameengine.Components.BoxBody;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.AbstractGameObject;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;

public class Planet extends AbstractGameObject {

    public Body body;

    public Planet() {
        super(new Transform(Game.center));
        addComponent(new SpriteRenderer(this, Gdx.files.internal("planet.png")));
        body = new CircleBody(this, 2.5f, BodyDef.BodyType.StaticBody, false);
        addComponent(body);
        setTag("Planet");
    }

    protected void update(float delta) {
        for (AbstractGameObject a : Game.getCurrentScreen().getGameObjects()) {
            if (a.getComponentByClass("BoxBody") != null) {
                BoxBody other = ((BoxBody)a.getComponentByClass("BoxBody"));
                other.getBody().applyForceToCenter(body.getBody().getPosition().cpy().sub(other.getBody().getPosition().cpy()).nor().scl(3), false);
            }
            if (a.getComponentByClass("CircleBody") != null) {
                CircleBody other = ((CircleBody)a.getComponentByClass("CircleBody"));
                other.getBody().applyForceToCenter(body.getBody().getPosition().cpy().sub(other.getBody().getPosition().cpy()).nor().scl(3), false);
            }
        }
    }
}
