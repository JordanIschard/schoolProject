package ProjetSpectacle;


public class PieceTheatre extends Spectacle {
	private String metteurEnScene;
	private int nbrEntracte;
	
	/**Constructeur
	 * 
	 * @param titre
	 * @param interpretes
	 * @param metteurEnScene
	 * @param nbrEntracte
	 */
	public PieceTheatre(String titre, String interpretes,
			String metteurEnScene, int nbrEntracte) {
		super(titre, interpretes);
		this.metteurEnScene = metteurEnScene;
		this.nbrEntracte = nbrEntracte;
	}
	
	/**Donne le nom du metteur en scène
	 * 
	 * @return String nom du metteur en scène
	 */
	public String getMetteurEnScene() {
		return metteurEnScene;
	}
	
	/**Change le nom du metteur en scène 
	 * 
	 * @param metteurEnScene
	 */
	public void setMetteurEnScene(String metteurEnScene) {
		this.metteurEnScene = metteurEnScene;
	}
	
	/**Donne le nombre d'entracte
	 * 
	 * @return int nombre d'entracte
	 */
	public int getNbrEntracte() {
		return nbrEntracte;
	}
	
	/**Change le nombre d'entracte
	 * 
	 * @param nbrEntracte
	 */
	public void setNbrEntracte(int nbrEntracte) {
		this.nbrEntracte = nbrEntracte;
	}
	
	@Override
	/**Donne l'affichage d'une séance de cinéma
	 * 
	 * @return String affichage de la séance 
	 */
	public String toString() {
		return "*Pièce de théâtre:\n"+super.toString()+"\n 	-metteurEnScene=" + metteurEnScene
				+ "\n	-nbrEntracte=" + nbrEntracte + "]";
	}
	
	@Override
	/**Vérifie l'égalité de deux pièces de
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
		PieceTheatre other = (PieceTheatre) obj;
		if (metteurEnScene == null) {
			if (other.metteurEnScene != null)
				return false;
		} else if (!metteurEnScene.equals(other.metteurEnScene))
			return false;
		if (nbrEntracte != other.nbrEntracte)
			return false;
		return true;
	}
	
	
}
