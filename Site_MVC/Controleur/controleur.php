<?php

require_once('Vue/vue.php');
require_once('Modele/modele.php');

/******************************************************/
/******************************************************/
/**********Fonctions pour la partie Connexion**********/
/******************************************************/
/******************************************************/

/**Contrôle qui gère la page de connexion
*
*/
function CtlPageConnexion(){
	afficherPageConnexion();
}

/**Contrôle qui gère le type d'employé donné
*
*return String type de l'employé
*/
function CtlTypeEmploye($login,$password){
	$typeEmploye=getTypeEmploye($login,$password);

	return $typeEmploye;
}

/*******************************************************/
/*******************************************************/
/**********Fonctions pour la partie Mécanicien**********/
/*******************************************************/
/*******************************************************/

function CtlAjouterFormation($date,$nom){
	$date1= date_create_from_format('Y-m-d H',$date);
	$heure=(int)$date1->format('H');
	AjouterFormation($date1,$heure,$nom);
}

function CtlIntervention($date,$nom,$numInter){
	$synthese=afficherSyntheseInter(getSyntheseInter($numInter));
	$date0=date_create_from_format('Y-m-d',$date);
	$date0=getfirstDayOfWeek($date0);
	CtlAccueilMecanoSyn($date0,$nom,$synthese);
}

function CtlAccueilMecanoSyn($date,$nom,$synthese){
	afficherAccueilMecanoSyn($date,$nom,getIntervention($nom),getFormation($nom),$synthese);
}

/**Contrôle l'affichage de la page d'accueil du mécano
*
*/
function CtlAccueilMecano($date,$nom){
	afficherAccueilMecano($date,$nom,getIntervention($nom),getFormation($nom));
}

/**Contrôle l'affichage de la page d'accueil du mécanicien
*selon une date choisie
*
*/
function CtlChoisirDate($date,$nom){
	$date0=date_create_from_format('Y-m-d',$date);
	$date0=getfirstDayOfWeek($date0);

	CtlAccueilMecano($date0,$nom);
}

/**Contrôle l'affichage de la page d'accueil du mécanicien
*avec l'agenda montrant une semaine antérieur
*
*/
function CtlSemainePrecedente($date,$nom){
	$date0=date_create_from_format('Y-m-d',$date);

	$date0=$date0->sub(new DateInterval('P7D'));
	$date0=getfirstDayOfWeek($date0);

	CtlAccueilMecano($date0,$nom);
}

/**Contrôle l'affichage de la page d'accueil du mécanicien
*avec l'agenda montrant une semaine postérieur
*
*/
function CtlSemaineSuivante($date,$nom){
	$date0=date_create_from_format('Y-m-d',$date);

	$date0=$date0->add(new DateInterval('P7D'));
	$date0=getfirstDayOfWeek($date0);

	CtlAccueilMecano($date0,$nom);
}

/**Contrôle l'affichage de la page d'accueil du mécanicien 
*avec l'agenda de la semaine courante
*
*/
function CtlSemaineCourante($nom){
	$date0=getcurrentDate();
	$date0=getfirstDayOfWeek($date0);

	CtlAccueilMecano($date0,$nom);
}

/**Contrôle qui gère le nom de l'employé
*
*return String nom de l'employé
*/
function CtlEmploye($login,$pwd){
	return getNomEmploye($login,$pwd);
}


/***********************************************/
/***********************************************/
/**********Fonctions pour le directeur**********/
/***********************************************/
/***********************************************/

function ctlAcceuilDirecteur(){
	afficherAcceuilDirecteur();
}

function ctlChoixActionInterf(){
	dChoixActionInterf();
}

function ctlDAjoutEmploye(){     //afficher le formulaire nécessaire pour creer un nouvel employe
	afficherDAjouterEmploye();
}

