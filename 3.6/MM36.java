import java.util.*;
public class MM36 {
    // .........................................................................
    // OUTILS DE BASE
    // .........................................................................

    // fonctions classiques sur les tableaux

    /**
     * pré-requis : nb >= 0
     * résultat : un tableau de nb entiers égaux à val
     */
    public static int[] initTab(int nb, int val) {
        int[] tab = new int[nb];
        for (int i = 0; i < nb; i++) {
            tab[i] = val;
        }
        return tab;
    }

    // ______________________________________________

    /**
     * pré-requis : aucun
     * résultat : une copie de tab
     */
    public static int[] copieTab(int[] tab) {
        int[] tab2 = new int[tab.length];
        for (int i = 0; i < tab.length; i++) {
            tab2[i] = tab[i];
        }
        return tab2;
    }

    // ______________________________________________

    /**
     * pré-requis : aucun
     * résultat : la liste des éléments de t entre parenthèses et séparés par des
     * virgules
     */
    public static String listElem(char[] t) {
        String parenthesis = "(";
        String backparenthesis = ")";
        String res = parenthesis;
        for (int n = 0; n < t.length; n++) {
            char a = t[n];
            String.valueOf(a);
            res += a;
            if (n + 1 < t.length) {
                res += ",";
            }
        }
        res = res + backparenthesis;
        return res;
    }

    // ______________________________________________

    /**
     * pré-requis : aucun
     * résultat : le plus grand indice d'une case de t contenant c s'il existe, -1
     * sinon
     */
    public static int plusGrandIndice(char[] t, char c) {
        for (int n = t.length - 1; n > -1; n--) {
            if (t[n] == c) {
                return n;
            }
        }
        return -1;
    }
    // ______________________________________________

    /**
     * pré-requis : aucun
     * résultat : vrai ssi c est un élément de t
     * stratégie : utilise la fonction plusGrandIndice
     */
    public static boolean estPresent(char[] t, char c) {
        return plusGrandIndice(t, c) > -1;
    }

    // ______________________________________________

    /**
     * pré-requis : aucun
     * action : affiche un doublon et 2 de ses indices dans t s'il en existe
     * résultat : vrai ssi les éléments de t sont différents
     * stratégie : utilise la fonction plusGrandIndice
     */
    public static boolean elemDiff(char[] t) {
        for (int n = 0; n < t.length; n++) {
            char a = t[n];
            if (plusGrandIndice(t, a) != n) {
                System.out.println("doublon : " + a + " aux indices " + n + " et " + plusGrandIndice(t, a));
                return false;
            }
        }
        return true;
    }

    // ______________________________________________

    /**
     * pré-requis : t1.length = t2.length
     * résultat : vrai ssi t1 et t2 contiennent la même suite d'entiers
     */
    public static boolean sontEgaux(int[] t1, int[] t2) {
        for (int n = 0; n < t1.length; n++) {
            if (t1[n] != t2[n]) {
                return false;
            }
        }
        return true;
    }

    // ______________________________________________

    // Dans toutes les fonctions suivantes, on a comme pré-requis implicites sur les
    // paramètres lgCode, nbCouleurs et tabCouleurs :
    // lgCode > 0, nbCouleurs > 0, tabCouleurs.length > 0 et les éléments de
    // tabCouleurs sont différents

    // fonctions sur les codes pour la manche Humain

    /**
     * pré-requis : aucun
     * résultat : un tableau de lgCode entiers choisis aléatoirement entre 0 et
     * nbCouleurs-1
     */
    public static int[] codeAleat(int lgCode, int nbCouleurs) {
        int[] res = initTab(lgCode, 0);
        for (int n = 0; n < res.length; n++) {
            res[n] = (int) (Math.random() * nbCouleurs);
        }
        return res;
    }

    // ____________________________________________________________

    /**
     * pré-requis : aucun
     * action : si codMot n'est pas correct, affiche pourquoi
     * résultat : vrai ssi codMot est correct, c'est-à-dire de longueur lgCode et ne
     * contenant que des éléments de tabCouleurs
     */
    public static boolean codeCorrect(String codMot, int lgCode, char[] tabCouleurs) {
        if (codMot.length() != lgCode) {
            System.out.println("Le code dois être de longueur " + lgCode);
            return false;
        }
        for (int n = 0; n < codMot.length(); n++) {
            char a = codMot.charAt(n);
            if (!estPresent(tabCouleurs, a)) {
                System.out.println("Le code ne contient pas que des éléments parmis " + listElem(tabCouleurs));
                return false;
            }
        }
        return true;
    }

