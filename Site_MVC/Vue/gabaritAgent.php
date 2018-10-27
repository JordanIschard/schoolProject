	<!DOCTYPE html>
<html lang="fr">
    <head>
      <title>Ma page</title>
      <meta charset="utf-8">
      <link rel="stylesheet" href="Vue/styleAgent.css"/>
    </head>
    
	<body>
		<header>
    	<img class="logo" src="Vue/MichelLOGO2.png" alt="Logo">
    	<h3 class="nom">Agent</h3>
        <form  class="deco" action="Garage.php" method="post">
             <input class="deco" name="deconnexion" value="Déconnexion" type="submit">
        </form>
		</header>
    <div>
		  <?php echo $contenu ; ?>
    </div>
		<footer></footer>
  </body>

 </html>
 



 