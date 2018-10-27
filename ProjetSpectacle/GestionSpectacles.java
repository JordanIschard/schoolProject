package ProjetSpectacle;
/**
 * @author mathieu
 *
 */

import java.io.*;
import java.util.*;

public class GestionSpectacles {
	
	static Scanner in=new  Scanner (System.in);
	static Scanner sc=new Scanner (System.in);
	static Scanner sc0=new Scanner (System.in);

	public static void main(String[] args) {
		
		Scanner sc= new Scanner(System.in);
		
		LinkedList<Salle> SallesCinema = ChargerSalleCinema("/home/jordan/Bureau/licence Informatique/L2/S3/POB/Projet2017/SallesCinema.csv");
		LinkedList<SalleTheatre> SallesTheatre = ChargerSalleTheatre("/home/jordan/Bureau/licence Informatique/L2/S3/POB/Projet2017/SallesTheatre.csv");
		List<ProgrammationSemaine> lesProgrammations= new ArrayList<ProgrammationSemaine>();
		String s="0", d="default" , e="0";					//,d="0";
		int i, nbrProg=0;               //nbrProg= nombre de 'ProgrammationSemaine' de 'lesProgammations'.
		
		while(!s.equals("q")){
			
			System.out.println("Pour créer la programmation de la semaine suivante tapez 1");
			
			if(nbrProg>0) {
				
				System.out.println("Pour modifier une programmation existante tapez 2");
				System.out.println("Pour vendre des places pour une programmation tapez 3");
				System.out.println("Pour consulter les informations concernant les ventes de places tapez 4"); //càd chiffre d'affaire et taux de remplissage.
				
			}
			System.out.println("Pour arrêtez le programme tapez q");
			
			s=sc.nextLine();
			
			if(s.equals("1")){                          //programmation de la semaine  
				
				ProgrammationSemaine semaine=new ProgrammationSemaine(nbrProg+1);
				nbrProg++;
				
				System.out.println("Vous programmez actuellement la semaine numero "+nbrProg+":");
				
				while(!d.equals("fin")){
					
					System.out.println();
					System.out.println("	-Pour programmer un film tapez f");
					System.out.println("	-Pour programmer une pièce de théâtre tapez t");
					System.out.println("	-Pour arrêter la programmation de la semaine suivante tapez fin");
					
					d=sc0.nextLine();
					
					if(d.equals("f")){		
						
						System.out.println();
						Film f=creerFilm(semaine);          //création du film			
						TreeSet<SeanceCinema> liste= new TreeSet<SeanceCinema>();
						e="0";
						
						while(!e.equals("end")){	
							
							liste.add(creerSCine(SallesCinema));            //création des séances
							System.out.println();
							System.out.println("	-Pour arrêter de créer des séances tapez end, sinon tapez n'importe quoi:");
							e=sc0.nextLine();
							
						}
						
						semaine.ajouterFilms(f, liste);      
						
					}
					
					if(d.equals("t")){
						
						PieceTheatre r=creerPiece(semaine);          //création de la pièce de théâtre		
						TreeSet<SeanceTheatre> liste= new TreeSet<SeanceTheatre>();
						e="0";
						
						while(!e.equals("end")){			
							
							liste.add(creerSTheatre(SallesTheatre));        //création des séances
							System.out.println();
							System.out.println("	-Pour arrêter de créer des séances tapez end, sinon tapez n'importe quoi:");
							e=sc0.nextLine();
							
						}
						
						semaine.ajouterPiece(r, liste);      						
					}
					
					lesProgrammations.add(semaine);
				}
			}
			
			if(s.equals("2")){             //modif programmation existante
				
				System.out.println();
				System.out.println(nbrProg+" semaines sont programmées.");
				System.out.println("Saisir le numéro de la semaine que vous souhaitez modifier: ");   // si supérieur à 7 ? inf à 0 ?
				
				i=in.nextInt(); 
				
				ProgrammationSemaine sem=lesProgrammations.get(i);
				
				modifSemaine(sem, SallesCinema, SallesTheatre);
				
			}
			
			
			if(s.equals("3")){            //vendre des places
				
				System.out.println();
				System.out.println(nbrProg+" semaines sont programmées.");
				System.out.println("	-Pour quelle semaine souhaitez vous réserver ?");        //meme prob que "2";     try catch ou while ?
				
				i=in.nextInt();
				
				ProgrammationSemaine sem=lesProgrammations.get(i);
				
				System.out.println("	-Pour réserver pour un film tapez f");
				System.out.println("	-Pour réserver pour une pièce de théâtre tapez t");
				System.out.println("	-Pour revenir en arrière tapez annuler");
				
				d="default";
				
				while(!d.equals("annuler") && !d.equals("f") && !d.equals("t")){  
					
					d=sc0.nextLine();
					
					if(d.equals("f")){
						
						ReservePlaceCine(sem);
						
					}
					if(d.equals("t")){        
						
						ReservePlaceTheatre(sem);
						
					}
					if(!d.equals("annuler") && !d.equals("f") && !d.equals("t")){
						
						System.out.println("Saisissez t, f ou annuler");
						
					}
				}
			}
			
			if(s.equals("4") && nbrProg>0){                 //consulter
				System.out.println(nbrProg+" semaines sont programmées.");
				System.out.println("Quelle semaine souhaitez vous consulter ?");        //meme prob que "2";     try catch ou while ?
				
				i=in.nextInt();
				
				ProgrammationSemaine sem=lesProgrammations.get(i);
				
				System.out.println("	-Pour consulter un film tapez f");
				System.out.println("	-Pour consulter une pièce de théâtre tapez t");
				System.out.println("	-Pour revenir en arriere tapez annuler");
				
				d="default";
				
				while(!d.equals("annuler") && !d.equals("f") && !d.equals("t")){ 
					
					d=sc0.nextLine();
					
					if(d.equals("f")){
						
						Film f=null;
						boolean estdedans=false;
						Set<Film> setF=sem.getFilmsSemaine();
						
						System.out.println("Liste des films de la semaine:");
						System.out.println(setF.toString());
						System.out.println("saisir le nom du Film que vous souhaitez consulter");
						String titre=sc0.nextLine();
						
						Iterator<Film> it= setF.iterator();
						
						while(it.hasNext()){             
							
							Film tmp=it.next();
							if(tmp.getTitre().equals(titre)){
								
								f=tmp;
								estdedans=true;
								
							}
						}
						
						if(!estdedans){
							
							System.out.println("Ce film n'existe pas");
							
						}else{
							
							Set<SeanceCinema> setSC=sem.getToutesLesSeancesCinema(f);
							
							System.out.println("Liste des séances: " + setSC.toString());
							System.out.println("Quelle séance souhaitez vous consulter ?");
							System.out.println("Saisir le jour:");
							
							int jour=in.nextInt();
							
							int heure;
							int minute;
							do {
								System.out.println("	-Saisir la durée du film (commencer par les heures puis les minutes):");
								
								heure=in.nextInt();
								minute=in.nextInt();
						
							}while(!Heure.horaireValide(heure,minute));
							Heure h=new Heure(heure,minute);
							boolean consulte=false;
							Iterator<SeanceCinema> SC= setSC.iterator();
							
							while(SC.hasNext()){
								
								SeanceCinema tmp=SC.next();
								
								if(tmp.getJour()==jour && tmp.getHoraire().equals(h)){
									
									consulte=true;
									Consulter(tmp,sem);
									
								}
								if(!consulte){
									
									System.out.println("Cette séance n'existe pas"); 
									
								}	
							}	
						}
					}	
					if(d.equals("t")){ 
						PieceTheatre t=null;
						boolean estdedans=false;
						Set<PieceTheatre> setT=sem.getPiecesSemaine();
						
						System.out.println("Liste des pieces de theatre de la semaine:");
						System.out.println(setT.toString());
						System.out.println("saisir le nom de la piece que vous souhaitez consulter");
						
						String titre=sc0.nextLine();
						Iterator<PieceTheatre> it= setT.iterator(); //Pas SP car on a besoin du t ET du estdedans
						
						while(it.hasNext()){
							
							PieceTheatre tmp=it.next();
							
							if(tmp.getTitre().equals(titre)){
								
								t=tmp;
								estdedans=true;
							}
							
						}
						if(!estdedans){
							
							System.out.println("Cette piece n'existe pas");
							
						}else{
							
							Set<SeanceTheatre> setST=sem.getToutesLesSeancesTheatre(t);
							
							System.out.println("Liste des séances: " + setST.toString());
							System.out.println("Quelle séance souhaitez vous consulter ?");
							System.out.println("Saisir le jour:");
							
							int jour=in.nextInt();
							int heure;
							int minute;
							do {
								
								System.out.println("	-Saisir la durée du film (commencer par les heures puis les minutes):");
								
								heure=in.nextInt();
								minute=in.nextInt();
						
							}while(!Heure.horaireValide(heure,minute));
							Heure h=new Heure(heure,minute);
							
							boolean consulte=false;
							Iterator<SeanceTheatre> ST= setST.iterator();
							
							while(ST.hasNext()){
								
								SeanceTheatre tmp=ST.next();
								
								if(tmp.getJour()==jour && tmp.getHoraire().equals(h)){ 
									
									consulte=true;
									Consulter(tmp,sem);
									
								}
								
								if(!consulte){
									
									System.out.println("Cette séance n'existe pas");
									
								}	
							}	
						}
					}

					if(!d.equals("annuler") && !d.equals("f") && !d.equals("t")){
						
						System.out.println("Saisissez t, f ou annuler");
						
					}
				}
			}		
			
			if(!s.equals("1") && !s.equals("2") && !s.equals("3") && !s.equals("4") && !s.equals("q")){  //Si l'utilisateur ne rentre pas un des 5 caractères acceptés:
				
				System.out.println("veillez saisir 1, 2, 3, 4 ou q");
				
			}	
		}
			
		sc.close();  
		System.out.println("Fin du programme");
	}

	
	
