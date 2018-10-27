<?php

require_once('Controleur/controleur.php');

try{

	if(isset($_POST['connexion'])){
		$login=$_POST['login'];
		$pwd=$_POST['password'];
		$typeEmploye=CtltypeEmploye($login,$pwd);
	}

	/*************************************/
	/*************************************/
	/**********Partie Mécanicien**********/
	/*************************************/
	/*************************************/

	if($typeEmploye=='mécanicien' 
		|| isset($_POST['choixFormation']) 
		|| isset($_POST['inter']) 
		|| isset($_POST['date']) 
		|| isset($_POST['choisirDate']) 
		|| isset($_POST['suivant']) 
		|| isset($_POST['precedent'])
	){

		if(isset($_POST['choisirDate'])){

			$date=$_POST['date'];
			$nom=$_POST['nom'];
			CtlChoisirDate($date,$nom);

		}else{

			if(isset($_POST['precedent'])){
				$date=$_POST['date'];
				$nom=$_POST['nom'];
				CtlSemainePrecedente($date,$nom);

			}else{

				if(isset($_POST['suivant'])){

					$date=$_POST['date'];
					$nom=$_POST['nom'];
					CtlSemaineSuivante($date,$nom);

				}else{
					if(isset($_POST['choix'])){

						$date=$_POST['date'];
						$nom=$_POST['nom'];
						
						if(isset($_POST['choixFormation'])){

							$choix=$_POST['choix'];
							CtlAjouterFormation($choix,$nom);
						}
						CtlChoisirDate($date,$nom);

					}else{

						if(isset($_POST['inter'])){
							$numInter=$_POST['inter'];
							$date=$_POST['date'];
							$nom=$_POST['nom'];
							CtlIntervention($date,$nom,$numInter);
						}else{

							$nom=CtlEmploye($login,$pwd);
							CtlSemaineCourante($nom);
						}
					}
				}
			}
		}

	}else{

		/***********************************/
		/***********************************/
		/*********Partie Directeur**********/
		/***********************************/
		/***********************************/

		if($typeEmploye=='directeur'
			|| isset($_POST['interf']) 
			|| isset($_POST['dAjoutEmploye'])
			|| isset($_POST['ajoutEmploye'])
			|| isset($_POST['dSupprModifEmploye'])
			|| isset($_POST['supprEmploye'])
			|| isset($_POST['modifEmploye'])
			|| isset($_POST['changeEmploye'])
			|| isset($_POST['interv'])
			|| isset($_POST['dAjoutTypeInterv'])
			|| isset($_POST['ajoutTypeInterv'])
			|| isset($_POST['dSupprModifInterv'])
			|| isset($_POST['supprTypeInterv'])
			|| isset($_POST['modifTypeInterv'])
			|| isset($_POST['changeListeElements'])
			|| isset($_POST['modifListe'])
			|| isset($_POST['changeMontant'])
			|| isset($_POST['modifMontant'])
			|| isset($_POST['changeNomIntervention'])
			|| isset($_POST['modifNomIntervention'])
			|| isset($_POST['elem'])
		){

			if(isset($_POST['interf'])){

				ctlChoixActionInterf();

			}elseif(isset($_POST['dAjoutEmploye'])){

				ctlDAjoutEmploye();

			}elseif(isset($_POST['ajoutEmploye'])){

				$nomEmploye=$_POST['nomEmploye'];
				$login=$_POST['login'];
				$pwd=$_POST['pwd'];
				$categorie=$_POST['categorie'];
				ctlAjouterEmploye($nomEmploye,$login,$pwd,$categorie);

			}elseif(isset($_POST['dSupprModifEmploye'])){

				ctlDSupprimerEmploye();

			}elseif(isset($_POST['supprEmploye'])){

				$login=$_POST['login'];
				ctlSupprEmploye($login);

			}elseif(isset($_POST['modifEmploye'])){

				$login=$_POST['login'];
				ctlDModifierEmploye($login);

			}elseif(isset($_POST['changeEmploye'])){

				$login=$_POST['login'];
				$newlogin=$_POST['newlogin'];
				$newpwd=$_POST['newpwd'];
				ctlModifEmploye($login,$newlogin,$newpwd);

			}elseif(isset($_POST['interv'])){

				ctlChoixActionInterv();

			}elseif(isset($_POST['dAjoutTypeInterv'])){

				ctlDAjoutTypeInterv();

			}elseif(isset($_POST['ajoutTypeInterv'])){

				$nom=$_POST['nom'];
				$listeElements=$_POST['listeElements'];
				$montant=$_POST['montant'];
				ctlAjouterTypeIntervention($nom,$listeElements,$montant);

			}elseif(isset($_POST['dSupprModifInterv'])){

				ctlDSupprimerTypeInterv();

			}elseif(isset($_POST['supprTypeInterv'])){

				$nomIntervention=$_POST['nomIntervention'];
				ctlSupprTypeInterv($nomIntervention);

			}elseif(isset($_POST['modifTypeInterv'])){

				$nomIntervention=$_POST['nomIntervention'];
				ctlDModifierTypeInterv($nomIntervention);

			}elseif(isset($_POST['changeListeElements'])){   //marche aussi pour paragraphe 3

				$nomIntervention=$_POST['nomIntervention'];
				ctlDAfficherChangeListeElements($nomIntervention);

			}elseif(isset($_POST['modifListe'])){

				$nomIntervention=$_POST['nomIntervention'];
				$newListeElements=$_POST['newListeElements'];
				cltModifListe($nomIntervention,$newListeElements);

			}elseif(isset($_POST['changeMontant'])){

				$nomIntervention=$_POST['nomIntervention'];
				ctlAfficherChangerMontant($nomIntervention);

			}elseif(isset($_POST['modifMontant'])){

				$nomIntervention=$_POST['nomIntervention'];
				$newMontant=$_POST['newMontant'];
				cltModifMontant($nomIntervention,$newMontant);

			}elseif(isset($_POST['changeNomIntervention'])){

				$nomIntervention=$_POST['nomIntervention'];
				ctlDAfficherChangeNomInterv($nomIntervention);

			}elseif(isset($_POST['modifNomIntervention'])){

				$nomIntervention=$_POST['nomIntervention'];
				$newNomIntervention=$_POST['newNomIntervention'];
				cltModifNomIntervention($nomIntervention,$newNomIntervention);

			}elseif(isset($_POST['elem'])){

				ctlDirectListeElement();

			}
			ctlAcceuilDirecteur();

		}else{

			/*******************************/
			/*******************************/
			/*********Partie Agent**********/
			/*******************************/
			/*******************************/

			if($typeEmploye=='agent'
				|| isset($_POST['espaceAgentAccueil'])
				|| isset($_POST['boutonRechercherClient']) 
				|| isset($_POST['boutonAjouterClient']) 
				|| isset($_POST['boutonRechercherClientParNomDateNaiss']) 
				|| isset($_POST['boutonEditerClient']) 
				|| isset($_POST['boutonModifierClient']) 
				|| isset($_POST['boutonAjouterNouveauClient']) 
				|| isset($_POST['boutonSupprimerClient']) 
				|| isset($_POST['boutonGestionRNV']) 
				|| isset($_POST['boutonValiderIntervention']) 
				|| isset($_POST['boutonSynthèseClient']) 
				|| isset($_POST['boutonSyntheseClientIntervention']) 
				|| isset($_POST['boutonGestionFinanciere']) 
				|| isset($_POST['boutonValiderGestionClient']) 
				|| isset($_POST['boutonPayerIntervention'])
				|| isset($_POST['choisirDateAgent'])
				|| isset($_POST['precedentAgent'])
				|| isset($_POST['suivantAgent'])
				|| isset($_POST['choixAgent']) 
				|| isset($_POST['boutonChoixMecano'])
				|| isset($_POST['boutonPayerInterventionDifferees'])
			){

				//pour afficher lespace agent d acceuil (partie agent)
				 if (isset($_POST['espaceAgentAccueil'])){
						afficherEspaceAgentAccueil();
					 }

				// quand l'utilisateur clique sur le bouton (boutonRechercherClient) il lui affiche un formulaire de recherche (partie agent)
				 if (isset($_POST['boutonRechercherClient'])){

						ctlAfficherForumRechercherClient();
					 }

				// quand l'utilisateur clique sur le bouton (boutonAjouterClient) il lui affiche un formulaire d'ajout client' (partie agent)
				 if (isset($_POST['boutonAjouterClient'])){
						afficherFormAjoutClient();
					 }
				// quand l'utilisateur clique sur le bouton (boutonRechercherClientParNomDateNaiss) il lui affiche un formulaire d'ajout client '(partie agent)
				if (isset($_POST['boutonRechercherClientParNomDateNaiss'])){
					 	$nomClient=$_POST['nomClient'];
					 	$dateNaissance=$_POST['dateNaissanceClient'];
						$clients = CtlRechercherClientParNomDateNaiss($nomClient, $dateNaissance);
						
						afficherRechercheClients($clients);
					 }
				// quand l'utilisateur clique sur le bouton (boutonRechercherClientParNomDateNaiss) il lui affiche un formulaire d'ajout' (partie agent)

				if (isset($_POST['boutonEditerClient'])){
						$clientId=$_POST['clientId'];
					 	$client=CtlEditerClient($clientId);
						 EditerClient($client);
					 }
				// quand l'utilisateur clique sur le bouton (boutonModifierClient) il lui affiche un formulaire de modifier le client (partie agent)
				 if (isset($_POST['boutonModifierClient'])){
				 	$idClient=$_POST['idClient'];
				 	$nomClient=$_POST['name'];
				 	$prenomClient=$_POST['prenom'];
				 	$dateNaissance=$_POST['dateDeNaissance'];
				 	$adresseClient=$_POST['adresse'];
				 	$numTel=$_POST['numTel'];
				 	$email=$_POST['email'];
				 	$montantMax=$_POST['decouvert'];

				 	CtlboutonModifierClient($idClient,$nomClient,$prenomClient,$adresseClient,$email,$montantMax,$numTel,$dateNaissance);
				 	echo "string";
				 	$client = CtlEditerClient($idClient);
					EditerClient($client);
				}

				// quand l'utilisateur clique sur le bouton (boutonAjouterNouveauClient) pour enregistrer les infos client (partie agent)
				 if (isset($_POST['boutonAjouterNouveauClient'])){
				 	
				 	$nomClient=$_POST['name'];
				 	$prenomClient=$_POST['prenom'];
				 	$dateNaissance=$_POST['dateDeNaissance'];
				 	$adresseClient=$_POST['adresse'];
				 	$numTel=$_POST['numTel'];
				 	$email=$_POST['email'];
				 	$montantMax=$_POST['decouvert'];

				 	CtlboutonAjouterNouveauClient($nomClient,$prenomClient,$adresseClient,$email,$montantMax,$numTel,$dateNaissance);
					confirmationAjoutNouveauClient();
					}

				// quand l'utilisateur clique sur le bouton (boutonSupprimerClient) pour supprimer  un client (partie agent)

				if (isset($_POST['boutonSupprimerClient'])){
				 	$clientId=$_POST['clientId'];
					CtlSupprimerClient($clientId);
					rechercherClient();

				 }

				if (isset($_POST['boutonGestionRNV'])
					|| isset($_POST['choisirDateAgent'])
					|| isset($_POST['precedentAgent'])
					|| isset($_POST['suivantAgent'])
					|| isset($_POST['boutonChoixMecano'])
				){
					if(isset($_POST['boutonChoixMecano'])
						|| isset($_POST['choisirDateAgent'])
						|| isset($_POST['precedentAgent'])
						|| isset($_POST['suivantAgent'])
					){
						if(isset($_POST['choisirDateAgent'])){
							$nom=$_POST['nom'];
							$date=$_POST['dateAgent'];
							$agenda=CtlChoisirDateAgent($date,$nom);

						}else{

							if(isset($_POST['precedentAgent'])){
								$nom=$_POST['nom'];
								$date=$_POST['dateAgent'];
								$agenda=CtlSemainePrecedenteAgent($date,$nom);

							}else{

								if(isset($_POST['suivantAgent'])){
									$nom=$_POST['nom'];
									$date=$_POST['dateAgent'];
									$agenda=CtlSemaineSuivanteAgent($date,$nom);

								}else{
									$nom=$_POST['nomEmploye'];
									$agenda=CtlSemaineCouranteAgent($nom);
								}
							}
						}
						$typeInterventions = CtlGetAllTypeIntervention();
				 		gestionDesRendezVous($typeInterventions,$agenda);
					}else{
				 		$mecanos = CtlgetListeMecano();
				 		gestionDesRDVChoixMecano($mecanos);
					}
				}

				// quand l'utilisateur clique sur le bouton (boutonValiderIntervention) pour enregister les infos de l'intervention (partie agent).

				 if (isset($_POST['choixAgent'])){
				 	$nomTypeIntervention=$_POST['typeInterventionName'];
				 	$dateIntervention=$_POST['choixAgent'];
				 	$nomEmploye=$_POST['nom'];
				 	$idClient=$_POST['idClient'];
					$message =  CtlajouterIntervention($nomTypeIntervention,$dateIntervention,$nomEmploye,$idClient);
					$listElements = CtlGetListElement($nomTypeIntervention);
					confirmationAjoutIntervention($listElements, $message);

					 }

					// quand l'utilisateur clique sur le bouton (boutonSynthèseClient) pour afficher le formulaire de recherer un client (partie agent).

					if (isset($_POST['boutonSynthèseClient'])){
					rechercheIdentitieClient();
					}
				// quand l'utilisateur clique sur le bouton (boutonSyntheseClientIntervention) pour afficher les infos du client  et ses interventions (partie agent).
					if (isset($_POST['boutonSyntheseClientIntervention'])){
						$idClient=$_POST['idClient'];
					 	$client=CtlgetIdentiteClient($idClient);
					 	$interventions=CtlgetListeInterventionClient($idClient);
					 	rechercheIdentiteClientEtInterventions($client,$interventions);
					 }
				// quand l'utilisateur clique sur le bouton (boutonGestionFinanciere) pour afficher le formulaire de recherche d'un client par son idClient (partie agent).
					 if (isset($_POST['boutonGestionFinanciere'])){
						gestionDePayement();
					}
				// quand l'utilisateur clique sur le bouton (boutonValiderGestionClient) pour afficher la derniere intervention et la listes des interventions en diférée pour le client recherché (partie agent).

					 if (isset($_POST['boutonValiderGestionClient'])){
					 	$idClient=$_POST['idClient'];
					 	$interventionsDifferees=CtlgetInterventionPourLeursGestion($idClient);
					 	$interventionNonPayee=CtlgetLastInterventionNonPaye($idClient);
						afficherInterventionParEtat( $interventionNonPayee, $interventionsDifferees);

					}

				// quand l'utilisateur clique sur le bouton (boutonPayerIntervention) pour changer l'etat de l'intervention (partie agent).

					 if (isset($_POST['boutonPayerIntervention'])){
					 	$numeroIntervention=$_POST['numeroIntervention'];
					 	$etatIntervention=$_POST['etatIntervention'];
					 	$AFonctionne=CtlupdateIntervention($numeroIntervention, $etatIntervention);
					 	if($AFonctionne){
					 		confirmationModificationIntervention($etatIntervention);
					 	}else{
					 		echecModificationIntervention();
					 	}
					 }

					  if (isset($_POST['boutonPayerInterventionDifferees'])){
						 	$interventionTableau=$_POST['intervention'];
						 	CtlupdateInterventions($interventionTableau);
						 	
						}

					 afficherEspaceAgentAccueil();


			}else{

				CtlPageConnexion();
			}
		}
	}

}catch(Exception $e) {

	$msg = $e->getMessage() ;
 	CtlErreur($msg);
 	
}