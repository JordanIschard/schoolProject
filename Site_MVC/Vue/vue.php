<?php

/*****************************************************/
/*****************************************************/
/**********Fonction pour la partie Connexion**********/
/*****************************************************/
/*****************************************************/

/**Affiche la page de connexion
*/
function afficherPageConnexion(){
	require_once('gabaritConnexion.php');
}

/******************************************************/
/******************************************************/
/**********Fonction pour la partie Mécanicien**********/
/******************************************************/
/******************************************************/

/**Affiche l'accueil de la page du mécanicien
*/
function afficherAccueilMecano($date,$nom,$intervention,$formation){

	$agenda=afficherAgendaMecano($date,$intervention,$formation);
	$personne=$nom;
	require_once('gabaritMecano.php');
}


/**Affiche l'accueil de la page du mécanicien
*/
function afficherAccueilMecanoSyn($date,$nom,$intervention,$formation,$synthese){

	$agenda=afficherAgendaMecano($date,$intervention,$formation);
	$personne=$nom;
	require_once('gabaritMecano.php');
}


/*Affiche l'agenda pour le mécanicien
*
*return String Agenda
*/
function afficherAgendaMecano($date,$intervention,$formation){
	$contenu='';
	$contenu.=afficherMoisAnnee($date);
	$contenu.=afficherJourSemaine($date,$intervention,$formation);
	$contenu.='<input class="t" type="date" name="date" value="'.($date)->format('Y-m-d').'"/>';
	
	return $contenu;
}

/*Affiche le mois et l'année de l'agenda
*
*return String début du tableau
*/
function afficherMoisAnnee($dateDebutSemaine){
	$contenu='';
	$contenu.='<table class="agendaMeca">';
	$contenu.='<tr><td class="vide" rowspan="2"></td><th colspan="7">'
	.$dateDebutSemaine->format('F Y').'</th></tr>';

	return $contenu;
}

/*Affiche chaque jour de la semaine sous forme de 6 colonnes
*
*return String les jours de la semaine
*/
function afficherJourSemaine($dateDebutSemaine,$intervention,$formation){
	$contenu='';
	$contenu.='<tr>';
	$date=createDate($dateDebutSemaine->format('d/m/Y H:i:s'));
	$jour=1;
	do{
		$contenu.='<th>'.$date->format('D d').'</th>';
		$date->add(new DateInterval('P1D'));
		$jour++;
	}while($jour<=6);
	$contenu.='</tr>';
	$contenu.=afficherHeureJour($dateDebutSemaine,$intervention,$formation);
	$contenu.='</table>';

	return $contenu;
}

/**Affiche chaque heure compris entre 8h et 19h avec son état
*
*return String les heures des jours
*/
function afficherHeureJour($dateDebutSemaine,$intervention,$formation){
	$contenu='';
	$heure=8;
	while($heure<=19){
		$contenu.='<tr>';
		$contenu.='<td class="heure">'.$heure.'</td>';
		$jour=1;
		$date=createDate($dateDebutSemaine->format('d/m/Y H:i:s'));
		$date=dateAvecHeureChoisie($date,$heure);
		while($jour<=6){
			$contenu.=etatHeure($date,$intervention,$formation);
			$date->add(new DateInterval('P1D'));
			$jour++;
		}
		$contenu.='</tr>';
		$heure++;
	}

	return $contenu;
}


/**Attribut un affichage pour chaque case selon son état:
*'heure de pause','heure libre','heure d'intervention',
*'heure de formation' et 'heure courante'
*
*return String affichage de la case
*/
function etatHeure($date0,$intervention,$formation){
	$datecourante=getcurrentDate();
	$date=$date0;

	if($date->format('H')=='12' || $date->format('H')=='13' || ($date->format('D')=='Sat' && (int)($date->format('H'))>12)){
		return '<td  class="pause"><p><input class="Pause" type="submit" name="" value="PAUSE" disabled="true"/><p></td>';
	}else{
		if($datecourante>$date){
			return '<td  class="desactiver"><p><input class="agenda" type="submit" name="" value="" disabled="true"/><p></td>';
		}else{

			foreach ($intervention as $ligne) {
				$date1=date_create_from_format('Y-m-d',$ligne->dateInter);
				$date1=dateAvecHeureChoisie($date1,$ligne->heure);

				if($date==$date1){
					return '<td  class="intervention"><p>'.$ligne->nomTypeIntervention.' <input  class="inter"  type="submit" name="inter" value="'.$ligne->numIntervention.'"/><p></td>';
				}
			}
			foreach ($formation as $ligne) {
				$date1=date_create_from_format('Y-m-d',$ligne->date);
				$date1=dateAvecHeureChoisie($date1,$ligne->heure);

				if($date==$date1){
					return '<td  class="formation"><p><input  class="forma"  type="submit" name="formation" value="Formation" disabled="true"/><p></td>';
				}
			}
			if($datecourante==$date){
				return '<td  class="heurecourante"><p><input  class="agenda"  type="submit" name="choix" value="'.$date->format('Y-m-d H').'"/><p></td>';
			}else{
				return '<td  class="activer"><p><input  class="agenda"  type="submit" name="choix" value="'.$date->format('Y-m-d H').'"/><p></td>';
			}

		}
	}
}


