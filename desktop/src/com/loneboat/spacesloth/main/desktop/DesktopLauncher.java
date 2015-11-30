package com.loneboat.spacesloth.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.loneboat.spacesloth.main.SpaceSloth;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "SpaceSloth: Deep Space";
		config.width = 1366;
		config.height = 768;
		new LwjglApplication(new SpaceSloth(), config);
	}
}
