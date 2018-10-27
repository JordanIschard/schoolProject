package ProjetSpectacle;
import java.util.*;

public class ProgrammationSemaine {
	private int semaine;
	private Map<Film,TreeSet<SeanceCinema>> LesFilms;
	private Map<PieceTheatre,TreeSet<SeanceTheatre>> LesPieces;
	
	/**Constructeur
	 * 
	 * @author jordan
	 * @param semaine
	 */
	public ProgrammationSemaine(int semaine) {
		this.semaine = semaine;
		LesFilms= new HashMap<Film,TreeSet<SeanceCinema>>();
		LesPieces= new HashMap<PieceTheatre,TreeSet<SeanceTheatre>>();
	}
	
	/**Donne le taux de remplissage d'une séance
	 * 
	 * @author jordan
	 * @param o
	 * @return double Taux de Remplissage
	 */
	public double getTauxRemplissage(Object o){
		if(o instanceof SeanceCinema){
			return((SeanceCinema)o).tauxRemplissage();
		}else{
			if(o instanceof SeanceTheatre){
				return((SeanceTheatre)o).tauxRemplissage();
			}else{ return -1;}	
		}
	}
	
	/**Donne le chiffre d'affaire d'une séance
	 * et retourne -1 si ce n'est ni une Séance de cinéma
	 * ni une séance  de théâtre
	 * 
	 * Attention cette méthode ne vérifie pas si l'objet existe!
	 * 
	 * @author jordan
	 * @param Object o
	 * @return double Chiffre d'Affaire
	 */
	public double getChiffredAffaire(Object o){
		double tarif;
		int placesTN;
		if(o instanceof SeanceCinema){
			SeanceCinema tmp=(SeanceCinema)o;
			tarif=tmp.getSalle().getTarif();
			placesTN=tmp.getNbPlacesVenduesTN();
			int placesTR=tmp.getNbPlacesVenduesTR();
			
			return (placesTN*tarif)+(placesTR*(tarif*(60/100)));
		}else{
			if(o instanceof SeanceTheatre){
				SeanceTheatre tmp=(SeanceTheatre)o;
				double tarifFauteuil=tmp.getSalleTheatre().getTarifFauteuil();
				double placesFauteuil=tmp.getNbFauteuilsVendus();
				tarif=tmp.getSalleTheatre().getTarif();
				placesTN=tmp.getNbPlacesVenduesTN();
				
				return (tarifFauteuil*placesFauteuil)+(tarif*placesTN);
			}else{return -1;}
		}
	}
	
	/**Donne la semaine
	 * 
	 * @author jordan
	 * @return int la semaine
	 */
	public int getSemaine() {
		return semaine;
	}

	/**Change la semaine
	 * 
	 * @author jordan
	 * @param int semaine
	 */
	public void setSemaine(int semaine) {
		this.semaine = semaine;
	}
	
	
	/*************************************Methodes pour les Films***********************************/
	
	/**Ajoute un film et une séance de cinéma ou
	 * seulement la séance si le film existe déjà
	 * 
	 * @author jordan
	 * @param Film f
	 * @param SeanceCinema s
	 */
	public void ajouterFilms(Film f,SeanceCinema s){
		if(estProgramme(f)){
			LesFilms.get(f).add(s);
			System.out.println(LesFilms.get(f));
		}else{
			System.out.println(f.toString());
			TreeSet<SeanceCinema> tmp=new TreeSet<SeanceCinema>();
			tmp.add(s);
			LesFilms.put(f,tmp);
			System.out.println(LesFilms.get(f));

		}
	}
	
	/**Ajoute un film et une liste de séance de cinéma ou
	 * seulement la liste de séance si le film existe déjà
	 * 
	 * @author jordan
	 * @param Film f
	 * @param  TreeSet<SeanceCinema> l
	 */
	public void ajouterFilms(Film f,TreeSet<SeanceCinema> l){
		if(estProgramme(f)){
			LesFilms.get(f).addAll(l);
		}else{
			LesFilms.put(f, l);
		}
	}
	
	/**Donne toutes les séances de cinéma  d'un film donné et 
	 * lève une exception si le film n'existe pas
	 * 
	 * @author jordan
	 * @param Film f
	 * @return TreeSet<SeanceCinema> Les séances de 'f'
	 * @throws IllegalArgumentException
	 */
	public SortedSet<SeanceCinema> getToutesLesSeancesCinema(Film f) throws IllegalArgumentException{
		if(estProgramme(f)){
			return LesFilms.get(f);
		}else{ throw new IllegalArgumentException("Le Film choisit est introuvable.");}
	}
	
