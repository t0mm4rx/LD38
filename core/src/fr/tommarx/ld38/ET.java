package fr.tommarx.ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.AnimationManager;
import fr.tommarx.gameengine.Components.Body;
import fr.tommarx.gameengine.Components.BoxBody;
import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.ParticleManager;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.AbstractGameObject;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Util.Animation;
import fr.tommarx.gameengine.Util.BloodParticle;

public class ET extends AbstractGameObject {

    Body body;
    Planet p;
    ParticleManager pm;
    AnimationManager am;
    SpriteRenderer renderer;
    float speed = 1;
    public int life = 10;
    boolean isHurted = false, isDead = false;

    public ET(Planet p, float angle, float speed, int life) {
        super(new Transform(Game.center.cpy().add(new Vector2(2.5f + 1.5f * 1.2f / 2, 0).rotate(angle))));

        this.p = p;

        this.speed = speed;
        this.life = life;

        setTag("ET");

        body = new BoxBody(this, 0.2f, 0.8f, BodyDef.BodyType.DynamicBody, false);
        addComponent(body);

        pm = new ParticleManager(this);
        addComponent(pm);

        renderer = new SpriteRenderer(this, Gdx.files.internal("et/4.png"), 0, 0, 0.2f, 0.8f);
        addComponent(renderer);

        am = new AnimationManager(this);
        am.addAnimation(new Animation(this, new Texture(Gdx.files.internal("et/walk.png")), 4, 1, 0.2f, true, 1));
        am.setCurrentAnimation(1);
        addComponent(am);

    }

    public void hurt() {
        life--;
        isHurted = true;
        if (life <= 0) {
            die();
        }
    }

    public void die() {
        isDead = true;
        ((GameScreen) Game.getCurrentScreen()).killed++;
    }

    public void goLeft() {
        body.getBody().applyForceToCenter(p.body.getBody().getPosition().cpy().sub(body.getBody().getPosition().cpy()).nor().rotate(-90).scl(speed), false);
        renderer.flip(true, false);
    }

    public void goRight() {
        body.getBody().applyForceToCenter(p.body.getBody().getPosition().cpy().sub(body.getBody().getPosition().cpy()).nor().rotate(90).scl(speed), false);
        renderer.flip(false, false);
    }

    protected void update(float delta) {
        body.getBody().setAwake(true);

        float deg = p.body.getBody().getPosition().cpy().sub(body.getBody().getPosition().cpy()).angle();
        body.getBody().setTransform(body.getBody().getPosition(), fr.tommarx.gameengine.Util.Math.DegreeToRadian(deg + 90));

        Player p = ((Player)Game.getCurrentScreen().getGameObjectByTag("Player"));
        goLeft();

        if (isHurted) {
            for (int i = 0; i < 6; i++) {
                pm.addParticle(new BloodParticle(getTransform().cpy()));
            }
            isHurted = false;
        }
        if (isDead) {
            Game.getCurrentScreen().remove(this);
        }
    }
}
