
/**
 * \file couple.h
 * \brief Fichier de déclaration de couple.c
 * \author Gael Marcadet
 * \version 1.0
 *
 * Le couple d'un caractère et d'un hash permet de connaître
 * par exemple une fréquence d'un caractère.
 */
#ifndef COUPLE_H
#define COUPLE_H

#include <stdlib.h>
#include <stdio.h>


/**
 * \struct couple
 * \brief La structure couple contenant un caractère et un hash (ici un entier).
 */
typedef struct couple * couple;
struct couple{
	char c; /*!< Caractère du couple . */
	long hash; /*!< hash du couple. */
};

/**
 * \fn couple creer_couple(char c, int hash)
 * \brief Créer un couple d'un caractère et d'un hash.
 *
 * \param c 		Le caractère à insèrer dans le couple.
 * \param hash 	Le hash du caractère à insérer dans le couple.
 *
 * \return Un couple de caractère et d'un hash.
 */
couple creer_couple(char c, long hash);

/**
 * \fn char couple_get_caractere(void * v)
 * \brief Renvoie le caractère du couple donné
 * en paramètre.
 *
 * \param v 	Couple dont on veut le caractère.
 *
 * \return Le caractère du couple.
 */
char couple_get_caractere(void * v);

/**
 * \fn char couple_get_valeur(void * v)
 * \brief Renvoie le nombre d'occurence du caractère
 * du couple donné en paramètre.
 *
 * \param v 	Couple dont on veut le nombre d'occurence
 * de son caractère.
 *
 * \return Le nombre d'occurence du caractère du couple.
 */
long couple_get_valeur(void * v);

/**
 * \fn int couple_compare(void * v1, void * v2)
 * \brief Compare deux couples entre eux.
 * Si le caractère v1 est identique au caractère de v2, alors on retourne 0.
 * Sinon si les hashs sont identiques, on fait la soustraction des caractères,
 * sinon la soustraction des hashs.
 *
 * \param v1  Couple 1 à comparer.
 * \param v2	Couple 2 à comparer.
 *
 * \return Un entier positif si v1 > v2, un entier négatif si v1 < v2, 0 sinon.
 */
int couple_compare(void * v1, void * v2);

/**
 * \fn void couple_update(void * v)
 * \brief Met à jour un couple en augmentant sont hash de 1.
 *
 * \param v Le couple dont on cherche à mettre à jour le hash.
 */
void couple_update(void * v);

/**
 * \fn void couple_copier(void * v, void ** t)
 * \brief Copie le couple à la position indiquée.
 *
 * \param v Le couple à copier.
 * \param t Pointeur sur l'emplacement où il faut copier le couple.
 *
 */
void couple_copier(void * v, void ** t);

/**
 * \fn void couple_afficher(void * v)
 * \brief Affiche le couple.
 *
 * \param v Le couple à afficher.
 */
void couple_afficher(void * v);

/**
 * \fn void couple_detruire(void * v)
 * \brief Détruit le couple.
 *
 * \param v Le couple à détruire.
 */
void couple_detruire(void * v);


#endif /* end of include guard: COUPLE_H */
