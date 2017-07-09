import java.awt.*;
import java.util.List;

public class Missile 
{
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int MISSILEWIDTH = 10;
	public static final int MISSILEHEIGTH = 10;
	
	private int x, y;
	public boolean live = true;
	private boolean good;

	private Direction dir;
	private TankFrame tf;
	
	public boolean isGood() 
	{
		return good;
	}
	
	public Missile(int x, int y, Direction dir, boolean good, TankFrame tf) 
	{
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.good = good;
		this.tf = tf;
	}
	
	public void draw(Graphics g)
	{
		if(!live)
		{
			tf.missiles.remove(this);
			return;
		}
		
		Color c = g.getColor();
		if(this.isGood())
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.fillOval(x, y, MISSILEWIDTH, MISSILEHEIGTH);
		g.setColor(c);
	
		Move();
	}

	public void Move()
	{
		switch(dir)
		{
		case L :
			x -= XSPEED;				break;
		case LU :
			x -= Math.sqrt(2)/2 * XSPEED; y -= Math.sqrt(2)/2 * YSPEED;	break;
		case U :
			y -= YSPEED;				break;
		case RU :
			x += Math.sqrt(2)/2 * XSPEED; y -= Math.sqrt(2)/2 * YSPEED;	break;
		case R :
			x += XSPEED;				break;
		case RD :
			x += Math.sqrt(2)/2 * XSPEED; y += Math.sqrt(2)/2 * YSPEED;	break;
		case D :
			y += YSPEED;				break;
		case LD :
			x -= Math.sqrt(2)/2 * XSPEED; y += Math.sqrt(2)/2 * YSPEED;	break;
		}
		if(x > TankFrame.GAME_WIDTH || x < -MISSILEWIDTH || y > TankFrame.GAME_HEIGHT || y < -MISSILEHEIGTH)
		{
			live = false;
		}
	}
	
	public boolean isLive() 
	{
		return live;
	}
	
	public Rectangle getRectangle()
	{
		return new Rectangle(x, y, MISSILEWIDTH, MISSILEHEIGTH);
	}
	
	public boolean hitTank(Tank t)
	{
		if(this.live && this.getRectangle().intersects(t.getRectangle()) && t.isLive() == true && this.good != t.isGood())
		{
			if(t.isGood())
			{
				t.setLife(t.getLife() - 20);
				
				if(t.getLife() <= 0)
				{
					t.setLive(false);
				}
			}
			else
			{
				t.setLive(false);
			}	
			this.live = false;
			Explode e = new Explode(x, y, tf);
			tf.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTank(List<Tank> tanks)
	{
		for(int i = 0 ; i < tanks.size() ; i++)
		{
			if(this.hitTank(tanks.get(i)))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall w)
	{/*
		for(int i = 0 ; i < tf.missiles.size() ; i++)
		{
			if(tf.missiles.get(i).live && tf.missiles.get(i).getRectangle().intersects(w.getRectangle()))
			{
				tf.missiles.get(i).live = false;
				return true;
			}
			return false;
		}
		return false;
	*/
		if(this.live && this.getRectangle().intersects(w.getRectangle()))
		{
			this.live = false;
			return true;
		}
		return false;
	}
}




