
/**
 * \file noeud.c noeud.h
 * \brief Noeud générique.
 * \author Gael Marcadet
 * \version 1.0
 *
 * La structure noeud est utilisée pour contenir des valeurs génériques.
 * Toutefois, il est recommandé d'utiliser
 * la structure liste définie dans liste.h.
 */
#include <stdlib.h>
#include <stdio.h>

#include "noeud.h"


/**
 * \struct noeud
 * \brief Structure noeud permettant de gérer des valeurs génériques.
 *
 * Cette structure utilise un pointeur vers le noeud suivant.
 * Ceci permet de créer une liste simplement chaînée.
 * La valeur de type void * permet l'utilisation de la généricité.
 */
struct noeud{
	void * value; /*!< Valeur générique */
	struct noeud * suivant; /*!< Pointeur vers le noeud suivant */
};

/**
 * \fn noeud creer_noeud(void * value, void (*copier)(void*, void**))
 * \brief Créer une structure noeud.
 *
 * \param value		Valeur à copier
 * \param copier 	Fonction permettant de copier la valeur à son emplacement.
 *
 * \return Un pointeur sur le nouveau noeud.
 */
noeud creer_noeud(void * value, void (*copier)(void*, void**)){
	noeud res = malloc(sizeof(struct noeud));
	res -> suivant = NULL;
	(*copier)(value, &(res -> value));
	return res;
}

/**
 * \fn void noeud_ajouter_fin(void * value, noeud n, void (*copier)(void *, void **))
 * \brief Ajoute un noeud à la fin de la chaîne.
 *
 * \param value		Valeur à copier.
 * \param n				Noeud de départ.
 * \param copier 	Fonction permettant de copier la valeur à son emplacement.
 */
void noeud_ajouter_fin(void * value, noeud n, void (*copier)(void *, void **)){
	if(n == NULL) return;
	while(n -> suivant != NULL)
		n = n -> suivant;
	n -> suivant = creer_noeud(value, copier);
}

/**
 * \fn void noeud_supprimer_fin(noeud* n_loc, void (*detruire)(void*))
 * \brief Supprime le noeud à la fin de la chaîne.
 *
 * \param n					Pointeur sur la noeud de départ.
 * \param detruire 	Fonction permettant de supprimer une valeur générique.
 *
 */
void noeud_supprimer_fin(noeud* n_loc, void (*detruire)(void*)){
	noeud n = *n_loc;
	if(!n) return;
	if(!n -> suivant){

		detruire(n -> value);
		free(*n_loc);
		*n_loc = NULL;
		return;
	}
	noeud n_suivant = n -> suivant;
	while(n_suivant -> suivant){
		n = n_suivant;
		n_suivant = n_suivant -> suivant;
	}
	free(n_suivant -> suivant);
	detruire(n_suivant -> value);
	free(n_suivant);
	n -> suivant = NULL;

}

/**
 * \fn void noeud_detruire_tout(noeud *n_loc, void(*detruire)(void*))
 * \brief Supprime toute la chaîne.
 *
 * \param n					Pointeur sur le noeud de départ.
 * \param detruire 	Fonction permettant de supprimer une valeur générique.
 *
 */
void noeud_detruire_tout(noeud *n_loc, void(*detruire)(void*)){
	noeud n = *n_loc;
	if(n == NULL){
		return;
	}
	while(n != 	NULL){
		noeud aux = n -> suivant;
		if(detruire != NULL){
			detruire(n -> value);
		}
		n -> value = NULL;
		//free(&n -> value);
		free(n);
		n = aux;
	}
	*n_loc = NULL;
	free(n);
}

/**
 * \fn void noeud_afficher_tout(noeud n, void(*afficher)(void *))
 * \brief Affiche toutes les valeurs contenues dans la chaîne.
 *
 * \param n					Noeud de départ.
 * \param afficher 	Fonction permettant d'afficher une valeur générique.
 *
 */
void noeud_afficher_tout(noeud n, void(*afficher)(void *)){
	while(n){
		(*afficher)(n -> value);
		n = n -> suivant;
	}
	printf("\n");
}

/**
 * \fn noeud noeud_get_suivant(noeud n)
 * \brief Retourne le noeud suivant pointé par le noeud en paramètre.
 *
 * \param n	Noeud dont on souhaite avoir le suivant.
 *
 * \return Le noeud suivant pointé par le noeud en paramètre.
 */
noeud noeud_get_suivant(noeud n){
	return n -> suivant;
}

/**
 * \fn void noeud_set_suivant(noeud n1, noeud n2)
 * \brief Défini le suivant d'un noeud.
 *
 * \param n1	Noeud dont on veut changer le suivant.
 * \param n2	Noeud qui sera défini comme le suivant.
 */
void noeud_set_suivant(noeud n1, noeud n2){
	n1 -> suivant = n2;
}

/**
 * \fn void * noeud_get_valeur(noeud n)
 * \brief Retourne la valeur pointé par le noeud.
 *
 * \param n	Noeud dont on souhaite avoir la valeur.
 *
 * \return Le pointeur sur la valeur contenue dans le noeud.
 */
void * noeud_get_valeur(noeud n){
	return n -> value;
}

/**
 * \fn void ** noeud_get_adresse_valeur(noeud n)
 * \brief Retourne un pointeur qui pointe la valeur void * du noeud.
 *
 * \param n	Noeud dont on souhaite avoir l'adresse de la valeur.
 *
 * \return Un pointeur qui pointe la valeur void * du noeud.
 */
void ** noeud_get_adresse_valeur(noeud n){
	return &(n->value);
}

/**
 * \fn void noeud_set_valeur(noeud n, void * value)
 * \brief Remplace le pointeur générique du noeud par celui en paramètre.
 *
 * Attention, la valeur qui était au préalable pointé par le noeud
 * sera définitvement perdue.
 * Il faut d'abord récupérer la valeur du noeud avant de faire ce changement.
 * Dans le cas contraire, des fuites mémoires peuvent apparaîtres.
 *
 * \param n	 		Noeud dont on souhaite changer la valeur.
 * \param value	Valeur à appliquer au noeud.
 */
void noeud_set_valeur(noeud n, void * value){
	n -> value = value;
}