	/**Permet de créer un nouveau film
	 * de manière interactive
	 * 
	 * @param ProgrammationSemaine p
	 * @return Film
	 */
	public static Film creerFilm(ProgrammationSemaine p){
		
		int heure=0,minute=0;
		
		System.out.println("Création d'un film:");
		System.out.println("	-Saisir le titre du film:");
		
		String titre=sc0.nextLine();
		
		System.out.println("	-Saisir l'interprete du film:");   //pas suffisant
		
		String interprete=sc0.nextLine();
		
		System.out.println("	-Saisir le réalisateur du film:");
		
		String real=sc0.nextLine();
		
		do {
			
			System.out.println("	-Saisir la durée du film (commencer par les heures puis les minutes):");
			
			heure=sc0.nextInt();
			minute=sc0.nextInt();
	
		}while(!Heure.horaireValide(heure,minute));
		
		Heure h=new Heure(heure,minute);
		Film f=new Film(titre,interprete,real,h);
		return f;
	}
	
	/**Permet de créer une nouvelle séance de cinéma
	 * de manière interactive
	 * 
	 *
	 * @return SeanceCinema
	 */
	public  static SeanceCinema creerSCine(LinkedList<Salle> SallesCinema){
		
		int jour,heure=0,minute=0;
		
		System.out.println("Création d'une séance de cinéma:");
		System.out.println("	-Saisir le jour:");
		
		do {
			
			jour=in.nextInt();
			if(jour<0 || jour>7) {
				
				System.out.println("Erreur vueillez choisir un jour entre 1 et 7");
			}
		}while(jour<0 || jour>7);
		
		do {
			
			System.out.println("	-Saisir la durée du film (commencer par les heures puis les minutes):");
			
			heure=sc0.nextInt();
			minute=sc0.nextInt();
	
		}while(!Heure.horaireValide(heure,minute));
		Heure h=new Heure(heure,minute);
		
		
		System.out.println("Liste des salles:\n" +SallesCinema.toString());              
		System.out.println("Saisir le nom de la salle voulu:");
		
		String nom;
		Salle s=null;
		boolean estdedans;
		
		do{
			
			estdedans=false;
			nom=sc0.nextLine();
			Iterator<Salle> it= SallesCinema.iterator();
			
			while(it.hasNext()){
				
				Salle tmp=it.next();
				if(tmp.getNomSalle().equals(nom)){
					
					s=tmp;
					estdedans=true;
					
				}
			}
			
		}while(!estdedans);
		
		return (new SeanceCinema(jour,h,s));
	}

