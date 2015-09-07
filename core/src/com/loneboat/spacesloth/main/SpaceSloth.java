package com.loneboat.spacesloth.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.screens.utilscreens.LoadingScreen;

/**
 * Created by Dubpub, inspirited by friends.
 */
public class SpaceSloth extends Game {
	// Create our asset manager.
	public ContentHandler chandle;
	public AssetManager manager;

	// Create our debug logger.
	private Logger logger;

	@Override
	public void create () {
		logger = new Logger("SpaceSloth:Terminal");
		logger.setLevel(Logger.DEBUG);
		logger.info("System started up successfully.");
		chandle = new ContentHandler(this);
		manager = chandle.getManager();

		setCurrentScreen(new LoadingScreen(this, chandle));
	}

	@Override
	public void render () {
		super.render();
	}

	public ContentHandler getChandle() {
		return chandle;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setCurrentScreen(Screen screen) {
		logger.info("Switching screens...");
		setScreen(screen);
	}

}
