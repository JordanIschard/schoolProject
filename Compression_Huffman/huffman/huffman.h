
/**
 * \file huffman.h
 * \brief Les fonctions contenues dans ce fichier permettent d'appliquer l'algorithme de huffman.
 * Elles permettent de créer, manipuler et détruire l'arbre de huffman.
 * \author Jordan Ischard
 * \author Gael Marcadet
 * \version 1.0
 */
#ifndef HUFFMAN_H
#define HUFFMAN_H

#include "../liste/liste.h"
#include "../arbreG/arbreG.h"
#include "../utils/utils.h"
#include "../couple/couple.h"

/****************************************
*          Fonction de création         *
*****************************************/

/**
  * \fn arbreG huffman_creer_arbre(char * s)
  * \brief Créer un arbre de huffman à partir d'une chaîne de caractères.
  * La chaîne est s en paramètre n'est pas détruite.
  *
  * \param s La chaîne qui va permettre la construction de l'arbre de huffman.
  *
  * \return L'arbre de huffman créé à partir de la chaîne de caractères.
  */
arbreG huffman_creer_arbre(char * s);

/**
  * \fn arbreG string_racine_to_racine(char * s)
  * \brief Transforme la chaîne de caractères en noeud contenant un long et deux fils.
  * La chaîne s est sous la forme R n
  *
  * \param s Chaîne permettant de construire une racine.
  *
  * \return L'arbre contenant un entier et deux fils à NULL.
  */
arbreG string_racine_to_racine(char * s);

/**
  * \fn arbreG string_couple_to_racine(char * s)
  * \brief Transforme la chaîne de caractères en feuille contenant un couple de caractères et d'un hash associé.
  * La chaîne s est sous la forme F c hash
  *
  * \param s Chaîne permettant de construire une feuille.
  *
  * \return L'arbre contenant un couple de caractère et d'un hash associé.
  */
arbreG string_couple_to_racine(char * s);


/**
 * \fn void huffman_construire_arbre(FILE * fichier, arbreG * a)
 * \brief Créer l'arbre à partir du fichier compressé.
 * L'arbre a doit être à NULL. Il sera construit grâce aux informations contenues en entête du fichier.
 *
 * \param fichier Fichier compressé.
 * \param a   Arbre qui sera construit au fur et à mesure de la lecture du fichier.
 */
void huffman_construire_arbre(FILE * fichier, arbreG * a);

arbreG liste_arbre_vers_arbre_generique(liste *loc, long (*operation)(long, long));

/************************************************
*          Fonctions de récupération            *
************************************************/

/**
 * \fn char * sequence(arbreG a, char c)
 * \brief Retourne la séquence binaire entre la racine de l'arbre et le caractère.
 * Dans la séquence retournée, 0 indique indique un déplacement à gauche, 1 à droite.
 * Dans le cas où il n'y a qu'un seul caractere dans l'arbre (donc la racine est aussi une feuille), on retourne 0.
 * Dans le cas où le caractères n'est pas présent dans l'arbre, on retourne NULL.
 *
 * \param a Arbre dans lequel on cherche la séquence binaire correspondante au caractère.
 * \param c Caractère dont on cherche la séquence.
 *
 * \return La séquence binaire associée au caractère.
*/
char * sequence(arbreG a, char c);





/**
 * \fn void huffman_debug_arbre(arbreG a)
 * \brief Parcours et affiche l'arbre pour débogage.
 *
 * \param a Arbre que l'on parcours.
 */
void huffman_debug_arbre(arbreG a);

/************************************************
*          Fonctions de suppression             *
************************************************/

/**
 * \fn void huffman_supprimer_arbre(arbreG *ap)
 * \brief Supprime l'arbre de huffman de la mémoire.
 *
 * \param a Arbre que l'on supprime de la mémoire.
 */
void huffman_supprimer_arbre(arbreG *ap);

/************************************************
*    Fonctions de compresion / décompression    *
************************************************/

/**
 * \fn char * huffman_compresser_string(arbreG a, char * s)
 * \brief Retourne la chaîne binaire correspondante à la chaîne en paramètre.
 *
 * \param a L'arbre de huffman permettant la compression.
 * \param s Chaîne à compresser.
 *
 * \return La chaîne de caractères compressée.
 */
char * huffman_compresser_string(arbreG a, char * s);

/**
 * \fn char * huffman_decompresser_string(arbreG a, char * s)
 * \brief Prends une chaîne de caractères composée de 0 et de 1 et
 * un arbre générique de huffman afin de retourner la version décompressé
 * du message
 *
 * \param a 	arbre générique utilisé pour la décompression
 * \param s 	la chaine de caractères à décompresser
 *
 * \return la chaîne de caractères décompressée.
 */
char * huffman_decompresser_string(arbreG a, char * s);

/**
 * \fn char * huffman_decompresser_string_fichier(char * chemin)
 *\brief Prends un fichier .ho et retourne la version décompressée
 * du fichier dans un fichier de sortie
 *
 * \param chemin    chemin du fichier que l'on veut décompresser
 *
 */
void huffman_decompresser_string_fichier(char * chemin);


/**
 * \fn void interne_exporter_entete(arbreG a, FILE * f)
 * \brief Fonction interne à la fonction exporter entête, qui imprime la description de l'arbre dans le fichier.
 *
 * \param a L'arbre que l'on imprime dans le fichier compressé.
 * \param f Stream vers le fichier de sortie.
 */
void interne_exporter_entete(arbreG a, FILE * f);

/**
 * \fn FILE * initialisation_fichier_export(arbreG a, char * name)
 * \brief Retourne un stream vers le fichier pour pouvoir écrire en dessous de la description.
 *
 * \param a L'arbre que l'on imprime dans le fichier compressé.
 * \param name Nom du fichier de sortie.
 *
 * \return Un stream vers un fichier préparé contenant déjà la description de l'arbre.
 */
FILE * initialisation_fichier_export(arbreG a, char * name);

/**
 * \fn void compression(char * filename, arbreG a, liste strings)
 * \brief Compresse un fichier texte dans un fichier.
 *
 * \param name Nom du fichier de sortie.
 * \param a L'arbre permettant de compressé un caractère.
 * \param strings Liste des strings contenus dans le fichier texte à compresser.
 */
void compression(char * filename, arbreG a, liste strings);




#endif
