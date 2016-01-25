package org.nixserds.coffeegrinder.objects;

import org.nixserds.coffeegrinder.parameters.Parameters;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

//Class that shows earned money
public class MoneyCounter extends Table
{
	Label money;
	
	public MoneyCounter(String money, LabelStyle style)
	{
		//Load initial amount of money
		this.money = new Label(money, style);

		add(new Label("Money: ", style)).left();
		add(this.money).right().expandX();
	}
	
	@Override
	//Set number to current value of variable
	public void act(float delta)
	{
		money.setText(Parameters.MONEY.toBigInteger().toString());
	}
}
