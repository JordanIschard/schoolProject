<!DOCTYPE html>
<html lang="fr">
    <head>
      <title>Page de connexion</title>
      <meta charset="utf-8">
      <link rel="stylesheet" href="Vue/styleConnexion.css"/>
    </head>
    
	<body>
		<header>
			<img class="logo" src="Vue/MichelLOGO2.png" alt="logo"/>
			<h5>Un garage for you Forever!</h5>
			<h1 class="nom">Michel Garage</h1>
		</header>
		<div class="connecter">
			<form method="post" action="Garage.php">
				<fieldset>
					<legend>Connexion</legend>
					<p>
						<label for="id">Identifiant :</label>
							<input type="text" name="login" id="id" required>	
					</p>
					<p>
						<label for="pwd">Mot de passe :</label>
							<input type="password" name="password" id="pwd" required>
					</p>
					<p>
						<input type="submit" name="connexion" value="Se connecter">
					</p>
				</fieldset>
			</form>
		</div>
		<footer></footer>
	 </body>

</html>
