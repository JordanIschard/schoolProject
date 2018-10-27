
/**
 * \file arbre.c arbre.h
 * \brief arbre binaire
 * \author Jordan Ischard
 * \version 1.0
 *
 * arbre est une structure permettant de contenir des valeurs entières 
 * sous la forme d'un arbre binaire
 */
 #include "arbre.h"

/**
 * \fn arbre_binaire creer_arbre(int elem)
 * \brief Créer un arbre avec une valeur à mettre dans la racine 
 *
 * \param elem		la valeur à mettre dans la racine 
 *
 * \return un arbre composé d'une racine 
 * 
 */
arbre_binaire creer_arbre(int elem){
	arbre_binaire ab =malloc(sizeof(arbre_binaire)); 
	ab->fils_g=NULL;
	ab->fils_d=NULL;
	ab->valeur=elem;
	return ab;
}

/**
 * \fn int arbreG_estVide(arbre_binaire a)
 * \brief la fonction informe si nous sommes
 * dans un arbre nul
 *
 * \param a 	l'arbre que l'on veut vérifier
 *
 * \return 1 si l'arbre est mis à NULL 
 *      et 0 sinon
 */
int arbre_estVide(arbre_binaire a){ return a==NULL; }

/**
 * \fn int arbre_ajouter(arbre_binaire a, int elem)
 * \brief Ajoute un élement dans l'arbre donné en paramètre 
 *
 * \param a 	l'arbre à changer
 * \param elem 		la valeur à ajouter dans l'arbre 
 *
 * \return 1 si tout c'est bien déroulé 
 *         0 sinon
 * 
 */
int arbre_ajouter(arbre_binaire a, int elem){
	while(!arbre_estVide(a)){
		if(a->valeur>elem){
			if(arbre_estVide(a->fils_g)){
				a->fils_g=creer_arbre(elem);
				return 0;
			}
			a=a->fils_g;
		}else{
			if(arbre_estVide(a->fils_d)){
				a->fils_d=creer_arbre(elem);
				return 0;
			}
			a=a->fils_d;
		}
	}
	return 1;
}

/**
 * \fn arbre_binaire arbre_union(arbre_binaire *a,arbre_binaire *b)
 * \brief Fait l'union de deux arbres binaire
 *
 * \param a 	1ère arbre binaire utilisé pour l'union 
 * \param b 	2nd arbre binaire utilisé pour l'union
 *
 * \return un nouvel arbre binaire qui est l'union des 2 arbres binaire
 * donnés en paramètre
 * 
 */
arbre_binaire arbre_union(arbre_binaire *a,arbre_binaire *b){
	arbre_binaire res= creer_arbre((*a)->valeur+(*b)->valeur);
	res->fils_g= (*a);
	res->fils_d= (*b);
	return res;
}

/**
 * \fn int arbre_supprimer(arbre_binaire *a,int elem)
 * \brief Supprime un noeud et ses fils (si il en a)
 *
 * \param a 	arbre binaire où l'on veut supprimer un élement 
 * \param elem 		l'élement que l'on cherche à supprimer de l'arbre
 *
 * \return 1 si tout se passe bien 
 * 		   0 sinon (si on ne trouve pas l'élement)
 * 
 */
int arbre_supprimer(arbre_binaire *a,int elem){
	arbre_binaire tmp=(*a);
	while(!arbre_estVide(tmp)){
		if(!arbre_estVide(tmp->fils_g) && tmp->fils_g->valeur==elem){
			arbre_detruire(&(tmp->fils_g));
			return 0;
		}
		if(!arbre_estVide(tmp->fils_d) && tmp->fils_d->valeur==elem){
			arbre_detruire(&(tmp->fils_d));
			return 0;
		}
		if(tmp->valeur>elem){ 
			tmp=tmp->fils_g;

		}else{
			tmp=tmp->fils_d;
		}
	}
	return 1;
}

/**
 * \fn int arbre_appartient(arbre_binaire a, int elem)
 * \brief Informe si un élement donné en paramètre est dans l'arbre binaire 
 *
 * \param a 	arbre binaire où l'on cherche l'appartenance d'un élement
 * \param elem 		l'élement que l'on cherche à trouver dans l'arbre
 *
 * \return 1 si elem appartient à l'arbre 
 *         0 sinon
 * 
 */
int arbre_appartient(arbre_binaire a, int elem){
	//retourne 1 si il est dedans 0 sinon
	while(!arbre_estVide(a)){
		if(a->valeur==elem) 
			return 0;
		if(a->valeur>elem){ 
			a=a->fils_g;
		}else{
			a=a->fils_d;
		}
	}
	return 1;
}

/**
 * \fn char* arbre_chemin_element(arbre_binaire a, int elem)
 * \brief Donne le chemin d'un élement dans l'arbre en prenant compte que lorsque qu'on
 * va "à gauche" dans l'arbre on note un 0 et 1 sinon
 *
 * \param a 	arbre binaire que l'on parcours pour trouver le chemin
 * \param elem 		l'élement dont on cherche son chemin 
 *
 * \return le chemin de l'élement ou indique via un message qu'il ne la pas trouvé
 * 
 */
char* arbre_chemin_element(arbre_binaire a, int elem){
	char* str= malloc(sizeof(char)*1);
	int i=2;
	while(!arbre_estVide(a)){
		if(a->valeur==elem) 
			return str;
		if(a->valeur>elem){
			strcat(str,"1"); 
			a=a->fils_g;
		}else{
			strcat(str,"0");
			a=a->fils_d;
		}
		str= realloc(str,sizeof(char)*i);
		i++;
	}
	free(str);
	return "Element non trouvé";
}

/**
 * \fn void arbre_detruire(arbre_binaire * a)
 * \brief Libère l'espace mémoire requisitionné pour l'arbre binaire
 * et tout ce qu'il le compose
 *
 * \param a 	arbre que l'on veut détruire 
 *
 */
void arbre_detruire(arbre_binaire* a){
	if(!arbre_estVide((*a))){
		if(!arbre_estVide((*a)->fils_g))	arbre_detruire(&((*a)->fils_g));
		if(!arbre_estVide((*a)->fils_d))	arbre_detruire(&((*a)->fils_d));
		free(*a);
		(*a)=NULL;
	}
}		

/**
 * \fn void arbre_afficher(arbre_binaire a)
 * \brief Affiche l'arbre binaire 
 *
 * \param a 	arbre binaire à afficher 
 *
 */
void arbre_afficher(arbre_binaire a){
	if(!arbre_estVide(a)){
		if(a->fils_g==NULL && a->fils_d==NULL){
			printf("%d \n", a-> valeur);
		}else{
			printf("%d \n", a-> valeur);
			printf("\n fils gauche de %d : ", a->valeur);
			arbre_afficher(a->fils_g);
			printf("\n fils droit de %d : ", a->valeur);
			arbre_afficher(a->fils_d);
		}
	}else{
		printf("\n");
	}
}	
