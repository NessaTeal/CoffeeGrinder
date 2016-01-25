package org.nixserds.coffeegrinder.stages;

import org.nixserds.coffeegrinder.CoffeeGrinder;
import org.nixserds.coffeegrinder.objects.MoneyCounter;
import org.nixserds.coffeegrinder.parameters.Parameters;
import org.nixserds.coffeegrinder.screens.GameScreen;
import org.nixserds.coffeegrinder.screens.UpgradesScreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

//Stage where income objects are placed
public class GameStage extends Stage
{
	final CoffeeGrinder game;
	final GameScreen screen;
	
	TextButton play;
	TextButton save;
	TextButton upgrades;
	
	MoneyCounter money;
	
	Table table;
	
	
	public GameStage(Viewport viewport, Batch batch, CoffeeGrinder gam, GameScreen scren)
	{
		//Initialize from parent
		super(viewport, batch);
		
		game = gam;
		screen = scren;

		//Place money counter
        money = new MoneyCounter(Parameters.MONEY.toBigInteger().toString(), game.labelStyle);

		//Create button and table with income objects
        createButtons();
        createTable();
	}

	//Create save button, button to upgrades screen and button to get money manually
	private void createButtons()
	{
		save = new TextButton("Save", game.textButtonStyle);
	    save.addListener(new ClickListener()
	    {
	    	@Override
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
	        {
	            return true;
	        }
	        @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
	        	game.save();
            }
	    });
	    
		upgrades = new TextButton("Upgrades", game.textButtonStyle);
	    upgrades.addListener(new ClickListener()
	    {
	    	@Override
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
	        {
	            return true;
	        }
	        @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
	        	game.setScreen(new UpgradesScreen(game, true));
            }
	    });
	    
		play = new TextButton("GET MONEY", game.textButtonStyle);
	    play.addListener(new ClickListener()
	    {
	        @Override
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
	        {
	            return true;
	        }
	        @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
	            Parameters.MONEY = Parameters.MONEY.add(Parameters.MONEY_GAIN);
            }
	    });
	}

	//Create and set all elements in table
	private void createTable()
	{
		table = new Table();
        table.setFillParent(true);

        table.add(money).width(getWidth() * 3 / 10).height(Parameters.SCREEN_HEIGHT / 10);
        table.add(save).width(getWidth() * 3 / 10);
        table.add(upgrades).width(getWidth() * 3 / 10);
        table.row();
        table.add(play).colspan(3).expandY().width(Parameters.SCREEN_WIDTH);
        table.row().padBottom(Parameters.SCREEN_HEIGHT / 30);
        table.add(game.bushButton).width(getWidth() * 3 / 11).height(Parameters.SCREEN_HEIGHT / 5);
        table.add(game.plantationButton).width(getWidth() * 3 / 11).height(Parameters.SCREEN_HEIGHT / 5);
        table.add(game.farmButton).width(getWidth() * 3 / 11).height(Parameters.SCREEN_HEIGHT / 5);
        table.row().padBottom(Parameters.SCREEN_HEIGHT / 60);
        table.add(game.cityButton).width(getWidth() * 3 / 11).height(Parameters.SCREEN_HEIGHT / 5);
        table.add(game.islandButton).width(getWidth() * 3 / 11).height(Parameters.SCREEN_HEIGHT / 5);
        table.add(game.javaButton).width(getWidth() * 3 / 11).height(Parameters.SCREEN_HEIGHT / 5);
        
        addActor(table);
	}
}
