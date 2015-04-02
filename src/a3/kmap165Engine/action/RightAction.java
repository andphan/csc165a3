package a3.kmap165Engine.action;

import sage.input.action.AbstractInputAction;
import sage.app.AbstractGame;
import net.java.games.input.Event;
import sage.camera.*;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import graphicslib3D.Matrix3D;
import sage.scene.SceneNode;
import sage.scene.shape.*;

import a3.kmap165Engine.network.*;

public class RightAction extends AbstractInputAction{ 
   private SceneNode s;
   private Matrix3D sM;
   private MyClient client;
   public RightAction(SceneNode sn, MyClient thisClient){ 
      s = sn;
      sM = s.getLocalTranslation();
      client = thisClient;
   }
   public void performAction(float time, Event e){
      sM.translate(0.1f,0,0);
      s.setLocalTranslation(sM);
      s.updateWorldBound();
  //    client.sendMoveMessage(s.getLocalTranslation());
   }
}