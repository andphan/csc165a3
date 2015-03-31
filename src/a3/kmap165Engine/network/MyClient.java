package a3.kmap165Engine.network;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

import sage.networking.client.GameConnectionClient;
import sage.networking.server.IClientInfo;

import java.util.Vector;

import a3.games.fighter2015.FightingGame;
import a3.kmap165Engine.network.ghost_avatar.GhostAvatar;
import graphicslib3D.Vector3D;
import a3.games.fighter2015.*;
import a3.kmap165Engine.network.ghost_avatar.*;

public class MyClient extends GameConnectionClient{ 
   private FightingGame game;
   private UUID id;
   private Vector<GhostAvatar> ghostAvatars;
   
   public MyClient(InetAddress remAddr, int remPort, ProtocolType pType,
      FightingGame game) throws IOException{ 
      
      super(remAddr, remPort, pType);
      this.game = game;
      this.id = UUID.randomUUID();
      this.ghostAvatars = new Vector<GhostAvatar>();
   }
   protected void processPacket (Object msg){ // override
      String message = (String) msg;
      String[] messageTokens = message.split(",");
      // extract incoming message into substrings. Then process:
      if(messageTokens[0].compareTo("join") == 0){ // receive “join”
         // format: join, success or join, failure
         if(messageTokens[1].compareTo("success") == 0){
            game.setIsConnected(true);
            sendCreateMessage(game.getPlayerPosition());
         }
         if(messageTokens[1].compareTo("failure") == 0)
            game.setIsConnected(false);
      }
      if(messageTokens[0].compareTo("bye") == 0){ // receive “bye”
         // format: bye, remoteId
         UUID ghostID = UUID.fromString(messageTokens[1]);
         sendByeMessage();
         removeGhostAvatar(ghostID);
      }
      if (messageTokens[0].compareTo("dsfr") == 0 ){ // receive “details for”
         // format: create, remoteId, x,y,z or dsfr, remoteId, x,y,z
         UUID ghostID = UUID.fromString(messageTokens[1]);
         Vector3D ghostPosition = new Vector3D();
         // extract ghost x,y,z, position from message, then:
         createGhostAvatar(ghostID, ghostPosition);
      }
      if(messageTokens[0].compareTo("create") == 0){ // receive “create…”
         // format: create, remoteId, x,y,z 
         UUID ghostID = UUID.fromString(messageTokens[1]);
         String[] ghostPosition = {messageTokens[2],messageTokens[3],messageTokens[4]};
         // extract ghost x,y,z, position from message, then:
         updateGhostAvatar(ghostID, ghostPosition);
      }
      if(messageTokens[0].compareTo("wsds") == 0){ // receive “wants…”
         // format: wsds, remoteID
         UUID remoteID = UUID.fromString(messageTokens[1]);
         Vector3D pos = new Vector3D();
         sendDetailsForMessage(remoteID, pos);
      }
      if(messageTokens[0].compareTo("move") == 0){ // receive “move”
         // format: create, remoteId, x,y,z 
         //String[] ghostPosition = {messageTokens[2],messageTokens[3]messageTokens[4]};
         UUID remoteID = UUID.fromString(messageTokens[1]);
         Vector3D pos = new Vector3D();
         sendDetailsForMessage(remoteID, pos);
      }
   }
   public void sendCreateMessage(Vector3D pos){
      // format: (create, localId, x,y,z)
      try{
         String message = new String("create," + id.toString());
         message += "," + pos.getX()+"," + pos.getY() + "," + pos.getZ();
         sendPacket(message);
      }
      catch (IOException e) { 
         e.printStackTrace(); 
      }
   }
   public void sendJoinMessage(){ 
      // format: join, localId
      try{ 
         sendPacket(new String("join," + id.toString())); 
      }
      catch (IOException e) { 
         e.printStackTrace(); 
      }
   }
   public void sendByeMessage(){
      // format: bye, localId
      try{ 
         sendPacket(new String("bye," + id.toString())); 
      }
      catch (IOException e) { 
         e.printStackTrace(); 
      }
   }
   public void sendDetailsForMessage(UUID remId, Vector3D pos){
      // format: (dsfr, remId, localID, x,y,z)
      try{
         String message = new String("dsfr," + remId.toString()+"," + id.toString());
         message += "," + pos.getX()+"," + pos.getY() + "," + pos.getZ();
         sendPacket(message); 
      }
      catch (IOException e) { 
         e.printStackTrace(); 
      } 
   }
   public void sendMoveMessage(Vector3D pos){
      // format: (move, localId, x,y,z)
      try{ 
         String message = new String("move," + id.toString());
         message += "," + pos.getX()+"," + pos.getY() + "," + pos.getZ();
         sendPacket(message); 
      }
      catch (IOException e) { 
         e.printStackTrace(); 
      }
   }
   private void removeGhostAvatar(UUID ghostID){
      ghostAvatars.remove(ghostID); 
   }
   private void createGhostAvatar(UUID ghostID, Vector3D ghostPosition){
   }
   private void updateGhostAvatar(UUID ghostID, String[] pos){
   }   
}