	/**Permet de créer une nouvelle pièce de théâtre
	 * de manière interactive
	 * 
	 * @param ProgrammationSemaine p
	 * @return PieceTheatre
	 */
	public static PieceTheatre creerPiece(ProgrammationSemaine p){
		
		System.out.println("Création d'une pièce de théâtre:");
		System.out.println("	-Saisir le titre de la pièce:");
		
		String titre=sc0.nextLine();
		
		System.out.println("	-Saisir l'interprète de la pièce:");          //pas suffisant
		
		String interprete=sc0.nextLine();
		
		System.out.println("	-Saisir le metteur en scène");
		
		String real=sc0.nextLine();
		
		System.out.println("	-Saisir le nombre d'entractes");
		
		int nbrE=in.nextInt();
		
		PieceTheatre r=new PieceTheatre(titre,interprete,real,nbrE);
		
		if(p.estProgramme(r)){
			System.out.println("le film existe déjà, saisissez en un nouveau ");
			return creerPiece(p);
		}
		return r;
	}
	
	/**Permet de créer une nouvelle séance de théâtre
	 * de manière interactive
	 * 
	 * @return SeanceTheatre
	 */
	public  static SeanceTheatre creerSTheatre(LinkedList<SalleTheatre> SallesTheatre){
		
		int heure=0,minute=0;
		
		System.out.println("Création d'une séance de theatre:");
		System.out.println("	-Saisir le jour:");
		
		int jour=in.nextInt();
		
		do {
			
			System.out.println("	-Saisir la durée du film (commencer par les heures puis les minutes):");
			
			heure=sc0.nextInt();
			minute=sc0.nextInt();
	
		}while(!Heure.horaireValide(heure,minute));
		Heure h=new Heure(heure,minute);
		
		System.out.println("Liste des salles de theatre:\n" +SallesTheatre.toString());              
		System.out.println("	-Saisir le nom de la salle voulu:");
		
		String nom;
		SalleTheatre s=null;
		boolean estdedans;
		
		do{
			
			estdedans=false;
			nom=sc0.nextLine();
			Iterator<SalleTheatre> it= SallesTheatre.iterator();
			
			while(it.hasNext()){
				
				SalleTheatre tmp=it.next();
				if(tmp.getNomSalle().equals(nom)){
					
					s=tmp;
					estdedans=true;
					
				}
			}
		}while(!estdedans);
		
		return (new SeanceTheatre(jour,h,s));
	}

