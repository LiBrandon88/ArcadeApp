import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

/**
 * extra class to intitalize and color all the objects in snake
 *@author Huzaifah Aamir
 *@author Brandon Li
 */
@SuppressWarnings("serial")
public class SnakePanel extends JPanel {

	public int black = 0;
		
	
	/**
	 * Uses the paintComponent method to color in the board
	 * and snake and food
	 */
	@Override
	protected void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		Snake s = Snake.h;
		
		graphic.setColor(new Color(black));
		graphic.fillRect(0, 0, 800, 700);
		graphic.setColor(Color.BLUE);
		for(Point point: s.snake) {
			graphic.fillRect(point.x *10, point.y*10, 10, 10);
		}//for
		graphic.fillRect(s.head.x*10, s.head.y *10, 10, 10);
		graphic.setColor(Color.RED);
		graphic.fillRect(s.food.x * 10, s.food.y*10, 10, 10);
		String score = "Score: " + s.score;
		graphic.setColor(Color.white);
		graphic.drawString(score, (int) (getWidth() / 2 - score.length() * 2.5f), 10);
		String end = "Game Over!";
		if(s.z) {
			graphic.drawString(end, (int) (getWidth() / 2 - end.length() * 2.5f), (int) s.dim.getHeight()/4);
		}
	}
	
	
}