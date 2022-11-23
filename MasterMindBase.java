import java.util.*;
import java.lang.*;

public class MasterMindBase {

    //.........................................................................
    // OUTILS DE BASE
    //.........................................................................
    
    // fonctions classiques sur les tableaux

    /** pré-requis : nb >= 0
	résultat : un tableau de nb entiers égaux à val
    */
    public static int[] initTab(int nb, int val){
        int[] tab = new int[nb];
        for(int i = 0; i < nb; i++){
            tab[i] = val;
        }
        return tab;
    }

    //______________________________________________
    
    /** pré-requis : aucun
	résultat : une copie de tab
    */
    public static int[] copieTab(int[] tab){
        int [] tab2 = tab;
        return tab2;
    }

    //______________________________________________
    
    /** pré-requis : aucun
	résultat : la liste des éléments de t entre parenthèses et séparés par des virgules
    */
    public static String listElem(char[] t){
        String parenthesis = "(";
        String backparenthesis = ")";
        String res = parenthesis;
        for(int n = 0; n < t.length; n++){
            char a = t[n];
            String.valueOf(a);
            res += a;
            if(n+1 < t.length){
                res += ",";
            }
        }
        res = res + backparenthesis;
        return res;
    }

    //______________________________________________
    
    /** pré-requis : aucun
	résultat : le plus grand indice d'une case de t contenant c s'il existe, -1 sinon
    */
    public static int plusGrandIndice(char[] t, char c){
        for(int n = t.length -1; n > -1; n--){
            if(t[n] == c){
                return n;
            }
        }
        return -1;
    }
    //______________________________________________

    /** pré-requis : aucun
	résultat : vrai ssi c est un élément de t
	stratégie : utilise la fonction plusGrandIndice
    */
    public static boolean estPresent(char[] t, char c){
        return plusGrandIndice(t, c) > -1;
    }

    //______________________________________________
    
    /** pré-requis : aucun
	action : affiche un doublon et 2 de ses indices dans t s'il en existe
	résultat : vrai ssi les éléments de t sont différents
	stratégie : utilise la fonction plusGrandIndice
    */
    public static boolean elemDiff(char[] t){
        for(int n = 0; n < t.length; n++){
            char a = t[n];
            if(plusGrandIndice(t, a) != n){
                System.out.println("doublon : " + a + " aux indices " + n + " et " + plusGrandIndice(t, a));
                return false;
            }
        }
        return true;
    }
    
    //______________________________________________

    /** pré-requis : t1.length = t2.length
	résultat : vrai ssi t1 et t2 contiennent la même suite d'entiers
    */
    public static boolean sontEgaux(int[] t1, int[] t2){
        for(int n = 0 ; n < t1.length; n++){
            System.out.println(n);
            if(t1[n] != t2[n]){
                return false;
            }
        }
        return true;
    }

    //______________________________________________

    // Dans toutes les fonctions suivantes, on a comme pré-requis implicites sur les paramètres lgCode, nbCouleurs et tabCouleurs :
    // lgCode > 0, nbCouleurs > 0, tabCouleurs.length > 0 et les éléments de tabCouleurs sont différents

    // fonctions sur les codes pour la manche Humain

    /** pré-requis : aucun
	résultat : un tableau de lgCode entiers choisis aléatoirement entre 0 et nbCouleurs-1
    */
    public static int[] codeAleat(int lgCode, int nbCouleurs){
    int[] res = new int[lgCode];
    for(int n = 0; n < res.length; n++){
        res[n] = (int)(Math.random() * nbCouleurs);
    }
    return res;
    }      

    //____________________________________________________________
    
    /** pré-requis : aucun
	action : si codMot n'est pas correct, affiche pourquoi
	résultat : vrai ssi codMot est correct, c'est-à-dire de longueur lgCode et ne contenant que des éléments de tabCouleurs
    */
    public static boolean codeCorrect(String codMot, int lgCode, char[] tabCouleurs){
        if(codMot.length() != lgCode){
            System.out.println("Le code n'a pas une longueur de " + lgCode);
            return false;
        }
        for(int n = 0; n < codMot.length(); n++){
            char a = codMot.charAt(n);
            if(!estPresent(tabCouleurs, a)){
                System.out.println("Le code ne contient pas que des éléments parmis " + listElem(tabCouleurs));
                return false;
            }
        }
        return true;
    }
   
    //____________________________________________________________
    