	/**Permet de modifier une semaine
	 * de manière interactive
	 * 
	 * @param ProgrammationSemaine p
	 */
	public static void modifSemaine(ProgrammationSemaine p,LinkedList<Salle> SallesCinema, LinkedList<SalleTheatre> SallesTheatre){
		
		boolean estFilm=false, estdedans=false;
		Set<Film> setF=p.getFilmsSemaine();
		Set<PieceTheatre> setP=p.getPiecesSemaine();
		Film f = null;
		PieceTheatre t= null;
		
		System.out.println("Saisir le nom du spectacle à modifier");
		System.out.println("	-Films:" + setF.toString());
		System.out.println("	-Pièce de théâtre:" + setP.toString());
		
		String titre=sc0.nextLine();
		Iterator<Film> it= setF.iterator();
		Iterator<PieceTheatre> it1= setP.iterator();
		
		while(it.hasNext()){
			
			Film tmp=it.next();
			
			if(tmp.getTitre().equals(titre)){
				
				f=tmp;
				estFilm=true;
				estdedans=true;
				
			}
		}
		
		while(it1.hasNext()){
			
			if(it1!=null){
				
				PieceTheatre tmp1=it1.next();
				if(tmp1.getTitre().equals(titre)){
					t=tmp1;
					estdedans=true;
					
				}
			}
		}
		
		if(estdedans){
			
			System.out.println("Pour ajouter une séance tapez a, pour supprimer une séance tapez s, pour annuler tapez annuler");
			
			String a=sc0.nextLine();
			
			if(a.equals("a")){
				
				if(estFilm){
					
					p.ajouterFilms(f,creerSCine(SallesCinema));
					
				}else{
					
					p.ajouterPiece(t,creerSTheatre(SallesTheatre));
					
				}
			}
			
			if(a.equals("s")){
				
				if(estFilm){    
					
					SupprSeanceCine(p,f);
					
				}else{
					
					SupprSeanceTheatre(p,t);
					
				}
				
			}
		
			if(!a.equals("a") && !a.equals("s") && !a.equals("annuler")){
				
				System.out.println("Saisie invalide; veuillez saisir a, s ou annuler");  //finir
				
				modifSemaine(p, SallesCinema, SallesTheatre);
				
			}
		}else{
			
			System.out.println("Ce spectacle n'existe pas");   //finir
			
			modifSemaine(p, SallesCinema, SallesTheatre);
		}
			
	}
	
