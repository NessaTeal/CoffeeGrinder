package org.nixserds.coffeegrinder.screens;

import org.nixserds.coffeegrinder.CoffeeGrinder;
import org.nixserds.coffeegrinder.stages.GameStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

//Main screen of game
public class GameScreen implements Screen
{
	final CoffeeGrinder game;
	
	public GameStage gameStage;
	
	public GameScreen(CoffeeGrinder game)
	{
		this.game = game;
		
		gameStage = new GameStage(new ScreenViewport(game.camera), game.batch, game, this);
		
        Gdx.input.setInputProcessor(gameStage);
	}
	
	@Override
	public void show()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	//Clear screen and redraw stage
	//Also call function act of game that will call act in all objects of game
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.17f, 0.17f, 0.17f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    gameStage.draw();
	    gameStage.act();
	    game.act(delta);
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		gameStage.dispose();
	}

	
}
