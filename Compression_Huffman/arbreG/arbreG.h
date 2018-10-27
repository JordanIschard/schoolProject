
/**
 * \file arbreG.h
 * \brief Fichier de déclaration de arbreG.c
 * \author Jordan Ischard
 * \version 1.0
 *
 * arbreG est une structure permettant de contenir des valeurs génériques 
 * sous la forme d'un arbre  
 */
#ifndef ARBREG_H
#define ARBREG_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../utils/utils.h"

/**
 * \struct noeudG
 * \brief La structure noeudG est un noeud d'un arbre générique
 */
struct noeudG {
	struct noeudG * fils_g;/*noeud générique représentant le fils gauche*/
	struct noeudG * fils_d;/*noeud générique représentant le fils droit*/
	void * valeur;/*la valeur générique du noeud*/
}noeudG;

/**
 * \typedef struct noeudG * arbreG
 * \brief La structure arbreG est un pointeur sur un noeudG afin
 * de représenter un arbre générique  
 */
typedef struct noeudG * arbreG;

/**
 * \fn int arbreG_estNul(arbreG a)
 * \brief la fonction informe si nous sommes
 * dans un arbre nul
 *
 * \param a 	l'arbre que l'on veut vérifier
 *
 * \return 1 si l'arbre est mis à NULL 
 *      et 0 sinon
 */
int arbreG_estNul(arbreG a);

/**
 * \fn int arbreG_est_une_feuille(arbreG a)
 * \brief la fonction informe si nous sommes
 * dans un noeud représentant une feuille
 *
 * \param a 	l'arbre que l'on veut vérifier
 *
 * \return 1 si a pointe sur une feuille de l'arbre
 *      et 0 sinon
 */
int arbreG_est_une_feuille(arbreG a);

/**
 * \fn arbreG creer_arbreG(void * elem, void(* copier)(void*, void**))
 * \brief Créer un arbre générique avec une valeur à mettre dans la racine 
 *
 * \param elem		la valeur à mettre dans la racine 
 * \param copier 	la fonction qui permet de copier l'élement dans ma
 * racine 
 *
 * \return un arbre composé d'une racine 
 * 
 */
arbreG creer_arbreG(void * elem, void(* copier)(void*, void**));

/**
 * \fn int arbreG_ajouter(arbreG a, void * elem,int (*comparer)(void*, void*)
	,void(* copier)(void*, void**)) 
 * \brief Ajouter un élement dans l'arbre générique donné en paramètre 
 *
 * \param a 	arbre générique à changer
 * \param elem 		la valeur à ajouter dans l'arbre 
 * \param comparer 		la fonction qui permet de comparer deux noeuds
 * \param copier 	la fonction qui de copier l'élement dans le noeud
 *
 * \return 1 si tout c'est bien déroulé 
 *         0 sinon
 * 
 */
int arbreG_ajouter(arbreG a, void * elem,int (*comparer)(void*, void*)
	,void(* copier)(void*, void**));

/**
 * \fn arbreG arbreG_union(arbreG *a,arbreG *b,void(* copier)(void*, void**)
	,void (* addition_element)(void *,void*, void **))
 * \brief Fait l'union de deux arbres génériques 
 *
 * \param a 	1ère arbre générique utilisé pour l'union 
 * \param b 	2nd arbre générique utilisé pour l'union
 * \param comparer  	la fonction qui permet de comparer deux noeuds
 * \param copier 	la fonction qui permet de copier un élement dans le noeud
 * \param addition_element 	la fonction permet de prendre la valeur de 2 noeuds
 * et de les additionner 
 *
 * \return un nouvel arbre générique qui est l'union des 2 arbres génériques
 * donnés en paramètre
 * 
 */
arbreG arbreG_union(arbreG *a,arbreG *b,void(* copier)(void*, void**)
	,void (* addition_element)(void *,void*, void **));

/**
 * \fn int arbreG_supprimer(arbreG *a,void * elem,int (*comparer)(void*, void*)
	,void (*detruire)(void**))
 * \brief Supprime un noeud et ses fils (si il en a)
 *
 * \param a 	arbre générique où l'on veut supprimer un élement 
 * \param elem 		l'élement que l'on cherche à supprimer de l'arbre
 * \param comparer  	la fonction qui permet de comparer deux noeuds
 * \param detruire 		la fonction permet de supprimer tout les élements 
 * à partir d'un noeud donné en paramètre 
 *
 * \return 1 si tout se passe bien 
 * 		   0 sinon (si on ne trouve pas l'élement)
 * 
 */
int arbreG_supprimer(arbreG *a,void * elem,int (*comparer)(void*, void*)
	,void (*detruire)(void**));

/**
 * \fn int arbreG_appartient(arbreG a,void * elem,int (*comparer)(void*, void*))
 * \brief Informe si un élement donné en paramètre est dans l'arbre générique 
 *
 * \param a 	arbre générique où l'on cherche l'appartenance d'un élement
 * \param elem 		l'élement que l'on cherche à trouver dans l'arbre
 * \param comparer  	la fonction qui permet de comparer deux noeuds
 *
 * \return 1 si elem appartient à l'arbre 
 *         0 sinon
 * 
 */
int arbreG_appartient(arbreG a,void * elem,int (*comparer)(void*, void*));

/**
 * \fn char* arbreG_chemin_element(arbreG a, void * elem,int (*comparer)(void*, void*))
 * \brief Donne le chemin d'un élement dans l'arbre en prenant compte que lorsque qu'on
 * va "à gauche" dans l'arbre on note un 0 et 1 sinon
 *
 * \param a 	arbre générique que l'on parcours pour trouver le chemin
 * \param elem 		l'élement dont on cherche son chemin 
 * \param comparer  	la fonction qui permet de comparer deux noeuds
 *
 * \return le chemin de l'élement ou indique via un message qu'il ne l'a pas trouvé
 * 
 */
char* arbreG_chemin_element(arbreG a, void * elem,int (*comparer)(void*, void*));

/**
 * \fn void arbreG_detruire(arbreG* a,void (*detruire)(void**))
 * \brief Libère l'espace mémoire requisitionné pour l'arbre générique
 * et tout ce qu'il le compose
 *
 * \param a 	arbre que l'on veut détruire 
 * \param detruire 		la fonction qui permet de libérer l'espace mémoire
 * prise par la valeur d'un noeud
 *
 */
void arbreG_detruire(arbreG* a,void (*detruire)(void**));

/**
 * \fn void arbreG_afficher(arbreG a,void (*afficher)(void*))
 * \brief Affiche l'arbre générique 
 *
 * \param a 	arbre générique à afficher 
 * \param afficher 		la fonction qui permet d'afficher la valeur d'un
 * noeud
 *
 */
void arbreG_afficher(arbreG a,void (*afficher)(void*));

#endif
