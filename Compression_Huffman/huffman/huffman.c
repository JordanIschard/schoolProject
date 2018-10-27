/**
 * \file huffman.c huffman.h
 * \brief Les fonctions contenues dans ce fichier permettent d'appliquer l'algorithme de huffman.
 * Elles permettent de créer, manipuler et détruire l'arbre de huffman.
 * \author Jordan Ischard
 * \author Gael Marcadet
 * \version 1.0
 */
#include "huffman.h"

liste liste_couple_vers_liste_arbre(liste *loc);
liste nombre_occurences(char * s);
arbreG liste_arbre_vers_arbre(liste *loc);

/**
 * \fn void interne_exporter_entete(arbreG a, FILE * f)
 * \brief Fonction interne à la fonction exporter entête, qui imprime la description de l'arbre dans le fichier.
 *
 * \param a L'arbre que l'on imprime dans le fichier compressé.
 * \param f Stream vers le fichier de sortie.
 */
void interne_exporter_entete(arbreG a, FILE * f){
	if(a == NULL) return;
	if(a -> fils_g == NULL){
		couple c = a -> valeur;
		fprintf(f,"F %c %ld\n",c -> c, c -> hash);
	} else {
		fprintf(f,"R %lu\n",(*(unsigned long *)(a -> valeur)) );
	}
	interne_exporter_entete(a -> fils_g, f);
	interne_exporter_entete(a -> fils_d, f);
}


/**
 * \fn FILE * initialisation_fichier_export(arbreG a, char * name)
 * \brief Retourne un stream vers le fichier pour pouvoir écrire en dessous de la description.
 *
 * \param a L'arbre que l'on imprime dans le fichier compressé.
 * \param name Nom du fichier de sortie.
 *
 * \return Un stream vers un fichier préparé contenant déjà la description de l'arbre.
 */
FILE * initialisation_fichier_export(arbreG a, char * name){
	FILE * f;
	if((f = fopen(name, "w")) == NULL){
		printf("huffman: %s: Impossible d'ouvrir le fichier\n", name);
		return NULL;
	}
	interne_exporter_entete(a, f);
	fprintf(f, "END\n");
	fclose(f);

	if((f = fopen(name, "a+")) == NULL){
		printf("huffman: %s: Impossible d'ouvrir le fichier\n", name);
		return NULL;
	}

	fseek(f, SEEK_END, 0);
	return f;
}

/**
 * \fn void compression(char * filename, arbreG a, liste strings)
 * \brief Compresse un fichier texte dans un fichier.
 *
 * \param name Nom du fichier de sortie.
 * \param a L'arbre permettant de compresser un caractère.
 * \param strings Liste des strings contenus dans le fichier texte à compresser.
 */
void compression(char * filename, arbreG a, liste strings){
	FILE * f = initialisation_fichier_export(a,filename);
	noeud n = liste_get_premier(strings);
	while(n){
		char * ligne_compressee = huffman_compresser_string(a, noeud_get_valeur(n));
		fprintf(f, "%s\n",ligne_compressee);
		if(ligne_compressee[0] != '\0')
			free(ligne_compressee);
		n = noeud_get_suivant(n);
	}
	fclose(f);
}


/**
  * \fn arbreG huffman_creer_arbre(char * s)
  * \brief Créer un arbre de huffman à partir d'une chaîne de caractères.
  * La chaîne s en paramètre n'est pas détruite.
  *
  * \param s La chaîne qui va permettre la construction de l'arbre de huffman.
  *
  * \return L'arbre de huffman créé à partir de la chaîne de caractères.
  */
arbreG huffman_creer_arbre(char * s){
	liste l = nombre_occurences(s);
	l = liste_couple_vers_liste_arbre(&l);
	return liste_arbre_vers_arbre_generique(&l, long_addition);
}

/**
  * \fn arbreG string_racine_to_racine(char * s)
  * \brief Transforme la chaîne de caractères en noeud contenant un long et deux fils.
  * La chaîne s est sous la forme R n
  *
  * \param s Chaîne permettant de construire une racine.
  *
  * \return L'arbre contenant un entier et deux fils à NULL.
  */
arbreG string_racine_to_racine(char * s){
	// format attendu: R n
	unsigned long * hash = malloc(sizeof(unsigned long));
  (*hash) = atol(s+2);
	arbreG a = creer_arbreG(hash, &utils_copier_generique);
	return a;
}

/**
  * \fn arbreG string_couple_to_racine(char * s)
  * \brief Transforme la chaîne de caractères en feuille contenant un couple de caractère et d'un hash associé.
  * La chaîne s est sous la forme F c hash
  *
  * \param s Chaîne permettant de construire une feuille.
  *
  * \return L'arbre contenant un couple de caractère et d'un hash associé.
  */