function ctlAjouterEmploye($nomEmploye,$login,$pwd,$categorie){  //creer nouvel employe
	if(!empty($nomEmploye) && !empty($login) && !empty($pwd) && !empty($categorie)){ 
	$employe=chercherTousLesEmployes();
	foreach ($employe as $ligne){
		if($ligne->login==$login){throw new Exception(afficherErreurLoginExistant()); }  //verifie que login n'est pas déjà utilisé
	}
		ajouterEmploye($nomEmploye,$login,$pwd,$categorie);        //fonction du modèle
		afficherAcceuilDirecteur();
	}
	else {throw new Exception(afficherErreurChamp()); }
}

function ctlDSupprimerEmploye(){  //affiche le formulaire nécessaire pour supprimer ou modifier un employe   
	$employe=chercherTousLesEmployes();
	dAfficherSupprEmploye($employe);
}

function ctlSupprEmploye($login){  //supprime un employe
	$employe=chercherTousLesEmployes();
	supprEmploye($login);         //fonction du modele
}

function ctlDModifierEmploye($login){  //affiche le formumaire nécessaire pour modifier un employe
	dAfficherModifEmploye($login);
}

function ctlModifEmploye($login,$newlogin,$newpwd){   //modifie un employe 
	if(!empty($login)){
		if(empty($newpwd) && empty($newlogin)){
			throw new Exception(afficherErreurChamp());
		}
		if(empty($newpwd)){
			modifEmployeLogin($login,$newlogin);    //fonction du modele
			afficherAcceuil();
		}else{
			$employe=chercherTousLesEmployes();
			foreach ($employe as $ligne){
				if(($ligne->login)==$newlogin){throw new Exception(afficherErreurLoginExistant()); } //verifie que login n'est pas déjà utilisé
			} 
			if(empty($newlogin)){
				modifEmployePwd($login,$newpwd);   //fonction du modele
			}else{
				modifEmploye($login,$newlogin,$newpwd);   //fonction du modele
			}
			afficherAcceuilDirecteur();
		}
	}
	else {throw new Exception(afficherErreurChamp()); }
}


function ctlChoixActionInterv(){
	dChoixActionInterv();
}

function ctlDAjoutTypeInterv(){ //afficher le formulaire nécessaire pour creer un nouveau type d'intervention
	afficherDAjouterInterv();
}

function ctlAjouterTypeIntervention($nom,$listeElements,$montant){ //creer un nouveau type d'intervention 
	if(!empty($nom) && !empty($listeElements) && !empty($montant) && !is_int($montant)){
		ajouteTypeIntervention($nom,$listeElements,$montant);
		afficherAcceuilDirecteur();
	}
	else {throw new Exception(afficherErreurChamp()); }
}

function ctlDSupprimerTypeInterv(){  //affiche le formulaire nécessaire pour supprimer ou modifier un type  d'intervention
	$interv=chercherTousLesTypesIntervention();  //fonction du modele
	dAfficherSupprInterv($interv);        //fonction de la vue
}

function ctlSupprTypeInterv($nomIntervention){ //supprime un employe
	supprTypeInterv($nomIntervention);         //fonction du modele
}

function ctlDModifierTypeInterv($login){  //affiche le formumaire nécessaire pour modifier un type d'intervention
	dAfficherModifTypeInterv($login);   // fonction de la vue
}

function ctlDAfficherChangeListeElements($nomIntervention){ //affiche le formumaire nécessaire pour modifierla liste des éléments
	$interv=chercherTousLesTypesIntervention();  //fonction du modele
	DAfficherChangeListeElements($interv,$nomIntervention);
}

function cltModifListe($nomIntervention,$newListeElements){ 
	if(!empty($nomIntervention) && !empty($newListeElements)){
		modifListe($nomIntervention,$newListeElements);  //fonction du modele
		afficherAcceuilDirecteur();
	}
	else {throw new Exception(afficherErreurChamp()); }
}

function ctlAfficherChangerMontant($nomIntervention){   //affiche le formumaire nécessaire pour modifier le montant
	$interv=chercherTousLesTypesIntervention();  //fonction du modele
	DAfficherChangeMontant($interv,$nomIntervention);
}

function cltModifMontant($nomIntervention,$newMontant){ 
	if(!empty($nomIntervention) && !empty($newMontant)){  
		modifMontant($nomIntervention,$newMontant);  //fonction du modele
		afficherAcceuil();
	}
	else {throw new Exception(afficherErreurChamp()); }
}

