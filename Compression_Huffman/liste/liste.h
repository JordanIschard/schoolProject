
/**
 * \file liste.h
 * \brief Fichier de déclaration de liste.c
 * \author Gael Marcadet
 * \version 1.0
 *
 * Cette liste se définie par une structure contenant une tête, une queue et une taille.
 * La tête pointra le premier élément tandis la queue pointra le dernier.
 * De ce fait, l'ajout en tête ou en fin sont optimisés.
 */
#ifndef LISTE_H
#define LISTE_H

#include "noeud.h"
#include "../utils/utils.h"
#include "../arbreG/arbreG.h"

#include <stdlib.h>
#include <stdio.h>


/**
 * \struct liste
 * \brief Structure liste permettant la gestion d'éléments dans une liste.
 *
 * Cette structure contient une tête et une queue, qui sont par défaut mis à NULL.
 */
typedef struct liste * liste;

/****************************************
*          Fonction de création         *
*****************************************/

/**
 * \fn liste creer_liste (void)
 * \brief Créer une structure liste vide et retourne le pointeur sur cette structure.
 *
 * \return Un pointeur sur la liste vide.
 */
liste creer_liste();


/************************************************
*    Fonctions de Tests et de récupération      *
************************************************/

/**
 * \fn int liste_est_vide(liste l)
 * \brief Vérifie si la liste est vide.
 *
 * \param l Liste dont on veut savoir si elle est vide ou non.
 *
 * \return L'entier 0 si la liste est vide. Dans les autres cas, la liste ne sera pas vide.
 */
int liste_est_vide(liste l);

/**
 * \fn int liste_appartient(liste l, void * value, int (*predicat)(void *, void *))
 * \brief Vérifie si la valeur donnée en paramètre est dans la liste.
 *
 * \param l 				Liste dont on veut savoir si elle contient la valeur donnée en paramètre.
 * \param value 		Valeur dont on veut savoir si elle contenue dans la liste.
 * \param predicat 	Fonction permettant de comparer deux valeurs génériques.
 *
 * \return L'entier 1 si la liste contient la valeur, 0 sinon.
 */
int liste_appartient(liste l, void * value, int (*predicat)(void *, void *));

/**
 * \fn noeud liste_get_premier(liste l)
 * \brief Retourne le premier élément.
 *
 * \param l Liste dont on veut obtenir le premier élement.
 *
 * \return Le premier élément de la liste. Peut être NULL.
 */
noeud liste_get_premier(liste l);

/**
 * \fn int liste_get_taille(liste l)
 * \brief Retourne la taille de la liste.
 *
 * \param l Liste dont on veut connaître la taille.
 *
 * \return La taille de la liste.
 */
int liste_get_taille(liste l);


/****************************************
*           Fonction d'ajout            *
*****************************************/

/**
 * \fn void liste_ajouter_debut(liste l, void * value, void (*copier)(void *, void **))
 * \brief Ajoute une valeur au début de la liste.
 *
 * \param l 			Liste auquel on souhaite ajouter un élément.
 * \param value 	Valeur que l'on souhaite ajouter.
 * \param copier 	Fonction permettant de placer la valeur à son emplacement.
 */
void liste_ajouter_debut(liste l, void * value, void (*copier)(void *, void **));

/**
 * void liste_ajouter_index(liste l, void * value, unsigned int index, void (*copier)(void *, void **))
 * \brief Ajoute une valeur dans la liste à un emplacement précis.
 *
 * \param l 			Liste auquel on souhaite ajouter un élément.
 * \param value 	Valeur que l'on souhaite ajouter.
 * \param index 	Indice où l'on souhaite ajouter la valeur.
 * \param copier	Fonction permettant de placer la valeur à son emplacement.
 */
void liste_ajouter_index(liste l, void * value, unsigned int index, void (*copier)(void *, void **));

/**
 * \fn void liste_ajouter_fin(liste l, void * value, void (*copier)(void *, void **))
 * \brief Ajoute une valeur en fin de la liste.
 *
 * \param l 			Liste auquel on souhaite ajouter un élément.
 * \param value 	Valeur que l'on souhaite ajouter.
 * \param copier 	Fonction permettant de placer la valeur à son emplacement.
 */