arbreG string_couple_to_racine(char * s){
	// format attendu: F c hash
	char c;
	long hash = 0;
	c = *(s+2);
	char *np = s+4;
	hash = atoi(np);
	return creer_arbreG(creer_couple(c,hash), utils_copier_generique);
}

/**
 * \fn void construire_arbre(FILE * fichier, arbreG * a)
 * \brief Créer l'arbre à partir du fichier compressé.
 * L'arbre a doit être à NULL. Il sera construit grâce aux informations contenues en entête du fichier.
 *
 * \param fichier Fichier compressé.
 * \param a   Arbre qui sera construit au fur et à mesure de la lecture du fichier.
 */
void huffman_construire_arbre(FILE * fichier, arbreG * a){
	char * ligne = malloc(sizeof(char) * 100);

	fgets(ligne, 100, fichier);
	ligne[strlen(ligne) - 1] = '\0';
	if(!strcmp(ligne, "END")){
		free(ligne);
		return;
	}
	if(ligne[0] == 'R'){
		// Traitement racine
		(*a) = string_racine_to_racine(ligne);
		free(ligne);
		huffman_construire_arbre(fichier, &(*a) -> fils_g);
		huffman_construire_arbre(fichier, &(*a) -> fils_d);
	} else {
		// Traitement feuille
		(*a) = string_couple_to_racine(ligne);
		free(ligne);
	}
}


/**
 * \fn liste liste_couple_vers_liste_arbre(liste *loc)
 * \brief Transforme une liste de couple en liste d'arbre contenant des couples.
 * La liste en paramètre est détruite
 *
 * \param loc Pointeur sur la liste à transformer.
 *
 * \return La liste d'arbre contenant des couples.
 */
liste liste_couple_vers_liste_arbre(liste *loc){
	liste res= creer_liste(), l = *loc;
	noeud n = liste_get_premier(l);
	while(n){
		arbreG a = creer_arbreG(noeud_get_valeur(n), &utils_copier_generique);
		liste_ajouter_fin(res, a, utils_copier_generique);
		n = noeud_get_suivant(n);
	}
	liste_detruire_liste(loc, NULL);
	return res;
}


/**
 * \fn long getHash(arbreG a)
 * \brief Retourne le hash accosiée d'un couple ou la valeur d'une racine.
 *
 * \param a L'arbre (racine ou feulle) dont on souhaite obtenir la valeur.
 *
 * \return Le long contenu dans l'arbre.
 */
long getHash(arbreG a){
	if(a -> fils_g == NULL){
		couple c = a -> valeur;
		return c -> hash;
	} else {
		return (*(long *)(a -> valeur));
	}
}

/**
 * \fn arbreG supprimer_minimum(liste l)
 * \brief Supprime de la liste et retourne l'arbre contenant la valeur minimale.
 *
 * \param l Liste dont on souhaite retirer l'arbre minimal.
 *
 * \return L'arbre minimal contenu dans la liste.
 */
arbreG supprimer_minimum(liste l){
	if(l == NULL) return NULL;
	noeud n = liste_get_premier(l);

	if(n == NULL) return NULL;
	//couple_afficher(((arbreG)noeud_get_valeur(n)) -> valeur);
	noeud min = n, prec_min = NULL;
	unsigned long hash_min = getHash(noeud_get_valeur(min));


	while(noeud_get_suivant(n)){
		noeud n_suivant = noeud_get_suivant(n);
		unsigned long aux = getHash(noeud_get_valeur(n_suivant));
		if(aux < hash_min){
			hash_min = aux;
			min = n_suivant;
			prec_min = n;
		}
		n = n_suivant;
		n_suivant = noeud_get_suivant(n_suivant);
	}
	arbreG res = noeud_get_valeur(min);
	if(prec_min == NULL){
		liste_supprimer_debut(l, NULL);
	} else {
		noeud_set_suivant(prec_min, noeud_get_suivant(min));
		free(min);
	}

	return res;

}

/**
 * \fn arbreG liste_arbre_vers_arbre_generique(liste *loc, long (*operation)(long, long))
 * \brief Transforme la liste d'arbres en arbre de huffman.
 * La liste en paramètre est détruite.
 *
 * \param loc Pointeur sur liste d'arbres contenant des couples.
 * \param operation Fonction générique d'opération binaire, permettant de traiter deux valeurs vers un arbre.
 *
 * \return L'arbre généré à partir de la liste d'arbres.
 */
