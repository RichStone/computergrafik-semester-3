import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Ex2Stars {

	boolean end = false;
	
	static int width = 1200;
	static int height = 800;
	
	float[] cameraPos = new float[]{0, 1500, 1500};
	
	static long initTime = System.currentTimeMillis();
	
	Planet sun, earth, moon;
	
	
	public static void main(String[] args) 
	{
		Ex2Stars app = new Ex2Stars();
		app.run();
	}
	
	public void run() 
	{
		init();
		while(!end) {
			update();
			draw();
		}
	}
	
	public void init() 
	{	
		try{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} 
		catch(LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(75.f, (float)width/height, 2.1f, 6000.f);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		addPlanets();
	}
	
	private void addPlanets() {
		moon = new Planet(10, 24, 300, 100);
		ArrayList<Planet> earthChild = new ArrayList<Planet>();
		earthChild.add(moon);
		
		float [] earthColor = {0.2f, 0.2f, 0.8f};
		earth = new Planet(50, 24, 360, 400, earthColor, earthChild);
		ArrayList<Planet> sunChildren = new ArrayList<Planet>();
		sunChildren.add(earth);
		
		float [] sunColor = {0.8f, 0.8f, 0.1f};
		sun = new Planet(200, 0, 0, 0, sunColor, sunChildren);
	}

	boolean keyPressed = false;
	boolean doWireframe = false;
	
	public void update() 
	{
		end = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Display.isCloseRequested();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if(!keyPressed) doWireframe = !doWireframe;
			keyPressed = true;
		}
		else {
			keyPressed = false;
		}
	}
	
	public void draw() 
	{	
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GLU.gluLookAt(cameraPos[0], cameraPos[1], cameraPos[2], 0, 0, 0, 0, 1, 0);
		
		if(doWireframe) {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		}
		else {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}
		
		sun.draw();
		
		Display.update();
		Display.sync(60);
	}

}