    // ____________________________________________________________

    /**
     * pré-requis : les caractères de codMot sont des éléments de tabCouleurs
     * résultat : le code codMot sous forme de tableau d'entiers en remplaçant
     * chaque couleur par son indice dans tabCouleurs
     */
    public static int[] motVersEntiers(String codMot, char[] tabCouleurs) {
        int[] res = new int[codMot.length()];
        for (int n = 0; n < codMot.length(); n++) {
            char a = codMot.charAt(n);
            res[n] = plusGrandIndice(tabCouleurs, a);
        }
        return res;
    }

    // ____________________________________________________________

    /**
     * pré-requis : aucun
     * action : demande au joueur humain de saisir la (nbCoups + 1)ème proposition
     * de code sous forme de mot, avec re-saisie éventuelle jusqu'à ce
     * qu'elle soit correcte (le paramètre nbCoups ne sert que pour l'affichage)
     * résultat : le code saisi sous forme de tableau d'entiers
     */
    public static int[] propositionCodeHumain(int nbCoups, int lgCode, char[] tabCouleurs) {
            System.out.println("Proposition " + (nbCoups + 1) + " : ");
            String codMot = Ut.saisirChaine();
            while (!codeCorrect(codMot, lgCode, tabCouleurs)) {
                System.out.println("Proposition " + (nbCoups + 1) + " : ");
                codMot = Ut.saisirChaine();
        }
        return motVersEntiers(codMot, tabCouleurs);
    }

    // ____________________________________________________________

    /**
     * pré-requis : cod1.length = cod2.length
     * résultat : le nombre d'éléments communs de cod1 et cod2 se trouvant au même
     * indice
     * Par exemple, si cod1 = (1,0,2,0) et cod2 = (0,1,0,0) la fonction retourne 1
     * (le "0" à l'indice 3)
     */
    public static int nbBienPlaces(int[] cod1, int[] cod2) {
        int res = 0;
        for (int n = 0; n < cod1.length; n++) {
            if (cod1[n] == cod2[n]) {
                res += 1;
            }
        }
        return res;
    }

    // ____________________________________________________________

    /**
     * pré-requis : les éléments de cod sont des entiers de 0 à nbCouleurs-1
     * résultat : un tableau de longueur nbCouleurs contenant à chaque indice i le
     * nombre d'occurrences de i dans cod
     * Par exemple, si cod = (1,0,2,0) et nbCouleurs = 6 la fonction retourne
     * (2,1,1,0,0,0)
     */
    public static int[] tabFrequence(int[] cod, int nbCouleurs) {
        int[] res = new int[nbCouleurs];
        for (int n = 0; n < cod.length; n++) {
            res[cod[n]] += +1;
        }
        return res;
    }

    // ____________________________________________________________

    /**
     * pré-requis : les éléments de cod1 et cod2 sont des entiers de 0 à
     * nbCouleurs-1
     * résultat : le nombre d'éléments communs de cod1 et cod2, indépendamment de
     * leur position
     * Par exemple, si cod1 = (1,0,2,0) et cod2 = (0,1,0,0) la fonction retourne 3
     * (2 "0" et 1 "1")
     */
    public static int nbCommuns(int[] cod1, int[] cod2, int nbCouleurs) {
        int[] tab1 = tabFrequence(cod1, nbCouleurs);
        int[] tab2 = tabFrequence(cod2, nbCouleurs);
        int res = 0;
        for (int n = 0; n < tab1.length; n++) 
        { 
            if (tab1[n] <= tab2[n]) {
                res += tab1[n];
            } else {
                res += tab2[n];
            }
        }
        return res;
    }

    // ____________________________________________________________

