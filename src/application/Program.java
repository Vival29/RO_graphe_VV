package application;
import business.*;

public class Program {
    public static void main(String[] args) {

        //new Program().createGraph();
        //new Program().delete1Node();
        //new Program().parcours();
        //new Program().parcoursBetter();
        //new Program().parcoursLimitedTyped();
        new Program().exerciceApplicatifTypedLimited();

    }
    public void createGraph(){
        Graph g1 = new Graph("G1");
        g1.addEdge("X1","X2",7,"u1");
        g1.addEdge("X2","X1",6,"u2");
        g1.addEdge("X1","X3",2,"u3");
        g1.addEdge("X2","X3",9,"u4");
        g1.addEdge("X3","X3",9,"u5");
        g1.addEdge("X1","X3",4,"u6");

        System.out.println(g1);
    }
    public void delete1Node(){
        Graph g1 = new Graph("G1");
        g1.addEdge("X1","X2",7,"u1");
        g1.addEdge("X2","X1",6,"u2");
        g1.addEdge("X1","X3",2,"u3");
        g1.addEdge("X2","X3",9,"u4");
        g1.addEdge("X3","X3",9,"u5");
        g1.addEdge("X1","X3",4,"u6");
        System.out.println(g1);

        System.out.println("Avec X2 supprime:");
        g1.deleteNodeBI("X2");
        System.out.println(g1);
    }
    public void parcours(){
        Graph g2 = new Graph("G2");
        g2.addEdge("A", "C", 0, "u1");
        g2.addEdge("A", "B", 0, "u2");
        g2.addEdge("A", "D", 0, "u3");
        g2.addEdge("B", "D", 0, "u4");
        g2.addEdge("C", "D", 0, "u5");
        g2.addEdge("D", "E", 0, "u6");
        g2.addEdge("C", "E", 0, "u7");


        System.out.println("Parcours largeur: ");
        StringBuilder sb = new StringBuilder();
        for(Node n: g2.widthWay(g2.findNode("A"))){
            sb.append("-->" + n.getName());
        }
        System.out.println(sb.toString());

        System.out.println("Parcours profondeur: ");
        StringBuilder sb1 = new StringBuilder();
        for(Node n: g2.depthWay(g2.findNode("A"))){
            sb1.append("-->" + n.getName());
        }
        System.out.println(sb1.toString());
    }
    public void parcoursBetter(){
        Graph g2 = new Graph("G3");
        g2.addEdge("A", "C", 0, "u1");
        g2.addEdge("A", "B", 0, "u2");
        g2.addEdge("A", "D", 0, "u3");
        g2.addEdge("B", "D", 0, "u4");
        g2.addEdge("C", "D", 0, "u5");
        g2.addEdge("D", "E", 0, "u6");
        g2.addEdge("C", "E", 0, "u7");
        g2.addEdge("E","F",0,"u8");
        g2.addEdge("F", "G",0,"u9");

        System.out.println("Parcours largeur limité: ");
        StringBuilder sb = new StringBuilder();
        for(Node n: g2.widthWayLimited(g2.findNode("A"), 3)){
            sb.append("-->" + n.getName());
        }
        System.out.println(sb.toString());

    }
    public void parcoursLimitedTyped(){
        Graph grapheAmitie= new Graph("amitie");
        Person jean= new Person("Jean", "Neuchâtel");
        Person paul= new Person("Paul", "Bienne");
        Person carlos= new Person("Carlos", "CHX");
        Person julie= new Person("Julie", "CHX");

        grapheAmitie.addNode(jean);
        grapheAmitie.addNode(paul);
        grapheAmitie.addNode(carlos);
        grapheAmitie.addNode(julie);


        //System.out.println("test type " + jean.getClass());
        jean.addFriend("a1",34,paul, jean);
        jean.addFriend("a2",25, carlos,jean);
        carlos.addFriend("a3",25,julie,carlos);
        jean.addFriend("a4",5, carlos, jean);

        jean.addLike("l1",2,new Recipe("lasagna"), jean);
        jean.addLike("l2", 1, new WebSite("Netflix"), jean);
        jean.addLike("l3", 1, julie, jean);

        jean.listenToWebSite("w1", 3, new WebSite("spotify"), jean);
        jean.listenToWebSite("w2", 3, new WebSite("Applemusic"), jean);

        jean.addRecipe("r1", 7, new Recipe("fajitas"), jean );
        jean.addRecipe("r2", 8, new Recipe("filet mignon"), jean );
        jean.addRecipe("r3", 9, new Recipe("pizza"), jean );

        // grapheAmitie.ParcoursEnLargeurLimite(grapheAmitie.rechercheNoeud("Jean"),EstAmi.class,2);
        grapheAmitie.toString();

        System.out.println("Parcours largeur limite et type amitie: ");
        StringBuilder sb = new StringBuilder();
        for(Node n: grapheAmitie.widthWayLimitedTyped(grapheAmitie.findNode("Jean"), 3, IsFriend.class)){
            sb.append("-->" + n.getName());
        }
        System.out.println(sb.toString());

        System.out.println("Parcours largeur limite et type recette: ");
        StringBuilder sb1 = new StringBuilder();
        for(Node n: grapheAmitie.widthWayLimitedTyped(grapheAmitie.findNode("Jean"), 3, IsCooking.class)){
            sb1.append("-->" + n.getName());
        }
        System.out.println(sb1.toString());

        System.out.println("Parcours largeur limite et type like: ");
        StringBuilder sb2 = new StringBuilder();
        for(Node n: grapheAmitie.widthWayLimitedTyped(grapheAmitie.findNode("Jean"), 3, IsLikes.class)){
            sb2.append("-->" + n.getName());
        }
        System.out.println(sb2.toString());

        System.out.println("Parcours largeur limite et type listen: ");
        StringBuilder sb3 = new StringBuilder();
        for(Node n: grapheAmitie.widthWayLimitedTyped(grapheAmitie.findNode("Jean"), 3, IsListeningTo.class)){
            sb3.append("-->" + n.getName());
        }
        System.out.println(sb3.toString());

    }
    public void exerciceApplicatifTypedLimited(){
        Graph ex= new Graph("exTypeLimite");

        Person paul= new Person("Paul", "Neuchatel");
        Person jean= new Person("Jean", "Neuchatel");
        Person alfred= new Person("Alfred", "Lausanne");
        Person julie= new Person("Julie", "Cernier");
        Person lucie= new Person("Lucie", "Neuchatel");
        Person albert= new Person("Albert", "Lausanne");

        ex.addNode(paul);
        ex.addNode(jean);
        ex.addNode(alfred);
        ex.addNode(julie);
        ex.addNode(lucie);
        ex.addNode(albert);

        WebSite netflix = new WebSite("Netflix");
        WebSite amazonPrime = new WebSite("Amazon Prime Video");
        WebSite disney = new WebSite("Disney +");

        ex.addNode(netflix);
        ex.addNode(amazonPrime);
        ex.addNode(disney);

        paul.addFriend("a1", 0, lucie, paul);
        paul.addFriend("a2", 0, jean, paul);
        paul.addFriend("a3", 0, julie, paul);

        jean.addFriend("a4", 0, alfred, jean);

        julie.addFriend("a5", 0, albert, julie);

        paul.listenToWebSite("u1", 0, amazonPrime,paul);
        paul.listenToWebSite("u2", 0, netflix,paul);

        lucie.listenToWebSite("u3", 0, amazonPrime,lucie);
        lucie.listenToWebSite("u4", 0, netflix,lucie);

        albert.listenToWebSite("u5", 0, amazonPrime, albert);
        albert.listenToWebSite("u6", 0, disney, albert);

        alfred.listenToWebSite("u7", 0, netflix, alfred);

        System.out.println("Q1: Lister tous les sites de streaming regardes par Paul: ");
        StringBuilder sb = new StringBuilder();
        for(Node n: ex.widthWayLimitedTyped(ex.findNode("Paul"), 3, IsListeningTo.class)){
            sb.append("--> " + n.getName());
        }
        System.out.println(sb.toString());
        // on peut vérifier si la liste des exiting edges contient un edge de classe isWatching avec u strem
        // on pourrait réutiliser la méthode de parcours en largeur typé, avec niveau 1 isListeningTo et dnas le cas ou il y a on ajoute au sb
        System.out.println("Q2: Donner tous les amis de Paul jusqu'au 2 eme niveau qui regarde un site de streaming ");
        StringBuilder sb1 = new StringBuilder();

        for(Node n: ex.widthWayLimitedTyped(ex.findNode("Paul"), 2, IsFriend.class)){
            if(n.getExitingEdges().values().stream().anyMatch(edge -> edge instanceof IsListeningTo)){
                sb1.append("--> " + n.getName());
            }
        }
        System.out.println(sb1.toString());

        System.out.println("Q3: Lister tous les amis(1er niv) de Paul qui habitent à NE et qui regarde Amazon");
        StringBuilder sb2 = new StringBuilder();

        //commment acceder aux méthodes de Person, alors qu'on boucle sur les nodes?
        //comment accéder au filtre que le noeud de dest doit etre amazon?
        for(Node n: ex.widthWayLimitedTyped(ex.findNode("Paul"), 1, IsFriend.class)) {
                if (((Person) n).getCity().equals("Neuchatel")) { //vérifier que la personne ai un attribut city "neuchatel"
                    if (ex.widthWayLimitedTyped(ex.findNode(n.getName()), 1, IsListeningTo.class).contains(amazonPrime)){
                        sb2.append("--> " + n.getName());
                    }
                }
        }
        System.out.println(sb2.toString());

    }
}