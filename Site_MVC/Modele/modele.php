<?php

/************************************/
/************************************/
/**********Fonction commune**********/
/************************************/
/************************************/

/**Créer une connexion vers ma base de donnée
*
*return PDO connexion
*/
function getconnect(){
	require_once('connect.php');
	$connexion=new PDO('mysql:host='.SERVEUR.';dbname='.BDD,USER,PASSWORD);
	$connexion->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	$connexion->query('SET NAMES UTF8');

	return $connexion;
}

/*****************************************************/
/*****************************************************/
/**********Fonction pour la partie Connexion**********/
/*****************************************************/
/*****************************************************/

/**Donne le type d'employé selon le login et le mot de passe
*
*return String le type d'employé
*/
function getTypeEmploye($login,$password){
	$connexion=getconnect();
	$requete="SELECT * FROM Employe WHERE(login='$login' AND password='$password')";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$type=$resultat->fetchall();
	$resultat->closeCursor();
	$type=$type[0]->categorie;

	return $type;
}

/*****************************************************/
/*****************************************************/
/**********Fonction pour la patie Mécanicien**********/
/*****************************************************/
/*****************************************************/


function getSyntheseInter($numInter){
	$connexion=getconnect();
	$requete="SELECT DISTINCT * FROM ((SELECT * FROM Intervention WHERE NumIntervention=$numInter) AS COUCOU NATURAL JOIN Client NATURAL JOIN TypeIntervention)";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$intervention=$resultat->fetchall();
	$resultat->closeCursor();
	
	return $intervention;
}

/**Donne le nom de l'employé selon le login et le mot de passe
*
*return String nom de l'employé
*/
function getNomEmploye($login,$password){
	$connexion=getconnect();
	$requete="SELECT * FROM Employe WHERE(login='$login' AND password='$password')";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$nom=$resultat->fetchall();
	$resultat->closeCursor();
	$nom=$nom[0]->nomEmploye;

	return $nom;
}

/**Donne la liste des mécaniciens du garage
*
*return Tableau liste des mécaniciens
*/
function getMecanicien(){
	$connexion=getconnect();
	$requete="SELECT * from Employe WHERE categorie='mécanicien'";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$mecanos=$resultat->fetchall();
	$resultat->closeCursor();

	return $mecanos;
}

/**Donne la liste des interventions d'un mécanicien
*
*return Tableau liste des interventions d'un mécanicien
*/
function getIntervention($nom){
	$connexion=getconnect();
	$requete="SELECT * from Intervention WHERE nomEmploye='$nom'";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$intervention=$resultat->fetchall();
	$resultat->closeCursor();

	return $intervention;
}

/**Donne la liste des formations d'un mécanicien
*
*return Tableau liste des formations d'un mécanicien
*/
function getFormation($nom){
	$connexion=getconnect();
	$requete="SELECT * from Formation WHERE nomEmploye='$nom'";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$formation=$resultat->fetchall();
	$resultat->closeCursor();

	return $formation;
}

function AjouterFormation($date,$heure,$nom){
	$date1=$date->format('Y-m-d');
	$connexion=getconnect();
	$requete="INSERT INTO Formation(nomEmploye,date,heure) VALUES ('$nom','$date1',$heure)";
	$resultat=$connexion->query($requete);
	$resultat->closeCursor();
}

/***********************************************/
/***********************************************/
/**********Fonction pour le directeur***********/
/***********************************************/
/***********************************************/

function chercherTousLesEmployes(){   //donne la liste de tous les employes
	$connexion=getConnect(); 
	$requete="SELECT * from Employe";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$res=$resultat->fetchall();
	$resultat->closeCursor();
	return $res;
}

function chercherTousLesTypesIntervention(){ //donne la liste de tous les employes
	$connexion=getConnect(); 
	$requete="SELECT * from TypeIntervention";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$res=$resultat->fetchall();
	$resultat->closeCursor();
	return $res;
}
	
function ajouterEmploye($nomEmploye,$login,$pwd,$categorie){
	$connexion=getConnect(); 
	$requete="INSERT into Employe values('$nomEmploye','$login','$pwd','$categorie')";
	$resultat=$connexion->query($requete);
	$resultat->closeCursor();
}

