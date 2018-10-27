package ProjetSpectacle;

public class Film extends Spectacle {
	
	private String realisateur;
	private Heure duree;

	/**Constructeur
	 * 
	 * @param titre
	 * @param interpretes
	 * @param realisateur
	 * @param duree
	 */
	public Film(String titre, String interpretes, String realisateur,
			Heure duree) {
		super(titre, interpretes);
		this.realisateur = realisateur;
		this.duree = duree;
	}
	
	/**Donne le nom du réalisateur
	 * 
	 * @return String nom du réal
	 */
	public String getRealisateur() {
		return realisateur;
	}
	
	/**Change le nom du réalisateur
	 * 
	 * @param realisateur
	 */
	public void setRealisateur(String realisateur) {
		this.realisateur = realisateur;
	}
	
	/**Donne la durée du film
	 * 
	 * @return Heure durée du film
	 */
	public Heure getDuree() {
		return duree;
	}
	
	/**Change la durée du film
	 * 
	 * @param duree
	 */
	public void setDuree(Heure duree) {
		this.duree = duree;
	}
	
	/**Donne l'affichage d'un film
	 * 
	 * @return String affichage du film 
	 */
	public String toString(){
		return "*Film:\n"+super.toString()+"\n		-Réalisateur: "+realisateur+"\n		-Durée: "+duree;
	}

	@Override
	/**Vérifie l'égalité de deux films
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
		Film other = (Film) obj;
		if (duree == null) {
			if (other.duree != null)
				return false;
		} else if (!duree.equals(other.duree))
			return false;
		if (realisateur == null) {
			if (other.realisateur != null)
				return false;
		} else if (!realisateur.equals(other.realisateur))
			return false;
		return true;
	}
	
	
	
}