function ctlDAfficherChangeNomInterv($nomIntervention){  //affiche le formumaire nécessaire pour modifier le nom du type d'intervention
	$interv=chercherTousLesTypesIntervention();  //fonction du modele
	DAfficherChangeNomIntervention($interv,$nomIntervention);
}

function cltModifNomIntervention($nomIntervention,$newNomIntervention){
	if(!empty($nomIntervention) && !empty($newNomIntervention)){
		modifNomIntervention($nomIntervention,$newNomIntervention);  //fonction du modele
		afficherAcceuil();
	}
	else {throw new Exception(afficherErreurChamp()); }
}

function ctlDirectListeElement(){
	$interv=chercherTousLesTypesIntervention();  //fonction du modele
	afficherChoixTypeInterv($interv);                //fonction de la vue
}

/****************************************/
/****************************************/
/**********Focntion pour l'agent*********/
/****************************************/
/****************************************/

//fonction qui appelle la fonction de la vue (rechercheClient()) pour laffichage du formulaire.(partie agent)
function ctlAfficherForumRechercherClient(){
	rechercherClient();
}
//fonction qui retourne un resultat de la fonction rechercherClientNomDateNaiss() du modele en lui passant les parametres nessecaires dans la variable $clients.(partie agent)
function CtlRechercherClientParNomDateNaiss($nom, $dateNaissance){
	$clients = rechercherClientNomDateNaiss($nom, $dateNaissance);
	return $clients;
}
//fonction qui retourne un resultat de la fonction getClient() du modele en lui passant le parametre nessecaire dans la variable $client.(partie agent)
function CtlEditerClient($clientId){
	$client=getClient($clientId);
	return $client;
}
//fonction qui appelle la fonction modifierClientlient() du modele en lui passant les parametres nessecaires.(partie agent)
function CtlboutonModifierClient($idClient,$nomClient,$prenomClient,$adresseClient,$email,$montantMax,$numTel,$dateNaissance){
	 modifierClient($idClient,$nomClient,$prenomClient,$adresseClient,$email,$montantMax,$numTel,$dateNaissance);

}
//fonction qui appelle la fonction ajouterNouveauClient () du modele en lui passant les parametres nessecaires.(partie agent)
function CtlboutonAjouterNouveauClient($nomClient,$prenomClient,$adresseClient,$email,$montantMax,$numTel,$dateNaissance){
	$numTel=(string)$numTel;
	$numTel=substr($numTel,1);
	$numTel=(int)$numTel;
	 ajouterNouveauClient($nomClient,$prenomClient,$adresseClient,$email,$montantMax,$numTel,$dateNaissance);

}
//fonction qui retourne le resultat de la fonction getListeMecano() du modele dans la variable $mecanos?(partie agent)
function CtlgetListeMecano(){
	$mecanos = getListeMecano();
	return $mecanos;

}
//fonction qui appelle la methode supprimerClient() du modele avec la parametre nessecaire .(partie agent)
function CtlSupprimerClient($idClient){
	 supprimerClient($idClient);

}
//fonction qui retourne le resultat de la focntion getTypeIntervention() du modele en lui passant les parametres nessecaires(partie agent).
function CtlGetTypeIntervention($nomTypeIntervention){
	 return getTypeIntervention($nomTypeIntervention);

}

