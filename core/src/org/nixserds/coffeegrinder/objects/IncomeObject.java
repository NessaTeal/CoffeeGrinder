package org.nixserds.coffeegrinder.objects;

import java.math.BigDecimal;

import org.nixserds.coffeegrinder.CoffeeGrinder;
import org.nixserds.coffeegrinder.parameters.Parameters;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

//This class hold information about income object that generate money over time
public class IncomeObject extends Button
{
	Label amountLabel;
	Label priceLabel;
	Label gainLabel;

	Image progress_bar;
	
	public BigDecimal gain;
	public BigDecimal amount;
	public BigDecimal price;
	
	float price_gain;
	public float progress;
	public float progress_speed;
	
	public IncomeObject(CoffeeGrinder game, String name, BigDecimal amount1, BigDecimal price1, float price_gain1, BigDecimal gain1, float progress1, float progress_speed1, Skin skin)
	{
		//Call constructor of parent (button) to assign style
		super(game.textButtonStyle);

		//Assign all starting values
		amount = amount1;
		price = price1;
		price_gain = price_gain1;
		gain = gain1;
		progress_speed = progress_speed1;
		progress = progress1;

		//Name labels and give them style
		amountLabel = new Label(amount.toPlainString(), game.labelStyle);
		priceLabel = new Label(price.toBigInteger().toString(), game.labelStyle);
		gainLabel = new Label(gain.multiply(amount).toPlainString(), game.labelStyle);

		//Progress bar is just stretched green pixel
		progress_bar = new Image(skin.getRegion("progress"));

		//Create layout
		createLayout(name, game.labelStyle);

		//Add reaction to click
		addListener(new ClickListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			//Reaction is on touchUp as user can click and sweep at side to cancel purchase
	        @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
				//If you have enough money they buy
				if(Parameters.MONEY.compareTo(price) >= 0)
				{
					//Subtract price
					Parameters.MONEY = Parameters.MONEY.subtract(price);

					//Update money gain by clicking
					Parameters.MONEY_GAIN = Parameters.MONEY_GAIN.add(gain.divide(new BigDecimal(10)));

					//Add plus one of corresponding item
					amount = amount.add(BigDecimal.ONE);

					//Increase price
					price = price.multiply(new BigDecimal(price_gain));
					update();
				}
            }
		});
	}
	
	@Override
	//This function is called by main cycle of game
	public void act(float delta)
	{
		//If progress bar would move too fast then make it solid and just get money continuously instead of flashing line
		if(progress_speed > 10)
		{
			Parameters.MONEY = Parameters.MONEY.add(gain.multiply(amount).multiply(new BigDecimal(progress_speed + progress).multiply(new BigDecimal(delta))));
			progress = progress_speed % 1;
			progress_bar.setWidth(Parameters.SCREEN_WIDTH * 3 / 11 - Parameters.SCREEN_WIDTH / 25);
			return;
		}


		//If there is at least one bought item
		if(amount.compareTo(new BigDecimal(0)) != 0)
		{
			//Then move progress
			progress += progress_speed * delta;

			//Update progress bar
			progress_bar.setWidth(progress * (Parameters.SCREEN_WIDTH * 3 / 11 - Parameters.SCREEN_WIDTH / 25));

			//If progress is full
			if(progress >= 1)
			{
				//Then assign to zero and add money
				progress = 0;
				Parameters.MONEY = Parameters.MONEY.add(gain.multiply(amount));
			}
		}
	}

	//Updates visual text
	public void update()
	{
		amountLabel.setText(amount.toPlainString());
		priceLabel.setText(price.toBigInteger().toString());
		gainLabel.setText(gain.multiply(amount).toBigInteger().toString());
	}

	//Create layout of income object
	private void createLayout(String name, LabelStyle labelStyle)
	{
		defaults().padLeft(Parameters.SCREEN_WIDTH / 50).padRight(Parameters.SCREEN_WIDTH / 50);
		add(new Label(name, labelStyle)).colspan(2).align(Align.center).expand();
		row();
		add(new Label("Amount: ", labelStyle)).align(Align.left);
		add(amountLabel).align(Align.right);
		row();
		add(new Label("Price: ", labelStyle)).align(Align.left);
		add(priceLabel).align(Align.right);
		row();
		add(progress_bar).colspan(2).left();
		row().padBottom(Parameters.SCREEN_WIDTH / 50);
		add(new Label("Gain: ", labelStyle)).align(Align.left);
		add(gainLabel).align(Align.right);
	}
}
