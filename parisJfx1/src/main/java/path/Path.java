package path;

public enum Path {
    LOGIN("/vues/login.fxml"),
    MENU("/vues/menu.fxml"),
    PARIS_OUVERTS("/vues/parisOuverts.fxml"),
    MES_PARIS("/vues/mesParis.fxml"),
    TOUS_LES_PARIS("/vues/admin/tousLesParis.fxml"),
    PARIS_OUVERTS_ADMIN("/vues/admin/parisOuvertsAdmin.fxml"),
    AJOUTER_ADMIN("/vues/admin/ajouter.fxml"),
    MES_PARIS_ADMIN("/vues/admin/mesParisAdmin.fxml"),
    PARIER("/vues/parier.fxml"),
    RESULTAT("/vues/admin/resultat.fxml");

    private String path;

    Path(String s) { this.path = s; }

    public String getPath() { return path; }
}
