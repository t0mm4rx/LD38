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
import fr.tommarx.gameengine.Easing.Tween;
import fr.tommarx.gameengine.Easing.TweenListener;
import fr.tommarx.gameengine.Game.AbstractGameObject;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.IO.Keys;
import fr.tommarx.gameengine.Util.Animation;
import fr.tommarx.gameengine.Util.Directions;
import fr.tommarx.gameengine.Util.Util;

public class Player extends AbstractGameObject{

    Planet p;
    SpriteRenderer renderer;
    Body body;
    float speed = 2, jump = 70;
    public int life = 20, totalLifes = 20;
    boolean canJump = false, isDead = false;
    AnimationManager am;
    Directions dir;
    public int gun = 0, fireRate = 100, lastFire = 100;

    public Player(Transform transform, Planet p) {
        super(transform);
        this.p = p;
        setTag("Player");
        renderer = new SpriteRenderer(this, Gdx.files.internal("player/idle_gun.png"), 0, 0, 0.4f, 0.8f);
        body = new BoxBody(this, 0.2f, 0.8f, BodyDef.BodyType.DynamicBody, false);
        body.getBody().setFixedRotation(true);
        addComponent(renderer);
        am = new AnimationManager(this);
        am.addAnimation(new Animation(this, new Texture(Gdx.files.internal("player/walk.png")), 4, 1, 0.1f, true, 10));
        am.addAnimation(new Animation(this, new Texture(Gdx.files.internal("player/walk_gun.png")), 4, 1, 0.1f, true, 1));
        am.addAnimation(new Animation(this, new Texture(Gdx.files.internal("player/idle_gun.png")), 1, 1, 0.1f, true, 2));
        am.addAnimation(new Animation(this, new Texture(Gdx.files.internal("player/jump.png")), 1, 1, 0.1f, true, 3));
        am.addAnimation(new Animation(this, new Texture(Gdx.files.internal("player/walk_ak.png")), 4, 1, 0.1f, true, 4));
        am.addAnimation(new Animation(this, new Texture(Gdx.files.internal("player/idle_ak.png")), 1, 1, 0.1f, true, 5));
        am.addAnimation(new Animation(this, new Texture(Gdx.files.internal("player/jump_ak.png")), 1, 1, 0.1f, true, 6));
        am.setCurrentAnimation(2);
        addComponent(am);
        addComponent(body);
        new CollisionsManager(new CollisionsListener() {
                public void collisionEnter(AbstractGameObject a, AbstractGameObject b, Contact contact) {
                    if (Util.areGameObjectsByTag(a, b, "Player", "Planet")) {canJump = true;}
                    if (a.getTag().equals("Bullet") && b.getTag().equals("ET")) {
                        ((ET) b).hurt();
                        ((Bullet) a).isDead = true;
                    }
                    if (b.getTag().equals("Bullet") && a.getTag().equals("ET")) {
                        ((ET) a).hurt();
                        ((Bullet) b).isDead = true;
                    }
                    if (Util.areGameObjectsByTag(a, b, "Player", "ET")) {
                        Game.getCurrentScreen().shake(0.1f, 500);
                        hurt();
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


        if (lastFire < fireRate) {
            lastFire++;
        }


        if (am.getCurrentAnimation() != 1 + gun && body.getBody().getLinearVelocity().len() > 0.1f) {
            am.setCurrentAnimation(1 + gun);
        }
        if (am.getCurrentAnimation() != 2 + gun && body.getBody().getLinearVelocity().len() < 0.1f) {
            am.setCurrentAnimation(2 + gun);
        }
        if (canJump == false && am.getCurrentAnimation() != 3 + gun) {
            am.setCurrentAnimation(3 + gun);
        }

        if (Keys.isKeyPressed(Input.Keys.RIGHT)) {
            body.getBody().applyForceToCenter(p.body.getBody().getPosition().cpy().sub(body.getBody().getPosition().cpy()).nor().rotate(90).scl(speed), false);
            dir = Directions.RIGHT;
        }
        if (Keys.isKeyPressed(Input.Keys.LEFT)) {
            body.getBody().applyForceToCenter(p.body.getBody().getPosition().cpy().sub(body.getBody().getPosition().cpy()).nor().rotate(-90).scl(speed), false);
            dir = Directions.LEFT;
        }
        if (Keys.isKeyPressed(Input.Keys.SPACE) && canJump) {
            body.getBody().applyForceToCenter(p.body.getBody().getPosition().cpy().sub(body.getBody().getPosition().cpy()).nor().rotate(180).scl(jump), false);
            canJump = false;
        }
        if (dir == Directions.LEFT) {
            renderer.flip(true, false);
        } else {
            renderer.flip(false, false);
        }
        if (fireRate == 100) {
            if (Keys.isKeyJustPressed(Input.Keys.X)) {
                fire();
            }
        } else {
            if (Keys.isKeyPressed(Input.Keys.X)) {
                if (lastFire >= fireRate) {
                    fire();
                }
            }
        }

    }

    public void fire() {

                lastFire = 0;
                Game.getCurrentScreen().add(new Bullet(new Transform(getTransform().getPosition().cpy()), p, this, dir));
                if (dir == Directions.LEFT) {
                    body.getBody().applyForceToCenter(body.getBody().getPosition().cpy().sub(p.body.getBody().getPosition().cpy().sub(body.getBody().getPosition().cpy()).nor().rotate(-90).scl(4)), false);
                } else {
                    body.getBody().applyForceToCenter(body.getBody().getPosition().cpy().sub(p.body.getBody().getPosition().cpy().sub(body.getBody().getPosition().cpy()).nor().rotate(90).scl(6)), false);
                }


    }

    public void hurt() {
        life--;
        if (life <= 0) {
            die();
        }
    }

    public void die() {
        if (isDead)
            return;
        isDead = true;
        new Tween(Tween.LINEAR_EASE_INOUT, 1f, 0f, false, new TweenListener() {
            public void onValueChanged(float v) {
                ((GameScreen) Game.getCurrentScreen()).music.setVolume(1 - v);
            }

            public void onFinished() {
                ((GameScreen) Game.getCurrentScreen()).music.stop();
            }
        });
        Game.getCurrentScreen().fadeOut(1);
        Game.waitAndDo(1000, () -> {
            Game.getCurrentScreen().setScreen(new GameOverScreen(Game.getCurrentScreen().game));
            return false;
        });

    }
}
