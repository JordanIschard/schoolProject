
/**
 * \file arbreG.c arbreG.h
 * \brief arbre générique
 * \author Jordan Ischard
 * \version 1.0
 *
 * arbreG est une structure permettant de contenir des valeurs génériques
 * sous la forme d'un arbre
 */
#include "arbreG.h"

/**
 * \fn arbreG creer_arbreG(void * elem, void(* copier)(void*, void**))
 * \brief Créer un arbre générique avec une valeur à mettre dans la racine
 *
 * \param elem		la valeur à mettre dans la racine
 * \param copier 	la fonction qui permet de copier l'élement dans ma
 * racine
 *
 * \return un arbre composé d'une racine
 *
 */
arbreG creer_arbreG(void *elem, void(* copier)(void*, void**)){
	arbreG ab = (arbreG) malloc(sizeof(struct noeudG));
	ab->fils_g=NULL;
	ab->fils_d=NULL;
	copier(elem, &ab->valeur);
	return ab;
}

/**
 * \fn int arbreG_estNul(arbreG a)
 * \brief la fonction informe si nous sommes
 * dans un arbre nul
 *
 * \param a 	l'arbre que l'on veut vérifier
 *
 * \return 1 si l'arbre est mis à NULL
 *      et 0 sinon
 */
int arbreG_estNul(arbreG a){ return a==NULL; }

/**
 * \fn int arbreG_est_une_feuille(arbreG a)
 * \brief la fonction informe si nous sommes
 * dans un noeud représentant une feuille
 *
 * \param a 	l'arbre que l'on veut vérifier
 *
 * \return 1 si a pointe sur une feuille de l'arbre
 *      et 0 sinon
 */
int arbreG_est_une_feuille(arbreG a){ return (a->fils_g==NULL && a->fils_d==NULL); }

/**
 * \fn int arbreG_ajouter(arbreG a, void * elem,int (*comparer)(void*, void*)
	,void(* copier)(void*, void**))
 * \brief Ajouter un élement dans l'arbre générique donné en paramètre
 *
 * \param a 	arbre générique à changer
 * \param elem 		la valeur à ajouter dans l'arbre
 * \param comparer 		la fonction qui permet de comparer deux noeuds
 * \param copier 	la fonction qui de copier l'élement dans le noeud
 *
 * \return 1 si tout c'est bien déroulé
 *         0 sinon
 *
 */
int arbreG_ajouter(arbreG a,void* elem, int (*comparer)(void*, void*)
	, void(* copier)(void*, void**)){
	if(a -> valeur == NULL){
		copier(elem, &(a -> valeur));
		return 1;
	}
	while(!arbreG_estNul(a)){
		if(comparer(elem,a->valeur)<0){
			if(arbreG_estNul(a->fils_g)){
				a -> fils_g = creer_arbreG(elem,copier);
				return 1;
			}
			a=a->fils_g;
		}else{
			if(arbreG_estNul(a->fils_d)){
				a -> fils_d = creer_arbreG(elem, *copier);
				return 1;
			}
			a=a->fils_d;
		}
	}
	return 0;
}


/**
 * \fn arbreG arbreG_union(arbreG *a,arbreG *b,void(* copier)(void*, void**)
	,void (* addition_element)(void *,void*, void **))
 * \brief Fait l'union de deux arbres génériques
 *
 * \param a 	1ère arbre générique utilisé pour l'union
 * \param b 	2nd arbre générique utilisé pour l'union
 * \param comparer  	la fonction qui permet de comparer deux noeuds
 * \param copier 	la fonction qui permet de copier un élement dans le noeud
 * \param addition_element 	la fonction permet de prendre la valeur de 2 noeuds
 * et de les additionner
 *
 * \return un nouvel arbre générique qui est l'union des 2 arbres génériques
 * donnés en paramètre
 *
 */
arbreG arbreG_union(arbreG *a,arbreG *b,void(* copier)(void*, void**)
	,void (* addition_element)(void *,void*,void **)){
	int z=0;
	arbreG res= creer_arbreG(&z,copier);
	addition_element((*a)->valeur,(*b)->valeur,&res->valeur);
	res->fils_g= (*a);
	res->fils_d= (*b);
	return res;
}

/**
 * \fn int arbreG_supprimer(arbreG *a,void * elem,int (*comparer)(void*, void*)
	,void (*detruire)(void**))
 * \brief Supprime un noeud et ses fils (si il en a)
 *
 * \param a 	arbre générique où l'on veut supprimer un élement
 * \param elem 		l'élement que l'on cherche à supprimer de l'arbre
 * \param comparer  	la fonction qui permet de comparer deux noeuds
 * \param detruire 		la fonction permet de supprimer tout les élements
 * à partir d'un noeud donné en paramètre
 *
 * \return 1 si tout se passe bien
 * 		   0 sinon (si on ne trouve pas l'élement)
 *
 */
