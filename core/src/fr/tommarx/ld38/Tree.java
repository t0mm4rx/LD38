package fr.tommarx.ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.AbstractGameObject;
import fr.tommarx.gameengine.Game.Game;

public class Tree extends AbstractGameObject{

    public Tree(float angle, Planet p) {
        super(new Transform(Game.center.cpy().add(new Vector2(2.5f + 1.5f * 1.2f / 2, 0).rotate(angle))));
        addComponent(new SpriteRenderer(this, Gdx.files.internal("tree.png"), 0, 0, 1f * 1.2f, 1.5f * 1.2f));
        getTransform().setRotation(p.body.getBody().getPosition().cpy().sub(getTransform().getPosition().cpy()).angle() + 90);
    }

    protected void update(float delta) {

    }
}
