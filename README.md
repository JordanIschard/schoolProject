# schoolProject

Projets réalisés lors de mes études à l'Université d'Orléans.

# DevMult

## Travail Rendu

- Un écran de connexion
- Un écran affichant la liste des lieux avec leur image associées
- Un écran de détail d’un lieu sans map
saisie soit utiliser la localisation actuelle de l’utilisateur.
- Un écran d’inscription
- Voir la liste des commentaires associées à un lieu.
- Pouvoir ajouter un commentaire.
- Voir son profil.
- Éditer son profil.
- Éditer son mot de passe.

## Problème rencontré

La map ainsi que la gestion des images non pas été implémenté car il n'arrive pas a accèder à l'appareil photo 
et la clé pour la map ne fonctionne pas.

=======

# Compilation
Utilisation de ANTLR pour créer une PMachine

## Travail réalisé

Le projet a été réalisé en suivant principalement la bibliographie proposée sur celene et les étapes des TPs. 
Le code est entièrement commenté *(excusez moi les fautes diverses et variées)*.

## Fonctionnalités

La PMachine contient tous les fonctionnements de base demandés dans les TPs :
- lecture 
- écriture
- gestion des entiers
- affectation
- boucle for,while
- condition
- tableau
- enregistrement
- type
- procédure

J'ai pour ma part ajouté :
- le switch
- la gestion des booléens
- les procédures imbriqués et récursives
- la boucle repeat
- les tableau avec choix de l'indéxation variable *(exemple : array [-3..8] of integer)*

## Les exemples

