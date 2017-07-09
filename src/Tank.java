
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.*;

public class Tank 
{
	private static final float XSPEED = 3;
	private static final float YSPEED = 3;
	private static final int TANKWIDTH = 30;
	private static final int TANKHEIGTH = 30;
	private int life = 100;
	private BloodBar bb = new BloodBar();

	private int x, y, oldX, oldY;	// 保存上一步坦克坐标,防止坦克在墙上一直STOP.
	private boolean good;

	private boolean live = true;

	private boolean bL=false, bU=false, bR=false, bD=false;
//	enum Direction {L,LU,U,RU,R,RD,D,LD,STOP};
	
	Direction ptDir = Direction.D;
	
	private Direction dir;

	private static Random r = new Random();
	private int step = r.nextInt(12) + 3;	// 随机数 3~14
	
	TankFrame tf;	
	
	
	private static Toolkit t = Toolkit.getDefaultToolkit();

	private static Image[] tankImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	
	static
	{
		tankImages = new Image[]
		{
			t.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
			t.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")),
			t.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
			t.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")),
			t.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
			t.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif")),
			t.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
			t.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif")),
		};

		imgs.put("L", tankImages[0]);
		imgs.put("LU", tankImages[1]);
		imgs.put("U", tankImages[2]);
		imgs.put("RU", tankImages[3]);
		imgs.put("R", tankImages[4]);
		imgs.put("RD", tankImages[5]);
		imgs.put("D", tankImages[6]);
		imgs.put("LD", tankImages[7]);

	}
	

	public Tank(int x, int y, TankFrame tf, Direction dir, boolean good) 
	{
		this.x = x;
		this.y = y;
		this.tf = tf;
		this.dir = dir;
		this.good = good;
		this.oldX = x;
		this.oldY = y;
	}

	public int getLife() 
	{
		return life;
	}
	
	public void setLife(int life)
	{
		this.life = life;
	}
	
	public void draw(Graphics g)
	{	
		if(!live)
		{
			if(!good)
			{
				tf.tanks.remove(this);
			}
			return;
		}
		if(this.isGood())
		{
			bb.draw(g);
		}
		
		if(tf.tanks.size() == 0)
		{
			for(int i = 0 ; i < 15 ; i++)
			{
				tf.tanks.add(new Tank(50 + 40*(i+1), 50, tf, Direction.D, false));
			}
		}
		
/*
		Color c = g.getColor();
		if(good)	g.setColor(Color.RED);
			else	g.setColor(Color.BLUE);
		g.fillOval(x, y, TANKWIDTH, TANKHEIGTH);
		g.setColor(c);
*/		
//		int ptX = x + TANKWIDTH/2;
//		int ptY = y + TANKHEIGTH/2;
		
		switch(ptDir)
		{
		case L :
//			g.drawLine(ptX, ptY, (int) (ptX - Math.sqrt(2)/2 * TANKWIDTH), ptY);
			g.drawImage(imgs.get("L"), x, y, null);
//			g.drawImage(tankImages[0], x, y, null);
			break;
		case LU :
//			g.drawLine(ptX, ptY, x, y);
			g.drawImage(imgs.get("LU"), x, y, null);
//			g.drawImage(tankImages[1], x, y, null);
			break;
		case U :
//			g.drawLine(ptX, ptY, ptX, (int) (ptY - Math.sqrt(2)/2 * TANKHEIGTH));
			g.drawImage(imgs.get("U"), x, y, null);
//			g.drawImage(tankImages[2], x, y, null);
			break;
		case RU :
//			g.drawLine(ptX, ptY, x + TANKWIDTH, y);
			g.drawImage(imgs.get("RU"), x, y, null);
//			g.drawImage(tankImages[3], x, y, null);
			break;
		case R :
//			g.drawLine(ptX, ptY, (int) (ptX + Math.sqrt(2)/2 * TANKWIDTH), ptY);
			g.drawImage(imgs.get("R"), x, y, null);
//			g.drawImage(tankImages[4], x, y, null);
			break;
		case RD :
//			g.drawLine(ptX, ptY, x + TANKWIDTH, y + TANKHEIGTH);
			g.drawImage(imgs.get("RD"), x, y, null);
//			g.drawImage(tankImages[5], x, y, null);
			break;
		case D :
//			g.drawLine(ptX, ptY, ptX, (int) (ptY + Math.sqrt(2)/2 * TANKHEIGTH));
			g.drawImage(imgs.get("D"), x, y, null);
//			g.drawImage(tankImages[6], x, y, null);
			break;
		case LD :
//			g.drawLine(ptX, ptY, x, y + TANKHEIGTH);
			g.drawImage(imgs.get("LD"), x, y, null);
//			g.drawImage(tankImages[7], x, y, null);
			break;
		}

		
		Move();
	}
	
	public void Move()
	{
		this.oldX = this.x;
		this.oldY = this.y;
		
		Graphics g = null;
/*		if(!init)
		{
			for (int i = 0; i < imgs.length; i++) 
			{
				g.drawImage(imgs[0], x, y, null);
			}
		}
*/		
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
		case STOP:
										break;
		}
		if(x <= 3)	x = 3;
		if(y <= 24)	y = 24;
		if(x >= tf.GAME_WIDTH - TANKWIDTH - 3)
			x = tf.GAME_WIDTH - TANKWIDTH - 3;
		if(y >= tf.GAME_HEIGHT - TANKHEIGTH - 3)
			y = tf.GAME_HEIGHT - TANKHEIGTH - 3;
		