arbreG liste_arbre_vers_arbre_generique(liste *loc, long (*operation)(long, long)){
	liste l = *loc;
	int taille = liste_get_taille(l);

	while(taille > 1){
		arbreG min1 = supprimer_minimum(l), min2 = supprimer_minimum(l);
		long hash_min1 = getHash(min1), hash_min2 = getHash(min2);

		long * new_hash = malloc(sizeof(long));
		(*new_hash) = operation(hash_min1, hash_min2);

		arbreG aux = creer_arbreG(new_hash, utils_copier_generique);
		aux -> fils_d = min1;
		aux -> fils_g = min2;

		liste_ajouter_debut(l, aux, utils_copier_generique);
		--taille;
	}
	noeud tete = liste_get_premier(l);
	arbreG res = noeud_get_valeur(tete);
	free(tete);
	free(l);
	*loc = NULL;
	return res;
}

/**
 * \fn arbreG liste_arbre_vers_arbre(liste *loc)
 * \brief Transforme la liste d'arbres en arbre de huffman.
 * La liste en paramètre est détruite.
 *
 * \param loc Pointeur sur liste d'arbres contenant des couples.
 *
 * \return L'arbre généré à partir de la liste d'arbres.
 */
arbreG liste_arbre_vers_arbre(liste *loc){
	liste l = *loc;
	int taille = liste_get_taille(l);

	while(taille > 1){
		arbreG min1 = supprimer_minimum(l), min2 = supprimer_minimum(l);
		long hash_min1 = getHash(min1), hash_min2 = getHash(min2);

		long * new_hash = malloc(sizeof(long));
		(*new_hash) = hash_min1 + hash_min2;

		arbreG aux = creer_arbreG(new_hash, utils_copier_generique);
		aux -> fils_d = min1;
		aux -> fils_g = min2;

		liste_ajouter_debut(l, aux, utils_copier_generique);
		--taille;
	}
	noeud tete = liste_get_premier(l);
	arbreG res = noeud_get_valeur(tete);
	free(tete);
	free(l);
	*loc = NULL;
	return res;
}

/**
 * \fn liste nombre_occurences(char * s)
 * \brief Transforme une chaîne de caractère en une liste contenant les occurences de chaque caractère.
 *
 * \param s Chaîne dont on souhaite obtenir la liste des occurences.
 *
 * \return La liste des occurences.
 */
liste nombre_occurences(char * s){
	liste l = creer_liste();
	int n = strlen(s);
	for(int i = 0; i < n; ++i){
		liste_ajouter_ou_update(l, creer_couple(s[i],1),couple_copier, couple_compare, couple_update, couple_detruire);
	}
	return l;
}

/**
 * \fn void huffman_supprimer_arbre(arbreG *ap)
 * \brief Supprime l'arbre de huffman de la mémoire.
 *
 * \param a Arbre que l'on supprime de la mémoire.
 */
void huffman_supprimer_arbre(arbreG *ap){
	//printf("ap %p\n",ap );
	if(ap == NULL) return;
	arbreG a = *ap;
	if(a == NULL) return;
	if(a -> fils_d == NULL){
		couple c = (couple)a -> valeur;
		free(c);
	} else {
		long * aux = a -> valeur;
		free(aux);
	}
	huffman_supprimer_arbre(&a -> fils_g);
	huffman_supprimer_arbre(&a -> fils_d);
	free(a);
}

/**
 * \fn void huffman_debug_arbre(arbreG a)
 * \brief Parcours et affiche l'arbre pour débogage.
 *
 * \param a Arbre que l'on parcours.
 */
void huffman_debug_arbre(arbreG a){
	if(a == NULL) return;
	if(a -> fils_g == NULL){
		couple c = a -> valeur;
		printf("F %c %ld\n",c -> c, c -> hash);
	} else {
		long l = *(long *)(a -> valeur);
		printf("R %ld\n",l );
	}
	huffman_debug_arbre(a -> fils_g);
	huffman_debug_arbre(a -> fils_d);
}

/**
 * \fn char * inside_sequence(arbreG a, char c, char * seq)
 * \brief Méthode interne à sequence: Transforme un caractère en une séquenc binaire.
 *
 * \param a Arbre que l'on parcours.
 * \param c Caractère que l'on cherche.
 * \param seq Séquence binaire que l'on construit au fur et à mesure.
 *
 * \return La séquence binaire représentant le caractère.
 */
