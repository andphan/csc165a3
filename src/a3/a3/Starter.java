package a3.a3;

import a3.games.fighter2015.*;
import a3.kmap165Engine.network.*;

public class Starter{
   public static void main(String[] args){
     FightingGame fg = new FightingGame("6945", 777);
     fg.start();
   }
}