		if(dir != Direction.STOP)
		{
			ptDir = dir;
		}
		
		if(!good)	// 敌方坦克
		{
			Direction[] dirs = dir.values();	// 将枚举类型转化为数组类型
			
			if(step == 0)
			{
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];		// 控制敌方坦克移动方向
			}
			step --;
			
			if(r.nextInt(20) > 18)
				this.fire();
		}
	}
	
	public void keyPressed(KeyEvent e) 
	{
		int key = e.getKeyCode();
		
		switch(key)
		{
		case KeyEvent.VK_LEFT :
			bL = true;
			break;				// 防止case穿透
		case KeyEvent.VK_UP :
			bU = true;
			break;
		case KeyEvent.VK_RIGHT :
			bR = true;
			break;
		case KeyEvent.VK_DOWN :
			bD = true;
			break;
		}
		
		locateDirection();
	}
	
	private void locateDirection()
	{
		if(bL && !bU && !bR && !bD)				{dir = Direction.L;			ptDir = Direction.L;}
		else if(bL &&  bU && !bR && !bD)		{dir = Direction.LU;		ptDir = Direction.LU;}
		else if(!bL&&  bU && !bR && !bD)		{dir = Direction.U;			ptDir = Direction.U;}
		else if(!bL&&  bU &&  bR && !bD)		{dir = Direction.RU;		ptDir = Direction.RU;}
		else if(!bL&& !bU &&  bR && !bD)		{dir = Direction.R;			ptDir = Direction.R;}
		else if(!bL&& !bU &&  bR &&  bD)		{dir = Direction.RD;		ptDir = Direction.RD;}
		else if(!bL&& !bU && !bR &&  bD)		{dir = Direction.D;			ptDir = Direction.D;}
		else if( bL&& !bU && !bR &&  bD)		{dir = Direction.LD;		ptDir = Direction.LD;}
		else if(!bL&& !bU && !bR && !bD)		{dir = Direction.STOP;}
	}

	public void keyReleased(KeyEvent e) 
	{
		int key = e.getKeyCode();
		
		switch(key)
		{
		case KeyEvent.VK_F2:
			ReBirth();
			break;
		case KeyEvent.VK_V :
			superFire();
			break;
		case KeyEvent.VK_CONTROL :
			fire();
			break;
		case KeyEvent.VK_LEFT :
			bL = false;
			break;
			
		case KeyEvent.VK_UP :
			bU = false;
			break;
			
		case KeyEvent.VK_RIGHT :
			bR = false;
			break;
			
		case KeyEvent.VK_DOWN :
			bD = false;
			break;
		}
		locateDirection();
	}
	
	private void ReBirth() 
	{
		if(!this.live)
		{
			tf.myTank = new Tank(0, 600, tf,Direction.STOP ,true);
			this.setLive(true);
		}
	}

	public void fire()
	{
		if(!live)	return;
		
		Missile m = new Missile(x + TANKWIDTH/2 - Missile.MISSILEWIDTH/2, y + TANKHEIGTH/2 - Missile.MISSILEHEIGTH/2, ptDir, good, tf);
		tf.missiles.add(m);
	}
	
	public Missile fire(Direction dir)
	{
		Missile m = new Missile(x + TANKWIDTH/2 - Missile.MISSILEWIDTH/2, y + TANKHEIGTH/2 - Missile.MISSILEHEIGTH/2, dir, good, tf);
		tf.missiles.add(m);
		
		return m;
	}
	
	public void superFire()
	{
		if(!live)	return;
		
		Direction[] dir = Direction.values();
		for(int i = 0 ; i < 8 ; i++)
		{
			fire(dir[i]);
		}
	}
	
	Rectangle getRectangle()
	{
		return new Rectangle(x, y, TANKWIDTH, TANKHEIGTH);
	}
	
	public void stay()
	{
		this.x = this.oldX;
		this.y = this.oldY;
	}
	
	public boolean collidesWithWall(Wall w)
	{
		if(this.getRectangle().intersects(w.getRectangle()))
		{
			stay();			// 碰撞时把坐标设回上一步坐标,否则改变方向时会一直STOP
			return true;
		}
		return false;
	}
	
	public boolean collidesWithTanks(List<Tank> tanks)
	{
		for(int i = 0 ; i < tanks.size() ; i++)
		{
			Tank t = tanks.get(i);
			if(this != t)
			{
				if(this.live && t.live && this.getRectangle().intersects(t.getRectangle()))
				{
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}

	public boolean isLive() 
	{
		return live;
	}

	public void setLive(boolean live) 
	{
		this.live = live;
	}
	
	public boolean isGood() 
	{
		return good;
	}
	
	public void setDir(Direction dir) 
	{
		this.dir = dir;
	}
	
	public boolean eat(Blood b)
	{
		if(this.live && b.isLive() && this.getRectangle().intersects(b.getRectangle()))
		{
			this.setLife(100);
			b.setLive(false);
			return true;
		}
		return false;
	}
	
	public class BloodBar
	{
		public void draw(Graphics g)
		{
			int width = TANKWIDTH * life / 100;
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-10, TANKWIDTH, 10);
			g.fillRect(x, y-10, width, 10);
		}
	}
}





