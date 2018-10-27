
/**
 * \file main.c
 * \brief Main du programme
 * \author Jordan Ischard
 * \author Gael Marcadet
 * \version 1.0
 *
 * Main.c contient le main et toutes les fonctions lancées par ce dernier.
 */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "huffman/huffman.h"
#include "arbreG/arbreG.h"
#include "utils/utils.h"
#include "question1_G.h"


/**
 * Numéro de version du programme
 */
#define version "1.0.0"
/**
 * Extension du fichier de sortie
 */
#define EXTENSION_OUT ".ho"

/**
 * Énumération des commandes possibles pour le programme.
 */
enum COMMANDE {UNKNOWN, HELP, COMPRESS, UNCOMPRESS, VERSION, TEST, STDIN};
typedef enum COMMANDE CMD;


/**
 * \fn CMD definir_commande(char * s)
 * \brief Retourne la commande par rapport à une chaîne de caractères.
 *
 * \param s La chaîne représentant une commande que l'utilisateur a tapée dans le terminal.
 *
 * \return La commande compréhensible par le programme.
 */
CMD definir_commande(char * s){
	if(!(strcmp(s,"-h") && strcmp(s,"--help"))) return HELP;
	if(!(strcmp(s,"-c") && strcmp(s,"--compress"))) return COMPRESS;
	if(!(strcmp(s,"-u") && strcmp(s,"--uncompress"))) return UNCOMPRESS;
	if(!(strcmp(s,"-v") && strcmp(s,"--version"))) return VERSION;
	if(!(strcmp(s,"-t") && strcmp(s,"--test"))) return TEST;
	if(!(strcmp(s,"-s") && strcmp(s,"--stdin"))) return STDIN;
	return UNKNOWN;
}

/**
 * \fn void afficher_version()
 * \brief Affiche le numéro de version du programme.
 */
void afficher_version(){
	printf("Version du programme huffman: %s\n", version);
}

/**
 * \fn void afficher_aide()
 * \brief Affiche l'aide du programme.
 */
void afficher_aide(){
	printf("Huffman est une application permettant la compression et la décompression de fichier texte.\n");
	printf("Version: %s\n\n", version);

	printf("Utilisation: huffman [STDIN] ou [OPTIONS] [fichier]\n\n");

	printf("Options:\n");
	printf("\t-h --help: \t\tAffiche l'aide\n");

	printf("\t-s --stdin: \t\tCompresse du texte demandé en entrée standard dans un fichier.\n");

	printf("\t-c --compress: \t\tCompresse un fichier au format texte.\n");

	printf("\t-u --uncompress: \tCompresse un fichier au format texte.\n");

	printf("\t-v --version: \t\tAffiche le numéro de version.\n");

	printf("\t-t --test: \t\tTest la question 1 du sujet.\n");

	printf("\n");
	printf("Exemples:\n");
	printf("\thuffman -s: \t\t\tCompresse un texte demandé par le système dans le fichier par défaut \'sortie.txt\'.\n");
	printf("\thuffman -s fichier.txt: \tCompresse un texte demandé par le système dans le fichier \'fichier.txt\'.\n");
	printf("\thuffman -c fichier.txt: \tCompresse le fichier \'fichier.txt\'.\n");
	printf("\thuffman -u fichier: \t\tDécompresse le fichier \'fichier\'\n");
	printf("\thuffman \"Ceci est une chaîne\": \tCompresse la chaîne de caractère depuis l'entrée standard\n");
	printf("\thuffman ceci est une chaîne: \tpareil que précédement\n\n");

	printf("Merci de reporter les bugs à l'adresse: https://gitlab.com/Kermite/huffman-project.git\n");
}

/**
 * \fn char * liste_string_vers_string(liste l)
 * \brief Compresse une liste de strings en un seul string.
 *
 * \param l Liste de chaînes de caractères.
 *
 * \return Une chaîne de caractères correspondant à une concaténation de chaque chaîne de la liste.
 */
char * liste_string_vers_string(liste l){
	noeud n = liste_get_premier(l);
	char * res = "";
	while(n){
		char * aux = noeud_get_valeur(n);
		char * s = string_concat(res, aux);
		if(res[0] != '\0')
			free(res);
		res = s;
		n = noeud_get_suivant(n);
	}
	return res;
}

/**
 * \fn liste recuperer_liste_string_depuis_fichier(char * filename)
 * \brief Récupère une liste de chaînes de caractères d'un fichier.
 * Chaque ligne du fichier est ajoutée à la liste.
 *
 * \param filename Nom du fichier duquel on souhaite récupèrer les chaînes.
 *
 * \return Une liste contenant chaque ligne du fichier.
 */
liste recuperer_liste_string_depuis_fichier(char * filename){
	FILE * f;
	if((f = fopen(filename, "r")) == NULL){
		printf("huffman: lecture: Impossible d'ouvrir le fichier\n");
		utils_exit();
	}

	liste l = creer_liste();

	while(!feof(f)){

		char * aux = malloc(sizeof(char) * 1024);
		fgets(aux, 1024, f);

		int taille_aux = strlen(aux);
		if(aux[taille_aux - 1] == '\n'){
			aux[taille_aux - 1] = '\0';
		}
		if(aux[0] != '\n')
			liste_ajouter_fin(l, aux, utils_copier_generique);

	}
	fclose(f);
	return l;
}