	/**Supprime une séance de cinéma d'un film donné dans une semaine donnée
	 * 
	 * @param p
	 * @param f
	 */
	public static void SupprSeanceCine(ProgrammationSemaine p,Film f){
		
		int heure=0,minute=0;
		Set<SeanceCinema> setSC=p.getToutesLesSeancesCinema(f);
		
		System.out.println("Liste des séances:\n" + setSC.toString());
		System.out.println("Quelle séance souhaitez vous supprimer ?");
		System.out.println("	-Saisir le jour:");
		
		int jour=in.nextInt();
		
		do {
			
			System.out.println("	-Saisir la durée du film (commencer par les heures puis les minutes):");
			
			heure=sc0.nextInt();
			minute=sc0.nextInt();
	
		}while(!Heure.horaireValide(heure,minute));
		
		Heure h=new Heure(heure,minute);
		
		Iterator<SeanceCinema> SC= setSC.iterator();
		
		while(SC.hasNext()){
			
			SeanceCinema tmp=SC.next();
			
			if(tmp.getJour()==jour && tmp.getHoraire().equals(h)){
				
				p.supprimerSeance(f, tmp);
				
			}
		}
	}

	/**Supprime une séance de theatre d'une piece donnée dans une semaine donnée
	 * 
	 * @param p
	 * @param t
	 */
	public static void SupprSeanceTheatre(ProgrammationSemaine p,PieceTheatre t){
		
		Set<SeanceTheatre> setST=p.getToutesLesSeancesTheatre(t);
		int heure=0,minute=0;
		
		System.out.println("Liste des séances:\n" + setST.toString());
		System.out.println("Quelle séance souhaitez vous supprimer ?");
		System.out.println("	-Saisir le jour:");
		
		int jour=in.nextInt();
		
		do {
			
			System.out.println("	-Saisir la durée du film (commencer par les heures puis les minutes):");
			
			heure=sc0.nextInt();
			minute=sc0.nextInt();
	
		}while(!Heure.horaireValide(heure,minute));
		
		Heure h=new Heure(heure,minute);
		
		Iterator<SeanceTheatre> ST= setST.iterator();
		
		while(ST.hasNext()){
			
			SeanceTheatre tmp=ST.next();
			
			if(tmp.getJour()==jour && tmp.getHoraire().equals(h)){
				
				p.supprimerSeanceTheatre(t, tmp);
				
				System.out.println("Liste des séances: " + setST.toString());
				
			}
		}
	}
	
