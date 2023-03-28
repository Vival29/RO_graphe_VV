package business;

import java.util.*;

public class Graph {
    protected String name;
    protected HashMap<String, Node> nodeList = new HashMap<>();
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
    public Node findNode(String name){

        return nodeList.get(name);
    }
    public void addNode(Node node){
        name = node.getName();
        nodeList.put(name, node);
        }
    public void addEdge(String src, String dest, double metric, String name){

        if(findNode(src) == null){
            Node nodeSource = new Node(src);
            this.nodeList.put(src, nodeSource);
        }
        if(findNode(dest) == null ){
            Node nodeDest = new Node(dest);
            this.nodeList.put(dest, nodeDest);
        }
        findNode(src).addEdge(name, metric, findNode(dest), findNode(src));
    }
    //cette méthode est basé sur le fait qu'on a que les arcs sortants dans notre structure de donné.
    public void deleteNode(String name){
        for(Node n: this.nodeList.values()){
            Iterator<Edge> itEdge = n.getExitingEdges().values().iterator();
            while(itEdge.hasNext()){
                Edge edge = itEdge.next();
                if(name == edge.getDest().getName()){
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
        for (Edge inE: findNode(name).getIncomingEdges().values()) {
            this.findNode(inE.getSrc().getName()).getExitingEdges().remove(inE.getName());
        }
        //pour le noeud 'name', on parcourt la liste de ses arc sortants,
        //et pour chaque arc, on prend le noeud dest et depuis ce noeud dest on
        // supprime l'arc entrant qui a le meme noeud
        Iterator<Edge> itEdge = findNode(name).getExitingEdges().values().iterator();
        while(itEdge.hasNext()){
            Edge exE = itEdge.next();
            this.findNode(exE.getDest().getName()).getIncomingEdges().remove(exE.getName());
        }
        this.nodeList.remove(name);
    }
    // méthode qui parcourt le graphe en largeur, en utilisant les arcs sortant uniquement car il y a une direction.
    public List<Node> widthWay (Node start){
        resetNodes();
        LinkedList<Node> mem = new LinkedList<>();
        List<Node> result = new ArrayList<>();
        start.setVisited(true);
        mem.addFirst(start);
        while(!mem.isEmpty()){
            Node liveNode = mem.removeLast();
            result.add(liveNode);
            for(Edge e: liveNode.getExitingEdges().values()){
                Node dest = e.getDest();
                if(!dest.isVisited()){
                    dest.setVisited(true);
                    mem.addFirst(dest);
                }
            }
        }
        return result;
    }
    public List<Node> widthWayLimited (Node start, int maxLevel){
        resetNodes();
        LinkedList<Node> mem = new LinkedList<>();
        List<Node> result = new ArrayList<>();
        start.setVisited(true);
        start.setLevel(0);
        mem.addFirst(start);
        while(!mem.isEmpty()){
            Node liveNode = mem.removeLast();
            result.add(liveNode);
            if(liveNode.getLevel() < maxLevel) {
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
    public List<Node> widthWayLimitedTyped (Node start, int maxLevel, Class EdgeType){
        resetNodes();
        LinkedList<Node> mem = new LinkedList<>();
        List<Node> result = new ArrayList<>();
        start.setVisited(true);
        start.setLevel(0);
         mem.addFirst(start);
        while(!mem.isEmpty()){
            Node liveNode = mem.removeLast();
            result.add(liveNode);
            if(liveNode.getLevel() < maxLevel) {
                for (Edge e : liveNode.getExitingEdges().values()) {
                    if(e.getClass() == EdgeType) {
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
        while(!mem.isEmpty()){
            Node liveNode = mem.pop();
            result.add(liveNode);
            for(Edge e: liveNode.getExitingEdges().values()){
                Node dest = e.getDest();
                if(!dest.isVisited()){
                    dest.setVisited(true);
                    mem.push(dest);
                }
            }
        }
        return result;
    }
    public List<Node> depthWayBetter(Node start, int maxDepth) {
        resetNodes();
        Stack<business.Node> mem = new Stack<>();
        List<business.Node> result = new ArrayList<>();
        start.setVisited(true);
        mem.push(start);
        while(!mem.isEmpty()){
            business.Node liveNode = mem.pop();
            result.add(liveNode);
            for(Edge e: liveNode.getExitingEdges().values()){
                business.Node dest = e.getDest();
                if(!dest.isVisited()){
                    dest.setVisited(true);
                    mem.push(dest);
                }
            }
        }
        return result;
    }
    //gére la réinitialisation des attributs techniques du noeud
    public void resetNodes() {
        for(Node n : nodeList.values()){
            n.setVisited(false);
            n.setLevel(0);

        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----- My graph " + this.getName() +  "-----\n");
        for(Map.Entry n: nodeList.entrySet()){
            sb.append(n.getValue().toString());
            sb.append("\n");
        }
        return sb.toString();
    }



}
