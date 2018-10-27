
/**
 * \file noeud.h
 * \brief Fichier de déclaration de noeud.c
 * \author Gael Marcadet
 * \version 1.0
 *
 * La structure noeud est utilisée pour contenir des valeurs génériques.
 * Toutefois, il est recommandé d'utiliser
 * la structure liste définie dans liste.h.
 */
#ifndef NOEUD_H
#define NOEUD_H

/**
 * \struct noeud
 * \brief Structure noeud permettant de gérer des valeurs génériques.
 *
 * Cette structure utilise un pointeur vers le noeud suivant.
 * Ceci permet de créer une liste simplement chaînée.
 * La valeur de type void * permet l'utilisation de la généricité.
 */
typedef struct noeud * noeud;

/****************************************
*          Fonction de création         *
*****************************************/

/**
 * \fn noeud creer_noeud(void * value, void (*copier)(void*, void**))
 * \brief Créer une structure noeud.
 *
 * \param value		Valeur à copier
 * \param copier 	Fonction permettant de copier la valeur à son emplacement.
 *
 * \return Un pointeur sur le nouveau noeud.
 */
noeud creer_noeud(void *, void (*)(void*, void**));


/****************************************
*       Fonction de manipulation        *
*****************************************/

/**
 * \fn void noeud_ajouter_fin(void * value, noeud n, void (*copier)(void *, void **))
 * \brief Ajoute un noeud à la fin de la chaîne.
 *
 * \param value		Valeur à copier.
 * \param n				Noeud de départ.
 * \param copier 	Fonction permettant de copier la valeur à son emplacement.
 */
void noeud_ajouter_fin(void *, noeud, void (*)(void *, void **));

/**
 * \fn void noeud_supprimer_fin(noeud* n_loc, void (*detruire)(void*))
 * \brief Supprime le noeud à la fin de la chaîne.
 *
 * \param n					Pointeur sur la noeud de départ.
 * \param detruire 	Fonction permettant de supprimer une valeur générique.
 *
 */
void noeud_supprimer_fin(noeud *, void (*)(void*));

/**
 * \fn void noeud_detruire_tout(noeud *n_loc, void(*detruire)(void*))
 * \brief Supprime toute la chaîne.
 *
 * \param n					Pointeur sur le noeud de départ.
 * \param detruire 	Fonction permettant de supprimer une valeur générique.
 *
 */
void noeud_detruire_tout(noeud *, void(*)(void*));


/****************************************
*         Fonction utilitaires          *
*****************************************/

/**
 * \fn void noeud_afficher_tout(noeud n, void(*afficher)(void *))
 * \brief Affiche toutes les valeurs contenues dans la chaîne.
 *
 * \param n					Noeud de départ.
 * \param afficher 	Fonction permettant d'afficher une valeur générique.
 *
 */
void noeud_afficher_tout(noeud, void(*)(void*));

/**
 * \fn noeud noeud_get_suivant(noeud n)
 * \brief Retourne le noeud suivant pointé par le noeud en paramètre.
 *
 * \param n	Noeud dont on souhaite avoir le suivant.
 *
 * \return Le noeud suivant pointé par le noeud en paramètre.
 */
noeud noeud_get_suivant(noeud);

/**
 * \fn void noeud_set_suivant(noeud n1, noeud n2)
 * \brief Défini le suivant d'un noeud.
 *
 * \param n1	Noeud dont on veut changer le suivant.
 * \param n2	Noeud qui sera défini comme le suivant.
 */
void  noeud_set_suivant(noeud, noeud);

/**
 * \fn void * noeud_get_valeur(noeud n)
 * \brief Retourne la valeur pointé par le noeud.
 *
 * \param n	Noeud dont on souhaite avoir la valeur.
 *
 * \return Le pointeur sur la valeur contenue dans le noeud.
 */
void * noeud_get_valeur(noeud);

/**
 * \fn void ** noeud_get_adresse_valeur(noeud n)
 * \brief Retourne un pointeur qui pointe la valeur void * du noeud.
 *
 * \param n	Noeud dont on souhaite avoir l'adresse de la valeur.
 *
 * \return Un pointeur qui pointe la valeur void * du noeud.
 */
void ** noeud_get_adresse_valeur(noeud);

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
void noeud_set_valeur(noeud, void *);

#endif
