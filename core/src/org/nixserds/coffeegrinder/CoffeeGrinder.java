package org.nixserds.coffeegrinder;

import org.nixserds.coffeegrinder.objects.IncomeObject;
import org.nixserds.coffeegrinder.parameters.Parameters;
import org.nixserds.coffeegrinder.screens.LoadingScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class CoffeeGrinder extends Game
{	
	public SpriteBatch batch;
	
	public BitmapFont font;
	
	public OrthographicCamera camera;
	
	public AssetManager manager;
	
	public Skin skin;
	
	public TextButtonStyle textButtonStyle;
	
	public LabelStyle labelStyle;

	public IncomeObject bushButton;
	public IncomeObject plantationButton;
	public IncomeObject farmButton;
	public IncomeObject cityButton;
	public IncomeObject islandButton;
	public IncomeObject javaButton;
	
	@Override
	public void create ()
	{
		//Get screen width and height
		Parameters.SCREEN_WIDTH = Gdx.graphics.getWidth();
		Parameters.SCREEN_HEIGHT = Gdx.graphics.getHeight();

		//Initialize variables
		batch = new SpriteBatch();
		font = new BitmapFont();
		camera = new OrthographicCamera();
		manager = new AssetManager();
		skin = new Skin();
		textButtonStyle = new TextButtonStyle();
		labelStyle = new LabelStyle();

		//Setup camera
		camera.update();
		camera.setToOrtho(false, Parameters.SCREEN_WIDTH, Parameters.SCREEN_HEIGHT);
		batch.setProjectionMatrix(camera.combined);

		//Turn on loading screen
		setScreen(new LoadingScreen(manager, this));
	}

	@Override
	public void render ()
	{
		super.render();
	}

	public void dispose()
	{
	    batch.dispose();
	    manager.dispose();
	}

	//Call function act in all income objects
	public void act(float delta)
	{
		bushButton.act(delta);
		plantationButton.act(delta);
		farmButton.act(delta);;
		cityButton.act(delta);
		islandButton.act(delta);;
		javaButton.act(delta);
	}

	//Save all parameters in shared preferences
	public void save()
	{
		Preferences prefs = Gdx.app.getPreferences("Gamedata");
		
		prefs.putString("money", Parameters.MONEY.toPlainString());
		
		prefs.putString("money_gain", Parameters.MONEY_GAIN.toPlainString());
		
		prefs.putString("bush_amount", bushButton.amount.toPlainString());
		prefs.putFloat("bush_speed", bushButton.progress_speed);
		prefs.putFloat("bush_progress", bushButton.progress);
		prefs.putString("bush_price", bushButton.price.toPlainString());
		prefs.putString("bush_gain", bushButton.gain.toPlainString());
		
		prefs.putString("plantation_amount", plantationButton.amount.toPlainString());
		prefs.putFloat("plantation_speed", plantationButton.progress_speed);
		prefs.putFloat("plantation_progress", plantationButton.progress);
		prefs.putString("plantation_price", plantationButton.price.toPlainString());
		prefs.putString("plantation_gain", plantationButton.gain.toPlainString());
		
		prefs.putString("farm_amount", farmButton.amount.toPlainString());
		prefs.putFloat("farm_speed", farmButton.progress_speed);
		prefs.putFloat("farm_progress", farmButton.progress);
		prefs.putString("farm_price", farmButton.price.toPlainString());
		prefs.putString("farm_gain", farmButton.gain.toPlainString());
		
		prefs.putString("city_amount", cityButton.amount.toPlainString());
		prefs.putFloat("city_speed", cityButton.progress_speed);
		prefs.putFloat("city_progress", cityButton.progress);
		prefs.putString("city_price", cityButton.price.toPlainString());
		prefs.putString("city_gain", cityButton.gain.toPlainString());
		
		prefs.putString("island_amount", islandButton.amount.toPlainString());
		prefs.putFloat("island_speed", islandButton.progress_speed);
		prefs.putFloat("island_progress", islandButton.progress);
		prefs.putString("island_price", islandButton.price.toPlainString());
		prefs.putString("island_gain", islandButton.gain.toPlainString());
		
		prefs.putString("java_amount", javaButton.amount.toPlainString());
		prefs.putFloat("java_speed", javaButton.progress_speed);
		prefs.putFloat("java_progress", javaButton.progress);
		prefs.putString("java_price", javaButton.price.toPlainString());
		prefs.putString("java_gain", javaButton.gain.toPlainString());
		
		prefs.putLong("upgrades", Parameters.UPGRADES);
		
		prefs.flush();
	}
}