function afficherSyntheseInter($intervention){
	$Synthese.='<div class="syn"><h4>--Information sur l\'intervention--</h4>';
	$Synthese.='<p>		-Informationom de l\'intervention: '.$intervention[0]->nomTypeIntervention.'</p>';
	$Synthese.='<p>		-Liste des éléments nécessaires: '.$intervention[0]->listeElements.'</p>';
	$Synthese.='<p>		-Montant: '.$intervention[0]->montant.' euros</p>';
	$Synthese.='<p>		-Horaire: '.$intervention[0]->heure.'h </p>';
	$Synthese.='<h4>--Information Client--</h4>';
	$Synthese.='<p>		-Nom et prénom: '.$intervention[0]->prenomClient.' '.$intervention[0]->nomClient.'</p>';
	$Synthese.='<p>		-Adresse: '.$intervention[0]->adresse.'</p>';
	$Synthese.='<p>		-Numéro de téléphone: 0'.$intervention[0]->numTel.'</p>';
	$Synthese.='<p>		-Adresse email: '.$intervention[0]->mail.'</p></div>';
	return $Synthese;
}

/**Créer une date à partir d'une chaine donnée
*
*return DATE date
*/
function createDate($chaine){
	$date= date_create_from_format('d/m/Y H:i:s',$chaine);

	return $date;
}

/**Créer un DATETIME avec une date et une choisit
*
*return DATETIME date
*/
function dateAvecHeureChoisie($date,$heureVoulue){
	$heureActuelle=(int)$date->format('H');
	$date=$date->sub(new DateInterval('PT'.$heureActuelle.'H'));
	$date=$date->add(new DateInterval('PT'.$heureVoulue.'H'));

	return $date;
}

/**Vérifie si la date donnée correspond à un lundi
*
*return Boolean est un lundi
*/
function estLundi($date){
	if($date->format('D')=='Mon'){
		return true;
	}
	return false;
}

/**Donne la date courante
*
*return DATETIME date courante
*/
function getcurrentDate(){
	$dtz = new DateTimeZone("Europe/Madrid");
	$now = new DateTime(date("Y-m-d H:i:s"), $dtz);
	
	return $now;
}

/**Donne le premier jour de la semaine
*
*return DATETIME date du premier jour de la semaine
*/
function getfirstDayOfWeek($date){
	while(!estLundi($date)){
		$date->sub(new DateInterval('P1D'));
	}

	return $date;
}

/**********************************************/
/**********************************************/
/**********Fonction pour le directeur**********/
/**********************************************/
/**********************************************/

function afficherAcceuilDirecteur(){
	$contenu=' ';
	require_once('gabaritDirecteur.php');
}


function dChoixActionInterf(){
	$contenu='<fieldset>
			<legend>Souhaitez vous creer, supprimer ou modifier ?</legend>
			<form action="Garage.php" method="post">
				<input type="submit" value="creer" name= "dAjoutEmploye" />
				<input type="submit" value="supprimer ou modifier" name= "dSupprModifEmploye" />
			</form>
		</fieldset>';
	
	require_once('gabaritDirecteur.php');
}


function dAfficherSupprEmploye($employe){ 
	$contenu='<fieldset>
			<legend>Quel employe souhaitez vous supprimer ou modifier ?</legend>
			<form action="Garage.php" method="post">
			<p>
				<label for="login">Employes :</label>       
				<select name="login">';
	foreach ($employe as $ligne){
		//$contenu.='<option value="'.$ligne->login.'">Login:'.$ligne->login.' ;Categorie:'.$ligne->categorie.'</option>';    AUTRE AFFICHAGE
		$contenu.='<option value="'.$ligne->login.'">'.$ligne->nomEmploye.'</option>';
	}
	$contenu.='</select></p>
	<p><input type="submit" value="Supprimer Employe" name= "supprEmploye" />
		<input type="submit" value="Modifier Employe" name= "modifEmploye" />
	</p></form></fieldset>';
	require_once('gabaritDirecteur.php');
}

function dAfficherModifEmploye($login){
	$contenu='<fieldset>
				<legend>Saisissez les nouvelles informations</legend>
					<form action="Garage.php" method="post">
					<p>
						<label for="login">Actuel login :</label>
						<input type="text" name="login" readonly="readonly" value="'.$login.' " />
					</p>
					<p>
						<label for="login">Nouveau login :</label>
						<input type="text" name="newlogin" />
					</p>
					<p>
						<label for="pwd">Nouveau mot de passe :</label>
						<input type="text" name="newpwd" />
					</p>
					<input type="submit" value="changer login et mot de passe" name= "changeEmploye" />
				</form>
			</fieldset>';
	require_once('gabaritDirecteur.php');
}