	/**Permet de reserver une pace de cinema
	 * 
	 * @param sem
	 */
	public static void ReservePlaceCine(ProgrammationSemaine sem){ 
		
		Film f=null;
		boolean estdedans=false;
		Set<Film> setF=sem.getFilmsSemaine();
		
		System.out.println("Liste des films de la semaine:");
		System.out.println(setF.toString());
		System.out.println("Saisir le nom du Film pour lequel vous souhaitez réserver");
		
		String titre=sc0.nextLine();
		Iterator<Film> it= setF.iterator();
		
		while(it.hasNext()){
			
			Film tmp=it.next();
			
			if(tmp.getTitre().equals(titre)){
				
				f=tmp;
				estdedans=true;
				
			}
			
		}
		if(!estdedans){
			
			System.out.println("Ce film n'existe pas");
			
		}else{
			
			Set<SeanceCinema> setSC=sem.getToutesLesSeancesCinema(f);
			
			System.out.println("Liste des séances:\n" + setSC.toString());
			System.out.println("Pour quelle séance souhaitez vous réserver ?");
			System.out.println("	-Saisir le jour:");
			
			int jour=in.nextInt();
			int heure;
			int minute;
			
			do {
				System.out.println("	-Saisir la durée du film (commencer par les heures puis les minutes):");
				
				heure=in.nextInt();
				minute=in.nextInt();
		
			}while(!Heure.horaireValide(heure,minute));
			Heure h=new Heure(heure,minute);
			
			boolean reserve=false;
			Iterator<SeanceCinema> SC= setSC.iterator();
			
			while(SC.hasNext()){
				
				SeanceCinema tmp=SC.next();
				
				if(tmp.getJour()==jour && tmp.getHoraire().equals(h) && tmp.nbPlacesDispo()!=0){ 
					
					System.out.println("	-Nombre de places disponibles: " + tmp.nbPlacesDispo());     //afficher nombre de places dispos
					System.out.println("Êtes vous éligible aux places à tarif réduit ? (en étant chomeurs, étudiant ou membre d'une famille nombreuse)");
					System.out.println("Répondez par oui ou par non:");
					
					String tarifR;
					do{
						
						tarifR=sc0.nextLine(); 
						
						if(tarifR.equals("oui")){                                                                       //si TR
							
							ReserveTR(sem, tmp);
							
						}else if(tarifR.equals("non")){
							
							ReserveTN(sem, tmp);
							
						}else{
							
							System.out.println("Erreur; répondez par oui ou par non:");
							
						}					
					}while(!tarifR.equals("oui") && !tarifR.equals("non"));
					reserve=true;
				}
					
				if(!reserve){
					
					System.out.println("Cette séance n'existe pas ou est déjà complète"); 
					
				}	
			}
		}
	}
	
