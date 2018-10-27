
/**
 * \file arbre.h
 * \brief Fichier de déclaration de arbre.c
 * \author Jordan Ischard
 * \version 1.0
 *
 * arbre est une structure d'arbre binaire utilisant des entiers 
 */
#ifndef ARBRE_H
#define ARBRE_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
 * \struct noeud_binaire
 * \brief La structure noeud_binaire est un noeud d'un arbre binaire
 */
struct noeud_binaire {
	struct noeud_binaire * fils_g;/*noeud représentant le fils gauche*/
	struct noeud_binaire * fils_d;/*noeud représentant le fils droit*/
	int valeur;/*la valeur du noeud*/
}noeud_binaire;

/**
 * \typedef struct noeud_binaire * arbre_binaire
 * \brief La structure arbre_binaire est un pointeur sur un noeud_binaire
 * afin de représenter un arbre binaire  
 */
typedef struct noeud_binaire* arbre_binaire;

/**
 * \fn arbre_binaire creer_arbre(int elem)
 * \brief Créer un arbre avec une valeur à mettre dans la racine 
 *
 * \param elem		la valeur à mettre dans la racine 
 *
 * \return un arbre composé d'une racine 
 * 
 */
arbre_binaire creer_arbre(int elem);

/**
 * \fn int arbreG_estVide(arbre_binaire a)
 * \brief la fonction informe si nous sommes
 * dans un arbre nul
 *
 * \param a 	l'arbre que l'on veut vérifier
 *
 * \return 1 si l'arbre est mis à NULL 
 *      et 0 sinon
 */
int arbre_estVide(arbre_binaire a);

/**
 * \fn int arbre_ajouter(arbre_binaire a, int elem)
 * \brief Ajoute un élement dans l'arbre donné en paramètre 
 *
 * \param a 	l'arbre à changer
 * \param elem 		la valeur à ajouter dans l'arbre 
 *
 * \return 1 si tout c'est bien déroulé 
 *         0 sinon
 * 
 */
int arbre_ajouter(arbre_binaire a, int elem);

/**
 * \fn arbre_binaire arbre_union(arbre_binaire *a,arbre_binaire *b)
 * \brief Fait l'union de deux arbres binaire
 *
 * \param a 	1ère arbre binaire utilisé pour l'union 
 * \param b 	2nd arbre binaire utilisé pour l'union
 *
 * \return un nouvel arbre binaire qui est l'union des 2 arbres binaire
 * donnés en paramètre
 * 
 */
arbre_binaire arbre_union(arbre_binaire *a,arbre_binaire *b);

/**
 * \fn int arbre_supprimer(arbre_binaire *a,int elem)
 * \brief Supprime un noeud et ses fils (si il en a)
 *
 * \param a 	arbre binaire où l'on veut supprimer un élement 
 * \param elem 		l'élement que l'on cherche à supprimer de l'arbre
 *
 * \return 1 si tout se passe bien 
 * 		   0 sinon (si on ne trouve pas l'élement)
 * 
 */
int arbre_supprimer(arbre_binaire *a,int elem);

/**
 * \fn int arbre_appartient(arbre_binaire a, int elem)
 * \brief Informe si un élement donné en paramètre est dans l'arbre binaire 
 *
 * \param a 	arbre binaire où l'on cherche l'appartenance d'un élement
 * \param elem 		l'élement que l'on cherche à trouver dans l'arbre
 *
 * \return 1 si elem appartient à l'arbre 
 *         0 sinon
 * 
 */
int arbre_appartient(arbre_binaire a, int elem);

/**
 * \fn char* arbre_chemin_element(arbre_binaire a, int elem)
 * \brief Donne le chemin d'un élement dans l'arbre en prenant compte que lorsque qu'on
 * va "à gauche" dans l'arbre on note un 0 et 1 sinon
 *
 * \param a 	arbre binaire que l'on parcours pour trouver le chemin
 * \param elem 		l'élement dont on cherche son chemin 
 *
 * \return le chemin de l'élement ou indique via un message qu'il ne la pas trouvé
 * 
 */
char* arbre_chemin_element(arbre_binaire a, int elem);

/**
 * \fn void arbre_detruire(arbre_binaire * a)
 * \brief Libère l'espace mémoire requisitionné pour l'arbre binaire
 * et tout ce qu'il le compose
 *
 * \param a 	arbre que l'on veut détruire 
 *
 */
void arbre_detruire(arbre_binaire* a);

/**
 * \fn void arbre_afficher(arbre_binaire a)
 * \brief Affiche l'arbre binaire 
 *
 * \param a 	arbre binaire à afficher 
 *
 */
void arbre_afficher(arbre_binaire a);

#endif