    /** pré-requis : les caractères de codMot sont des éléments de tabCouleurs
	résultat : le code codMot sous forme de tableau d'entiers en remplaçant chaque couleur par son indice dans tabCouleurs
    */
    public static int[] motVersEntiers(String codMot, char[] tabCouleurs){
        int[] res = new int[codMot.length()];
        for(int n = 0; n < codMot.length(); n++){
            char a = codMot.charAt(n);
            res[n] = plusGrandIndice(tabCouleurs, a);
        }
        return res;
    }

    //____________________________________________________________
    
    /** pré-requis : aucun
	action : demande au joueur humain de saisir la (nbCoups + 1)ème proposition de code sous forme de mot, avec re-saisie éventuelle jusqu'à ce 
	qu'elle soit correcte (le paramètre nbCoups ne sert que pour l'affichage)
	résultat : le code saisi sous forme de tableau d'entiers
    */
    public static int[] propositionCodeHumain(int nbCoups, int lgCode, char[] tabCouleurs){
        try (Scanner scan = new Scanner(System.in)) {
            System.out.println("Proposition " + (nbCoups + 1) + " : ");
            String codMot = scan.nextLine();
            while(!codeCorrect(codMot, lgCode, tabCouleurs)){
                System.out.println("Proposition " + (nbCoups + 1) + " : ");
                codMot = scan.nextLine();
            }
            return motVersEntiers(codMot, tabCouleurs);
        }
    }

    //____________________________________________________________
    
    /** pré-requis : cod1.length = cod2.length
	résultat : le nombre d'éléments communs de cod1 et cod2 se trouvant au même indice
	Par exemple, si cod1 = (1,0,2,0) et cod2 = (0,1,0,0) la fonction retourne 1 (le "0" à l'indice 3)
    */
    public static int nbBienPlaces(int[] cod1,int[] cod2){
        int res = 0;
        for(int n = 0; n < cod1.length; n++){
            if(cod1[n] == cod2[n]){
                res += 1;
            }
        }
        return res;
    }

    //____________________________________________________________
    
    /** pré-requis : les éléments de cod sont des entiers de 0 à nbCouleurs-1
	résultat : un tableau de longueur nbCouleurs contenant à chaque indice i le nombre d'occurrences de i dans cod
	Par exemple, si cod = (1,0,2,0) et nbCouleurs = 6 la fonction retourne (2,1,1,0,0,0)
    */
    public static int[] tabFrequence(int[] cod, int nbCouleurs){
        int[] res = new int[nbCouleurs];
        for(int n = 0; n < cod.length; n++){
            res[cod[n]] += + 1;
        }
        return res;
    }

    //____________________________________________________________
    
    /** pré-requis : les éléments de cod1 et cod2 sont des entiers de 0 à nbCouleurs-1
	résultat : le nombre d'éléments communs de cod1 et cod2, indépendamment de leur position
	Par exemple, si cod1 = (1,0,2,0) et cod2 = (0,1,0,0) la fonction retourne 3 (2 "0" et 1 "1")
    */
    public static int nbCommuns(int[] cod1,int[] cod2, int nbCouleurs){
        int[] tab1 = tabFrequence(cod1, nbCouleurs);
        int[] tab2 = tabFrequence(cod2, nbCouleurs);
        int res = 0;
        for(int n = 0; n < cod1.length; n++){
            if(tab1[n] <= tab2[n]){
                res += tab1[n];
            }
            else{
                res += tab2[n];
            }
        }
        return res;
    }

    //____________________________________________________________
    
    /** pré-requis : cod1.length = cod2.length et les éléments de cod1 et cod2 sont des entiers de 0 à nbCouleurs-1
	résultat : un tableau de 2 entiers contenant à l'indice 0 (resp. 1) le nombre d'éléments communs de cod1 et cod2
	se trouvant  (resp. ne se trouvant pas) au même indice
	Par exemple, si cod1 = (1,0,2,0) et cod2 = (0,1,0,0) la fonction retourne (1,2) : 1 bien placé (le "0" à l'indice 3) 
	et 2 mal placés (1 "0" et 1 "1")
    */
    public static int[] nbBienMalPlaces(int[] cod1,int[] cod2, int nbCouleurs){
        int[] res = new int[2];
        res[0] = nbBienPlaces(cod1, cod2);
        res[1] = nbCommuns(cod1, cod2, nbCouleurs) - res[0];
        return res;
    }


    //____________________________________________________________

    //.........................................................................
    // MANCHEHUMAIN
    //.........................................................................

    /** pré-requis : numMache >= 1
	action : effectue la (numManche)ème manche où l'ordinateur est le codeur et l'humain le décodeur
	(le paramètre numManche ne sert que pour l'affichage)
	résultat : 
            - un nombre supérieur à nbEssaisMax, calculé à partir du dernier essai du joueur humain (cf. sujet), s'il n'a toujours pas trouvé au bout du nombre maximum d'essais 
            - sinon le nombre de codes proposés par le joueur humain          
    */
    public static int mancheHumain(int lgCode, char[] tabCouleurs, int numManche, int nbEssaisMax){
        a 
    }

