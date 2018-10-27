package ProjetSpectacle;


public class Heure implements Comparable<Heure>{
	private int heures;
	private int minutes;
	
	/**Constructeur
	 * 
	 * @param heures
	 * @param minutes
	 * @throws IllegalStateException
	 */
	public Heure(int heures, int minutes) throws IllegalStateException{
		if(heures<24 && heures>=0 && minutes<60 && minutes>=0){
			this.heures= heures;
			this.minutes = minutes;
		}else{
			throw new IllegalStateException ("Saisie invalide");
		}
	}

	/**Donne l'horaire en minutes
	 * 
	 * @return int horaire
	 */
	public int getHeures_Minutes() {
		return heures*60+minutes;
	}

	/**Verifie si l'heure et les minutes
	 * donnés sont valide
	 * 
	 * @param heure
	 * @param minute
	 * @return boolean valide
	 */
	public static boolean horaireValide(int heure,int minute) {
		if(heure<0 || heure>23) {
			System.out.println("Erreur le nombre d'heure doit être comprise entre 0 et 23");
			return false;
		}
		if(minute<0 || minute>59) {
			System.out.println("Erreur le nombre de minute doit être comprise entre 0 et 59");
			return false;
		}
		return true;
	}

	@Override
	/**Vérifie l'égalité de deux horaires
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
		Heure other = (Heure) obj;
		if (heures != other.heures)
			return false;
		if (minutes != other.minutes)
			return false;
		return true;
	}

	public int compareTo(Heure arg0) {
		if(this.heures>arg0.heures){
			return 1;
		}else{
			if(this.heures<arg0.heures){
				return -1;
			}else{
				if(this.minutes>arg0.minutes){
					return 1;
				}else{
					if(this.minutes<arg0.minutes){
						return -1;
					}else{
						return 0;
					}
				}
			}
		}
	}

	@Override
	/**Donne l'affichage d'un horaire
	 * 
	 * @return String affichage de l'horaire
	 */
	public String toString() {
		return heures + "h " + minutes + " min";
	}	

}