	/**Permet de reserver une place de theatre
	 * 
	 * @param sem
	 */
	public static void ReservePlaceTheatre(ProgrammationSemaine sem){    
		
		PieceTheatre t=null;
		boolean estdedans=false;
		Set<PieceTheatre> setT=sem.getPiecesSemaine();
		
		System.out.println("Liste des pièces de théâtre de la semaine:");
		System.out.println(setT.toString());
		System.out.println("Saisir le nom de la pièce pour lequel vous souhaitez réserver");
		
		String titre=sc0.nextLine();
		Iterator<PieceTheatre> it= setT.iterator();
		
		while(it.hasNext()){
			
			PieceTheatre tmp=it.next();
			
			if(tmp.getTitre().equals(titre)){
				
				t=tmp;
				estdedans=true;
				
			}
		}
		if(!estdedans){
			
			System.out.println("Cette pièce n'existe pas");
			
		}else{
			
			Set<SeanceTheatre> setST=sem.getToutesLesSeancesTheatre(t);
			
			System.out.println("Liste des séances: " + setST.toString());
			System.out.println("Pour quelle séance souhaitez vous réserver ?");
			System.out.println("	-Saisir le jour:");
			
			int jour=in.nextInt();
			int heure;
			int minute;
			do {
				
				System.out.println("	-Saisir la durée du film (commencer par les heures puis les minutes):");
				
				heure=in.nextInt();
				minute=in.nextInt();
		
			}while(!Heure.horaireValide(heure,minute));
			Heure h=new Heure(heure,minute);
			
			boolean reserve=false;
			Iterator<SeanceTheatre> ST= setST.iterator();
			
			while(ST.hasNext()){
				
				SeanceTheatre tmp=ST.next();
				
				if(tmp.getJour()==jour && tmp.getHoraire().equals(h) && (tmp.nbPlacesDispo()!=0 || tmp.nbFauteuilsDispo()!=0) ){ 
					
					System.out.println("Nombre de places aux balcons à tarif normal disponibles: " + tmp.nbPlacesDispo());    
					System.out.println("Nombre de fauteuils à tarif spécial disponibles: " + tmp.nbFauteuilsDispo());
					System.out.println("Souhaiter vous une places à tarifs normal sur les balcons ?");
					System.out.println("Répondez par oui ou par non:");
					
					String tarifN;
					
					do{
						tarifN=sc0.nextLine(); 
						
						if(tarifN.equals("oui")){                                                                       //si TR
							
							ReserveTN(sem, tmp);
							
						}else if(tarifN.equals("non")){
							
							ReserveF(sem, tmp);
							
						}else{
							
							System.out.println("Erreur; répondez par oui ou par non:");
							
						}					
						
					}while(!tarifN.equals("oui") && !tarifN.equals("non"));
					reserve=true;
				}
					
				if(!reserve){
					
					System.out.println("Cette séance n'existe pas ou est déjà complète");     
					
				}	
					
			}	
		}
	}
	
	/**reserve des places à une séance de cinéma
	 * 
	 * @param sem
	 * @param tmp
	 */
	public static void ReserveTR(ProgrammationSemaine sem, SeanceCinema tmp){                  //si TR
		int i;
		
		System.out.println("Combien de places à tarif réduit voulez-vous réserver ?");
		
		do{
			
			i=in.nextInt(); 
			
			if(i>=0 && i<=tmp.nbPlacesDispo()){
				
				tmp.vendrePlacesTR(i);
				
				System.out.println("Les places ont bien été réservées.");
				
			}else{
				
				System.out.println("Saisissez un nombre positif inférieur à "+tmp.nbPlacesDispo());
				
			}
			
		}while(i<=0 || i>=tmp.nbPlacesDispo());
	}
	
	/**reserve des places à une séance de cinéma OU de theatre
	 * 
	 * @param sem
	 * @param tmp
	 */
	public static void ReserveTN(ProgrammationSemaine sem, Seance tmp){                           //si TN
		
		int i;
		
		System.out.println("Combien de places à tarif normal voulez-vous réserver ?");
		
		do{                                                                                               
			i=in.nextInt(); 
			
			if(i>=0 && i<=tmp.nbPlacesDispo()){
				
				tmp.vendrePlacesTN(i);
				
				System.out.println("Les places ont bien été réservées.");
				
			}else{
				
				System.out.println("Saisissez un nombre positif inférieur à "+tmp.nbPlacesDispo());
				
			}
			
		}while(i<=0 || i>=tmp.nbPlacesDispo());
	}
	