    //____________________________________________________________

    //...................................................................
    // FONCTIONS COMPLÉMENTAIRES SUR LES CODES POUR LA MANCHE ORDINATEUR
    //...................................................................

    /** pré-requis : les éléments de cod sont des entiers de 0 à tabCouleurs.length-1
	résultat : le code cod sous forme de mot d'après le tableau tabCouleurs
    */
    public static String entiersVersMot(int[] cod, char[] tabCouleurs){
        String res = "";
        for(int n = 0; n < cod.length; n++){
            res += tabCouleurs[cod[n]];
        }
        return res;
    }

    //___________________________________________________________________
    
    /** pré-requis : rep.length = 2
	action : si rep n'est pas  correcte, affiche pourquoi, sachant que rep[0] et rep[1] sont les nombres de bien et mal placés resp.
	résultat : vrai ssi rep est correct, c'est-à-dire rep[0] et rep[1] sont >= 0 et leur somme est <= lgCode
    */
    public static boolean repCorrecte(int[] rep, int lgCode){
        if(rep[0] < 0 || rep[1] < 0){
            System.out.println("Le nombre de bien et de mal placés ne peut pas être négatif");
            return false;
        }
        else if(rep[0] + rep[1] > lgCode){
            System.out.println("Le nombre de bien et de mal placés ne peut pas être supérieur à la longueur du code");
            return false;
        }
        else{
            return true;
        }
    }

    //___________________________________________________________________
    
    /** pré-requis : aucun
	action : demande au joueur humain de saisir les nombres de bien et mal placés, 
                 avec re-saisie éventuelle jusqu'à ce qu'elle soit correcte
	résultat : les réponses du joueur humain dans un tableau à 2 entiers
    */
    public static int[] reponseHumain(int lgCode){
        int[] rep = new int[2];
        try (Scanner scan = new Scanner(System.in)) {
            System.out.println("Entrez le nombre de corrects et bien placés");
            rep[0] = scan.nextInt();
            System.out.println("Entrez le nombre de corrects et mal placés");
            rep[1] = scan.nextInt();
            while(!repCorrecte(rep, lgCode)){
                System.out.println("Les nombres entrés ne sont pas corrects");
                System.out.println("Entrez le nombre de corrects et bien placés");
                rep[0] = scan.nextInt();
                System.out.println("Entrez le nombre de corrects et mal placés");
                rep[1] = scan.nextInt();
            }
        }
        return rep;
    }

    //___________________________________________________________________

     /**CHANGE : action si le code suivant n'existe pas
     *************************************************
        pré-requis : les éléments de cod1 sont des entiers de 0 à nbCouleurs-1
	action/résultat : met dans cod1 le code qui le suit selon l'ordre lexicographique (dans l'ensemble des codes à valeurs  de 0 à nbCouleurs-1) et retourne vrai si ce code existe,
     sinon met dans cod1 le code ne contenant que des "0" et retourne faux
    */
    public static boolean passeCodeSuivantLexico(int[] cod1, int  nbCouleurs){
        a
    }

    //___________________________________________________________________

     /**CHANGE : ajout du paramètre cod1 et modification des spécifications 
     *********************************************************************  
        pré-requis : cod est une matrice à cod1.length colonnes, rep est une matrice à 2 colonnes, 0 <= nbCoups < cod.length, nbCoups < rep.length et les éléments de cod1 et de cod sont des entiers de 0 à nbCouleurs-1
	résultat : vrai ssi cod1 est compatible avec les nbCoups premières lignes de cod et de rep,
             c'est-à-dire que si cod1 était le code secret, les réponses aux nbCoups premières
            propositions de cod seraient les nbCoups premières réponses de rep resp.
   */
   public static boolean estCompat(int [] cod1, int [][] cod,int [][] rep, int nbCoups, int  nbCouleurs){
        //cod = new int[nbCoups][cod1.length];
        //rep = new int[nbCoups][2];
        
    }

    //___________________________________________________________________

