
/**
 * \file couple.c couple.h
 * \brief Couple d'un caractère et d'un hash.
 * \author Gael Marcadet
 * \version 1.0
 *
 * Le couple d'un caractère et d'un hash permet de connaître
 * par exemple une fréquence d'un caractère.
 */
#include "couple.h"

/**
 * \fn couple creer_couple(char c, int hash)
 * \brief Créer un couple d'un caractère et d'un hash.
 *
 * \param c 		Le caractère à insèrer dans le couple.
 * \param hash 	Le hash du caractère à insérer dans le couple.
 *
 * \return Un couple de caractère et d'un hash.
 */
couple creer_couple(char c, long hash){
	couple res = malloc(sizeof(struct couple));
	res -> c = c;
	res -> hash = hash;
	return res;
}

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
int couple_compare(void * v1, void * v2){
	couple c1 = (couple)v1, c2 = (couple)v2;
	int res = 0;
	if(c1 -> c == c2 -> c)
		res = 0;
	else
		res = c1 -> hash == c2 -> hash ? c1 -> c - c2 -> c : c1 -> hash - c2 -> hash;
	return res;
}


/**
 * \fn char couple_get_caractere(void * v)
 * \brief Renvoie le caractère du couple donné
 * en paramètre.
 *
 * \param v 	Couple dont on veut le caractère.
 *
 * \return le caractère du couple
 */

char couple_get_caractere(void * v){
	couple c = (couple)v;
	return c->c;
}


/**
 * \fn char couple_get_valeur(void * v)
 * \brief renvoie le nombre d'occurence du caractère
 * du couple donné en paramètre
 *
 * \param v 	Couple dont on veut le nombre d'occurence
 * de son caractère.
 *
 * \return le nombre d'occurence du caractère du couple
 */
long couple_get_valeur(void * v){
	couple c = (couple)v;
	return c->hash;
}

/**
 * \fn void couple_update(void * v)
 * \brief Met à jour un couple en augmentant sont hash de 1.
 *
 * \param v Le couple dont on cherche à mettre à jour le hash.
 *
 */
void couple_update(void * v){
	couple c = (couple)v;
	++c -> hash;
}

/**
 * \fn void couple_copier(void * v, void ** t)
 * \brief Copie le couple à la position indiquée.
 *
 * \param v Le couple à copier.
 * \param t Pointeur sur l'emplacement où il faut copier le couple.
 *
 */
void couple_copier(void * v, void ** t){
	couple c = (couple)v;
	couple * location = (couple *)t;
	(*location) = c;

}

/**
 * \fn void couple_afficher(void * v)
 * \brief Affiche le couple.
 *
 * \param v Le couple à afficher.
 */
void couple_afficher(void * v){
	couple c = (couple)v;
	printf("%c\n",c->c);
	printf("char: %c - hash: %ld\n",c -> c, c -> hash);
}

/**
 * \fn void couple_detruire(void * v)
 * \brief Détruit le couple.
 *
 * \param v Le couple à détruire.
 */
void couple_detruire(void * v){
	couple c = (couple)v;
	free(c);
}
