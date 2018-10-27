
/**
 * \file utils.h
 * \brief Fichier de déclaration de utils.c
 * \author Gael Marcadet
 * \version 1.0
 *
 * utils est une compilation de fonction permettant
 * une utilisation de méthode générique.
 */
#ifndef UTILS_H
#define UTILS_H
#define EXTENSION_OUT ".ho"

#include <stdio.h>
#include <stdlib.h>

#include "../arbreG/arbreG.h"
#include "../couple/couple.h"


/**
 * Passage en mode débogage
 */
#ifdef DEBUG
  #define LOG(m)        printf("===   LOG   === %s\n", m)
  #define LOGERROR(e,m) printf("===   LOG   ===[%d] %s\n",e,m)
  #define LOG_PTR(p)    printf("=== ADRESSE === %p\n",p )
  #define LOG_MISS(m)   printf("=== MISSING === %s\n",m )
  #define ASSERT(p,m,e)     if(p){printf("=== ERROR === %s\n",m); exit(e);}
#else
  #define LOG(m)
  #define LOGERROR(e,m)
  #define LOG_PTR(p)
  #define LOG_MISS(m)
  #define ASSERT(p,m,e)
#endif


/**
 * \fn void huffman_exit()
 * \brief Quitte le programme en indiquant une mauvaise utilisation de la part de l'utilisateur.
 */
void utils_exit();

/**
 * \fn long long_addition(long l1, long l2)
 * \brief Ajoute les deux long en paramètres
 *
 * \param l1 Long a ajouter.
 * \param l2 long a ajouter.
 *
 * \return L'addition des deux longs.
 */
long long_addition(long l1, long l2);

/**
 * \fn char * transformer_nom_fichier_sortie(char * fichier_entree)
 * \brief Rajoute ".ho" au fichier pour créer un nom de sortie.
 *
 * \param fichier_entre Nom de fichier à transformer.
 *
 * \return Le nom de fichier d'entrée avec l'extension .ho
 */
char * transformer_nom_fichier_sortie(char * fichier_entree);

/**
 * \fn char * compression_tableau_string(int nbarg, char ** args)
 * \brief Compresse un tableau de chaîne de caractères en une seule chaîne.
 *
 * \param nbargs Nombre de chaînes.
 * \param args Tableau de chaînes de caractères;
 *
 * \return Une chaîne de caractères correspondant à une concaténation de chaque chaînes du tableau.
 */
char * compression_tableau_string(int nbarg, char ** args);

/**
 * \fn void * string_concat(void * v1, void * v2)
 * \brief Concatène deux chaînes de caractères
 *
 * \param v1    1ère chaîne de caractères
 * \param v2    2nd chaîne de caractères
 *
 * \return Un pointeur vers la chaîne de caractères.
 */
void * string_concat(void * v1, void * v2);

/**
 * \fn  void string_copier(void * v, void ** pl)
 * \brief Mets un pointeur sur une chaîne de caractère
 *
 * \param v   chaîne de caractères à copier
 * \param pl    pointeur qui va pointer la chaîne de caractères
 *
 */
void string_copier(void * v, void ** pl);

/**
 * \fn void string_dup(void * v, void ** pl)
 * \brief Copie une chaîne de caractères dans une zone
 * mémoire pointée.
 *
 * \param v   chaîne de caractères à copier
 * \param pl    pointeur du duplicata de la
 * chaîne de caractères.
 *
 */
void string_dup(void * v, void ** pl);

/**
 * \fn void string_detruire(void * v)
 * \brief Désalloue la place mémoire d'une
 * chaîne de caractères.
 *
 * \param v   chaîne de caractères à détruire
 *
 */
void string_detruire(void * v);

/**
 * \fn void string_afficher(void * v)
 * \brief Afficher une chaîne de caractères.
 *
 * \param v   chaîne de caractères à afficher.
 *
 */
void string_afficher(void * v);

/**
 * \fn char * concat(char * s1, char * s2)
 * \brief Concatène deux chaînes de caractères.
 *
 * \param s1    1ère chaîne de caractères.
 * \param s2    2nd chaîne de caractères.
 *
 * \return  une chaîne de caractères résultat de
 * la concaténation des chaînes de caractères données
 * en paramètre
 *
 */
char * concat(char * s1, char * s2);

/**
 * \fn void utils_afficher_arbre_couple(void *v)
 * \brief Affiche un arbre de couples.
 *
 * \param v   arbre de couples à afficher.
 *
 */
void utils_afficher_arbre_couple(void *v);

/**
 * \fn void utils_supprimer_arbre_couple(void * v)
 * \brief Supprime un arbre de couple
 *
 * \param v   arbre de couples à supprimer.
 */
void utils_supprimer_arbre_couple(void * v);

/**
 * \fn void utils_copier_generique(void * v, void ** vloc)
 * \brief Fait pointer un pointeur sur une valeur donnée en
 * paramètre
 *
 * \param v   élément à pointer.
 * \param vloc    pointeur
 *
 */
void utils_copier_generique(void * v, void ** vloc);

/**
 * \fn void int_copier(void * v, void ** vloc)
 * \brief Copie un entier dans une zone mémoire
 * désignée.
 *
 * \param v   entier à copier.
 * \param vloc    pointeur du duplicata de
 * l'entier.
 *
 */
void int_copier(void * v, void ** vloc);

/**
 * \fn int int_comparer(void * v1, void * v2)
 * \brief Compare deux entiers.
 *
 * \param v1   1ère entier à comparer
 * \param v2   2nd entier à comparer
 *
 * \return >0 v1>v2
 *         =0 v1=v2
 *         <0 v1<v2
 *
 */
int int_comparer(void * v1, void * v2);

/**
 * \fn void int_afficher(void * v)
 * \brief Affiche un entier
 *
 * \param v   entier à afficher
 *
 */
void int_afficher(void * v);


#endif /* end of include guard: UTILS_H */
