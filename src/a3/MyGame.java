package a3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import sage.app.BaseGame;
import sage.camera.ICamera;
import sage.display.IDisplaySystem;
import sage.scene.Group;
import sage.scene.SceneNode;
import sage.scene.SkyBox;
import sage.scene.shape.Pyramid;
import sage.terrain.*;
import sage.texture.Texture;
import sage.texture.TextureManager;


public class MyGame extends BaseGame {

	private Group rootNode;
	private SkyBox skybox;
	private ICamera camera;
	private TerrainBlock parkingLot;
	private IDisplaySystem display;
	private Group scene;
	public static final String dir = "." + File.separator + "src" + File.separator + "a3"+ File.separator + "images" + File.separator; 
	public static final String textureT = "heightMapTest.bmp";
	public static final String textureB = "lotTest.bmp";
	public static final String textureC = "testFloor.bmp";
	
	public static final String texDir = dir + textureT;
	public static final String texxDir = dir + textureB;
	public static final String texxxDir = dir + textureC;
	
	
	
	public void initGame()
	{
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

		
		
		skybox.setTexture(SkyBox.Face.North, tex);
		skybox.setTexture(SkyBox.Face.South, tex);
		scene.addChild(skybox);
		
		Pyramid pyr = new Pyramid("Pyramid");
		pyr.translate(5, 2, 2);
		
		
		
		AbstractHeightMap heightmap = null;

	//	Texture heightMapImage = TextureManager.loadTexture2D(texxxDir);		
	//	heightmap.setHeightScale(0);
	//	heightmap.setHeightAtPoint(100, 10, 10);
	//	heightmap.setSize(25);
    //	heightmap.load();
		

//		parkingLot = new TerrainBlock("Terrain", 65, new Vector3D(10, 10, 10), null, new Point3D(0, 0, 0));
		
	//    scene.addChild(parkingLot);
		
		scene.addChild(pyr);
		addGameWorldObject(scene);
		
	}
}