Pour tester il suffit de mettre le projet dans un IDE, vérifier que la dépendance avec ANTLR fonctionne bien 
et le lancer via L'IDE. 5 exemples sont proposés mais libre à vous dans rajouter pour les testers. Une fois écrit 
et placé dans le dossier *examples*, il se rajoutera à la liste des exemples lors de l'exécution.
Si vous voulez voir la table des symboles, le PCode généré et la mémoire à la fin de l'exécution alors demander
le **debug** *(vous en aurez la possibilité lors de l'exécution)*.


========

# CudaProject

## Introduction

Le projet **CUDA** est réalisé en binôme. Il est ici composée de *Mathieu Guilbert* et de *Jordan Ischard*.

Nous avons choisi de faire la convolution **laplacian of gaussian** avec un **grayscale** ainsi que la convolution **Edge Detection + Simple box blur** avec un **grayscale** également.
Ces convolutions seront fait respectivement par Jordan Ischard et Mathieu Guilbert.

---

## 1ère convolution : **Laplacian of Gaussian**

Cette convolution utilise la matrice suivante :
|   |   |   |   |   |
|---|---|---|---|---|
| 0 | 0 |-1 | 0 | 0 |
| 0 |-1 |-2 |-1 | 0 |
|-1 |-2 |16 |-2 |-1 |
| 0 |-1 |-2 |-1 | 0 |
| 0 | 0 |-1 | 0 | 0 |

Il est nécessaire d'effectuer un filtre **grayscale** sur l'image avant l'utilisation de cette convolution. D'où son rajout dans le programme. La première version va consister à effectuer cette convolution sur *CPU*, ensuite nous passerons sur *GPU* sans utilisation de mémoire partagée. 
Dans la version suivante, nous ajouterons la mémoire partagée pour voir le gain en temps d'exécution et enfin, si nous avons le temps, nous utiliserons les streams.

### Programme sur CPU

Cette version va simplement effectuer un **grayscale** et ensuite la convolution désirée. Sur cette partie aucun problème n'a été remarqué.

| Version | Nom de l'image | Dimensions | Temps d'exécution (millisecondes) |
| :--: | :--: | :--: | :--: |
| *CPU* | `color_building.jpg` | 853 x 1280 | 28 |
| *CPU* | `color_house.jpg` | 1920 x 1279 | 63 |

### Programme sur GPU

#### Première version : Sans mémoire partagée

Cette version va effectuer un **grayscale** et ensuite la convolution désirée.
Le gain est important mais il reste un gros soucis lié aux données entre le **grayscale** et la convolution qui sont obligatoirement réimportées sur le GPU.

Cette partie m'a bloqué sur un point que je ne pensais pourtant pas difficile. Lorsque que l'on récupère les données d'une image en noir et blanc la taille de celle-ci est `rows*cols`.
Cependant, pour une image en couleur elle est égale à `3*rows*cols`.

| Version | Nom de l'image | Dimensions | Nombre de threads | Temps d'exécution (millisecondes) | Gain sur la dernière version
| :--: | :--: | :--: | :--: | :--: | :--: |
| *GPU V1* | `color_building.jpg` | 853 x 1280 | 16 x 16 | 0.39 | x70 |
| *GPU V1* | `color_house.jpg` | 1920 x 1279 | 16 x 16 | 0.75 | x84 |
| *GPU V1* | `color_building.jpg` | 853 x 1280 | 32 x 32 | 0.32 | x84 |
| *GPU V1* | `color_house.jpg` | 1920 x 1279 | 32 x 32 | 0.59 | x105 |

#### Deuxième version : Avec mémoire partagée

Afin d'avoir un meilleur gain, nous avons ajouter une mémoire partagée. Les données de l'image initiale sont converties pour donner une image en noir et blanc. Dans la version précédente on perdait du temps en récupérant les données intermédiaires.
Pour palier à cela, on mets les données en noir et blanc dans la mémoire partagée.

Un problème est survenu via l'utilisation de la mémoire partagée. En effet, les indices étant un peu modifiés un cadriage apparraisait sur l'image de sortie.

| Version | Nom de l'image | Dimensions | Nombre de threads | Temps d'exécution (millisecondes) | Gain sur la dernière version
| :--: | :--: | :--: | :--: | :--: | :--: |
| *GPU V2* | `color_building.jpg` | 853 x 1280 | 16 x 16 | 0.28 | x1.43 |
| *GPU V2* | `color_house.jpg` | 1920 x 1279 | 16 x 16 | 0.51 | x1.47 |
| *GPU V2* | `color_building.jpg` | 853 x 1280 | 32 x 32 | 0.26 | x1.27 |
| *GPU V2* | `color_house.jpg` | 1920 x 1279 | 32 x 32 | 0.48 | x1.25 |

#### Troisième version : Avec streams

Nous pouvons pousser le parallélisme encore plus loin avec les streams. Théoriquement, séparer en deux l'image de départ pour le donner à deux streams différents devrait nous faire gagner du temps. En pratique on n'a aucun gain par rapport à la version précédente.

La jonction entre les deux streams n'est pas bien calculée et après différents essais je n'ai pas réussi à régler ce problème.

| Version | Nom de l'image | Dimensions | Nombre de threads | Nombre de streams | Temps d'exécution (millisecondes) | Gain sur la dernière version
| :--: | :--: | :--: | :--: | :--: | :--: | :--: |
| *GPU V3* | `color_building.jpg` | 853 x 1280 | 16 x 16 | 2 | 0.28 | x1 |
| *GPU V3* | `color_house.jpg` | 1920 x 1279 | 16 x 16 | 2 | 0.51 | x1 |
| *GPU V3* | `color_building.jpg` | 853 x 1280 | 32 x 32 | 2 | 0.26 | x1 |
| *GPU V3* | `color_house.jpg` | 1920 x 1279 | 32 x 32 | 2 | 0.48 | x1 |

### Résumé des résultats

| Version | Nombre de threads optimale | Nombre de streams optimale | Temps d'exécution en millisecondes (moyen sur les images testées) | Gain sur la dernière version (moyen)
| :--: | :--: | :--: | :--: | :--: |
| *GPU V1* | 32 x 32 | X | 0.46 | x97 |
| *GPU V2* | 32 x 32 | X | 0.37 | x1.26 |
| *GPU V3* | 32 x 32 | 2 | 0.37 | x1 |





## 2ème convolution : **Edge Detection + Simple box blur**

Ces convolutions utilisent les matrices suivantes :
|    |    |    |
|----|----|----|
| -1 | -1 | -1 |
| -1 |  8 | -1 |
| -1 | -1 | -1 |

|     |     |     |
|-----|-----|-----|
| 1/9 | 1/9 | 1/9 |
| 1/9 | 1/9 | 1/9 |
| 1/9 | 1/9 | 1/9 |


Comme pour la première convolution, il est nécessaire d'utiliser un filtre **grayscale** sur l'image avant d'utiliser de les convolutions voulues.
La première version va consister à effectuer les convolutions **Edge Detection et Simple box blur** sur *CPU*. La seconde sera sur *GPU* sans utilisation de mémoire partagée.
Enfin, la troisième sera sur GPU avec utilisation de la mémoire partagées.

ATTENTION: la convolution Simple box blur n'altère que très peu l'image d'entrée. Pour s'assurer qu'elle s'est bien réalisée,
vous pouvez cependant comparer la version finale de l'image avec la version intermédiaire sur laquelle le grayscale et l'edge detection ont été effectués.

### Programme sur CPU

Cette version va simplement effectuer un **grayscale** et ensuite la convolution désirée. Sur cette partie aucun problème n'a été remarqué.

| Version | Nom de l'image | Dimensions | Temps d'exécution (millisecondes) |
| :--: | :--: | :--: | :--: |
| *CPU* | `color_building.jpg` | 853 x 1280 | 43 |
| *CPU* | `color_house.jpg`   | 1920 x 1279 | 97 |


### Programme sur GPU

#### Première version : Sans mémoire partagée

Cette version va effectuer un **grayscale** et ensuite la convolution désirée.
Comme pour la convolution **Laplacian of Gaussian**, on gagne du temps avec cette version, mais il reste le problème lié aux données entre le **grayscale** et la convolution qui sont réimportées sur le GPU.

| Version | Nom de l'image | Dimensions | Nombre de threads | Nombre de blocs | Temps d'exécution (millisecondes)
| :--: | :--: | :--: | :--: | :--: | :--: |
| *GPU V1* | `color_building.jpg` | 853 x 1280 | 16 x 16 | 54 x 80 | 0.49 |
| *GPU V1* | `color_house.jpg`  | 1920 x 1279 | 16 x 16 | 120 x 80 | 0.95 |
| *GPU V1* | `color_building.jpg` | 853 x 1280 | 32 x 32 | 27 x 40 | 0.41 |
| *GPU V1* | `color_house.jpg`  | 1920 x 1279 | 32 x 32 | 60 x 40 | 0.78 |


#### Deuxième version (dossier V3) : Avec mémoire partagée


Ici aussi, un problème majeur à été l'apparition d'un cadrillage sur l'image de sortie.
Je n'ai pas réussi à résoudre ce problème.