//fonction qui retourne le resultat de la fonction getAllTypeIntervention() du modele (partie agent)
function CtlGetAllTypeIntervention(){
	 return getAllTypeIntervention();

}
//fonction qui appelle la fonction ajouterIntervention ()  from modele avec les parametres nessecaires (partie agent)
function CtlajouterIntervention($nomTypeIntervention,$dateIntervention,$nomEmploye,$idClient){
	$message = ajouterIntervention($nomTypeIntervention,$dateIntervention,$nomEmploye,$idClient);
	return $message;
}
//fonction qui retourne le resultat de la fonction  getListElement() from modele avec les parametres nessecaires dans la variable $listElements.(partie agent)
function CtlGetListElement($nomTypeIntervention){
	$listElements = getListElement($nomTypeIntervention);
	return $listElements;
}
//fonction qui retourne le resultat de la fonction  getIdentiteClient() from modele avec les parametres nessecaires dans la variable $client (partie agent)
function CtlgetIdentiteClient($idClient){
	$client = getIdentiteClient($idClient);
	return $client;

}
//fonction qui retourne le resultat de la fonction  getListeInterventionClient() from modele avec les parametres nessecaires dans la variable $interventions (partie agent) 
function CtlgetListeInterventionClient($idClient){
	$interventions=getListeInterventionClient($idClient);
	return $interventions;
}

//fonction qui retourne le resultat de la fonction getInterventionPourLeursGestion() from modele avec le parametre nessecaire dans la variable $interventions (partie agent) 
function CtlgetInterventionPourLeursGestion($idClient){
	$interventions=getInterventionPourLeursGestion($idClient);
	return $interventions;
}
//fonction qui retourne le resultat de la fonction getLastInterventionNonPaye() from modele avec le parametre nessecaire dans la variable $intervention (partie agent) 
function CtlgetLastInterventionNonPaye($idClient){
	$intervention=getLastInterventionNonPaye($idClient);
	return $intervention;
}

function CtlMontantMaxNonDepasse($numeroIntervention){
	$Client=getClientViaInter($numeroIntervention);
	$idClient=$Client[0]->idClient;
	$interventions=getInterventionPourLeursGestion($idClient);
	$Montantintervention=(int)getMontantInterventionViaNum($numeroIntervention);
	$cpt=0;
	foreach ($intervention as $ligne) {
		$cpt+=$ligne->montant;
	}
	$plafond=(int)$Client[0]->montantMax;
	if($plafond-($cpt+$Montantintervention)>0){
		return true;
	}else{
		return false;
	}
}

function CtlupdateIntervention($numeroIntervention, $etatIntervention){
	if(CtlMontantMaxNonDepasse($numeroIntervention) || $etatIntervention=='payée'){
		updateIntervention($numeroIntervention, $etatIntervention);
		return true;
	}
	return false;
}

//fonction qui appelle la fonction du modele 
function CtlupdateInterventions($interventions){
	updateInterventions($interventions);
}

/**Contrôle l'affichage de l'agenda
*selon une date choisie
*
*/
function CtlChoisirDateAgent($date,$nom){
	$date0=date_create_from_format('Y-m-d',$date);
	$date0=getfirstDayOfWeek($date0);

	return CtlAccueilMecanoAgent($date0,$nom);
}

/**Contrôle l'affichage de l'agenda
*
*/
function CtlAccueilMecanoAgent($date,$nom){
	return afficherAgendaMecanoPourAgent($nom,$date,getIntervention($nom),getFormation($nom));
}

/**Contrôle l'affichage de l'agenda
*avec la date de la semaine courante
*
*/
function CtlSemaineCouranteAgent($nom){
	$date0=getcurrentDate();
	$date0=getfirstDayOfWeek($date0);

	return CtlAccueilMecanoAgent($date0,$nom);
}


/**Contrôle l'affichage de l'agenda montrant une semaine antérieur
*
*/
function CtlSemainePrecedenteAgent($date,$nom){
	$date0=date_create_from_format('Y-m-d',$date);

	$date0=$date0->sub(new DateInterval('P7D'));
	$date0=getfirstDayOfWeek($date0);

	return CtlAccueilMecanoAgent($date0,$nom);
}

/**Contrôle l'affichage de l'agenda montrant une semaine postérieur
*
*/
function CtlSemaineSuivanteAgent($date,$nom){
	$date0=date_create_from_format('Y-m-d',$date);

	$date0=$date0->add(new DateInterval('P7D'));
	$date0=getfirstDayOfWeek($date0);

	return CtlAccueilMecanoAgent($date0,$nom);
}



/*********Reste**********/

function CtlErreur($erreur){
	afficherErreur($erreur) ;
}