package fr.tommarx.ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import fr.tommarx.gameengine.Components.Transform;
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

    public void show() {
        background = new Texture("background.jpg");
        background2 = new Texture("background2.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("song.mp3"));
        music.setLooping(true);
        music.play();
        tree = new Texture("tree.png");
        world.setGravity(new Vector2(0, 0));
        p = new Planet();
        player = new Player(new Transform(new Vector2(Game.center.x - 2, 6f)), p);
        add(p);
        add(player);

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

    }

    public void update() {
        if (Keys.isKeyJustPressed(Input.Keys.D)) {
            Game.debugging = !Game.debugging;
        }
    }

}
