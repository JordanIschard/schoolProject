package ProjetSpectacle;

public class Salle {
	
	private String nomSalle;
	private int capacite;
	private double tarif;
	
	
	/**Constructeur
	 * 
	 * @param nomSalle
	 * @param capacite
	 * @param tarif
	 */
	public Salle(String nomSalle, int capacite, double tarif) {
		this.nomSalle = nomSalle;
		this.capacite = capacite;
		this.tarif = tarif;
	}
	
	/**Donne le nom de la salle
	 * 
	 * @return String nom de la salle
	 */
	public String getNomSalle() {
		return nomSalle;
	}
	
	/**Change le nom de la salle
	 * 
	 * @param nomSalle
	 */
	public void setNomSalle(String nomSalle) {
		this.nomSalle = nomSalle;
	}
	
	/**Donne la capacité de la salle
	 * 
	 * @return int capacité
	 */
	public int getCapacite() {
		return capacite;
	}
	
	/**Change la capacité de la salle 
	 * 
	 * @param capacite
	 */
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	
	/**Donne le tarif de la salle
	 * 
	 * @return double tarif
	 */
	public double getTarif() {
		return tarif;
	}
	
	/**Change le tarif de la salle
	 * 
	 * @param tarif
	 */
	public void setTarif(double tarif) {
		this.tarif = tarif;
	}

	@Override
	/**Donne l'affichage d'une salle
	 * 
	 * @return String affichage de la salle 
	 */
	public String toString() {
		return "Nom: " + nomSalle + " capacite: " + capacite
				+ " tarif: " + tarif + ";\n";
	}

	@Override
	/**Vérifie l'égalité de deux salles
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
		Salle other = (Salle) obj;
		if (capacite != other.capacite)
			return false;
		if (nomSalle == null) {
			if (other.nomSalle != null)
				return false;
		} else if (!nomSalle.equals(other.nomSalle))
			return false;
		if (Double.doubleToLongBits(tarif) != Double
				.doubleToLongBits(other.tarif))
			return false;
		return true;
	}
	
	
	
}