function afficherDAjouterEmploye(){        //afficher le formulaire nécessaire pour creer un nouvel employe
	$contenu='<fieldset><legend>Ajouter un employe</legend>
					<form action="Garage.php" method="post">
					<p>
						<label for="nom">Nom :</label>
						<input type="text" name="nomEmploye"/>
					</p>
					<p>
						<label for="login">Login :</label>
						<input type="text" name="login" />
					</p>
					<p>
						<label for="pwd">Mot de passe :</label>
						<input type="text" name="pwd" />
					</p>
					<p>
						<label for="categorie">Categorie :</label>       
						<select name="categorie">
							<option value="agent" selected >Agent</option>
							<option value="mecanicien">Mécanicien</option>
							<option value="directeur">Directeur</option>
						</select>
					</p>
					<p> <input type="submit" value="Ajouter employe" name= "ajoutEmploye" />
					</form>
				</fieldset>';
	require_once('gabaritDirecteur.php');                       // /!\ On ne peut pas creer Directeur <option value="directeur">Directeur</option>                                   
}
	
	
	
function dChoixActionInterv(){
	$contenu='<fieldset>
			<legend>Souhaitez vous creer, supprimer ou modifier ?</legend>
			<form action="Garage.php" method="post">
				<input type="submit" value="creer" name= "dAjoutTypeInterv" />
				<input type="submit" value="supprimer ou modifier" name= "dSupprModifInterv" />
			</form>
		</fieldset>';
	
	require_once('gabaritDirecteur.php');
}

function afficherDAjouterInterv(){        //afficher le formulaire nécessaire pour creer un nouveaux type d'intervention
	$contenu='<fieldset><legend>Ajouter un type d intervention</legend>
					<form action="Garage.php" method="post">
					<p>
						<label for="nom">Nom du type :</label>
						<input type="text" name="nom"/>
					</p>
					<p>
						<label for="listElements">liste des éléments :</label>
						<input type="text" name="listeElements" />
					</p>
					<p>
						<label for="montant">Montant :</label>
						<input type="text" name="montant" />
					</p>
					<p> <input type="submit" value="Ajouter un type d intervention" name= "ajoutTypeInterv" />
					</form>
				</fieldset>';
	require_once('gabaritDirecteur.php');
}

function dAfficherSupprInterv($interv){
	$contenu='<fieldset>
			<legend>Quel type d intervention souhaitez vous supprimer ou modifier ?</legend>
			<form action="Garage.php" method="post">
			<p>
				<label for="nomIntervention">Type d intervention :</label>       
				<select name="nomIntervention">';
				
	foreach ($interv as $ligne){
		$contenu.='<option value="'.$ligne->nomIntervention.'">'.$ligne->nomIntervention.'</option>';
	}
	
	$contenu.='</select></p>
		<p><input type="submit" value="Supprimer type" name= "supprTypeInterv" />
			<input type="submit" value="Modifier type" name= "modifTypeInterv" />
		</p></form></fieldset>';
	require_once('gabaritDirecteur.php');
}

function dAfficherModifTypeInterv($nomIntervention){
	$contenu='<fieldset>
				<legend>Que voulez vous modifier ?</legend>
					<form action="Garage.php" method="post">
					<p>
						<label for="nomIntervention">Actuel nom d intervention :</label>
						<input type="text" name="nomIntervention" readonly="readonly" value="'.$nomIntervention.'" />
					</p>
					<input type="submit" value="changer liste des élements à fournir" name= "changeListeElements" />
					<input type="submit" value="changer Montant" name= "changeMontant" />
					<input type="submit" value="change nom intervention" name= "changeNomIntervention" />
				</form>
			</fieldset>';
	require_once('gabaritDirecteur.php');
}

function  DAfficherChangeListeElements($interv,$nomIntervention){
	$contenu='<fieldset><legend>Changement de la liste des éléments à fournir</legend>
				<form action="Garage.php" method="post">
				<p>
					<label for="nomIntervention">Actuel nom d intervention :</label>
					<input type="text" name="nomIntervention" readonly="readonly" value="'.$nomIntervention.'" />
					<label for="listeElements">Actuel liste  des élements :</label>';
	foreach ($interv as $ligne){
		if($ligne->nomIntervention==$nomIntervention){
			$contenu.='<input type="text" name="listeElements" readonly="readonly" value="'.$ligne->listeElements.' " />';
		}
	}
	$contenu.='</p>
				<p>
					<label for="listeElements">Nouvelle liste d élements à fournir :</label>
					<input type="text" name="newListeElements" />
				</p>
				<input type="submit" value="valider" name= "modifListe" />
				</form>
			</fieldset>';
	require_once('gabaritDirecteur.php');
}

function DAfficherChangeMontant($interv,$nomIntervention){
	$contenu='<fieldset><legend>Changement du montant</legend>
				<form action="Garage.php" method="post">
				<p>
					<label for="nomIntervention">Actuel nom d intervention :</label>
					<input type="text" name="nomIntervention" readonly="readonly" value="'.$nomIntervention.'" />
					<label for="montant">Actuel montant :</label>';
	foreach ($interv as $ligne){
		if($ligne->nomIntervention==$nomIntervention){
			$contenu.='<input type="text" name="montant" readonly="readonly" value="'.$ligne->montant.' " />';
		}
	}
	$contenu.='</p>
				<p>
					<label for="montant">Nouveau montant:</label>
					<input type="text" name="newMontant" />
				</p>
				<input type="submit" value="valider" name= "modifMontant" />
				</form>
			</fieldset>';
	require_once('gabaritDirecteur.php');
}

