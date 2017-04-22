package fr.tommarx.ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;

import fr.tommarx.gameengine.Collisions.CollisionsListener;
import fr.tommarx.gameengine.Collisions.CollisionsManager;
import fr.tommarx.gameengine.Components.AnimationManager;
import fr.tommarx.gameengine.Components.Body;
import fr.tommarx.gameengine.Components.BoxBody;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.AbstractGameObject;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.IO.Keys;
import fr.tommarx.gameengine.Util.Animation;
import fr.tommarx.gameengine.Util.Util;

public class Player extends AbstractGameObject{

    Planet p;
    SpriteRenderer renderer;
    Body body;
    float speed = 2, jump = 60;
    boolean canJump = false;
    AnimationManager am;

    public Player(Transform transform, Planet p) {
        super(transform);
        this.p = p;
        setTag("Player");
        renderer = new SpriteRenderer(this, Gdx.files.internal("player/stand.png"), 0, 0, 0.2f, 0.8f);
        body = new BoxBody(this, 0.2f, 0.8f, BodyDef.BodyType.DynamicBody, false);
        body.getBody().setFixedRotation(true);
        addComponent(renderer);
        am = new AnimationManager(this);
        am.addAnimation(new Animation(this, new Texture(Gdx.files.internal("player/walk.png")), 4, 1, 0.1f, true, 1));
        am.addAnimation(new Animation(this, new Texture(Gdx.files.internal("player/idle.png")), 1, 1, 0.1f, true, 2));
        am.addAnimation(new Animation(this, new Texture(Gdx.files.internal("player/jump.png")), 1, 1, 0.1f, true, 3));
        am.setCurrentAnimation(2);
        addComponent(am);
        addComponent(body);
        new CollisionsManager(new CollisionsListener() {
                public void collisionEnter(AbstractGameObject a, AbstractGameObject b, Contact contact) {
                         if (Util.areGameObjectsByTag(a, b, "Player", "Planet")) {
                             canJump = true;
                         }
                }

                public void collisionEnd(AbstractGameObject a, AbstractGameObject b, Contact contact) {

                }
        });
    }

    protected void update(float delta) {
        body.getBody().setAwake(true);

        float deg = p.body.getBody().getPosition().cpy().sub(body.getBody().getPosition().cpy()).angle();
        body.getBody().setTransform(body.getBody().getPosition(), fr.tommarx.gameengine.Util.Math.DegreeToRadian(deg + 90));

        

        if (am.getCurrentAnimation() != 1 && body.getBody().getLinearVelocity().len() > 0.1f) {
            am.setCurrentAnimation(1);
        }
        if (am.getCurrentAnimation() != 2 && body.getBody().getLinearVelocity().len() < 0.1f) {
            am.setCurrentAnimation(2);
        }
        if (canJump == false && am.getCurrentAnimation() != 3) {
            am.setCurrentAnimation(3);
        }

        if (Keys.isKeyPressed(Input.Keys.RIGHT)) {
            body.getBody().applyForceToCenter(p.body.getBody().getPosition().cpy().sub(body.getBody().getPosition().cpy()).nor().rotate(90).scl(speed), false);
            renderer.flip(false, false);
        }
        if (Keys.isKeyPressed(Input.Keys.LEFT)) {
            body.getBody().applyForceToCenter(p.body.getBody().getPosition().cpy().sub(body.getBody().getPosition().cpy()).nor().rotate(-90).scl(speed), false);
            renderer.flip(true, false);
        }
        if (Keys.isKeyPressed(Input.Keys.SPACE) && canJump) {
            body.getBody().applyForceToCenter(p.body.getBody().getPosition().cpy().sub(body.getBody().getPosition().cpy()).nor().rotate(180).scl(jump), false);
            canJump = false;
        }

    }
}