     /**CHANGE : renommage de passePropSuivante en passeCodeSuivantLexicoCompat, 
                 ajout du paramètre cod1 et modification des spécifications 
     **************************************************************************      
            pré-requis : cod est une matrice à cod1.length colonnes, rep est une matrice à 2 colonnes, 0 <= nbCoups < cod.length, 
                    nbCoups < rep.length et les éléments de cod1 et de cod sont des entiers de 0 à nbCouleurs-1
	    action/résultat : met dans cod1 le plus petit code (selon l'ordre lexicographique (dans l'ensemble
    des codes à valeurs  de 0 à nbCouleurs-1) qui est à la fois plus grand que
      cod1 selon cet ordre et compatible avec les nbCoups premières lignes de cod et rep si ce code existe,
      sinon met dans cod1 le code ne contenant que des "0" et retourne faux
   */
   public static boolean passeCodeSuivantLexicoCompat(int [] cod1, int [][] cod,int [][] rep, int nbCoups, int  nbCouleurs){
  
    }

    //___________________________________________________________________
    
    // manche Ordinateur

    /** pré-requis : numManche >= 2
	action : effectue la (numManche)ème  manche où l'humain est le codeur et l'ordinateur le décodeur
	(le paramètre numManche ne sert que pour l'affichage)
	résultat : 
            - 0 si le programme détecte une erreur dans les réponses du joueur humain
            - un nombre supérieur à nbEssaisMax, calculé à partir du dernier essai de l'ordinateur (cf. sujet), 
              s'il n'a toujours pas trouvé au bout du nombre maximum d'essais 
            - sinon le nombre de codes proposés par l'ordinateur
    */
    public static int mancheOrdinateur(int lgCode,char[] tabCouleurs, int numManche, int nbEssaisMax) {
  
    }

    //___________________________________________________________________

    //.........................................................................
    // FONCTIONS DE SAISIE POUR LE PROGRAMME PRINCIPAL
    //.........................................................................


    /** pré-requis : aucun
	action : demande au joueur humain de saisir un entier strictement positif, 
                 avec re-saisie éventuelle jusqu'à ce qu'elle soit correcte
	résultat : l'entier strictement positif saisi
    */
    public static int saisirEntierPositif(){
        try(Scanner scan = new Scanner(System.in)){
            int rep = scan.nextInt();
            while(rep <= 0){
                System.out.println("L'entier saisi n'est pas strictement positif");
                rep = scan.nextInt();
            }
            return rep;
        }
    }

    //___________________________________________________________________
    
    /** pré-requis : aucun
	action : demande au joueur humain de saisir un entier pair strictement positif, avec re-saisie éventuelle jusqu'à ce qu'elle soit correcte
	résultat : l'entier pair strictement positif saisi
    */
    public static int saisirEntierPairPositif(){
        try(Scanner scan = new Scanner(System.in)){
            int rep = scan.nextInt();
            while(rep <= 0 && rep%2 != 0){
                System.out.println("L'entier saisi n'est pas strictement positif ou pair");
                rep = scan.nextInt();
            }
            return rep;
        }	
    }

    //___________________________________________________________________
    
    /** pré-requis : aucun
	action : demande au joueur humain de saisir le nombre de couleurs (stricement positif), puis les noms de couleurs aux initiales différentes, avec re-saisie éventuelle jusqu'à ce qu'elle soit correcte
	résultat : le tableau des initiales des noms de couleurs saisis
    */
    public static char[] saisirCouleurs(){
        try(Scanner scan = new Scanner(System.in)){
            System.out.println("Saisissez le nombre de couleurs voulues.");
            int nbCoul = scan.nextInt();
            char[] rep = new char[nbCoul];
            System.out.println("Saisissez " + nbCoul + " couleurs.");
            for(int c = 0; c < nbCoul; c++){
                String coul = scan.nextLine();
                char initiales = coul.charAt(0);
                rep[c] = initiales;
                for(int n = c; n > 0; n--){
                    if(rep[n] == rep[c]){
                        c -= 1;
                        System.out.println("Il ne peut pas y avoir 2 couleurs avec la même initiale");
                    }
                }
            }

            return rep;
        }
    }

    //___________________________________________________________________

    //.........................................................................
    // PROGRAMME PRINCIPAL
    //.........................................................................
    

        /**CHANGE : ajout de : le nombre d'essais maximum doit être strictement positif
        ******************************************************************************
           action : demande à l'utilisateur de saisir les paramètres de la partie (lgCode, tabCouleurs, 
           nbManches, nbEssaisMax), 
	   effectue la partie et affiche le résultat (identité du gagnant ou match nul).
	   La longueur d'un code, le nombre de couleurs et le nombre d'essais maximum doivent être strictement positifs. 
	   Le nombre de manches doit être un nombre pair strictement positif.
	   Les initiales des noms de couleurs doivent être différentes. 
	   Toute donnée incorrecte doit être re-saisie jusqu'à ce qu'elle soit correcte.
    */
    public static void main (String[] args){
	
   
    } // fin main

    //___________________________________________________________________
    
} // fin MasterMindBase
