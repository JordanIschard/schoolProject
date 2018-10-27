package ProjetSpectacle;

public class SalleTheatre extends Salle {

	private int nbrFauteuils;
	private double tarifFauteuil;
	
	/**Constructeur
	 * 
	 * @param nomSalle
	 * @param capacite
	 * @param tarif
	 * @param nbrFauteuils
	 * @param tarifFauteuil
	 */
	public SalleTheatre(String nomSalle, int capacite, double tarif,
			int nbrFauteuils, double tarifFauteuil) {
		super(nomSalle, capacite+nbrFauteuils, tarif);
		this.nbrFauteuils = nbrFauteuils;
		this.tarifFauteuil = tarifFauteuil;
	}
	
	/**Donne le nombre de fauteuils
	 * 
	 * @return int nombre de fauteuils
	 */
	public int getNbrFauteuils() {
		return nbrFauteuils;
	}

	/**Change le nombre de fauteuils
	 * 
	 * @param nbrFauteuils
	 */
	public void setNbrFauteuils(int nbrFauteuils) {
		this.nbrFauteuils = nbrFauteuils;
	}

	/**Donne le tarif des fauteuils
	 * 
	 * @return double tarif
	 */
	public double getTarifFauteuil() {
		return tarifFauteuil;
	}

	/**Change le tarif des fauteuils
	 * 
	 * @param tarifFauteuil
	 */
	public void setTarifFauteuil(double tarifFauteuil) {
		this.tarifFauteuil = tarifFauteuil;
	}

	@Override
	/**Vérifie l'égalité de deux salles de
	 * théâtre
	 * 
	 * @return boolean égalité
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalleTheatre other = (SalleTheatre) obj;
		if (nbrFauteuils != other.nbrFauteuils)
			return false;
		if (Double.doubleToLongBits(tarifFauteuil) != Double
				.doubleToLongBits(other.tarifFauteuil))
			return false;
		return true;
	}

	@Override
	/**Donne l'affichage d'une salle de
	 * théâtre
	 * 
	 * @return String affichage de la salle de
	 * théâtre 
	 */
	public String toString() {
		return super.toString()+"       nbrFauteuils: " + nbrFauteuils
				+ " tarifFauteuil: " + tarifFauteuil + ";\n";
	}
	
	

	
	
}