char * inside_sequence(arbreG a, char c, char * seq){
	if(a -> fils_d == NULL){
		couple cb = a -> valeur;
		if(cb -> c == c){
			return seq;
		}
		free(seq);
		return NULL;
	}
	char * sequence_gauche = string_concat(seq, "0");
	char * sequence_droite = string_concat(seq, "1");

	if(seq[0] != '\0')
		free(seq);

	char * resultat_gauche = inside_sequence(a -> fils_g, c, sequence_gauche);
	if(resultat_gauche){
		free(sequence_droite);
		return resultat_gauche;
	} else {
		return inside_sequence(a -> fils_d, c, sequence_droite);
	}
}

/**
 * \fn char * sequence(arbreG a, char c)
 * \brief Retourne la séquence binaire entre la racine de l'arbre et le caractère.
 * Dans la séquence retournée, 0 indique un déplacement à gauche, 1 à droite.
 * Dans le cas où il n'y a qu'un seul caractère dans l'arbre (donc la racine est aussi une feuille), on retourne 0.
 * Dans le cas où le caractère n'est pas présent dans l'arbre, on retourne NULL.
 *
 * \param a Arbre dans lequel on cherche la séquence binaire correspondante au caractère.
 * \param c Caractère dont on cherche la séquence.
 *
 * \return La séquence binaire associée au caractère.
 */
char * sequence(arbreG a, char c){
	if(a == NULL) return NULL;
	if(a -> fils_d == NULL) return "0";
	return inside_sequence(a, c, "\0");
}

/**
 * \fn char * huffman_compresser_string(arbreG a, char * s)
 * \brief Retourne la chaîne binaire correspondante à la chaîne en paramètre.
 *
 * \param a L'arbre de huffman permettant la compression.
 * \param s Chaîne à compresser.
 *
 * \return La chaîne de caractères compressée.
 */
char * huffman_compresser_string(arbreG a, char * s){
	char * res = "\0";
	int l = strlen(s);
	for(int i = 0; i < l; ++i){
		char * sequence_caractere = sequence(a,s[i]);
		char * concat_res_seq = string_concat(res, sequence_caractere);
		if(a -> fils_d != NULL)
			free(sequence_caractere);
		if(res[0] != '\0'){
			free(res);
		}
		res = concat_res_seq;
	}
	return res;
}


/**
 * \fn char * huffman_decompresser_string(arbreG a, char * s)
 * \brief Prend une chaîne de caractères composée de 0 et de 1 et
 * un arbre générique de huffman afin de retourner la version décompressée
 * du message
 *
 * \param a 	Arbre générique utilisé pour la décompression.
 * \param s 	Chaine de caractère à décompresser
 *
 * \return la chaîne de caractère décompressée
 *
 */
char * huffman_decompresser_string(arbreG a, char * s){
	if(arbreG_estNul(a)){
		return "Erreur l'arbre est vide";
	}else{
		char * res = malloc(sizeof(char)*(*(unsigned long *)a->valeur));
		arbreG tmp= a;
		int j=0;

		for(unsigned int i=0; i<strlen(s); i++){
			switch(s[i]){
				case '0': tmp=tmp->fils_g; break;
				case '1': tmp=tmp->fils_d; break;
			}
			if(arbreG_est_une_feuille(tmp)){
				res[j] = (char)couple_get_caractere(tmp->valeur);
				tmp = a;
				j++;
			}
		}
		if(strlen(res)>(*(unsigned long *)a->valeur)){
			res[strlen(res)-1]='\0';
		}
		return res;
	}
}

/**
 * \fn char * huffman_decompresser_string_fichier(char * chemin)
 *\brief Prends un fichier .ho et retourne la version décompressée
 * du fichier dans un fichier de sortie
 *
 * \param chemin    chemin du fichier que l'on veut décompresser
 *
 */
void huffman_decompresser_string_fichier(char * chemin){
	FILE * fichier = fopen(chemin,"r");
	char * new_chemin = "sortie.txt";
	FILE * fichier_sortie= fopen(new_chemin,"w");
	if (fichier != NULL){

		//Construction de l'arbre
		arbreG a =NULL;
		huffman_construire_arbre(fichier,&a);

		//Décompression du message
		char * tmp;
		char * chaine = malloc(sizeof(char)*1500);
		fgets(chaine, 1500, fichier);
        while ((tmp=fgets(chaine, 1500, fichier))!=NULL){
        	if(strcmp(tmp,"\n")!=0){
        		char * dec = huffman_decompresser_string(a,tmp);

        		fprintf(fichier_sortie, "%s\n",dec);
        		free(dec);
        	}else{
        		fprintf(fichier_sortie, "\n");
        	}

        }

        huffman_supprimer_arbre(&a);
    	free (chaine);
        free(tmp);
        fclose(fichier);
        fclose(fichier_sortie);

    }else{

        printf("Impossible d'ouvrir le fichier %s",chemin);

    }
}