    /**
     * pré-requis : cod1.length = cod2.length et les éléments de cod1 et cod2 sont
     * des entiers de 0 à nbCouleurs-1
     * résultat : un tableau de 2 entiers contenant à l'indice 0 (resp. 1) le nombre
     * d'éléments communs de cod1 et cod2
     * se trouvant (resp. ne se trouvant pas) au même indice
     * Par exemple, si cod1 = (1,0,2,0) et cod2 = (0,1,0,0) la fonction retourne
     * (1,2) : 1 bien placé (le "0" à l'indice 3)
     * et 2 mal placés (1 "0" et 1 "1")
     */
    public static int[] nbBienMalPlaces(int[] cod1, int[] cod2, int nbCouleurs) {
        int[] res = new int[2];
        res[0] = nbBienPlaces(cod1, cod2);
        res[1] = nbCommuns(cod1, cod2, nbCouleurs) - res[0];
        return res;
    }

    // ____________________________________________________________

    // .........................................................................
    // MANCHEHUMAIN
    // .........................................................................

    /**
     * pré-requis : numMache >= 1
     * action : effectue la (numManche)ème manche où l'ordinateur est le codeur et
     * l'humain le décodeur
     * (le paramètre numManche ne sert que pour l'affichage)
     * résultat :
     * - un nombre supérieur à nbEssaisMax, calculé à partir du dernier essai du
     * joueur humain (cf. sujet), s'il n'a toujours pas trouvé au bout du nombre
     * maximum d'essais
     * - sinon le nombre de codes proposés par le joueur humain
     */
    public static int mancheHumain(int lgCode, char[] tabCouleurs, int numManche, int nbEssaisMax) {
        int[] code = codeAleat(lgCode, tabCouleurs.length);
        int[] res = new int[2];
        int nbCoups = 0;
        String codeProp;
        while (res[0] != code.length && nbCoups <= nbEssaisMax) {
            System.out.println("Manche " + numManche);
            System.out.println("Coups : " + (nbCoups+1) + " / " + nbEssaisMax);
            System.out.print("Couleurs disponibles : ");
            for (int i = 0; i < tabCouleurs.length; i++) {
                System.out.print(tabCouleurs[i] + ", ");
            }
            System.out.println("\nProposez un code" + " de " + code.length + " couleurs");
            codeProp = Ut.saisirChaine().toUpperCase();
            while (!codeCorrect(codeProp, lgCode, tabCouleurs) || codeProp.length() != code.length) {
                System.out.println("Code incorrect, ressaisir un nouveau code");
                codeProp = Ut.saisirChaine().toUpperCase();
            }
            res = nbBienMalPlaces(code, motVersEntiers(codeProp, tabCouleurs), code.length);
            System.out.println("Il y a " + res[0] + " bien placés et " + res[1] + " de la bonne couleur mais mal placés.\n");
            nbCoups++;
        }
        if (nbCoups < nbEssaisMax) {
            System.out.println("Félicitations! Vous avez trouvé le code en " + nbCoups + " essais");
            return nbCoups;
        } 
        else {
            System.out.println("Vous avez perdu, le code était : " + entiersVersMot(code, tabCouleurs));
            int nbMalPlaces = res[0];
            int nbBienPlaces = res[1];
            return  nbMalPlaces + 2 * (lgCode - (nbBienPlaces + nbMalPlaces));
        }
    }
    // ____________________________________________________________

    // ...................................................................
    // FONCTIONS COMPLÉMENTAIRES SUR LES CODES POUR LA MANCHE ORDINATEUR
    // ...................................................................

    /**
     * pré-requis : les éléments de cod sont des entiers de 0 à tabCouleurs.length-1
     * résultat : le code cod sous forme de mot d'après le tableau tabCouleurs
     */
    public static String entiersVersMot(int[] cod, char[] tabCouleurs) {
        String res = "";
        for (int n = 0; n < cod.length; n++) {
            res += tabCouleurs[cod[n]];
        }
        return res;
    }

    // ___________________________________________________________________

    /**
     * pré-requis : rep.length = 2
     * action : si rep n'est pas correcte, affiche pourquoi, sachant que rep[0] et
     * rep[1] sont les nombres de bien et mal placés resp.
     * résultat : vrai ssi rep est correct, c'est-à-dire rep[0] et rep[1] sont >= 0
     * et leur somme est <= lgCode
     */
    public static boolean repCorrecte(int[] rep, int lgCode) {
        if (rep[0] < 0 || rep[1] < 0) {
            System.out.println("Le nombre de bien et de mal placés ne peut pas être négatif");
            return false;
        } else if (rep[0] + rep[1] > lgCode) {
            System.out.println("Le nombre de bien et de mal placés ne peut pas être supérieur à la longueur du code");
            return false;
        } else {
            return true;
        }
    }

