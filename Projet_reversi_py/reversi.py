###########################################
###         Partie initialisation       ###
###########################################

# Initialise le plateau de jeu
#
# param taille int le nombre de ligne et colonne
#
# return le plateau qui est un tableau à 2 dimensions
def Plateau(taille):
    # Mise en global pour y avoir accès dans tout le programme
    global lig
    global col
    global nbLigne
    global nbColonne
    global plateau

    plateau=[[' ' for ligne in range(taille)] for colonne in range(taille)]
    nbLigne = taille
    nbColonne = taille
    pos = int(taille/2)
    plateau[pos-1][pos-1] = plateau[pos][pos] = 'X'
    plateau[pos-1][pos] = plateau[pos][pos-1] = 'O'
    lig= Lig(taille)
    col= Col(taille)
    return plateau

# Créer une liste de nbLig lettre
#
# param nbLig int le nombre de ligne
#
# return la liste de lettre
def Lig(nbLig):
    li = [str(chr(97+ligne)) for ligne in range(nbLig)]
    return li

# Créer une liste de nbcolonne nombre
#
# param nbCol int le nombre de colonne
#
# return la liste de nombre
def Col(nbCol):
    co = [str(colonne+1) for colonne in range(nbCol)]
    return co

############################################
###         Partie fonctionnelle         ###
############################################

# Vérifie si la case pointée existe
#
# param index_ligne int l'index sur la ligne pointée
# param index_colonne int l'index sur la colonne pointée
#
# return un booléen qui est vrai si la case est dans le plateau et faux sinon
def estDansPlateau(index_ligne, index_colonne):
    return -1 < index_ligne < nbLigne and -1 < index_colonne < nbColonne

# Vérifie si la case pointée contient déjà un pion
#
# param index_ligne int l'index sur la ligne pointée
# param index_colonne int l'index sur la colonne pointée
#
# return un booléen vrai si la case contient un pion et faux sinon
def estUnPion(index_ligne, index_colonne):
    return plateau[index_ligne][index_colonne] != ' '

# Vérifie si on peut placer le pion dans la case pointée
#
# param index_ligne int l'index sur la ligne pointée
# param index_colonne int l'index sur la colonne pointée
#
# return un booléen vrai si l'on peut et faux sinon
def estPlacable(index_ligne, index_colonne):
    if(not estUnPion(index_ligne, index_colonne)):
        for co in range (index_colonne-1, index_colonne+2):
            for li in range (index_ligne-1, index_ligne+2):
                if(estDansPlateau(li,co)):
                    if(not estUnPion(li,co)):
                        return True
    return False

# Vérifie si la couleur(le caractère) donnée est
# la couleur(le caractère) de l'adversaire
#
# param macouleur la couleur(le caractère) du joueur
# param sacouleur la couleur(le caractère) du pion que l'on
#       regarde
#
# return un booléen vrai si c'est la couleur(le caractère) adversaire et faux sinon
def adversaire(macouleur, sacouleur):
    return macouleur != sacouleur

# Compte le nombre de pions de chaque joueur
#
# return un couple qui contient les compteurs
#        respectivement noir(X) et blanc(O)
def compteur():
    cptNoir = 0
    cptBlanc = 0
    for index_ligne in range(nbLigne):
        for index_colonne in range(nbColonne):
            if(estUnPion(index_ligne,index_colonne)):
                couleur = plateau[index_ligne][index_colonne]
                if(couleur == "X"):
                    cptNoir += 1
                else:
                    cptBlanc += 1
    return (cptNoir,cptBlanc)

# Vérifie que le joueur peut encore gagner
# (en effet si il n'a plus de pion sur le plateau
# il ne peut plus prendre de pion adverse)
#
# param macouleur la couleur(le caractère) du joueur
#
# return un booléen qui est vrai si il a encore des pions
#        sur le plateau et faux sinon
def PeutGagner(macouleur):
    if(macouleur == 'X'):
        (cpt,_) = compteur()
    else:
        (_,cpt) = compteur()
    return cpt > 0

# Vérifie si le plateau est complet
#
# return un booléen qui est vrai si le plateau est
# complet et faux sinon
def PlateauComplet():
    for index_ligne in range(nbLigne):
        for index_colonne in range(nbColonne):

            if(not estUnPion(index_ligne,index_colonne)):
                return False
    return True

# Donne l'index de la ligne via un numéro de ligne
#
# return int l'index de la ligne et -1 si elle n'est
#        pas trouvé
def getLigne(l):
    for ligne in range(nbLigne):
        if l == lig[ligne]:
            return ligne
    return -1

