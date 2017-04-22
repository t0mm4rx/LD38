package fr.tommarx.ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Util.Util;

public class GameClass extends Game {

	static BitmapFont font20, font30;
	static GlyphLayout glyphLayout;

	public void init() {
		glyphLayout = new GlyphLayout();
		font20 = Util.ttfToBitmap(Gdx.files.internal("font.otf"), 20);
		font30 = Util.ttfToBitmap(Gdx.files.internal("font_bold.otf"), 30);

		setScreen(new GameScreen(this));
	}
}