void liste_ajouter_fin(liste l, void * value, void (*copier)(void *, void **));

/**
 * \fn void liste_ajouter_ou_update(const liste l, void * value, void (*copier)(void *, void **), int (*comparateur)(void *, void *),void (*update)(void *), void (*detruire)(void *))
 * \brief Ajoute une valeur dans la liste ou applique la fonction update si elle est déjà présente.
 *	Dans la cas où la valeur est déjà dans la liste, la valeur en paramètre est détruite.
 *
 * \param l 					Liste auquel on souhaite ajouter une valeur.
 * \param value 			Valeur que l'on souhaite ajouter dans la liste.
 * \param copier 			Copie la valeur dans un noeud.
 * \param comparateur Compare deux valeurs générique ensemble. Permet de savoir si deux valeurs sont identiques.
 * \param update 			Met à jour la valeur déjà présente dans la liste.
 * \param detruire 		Détruit la valeur en paramètre si elle est déjà dans la liste.
 */
void liste_ajouter_ou_update(liste l, void * value, void (*copier)(void *, void **), int (*comparateur)(void *, void *), void (*update)(void *), void (*detruire)(void *));


/****************************************
*        Fonction de suppression        *
*****************************************/

/**
 * \fn void liste_supprimer_debut(liste l, void (*detruire)(void *))
 * \brief Supprime la valeur situé en début de liste.
 *
 * \param l 				Liste auquel on souhaite supprimer un élément.
 * \param detruire 	Fonction permettant de supprimer une valeur générique.
 */
void liste_supprimer_debut(liste l, void (*detruire)(void *));

/**
 * \fn void liste_supprimer_index(liste l, unsigned int index, void (*detruire)(void *))
 * \brief Supprime la valeur situé à un index précis.
 *
 * \param l 				Liste auquel on souhaite supprimer un élément.
 * \param index 		Indice de la valeur à supprimer.
 * \param detruire 	Fonction permettant de supprimer une valeur générique.
 */
void liste_supprimer_index(liste l, unsigned int index, void (*detruire)(void *));

/**
 * \fn void liste_supprimer_fin(liste l, void (*detruire)(void *))
 * \brief Supprime la valeur situé en fin de liste.
 *
 * \param l 				Liste auquel on souhaite supprimer un élément.
 * \param detruire 	Fonction permettant de supprimer une valeur générique.
 */
void liste_supprimer_fin(liste l, void (*detruire)(void *));


/****************************************
*        Fonction de libération         *
*****************************************/

/**
 * \fn void liste_detruire_contenu(liste l, void (*detruire)(void *))
 * \brief Détruit le contenu de la liste.
 *
 * \param l 				Liste dont on souhaite supprimer le contenu.
 * \param detruire 	Fonction permettant de supprimer une valeur générique.
 */
void liste_detruire_contenu(liste l, void (*)(void *));

/**
 * \fn void void liste_detruire_liste(liste* l, void (*detruire)(void *))
 * \brief Détruit la structure liste ainsi que son contenu
 *
 * \param l 				Liste à supprimer.
 * \param detruire 	Fonction permettant de supprimer une valeur générique.
 */
void liste_detruire_liste(liste* l, void (*)(void *));

/****************************************
*        Fonction utilitaires           *
*****************************************/

/**
 * \fn void liste_afficher(liste l, void (*afficher)(void *))
 * \brief Affiche le contenu de la liste.
 *
 * \param l 				Liste dont on souhaite afficher le contenu.
 * \param afficher 	Fonction permettant de détruire une valeur générique.
 */
void liste_afficher(liste l, void (*)(void *));

/**
 * \fn liste liste_copier(liste l, void (*copier)(void *, void **))
 * \brief Fait une copie de liste en paramètre.
 *
 * \param l 			Liste à copier.
 * \param copier 	Fonction permettant de copier une valeur générique et de l'affecter à un nouvel emplacement.
 *
 * \return La liste copiée de la liste initiale.
 */
liste liste_copier(liste l, void (*copier)(void *, void **));

#endif
