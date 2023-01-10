import java.util.*;
import java.lang.*;

public class RelationBinaire {

    // attributs

    private int n;           // n > 0, E = {0,1,2, ..., n-1}
    private boolean[][] matAdj;  // matrice d'adjacence de R
    private int m;           // cardinal de R
    private EE[] tabSucc;    // tableau des ensembles de successeurs

    // constructeurs

    /**
     * pré-requis : nb > 0
     * action : construit la relation binaire vide dans l'ensemble {0,1,2, ..., nb-1}
     */
    public RelationBinaire(int nb) {
        this.n = nb;
        this.m = 0;
        this.matAdj = new boolean[nb][nb];
        this.tabSucc = new EE[nb];
        for(int i = 0; i < nb; i++){
            this.tabSucc[i] = new EE(nb);
        }
    }

    //______________________________________________


    /**
     * pré-requis : nb > 0 et 0 <= p <= 1
     * action : construit une relation binaire aléatoire dans l'ensemble {0,1,2, ..., nb-1}
     * à laquelle chaque couple a la probabilité p d'appartenir.
     * En particulier, construit la relation vide si p = 0 et la relation pleine si p = 1.
     * Indication : Math.random() retourne un réel de type double aléatoire de l'intervalle [0,1[
     */
    public RelationBinaire(int nb, double p) {
        this.n = nb;
        this.m = 0;
        this.matAdj = new boolean[nb][nb];
        this.tabSucc = new EE[nb];
        for (int i = 0; i < nb; i++) {
            this.tabSucc[i] = new EE(nb);
            for (int j = 0; j < nb; j++) {
                if (Math.random() <= p) {
                    this.matAdj[i][j] = true;
                    this.m++;
                }
            }
        }
    }

    //______________________________________________


    /**
     * pré-requis : nb > 0 et 1 <= choix <= 3
     * action : construit la relation binaire dans l'ensemble {0,1,2, ..., nb-1}
     * '=' si egal a la valeur vrai et '<=' sinon
     */
    public RelationBinaire(int nb, boolean egal) {
        this.n = nb;
        this.m = 0;
        this.tabSucc = new EE[nb];
        this.matAdj = new boolean[nb][nb];
        if (egal) {
            this.matAdj = avecBoucles().matAdj;
        } else {
            int k = 1;
            for (int i = 0; i < nb; i++) {
                this.tabSucc[i] = new EE(nb);
                for (int j = 0; j < k; j++) {
                    this.tabSucc[i].ajoutElt(j);
                    this.matAdj[i][j] = true;
                    this.m++;
                }
                k++;
            }
        }
    }

    //______________________________________________


    /**
     * pré-requis : mat est une matrice carrée de dimension > 0
     * action : construit une relation binaire dont la matrice d'adjacence
     * est une copie de mat
     */
    public RelationBinaire(int[][] mat) {
        int lgM = mat.length;
        this.n = lgM;
        this.m = 0;
        this.matAdj = new boolean[lgM][lgM];
        this.tabSucc = new EE[lgM];
        for (int i = 0; i < lgM; i++) {
            this.tabSucc[i] = new EE(lgM);
            for (int j = 0; j < lgM; j++) {
                if (mat[i][j] == 1) {
                    this.tabSucc[i].ajoutElt(j);
                    this.matAdj[i][j] = true;
                    this.m++;
                }
            }
        }
    }

    //______________________________________________


    /**
     * pré-requis : tab.length > 0 et pour tout i, les éléments de tab[i] sont compris entre 0 et tab.length-1
     * action : construit une relation binaire dont le tableau des ensembles de successeurs est une copie de tab
     */
    /*public RelationBinaire(EE[] tab) {
        n = tab.length;
        this.n = n;
        this.m = 0;
        this.matAdj = new boolean[n][n];
        for(int i = 0;)
    }
    //______________________________________________
    /**
    * pré-requis : aucun
    * action : construit une copie de r
    */
    public RelationBinaire(RelationBinaire r) {
        this.n = r.n;
        this.m = r.m;
        this.matAdj = new boolean[r.n][r.n];
        this.tabSucc = new EE[r.n];
        for (int i = 0; i < r.n; i++){
            this.tabSucc[i] = new EE(r.n);
            for (int j = 0; j < r.n; j++) {
                this.matAdj[i][j] = r.matAdj[i][j];
                if(r.matAdj[i][j]){
                    this.tabSucc[i].ajoutElt(j);
                }
            }
        }
    }