    // ___________________________________________________________________

    /**
     * pré-requis : aucun
     * action : demande au joueur humain de saisir les nombres de bien et mal
     * placés,
     * avec re-saisie éventuelle jusqu'à ce qu'elle soit correcte
     * résultat : les réponses du joueur humain dans un tableau à 2 entiers
     */
    public static int[] reponseHumain(int lgCode) {
        int[] rep = new int[2];
        System.out.println("Entrez le nombre de couleurs bien placées");
        rep[0] = Ut.saisirEntier();
        System.out.println("Entrez le nombre de couleurs mal placées");
        rep[1] = Ut.saisirEntier();
        while (!repCorrecte(rep, lgCode)) {
            System.out.println("Les nombres entrés ne sont pas corrects");
            System.out.println("Entrez le nombre de corrects et bien placés");
            rep[0] = Ut.saisirEntier();
            System.out.println("Entrez le nombre de corrects et mal placés");
            rep[1] = Ut.saisirEntier();
        }
        return rep;
    }

    // ___________________________________________________________________

    /**
     * CHANGE : action si le code suivant n'existe pas
     *************************************************
     * pré-requis : les éléments de cod1 sont des entiers de 0 à nbCouleurs-1
     * action/résultat : met dans cod1 le code qui le suit selon l'ordre
     * lexicographique (dans l'ensemble des codes à valeurs de 0 à nbCouleurs-1) et
     * retourne vrai si ce code existe,
     * sinon met dans cod1 le code ne contenant que des "0" et retourne faux
     */
    public static boolean passeCodeSuivantLexico(int[] cod1, int nbCouleurs) {
        int i = cod1.length - 1;
        while (i >= 0 && cod1[i] == nbCouleurs - 1) {
            cod1[i] = 0;
            i--;
        }
        if (i < 0) {
            return false;
        } else {
            cod1[i]++;
            return true;
        }
    }

    // ___________________________________________________________________

    /**
     * CHANGE : ajout du paramètre cod1 et modification des spécifications
     ********************************************************************* 
     * pré-requis : cod est une matrice à cod1.length colonnes, rep est une matrice
     * à 2 colonnes, 0 <= nbCoups < cod.length, nbCoups < rep.length et les éléments
     * de cod1 et de cod sont des entiers de 0 à nbCouleurs-1
     * résultat : vrai ssi cod1 est compatible avec les nbCoups premières lignes de
     * cod et de rep,c'est-à-dire que si cod1 était le code secret, les réponses aux
     * nbCoups premières propositions de cod seraient les nbCoups premières réponses
     * de rep resp.
     */
    public static boolean estCompat(int[] cod1, int[][] cod, int[][] rep, int nbCoups, int nbCouleurs) {
        for (int n = 0; n < nbCoups; n++) {
            if (!sontEgaux(rep[n], nbBienMalPlaces(cod1, cod[n], nbCouleurs))) {
                return false;
            }
        }
        return true;
    }

    // ___________________________________________________________________

    /**
     * CHANGE : renommage de passePropSuivante en passeCodeSuivantLexicoCompat,
     * ajout du paramètre cod1 et modification des spécifications
     ************************************************************************** 
     * pré-requis : cod est une matrice à cod1.length colonnes, rep est une matrice
     * à 2 colonnes, 0 <= nbCoups < cod.length, nbCoups < rep.length et les éléments
     * de cod1 et de cod sont des entiers de 0 à nbCouleurs-1
     * action/résultat : met dans cod1 le plus petit code (selon l'ordre
     * lexicographique (dans l'ensemble des codes à valeurs de 0 à nbCouleurs-1) qui
     * est à la fois plus grand que
     * cod1 selon cet ordre et compatible avec les nbCoups premières lignes de cod
     * et rep si ce code existe, sinon met dans cod1 le code ne contenant que des
     * "0" et retourne faux
     */
    public static boolean passeCodeSuivantLexicoCompat(int[] cod1, int[][] cod, int[][] rep, int nbCoups, int nbCouleurs) {
        while (!estCompat(cod1, cod, rep, nbCoups, nbCouleurs)) {
            if (!passeCodeSuivantLexico(cod1, nbCouleurs)) {
                return false;
            }
        }

        return true;
    }