function DAfficherChangeNomIntervention($interv,$nomIntervention){
	$contenu='<fieldset><legend>Changement du nom</legend>
				<form action="Garage.php" method="post">
				<p>
					<label for="nomIntervention">Actuel nom d intervention :</label>
					<input type="text" name="nomIntervention" readonly="readonly" value="'.$nomIntervention.'" />
				</p>
				<p>
					<label for="montant">Nouveau nom de type d intervention:</label>
					<input type="text" name="newNomIntervention" />
				</p>
				<input type="submit" value="valider" name= "modifNomIntervention" />
				</form>
			</fieldset>';
	require_once('gabaritDirecteur.php');
}

function afficherChoixTypeInterv($interv){
	$contenu='<fieldset>
			<legend>De quel type d intervention souhaitez vous modifier la liste d éléments à fournir ?</legend>
			<form action="Garage.php" method="post">
			<p>
				<label for="nomIntervention">Type d intervention :</label>       
				<select name="nomIntervention">';
				
	foreach ($interv as $ligne){
		$contenu.='<option value="'.$ligne->nomIntervention.'">'.$ligne->nomIntervention.'</option>';
	}
	
	$contenu.='</select></p>
		<p><input type="submit" value="Modifier" name= "changeListeElements" />
		</p></form></fieldset>';
	require_once('gabaritDirecteur.php');
}


/*****************************************/
/*****************************************/
/*********Fonction pour l'agent***********/
/*****************************************/
/*****************************************/


	// variable qui contient le menu principal et tous les boutons principaux.(partie agent)
 
$GLOBALS['menu'] =  	'<form class="gabarit" name="agent" id="monForm"  action="Garage.php" method="post">
	 			<fieldset class="gabarit">
					 <INPUT type="submit" class="bouton" value="Rechercher un Client" name="boutonRechercherClient" />
					 <INPUT type="submit" class="bouton" value="Ajouter un client" name="boutonAjouterClient" />
					 <INPUT type="submit" class="bouton" value="Synthèse  Client" name="boutonSynthèseClient" />
					 <INPUT type="submit" class="bouton" value="Gestion Financière" name="boutonGestionFinanciere" />
					 <INPUT type="submit" class="bouton" value="Gestion des Rendez-vous" name="boutonGestionRNV" />
				</fieldset>  
		  	</form>';



		  		// // fonction du menu principal elle contient la variable $menu .(partie agent)
	 function afficherEspaceAgentAccueil(){
	   $contenu=$GLOBALS['menu'];
	    require_once('gabaritAgent.php'); 
	}


// fonction qui cree le formulaire pour rechercher un client par son nom et sa date de naissance elle contient un bouton pour valider la recherche.(partie agent)
 function rechercherClient(){
	   $contenu=$GLOBALS['menu'];
		   $contenu.='
		   <fieldset>
	  			 <legend> Recherche un client par son nom </legend>
		   <form name="rechercher" id="monForm2"  action="Garage.php" method="post">
	 			 <p>
	  			<label for="Nom">  Nom du client :  </label> 
	  			 <INPUT type="input" name="nomClient" id="Nom" required/>
	  			</p>
			   <p>
			  	<label for="dateNaiss">  Date de naissance :  </label> 
			  	 <INPUT type="date" name="dateNaissanceClient id=dateNaiss" required />
			 <p>
			 </p>
			     <INPUT type="submit" class="bouton" value="Rechercher" name="boutonRechercherClientParNomDateNaiss" /> 
			 </p>
			</form></fieldset>';
	    require_once('gabaritAgent.php'); 
	}	