    //______________________________________________


    // méthodes


    /**
     * pré-requis : aucun
     * résultat : une chaîne de caractères permettant d'afficher this par sa matrice d'adjacence contenant des '0' et des '1' (plus lisibles que des 'V' et des 'F') et définition en extension (ensemble de couples {(..,..),(..,..), ...})
     */
    public String toString() {
        String res = "";
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.matAdj[i][j]) {
                    res += "1";
                } else {
                    res += "0";
                }
            }
            res += "\n";
        }
        res += "Extension : {";
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.matAdj[i][j]) {
                    res += "(" + i + "," + j + ")";
                }
            }
        }
        res += "}";
        return res;
    }

    //______________________________________________


    // A) Logique et calcul matriciel
    //-------------------------------


    /**
     * pré-requis : m1 et m2 sont des matrices carrées de même dimension et 1 <= numConnecteur <= 5
     * résultat : la matrice obtenue en appliquant terme à terme le  connecteur de numéro numConnecteur
     * sur m1 si numConnecteur  = 3 (dans ce cas le paramètre m2 n'est pas utilisé),
     * et sur m1 et m2 dans cet ordre sinon, sachant que les connecteurs "ou","et","non",
     * "implique"et "equivalent" sont numérotés de 1 à 5 dans cet ordre
     */

    public static boolean[][] opBool(boolean[][] m1, boolean[][] m2, int numConnecteur) {
        int lgM = m1.length;
        boolean[][] res = new boolean[lgM][lgM];
        if (numConnecteur == 1) {
            for (int i = 0; i < lgM; i++) {
                for (int j = 0; j < lgM; j++) {
                    res[i][j] = m1[i][j] || m2[i][j];
                }
            }
        } else if (numConnecteur == 2) {
            for (int i = 0; i < lgM; i++) {
                for (int j = 0; j < lgM; j++) {
                    res[i][j] = m1[i][j] && m2[i][j];
                }
            }
        } else if (numConnecteur == 3) {
            for (int i = 0; i < lgM; i++) {
                for (int j = 0; j < lgM; j++) {
                    res[i][j] = !m1[i][j];
                }
            }
        } else if (numConnecteur == 4) {
            for (int i = 0; i < lgM; i++) {
                for (int j = 0; j < lgM; j++) {
                    res[i][j] = !m1[i][j] || m2[i][j];
                }
            }
        } else if (numConnecteur == 5) {
            for (int i = 0; i < lgM; i++) {
                for (int j = 0; j < lgM; j++) {
                    res[i][j] = m1[i][j] == m2[i][j];
                }
            }
        }
        return res;
    }

    //______________________________________________


    /**
     * pré-requis : m1 et m2 sont des matrices carrées de même dimension
     * résultat : le produit matriciel de m1 et m2
     */
    public static boolean[][] produit(boolean[][] m1, boolean[][] m2) {
        int lgM = m1.length;
        boolean[][] res = new boolean[lgM][lgM];
        for (int i = 0; i < lgM; i++) {
            for (int j = 0; j < lgM; j++) {
                for (int k = 0; k < lgM; k++) {
                    res[i][j] = res[i][j] || (m1[i][k] && m2[k][j]);
                }
            }
        }
        return res;
    }
    //______________________________________________


    /**
     * pré-requis : m est une matrice carrée
     * résultat : la matrice transposée de m
     */
    public static boolean[][] transposee(boolean[][] m) {
        int lgM = m.length;
        boolean[][] res = new boolean[lgM][lgM];
        for (int i = 0; i < lgM; i++) {
            for (int j = 0; j < lgM; j++) {
                res[i][j] = m[j][i];
            }
        }
        return res;
    }

    //______________________________________________


    // B) Théorie des ensembles
    //--------------------------


    /**
     * pré-requis : aucun
     * résultat : vrai ssi this est vide
     */
    public boolean estVide() {
        return (this.m == 0);
    }

    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : vrai ssi this est pleinee (contient tous les couples d'éléments de E)
     */
    public boolean estPleine() {
        return (this.m == this.n * this.n);
    }

    //______________________________________________

    /**
     * pré-requis : aucun
     * résultat : vrai ssi (x,y) appartient à this
     */
    public boolean appartient(int x, int y) {
        return matAdj[x][y];
    }

    //______________________________________________


    /**
     * pré-requis : 0 <= x < this.n et 0 <= y < this.n
     * résultat : ajoute (x,y) à this s'il n'y est pas déjà
     */
    public void ajouteCouple(int x, int y) {
        if (!this.appartient(x, y)) {
            matAdj[x][y] = true;
            this.tabSucc[x].ajoutElt(y);
            this.m++;
        }
    }

    //______________________________________________


    /**
     * pré-requis : 0 <= x < this.n et 0 <= y < this.n
     * résultat : enlève (x,y) de this s'il y est
     */
    public void enleveCouple(int x, int y) {
        if (this.appartient(x, y)) {
            matAdj[x][y] = false;
            this.tabSucc[x].retraitElt(y);
            this.m--;
        }
    }

    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : une nouvelle relation binaire obtenue à partir de this en ajoutant
     * les couples de la forme  (x,x) qui n'y sont pas déjà
     */
    public RelationBinaire avecBoucles() {
        RelationBinaire res = new RelationBinaire(this);
        int lgM = this.n;
        for (int i = 0; i < lgM; i++) {
            if (!res.appartient(i, i)) {
                res.ajouteCouple(i, i);
                res.tabSucc[i].ajoutElt(i);
                this.m++;
            }
        }
        return res;
    }


    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : une nouvelle relation binaire obtenue à partir de this en enlèvant
     * les couples de la forme  (x,x) qui y sont
     */
    public RelationBinaire sansBoucles() {
        RelationBinaire res = new RelationBinaire(this);
        int lgM = this.n;
        for (int i = 0; i < lgM; i++) {
            if (res.appartient(i, i)) {
                res.enleveCouple(i, i);
                res.tabSucc[i].retraitElt(i);
                this.m--;
            }
        }
        return res;
    }

    //______________________________________________


    /**
     * pré-requis : this.n = r.n
     * résultat : l'union de this et r
     */
    public RelationBinaire union(RelationBinaire r) {
        RelationBinaire res = new RelationBinaire(this);
        res.matAdj = opBool(r.matAdj, this.matAdj, 1);
        return res;
    }

    //______________________________________________


    /**
     * pré-requis : this.n = r.n
     * résultat : l'intersection de this et r
     */
    public RelationBinaire intersection(RelationBinaire r) {
        RelationBinaire res = new RelationBinaire(this);
        int lgM = this.n;
        res.matAdj = opBool(r.matAdj, this.matAdj, 2);
        for (int i = 0; i < lgM; i++) {
            for (int j = 0; j < lgM; j++) {
                if (res.matAdj[i][j]) {
                    res.tabSucc[i].ajoutElt(j);
                    res.m++;
                }
            }
        }
        return res;
    }

    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : la relation complémentaire de this
     */
    public RelationBinaire complementaire() {
        RelationBinaire res = new RelationBinaire(this);
        res.matAdj = opBool(this.matAdj, this.matAdj, 3);
        return res;
    }

    //______________________________________________


    /**
     * pré-requis : this.n = r.n
     * résultat : la différence de this et r
     */
    public RelationBinaire difference(RelationBinaire r) {
        RelationBinaire res = new RelationBinaire(this);
        res.matAdj = opBool(this.matAdj, r.matAdj, 2);
        return res;
    }

    //______________________________________________


    /**
     * pré-requis : this.n = r.n
     * résultat : vrai ssi this est incluse dans r
     */
    public boolean estIncluse(RelationBinaire r) {
        int lgM = this.matAdj.length;
        for (int i = 0; i < lgM; i++) {
            for (int j = 0; j < lgM; j++) {
                if (this.matAdj[i][j] && !r.matAdj[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //______________________________________________


    /**
     * pré-requis : this.n = r.n
     * résultat : vrai ssi this est égale à r
     */
    public boolean estEgale(RelationBinaire r) {
        int lgM = this.matAdj.length;
        for (int i = 0; i < lgM; i++) {
            for (int j = 0; j < lgM; j++) {
                if (this.matAdj[i][j] != r.matAdj[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //______________________________________________

    // C) Théorie des graphes orientés
    //---------------------------------

    /** pré-requis : 0 <= x < this.n
     résultat : l'ensemble des successeurs de x dans this, "indépendant"
     (c'est-à-dire dans une autre zône mémoire) de l'attribut this.tabSucc
     */
    public EE succ(int x){
        EE res = new EE(this.tabSucc.length);
        for(int i = 0; i < tabSucc.length; i++){
            if(this.tabSucc[x].contient(i)){
                res.ajoutElt(i);
            }
        }
        return res;
    }

    //______________________________________________


    /** pré-requis : 0 <= x < this.n
     résultat : l'ensemble des prédécesseurs de x dans this
     */
    public EE pred(int x){
        //parcourir le tab et mettre dans un new EE toutes les cases(indices) de tab.succ comprenant x dans leur EE c'est TRIVIAL
    }

    //______________________________________________


    // D) Relation binaire
    //---------------------

    /** pré-requis : aucun
     résultat : vrai ssi this est réflexive
     */
    public boolean estReflexive(){
        for (int i = 0; i < this.matAdj.length; i++) {
            if (!this.matAdj[i][i]) {
                return false;
            }
        }
        return true;
    }

    //______________________________________________


    /** pré-requis : aucun
     résultat : vrai ssi this est antiréflexive
     */
    public boolean estAntireflexive(){
        for (int i = 0; i < this.matAdj.length; i++) {
            if (this.matAdj[i][i]) {
                return false;
            }
        }
        return true;
    }

    //______________________________________________


    /** pré-requis : aucun
     résultat : vrai ssi this est symétrique
     */
    public boolean estSymetrique(){
        int lgM = this.matAdj.length;
        for (int i = 0; i < lgM; i++) {
            for (int j = 0; j < lgM; j++) {
                if (this.matAdj[i][j] != this.matAdj[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    //______________________________________________


    /** pré-requis : aucun
     résultat : vrai ssi this est antisymétrique
     */
    public boolean estAntisymetrique(){
        int lgM = this.matAdj.length;
        for (int i = 0; i < lgM; i++) {
            for (int j = 0; j < lgM; j++) {
                if (this.matAdj[i][j] && this.matAdj[i][j] == this.matAdj[j][i] && i != j) {
                    return false;
                }
            }
        }
        return true;
    }

    //______________________________________________


    /** pré-requis : aucun
     résultat : vrai ssi this est transitive
     */
    public boolean estTransitive(){
        int lgM = this.tabSucc.length;
        for(int i = 0; i < lgM; i++){
            for(int j = 0; j < lgM; j++){
                for(int k = 0; k < lgM; k++){
                    if(this.matAdj[i][j] && this.matAdj[j][k] && !this.matAdj[i][k]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //______________________________________________


    /** pré-requis : aucun
     résultat : vrai ssi this est une relation d'ordre
     */
    public boolean estRelOrdre(){
        if(this.estReflexive() && this.estAntisymetrique() && this.estTransitive()){
            return true;
        }
        return false;
    }

    //______________________________________________



    /** pré-requis : aucun
     résultat : la relation binaire assiciée au diagramme de Hasse de this
     */
    public RelationBinaire hasse(){
        RelationBinaire res = new RelationBinaire(this);
        int lgM = this.tabSucc.length;
        for(int i = 0; i < lgM; i++){
            for(int j = 0; j < lgM; j++){
                for(int k = 0; k < lgM; k++){
                    if(this.matAdj[i][j] && this.matAdj[j][k] && this.matAdj[i][k]){
                        res.matAdj[i][k] = false;
                        res.tabSucc[i].retraitElt(k);
                        m--;
                    }
                }
            }
        }
        return res;
    }

    //______________________________________________

    /** pré-requis : aucun
     résultat : la fermeture transitive de this
     */
    public RelationBinaire ferTrans(){
        RelationBinaire res = new RelationBinaire(this);
        int lgM = this.tabSucc.length;
        for(int i = 0; i < lgM; i++){
            for(int j = 0; j < lgM; j++){
                for(int k = 0; k < lgM; k++){
                    if(this.matAdj[i][j] && this.matAdj[j][k] && !this.matAdj[i][k]){
                        res.matAdj[i][k] = true;
                        res.tabSucc[i].ajoutElt(k);
                        m++;
                    }
                }
            }
        }
        return res;
    }

    //______________________________________________

    /** pré-requis : aucun
     action : affiche this sous 2 formes (matrice et ensemble de couples), puis affiche ses propriétés
     (réflexive, ..., relation d'ordre) et les relations binaires suivantes obtenues à partir de this :
     Hasse, fermeture transitive de Hasse et fermeture transitive de Hasse avec boucles (sous 2 formes aussi)
     */
    public void afficheDivers(){

    }

    //______________________________________________


} // fin RelationBinaire