    // ___________________________________________________________________

    // manche Ordinateur

    /**
     * pré-requis : numManche >= 2
     * action : effectue la (numManche)ème manche où l'humain est le codeur et
     * l'ordinateur le décodeur
     * (le paramètre numManche ne sert que pour l'affichage)
     * résultat :
     * - 0 si le programme détecte une erreur dans les réponses du joueur humain
     * - un nombre supérieur à nbEssaisMax, calculé à partir du dernier essai de
     * l'ordinateur (cf. sujet), s'il n'a toujours pas trouvé au bout du nombre
     * maximum d'essais
     * - sinon le nombre de codes proposés par l'ordinateur
     */
    public static int mancheOrdinateur(int lgCode, char[] tabCouleurs, int numManche, int nbEssaisMax) {
        int[][] cod = new int[nbEssaisMax+1][lgCode];
        int[][] rep = new int[nbEssaisMax+1][2];
        int[] cod1 = new int[lgCode];
        int nbCouleurs = tabCouleurs.length;
        int nbCoups = 0;
        int strat = Ut.randomMinMax(1, 2);
        if(strat == 2){
            for(int i = 0; i < lgCode; i++){
                cod1[i] = tabCouleurs.length-1;
            }
        }
        while(nbCoups <= nbEssaisMax) {
            System.out.println("Manche " + numManche);
            System.out.println("Coups : " + (nbCoups+1) + " / " + nbEssaisMax);
            System.out.println("La machine propose le code suivant:");
            for (int i : cod1) {
                System.out.print(tabCouleurs[i] + " ");
            }
            System.out.println();
            rep[nbCoups] = reponseHumain(lgCode);
            cod[nbCoups] = copieTab(cod1);
            if (rep[nbCoups][0] == lgCode) {
                System.out.println("\nLa machine a gagné en " + nbCoups + " coups");
                return nbCoups;
            }
            nbCoups++;
            if (!contreStrategie(cod1, cod, rep, nbCoups, nbCouleurs, strat)) {
                System.out.println("Aucun code n'est compatible avec vos réponses");
            }
            Ut.clearConsole();
        }
        System.out.println("La machine a dépassé le nombre d'essais maximum.");
        System.out.println("Elle n'a pas trouvé le code secret.");
        return nbEssaisMax + rep[nbEssaisMax - 1][1] + 2 * (lgCode - (rep[nbEssaisMax - 1][0] + rep[nbEssaisMax - 1][1])); 
    }

    // ___________________________________________________________________

    // .........................................................................
    // FONCTIONS DE SAISIE POUR LE PROGRAMME PRINCIPAL
    // .........................................................................

    /**
     * pré-requis : aucun
     * action : demande au joueur humain de saisir un entier strictement positif,
     * avec re-saisie éventuelle jusqu'à ce qu'elle soit correcte
     * résultat : l'entier strictement positif saisi
     */
    public static int saisirEntierPositif() {
        int rep = Ut.saisirEntier();
        while (rep <= 0) {
            System.out.println("L'entier saisi n'est pas strictement positif");
            rep = Ut.saisirEntier();
        }
        return rep;
    }

    // ___________________________________________________________________

    /** pré-requis : aucun
	action : demande au joueur humain de saisir un entier pair strictement positif, avec re-saisie éventuelle jusqu'à ce qu'elle soit correcte
	résultat : l'entier pair strictement positif saisi
    */
    public static int saisirEntierPairPositif(){
        int rep = saisirEntierPositif();
        while (rep % 2 != 0) {
            System.out.println("L'entier saisi n'est pas pair");
            rep = saisirEntierPositif();
        }
        return rep;
    }

    // ___________________________________________________________________