// fonction qui affiche le formulaire pour ajouter un client contient un bouton enrigistrer pour valider l'ajout.(partie agent)
function afficherFormAjoutClient(){
	   $contenu=$GLOBALS['menu'];
		   $contenu.=
			'<form name="ajouterClient" id="monForm"  action="Garage.php" method="post">
		 	<fieldset>
		        <legend> Ajouter un client </legend>
		  <p><label>  Nom : </label> <INPUT type="input" name="name" /></p> 
		  <p> <label>  Prenom : </label> <INPUT type="input" name="prenom" /></p>
		  <p> 
		  	<label>  Date de naissance : </label> <INPUT type="date" name="dateDeNaissance" />
		  </p>
		 	<p>
			<label for="adresse :">Adresse :</label>
			<textarea name="adresse"></textarea>
			</p>

		  <p> <label>  NumTel : </label> <INPUT type="input" name="numTel" /></p>
		  <p> <label>  email : </label> <INPUT type="input" name="email" /></p>
		  <p> <label>  Decouvert : </label> <INPUT type="input" name="decouvert" /></p>

		  <p>
		    <INPUT type="submit" class="bouton" value="Enregistrer" name="boutonAjouterNouveauClient" />
			</p>
		</fieldset>  
		</form>';
	    require_once('gabaritAgent.php'); 
	}
	// fonction pour afficher le formulaire de la recher he du client et le resultat de la recherche sous forme dun tableau. le tableau contient deux boutons supprimer pour supprimer un client et un bouton editer pour afficher les infos du client on peut les modifier aussi.(partie agent)
	function afficherRechercheClients($clients){
		$contenu=$GLOBALS['menu'];
		$contenu.='
		   <form name="rechercher" id="monForm2"  action="Garage.php" method="post">
	 			<fieldset>
	  			 <legend> Recherche un client par son nom </legend>
	 			 <p>
	  			<label>  Nom du client :  </label> 
	  			 <INPUT type="input" name="nomClient"  />
	  			</p>
			   <p>
			  	<label>  Date de naissance :  </label> 
			  	 <INPUT type="date" name="dateNaissanceClient" />
			  </p>
			 <p>
			     <INPUT type="submit" class="bouton" value="Rechercher" name="boutonRechercherClientParNomDateNaiss" /> 
			 </p>
				 </fieldset>
			</form>';
		$contenu.='<h4 class="rech">Resultats de la recherche : </h4>';


		if($clients!=null){
			$contenu.='<table class="intervRech" border = "1">
			<tr><td>id</td><td>nom</td><td>prenom</td><td>adresse</td><td>nomTel</td><td>email</td><td>decouvert max</td><td>action</td></tr>';
			foreach($clients as $ligne){
				$tableauClient='';
				$tableauClient.= 
						 '<tr>
							 <td>'.$ligne->idClient.'</td>
							 <td>'.$ligne->nomClient.'</td>
							 <td>'.$ligne->prenomClient.'</td>
							 <td>'.$ligne->adresse.'</td>
							 <td>0'.$ligne->numTel.'</td>
							 <td>'.$ligne->mail.'</td>
							 <td>'.$ligne->montantMax.'</td>
							 <td> 
								 <form name="Editer" id="monForm3"  action="Garage.php" method="post">
								 	<INPUT type="hidden" name="clientId" value = "'.$ligne->idClient.'" />
								 	<INPUT type="submit" class="bouton" value="Editer" name="boutonEditerClient" />	&#129;	&#129;
								 	<INPUT type="submit" class="bouton" value="Supprimer" name="boutonSupprimerClient" />
								 </form>
							 </td>
						 </tr>'
						;
						}
			$contenu.= $tableauClient .'</table>';
	} 
		else{
			$contenu.= '<p>Aucun résultat</p>';
		}

		  require_once('gabaritAgent.php'); 

	}


	 //la fonction pour afficher le formulaire de modifier un client rechercher contient un bouton enregistrer.(partie agent).
	function EditerClient($client){
	   $contenu=$GLOBALS['menu'];
	   
		   $contenu.=
			'<form name="modifierClient" id="monForm"  action="Garage.php" method="post">
		 	<fieldset>
		        <legend> modifier un client </legend>
		        <p><label>  idClient: </label><INPUT type="input" disable name="idClient" value = "'.$client->idClient.'"/></p>
		  <p><label>  Nom : </label> <INPUT type="input" name="name" value = "'.$client->nomClient.'"/></p> 
		  <p> <label>  Prenom : </label> <INPUT type="input" name="prenom" value = "'.$client->prenomClient.'" /></p>
		  <p> 
		  	<label>  Date de naissance : </label> <INPUT type="input" name="dateDeNaissance" value = "'.$client->dateNaissance.'" />
		  </p>
		 	<p>
			<label for="adresse :">Adresse</label><textarea name="adresse">'.$client->adresse.'</textarea>
			</p>

		  <p> <label>  NumTel : </label> <INPUT type="input" name="numTel"  value = "0'.$client->numTel.'"/></p>
		  <p> <label>  email: </label> <INPUT type="input" name="email" value = "'.$client->mail.'" /></p>
		  <p> <label>  Decouvert : </label> <INPUT type="input" name="decouvert" value = "'.$client->montantMax.'" /></p>

		  <p>
		    <INPUT type="submit" class="bouton" value="Enregistrer" name="boutonModifierClient" />
			</p>
		</fieldset>  
		</form>';
	
	    require_once('gabaritAgent.php'); 
	}
	// fonction pour afficher un message.(partie agent)
	function confirmationAjoutNouveauClient(){
	   $contenu=$GLOBALS['menu'];
	   $contenu.='<h4>Le client a été ajouté avec succès !</h4>';
	    require_once('gabaritAgent.php'); 
	}

	// fonction qui permet d'afficher le formulaire pour ajouter une intervention. la fonction contient un bouton valider.(partie agent)

	


	function gestionDesRDVChoixMecano($mecanos){
		$contenu=$GLOBALS['menu'];

	  	$contenu.='<fieldset>
	  	<form name="Choix Mecanicien" id="monForm6"  action="Garage.php" method="post">
		<p>';
		$contenu.='<label>  Nom de mécanicien : </label> 
		       <select id="select" name="nomEmploye">';
		       		$optionSelect ='';
			       		foreach($mecanos as $ligne){
						  	$optionSelect .='<option value="'.$ligne->nomEmploye.'">'.$ligne->nomEmploye.'</option> ';
						}
				
					$contenu.=$optionSelect;
		$contenu.='</select></p>';
		$contenu.='<input type="submit" class="bouton" name="boutonChoixMecano" value="Choisir le mécanicien"></form></fieldset>';
		require_once('gabaritAgent.php'); 
	}

	function gestionDesRendezVous($typeInterventions,$agenda){
		$contenu=$GLOBALS['menu'];

	  	 $contenu.='<fieldset><legend>Créer une intervention</legend>
	  	 <h6>Remplir le champ Client,Choisir une intervention et Cliquer sur le créneau
	  	 voulue</h6>
	  	 <form class="t1" name="Ajout Intervention" id="monForm5"  action="Garage.php" method="post">
		<p>';
				$contenu.=$agenda;
				$contenu.='<p>
				<input class="bouton" type="submit" name="choisirDateAgent" value="Choisir"/>
                <input class="bouton" type="submit" name="precedentAgent" value="Precedent"/>
                <input class="bouton" type="submit" name="suivantAgent" value="Suivant"/>
                </p>
				<p>
				<label> Id client : </label><INPUT type="text"  name="idClient" />
				</p>
				<p>
				<label>  Type intervention : </label> 

		       <select id="selecttypeInterventionName" name="typeInterventionName">';
		       		$optionIntervSelect = '';
			       		foreach($typeInterventions as $ligne){
						  	$optionIntervSelect .='<option value="'.$ligne->nomIntervention.'">'.$ligne->nomIntervention.'</option> ';
						}
				
					$contenu.=$optionIntervSelect;

				$contenu.=
				'</select>
				</p>
				</form></fieldset>';
				require_once('gabaritAgent.php'); 

	}
	// fonction pour afficher un message plus la liste des elements à apporter.(partie agent)
		function confirmationAjoutIntervention($listElements, $message){
	   $contenu=$GLOBALS['menu'];
	   if($message == ''){
	   $contenu.='<fieldset><h4>L\'intervention a été ajoutée avec succès !</h4>
	   <p>La liste des élements à apporter est:</p>';
	   foreach($listElements as $ligne){
	   	$contenu.=$ligne->listeElements  ;
		
		}
	}else{
		$contenu.=$message;
	}
	$contenu.='</fieldset>';   
	    require_once('gabaritAgent.php'); 
	}


	//fonction qui affiche un formulaire  de rechercche de client par son idClient elle contient un bouton syntehese client pour voir les infos sur le client plus les interventions realisées.(partie agent)
	function rechercheIdentitieClient(){
		 $contenu=$GLOBALS['menu'];
	
		   $contenu.=
			'<form name="identiteClient" id="monForm"  action="Garage.php" method="post">
		 	<fieldset>
		        <legend> Recherche client et sa liste des interventions</legend>
		  <p><label>  idClient : </label> <INPUT type="text" name="idClient" /></p> 
		 
		  <p>
		    <INPUT type="submit" class="bouton" value="Synthèse client" name="boutonSyntheseClientIntervention" />
			</p>
		</fieldset>  
		</form>';
		require_once('gabaritAgent.php'); 
	}

	// fonction pour afficher les infos du clients plus ses interventions(partie agent)
	function rechercheIdentiteClientEtInterventions($client,$interventions){
	   $contenu=$GLOBALS['menu'];
		
		 $contenu.=
			'<form name="identiteClient" id="monForm"  action="Garage.php" method="post">
		 	<fieldset>
		        <legend> Recherche client et sa liste des interventions</legend>
		  <p><label>  idClient : </label> <INPUT type="text" name="idClient" /></p> 
		 
		  <p>
		    <INPUT type="submit" class="bouton" value="Synthèse client" name="boutonSyntheseClientIntervention" />
			</p>
		</fieldset>  
		</form>';

		

		if($client!=null){
			
			foreach($client as $ligne){
				$formClient='';
				$formClient.= 
							 '<fieldset class="field2">
		       					 <legend> Identité du client </legend>
							 <p> id :'.$ligne->idClient.'</p>
							 <p>nomClient :'.$ligne->nomClient.'</p>
							 <p>prenomClient :'.$ligne->prenomClient.'</p>
							 <p>adresseClient :'.$ligne->adresse.'</td>
							 <p>numTel :0'.$ligne->numTel.'</p>
							 <p>email :'.$ligne->mail.'</p>
							 <p>montantMax :'.$ligne->montantMax.'</p>
							</fieldset>'
							
						;
						}
			$contenu.= $formClient;
	} 
		else{
			$contenu.= '<h5>Aucun client</h5>';
		}




		


		if($interventions!=null){
		
			$contenu.='<table class="interv" border = "1">
			<tr><td>Numéro intervention</td><td>dateIntervention</td><td>etat</td><td>heureIntervention</td><td>idClient</td><td>nomEmploye</td><td>Type Intervention</td><td>Montant</td></tr>';
			$tableauInterventions='';
			$montantDiffere = 0;
			foreach($interventions as $ligne){

				$tableauInterventions.= 
						 '<tr>
						 	<td>'.$ligne->numIntervention.'</td>
							 <td>'.$ligne->dateInter.'</td>
							 <td>'.$ligne->etat.'</td>
							 <td>'.$ligne->heure.'</td>
							 <td>'.$ligne->idClient.'</td>
							 <td>'.$ligne->nomEmploye.'</td>
							 <td>'.$ligne->nomTypeIntervention.'</td>
							 <td>'.$ligne->montant.'</td>
						 </tr>'
						;
						$idClient = $ligne->idClient;
						if($ligne->etat=='en différé'){
							$montantDiffere = $montantDiffere + $ligne->montant;
						}
			}
			$contenu.= $tableauInterventions .'</table><div class="end">';
			$contenu.='<p>Le montant total des différés en cours :' ;
			$contenu.= $montantDiffere;
			$contenu.='</p><p>Le montant maximum des différés :' ;
			$client = CtlEditerClient($idClient);
			$contenu.= $client->montantMax.'</p>' ;
			$contenu.='</p><p>Le montant disponible pour des différés ultérieurs :' ;
			$contenu.=  $client->montantMax - $montantDiffere ;
			$contenu.='</div>';

	} 
		else{
			$contenu.= '<fieldset><p>Aucun résultat</p></fieldset>';
		}
	    require_once('gabaritAgent.php'); 
	}


	//fonction qui affiche le menu gestion de payement elle contient un bouton valider.(partie agent)
	function gestionDePayement(){
		 $contenu=$GLOBALS['menu'];
	
		   $contenu.=
			'<form name="identiteClient" id="monForm"  action="Garage.php" method="post">
		 	<fieldset>
		        <legend>Gestion de payement </legend>
		  <p><label>  idClient : </label> <INPUT type="text" name="idClient" /></p> 
		 
		  <p>
		    <INPUT type="submit" class="bouton" value="Valider" name="boutonValiderGestionClient" />
			</p>
		</fieldset>  
		</form>';
		require_once('gabaritAgent.php'); 

	}
	// fonction pour afficher les interventions d'un client par leurs etat.(partie agent).

	function afficherInterventionParEtat($interventionNonPayee, $interventions){
		   $contenu=$GLOBALS['menu'];
		  
		    $contenu.='<form name="formInterventionNonPayee" id="monForm"  action="Garage.php" method="post">
		 	<fieldset><legend>La dernière intervention en attente de payement</legend>';
		   foreach($interventionNonPayee as $ligne){
		   $contenu.='<p><label>  Numéro intervention : </label></label> <INPUT type="text" name="numeroIntervention"   value = "'.$ligne->numIntervention.'" /></p>';
		   $contenu.='<p><label>  Nom intervention : </label>' .$ligne->nomTypeIntervention.'</p>';
		    $contenu.='<p><label>  Date intervention : </label>' .$ligne->dateInter.'</p>';
		     $contenu.='<p><label>  Heure intervention : </label>' .$ligne->heure.'</p>';
		      $contenu.='<p><label>  Nom employe : </label>' .$ligne->nomEmploye.'</p>';
		      
		  $contenu.='<p><label>  Etat à choisir: </label> 
		   <select id="selectetatInterventionName" name="etatIntervention">
		  		<option value="payée">Payée</option>
		  		<option value="en différé">En différé</option>
		  </select>
		  </p>
		  <INPUT type="submit" class="bouton" value="Valider" name="boutonPayerIntervention" />
		  ';


		  $contenu.='</fieldset></form>';

		}



		if($interventions!=null){
		
			$contenu.='<fieldset class="field3"><legend> Listes des interventions en différe</legend>';
			$contenu.='<form name="formlistInterventionNon" id="monForm"  action="Garage.php" method="post">';
			$contenu.='<table class="interv3" border = "1">
			<tr><td></td><td>dateIntervention</td><td>etat</td><td>heureIntervention</td><td>idClient</td><td>nomEmploye</td><td>nomIntervention</td><td>numIntervention</td></tr>';
			$tableauInterventions='';
			$i = 0;
			
			foreach($interventions as $ligne){

				$tableauInterventions.= 
						 '<tr>
						 	
							<td><input id="checkBox" name="intervention['.$i.'][0]" type="checkbox"></td>
							<input type ="hidden" name="intervention['.$i.'][1]"  value = "'.$ligne->numIntervention.'" />
							
							 <td>'.$ligne->dateInter.'</td>
							 <td>'.$ligne->etat.'</td>
							 <td>'.$ligne->heure.'</td>
							 <td>'.$ligne->idClient.'</td>
							 <td>'.$ligne->nomEmploye.'</td>
							 <td>'.$ligne->nomTypeIntervention.'</td>
							 <td>'.$ligne->numIntervention.'</td>
							 
							
						 </tr>'
						;
						$i = $i+ 1;
						}


			$contenu.= $tableauInterventions .'</table>';
			 $contenu.='<INPUT type="submit" class="bouton" value="Payer" name="boutonPayerInterventionDifferees" />';
			$contenu.='</form></fieldset>';
	} 
		else{
			$contenu.= '<h5>Aucune intervention différée</h5>';
		}
	    require_once('gabaritAgent.php'); 

	}

	//Fonction pour confirmer l'enrigistrement de l'état de l'intervention
	function confirmationModificationIntervention($etat){
		 $contenu=$GLOBALS['menu'];
		$contenu .= '<h4>L\'intervention est '.$etat.'!</h4>';
		 require_once('gabaritAgent.php'); 
	}

		//Fonction pour confirmer l'echec de l'enrigistrement de l'état de l'intervention
	function echecModificationIntervention(){
		 $contenu=$GLOBALS['menu'];
		$contenu .= '<h5>Impossible de mettre en différé: Plafond trop bas!</h5>';
		 require_once('gabaritAgent.php'); 
	}


