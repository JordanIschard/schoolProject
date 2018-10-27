package ProjetSpectacle;

public abstract class Spectacle {

	private String titre;
	private String interpretes;
	
	/**Constructeur
	 * 
	 * @param titre
	 * @param interpretes
	 */
	public Spectacle(String titre, String interpretes) {
		this.titre = titre;
		this.interpretes = interpretes;
	}
	
	/**Donne le titre du spectacle
	 * 
	 * @return String titre
	 */
	public String getTitre() {
		return titre;
	}
	
	/**Change le titre du film
	 * 
	 * @param titre
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	/**Donne le nom des interprètes
	 * 
	 * @return String nom des interprètes
	 */
	public String getInterpretes() {
		return interpretes;
	}
	
	/**Change le nom des interprètes
	 * 
	 * @param interpretes
	 */
	public void setInterpretes(String interpretes) {
		this.interpretes = interpretes;
	}
	
	/**Donne l'affichage d'un spectacle
	 * 
	 * @return String affichage du spectacle
	 */
	public String toString(){
		return titre+"\n	-Interprètes: "+interpretes;
	}
	
	@Override
	/**Vérifie l'égalité de deux spectacles
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
		Spectacle other = (Spectacle) obj;
		if (interpretes == null) {
			if (other.interpretes != null)
				return false;
		} else if (!interpretes.equals(other.interpretes))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		return true;
	}
	
}
