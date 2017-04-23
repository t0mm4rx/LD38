package fr.tommarx.ld38;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import fr.tommarx.gameengine.Game.Draw;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.Screen;
import fr.tommarx.gameengine.IO.Keys;

public class GameOverScreen extends Screen {
    public GameOverScreen(Game game) {
        super(game);
    }
    Texture background, background2;

    public void show() {
        background = new Texture("background.jpg");
        background2 = new Texture("background2.png");
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
        GameClass.glyphLayout.setText(GameClass.font30, "Game is over !");
        Draw.text("Game is over !", Game.center.x - GameClass.glyphLayout.width / 2 / 100, Game.center.y - GameClass.glyphLayout.height / 2 / 100 + 0.3f, Color.BLACK, GameClass.font30, GameClass.glyphLayout);
        GameClass.glyphLayout.setText(GameClass.font20, "Press space to restart...");
        Draw.text("Press space to restart...", Game.center.x - GameClass.glyphLayout.width / 2 / 100, Game.center.y - GameClass.glyphLayout.height / 2 / 100 - 0.2f, Color.BLACK, GameClass.font20, GameClass.glyphLayout);
    }

    public void update() {
        if (Keys.isKeyJustPressed(Input.Keys.SPACE)) {
            setScreen(new GameScreen(game));
        }
    }

}
