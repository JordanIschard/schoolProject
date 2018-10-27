
/**
 * \file utils.c utils.h
 * \brief Compilation de fonction
 * \author Gael Marcadet
 * \version 1.0
 *
 * utils est une compilation de fonction permettant
 * une utilisation de méthode générique.
 */
#include "utils.h"

/**
 * \fn long long_addition(long l1, long l2)
 * \brief Ajoute les deux long en paramètres
 *
 * \param l1 Long a ajouter.
 * \param l2 long a ajouter.
 *
 * \return L'addition des deux longs.
 */
long long_addition(long l1, long l2){
	return l1 + l2;
}

/**
 * \fn char * transformer_nom_fichier_sortie(char * fichier_entree)
 * \brief Rajoute ".ho" au fichier pour créer un nom de sortie.
 *
 * \param fichier_entre Nom de fichier à transformer.
 *
 * \return Le nom de fichier d'entrée avec l'extension .ho
 */
char * transformer_nom_fichier_sortie(char * fichier_entre){
	 return concat(fichier_entre, EXTENSION_OUT);
}

/**
 * \fn char * compression_tableau_string(int nbarg, char ** args)
 * \brief Compresse un tableau de chaînes de caractères en une seule chaîne.
 *
 * \param nbargs Nombre de chaînes.
 * \param args Tableau de chaînes de caractères;
 *
 * \return Une chaîne de caractères correspondant à une concaténation de chaque chaînes du tableau.
 */
char * compression_tableau_string(int nbarg, char ** args){
	if(nbarg == 0) return "";
	char * res = strdup(args[0]);
	for(int i = 1; i < nbarg; ++i){
		char * aux = string_concat(res, " ");
		free(res);
		res = aux;

		aux = string_concat(res,args[i]);
		free(res);
		res = aux;
	}
	return res;
}


/**
 * \fn void utils_exit()
 * \brief Quitte le programme en indiquant une mauvaise utilisation de la part de l'utilisateur.
 */
void utils_exit(){
	printf("Utilisation: huffman [STDIN] ou [OPTIONS] [fichier]\n");
	printf("Utilisez huffman -h pour afficher l'aide\n");
	exit(EXIT_FAILURE);
}

/**
 * \fn void * string_concat(void * v1, void * v2)
 * \brief Concatène deux chaînes de caractères
 *
 * \param v1    1ère chaîne de caractères
 * \param v2    2nd chaîne de caractères
 *
 * \return Un pointeur vers la chaîne de caractères.
 */
void * string_concat(void * v1, void * v2){
	char *s1 = (char *)v1, *s2 = (char *)v2;
	int ts1 = strlen(s1);
	int t = ts1 + strlen(s2);

	char * res = malloc(sizeof(char) * t + 1);
	for(int i = 0; i < t; ++i){
		res[i] = i < ts1 ? s1[i] : s2[i - ts1];
	}
	res[t] = '\0';
	return res;
}

/**
 * \fn  void string_copier(void * v, void ** pl)
 * \brief Mets un pointeur sur une chaîne de caractère
 *
 * \param v   chaîne de caractères à copier
 * \param pl    pointeur qui va pointer la chaîne de caractères
 *
 */
void string_copier(void * v, void ** pl){
	char * c = (char *)v;
	char ** cloc = (char **)pl;
	(*cloc) = c;
}

/**
 * \fn void string_dup(void * v, void ** pl)
 * \brief Copie une chaîne de caractères dans une zone
 * mémoire pointée.
 *
 * \param v   chaîne de caractères à copier.
 * \param pl    pointeur du duplicata de la chaîne de caractères.
 *
 */
void string_dup(void * v, void ** pl){
	char * c = (char *)v;
	char ** cloc = (char **)pl;
	(*cloc) = strdup(c);
}

/**
 * \fn void string_detruire(void * v)
 * \brief Désalloue la place mémoire d'une
 * chaîne de caractères.
 *
 * \param v   chaîne de caractères à détruire
 *
 */
void string_detruire(void * v){
	char * aux = (char *)v;
	free(aux);
}

/**
 * \fn void string_afficher(void * v)
 * \brief Afficher une chaîne de caractères.
 *
 * \param v   chaîne de caractères à afficher
 *
 */
void string_afficher(void * v){
	printf("%s ",((char *)v));
}

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
char * concat(char * s1, char * s2){
	int ts1 = strlen(s1);
	int t = ts1 + strlen(s2);

	char * res = malloc(sizeof(char) * t + 1);
	for(int i = 0; i < t; ++i){
		res[i] = i < ts1 ? s1[i] : s2[i - ts1];
	}
	res[t] = '\0';
	return res;
}

/**
 * \fn void utils_supprimer_arbre_couple(void * v)
 * \brief Supprime un arbre de couple
 *
 * \param v   arbre de couples à supprimer.
 */
void utils_supprimer_arbre_couple(void * v){
	if(v == NULL) return;
	arbreG a = v;
	couple c = a -> valeur;
	free(c);
	free(a);
}

/**
 * \fn void utils_copier_generique(void * v, void ** vloc)
 * \brief Fait pointer un pointeur sur une valeur donnée en
 * paramètre.
 *
 * \param v   élément à pointer.
 * \param vloc    pointeur
 *
 */
void utils_copier_generique(void * v, void ** vloc){
	(*vloc) = v;
}

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
void int_copier(void * v, void ** vloc){
	if(v == NULL){
		LOG("NULL VALUE INSERT : WARNING");
	}
	*vloc = (int*)malloc(sizeof(int));
    memcpy(*vloc,v,sizeof(int));
}

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
int int_comparer(void * v1, void * v2){
	int * i1 = (int *)v1, *i2 = (int *)v2;
	int res =  (*i1) - (*i2);
	return res;
}

/**
 * \fn void int_afficher(void * v)
 * \brief Affiche un entier
 *
 * \param v   entier à afficher
 *
 */
void int_afficher(void * v){
	if(v == NULL)
	   LOG("Access to NULL value");
	printf("%d\n", (*(int *)v));
}

/**
 * \fn void utils_afficher_arbre_couple(void *v)
 * \brief Affiche un arbre de couples
 *
 * \param v   arbre de couple à afficher
 *
 */
void utils_afficher_arbre_couple(void *v){
	arbreG a = (arbreG)v;
	couple c = (couple)a -> valeur;
	printf("%c %ld\n",c -> c, c -> hash );
}
