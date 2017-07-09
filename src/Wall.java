import java.awt.*;

public class Wall {
	private int x,y;
	private int wallWidth,wallHeigth;
	
	TankFrame tf;
	
	Wall(int x, int y, int wallWidth, int wallHeigth, TankFrame tf)
	{
		this.x = x;
		this.y = y;
		this.wallWidth = wallWidth;
		this.wallHeigth = wallHeigth;
		this.tf = tf;
	}
	
	public void draw(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(x, y, wallWidth, wallHeigth);
		g.setColor(c);
	}
	
	public Rectangle getRectangle()
	{
		return new Rectangle(x, y, wallWidth, wallHeigth);
	}
}





