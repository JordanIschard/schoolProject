package ProjetSpectacle;

public class SeanceCinema extends Seance {
	
	private Salle salle;
	private int nbPlacesVenduesTR;

	/**Constructeur
	 * 
	 * @param jour
	 * @param horaire
	 * @param salle
	 */
	public SeanceCinema(int jour, Heure horaire, Salle salle) {
		super(jour, horaire);
		this.nbPlacesVenduesTR=0;
		this.salle = salle;
	}
	
	/**Change le nombre de places vendus à
	 * TR
	 * 
	 * @param nbre
	 */
	public void vendrePlacesTR(int nbre){
		this.nbPlacesVenduesTR+=nbre;
	}

	@Override
	/**Donne le nombre de places disponibles
	 * 
	 * @return int nombre de places dispo
	 */
	public int nbPlacesDispo() {
		return salle.getCapacite()-(this.getNbPlacesVenduesTN()+this.nbPlacesVenduesTR);
	}

	@Override
	/**Donne le total vendus 
	 * 
	 * @return int total vendus
	 */
	public int totalVendu() {
		return this.getNbPlacesVenduesTN()+this.nbPlacesVenduesTR;
	}

	@Override
	/**Donne le taux de remplissage
	 * d'une séance de cinéma
	 * 
	 * @return double taux de remplissage
	 */
	public double tauxRemplissage() {
		return (totalVendu()*100)/salle.getCapacite();
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
		SeanceCinema other = (SeanceCinema) obj;
		if (nbPlacesVenduesTR != other.nbPlacesVenduesTR)
			return false;
		if (salle == null) {
			if (other.salle != null)
				return false;
		} else if (!salle.equals(other.salle))
			return false;
		return true;
	}

	/**Donne la salle de la séance de 
	 * cinéma
	 * 
	 * @return Salle  salle de la séance courante 
	 */
	public Salle getSalle() {
		return salle;
	}
	
	/**Change la salle de la séance de 
	 * cinéma
	 * 
	 * @param salle
	 */
	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	/**Donne le nombre de places TR vendus
	 * 
	 * @return int nombre de places TR vendus
	 */
	public int getNbPlacesVenduesTR() {
		return nbPlacesVenduesTR;
	}
	
	/**Change le nombre de places TR vendus 
	 * 
	 * @param nbPlacesVenduesTR
	 */
	public void setNbPlacesVenduesTR(int nbPlacesVenduesTR) {
		this.nbPlacesVenduesTR = nbPlacesVenduesTR;
	}

	@Override
	/**Donne l'affichage d'une séance de cinéma
	 * 
	 * @return String affichage de la séance 
	 */
	public String toString() {
		return super.toString()+"\n 	-" + salle + "-Place(s) à tarif réduit vendue(s):"
				+ nbPlacesVenduesTR;
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
