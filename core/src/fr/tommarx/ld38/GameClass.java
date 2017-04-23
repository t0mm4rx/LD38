package fr.tommarx.ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Util.Util;

public class GameClass extends Game {

	static BitmapFont font20, font30;
	static GlyphLayout glyphLayout;

	public static String TITLE = "Defending my small world";

	public void init() {
		glyphLayout = new GlyphLayout();
		font20 = Util.ttfToBitmap(Gdx.files.internal("font.ttf"), 30);
		font30 = Util.ttfToBitmap(Gdx.files.internal("font.ttf"), 45);

		setScreen(new GameScreen(this));

	}
}
