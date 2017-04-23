package fr.tommarx.ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.BoxBody;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.AbstractGameObject;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Util.Directions;

public class Bullet extends AbstractGameObject{

    BoxBody body;

    float power = 3;
    public boolean isDead = false;

    public Bullet(Transform transform, Planet p, Player player, Directions direction) {
        super(transform);

        body = new BoxBody(this, 0.1f, 0.05f, BodyDef.BodyType.DynamicBody, false);
        addComponent(body);

        float deg = p.body.getBody().getPosition().cpy().sub(player.body.getBody().getPosition().cpy()).angle();
        body.getBody().setTransform(body.getBody().getPosition(), fr.tommarx.gameengine.Util.Math.DegreeToRadian(deg + 90 + 180));

        setTag("Bullet");

        if (direction == Directions.LEFT) {
            body.getBody().setTransform(body.getBody().getPosition().add(p.body.getBody().getPosition().cpy().sub(player.body.getBody().getPosition().cpy()).rotate(-70).nor().scl(0.1f)), body.getBody().getAngle());
            body.getBody().applyForceToCenter(p.body.getBody().getPosition().cpy().sub(player.body.getBody().getPosition().cpy()).nor().rotate(-70).scl(power), false);
        }

        if (direction == Directions.RIGHT) {
            body.getBody().setTransform(body.getBody().getPosition().add(p.body.getBody().getPosition().cpy().sub(player.body.getBody().getPosition().cpy()).rotate(70).nor().scl(0.1f)), body.getBody().getAngle());
            body.getBody().applyForceToCenter(p.body.getBody().getPosition().cpy().sub(player.body.getBody().getPosition().cpy()).nor().rotate(70).scl(power), false);
        }

        addComponent(new SpriteRenderer(this, Gdx.files.internal("player/bullet.png"), 0, 0, 0.1f, 0.05f));

    }

    protected void update(float delta) {

        if (isDead) {
            Game.getCurrentScreen().remove(this);
        }

    }
}
