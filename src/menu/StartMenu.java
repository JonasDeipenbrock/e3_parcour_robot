package menu;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.TextMenu;

public class StartMenu implements IMenu {
	
	public GraphicsLCD display;
	public TextMenu menu;
	public StartMenu() {
		display = LocalEV3.get().getGraphicsLCD();
	}

	@Override
	public void drawMenu(String[] items) {
		display.clear();
		menu = new TextMenu(items, 1, "Choose a mode");
		// TODO Auto-generated method stub

	}

	@Override
	public int selectMenuElement() {
		// TODO Auto-generated method stub
		if(menu == null) {
			return -1;
		}
		int selection = menu.select();
		return selection;
	}
	
	@Override
	public void clearMenu() {
		display.clear();
		menu = null;
	}

}
