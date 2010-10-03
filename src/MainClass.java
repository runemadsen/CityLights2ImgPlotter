import java.util.ArrayList;
import proxml.*;
import processing.core.*;

public class MainClass extends PApplet 
{
	/* Setup
	__________________________________________________ */
	
	String song = "1.mp3";
	String img1 = "1_light.jpg";
	String img2 = "1_nolight.jpg";
	int lowestFreq = 40;
	int bandsPerOctave = 8;
	int sensitivity = 10;
	
	/* Properties
	__________________________________________________ */
	
	PImage img;
	ArrayList shapes = new ArrayList();
	int selectedShape = -1;
	
	/* Setup
	__________________________________________________ */
	
	public void setup()
	{
		size(1920, 1080); // this must be set to size of loaded img
		smooth();
		
		img = loadImage(img1);
	}
	
	/* Draw
	__________________________________________________ */
	
	public void draw()
	{
		image(img, 0, 0);
		
		for(int i = 0; i < shapes.size(); i++)
		{
			FrequencyShape shape =  (FrequencyShape) shapes.get(i);
			shape.draw();
		}
	}
	
	public void mousePressed()
	{
		if(selectedShape == -1)
		{
			FrequencyShape shape = new FrequencyShape(this, 5, 8, 3, 300, 2, color(random(255), random(255), random(255)));
			shape.addPos(mouseX, mouseY);
			shapes.add(shape);
			selectedShape = shapes.size() - 1;
		}
		else
		{
			FrequencyShape shape = (FrequencyShape) shapes.get(selectedShape);
			
			if(shape.isWithinPoint(new PVector(mouseX, mouseY)))
			{
				selectedShape = -1;
			}
			
			shape.addPos(mouseX, mouseY);
		}
	}
	
	public void keyPressed() 
	{
		switch(keyCode)
		{
		case 'S':
			saveInfo();
			break;
		}
	}
	
	public void saveInfo()
	{
		XMLElement rootNode = new XMLElement("root");
		XMLInOut xmlInOut = new XMLInOut(this);
		
		XMLElement setupNode = new XMLElement("setup");
		setupNode.addAttribute("song", song);
		setupNode.addAttribute("img1", img1);
		setupNode.addAttribute("img2", img2);
		setupNode.addAttribute("lowestFreq", lowestFreq);
		setupNode.addAttribute("bandsPerOctave", bandsPerOctave);
		setupNode.addAttribute("sensitivity", sensitivity);
		rootNode.addChild(setupNode);
		
		XMLElement shapesNode = new XMLElement("shapes");
		
		for(int i = 0; i < shapes.size(); i++)
		{
			FrequencyShape shape = (FrequencyShape) shapes.get(i);
			
			XMLElement shapeNode = new XMLElement("shape");
			shapeNode.addAttribute("lowBand", shape.getLowBand());
			shapeNode.addAttribute("highBand", shape.getHighBand());
			shapeNode.addAttribute("triggerNum", shape.getTriggerNum());
			shapeNode.addAttribute("sensitivity", shape.getSensitivity());
			shapeNode.addAttribute("minVolume", shape.getMinVolume());
			shapesNode.addChild(shapeNode);
			
			XMLElement positionsNode = new XMLElement("positions");
			
			for(int j = 0; j < shape.getSize(); j++)
			{
				XMLElement posNode = new XMLElement("pos");
				posNode.addAttribute("x", shape.getPos(j).x);
				posNode.addAttribute("y", shape.getPos(j).y);
				positionsNode.addChild(posNode);
			}
			
			shapeNode.addChild(positionsNode);
		}
		
		rootNode.addChild(shapesNode);
		
		xmlInOut.saveElement(rootNode,"shapes.xml");
	}
}




