package fr.tommarx.ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Easing.Tween;
import fr.tommarx.gameengine.Easing.TweenListener;
import fr.tommarx.gameengine.Game.Draw;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.Screen;
import fr.tommarx.gameengine.IO.Keys;

public class GameScreen extends Screen{

    public GameScreen(Game game) {
        super(game);
    }

    Texture background, background2, tree;
    Planet p;
    Player player;
    Music music;
    int wave = 1, killed = 0;
    boolean needInstantiate = true;
    float a = 0;
    int b = 0;

    public void show() {
        background = new Texture("background.jpg");
        background2 = new Texture("background2.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("song.mp3"));
        music.setLooping(true);
        music.play();
        tree = new Texture("tree.png");
        world.setGravity(new Vector2(0, 0));
        p = new Planet();

        add(p);
        player = new Player(new Transform(new Vector2(Game.center.x - 2, 6f)), p);
        add(player);

        Game.waitAndDo(11000, () -> {
            for (int i = 0; i < 5; i++) {
                add(new ET(p, fr.tommarx.gameengine.Util.Math.randomInt(0, 360), 1, 10));
            }
            return false;
        });

        add(new Tree(200, p));
        add(new Tree(85, p));
        add(new Tree(300, p));
    }

    public void renderBefore() {

        for (int i = 3; i < 15; i+= 3) {
            Draw.texture(background2, i, background2.getHeight() / 100 / 2 - 0.5f);
            Draw.texture(background2, i, background2.getHeight() / 100 / 2 * 2);
            Draw.texture(background2, i, background2.getHeight() / 100 / 2 * 3);
            Draw.texture(background2, i, background2.getHeight() / 100 / 2 * 4);
            Draw.texture(background2, i, background2.getHeight() / 100 / 2 * 5);
        }


        Draw.texture(background, background.getWidth() / 100 / 2, background.getHeight() / 100 / 2 - 0.5f);
        Draw.texture(background, background.getWidth() / 100 / 2, background.getHeight() / 100 / 2 * 2);
        Draw.texture(background, background.getWidth() / 100 / 2, background.getHeight() / 100 / 2 * 3);
        Draw.texture(background, background.getWidth() / 100 / 2, background.getHeight() / 100 / 2 * 4);
        Draw.texture(background, background.getWidth() / 100 / 2, background.getHeight() / 100 / 2 * 5);

    }

    public void renderAfter() {
        GameClass.glyphLayout.setText(GameClass.font20, "X to use the gun");
        Draw.text("X to use the gun", Game.center.x + 3, Game.center.y + 0.1f, Color.BLACK, GameClass.font20, GameClass.glyphLayout);
        GameClass.glyphLayout.setText(GameClass.font20, "Space to jump");
        Draw.text("Space to jump", Game.center.x + 3, Game.center.y - 0.1f, Color.BLACK, GameClass.font20, GameClass.glyphLayout);

        GameClass.glyphLayout.setText(GameClass.font20, player.life + " / " + player.totalLifes);
        Draw.text(player.life + " / " + player.totalLifes, 0.3f, Game.center.y * 2 + 0.3f, Color.BLACK, GameClass.font20, GameClass.glyphLayout);

        GameClass.glyphLayout.setText(GameClass.font20, killed + " kills");
        Draw.text(killed + " kills", 0.3f, Game.center.y * 2, Color.BLACK, GameClass.font20, GameClass.glyphLayout);

        GameClass.glyphLayout.setText(GameClass.font30, "New weapon unlocked !");
        Draw.text("New weapon unlocked !", Game.center.x - GameClass.glyphLayout.width / 2 / 100, Game.center.y, new Color(.1f, .8f, .1f, a), GameClass.font30, GameClass.glyphLayout);

    }

    public void update() {
        if (Keys.isKeyJustPressed(Input.Keys.D)) {
            Game.debugging = !Game.debugging;
        }
        Game.debug(1, "FPS : " + Gdx.graphics.getFramesPerSecond());


        /**** Waves *****/

        if (needInstantiate) {
            switch (wave) {
                case 1:
                    for (int i = 0; i < 3; i++) {
                        add(new ET(p, fr.tommarx.gameengine.Util.Math.randomInt(0, 360), 1, 10));
                    }
                    break;
                case 2:
                    for (int i = 0; i < 2; i++) {
                        add(new ET(p, fr.tommarx.gameengine.Util.Math.randomInt(0, 360), 1, 25));
                    }
                    break;
                case 3:
                    add(new ET(p, fr.tommarx.gameengine.Util.Math.randomInt(0, 360), 2.5f, 10));
                    break;
                case 4:
                    for (int i = 0; i < 3; i++) {
                        add(new ET(p, fr.tommarx.gameengine.Util.Math.randomInt(0, 360), 1f, 50));
                    }
                    break;
                case 5:
                    for (int i = 0; i < 2; i++) {
                        add(new ET(p, fr.tommarx.gameengine.Util.Math.randomInt(0, 360), 2f, 15));
                    }
                    break;
                case 6:
                    if (player.gun == 0) {
                        new Tween(Tween.LINEAR_EASE_INOUT, 0.5f, 0f, true, new TweenListener() {
                            public void onValueChanged(float v) {
                                if (b < 5) {
                                    if (v > .99f){
                                        b++;
                                    }
                                    if (v > .5f) {
                                        a = 1f;
                                    } else {
                                        a = 0f;
                                    }
                                } else {
                                    a = 0f;
                                }
                            }

                            public void onFinished() {

                            }
                        });
                    }
                    player.gun = 3;
                    player.fireRate = 5;
                    player.life += player.life / 4;
                    if (player.life > player.totalLifes) {
                        player.life = player.totalLifes;
                    }
                    for (int i = 0; i < 10; i++) {
                        add(new ET(p, fr.tommarx.gameengine.Util.Math.randomInt(0, 360), 0.7f, 12));
                    }
                    break;
                default:
                    wave -= 4;
            }
            needInstantiate = false;
        }

        if (getGameObjectsByTag("ET").size() == 0) {
            wave++;
            needInstantiate = true;
        }


    }

}