    /**
     * pré-requis : aucun
     * action : demande au joueur humain de saisir le nombre de couleurs (stricement
     * positif), puis les noms de couleurs aux initiales différentes, avec re-saisie
     * éventuelle jusqu'à ce qu'elle soit correcte
     * résultat : le tableau des initiales des noms de couleurs saisis
     */
    public static char[] saisirCouleurs() {
        int nbCoul = Ut.saisirEntier();
        char[] rep = new char[nbCoul];
        System.out.println("Saisissez " + nbCoul + " couleurs.");
        for (int c = 0; c < nbCoul; c++) {
            String coul = Ut.saisirChaine().toUpperCase();
            char initiales = coul.charAt(0);
            rep[c] = initiales;
            for (int i = 0; i < c; i++) {
                while (rep[i] == rep[c] && i != c) {
                    System.out.println("Erreur, 2 couleurs ne peuvent pas avoir les mêmes initiales.");
                    c--;
                }
            }
        }
        return rep;
    }

    public static boolean passeCodePrecdentLexico(int[] cod1, int nbCouleurs) {
        int i = cod1.length - 1;
        while (i >= 0 && cod1[i] == 0) {
            i--;
        }
        if (i < 0) {
            return false;
        }
        cod1[i]--;
        for (int j = i + 1; j < cod1.length; j++) {
            cod1[j] = nbCouleurs - 1;
        }
        return true;
    }

    public static boolean contreStrategie(int[] cod1, int[][] cod, int[][] rep, int nbCoups, int nbCouleurs, int sensAlea) {
        while (!estCompat(cod1, cod, rep, nbCoups, nbCouleurs)) {
            if(sensAlea == 1){
                if (!passeCodeSuivantLexico(cod1, nbCouleurs)) {
                    return false;
                }
            }
            else if(sensAlea == 2){
                if (!passeCodePrecdentLexico(cod1, nbCouleurs)) {
                    return false;
                }
            }
        }

        return true;
    }
    // ___________________________________________________________________

    // .........................................................................
    // PROGRAMME PRINCIPAL
    // .........................................................................

    /**
     * CHANGE : ajout de : le nombre d'essais maximum doit être strictement positif
     ******************************************************************************
     * action : demande à l'utilisateur de saisir les paramètres de la partie
     * (lgCode, tabCouleurs,
     * nbManches, nbEssaisMax),
     * effectue la partie et affiche le résultat (identité du gagnant ou match nul).
     * La longueur d'un code, le nombre de couleurs et le nombre d'essais maximum
     * doivent être strictement positifs.
     * Le nombre de manches doit être un nombre pair strictement positif.
     * Les initiales des noms de couleurs doivent être différentes.
     * Toute donnée incorrecte doit être re-saisie jusqu'à ce qu'elle soit correcte.
     */
    //
    public static void main(String[] args) {
        Ut.clearConsole();
        int numManche = 0;
        int pointOrdinateur = 0;
        int pointHumain = 0;
        System.out.println("Saisissez le nombre de manches.");
        int nbManches = saisirEntierPairPositif();
        Ut.clearConsole();
        System.out.println("Saisissez la longueur du code secret.");
        int lgCode = saisirEntierPositif();
        Ut.clearConsole();
        System.out.println("Saisissez le nombre de couleurs voulues.");
        char[] tabCouleurs = saisirCouleurs();
        Ut.clearConsole();
        System.out.println("Saisissez le nombre d'essais maximum.");
        int nbEssaisMax = saisirEntierPositif();
        Ut.clearConsole();
        while(numManche <= nbManches){
            if(numManche%2 == 0){
                pointHumain += mancheOrdinateur(lgCode, tabCouleurs, numManche, nbEssaisMax);
                pointOrdinateur += mancheHumain(lgCode, tabCouleurs, numManche, nbEssaisMax);
                numManche++;
            }
        }
        System.out.println("Score finaux :");
        System.out.println("Ordinateur : " + pointOrdinateur);
        System.out.println("Humain : " + pointHumain);
        if(pointOrdinateur < pointHumain){
            System.out.println("L'ordinateur a gagné la partie!");
        }
        else if(pointOrdinateur > pointHumain){
            System.out.println("Bravo, vous avez gagner la partie!");
        }
        else{
            System.out.println("Match nul!");
        }
        /*
        char[] tabCouleurs = {'R', 'V', 'B', 'J', 'O', 'N'};
        mancheOrdinateur(4, tabCouleurs, 2, 15);
	*/
    }
}
