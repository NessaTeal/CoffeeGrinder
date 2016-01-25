package org.nixserds.coffeegrinder.screens;

import java.math.BigDecimal;

import org.nixserds.coffeegrinder.CoffeeGrinder;
import org.nixserds.coffeegrinder.objects.IncomeObject;
import org.nixserds.coffeegrinder.parameters.Parameters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

//First called screen that prepares game
public class LoadingScreen implements Screen
{
	final CoffeeGrinder game;
	
	public AssetManager manager;
	
	public LoadingScreen(AssetManager manager, CoffeeGrinder game)
	{
		//Assign game and loading manager
		this.game = game;
		this.manager = manager;

		//Load all resources
		manager.load("images/game/images.atlas", TextureAtlas.class);
	}
	
	private void load()
	{
		//All game data is saved inside shared preferences
		//If there is some value then load it
		//Else assign default that written on the right
		Preferences prefs = Gdx.app.getPreferences("Gamedata");
		
		Parameters.MONEY = new BigDecimal(prefs.getString("money", "0"));
		
		Parameters.MONEY_GAIN = new BigDecimal(prefs.getString("money_gain", "1"));
		
		Parameters.BUSH_AMOUNT = prefs.getString("bush_amount", "0");
		Parameters.BUSH_SPEED = prefs.getFloat("bush_speed", 1f);
		Parameters.BUSH_PROGRESS = prefs.getFloat("bush_progress", 0f);
		Parameters.BUSH_PRICE = prefs.getString("bush_price", "10");
		Parameters.BUSH_GAIN = prefs.getString("bush_gain", "1");
		
		Parameters.PLANTATION_AMOUNT = prefs.getString("plantation_amount", "0");
		Parameters.PLANTATION_SPEED = prefs.getFloat("plantation_speed", 1/15f);
		Parameters.PLANTATION_PROGRESS = prefs.getFloat("plantation_progress", 0f);
		Parameters.PLANTATION_PRICE = prefs.getString("plantation_price", "200");
		Parameters.PLANTATION_GAIN = prefs.getString("plantation_gain", "30");
		
		Parameters.FARM_AMOUNT = prefs.getString("farm_amount", "0");
		Parameters.FARM_SPEED = prefs.getFloat("farm_speed", 1/100f);
		Parameters.FARM_PROGRESS = prefs.getFloat("farm_progress", 0f);
		Parameters.FARM_PRICE = prefs.getString("farm_price", "3000");
		Parameters.FARM_GAIN = prefs.getString("farm_gain", "300");
		
		Parameters.CITY_AMOUNT = prefs.getString("city_amount", "0");
		Parameters.CITY_SPEED = prefs.getFloat("city_speed", 1/400f);
		Parameters.CITY_PROGRESS = prefs.getFloat("city_progress", 0f);
		Parameters.CITY_PRICE = prefs.getString("city_price", "40000");
		Parameters.CITY_GAIN = prefs.getString("city_gain", "1600");
		
		Parameters.ISLAND_AMOUNT = prefs.getString("island_amount", "0");
		Parameters.ISLAND_SPEED = prefs.getFloat("island_speed", 1/1600f);
		Parameters.ISLAND_PROGRESS = prefs.getFloat("island_progress", 0f);
		Parameters.ISLAND_PRICE = prefs.getString("island_price", "500000");
		Parameters.ISLAND_GAIN = prefs.getString("island_gain", "8000");
		
		Parameters.JAVA_AMOUNT = prefs.getString("java_amount", "0");
		Parameters.JAVA_SPEED = prefs.getFloat("java_speed", 1/6400f);
		Parameters.JAVA_PROGRESS = prefs.getFloat("java_progress", 0f);
		Parameters.JAVA_PRICE = prefs.getString("java_price", "6000000");
		Parameters.JAVA_GAIN = prefs.getString("java_gain", "38400");
		
		Parameters.UPGRADES = prefs.getLong("upgrades", 0);
	}
	
	private void setup()
	{
		//Load textures saved in one batch and crop them
		TextureAtlas atlas = game.manager.get("images/game/images.atlas", TextureAtlas.class);
		game.skin.addRegions(atlas);
		game.textButtonStyle.up = game.skin.getDrawable("button-up");
		game.textButtonStyle.down = game.skin.getDrawable("button-down");
		game.textButtonStyle.checked = game.skin.getDrawable("button-up");

		//Set fonts
        game.labelStyle.font = game.font;
		game.textButtonStyle.font = game.font;
	}
	@Override
	public void show()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	//There are no graphics yet
	//There would be progress bar
	public void render(float delta)
	{
		//Clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//If manager loaded everything
		if(manager.update())
		{

			//Load preferences
			load();

			//Crop textures
			setup();

			//Create income objects
			createIncomeObjects();

			//Set main game screen
			game.setScreen(new GameScreen(game));
		}
	}

	private void createIncomeObjects()
	{
		game.bushButton = new IncomeObject(game, "Bush", new BigDecimal(Parameters.BUSH_AMOUNT),
        		new BigDecimal(Parameters.BUSH_PRICE), 1.12f, new BigDecimal(Parameters.BUSH_GAIN), 
        		Parameters.BUSH_PROGRESS, Parameters.BUSH_SPEED, game.skin);
        
		game.plantationButton = new IncomeObject(game, "Plantation", new BigDecimal(Parameters.PLANTATION_AMOUNT),
				new BigDecimal(Parameters.PLANTATION_PRICE), 1.15f, new BigDecimal(Parameters.PLANTATION_GAIN), 
        		Parameters.PLANTATION_PROGRESS, Parameters.PLANTATION_SPEED, game.skin);
        
		game.farmButton = new IncomeObject(game, "Farm", new BigDecimal(Parameters.FARM_AMOUNT),
				new BigDecimal(Parameters.FARM_PRICE), 1.14f, new BigDecimal(Parameters.FARM_GAIN), 
        		Parameters.FARM_PROGRESS, Parameters.FARM_SPEED, game.skin);
        
		game.cityButton = new IncomeObject(game, "City", new BigDecimal(Parameters.CITY_AMOUNT),
				new BigDecimal(Parameters.CITY_PRICE), 1.13f, new BigDecimal(Parameters.CITY_GAIN), 
        		Parameters.CITY_PROGRESS, Parameters.CITY_SPEED, game.skin);
        
		game.islandButton = new IncomeObject(game, "Java Island", new BigDecimal(Parameters.ISLAND_AMOUNT),
				new BigDecimal(Parameters.ISLAND_PRICE), 1.12f, new BigDecimal(Parameters.ISLAND_GAIN), 
        		Parameters.ISLAND_PROGRESS, Parameters.ISLAND_SPEED, game.skin);
        
		game.javaButton = new IncomeObject(game, "Java Machine", new BigDecimal(Parameters.JAVA_AMOUNT),
				new BigDecimal(Parameters.JAVA_PRICE), 1.11f, new BigDecimal(Parameters.JAVA_GAIN), 
        		Parameters.JAVA_PROGRESS, Parameters.JAVA_SPEED, game.skin);
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