function supprEmploye($login){
	$connexion=getConnect(); 
	$requete="DELETE from Employe where login='$login'";
	$resultat=$connexion->query($requete);
	$resultat->closeCursor();	
}

function modifEmploye($login,$newlogin,$newpwd){ 
	$connexion=getConnect();
	$requete="UPDATE Employe
			SET login='$newlogin',
				password='$newpwd'
			WHERE login='$login'";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$resultat->closeCursor();
}


function ajouteTypeIntervention($nom,$listeElements,$montant){
	$connexion=getConnect();
	$requete="INSERT into TypeIntervention values('$nom','$listeElements','$montant')";
	$resultat=$connexion->query($requete);
	$resultat->closeCursor();
}

function supprTypeInterv($nomIntervention){
	$connexion=getConnect(); 
	$requete="DELETE from TypeIntervention where nomIntervention='$nomIntervention'";
	$resultat=$connexion->query($requete);
	$resultat->closeCursor();	
}

function modifListe($nomIntervention,$newListeElements){
	$connexion=getConnect();
	$requete="UPDATE TypeIntervention
			SET listeElements='$newListeElements'
			WHERE nomIntervention='$nomIntervention'";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$resultat->closeCursor();
}

function modifMontant($nomIntervention,$newMontant){
	$connexion=getConnect();
	$requete="UPDATE TypeIntervention
			SET montant='$newMontant'
			WHERE nomIntervention='$nomIntervention'";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$resultat->closeCursor();
}

function modifNomIntervention($nomIntervention,$newNomIntervention){
	$connexion=getConnect();
	$requete="UPDATE TypeIntervention
			SET nomIntervention='$newNomIntervention'
			WHERE nomIntervention='$nomIntervention'";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$resultat->closeCursor();
}


function modifEmployeLogin($login,$newlogin){ 
	$connexion=getConnect();
	$requete="UPDATE Employe
			SET login='$newlogin'
			WHERE login='$login'";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$resultat->closeCursor();
}

function  modifEmployePwd($login,$newpwd){  
	$connexion=getConnect();
	$requete="UPDATE Employe
			SET password='$newpwd'
			WHERE login='$login'";
	$resultat=$connexion->query($requete);
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$resultat->closeCursor();
}

/****************************************/
/****************************************/
/*********Fonction pour l'agent**********/
/****************************************/
/****************************************/

// fonction pour rechercher un client avec son nom et sa date de naissance avec la requete on obtient toutes les infos du client indiqué par son nom et sa date de naissance qui sont passés en paramètres.la fonction retourne les infos dans la variable $clients(partie agent)
function rechercherClientNomDateNaiss($nom, $dateNaissance){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="SELECT * FROM Client WHERE nomClient='$nom' and dateNaissance='$dateNaissance'" ;       
	$resultat=$connexion->query($requete); 
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$clients=$resultat->fetchall(); 
	
	$resultat->closeCursor();
	return $clients; 

}
// fonction pour avoir toutes les infos du client avec son idClient passé en parametre.la fonction retourne les infos dans la variable $client(partie agent)
function getClient($clientId){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="SELECT * FROM Client where idClient =".$clientId."";       
	$resultat=$connexion->query($requete); 
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$clients=$resultat->fetchall(); 
	
	$resultat->closeCursor();
	foreach($clients as $ligne){
		$client = $ligne;
	}
	return $client; 

}
// fonction pour modifier un client dans la base de données.la requete nous permet de changer les valeurs encienne avec les nouvelles valeurs passées en parametre.(partie agent).
function modifierClient($idClient,$nomClient,$prenomClient,$adresseClient,$email,$montantMax,$numTel,$dateNaissance){

	$connexion=getConnect();  // création d'une connexion PDO
	$requete="UPDATE Client SET nomClient ='$nomClient', prenomClient = '$prenomClient', adresse = '$adresseClient', numTel='$numTel', mail='$email',montantMax='$montantMax' where idClient=".$idClient."" ;   
	$resultat=$connexion->query($requete); 
	$resultat->closeCursor();
}
//fonction pour ajouter un client a la base de données.la requete nous permet dinsirer les infos passées en parametre dans la BDD(partie agent)
function ajouterNouveauClient($nomClient,$prenomClient,$adresseClient,$email,$montantMax,$numTel,$dateNaissance){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="insert into Client values (NULL,'$nomClient','$prenomClient','$adresseClient','$numTel','$email','$montantMax','$dateNaissance')";  
	$resultat=$connexion->query($requete); 
	$resultat->closeCursor();
}
//fonction pour supprimer un client dans la BDD.la requete nous permet de supprimer le client qui a comme idClient passée en parametre.(partie agent)
function supprimerClient($idClient){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="DELETE from Client  where idClient=".$idClient."" ;   
	$resultat=$connexion->query($requete); 
	$resultat->closeCursor();
}