# Donne l'index de la colonne via un numéro de colonne
#
# return int l'index de la colonne et -1 si elle n'est
#        pas trouvé
def getColonne(c):
    for colonne in range(nbColonne):
        if c == col[colonne]:
            return colonne
    return -1


########################################
###         Partie principal         ###
########################################

# Place le pion sur la case pointée et fais appel à la méthode
# changement pour retourner les pions adjacents si il le faut
#
# param index_ligne int l'index sur la ligne pointée
# param index_colonne int l'index sur la colonne pointée
# param couleur char la couleur(le caractère) du joueur
#
# return un booléen qui est vrai si on a réussi à le placer et faux sinon
def placer(index_ligne, index_colonne, couleur):
    placer = False
    if(estPlacable(index_ligne, index_colonne)):
        for co in range (index_colonne-1, index_colonne+2):
            for li in range (index_ligne-1, index_ligne+2):
                if(estDansPlateau(li,co)):
                    sacouleur = plateau[li][co]
                    if(estUnPion(li,co)):
                        if(adversaire(couleur,sacouleur)):
                            direction = (li - index_ligne, co - index_colonne)
                            changement(couleur,li,co, direction)

                        plateau[index_ligne][index_colonne] = couleur
                        placer =True

    return placer

# Méthode récursive qui parcours tous les éléments selon une direction donnée
# et retourne les pions si le coup est valide
#
# param index_ligne int l'index sur la ligne pointée
# param index_colonne int l'index sur la colonne pointée
# param macouleur char la couleur(le caractère) du joueur
# param direction couple d'entier x et y qui donne la direction à parcourir
#
# return un booléen qui est vrai si le coup permet de retourner un/des pion(s) et faux si
#        la case pointée n'existe pas ou est vide
def changement(macouleur, index_ligne, index_colonne, direction):

    if(estDansPlateau(index_ligne,index_colonne) and estUnPion(index_ligne, index_colonne)):
        if(adversaire(macouleur,plateau[index_ligne][index_colonne])):
            case_suivante = (index_ligne + direction[0], index_colonne + direction[1])
            retourner = changement(macouleur, case_suivante[0], case_suivante[1], direction)
            if(retourner):
                plateau[index_ligne][index_colonne] = macouleur
                return True
        else:
            return True
    else:
        return False


########################################
###         Partie affichage         ###
########################################

# Ta fonction :p
def afficheLigneSeparatrices(tailleCellule=4):
    print()
    for i in range(nbColonne+1):
        print('-'*tailleCellule+ '+',end='')
    print()

# Ta fonction :p
def afficheMatrice(tailleCellule=4):
    print(' '*tailleCellule+ '|',end='')
    for i in range(nbColonne):
        print(str(i+1).center(tailleCellule)+'|',end='')

    afficheLigneSeparatrices(tailleCellule)
    for i in range(nbLigne):
        print(str(lig[i]).rjust(tailleCellule)+'|',end='')
        for j in range(nbColonne):
            print(str(plateau[i][j]).center(tailleCellule)+'|',end='')
        afficheLigneSeparatrices(tailleCellule)
    print()

# Affiche un message de bienvenue
def afficheBienvenueAccueil():
    print()
    print("#####################################")
    print("#                                   #")
    print("#                                   #")
    print("#              REVERSI              #")
    print("#                                   #")
    print("#                                   #")
    print("#####################################")
    print()
    print("   Bienvenue dans le jeu Reversi !")
    print()

# Affiche le choix de l'accueil
def afficheChoixAccueil():
    print()
    print("Que voulez-vous faire ?")
    print("   - Joueur vs Joueur (jvsj)")
    print("   - quitter          (quit)")
    print()

# Affiche le message de fin de l'accueil
def afficheFinAccueil():
    print()
    print("            Fin du jeu !")
    print()
    print("#####################################")
    print("#                                   #")
    print("#                                   #")
    print("#             AU REVOIR             #")
    print("#                                   #")
    print("#                                   #")
    print("#####################################")
    print()

# Affiche le message de bienvenue du mode JvsJ
def afficheBienvenueJvsJ():
    print()
    print("    #####################################")
    print("    #                                   #")
    print("    #        Joueur VS Joueur           #")
    print("    #                                   #")
    print("    #####################################")
    print()
    print("Bienvenue dans la partie joueur contre joueur !")
    print()

