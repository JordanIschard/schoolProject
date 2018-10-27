
/**
 * \file question1_G.c
 * \brief programme principal servant de teste à la structure 
 * d'arbre générique
 * \author Jordan Ischard
 * \version 1.0
 *
 * programme permettant de faire les testes pour la question 1 
 */
#include "question1_G.h"

/**
 * \fn void copier_entier(void* elem, void** ptr)
 * \brief Copie une valeur entière
 *
 * \param elem 		élément à copier
 * \param ptr 		pointeur de la case mémoire
 * où l'on veut copier l'élément
 *
 */
void copier_entier(void* elem, void** ptr) {
    *ptr = (int*)malloc(sizeof(int));
    memcpy(*ptr,elem,sizeof(int));
}

/**
 * \fn void afficher_entier(void* elem)
 * \brief Affiche une valeur entière
 *
 * \param elem 		élément que l'on veut 
 * afficher
 *
 */
void afficher_entier(void* elem) {
    int a = *((int*) elem);
    printf("%d ",a);
}

/**
 * \fn int comparer_entier(void * val1, void *val2)
 * \brief Compare 2 valeurs entières
 *
 * \param val1 		1ère valeur à comparer
 * \param val2 		2nd valeur à comparer
 *
 * \return >0 si val1>val2
 *		   =0 si val1=val2
 *	 	   <0 si val1<val2
 */
int comparer_entier(void * val1, void *val2){
	int a = *((int *)val1);
	int b = *((int *)val2);

	return a-b;
}

/**
 * \fn void detruire_entier(void** ptr_elem)
 * \brief désalloue la place mémoire utilisé pour un
 * pointeur d'entier
 *
 * \param ptr_elem 		pointeur sur la valeur entière
 *
 */
void detruire_entier(void** ptr_elem) { free(* ptr_elem); }

/**
 * \fn void addition_element_entier(void * elem , void * elem1,void ** ptr)
 * \brief additionne 2 entiers
 *
 * \param elem 		1ère valeur à addtionner
 * \param elem1		2nd valeur à additionner
 * \param ptr 		pointeur de la place mémoire où l'on
 * veut mettre le résultat de l'addition
 *
 */
void addition_element_entier(void * elem , void * elem1,void ** ptr){
	int c=*((int*)elem);
	int d=*((int*)elem1);
	c=(c+d);
	memcpy(*ptr,&c,sizeof(int));
}

/**
 * \fn int test_arbreG(void) 
 * \brief Programme de test
 *
 * \return 0 
 */
int test_arbreG(void){
	int c=5;
	printf("----Début du programme----\n\n");

	printf("\nCréation d'1ère arbre avec l'entier %d ...\n",c);
	arbreG a= creer_arbreG(&c,&copier_entier);
	arbreG_afficher(a,&afficher_entier);

	printf("\n---Début des Ajouts---\n\n");

	c=15;
	printf("Ajout de %d dans l'arbre \n",c);
	arbreG_ajouter(a,&c,&comparer_entier,&copier_entier);

	c=8;
	printf("Ajout de %d dans l'arbre \n",c);
	arbreG_ajouter(a,&c,&comparer_entier,&copier_entier);
	
	c=4;
	printf("Ajout de %d dans l'arbre \n",c);
	arbreG_ajouter(a,&c,&comparer_entier,&copier_entier);
	
	c=1;
	printf("Ajout de %d dans l'arbre \n",c);
	arbreG_ajouter(a,&c,&comparer_entier,&copier_entier);
	
	printf("\n---Fin des Ajouts---\n\n");
	arbreG_afficher(a,&afficher_entier);
	
	printf("\n---Début des recherches et des codes---\n\n");
	
	printf("%d appartient à l'arbre : %d\n",c,arbreG_appartient(a,&c,&comparer_entier) );
	char * str =arbreG_chemin_element(a,&c,&comparer_entier);
	printf("%d a pour code : %s\n",c,str);
	free(str);
	
	c=25;
	printf("%d appartient à l'arbre : %d\n",c,arbreG_appartient(a,&c,&comparer_entier) );
	printf("%d a pour code : %s\n",c,arbreG_chemin_element(a,&c,&comparer_entier) );
	
	printf("\n---Fin des recherches et des codes---\n\n");

	printf("\nCréation d'un 2ème arbre avec l'entier %d ...\n",c);
	c=100;
	arbreG e= creer_arbreG(&c,&copier_entier);
	arbreG_afficher(e,&afficher_entier);

	printf("\n---Union des deux arbres---\n\n");
	a=arbreG_union(&a,&e,&copier_entier,&addition_element_entier);
	arbreG_afficher(a,&afficher_entier);

	c=4;
	printf("\nSupression de %d dans l'arbre...\n\n",c);
	arbreG_supprimer(&a,&c,&comparer_entier,&detruire_entier);
	arbreG_afficher(a,&afficher_entier);

	printf("\n---Destruction de l'arbre---\n");
	arbreG_detruire(&a,&detruire_entier);
	arbreG_afficher(a,&afficher_entier);

	printf("\n----Fin du programme----\n");
	return 0;
}