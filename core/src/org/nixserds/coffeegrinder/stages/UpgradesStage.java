package org.nixserds.coffeegrinder.stages;

import java.io.IOException;
import java.math.BigDecimal;

import org.nixserds.coffeegrinder.CoffeeGrinder;
import org.nixserds.coffeegrinder.objects.MoneyCounter;
import org.nixserds.coffeegrinder.objects.Upgrades;
import org.nixserds.coffeegrinder.parameters.Parameters;
import org.nixserds.coffeegrinder.screens.GameScreen;
import org.nixserds.coffeegrinder.screens.UpgradesScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.viewport.Viewport;

//Stage where shows upgrades that can be bought or already bought
public class UpgradesStage extends Stage
{
	final CoffeeGrinder game;

	final UpgradesScreen screen;
	
	XmlReader reader;
	
	Table fixedTable;
	Table movableTable;
	
	ScrollPane scrollPane;
	
	MoneyCounter money;
	
	TextButton back;
	TextButton swap;
	
	Upgrades [] upgrades;
	
	int totalAmount;
	int realAmount;
	
	boolean touchable;
	
	public UpgradesStage(Viewport viewport, Batch batch, CoffeeGrinder gam, UpgradesScreen scren, boolean touchable)
	{
		//Initialize from parent
		super(viewport, batch);

		game = gam;
		screen = scren;
		this.touchable = touchable;
		realAmount = 0;

		//XML reader used because all upgrades are saved in XML and need to be parsed
		reader = new XmlReader();
		
        money = new MoneyCounter(Parameters.MONEY.toBigInteger().toString(), game.labelStyle);


        getUpgrades();
        createButtons();
        createTable();
	}
	
	public void update()
	{
		realAmount = 0;
		clear();
		getUpgrades();
        createTable();
	}

	//Parse XML file to get upgrades
	private void getUpgrades()
	{
		try
		{
			XmlReader.Element root = reader.parse(Gdx.files.internal("upgrades/1.xml"));
			totalAmount = root.getChildCount();
			upgrades = new Upgrades[totalAmount];
			for(int i = 0; i < totalAmount; i++)
			{
				//Check if is for buyable screen
				if(touchable)
				{
					//If upgrade is not bought then add it
					//This decision about mask is awful and should be replace
					//TODO get rid of mask method
					if((Parameters.UPGRADES & (int)Math.pow(2, i)) != (int)Math.pow(2, i))
					{
						XmlReader.Element upgrade = root.getChild(i);
						String name = upgrade.getAttribute("name");
						String type = upgrade.getAttribute("type");
						int gain = upgrade.getInt("gain");
						String price = upgrade.getAttribute("price");
						upgrades[realAmount] = new Upgrades(game, screen, name, type, new BigDecimal(gain), new BigDecimal(price), i, touchable);
						realAmount++;
					}
				}
				//Else if it is bought screen
				else
				{
					//If something is bought then add it there
					if((Parameters.UPGRADES & (int)Math.pow(2, i)) == (int)Math.pow(2, i))
					{
						XmlReader.Element upgrade = root.getChild(i);
						String name = upgrade.getAttribute("name");
						String type = upgrade.getAttribute("type");
						int gain = upgrade.getInt("gain");
						int price = upgrade.getInt("price");
						upgrades[realAmount] = new Upgrades(game, screen, name, type, new BigDecimal(gain), new BigDecimal(price), i, touchable);
						realAmount++;
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	//Create button to back to game screen
	//And button to flip flop from buyable and bough upgrades
	private void createButtons()
	{
		//Button to go back to game screen
		back = new TextButton("Back", game.textButtonStyle);
        back.addListener(new ClickListener()
        {
        	@Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
            	game.setScreen(new GameScreen(game));
            }
        });

		//Button to switch to bought screen
        if(touchable)
        {
        	swap = new TextButton("Bought", game.textButtonStyle);
        	swap.addListener(new ClickListener()
        	{
        		@Override
        		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    return true;
                }
	            @Override
	            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
	            {
	            	game.setScreen(new UpgradesScreen(game, false));
	            }
        	});
        }
		//Button to buyable screen
        else
        {
        	swap = new TextButton("Buy", game.textButtonStyle);
	    	swap.addListener(new ClickListener()
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
        }
	}

	//Place all upgrades in table
	//Still not working as intended
	private void createTable()
	{
        movableTable = new Table();
        
        for(int i = 0; i < realAmount; i++)
        {
        	if(i != 0) movableTable.row();
        	movableTable.add(upgrades[i]).colspan(3).width(getWidth() * 9 / 10);
        }
        scrollPane = new ScrollPane(movableTable);
		fixedTable = new Table();
        fixedTable.setFillParent(true);
		fixedTable.align(Align.top);
        fixedTable.add(money).width(getWidth() * 3 / 10);
        fixedTable.add(back).width(getWidth() * 3 / 10);
        fixedTable.add(swap).width(getWidth() * 3 / 10);
        fixedTable.row();
        if(touchable)
        {
        	fixedTable.add(new Label("Available upgrades", game.labelStyle)).colspan(3);
        }
        else
        {
        	fixedTable.add(new Label("Purchased upgrades", game.labelStyle)).colspan(3);
        }
        fixedTable.row();
        fixedTable.add(scrollPane).fill().expand().colspan(3);
        
        addActor(fixedTable);
	}
}
