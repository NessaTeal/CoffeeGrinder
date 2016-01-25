package org.nixserds.coffeegrinder.objects;

import java.math.BigDecimal;

import org.nixserds.coffeegrinder.CoffeeGrinder;
import org.nixserds.coffeegrinder.parameters.Parameters;
import org.nixserds.coffeegrinder.screens.UpgradesScreen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

//Class that describes upgrades on upgrade screen
public class Upgrades extends Button
{
	BigDecimal gain;
	BigDecimal price;
	
	int index;
	
	IncomeObject object;
	
	UpgradesScreen screen;
	
	public Upgrades(CoffeeGrinder game, UpgradesScreen scren, String name, String type, BigDecimal gain1, BigDecimal price1, int index1, boolean buy)
	{
		//Get button style from game
		super(game.textButtonStyle);

		//Assign appropriate values
		gain = gain1;
		price = price1;
		index = index1;
		screen = scren;

		//Define which of six income objects is upgraded
		switch(type) {
			case "bush":
				object = game.bushButton;
				break;
			case "plantation":
				object = game.plantationButton;
				break;
			case "farm":
				object = game.farmButton;
				break;
			case "city":
				object = game.cityButton;
				break;
			case "island":
				object = game.islandButton;
				break;
			case "java":
				object = game.javaButton;
				break;
		}

		//Create layout for the upgrade
		defaults().padLeft(Parameters.SCREEN_WIDTH / 30).padRight(Parameters.SCREEN_WIDTH / 30);
		add(new Label(name, game.labelStyle)).center();
		row();
		add(new Label("Increase " + type + " gain by " + gain, game.labelStyle)).left();
		add(new Label(price + "", game.labelStyle)).right();

		//Check if it is added as buyable option or as already bought
		if(buy) {
			addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					//If there is enough money then buy and do all related stuff
					if (Parameters.MONEY.compareTo(price) == 1) {
						Parameters.MONEY = Parameters.MONEY.subtract(price);
						object.gain = object.gain.multiply(gain);
						object.update();
						Parameters.UPGRADES += (int) Math.pow(2, index);
						screen.upgradesStage.update();
					}
				}
			});
		}
		//Else set it unclickable
		else {
			setTouchable(Touchable.disabled);
		}
	}
}
