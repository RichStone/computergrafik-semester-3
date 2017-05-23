import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class Planet {
	
	int size;
	int dayLength;
	int yearLength;
	int motherDistance;
	ArrayList<Planet> childList;
	
	long startTime = System.currentTimeMillis();
	
	public Planet(
			int size, 
			int dayLength, 
			int yearLength, 
			int motherDistance, 
			ArrayList<Planet> childList) 
	{
		this.size = size;
		this.dayLength = dayLength;
		this.yearLength = yearLength;
		this.motherDistance = motherDistance;
		this.childList = childList;
	}

	public void draw() {
		float motherRotationAngle = getMotherRotationAngle();
		GL11.glPushMatrix();
		GL11.glRotatef(motherRotationAngle, 0, 1, 0);
	}
	
	public float getMotherRotationAngle() {
		float day = getSimDay();
		float motherRotationAngle = day / yearLength * 360;
		return motherRotationAngle;
	}
	
	public float getOwnRotationAngle() {
		float day = getSimDay();
		float ownRotationAngle = day * 360;
		return ownRotationAngle;
	}
	
	public float getSimDay() {
		long timeElapsed = System.currentTimeMillis() - startTime;
		float day = (float)(timeElapsed / 1000.0) * 10;
		System.out.println(day);
		return day;
	}
}