function afficherAgendaMecanoPourAgent($nom,$date,$intervention,$formation){
	$contenu='';
	$contenu.=afficherMoisAnnee($date);
	$contenu.=afficherJourSemainePourAgent($date,$intervention,$formation);
	$contenu.='</br>';
	$contenu.='</br>';
	$contenu.='<input class="t" type="date" name="dateAgent" value="'.($date)->format('Y-m-d').'"/>';
	$contenu.='<input type="hidden" name="nom" value="'.$nom.'"/>';
	return $contenu;
}

/*Affiche chaque jour de la semaine sous forme de 6 colonnes
*
*return String les jours de la semaine
*/
function afficherJourSemainePourAgent($dateDebutSemaine,$intervention,$formation){
	$contenu='';
	$contenu.='<tr>';
	$date=createDate($dateDebutSemaine->format('d/m/Y H:i:s'));
	$jour=1;
	do{
		$contenu.='<th>'.$date->format('D d').'</th>';
		$date->add(new DateInterval('P1D'));
		$jour++;
	}while($jour<=6);
	$contenu.='</tr>';
	$contenu.=afficherHeureJourPourAgent($dateDebutSemaine,$intervention,$formation);
	$contenu.='</table>';

	return $contenu;
}

/**Affiche chaque heure compris entre 8h et 19h avec son état
*
*return String les heures des jours
*/
function afficherHeureJourPourAgent($dateDebutSemaine,$intervention,$formation){
	$contenu='';
	$heure=8;
	while($heure<=19){
		$contenu.='<tr>';
		$contenu.='<td class="heure">'.$heure.'</td>';
		$jour=1;
		$date=createDate($dateDebutSemaine->format('d/m/Y H:i:s'));
		$date=dateAvecHeureChoisie($date,$heure);
		while($jour<=6){
			$contenu.=etatHeurePourAgent($date,$intervention,$formation);
			$date->add(new DateInterval('P1D'));
			$jour++;
		}
		$contenu.='</tr>';
		$heure++;
	}

	return $contenu;
}

