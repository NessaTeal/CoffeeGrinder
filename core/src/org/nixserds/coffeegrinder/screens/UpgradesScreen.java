package org.nixserds.coffeegrinder.screens;

import org.nixserds.coffeegrinder.CoffeeGrinder;
import org.nixserds.coffeegrinder.stages.UpgradesStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

//Screen that holds upgrades
public class UpgradesScreen implements Screen
{
	final CoffeeGrinder game;

	public UpgradesStage upgradesStage;
	
	public UpgradesScreen(CoffeeGrinder gam, boolean touchable)
	{
		game = gam;
		
		upgradesStage = new UpgradesStage(new ScreenViewport(game.camera), game.batch, game, this, touchable);
		
		Gdx.input.setInputProcessor(upgradesStage);
	}
	
	@Override
	public void show()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	//Redraw screen and don't forget to still call act on income objects that game won't pause
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.17f, 0.17f, 0.17f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    upgradesStage.draw();
	    upgradesStage.act();
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
		// TODO Auto-generated method stub
		
	}

}
