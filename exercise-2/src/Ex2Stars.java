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
	
	float[] cameraPos = new float[]{0, 0, 1500};
	
	
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
		GLU.gluPerspective(45.f, (float)width/height, 1.1f, 6000.f);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
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
	
	float motherRotationAngle = 0;
	float ownRotationAngle = 0;
	float motherDistance = 400;
	
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
		
		//increase rotation value
		motherRotationAngle += 1.0f;
		ownRotationAngle += 1.0f;
		
		//draw sun
		GL11.glColor3f(0.8f, 0.8f, 0.1f);
		GL11.glRotatef(motherRotationAngle, 0, 1, 0);
		GLDrawHelper.drawSphere(200, 10, 10);
		
		//draw earth
		GL11.glTranslatef(motherDistance, 0, 0);
		GL11.glColor3f(0.2f, 0.2f, 0.8f);
		GL11.glRotatef(ownRotationAngle, 0, 1, 0);
		GLDrawHelper.drawSphere(100, 10, 10);
		
		Display.update();
		Display.sync(25);
	}

}
