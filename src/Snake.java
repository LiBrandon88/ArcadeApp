import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Snake class for arcade app
 * @author Huzaifah Aamir
 * @author Brandon Li
 */
public class Snake implements ActionListener, KeyListener {
	public Timer timer = new Timer(20, this);
	public Random r;
	public JFrame jf;
	public SnakePanel sp;
	public ArrayList<Point> snake = new ArrayList<Point>();
	public int tik = 0;
	public int tail;
	public int time;
	public boolean paused;
	public final int UP =0; 
	public final int DOWN = 1;
	public final int LEFT = 2;
	public final int RIGHT = 3;
	public int direct = DOWN;
	public int score;
	public static final Snake h = new Snake();
	public Point head;
	public Point food;
	public Dimension dim;
	public boolean z;
	public boolean pause;
	
	
	/**
	 * Constructor for the snake class
	 */
	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jf = new JFrame("Snake");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setSize(805, 700);
		jf.setLocation(dim.width / 2 - jf.getWidth()/2 , dim.height / 2 - jf.getHeight()/2);
		jf.add(sp= new SnakePanel());
		
		jf.setResizable(false);
		jf.addKeyListener(this);
		
		
	}
	
	/**
	 * Initializes all variables and starts the game
	 */
	public void start() {
		r = new Random();
		tik = 0;
		direct = DOWN;
		pause = false;
		z = false;
		score = 0;
		tail = 0;
		head = new Point(0,-1);
		snake.clear();
		food = new Point(r.nextInt(79), r.nextInt(66));
		time = 0;
		timer.start();
		
	}//start
	@Override
	public void actionPerformed(ActionEvent arg0) {
		sp.repaint();
		tik++;
		
		if(head != null && tik%2 == 0 && !z && !paused) {
			time++;
			snake.add(new Point(head.x, head.y));
			
			if(direct==RIGHT) {
				if(head.x+1<80 && checked(head.x+1, head.y)) {
					head = new Point(head.x+1, head.y);
				}//if
				else {
					z = true;
				}//else
			}//if RIGHT
			if(direct==UP) {
				if(head.y-1>0 && checked(head.x, head.y-1)) {
					head = new Point(head.x, head.y-1);
				}//if
				else {
					z = true;
				}//else
			}//if UP
			if(direct==LEFT) {
				if(head.x-1>=0 && checked(head.x-1, head.y)) {
					head = new Point(head.x-1, head.y);
				}//if
				else {
					z = true;
				}//else
			}//if LEFT
			if(direct==DOWN) {
				if(head.y+1<67 && checked(head.x, head.y+1)) {
					head = new Point(head.x, head.y+1);
				}//if
				else {
					z = true;
				}//else
			}//if DOWN
			if(snake.size()> tail) {
				snake.remove(0);
			}//if
			if(food!= null) {
				if(head.equals(food)) {
					score += 10;
					tail++;
					food.setLocation(r.nextInt(79), r.nextInt(66));
				}//if
			}//if
		}
	}
	
	/**
	 * Gets the snake
	 * @return snake
	 */
	public Snake getSnake() {
		return h;
	}
	
	/**
	 * checks to see if you are actually adding onto the snake
	 * instead of an already filled spot
	 */
	public boolean checked(int x, int y) {
		for(Point point : snake) {
			if(point.equals(new Point(x,y))) {
				return false;
			}
		}
		return true;
	}//checked
	
	@Override
	public void keyPressed(KeyEvent e) {
		int x = e.getKeyCode();
		if(x== KeyEvent.VK_RIGHT && direct != LEFT) {
			direct = RIGHT;
		}//if
		if(x== KeyEvent.VK_UP && direct != DOWN) {
			direct = UP;
		}//if
		if(x== KeyEvent.VK_LEFT && direct != RIGHT) {
			direct = LEFT;
		}//if
		if(x== KeyEvent.VK_DOWN && direct != UP) {
			direct = DOWN;
		}//if
		
	}//keyPressed
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}//keyReleased
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}//keyTyped
	

}//Snake