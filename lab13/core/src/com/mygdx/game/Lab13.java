package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Lab13 extends Game {
	SpriteBatch batch;
	Texture img;

	@Override
	public void create() {

		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		setScreen(new MainGameScreen());
	}

    /*@Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }*/
}