int arbreG_supprimer(arbreG *a,void * elem,int (*comparer)(void*, void*)
	,void (*detruire)(void**)){
	arbreG tmp=(*a);
	while(!arbreG_estNul(tmp)){
		if(!arbreG_estNul(tmp->fils_g) && comparer(tmp->fils_g->valeur,elem)==0){
			arbreG_detruire(&(tmp->fils_g),detruire);
			return 1;
		}
		if(!arbreG_estNul(tmp->fils_d) && comparer(tmp->fils_d->valeur,elem)==0){
			arbreG_detruire(&(tmp->fils_d),detruire);
			return 1;
		}
		if(comparer(tmp->fils_g->valeur,elem)>0){
			tmp=tmp->fils_g;

		}else{
			tmp=tmp->fils_d;
		}
	}
	return 0;
}

/**
 * \fn int arbreG_appartient(arbreG a,void * elem,int (*comparer)(void*, void*))
 * \brief Informe si un élement donné en paramètre est dans l'arbre générique
 *
 * \param a 	arbre générique où l'on cherche l'appartenance d'un élement
 * \param elem 		l'élement que l'on cherche à trouver dans l'arbre
 * \param comparer  	la fonction qui permet de comparer deux noeuds
 *
 * \return 1 si elem appartient à l'arbre
 *         0 sinon
 *
 */
int arbreG_appartient(arbreG a, void* elem,int (*comparer)(void*, void*)){
	while(!arbreG_estNul(a)){
		int comp= comparer(elem,a->valeur);
		if(comp==0)
			return 1;
		if(comp<0){
			a=a->fils_g;
		}else{
			a=a->fils_d;
		}
	}
	return 0;
}

/**
 * \fn char* arbreG_chemin_element(arbreG a, void * elem,int (*comparer)(void*, void*))
 * \brief Donne le chemin d'un élement dans l'arbre en prenant compte que lorsque qu'on
 * va "à gauche" dans l'arbre on note un 0 et 1 sinon
 *
 * \param a 	arbre générique que l'on parcours pour trouver le chemin
 * \param elem 		l'élement dont on cherche son chemin
 * \param comparer  	la fonction qui permet de comparer deux noeuds
 *
 * \return le chemin de l'élement ou indique via un message qu'il ne l'a pas trouvé
 *
 */
char* arbreG_chemin_element(arbreG a, void * elem,int (*comparer)(void*, void*)){
	char* str= malloc(sizeof(char)*1);
	int i=2;
	while(!arbreG_estNul(a)){
		int comp= comparer(elem,a->valeur);
		if(comp==0)
			return str;
		if(comp<0){
			strcat(str,"0");
			a=a->fils_g;
		}else{
			strcat(str,"1");
			a=a->fils_d;
		}
		str= realloc(str,sizeof(char)*i);
		i++;
	}
	free(str);
	return "Element non trouvé";
}

/**
 * \fn void arbreG_detruire(arbreG* a,void (*detruire)(void**))
 * \brief Libère l'espace mémoire requisitionné pour l'arbre générique
 * et tout ce qu'il le compose
 *
 * \param a 	arbre que l'on veut détruire
 * \param detruire 		la fonction qui permet de libérer l'espace mémoire
 * prise par la valeur d'un noeud
 *
 */
void arbreG_detruire(arbreG* a,void (*detruire)(void**)){
	if(!arbreG_estNul((*a))){
		if(!arbreG_estNul((*a)->fils_g))	arbreG_detruire(&((*a)->fils_g),detruire);
		if(!arbreG_estNul((*a)->fils_d))	arbreG_detruire(&((*a)->fils_d),detruire);
		if(detruire != NULL) detruire(&((*a)->valeur));
		free(*a);
		(*a)=NULL;
	}
}

/**
 * \fn void arbreG_afficher(arbreG a,void (*afficher)(void*))
 * \brief Affiche l'arbre générique
 *
 * \param a 	arbre générique à afficher
 * \param afficher 		la fonction qui permet d'afficher la valeur d'un
 * noeud
 *
 */
void arbreG_afficher(arbreG a,void (*afficher)(void*)){
	if(!arbreG_estNul(a)){
		if(a->fils_g==NULL && a->fils_d==NULL){
			afficher(a->valeur);
			printf("\n");
		}else{
			afficher(a->valeur);
			printf("\n");
			printf("\n fils gauche de ");
			afficher(a->valeur);
			printf(" : ");
			arbreG_afficher(a->fils_g, afficher);
			printf("\n fils droit de ");
			afficher(a->valeur);
			printf(" : ");
			arbreG_afficher(a->fils_d, afficher);
		}
	}else{
		printf("\n");
	}
}
