package ProjetSpectacle;

public abstract class Seance implements Comparable<Seance>{
	private int jour;
	private Heure horaire;
	private int nbPlacesVenduesTN;
	
	/**Constructeur
	 * 
	 * @param jour
	 * @param horaire
	 */
	public Seance(int jour, Heure horaire) {
		this.jour = jour;
		this.horaire = horaire;
		this.nbPlacesVenduesTN =0;
	}
	
	/**Donne le jour de la séance
	 * 
	 * @return int jour 
	 */
	public int getJour() {
		return jour;
	}

	/**Change le jour de la séance 
	 * 
	 * @param jour
	 */
	public void setJour(int jour) {
		this.jour = jour;
	}


	/**Donne l'horaire de la séance
	 * 
	 * @return Heure horaire
	 */
	public Heure getHoraire() {
		return horaire;
	}


	/**Change l'horaire de la séance
	 * 
	 * @param horaire
	 */
	public void setHoraire(Heure horaire) {
		this.horaire = horaire;
	}


	/**Donne le nombre de places vendus à
	 * TN de la séance
	 * 
	 * @return int nombre de place vendus à TN
	 */
	public int getNbPlacesVenduesTN() {
		return nbPlacesVenduesTN;
	}

	/**Change le nombre de places vendus à 
	 * TN de la séance
	 * 
	 * @param nbre
	 */
	public void vendrePlacesTN(int nbre){
		this.nbPlacesVenduesTN+=nbre;
	}

	@Override
	/**Donne l'affichage d'une séance
	 * 
	 * @return String Affichage d'une séance
	 */
	public String toString() {
		return "\n-Date de la séance:"+horaire+"\n	-Jour:" + jour
				+ "\n 	-nbPlacesVenduesTN:" + nbPlacesVenduesTN;
	}


	
	public abstract int nbPlacesDispo();
	public abstract int totalVendu();
	public abstract double tauxRemplissage();
	
	

}