	/**Donne les séances de cinéma d'un film pour un jour donné et 
	 * lève une exception si le film n'existe pas ou le jour est erroné
	 * 
	 * @author jordan
	 * @param Film f
	 * @param int jour
	 * @return TreeSet<SeanceCinema> les séances de 'f' pour le jour 'jour'
	 * @throws IllegalArgumentException
	 */
	public TreeSet<SeanceCinema> getSeancesCinema(Film f,int jour) throws IllegalArgumentException{
		if(estProgramme(f) && jour>0 && jour<8){
			TreeSet<SeanceCinema> l= new TreeSet<SeanceCinema>();
			Iterator<SeanceCinema> it=LesFilms.get(f).iterator();
			while(it.hasNext()){
				SeanceCinema c=it.next();
				if(c.getJour()==jour){
					l.add(c);
				}
			}
			return l;
		}else{ throw new IllegalArgumentException("Le Film choisit est introuvable ou le jour est erroné");}
	}
	
	/**Supprime le film donné
	 * 
	 * @author jordan
	 * @param Film f
	 */
	public void SupprimerFilm(Film f){
		if(estProgramme(f)){
			LesFilms.remove(f);
		}else{ System.out.println("Ce film a déjà été supprimé");}
	}
	
	/**Donne la liste des films de la semaine
	 * 
	 * @author jordan
	 * @return Set<Film> les films de la semaine
	 */
	public Set<Film> getFilmsSemaine(){
		return LesFilms.keySet();
	}
	
	
	/**Donne le nombre de film de la semaine
	 * 
	 * @author jordan
	 * @return int le nombre de films
	 */
	public int nbrdeFilms(){
		return LesFilms.size();
	}
	
	/**Vérifie si le film est contenu dans la programmation
	 * de la semaine
	 * 
	 * @author jordan
	 * @param Film f
	 * @return boolean est programmé
	 */
	public boolean estProgramme(Film f){
		return LesFilms.containsKey(f);
	}
	
	/**Supprime une séance d'un film donné et 
	 * lève une exception si le film ou la séance n'existe pas
	 * 
	 * @author jordan
	 * @param Film f
	 * @param SeanceCinema c
	 * @throws IllegalArgumentException
	 */
	public void supprimerSeance(Film f,SeanceCinema c)throws IllegalArgumentException{
		if(estProgramme(f) && LesFilms.get(f).contains(c)){
			LesFilms.get(f).remove(c);
		}else{ throw new IllegalArgumentException("Le Film ou la séance choisit est introuvable.");}
	}
	
	/**Donne la séance demandée par rapport au film, au jour
	 * et à l'heure
	 * 
	 * @author jordan
	 * @param Film film
	 * @param Heure heure
	 * @param int jour
	 * @return SeanceCinema la séance demandée
	 * @throws IllegalArgumentException
	 */
	public SeanceCinema consulterSeanceCinema(Film film,Heure heure,int jour)throws IllegalArgumentException{
		if(estProgramme(film)){
			Iterator<SeanceCinema> it=LesFilms.get(film).iterator();
			SeanceCinema res=null;
			while(it.hasNext()){
				SeanceCinema tmp=it.next();
				if(tmp.getJour()==jour && tmp.getHoraire().equals(heure)){
					res=tmp;
					break;
				}
			}
			return res;
		}else{ throw new IllegalArgumentException("Le Film choisit est introuvable");}
	}

	/***************************Méthodes pour les Théâtres****************************************/

	/**Ajoute une pièce de théâtre et une séance de théâtre ou
	 * seulement la séance si la pièce de théâtre existe déjà
	 * 
	 * @author jordan
	 * @param PieceTheatre t
	 * @param SeanceTheatre s
	 */
	public void ajouterPiece(PieceTheatre t,SeanceTheatre s){
		if(estProgramme(t)){
			LesPieces.get(t).add(s);
		}else{
			LesPieces.put(t,null);
			LesPieces.get(t).add(s);
		}
	}
	
	/**Ajoute une pièce de théâtre et une liste de séance ou
	 * seulement la liste de séance si la pièce existe déjà
	 * 
	 * @author jordan
	 * @param PieceTheatre t
	 * @param  TreeSet<SeanceTheatre> l
	 */
	public void ajouterPiece(PieceTheatre t,TreeSet<SeanceTheatre> l){
		if(estProgramme(t)){
			LesPieces.get(t).addAll(l);
		}else{
			LesPieces.put(t, l);
		}
	}
	