//fonction pour avoir la liste des mecaniciens from la BDD.la requete nous permet de retourner touts les employes dans la categorie =mecanicien.(partie agent)
function getListeMecano(){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="SELECT * FROM Employe WHERE categorie='mécanicien'";       
	$resultat=$connexion->query($requete); 
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$mecanos=$resultat->fetchall(); 
	$resultat->closeCursor();
	return $mecanos; 

}

function getTypeIntervention($nomTypeIntervention){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="SELECT * FROM TypeIntervention WHERE nomTypeIntervention=".$nomTypeIntervention."";       
	$resultat=$connexion->query($requete); 
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$typeIntervention=$resultat->fetchall(); 
	$resultat->closeCursor();
	return $typeIntervention; 
}

function getAllTypeIntervention(){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="SELECT * FROM TypeIntervention ";       
	$resultat=$connexion->query($requete); 
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$typeInterventions=$resultat->fetchall(); 
	$resultat->closeCursor();
	return $typeInterventions; 
}
//fonction pour ajouter une intervention dans la BDD.la requete nous permet d'entrer touts les infos passées en parametres dans la BDD.(partie agent)
function ajouterIntervention($nomTypeIntervention,$dateIntervention,$nomEmploye,$idClient){

	$heureIntervention=(int)substr($dateIntervention,11);
	$dateIntervention=substr($dateIntervention,0,10);
	$connexion=getConnect();  // création d'une connexion PDO

	//Vérifier si le client existe dans la bdd
	$requete="select * from Client where idClient ='".$idClient."'";
	//echo $requete;
	$resultat=$connexion->query($requete); 
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$result=$resultat->fetchall();
	$ok='true';
	$message ='';
	if (empty($result)){
		$ok=false;
		$message = '<p>Le client saisi n existe pas</p>';
	}
	$requete="select * from Formation  where nomEmploye  ='".$nomEmploye."' and date ='".$dateIntervention."' and heure ='".$heureIntervention."'";
	 $resultat=$connexion->query($requete); 
	 $resultat->setFetchMode(PDO::FETCH_OBJ);
	$result=$resultat->fetchall();
	if (!empty($result)){
		$ok='false';
		$message .= 'le mécanicien est en formation';
	}

	
	
	if($ok == 'true'){
	$requete="insert into Intervention values (NULL,'$nomTypeIntervention','$nomEmploye','$idClient','$dateIntervention','$heureIntervention','en attente de payement')"; 
	$resultat=$connexion->query($requete); 
}
	$resultat->closeCursor();
	return 	$message;
}
//fonction pour avoir la liste des elements a aporter from la BDD.la requete nous permets de recuperer la liste de l'intervention qui a le meme type que celui passé en parametre.la fontion retourne les infos dans la variable $listElements.(partie agent)
function getListElement($nomTypeIntervention){
	$connexion=getConnect(); 
	$requete2="SELECT *  FROM TypeIntervention where nomIntervention = '".$nomTypeIntervention."'"; 
	$resultat2=$connexion->query($requete2); 
	$resultat2->setFetchMode(PDO::FETCH_OBJ);
	$listElements = $resultat2->fetchall(); 
	$resultat2->closeCursor();
	return $listElements;
}
//fonction pour avoir toutes les infos sur le client qui a comme idClient celui passé en parametre.la requete nous permet davoir tt les infos from la BDD et puis la fonction nous retourne les infos dans la variable $client.(partie agent)
function getIdentiteClient($idClient){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="SELECT * FROM Client WHERE idClient='".$idClient."'" ;       
	$resultat=$connexion->query($requete); 
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$client=$resultat->fetchall(); 
	$resultat->closeCursor();
	return $client; 
}

