import java.awt.*;
import java.awt.event.*;
import java.util.List;

import java.util.ArrayList;

public class TankClient 
{
	public static void main(String[] args)
	{
		new TankFrame().LaunchFrame("TankWar2.84");
	}
}

class TankFrame extends Frame
{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Missile m = null;
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<Tank> tanks = new ArrayList<Tank>();
	
	Tank myTank = new Tank(0, 600, this,Direction.STOP ,true);
	private int life = 100;

	public int getLife() 
	{
		return life;
	}

	public void setLife(int life) 
	{
		this.life = life;
	}
	
	Wall w1 = new Wall(100, 200, 20, 150, this), 
		 w2 = new Wall(300, 100, 300, 20, this);
	
	Blood b = new Blood();

	Image offScreenImage = null;
	
	void LaunchFrame(String s)
	{
		for(int i = 0 ; i < 15 ; i++)
		{
			tanks.add(new Tank(50 + 40*(i+1), 50, this, Direction.D, false));
		}
		
		setTitle(s);
		setBounds(100, 50, GAME_WIDTH, GAME_HEIGHT);
		setVisible(true);
		this.setResizable(false);
		this.addKeyListener(new KeyMonitor());
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		this.setBackground(Color.GREEN);

		new Thread(new MoveThread()).start();
		
	}

	public void paint(Graphics g) 
	{
		for(int i = 0 ; i < missiles.size() ; i++)
		{
			Missile m = missiles.get(i);
			
			m.hitTank(tanks);
			m.hitTank(myTank);
			missiles.get(i).hitWall(w1);
			missiles.get(i).hitWall(w2);
//			if(!m.isLive())
//			{
//				missiles.remove(m);
//			}
//			else
			m.draw(g);
		}
		
		for(int i = 0 ; i < explodes.size() ; i++)
		{
			Explode e = explodes.get(i);
			e.draw(g);
		}

		for(int i = 0 ; i < tanks.size() ; i++)
		{
			Tank t = tanks.get(i);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithTanks(tanks);
//			myTank.collideWithWall(w1);
//			myTank.collideWithWall(w2);		//我方坦克特权
			t.draw(g);
		}
		myTank.draw(g);
		myTank.eat(b);
		b.draw(g);
		w1.draw(g);
		w2.draw(g);

		g.drawString("Missile's count : " + missiles.size(), 10, 50);
		g.drawString("Explode's count : " + explodes.size(), 10, 70);
		g.drawString("tanks' count : " + tanks.size(), 10, 90);
		g.drawString("life : " + myTank.getLife(), 10, 110);
	}

	public void update(Graphics g) 
	{
		if(offScreenImage == null)
		{
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		
		paint(gOffScreen);

		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	public class MoveThread implements Runnable
	{
		public void run()
		{
			while(true)
			{
				try 
				{
					Thread.sleep(20);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				repaint();
			}
		}
	}

	private class KeyMonitor extends KeyAdapter
	{
		public void keyReleased(KeyEvent e) 
		{
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) 
		{
			myTank.keyPressed(e);
		}
	}
}

