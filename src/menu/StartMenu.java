package menu;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
import lejos.utility.TextMenu;

public class StartMenu implements IMenu {
	
	public GraphicsLCD display;
	public TextMenu menu;
	public StartMenu() {
		display = LocalEV3.get().getGraphicsLCD();
	}

	@Override
	public void drawMenu(String[] items) {
		// Clear stdout from screen
		for (int i = 0; i < 8; i++) {
  			System.out.println("");
		}
		display.clear();
		menu = new TextMenu(items, 1, "Choose a mode");
	}

	@Override
	public int selectMenuElement() {
		if(menu == null) {
			return -1;
		}
		int selection = menu.select();
		Delay.msDelay(250);
		return selection;
	}
	
	@Override
	public void clearMenu() {
		display.clear();
		menu = null;
	}

}
