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