# Affiche le choix du mode JvsJ
def afficheChoixJvsJ():
    print()
    print("Que voulez-vous faire ?")
    print("   - Plateau standard 8x8 (ps)")
    print("   - Plateau modifiable   (pm)")
    print("   - quitter              (quit)")
    print()

# Affiche
def afficheFinJvsJ(joueur_noir, joueur_blanc, cpt_gagne_noir, cpt_gagne_blanc):
    print()
    print("%s a gagné %d fois" % (joueur_noir,cpt_gagne_noir))
    print()
    print("%s a gagné %d fois" % (joueur_blanc,cpt_gagne_blanc))
    print()
    print("          Fin du joueur vs joueur !")
    print()
    print("    #####################################")
    print("    #                                   #")
    print("    #       FIN JOUEUR VS JOUEUR        #")
    print("    #                                   #")
    print("    #####################################")
    print()



##########################################
###         Partie intéractive         ###
##########################################

# Vérifie si c'est une victoire du joueur noir, blanc ou une
# égalité et incrémente le nombre de victoire du gagnant
#
# param nom_noir nom du joueur noir
# param nom_blanc nom du joueur blanc
#
# return un couple d'int représentant qui a gagné
#        le premier est une victoire du joueur noir et
#        le second est une victoire du joueur blanc
def victoire(nom_noir, nom_blanc):
    (cptNoir,cptBlanc) = compteur()
    print("%s a %d point(s)" %(nom_noir,cptNoir))
    print()
    print("%s a %d point(s)" %(nom_blanc,cptBlanc))
    print()
    if(cptNoir > cptBlanc):
        print("%s a gagné !" % (nom_noir))
        return (1,0)
    elif(cptNoir < cptBlanc):
        print("%s a gagné !" % (nom_blanc))
        return (0,1)
    else:
        print("Égalité !")
        return (0,0)

# Donne le nom du joueur demandé à l'utilisateur
#
# param couleur la couleur(le caractère) du joueur
#
# return le nom du joueur
def choixNom(couleur):
    nom = input("Donner le nom du joueur %s : " % (couleur))
    print()
    return nom

# Fais jouer un joueur
#
# param nom_joueur le nom du joueur qui joue
# param couleur la couleur(le caractère) du joueur
#
# return un booléen qui vaut vrai si le placement c'est bien passée et faux sinon
def JouerJ(nom_joueur,couleur):
    print("À %s de jouer" %(nom_joueur))
    index_ligne = index_colonne = -1

    while(not estDansPlateau(index_ligne,index_colonne)):
        ligne = input("Donner une ligne : ")
        colonne = input("Donner une colonne : ")
        index_ligne = getLigne(ligne)
        index_colonne = getColonne(colonne)
    if(not placer(index_ligne,index_colonne,couleur)):
        print("On ne pas le placer là, veuillez recommencer")
        JouerJ(nom_joueur,couleur)

# Partie intéractive du mode JvsJ
def JoueurVsJoueur():
    afficheBienvenueJvsJ()
    commande = " "
    nom_joueur_noir = "Noir"
    nom_joueur_blanc = "Blanc"
    jeu_gagne_noir = 0
    jeu_gagne_blanc = 0

    while(commande != "quit"):
        afficheChoixJvsJ()
        commande = input()
        print()

        if(commande == "ps" or commande == "pm"):

            if(commande == "ps"):
                plateau = Plateau(8)
            else:
                taille = input("Donner la taille : ")
                print()
                plateau = Plateau(int(taille))

            nom_joueur_noir = choixNom("noir")
            nom_joueur_blanc = choixNom("blanc")

            while(not PlateauComplet()):

                if(PeutGagner('X')):
                    afficheMatrice()
                    JouerJ(nom_joueur_noir,'X')
                else:
                    break
                if(PeutGagner('O')):
                    afficheMatrice()
                    JouerJ(nom_joueur_blanc,'O')
                else:
                    break

            afficheMatrice()
            (victoire_noir,victoire_blanc) = victoire(nom_joueur_noir,nom_joueur_blanc)
            jeu_gagne_noir += victoire_noir
            jeu_gagne_blanc += victoire_blanc

        if(commande == "quit"):
            afficheFinJvsJ(nom_joueur_noir, nom_joueur_blanc, jeu_gagne_noir, jeu_gagne_blanc)

# Main
commande = " "
afficheBienvenueAccueil()

while(commande != "quit"):
    afficheChoixAccueil()
    commande = input()
    print()

    if(commande == "jvsj"):
        JoueurVsJoueur()
    if(commande == "quit"):
        afficheFinAccueil()