	/**Donne toutes les séances d'une pièce de théâtre donnée et 
	 * lève une exception si la pièce n'existe pas
	 * 
	 * @author jordan
	 * @param PieceTheatre t
	 * @return TreeSet<SeanceTheatre> Les séance de 't'
	 * @throws IllegalArgumentException
	 */
	public TreeSet<SeanceTheatre> getToutesLesSeancesTheatre(PieceTheatre t) throws IllegalArgumentException{
		if(estProgramme(t)){
			return LesPieces.get(t);
		}else{ throw new IllegalArgumentException("La Pièce de théâtre choisit est introuvable.");}
	}
	
	/**Donne les séances d'une pièce pour un jour donné et 
	 * lève une exception si la pièce n'existe pas ou le jour est erroné
	 * 
	 * @author jordan
	 * @param PieceTheatre t
	 * @param int jour
	 * @return TreeSet<SeanceTheatre> les séances de 't' pour le jour 'jour'
	 * @throws IllegalArgumentException
	 */
	public TreeSet<SeanceTheatre> getSeancesTheatre(PieceTheatre t,int jour) throws IllegalArgumentException{
		if(estProgramme(t) && jour>0 && jour<8){
			TreeSet<SeanceTheatre> l= new TreeSet<SeanceTheatre>();
			Iterator<SeanceTheatre> it=LesPieces.get(t).iterator();
			while(it.hasNext()){
				SeanceTheatre c=it.next();
				if(c.getJour()==jour){
					l.add(c);
				}
			}
			return l;
		}else{ throw new IllegalArgumentException("La Pièce choisit est introuvable ou le jour est erroné");}
	}
	
	/**Supprime la pièce donnée
	 * 
	 * @author jordan
	 * @param PieceTheatre t
	 */
	public void SupprimerPieceTheatre(PieceTheatre t){
		if(estProgramme(t)){
			LesPieces.remove(t);
		}else{ System.out.println("Cette pièce a déjà été supprimé");}
	}
	
	/**Donne la liste des pièces de la semaine
	 * 
	 * @author jordan
	 * @return Set<PieceTheatre> les pièces de la semaine
	 */
	public Set<PieceTheatre> getPiecesSemaine(){
		return LesPieces.keySet();
	}
	
	
	/**Donne le nombre de pièces de la semaine
	 * 
	 * @author jordan
	 * @return int le nombre de pièces
	 */
	public int nbrdePieces(){
		return LesPieces.size();
	}
	
	/**Vérifie si la pièce est contenu dans la programmation
	 * de la semaine
	 * 
	 * @author jordan
	 * @param PieceTheatre t
	 * @return boolean est programmé
	 */
	public boolean estProgramme(PieceTheatre t){
		return LesPieces.containsKey(t);
	}
	
	/**Supprime une séance d'une séance donnée et 
	 * lève une exception si la pièce ou la séance n'existe pas
	 * 
	 * @author jordan
	 * @param PieceTheatre t
	 * @param SeanceTheatre p
	 * @throws IllegalArgumentException
	 */
	public void supprimerSeanceTheatre(PieceTheatre t,SeanceTheatre p)throws IllegalArgumentException{
		if(estProgramme(t) && LesPieces.get(t).contains(p)){
			LesPieces.get(t).remove(p);
		}else{ throw new IllegalArgumentException("La pièce ou la séance choisit est introuvable.");}
	}
	
	/**Donne la séance demandée par rapport à la pièce, au jour
	 * et à l'heure
	 * 
	 * @author jordan
	 * @param PieceTheatre t
	 * @param Heure heure
	 * @param int jour
	 * @return SeanceTheatre la séance demandée
	 * @throws IllegalArgumentException
	 */
	public SeanceTheatre consulterSeanceTheatre(PieceTheatre t,Heure heure,int jour)throws IllegalArgumentException{
		if(estProgramme(t)){
			Iterator<SeanceTheatre> it=LesPieces.get(t).iterator();
			SeanceTheatre res=null;
			while(it.hasNext()){
				SeanceTheatre tmp=it.next();
				if(tmp.getJour()==jour && tmp.getHoraire().equals(heure)){
					res=tmp;
					break;
				}
			}
			return res;
		}else{ throw new IllegalArgumentException("La pièce choisit est introuvable");}
	}
}
