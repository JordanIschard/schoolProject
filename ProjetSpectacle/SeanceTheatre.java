package ProjetSpectacle;

public class SeanceTheatre extends Seance {
	
	SalleTheatre salleTheatre;
	int nbFauteuilsVendus;
	
	
	/**Constructeur
	 * 
	 * @param jour
	 * @param horaire
	 * @param salleTheatre
	 */
	public SeanceTheatre(int jour, Heure horaire, SalleTheatre salleTheatre) {
		super(jour, horaire);
		this.salleTheatre = salleTheatre;
		this.nbFauteuilsVendus =0;
	}

	/**Donne la nombre de place disponible
	 * 
	 * @return int nombre de place dispo
	 */
	public int nbPlacesDispo() {
		return salleTheatre.getCapacite()-(this.getNbPlacesVenduesTN()+this.nbFauteuilsVendus);
	}
	
	/**Donne le nombre de fauteuil disponible
	 * 
	 * @return int nombre de fauteuil dispo
	 */
	public int nbFauteuilsDispo() {
		return salleTheatre.getNbrFauteuils()-nbFauteuilsVendus;
	}
	
	/**Vends une place de type fauteuils
	 * 
	 * @param int nbre
	 */
	public void vendrePlacesFauteuil(int nbre){
		nbFauteuilsVendus+=nbre;                                // on ajoute nbre au nombre total de placesTR vendues
	}

	@Override
	/**Donne le total vendu
	 * 
	 * @return int Total vendu
	 */
	public int totalVendu() {
		return this.getNbPlacesVenduesTN()+this.nbFauteuilsVendus;
	}

	@Override
	/**Donne le taux de remplissage d'une salle de théâtre
	 * pour une séance donnée
	 * 
	 * @return double taux de remplissage
	 */
	public double tauxRemplissage() {
		return (totalVendu()*100)/salleTheatre.getCapacite();
	}

	@Override
	/**Vérifie l'égalité de deux séances
	 * 
	 * @return boolean égalité
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeanceTheatre other = (SeanceTheatre) obj;
		if (nbFauteuilsVendus != other.nbFauteuilsVendus)
			return false;
		if (salleTheatre == null) {
			if (other.salleTheatre != null)
				return false;
		} else if (!salleTheatre.equals(other.salleTheatre))
			return false;
		return true;
	}

	/**Donne la salle de théâtre liée à la séance
	 * 
	 * @return SalleTheatre la salle de la séance courante
	 */
	public SalleTheatre getSalleTheatre() {
		return salleTheatre;
	}
	
	/**Change la salle de théâtre liée à la séance
	 * 
	 * @param salleTheatre nouvelle salle de la séance courante
	 */
	public void setSalleTheatre(SalleTheatre salleTheatre) {
		this.salleTheatre = salleTheatre;
	}

	/**Donne le nombre de Fauteuils vendus
	 * 
	 * @return int fauteuils vendus
	 */
	public int getNbFauteuilsVendus() {
		return nbFauteuilsVendus;
	}

	/**Change le nombre de fauteuils vendus
	 * 
	 * @param nbFauteuilsVendus nouveau nombre de fauteuils vendus
	 */
	public void setNbFauteuilsVendus(int nbFauteuilsVendus) {
		this.nbFauteuilsVendus = nbFauteuilsVendus;
	}

	@Override
	/**Donne l'affichage d'une séance de théâtre
	 * 
	 * @return String affichage de la séance 
	 */
	public String toString() {
		return super.toString()+"\n -" + salleTheatre
				+ "-nbFauteuilsVendus=" + nbFauteuilsVendus;
	}
	
	/**Compare deux séances entre elle par rapport
	 * aux jours et aux heures
	 * 
	 * @return int etat de la comparaison 
	 * >0 si this>arg0
	 * <0 si this<arg0
	 * =0 si this=arg0
	 */
	public int compareTo(Seance arg0) {
		if(this.getJour()-arg0.getJour()==0) {
			return this.getHoraire().compareTo(arg0.getHoraire());
		}else{
			return this.getJour()-arg0.getJour();
		}
	}
	
	
}
