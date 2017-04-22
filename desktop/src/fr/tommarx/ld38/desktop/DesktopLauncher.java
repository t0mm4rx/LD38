package fr.tommarx.ld38.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.tommarx.ld38.GameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Ludum Dare 38 - A small world - by t0m";
		final int SIZE = 80;
		config.width = 16 * SIZE;
		config.height = 9 * SIZE;
		new LwjglApplication(new GameClass(), config);
	}
}
