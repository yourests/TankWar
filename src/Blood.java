import java.awt.*;

public class Blood 
{
	int x, y, w, h;
	int step = 0;
	private boolean live = true;
	
	int [][] pos = {{300,220},{400,280},{370,300},{330,260},{440,250},{200,360},{320,330}};
	
	public boolean isLive() 
	{
		return live;
	}

	public void setLive(boolean live) 
	{
		this.live = live;
	}

	public Blood()
	{
		this.x = pos[step][0];
		this.y = pos[step][1];
		this.w = 10;
		this.h = 10;
	}
	
	public Rectangle getRectangle()
	{
		return new Rectangle(x, y, w, h);
	}
	
	public void draw(Graphics g)
	{
		if(!live)	return;
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
	}
	
	public void move()
	{
		step ++;
		if(step >= pos.length)
		{
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
	}
}




