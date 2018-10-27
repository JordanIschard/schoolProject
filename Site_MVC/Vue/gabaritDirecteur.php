<!DOCTYPE html>
<html lang="fr">
    <head>
      <title>Page du directeur</title>
      <meta charset="utf-8">
	  <link rel="stylesheet"  href="Vue/styleDirecteur.css" />
	  
    </head>
    
	<body>	
	
	<header>
    	<img class="logo" src="Vue/MichelLOGO2.png" alt="Logo">
    	<h3 class="nom">Directeur</h3>
        <form action="Garage.php" method="post">
          <p>
             <input class="deco" name="deconnexion" value="Déconnexion" type="submit">
          </p>
        </form>
	</header>
	<fieldset class="gabarit">
		<legend>Que voulez vous faire ?</legend>
		<form action="Garage.php" method="post">
			<input type="submit" value="creer, supprimer ou modifier un employé" name= "interf" />     
			<input type="submit" value="creer, supprimer ou modifier intervention possibles" name= "interv" />
			<input type="submit" value="modifier la liste des elements a fournir par le client" name= "elem" />
		</form>
	</fieldset>
	
		
	  <?php echo $contenu;?> 
	  
	<footer></footer>  
  </body>

 </html>
 
