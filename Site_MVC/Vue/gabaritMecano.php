<!DOCTYPE html>
<html lang="fr">
    <head>
      <title>Page Mécanicien</title>
      <meta charset="utf-8">
      <link rel="stylesheet" href="Vue/stylePageMeca.css"/>
    </head>
    <body>
    	<header>
    		<img class="logo" src="Vue/MichelLOGO2.png" alt="Logo"/>
    		<h3 class="nom">Mécanicien: <?php echo $personne ?></h3>
            <form action="Garage.php" method="post">
                    <p>
    		          <input class="deco" type="submit" name="deconnexion" value="Déconnexion"/>
                    </p>
            </form>
    	</header>
        <h5 class="t5">Pannel de contrôle de l'agenda</h5>
    	<div class="affichageAgendaMecano">
            <form method="post" action="Garage.php">
                <input type="hidden" name="nom" value="<?php echo $personne?>"/>
                <?php echo $agenda ?>
                <input class="t2" type="submit" name="choisirDate" value="Choisir"/>
                <input class="t3" type="submit" name="precedent" value="Precedent"/>
                <input class="t4" type="submit" name="suivant" value="Suivant"/>
                </br>
                <label class="t1" id="Formation">Pour placer une formation cochez la case</label>
                    <input class="t1" type="checkbox" id="Formation" name="choixFormation"/>
            </form>
        </div>
    	<aside>
            <h4>---Synthèse---</h4>
    		<?php echo $synthese?>
    	</aside>
    </body>
</html>
