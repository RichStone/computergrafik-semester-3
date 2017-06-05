import java.security.acl.Owner;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class Planet {
	
	int size;
	int dayLength; //factor based on earth days, e.g. Earth = 1
	int yearLength; //in days
	float motherDistance;
	float [] color = new float[3];
	ArrayList<Planet> childPlanets;
	
	long startTime = Ex2Stars.initTime;
	
	public Planet(
			int size, 
			int dayLength, 
			int yearLength, 
			float motherDistance,
			float [] color,
			ArrayList<Planet> childPlanets) 
	{
		this.size = size;
		this.dayLength = dayLength;
		this.yearLength = yearLength;
		this.motherDistance = motherDistance;
		this.color[0] = color[0];
		this.color[1] = color[1];
		this.color[2] = color[2];
		this.childPlanets = childPlanets;
	}
	
	public Planet(
			int size, 
			int dayLength, 
			int yearLength, 
			float motherDistance)
	{
		this.size = size;
		this.dayLength = dayLength;
		this.yearLength = yearLength;
		this.motherDistance = motherDistance;
		this.color[0] = 0.8f;
		this.color[1] = 0.8f;
		this.color[2] = 0.1f;
		this.childPlanets = new ArrayList<Planet>();
	}

	
	public void draw() {
		float motherRotationAngle;
		if(yearLength == 0)
			motherRotationAngle = 0;
		else 
			motherRotationAngle = getMotherRotationAngle();
		
		float ownRotationAngle = getOwnRotationAngle();
		
		System.out.println("mr: " + motherRotationAngle);
		System.out.println(ownRotationAngle);
		GL11.glPushMatrix();
		
		GL11.glColor3f(color[0], color[1], color[2]);
		
		if(motherRotationAngle > 0)
			GL11.glRotatef(motherRotationAngle, 0, 1, 0);
		
		GL11.glTranslatef(motherDistance, 0, 0);
		
		if(ownRotationAngle > 0)
			GL11.glRotatef(ownRotationAngle, 0, 1, 0);
		
		GLDrawHelper.drawSphere(size, 10, 10);
		
		if(!childPlanets.isEmpty()) {
			for(Planet child : childPlanets) {
				child.draw();
			}
		}
		
		
		GL11.glPopMatrix();
	}
	
	public float getMotherRotationAngle() {
		float day = getSimDay();
		try {
			float motherRotationAngle = day / yearLength * 360;
			return motherRotationAngle;
		} catch(Exception e) {
			return 0;
		}
	}
	
	public float getOwnRotationAngle() {
		float day = getSimDay();
		float ownRotationAngle = day * dayLength * 360;
		return ownRotationAngle;
	}
	
	public float getSimDay() {
		long timeElapsed = System.currentTimeMillis() - startTime;
		float day = (float)(timeElapsed / 1000.0) * 10;
		return day;
	}
}
