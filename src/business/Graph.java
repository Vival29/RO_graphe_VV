package business;

import java.io.*;
import java.util.*;

import static business.Node.copyNode;

public class Graph implements Serializable {
    protected String name;
    protected HashMap<String, Node> nodeList = new HashMap<>();
    protected HashMap<Integer, List<Node>> miseEnRang = new HashMap<>();

    public Graph(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(HashMap<String, Node> nodeList) {
        this.nodeList = nodeList;
    }

    public HashMap<Integer, List<Node>> getMiseEnRang() {
        return miseEnRang;
    }

    public void setMiseEnRang(HashMap<Integer, List<Node>> miseEnRang) {
        this.miseEnRang = miseEnRang;
    }

    public Node findNode(String name) {

        return nodeList.get(name);
    }

    public void addNode(Node node) {
        name = node.getName();
        nodeList.put(name, node);
    }

    public void addEdge(String src, String dest, double metric, String name) {

        if (findNode(src) == null) {
            Node nodeSource = new Node(src);
            this.nodeList.put(src, nodeSource);
        }
        if (findNode(dest) == null) {
            Node nodeDest = new Node(dest);
            this.nodeList.put(dest, nodeDest);
        }
        findNode(src).addEdge(name, metric, findNode(dest), findNode(src));
    }

    //cette méthode est basé sur le fait qu'on a que les arcs sortants dans notre structure de donné.
    public void deleteNode(String name) {
        for (Node n : this.nodeList.values()) {
            Iterator<Edge> itEdge = n.getExitingEdges().values().iterator();
            while (itEdge.hasNext()) {
                Edge edge = itEdge.next();
                if (name == edge.getDest().getName()) {
                    itEdge.remove();
                }
            }
        }
        this.nodeList.remove(name);
    }

    //cette méthode est basé sur le fait qu'on a la liste entrante et sortante pour chaque noeud
    public void deleteNodeBI(String name) {
        //pour le noeud 'name', on parcourt la liste de ses arc entrants,
        //et pour chaque arc, on prend le noeud src et depuis ce noeud src on
        // supprime l'arc avec deletEdge, qui lui check si il doit supprimer dans entrant ou sortant ou les deux --> erreur vient de là
        // il faut supprimer spécifiquement sur les listes entrantes quand on parcourt les liste sortantes et inversement
        for (Edge inE : findNode(name).getIncomingEdges().values()) {
            this.findNode(inE.getSrc().getName()).getExitingEdges().remove(inE.getName());
        }
        //pour le noeud 'name', on parcourt la liste de ses arc sortants,
        //et pour chaque arc, on prend le noeud dest et depuis ce noeud dest on
        // supprime l'arc entrant qui a le meme noeud
        Iterator<Edge> itEdge = findNode(name).getExitingEdges().values().iterator();
        while (itEdge.hasNext()) {
            Edge exE = itEdge.next();
            this.findNode(exE.getDest().getName()).getIncomingEdges().remove(exE.getName());
        }
        this.nodeList.remove(name);
    }

    // méthode qui parcourt le graphe en largeur, en utilisant les arcs sortant uniquement car il y a une direction.
    public List<Node> widthWay(Node start) {
        resetNodes();
        LinkedList<Node> mem = new LinkedList<>();
        List<Node> result = new ArrayList<>();
        start.setVisited(true);
        mem.addFirst(start);
        while (!mem.isEmpty()) {
            Node liveNode = mem.removeLast();
            result.add(liveNode);
            for (Edge e : liveNode.getExitingEdges().values()) {
                Node dest = e.getDest();
                if (!dest.isVisited()) {
                    dest.setVisited(true);
                    mem.addFirst(dest);
                }
            }
        }
        return result;
    }

    public List<Node> widthWayLimited(Node start, int maxLevel) {
        resetNodes();
        LinkedList<Node> mem = new LinkedList<>();
        List<Node> result = new ArrayList<>();
        start.setVisited(true);
        start.setLevel(0);
        mem.addFirst(start);
        while (!mem.isEmpty()) {
            Node liveNode = mem.removeLast();
            result.add(liveNode);
            if (liveNode.getLevel() < maxLevel) {
                for (Edge e : liveNode.getExitingEdges().values()) {
                    Node dest = e.getDest();
                    if (!dest.isVisited()) {
                        dest.setVisited(true);
                        dest.setLevel(liveNode.getLevel() + 1);
                        mem.addFirst(dest);
                    }
                }
            }
        }
        return result;
    }

    public List<Node> widthWayLimitedTyped(Node start, int maxLevel, Class EdgeType) {
        resetNodes();
        LinkedList<Node> mem = new LinkedList<>();
        List<Node> result = new ArrayList<>();
        start.setVisited(true);
        start.setLevel(0);
        mem.addFirst(start);
        while (!mem.isEmpty()) {
            Node liveNode = mem.removeLast();
            result.add(liveNode);
            if (liveNode.getLevel() < maxLevel) {
                for (Edge e : liveNode.getExitingEdges().values()) {
                    if (e.getClass() == EdgeType) {
                        Node dest = e.getDest();
                        if (!dest.isVisited()) {
                            dest.setVisited(true);
                            dest.setLevel(liveNode.getLevel() + 1);
                            mem.addFirst(dest);
                        }
                    }
                }
            }
        }
        return result;
    }

    public List<Node> depthWay(Node start) {
        resetNodes();
        Stack<Node> mem = new Stack<>();
        List<Node> result = new ArrayList<>();
        start.setVisited(true);
        mem.push(start);
        while (!mem.isEmpty()) {
            Node liveNode = mem.pop();
            result.add(liveNode);
            for (Edge e : liveNode.getExitingEdges().values()) {
                Node dest = e.getDest();
                if (!dest.isVisited()) {
                    dest.setVisited(true);
                    mem.push(dest);
                }
            }
        }
        return result;
    }

    //algo Dijkstra, qui permet de créer les tables VPCC sur lesquesl l'algo du plus court chemin se base pour
    //calculer le plus court chemin entre un noeud de départ et un d'arrivé.
    private void dijkstra(Node start) {
        //quand on applique Djikstra c'est qu'on doit remplacer la table VPCC du noeud courant car le graphe a changé
        //donc il faut reset tous les éléments du noeud courant.
        this.resetNodes();
        Node current;
        Node dest;
        double currentWeight;
        //la mémoire dans laquelle on reprend toujours l'élément avec la valeur d'Edge la plus petite.
        ArrayList<Node> mem = new ArrayList<>();
        //on commence par mettre le poid djikstra à 0 sur le noeud courant, car c'est notre noeu de départ.
        start.setDjikstraWeight(0);
        mem.add(start);

        while (!mem.isEmpty()) {
            //on ordre la mémoire pour avoir en premier l'élément avec DjikstraWeight le plus petit.
            mem.sort(new DijkstraNodeComparator());
            current = (Node) mem.remove(0);
            //la copie du noeud qui est faite ici permet par récursion de copier tous les noeuds
            Node copy = current.copyNode(current);

            start.getTableVPCC().put(copy.getName(), copy);
            //pour chaque arc sortant on check le djikstra weight et s'il est encore infini donc pas découvert on entre
            //dans la premiere boucle if, et on le met à jour, sinon on le met à jour en supprimant celui qu'on avait
            // dans la mémoire et en mettant le nouveau, avec le nouveau djikstraWeight
            for (Edge e : current.getExitingEdges().values()) {
                dest = e.getDest();
                currentWeight = current.getDjikstraWeight() + e.getMetric();

                if (dest.getDjikstraWeight() == Integer.MAX_VALUE) {
                    dest.setDjikstraWeight(currentWeight);
                    dest.setPredecessor(current);
                    mem.add(dest);
                }
                if (currentWeight < dest.getDjikstraWeight()) {
                    mem.remove(dest);
                    dest.setDjikstraWeight(currentWeight);
                    dest.setPredecessor(current);
                    mem.add(dest);
                }

            }
        }


    }

    //méthode qui utilise Djikstra pour calculer le plus court chemin entre deux Noeud en utilisant
    // la table VPCC du noeud start
    public void vectorShortestPath(String nsrc, String ndest) {
        Node src = findNode(nsrc);

        System.out.println("The shortest path between " + nsrc + " and " + ndest);
        if (src.getTableVPCC().size() < 1) {
            src.setTableVPCC(new HashMap<>());
            dijkstra(src);
        }
        Node destVPCC = src.getTableVPCC().get(ndest);
        if (destVPCC == null) {
            System.out.println("No path between " + nsrc + " and " + ndest);
        } else {
            System.out.println("The shortest path length is " + destVPCC.getDjikstraWeight());
        }
        LinkedList<Node> path = new LinkedList<Node>();
        //faire l'affichage des node en linked list, parcourir.

        while (destVPCC != null) {
            path.addFirst(destVPCC);
            destVPCC = src.getTableVPCC().get(destVPCC.getName()).getPredecessor();
        }
        //affichage
        StringBuilder sb = new StringBuilder();
        sb.append("this is the way: ");
        for (Node n : path) {
            sb.append(" --> ").append(n.getName());
        }
        System.out.println(sb.toString());
    }

    public void triTopologique() throws IOException, ClassNotFoundException {
        Graph copy = Graph.copyGraph(this);
        this.miseEnRang.clear();
        int rang = 0;
        boolean cycle = false;
        boolean end = false;
        //set tous les noeuds avec leurs valeurs pour degIn, degOut et degree.
        for (Node setUp : copy.nodeList.values()) {
            setUp.calculateScale();
        }
        do {
            ArrayList<Node> nodesCurrentScale = new ArrayList<>();
            //on boucle sur les noeuds de la copie du graphe
            for (Node n : copy.nodeList.values()) {
                // si ils n'ont pas d'arcs entrants, ils sont ajouté au rang d ela liste current
                if (n.getScaleIn() == 0) {
                    //on ajoute le vrai noeud à la liste, pas celui de la copie
                    Node currentNode = this.findNode(n.getName());
                    nodesCurrentScale.add(currentNode);
                }
            }
            //une fois parcourus chaque noeud pour le rang n, on change les valeur de degin des noeud de
            // destinations du noeud mis a la liste. On "supprime" ses arcs venant du noeud d'origine.
            for (Node scaleNode : nodesCurrentScale) {
                Node m = copy.findNode(scaleNode.getName());
                //pour indiquer que ce noeau a déja été pris, au lieu de le supprimer on set son degIn à l'infini.
                m.setScaleIn(Integer.MAX_VALUE);
                //on verifie qu'on est pas arrivé au bout du graphe.
                if (m.getScaleOut() == 0) {
                    //si c'est le cas, car ce noeud ne comporte pas d'arc sortant (degOut == 0)
                    // alors on set end à true pour sortir de la boucle
                    end = true;
                }
                //pour chaque noeud de la liste a mettre dans misEnRang, on decrémente le degIn du noeud de destination.
                for (Edge e : m.getExitingEdges().values()) {
                    Node dest = e.getDest();
                    Integer i = dest.getScaleIn();
                    dest.setScaleIn(i - 1);
                }
            }
            //on met la liste nodesCurrentScale dans la map avec le rang en index.
            this.miseEnRang.put(rang, nodesCurrentScale);
            rang++;
            //on check si on est dans un cycle, si on fait un tour sur tous les noeud et que aucun n'est à zero degIn.
            if ((rang < nodesCurrentScale.size()) && (copy.getNodeList().isEmpty() == false)) {
                cycle = true;
                //Le cycle peut arriver plus tard dans l'algo et du coup on ne veut pas garder
                // le début de mise en rang. ce nest pas possible car c'est cyclique.
                this.miseEnRang.clear();
            }
        } while (cycle == false && end == false);
    }

    public static Graph copyGraph(Graph g) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream cout = new ObjectOutputStream(bout);
            cout.writeObject(g);
            byte[] bytes = bout.toByteArray();
            ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
            ObjectInputStream cin = new ObjectInputStream(bin);
            return (Graph) cin.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteTablesVPCC() {
        for (Node n : nodeList.values()) {
            n.setTableVPCC(null);
        }
    }

    //gére la réinitialisation des attributs techniques du noeud
    public void resetNodes() {
        for (Node n : nodeList.values()) {
            n.setVisited(false);
            n.setLevel(0);
            n.setPredecessor(null);
            n.setDjikstraWeight(Integer.MAX_VALUE);

        }
    }

    //méthode qui set les attributs scaleIn et scaleOut (et auto scale) de tous les noeuds du graphe avec
    // uniquement une liste d'arc sortant!
    /*public void setScaleNodes(){
        for (Node n:nodeList.values()) {
            for (Edge e:n.getExitingEdges().values()){
                n.setScaleOut(n.getExitingEdges().size());
                Node dest = e.getDest();
                dest.setScaleIn(dest.getScaleIn() + 1);
            }
        }

    }*/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName() + "\n");
        sb.append("Liste des Noeuds: \n");
        for (Node n : nodeList.values()) {
            sb.append(n.toString());
            sb.append("-->");
        }
        return sb.toString();
    }
    //Affichage selon liste d'incidence
    /*@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----- My graph " + this.getName() +  "-----\n");
        for(Map.Entry n: nodeList.entrySet()){
            sb.append(n.getValue().toString());
            sb.append("\n");
        }
        return sb.toString();
    }*/


}
