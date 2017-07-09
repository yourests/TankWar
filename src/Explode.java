
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.*;

public class Explode 
{
	private int x,y;
	private boolean live = true;
	private int step = 0;
	private boolean init = false;
	
	TankFrame tf;
	
/*	int[] radius = {2, 4, 6, 8, 12, 12, 16, 16, 20, 20, 24, 24, 
					30, 30, 36, 36, 44, 44, 50, 50, 54, 54, 60, 61, 62,
					61, 60, 54, 54, 50, 50, 44, 44, 36, 36, 30, 30, 24 ,
					24, 20, 20, 16, 16, 12, 12, 8, 6, 4, 2};
*/
	private static Toolkit t = Toolkit.getDefaultToolkit();	// 获取到默认的工具包才能把  硬盘资源  引入  内存
	
	private static Image[] imgs = 
		{
			t.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			t.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			t.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			t.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			t.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			t.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			t.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			t.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			t.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
			t.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
			t.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
		};			// 工具包引入图片资源时,路径选择URL,通过"类加载器" 中的Class对象,获取classpath下的 "完整" 图片路径 + 后缀名	
	
	public Explode(int x, int y, TankFrame tf)
	{
		this.x = x;
		this.y = y;
		this.tf = tf;
	}

	public void draw(Graphics g)
	{
		if(!init)		// 异步加载图片,还没加载到内存,必须先画一遍,第一次才有显示
		{
			for (int i = 0; i < imgs.length; i++) 
			{
				g.drawImage(imgs[i], -100, -100, null);
			}
		}
		
		if(!live)
		{
			tf.explodes.remove(this);
			return;
		}
		
		if(step == imgs.length)
		{
			step = 0;
			live = false;
			return;
		}
		/*
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x + Missile.MISSILEWIDTH/2 - radius[step]/2, y + Missile.MISSILEHEIGTH - radius[step]/2, radius[step], radius[step]);
		g.setColor(c);
		*/
		g.drawImage(imgs[step], x + Missile.MISSILEWIDTH/2 - imgs[step].getWidth(null)/2, y + Missile.MISSILEWIDTH - imgs[step].getHeight(null)/2, null);
		step ++;
	}
}
