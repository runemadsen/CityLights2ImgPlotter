import processing.core.*;
import java.util.ArrayList;

public class FrequencyShape
{
	/* Properties
    ____________________________________________ */

	PApplet p;
	private int _margin = 5;
	private ArrayList _positions = new ArrayList();
	private int _lowBand;
	private int _highBand;
	private int _triggerNum;
	private int _sensitivity;
	private float _minVolume = 0;
	int _dotColor;

	/* Constructor
    ____________________________________________ */

	FrequencyShape(PApplet parent, int lowBand, int highBand, int triggerNum, int sensitivity, float minVolume, int dotColor)
	{
		p = parent;
		_dotColor = dotColor;
		_sensitivity = sensitivity;
		_lowBand = lowBand;
		_highBand = highBand;
		_triggerNum = triggerNum;
		_minVolume = minVolume;
	}  
	
	void draw()
	{
		for(int i = 0; i < _positions.size(); i++)
		{
			PVector pos =  (PVector) _positions.get(i);
			
			p.fill(_dotColor);
			p.noStroke();
			p.ellipse(pos.x, pos.y, 5, 5);
			
			p.noFill();
			p.stroke(_dotColor);
			
			if(i > 0)
			{
				PVector prevPos =  (PVector) _positions.get(i - 1);
				p.line(prevPos.x, prevPos.y, pos.x, pos.y);
			}
		}
	}

	/* Add position
    ____________________________________________ */

	void addPos(float xPos, float yPos)
	{
		_positions.add(new PVector(xPos, yPos));  
	}

	/* Getter / Setter
    ____________________________________________ */

	boolean isWithinPoint(PVector clickPos)
	{
		for(int i = 0; i < _positions.size(); i++)
		{
			PVector pos =  (PVector) _positions.get(i);
			
			if(PApplet.abs(clickPos.x - pos.x) < _margin && PApplet.abs(clickPos.y - pos.y) < _margin)
			{				
				return true;
			}
		}
		
		return false;
	}
	
	int getLowBand()
	{
		return _lowBand;  
	}

	int getHighBand()
	{
		return _highBand;  
	}

	int getTriggerNum()
	{
		return _triggerNum;  
	}
	
	int getSensitivity()
	{
		return _sensitivity;  
	}

	int getSize()
	{
		return _positions.size();  
	}

	PVector getPos(int i)
	{
		return (PVector) _positions.get(i);  
	}

	float getMinVolume()
	{
		return _minVolume;
	}
}