function getClientViaInter($num){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="SELECT * FROM (SELECT * FROM Intervention WHERE numIntervention='".$num."') AS Num,Client WHERE Num.idClient=Client.idClient" ;       
	$resultat=$connexion->query($requete); 
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$client=$resultat->fetchall(); 
	$resultat->closeCursor();
	return $client; 
}

function getMontantInterventionViaNum($num){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="SELECT montant FROM (Select * FROM Intervention WHERE numIntervention='".$num."') AS Inter,TypeIntervention Where Inter.nomTypeIntervention=TypeIntervention.nomIntervention;" ;       
	$resultat=$connexion->query($requete); 
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$montant=$resultat->fetchall(); 
	$resultat->closeCursor();
	$montant=$montant[0]->montant;
	return $montant; 
}

//fonction pour recuperer la liste des interventions d'un client qui a comme idClient celui passé en parametre et seulement celles qui sont en différes.la fonction nous retourne les ifos dans la variable $interventions.(partie agent)
function getListeInterventionClient($idClient){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="SELECT * FROM Intervention interv, TypeIntervention typeinterv where interv.idClient = '".$idClient."' and interv.nomTypeIntervention= typeinterv.nomIntervention"; 
	//$requete="SELECT * FROM intervention where idClient ='".$idClient."'";       
	$resultat=$connexion->query($requete); 
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$interventions=$resultat->fetchall(); 
	$resultat->closeCursor();
	return $interventions; 

}
//la fonction pour avoir les interventions from la BDD pour leurs faire la gestion de payement.la requete nous permet de recuperer celles d'un client qui a comme idClient celui passé en parametre.la fonction retourne les resultas dans la variable $interventions.(partie agent)
function getInterventionPourLeursGestion($idClient){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="SELECT * FROM Intervention where idClient ='".$idClient."' and etat ='en différé'";       
	$resultat=$connexion->query($requete); 
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$interventions=$resultat->fetchall(); 
	$resultat->closeCursor();
	return $interventions; 

}

//fonction pour recuperer de la BDD la derniere intervention dun client qui a comme idClient celui passé en parametre.la fonction retourne les resultats dans la variable $intervention.(partie agent)
function getLastInterventionNonPaye($idClient){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="SELECT * FROM Intervention where idClient ='".$idClient."' and etat ='en attente de payement' ORDER BY dateInter DESC LIMIT 0,1"; 
	$resultat=$connexion->query($requete); 
	$resultat->setFetchMode(PDO::FETCH_OBJ);
	$intervention=$resultat->fetchall(); 
	$resultat->closeCursor();
	return $intervention; 
}

//fonction pour modifier letat de l'intervention.(partie agent)
function updateIntervention($numeroIntervention, $etatIntervention){
	$connexion=getConnect();  // création d'une connexion PDO
	$requete="UPDATE Intervention SET etat  ='$etatIntervention' where numIntervention=".$numeroIntervention."";  
	$resultat=$connexion->query($requete); 
	$resultat->closeCursor();

}

function updateInterventions($interventions){
	$t = 0;
	$t1 = 1;
	for ($numero = 0; $numero < sizeof($interventions) ; $numero++){
		if(isset($interventions[$numero][$t])){
			$connexion=getConnect();  // création d'une connexion PDO
			$requete="UPDATE Intervention SET etat  ='payée' where numIntervention='".$interventions[$numero][$t1]."'"; 
			echo $requete ;
			$resultat=$connexion->query($requete); 
			$resultat->closeCursor();
		}

	}
}



/*********Reste**********/