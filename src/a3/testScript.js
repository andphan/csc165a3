var JavaPackages = new JavaImporter(
 Packages.java.awt.Color,
 Packages.sage.scene.shape.Teapot);
 
 with (JavaPackages)
 {
   function updateCharacter(character)
    {
       character.setColor(java.awt.Color.orange);
    }
 }