/**
 * \fn char * compression_stdin(int argc, char ** argv)
 * \brief Compresse les arguments de l'entrée standard grâce à l'algorithme de huffman.
 *
 * \param argc Nombre d'arguments (de chaînes de caractères).
 * \param argv Tableau de chaînes de caractères.
 *
 * \return La chaîne de caractères compressée représentant la concaténation de chaque argument.
 */
char * compression_stdin(int argc, char ** argv){
	char * string = compression_tableau_string(argc, argv);
	// Puis on créer l'arbre de huffman
	arbreG a = huffman_creer_arbre(string);
	// Enfin on compresse la chaîne de caractere en chaîne binaire
	char * res = huffman_compresser_string(a, string);
	free(string);
	huffman_supprimer_arbre(&a);
	return res;
}

/**
 * \fn void compresser_liste_string_vers_fichier(liste l, char * fichier_sortie)
 * \brief Compresse une liste de chaînes de caractères et ajoute les séquences binaires dans le fichier en paramètre.
 *
 * \param l 	Liste de chaînes de caractères.
 * \param fichier_sortie	Nom du fichier de sortie qui sera soit généré soit écrasé.
 */
void compresser_liste_string_vers_fichier(liste l, char * fichier_sortie){
	// Création de l'arbre
	char * ligne_regroupee = liste_string_vers_string(l);

	arbreG a = huffman_creer_arbre(ligne_regroupee);
	free(ligne_regroupee);

	// Compression du fichier vers le fichier de sortie
	compression(fichier_sortie, a, l);

	// Libération mémoire
	liste_detruire_liste(&l, string_detruire);
	huffman_supprimer_arbre(&a);
}

/**
 * \fn void compresser_fichier(int argc, char ** argv)
 * \brief Compresse le fichier passé en argument grâce à l'algorithme de huffman.
 *
 * \param argc Nombre d'arguments (de chaînes de caractères).
 * \param argv Tableaud de chaînes de caractères.
 */
void compresser_fichier(int argc, char ** argv){
	if(argc == 1){
		utils_exit();
	}
	// Préparation pour lecture de l'arbre
	char * fichier = argv[0];
	char * fichier_sortie = transformer_nom_fichier_sortie(fichier);

	liste l = recuperer_liste_string_depuis_fichier(fichier);

	compresser_liste_string_vers_fichier(l, fichier_sortie);

	free(fichier_sortie);
}

/**
 * \fn void demande_depuis_entree_standard(char ** argv)
 * \brief Compresse un texte demandé depuis l'entrée standard dans un fichier texte.
 *
 * \param argc Nombre d'arguments (de chaînes de caractères).
 */
void demande_depuis_entree_standard(char ** argv){

	char * fichier_sortie = argv[1];
	if(fichier_sortie == NULL){
		printf("Aucun nom de fichier en sortie saisie. Utilisation par défaut de \"sortie.txt\"\n");
		fichier_sortie = "sortie.txt";
	}

	liste l = creer_liste();
	printf("Début de la saisie. Pour arrêter, écrivez \"END\"\n\n");
	char * response;
	int stop = 0;
	do{
		response = malloc(sizeof(char) * 200);
		scanf("%s",response);
		stop = strcmp(response, "END");
		if(stop){
			liste_ajouter_fin(l, response, utils_copier_generique);
		} else {
			free(response);
		}
	}while(stop);

	if(liste_get_taille(l) == 0){
		printf("Aucune ligne à imprimer. Fin du programme\n");
		exit(0);
	}
	compresser_liste_string_vers_fichier(l, fichier_sortie);
	printf("\n");

}





/**
 * \fn void decompresser_fichier(int argc, char ** argv)
 * \brief Décompresse le fichier passé en argument en inversant l'algorithme de huffman.
 *
 * \param argc Nombre d'arguments (de chaînes de caractères).
 * \param argv Tableau de chaînes de caractères.
 */
void decompresser_fichier(int argc, char ** argv){
	if(argc == 1){
		utils_exit();
	}
	char * fichier = argv[1];
	huffman_decompresser_string_fichier(fichier);
}


/**********************
*         MAIN        *
***********************/

/**
 * \fn int main(int argc, char ** argv)
 * \brief Main du programe.
 *
 * \param argc Nombre d'arguments (de chaînes de caractères).
 * \param argv Tableau de chaînes de caractères.
 *
 * \return Code de retour du programme.
 */
int main(int argc, char ** argv){
	if(argc == 1){
		printf("Utilisation: huffman [STDIN] ou [OPTIONS] [fichier]\n");
		printf("Utilisez huffman -h pour afficher l'aide\n");
		return EXIT_FAILURE;
	}

	const CMD commande = definir_commande(argv[1]);
	--argc;
	switch (commande) {
		case HELP: afficher_aide(); break;
		case VERSION: afficher_version(); break;
		case COMPRESS: compresser_fichier(argc, argv+2); break;
		case UNCOMPRESS: decompresser_fichier(argc, argv+1); break;
		case TEST: test_arbreG(); break;
		case STDIN: demande_depuis_entree_standard(argv+1); break;
		default: {
			char * res = compression_stdin(argc, argv+1);
			printf("%s\n", res);
			free(res);
		}

	}
	return EXIT_SUCCESS;
}