	/**reserve des places de fauteuils à une seance de theatre
	 * 
	 * @param sem
	 * @param tmp
	 */
	public static void ReserveF(ProgrammationSemaine sem, SeanceTheatre tmp){                   //si Fauteuils
		
		int i;
		
		System.out.println("Combien de fauteuils à tarif spécial voulez-vous réserver ?");
		
		do{                                                                                            
			
			i=in.nextInt(); 
			
			if(i>=0 && i<=tmp.nbFauteuilsDispo()){
				
				tmp.vendrePlacesFauteuil(i);
				
				System.out.println("Les fauteuils ont bien été réservées.");
				
			}else{
				
				System.out.println("Saisissez un nombre positif inférieur à "+tmp.nbPlacesDispo());
				
			}
			
		}while(i<=0 || i>=tmp.nbPlacesDispo());
	}

	/**Consulte une seance de cinema ou de theatre 
	 * 
	 * @param s
	 * @param sem
	 */
	public static void Consulter(Seance s, ProgrammationSemaine sem){
		
		String cons;
		
		do{
			
			System.out.println("Que voulez-vous consulter ?");
			System.out.println("	-Pour le chiffre d'affaire tapez c");
			System.out.println("	-Pour le taux de remplissage tapez t");
			
			cons=sc0.nextLine();
			
			if(cons.equals("c")){
				
				System.out.println("Le chiffre d'affaire de cette séance est: " + sem.getChiffredAffaire(s)+" euros");
				
			}else{
				
				if(cons.equals("t")){
					
					System.out.println("Le taux de remplissage de cette séance est: " + s.tauxRemplissage() +"%");     //TODO transforme en 0.0 pour aucune raison
					
				}
			}
			
		}while(!cons.equals("c") && !cons.equals("t"));
	}
	
	/**Charge les salles de cinéma du fichier
	 * 
	 * @author jordan
	 * @param String nomFichier
	 * @return LinkedList<Salle> Salles de cinéma
	 */
 	public static LinkedList<Salle> ChargerSalleCinema(String nomFichier) {
 		
		LinkedList<Salle> SallesCinema = new LinkedList<Salle>();
		
		try {
			
			FileReader fr= new FileReader(nomFichier);
			BufferedReader br= new BufferedReader(fr);
			String s="";
			
			try {
				
				br.readLine();
				
				while((s= br.readLine())!=null) {
					
					String [] ligne=s.split(";");
					String NomSalle=ligne[0];
					int capacite=Integer.parseInt(ligne[1]);
					double tarif=Double.parseDouble(ligne[2].replace(',', '.'));
					SallesCinema.add(new Salle(NomSalle,capacite,tarif));
					
				}
				br.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return SallesCinema;
	}
	
	/**Charge les salles de théâtre du fichier
	 * 
	 * @author jordan
	 * @param String nomFichier
	 * @return LinkedList<SalleTheatre> Salles de théâtre
	 */
	public static LinkedList<SalleTheatre> ChargerSalleTheatre(String nomFichier) {
		
		LinkedList<SalleTheatre> SallesTheatre = new LinkedList<SalleTheatre>();
		
		try {
			
			FileReader fr= new FileReader(nomFichier);
			BufferedReader br= new BufferedReader(fr);
			String s="";
			
			try {
				
				br.readLine();
				
				while((s= br.readLine())!=null) {
					
					String [] ligne=s.split(";");
					String NomSalle=ligne[0];
					int capacite=Integer.parseInt(ligne[1]);
					double tarif=Double.parseDouble(ligne[2].replace(',', '.'));
					int nbFauteuils=Integer.parseInt(ligne[3]);
					double tarifFauteuil=Double.parseDouble(ligne[4].replace(',', '.'));
					SallesTheatre.add(new SalleTheatre(NomSalle,capacite,tarif,nbFauteuils,tarifFauteuil));
					
				}
				br.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return SallesTheatre;
	}
}

