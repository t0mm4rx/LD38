package fr.tommarx.ld38;

import com.badlogic.gdx.graphics.Color;

import fr.tommarx.gameengine.Game.Draw;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.Screen;

public class SplashScreen extends Screen {
    public SplashScreen(Game game) {
        super(game);
    }

    public void show() {
        fadeIn(3);
        Game.waitAndDo(3000, () -> {
            fadeOut(3);
            return false;
        });
        Game.waitAndDo(6000, () -> {
            setScreen(new GameScreen(this.game));
           return false;
        });
    }

    public void renderBefore() {

    }

    public void renderAfter() {
        GameClass.glyphLayout.setText(GameClass.font30, GameClass.TITLE);
        Draw.text(GameClass.TITLE, Game.center.x - GameClass.glyphLayout.width / 2 / 100, Game.center.y - GameClass.glyphLayout.height / 2 / 100, Color.WHITE, GameClass.font30, GameClass.glyphLayout);
        GameClass.glyphLayout.setText(GameClass.font20, "-- by t0m --");
        Draw.text("-- by t0m --", Game.center.x - GameClass.glyphLayout.width / 2 / 100, Game.center.y - GameClass.glyphLayout.height / 2 / 100 - 0.5f, Color.WHITE, GameClass.font20, GameClass.glyphLayout);
    }

    public void update() {

    }

}
