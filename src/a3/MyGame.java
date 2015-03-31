package a3;

import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import sage.app.BaseGame;
import sage.camera.ICamera;
import sage.display.IDisplaySystem;
import sage.input.IInputManager;
import sage.input.action.AbstractInputAction;
import sage.input.action.IAction;
import sage.scene.Group;
import sage.scene.SceneNode;
import sage.scene.SkyBox;
import sage.scene.shape.Pyramid;
import sage.scene.shape.Teapot;
import sage.terrain.*;
import sage.texture.Texture;
import sage.texture.TextureManager;











import javax.script.*;

import net.java.games.input.Event;


public class MyGame extends BaseGame {

	private Group rootNode;
	private SkyBox skybox;
	private ICamera camera;
	private TerrainBlock parkingLot;
	private IDisplaySystem display;
	private Texture tf;
	private Group scene;
	
	// objects for testing aka players
	private Teapot p1, p2;
	
	private ScriptEngine engine;
	private String scriptName = "testScript.js";
	private File scriptFile;
	
	public static final String dir = "." + File.separator + "src" + File.separator + "a3"+ File.separator + "images" + File.separator; 
	public static final String textureT = "heightMapTest.bmp";
	public static final String textureB = "lotTest.bmp";
	public static final String textureC = "squaresquare.bmp";
	
	public static final String texDir = dir + textureT;
	public static final String texxDir = dir + textureB;
	public static final String texxxDir = dir + textureC;
	
	
	
	protected void initGame()
	{
		ScriptEngineManager factory = new ScriptEngineManager();
		List<ScriptEngineFactory> list = factory.getEngineFactories();
		engine = factory.getEngineByName("js");
		scriptFile = new File(scriptName);
		this.runScript();
		
		IInputManager im = getInputManager();
		String kbName = im.getKeyboardName();
		
		IAction testScriptB = new TestScriptAction();
		
		im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.SPACE, testScriptB, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
		
		initGameObjects();
		createScene();
	}
	
	private void initGameObjects()
	{
		
		
		IDisplaySystem display = getDisplaySystem();
		display.setTitle("Untitled Fighting Game");
		camera = display.getRenderer().getCamera();
		camera.setPerspectiveFrustum(45, 1, 0.01, 1000);
		camera.setLocation(new Point3D(1, 1, 20));
		
		p1 = new Teapot();
		addGameWorldObject(p1);
	
	}
	public void update(float time)
	{
		super.update(time);
	}
	private void createScene()
	{
		scene = new Group("Root Node");
		
		skybox = new SkyBox("SkyBox", 20.0f, 20.0f, 20.0f);
		
		Texture tex = TextureManager.loadTexture2D(texDir);
		Texture texF = TextureManager.loadTexture2D(texxDir);
		
		skybox.setTexture(SkyBox.Face.North, tex);
		skybox.setTexture(SkyBox.Face.South, tex);
		scene.addChild(skybox);
		
		Pyramid pyr = new Pyramid("Pyramid");
		pyr.translate(5, 2, 2);
		
	
		AbstractHeightMap heightmap = null;

		heightmap = new ImageBasedHeightMap(texF.getImage());
		heightmap.load();

		Vector3D scaleFactor = new Vector3D(new Point3D(1, 1, 1));
		
		try
		{
		parkingLot = new TerrainBlock("terrainname", 200, scaleFactor, heightmap.getHeightData(), new Point3D(0, 0, 0));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		scene.addChild(parkingLot);
		
		scene.addChild(pyr);
		addGameWorldObject(scene);
		
	}
	
	private void runScript()
	{
		try 
		{
			FileReader fR = new FileReader(scriptFile);
			engine.eval(fR);
			fR.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		catch (ScriptException e2)
		{
			e2.printStackTrace();
		}
		catch (NullPointerException e3)
		{
			e3.printStackTrace();
		}
	}
	
	public class TestScriptAction extends AbstractInputAction {
		
		public void performAction(float t, Event e)
		{
			Invocable invocable = (Invocable) engine;
			
			SceneNode player = p1;
			
			try
			{
				invocable.invokeFunction("testScriptB", player);
			}
			catch (ScriptException ea)
			{
				ea.printStackTrace();
			}
			catch (NoSuchMethodException aa)
			{
				aa.printStackTrace();
			}
			catch (NullPointerException e33)
			{
				e33.printStackTrace();
			}
			
		}
	}

}
