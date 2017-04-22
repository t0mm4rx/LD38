package fr.tommarx.ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.Body;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.AbstractGameObject;
import fr.tommarx.gameengine.Game.Game;

public class Planet extends AbstractGameObject {

    public Body body;

    public Planet() {
        super(new Transform(Game.center));
        addComponent(new SpriteRenderer(this, Gdx.files.internal("planet.png")));
        body = new CircleBody(this, 2.5f, BodyDef.BodyType.StaticBody, false);
        setTag("Planet");
    }

    protected void update(float delta) {

    }
}