/**Attribut un affichage pour chaque case selon son état:
*'heure de pause','heure libre','heure d'intervention',
*'heure de formation' et 'heure courante'
*
*return String affichage de la case
*/
function etatHeurePourAgent($date0,$intervention,$formation){
	$datecourante=getcurrentDate();
	$date=$date0;

	if($date->format('H')=='12' || $date->format('H')=='13' || ($date->format('D')=='Sat' && (int)($date->format('H'))>12)){
		return '<td  class="pause"><p><input class="Pause" type="submit" name="" value="PAUSE" disabled="true"/><p></td>';
	}else{
		if($datecourante>$date){
			return '<td  class="desactiver"><p><input class="agenda" type="submit" name="" value="" disabled="true"/><p></td>';
		}else{

			foreach ($intervention as $ligne) {
				$date1=date_create_from_format('Y-m-d',$ligne->dateInter);
				$date1=dateAvecHeureChoisie($date1,$ligne->heure);

				if($date==$date1){
					return '<td  class="intervention"><p><input  class="inter"  type="submit" name="" value="'.$ligne->nomTypeIntervention.'" disabled="true"/><p></td>';
				}
			}
			foreach ($formation as $ligne) {
				$date1=date_create_from_format('Y-m-d',$ligne->date);
				$date1=dateAvecHeureChoisie($date1,$ligne->heure);

				if($date==$date1){
					return '<td  class="formation"><p><input  class="forma"  type="submit" name="formation" value="Formation" disabled="true"/><p></td>';
				}
			}
			if($datecourante==$date){
				return '<td  class="heurecourante"><p><input  class="agenda"  type="submit" name="choix" value="'.$date->format('Y-m-d H').'" disabled="true"/><p></td>';
			}else{
				return '<td  class="activer"><p><input  class="agenda"  type="submit" name="choixAgent" value="'.$date->format('Y-m-d H').'"/><p></td>';
			}

		}
	}
}

	






/**********Reste**********/


function afficherErreur($erreur){  
	$contenu='<p>'. $erreur.'</p>
		<h5 class="erreur"><a href="Garage.php"/> Revenir à l acceuil </a></h5>';
	require_once('gabaritConnexion.php');
}

function afficherErreurChampC(){ 
	$contenu='<h5 class="exception">Un ou plusieurs champs sont invalides</h5>';
	require_once('gabaritConnexion.php');
}


function afficherErreurChampD(){ 
	$contenu='<h5 class="exception">Un ou plusieurs champs sont invalides</h5>';
	require_once('gabaritDirecteur.php');
}

function afficherErreurChampA(){  
	$contenu='<h5 class="exception">Un ou plusieurs champs sont invalides</h5>';
	require_once('gabaritAgent.php');
}

function afficherErreurSupprDirecteur(){ 
	$contenu='<h5 class="exception">On ne peut pas suprimer le directeur !</h5>';
	require_once('gabaritDirecteur.php');
}

function afficherErreurLoginExistant(){ 
	$contenu='<h5 class="exception">Ce login est déjà utilisé</h5>';
	require_once('gabaritDirecteur.php');
}



