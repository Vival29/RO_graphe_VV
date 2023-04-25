package business;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Node implements Serializable {
    protected String name;
    protected boolean visited = false;
    protected Integer level;
    protected double djikstraWeight;
    protected Node predecessor;
    protected HashMap<String, Node> tableVPCC;
    protected HashMap<String, Edge> exitingEdges = new HashMap<>();
    protected HashMap<String, Edge> incomingEdges = new HashMap<>();
    public Node(String name) {
        this.name = name;
        this.tableVPCC = new HashMap<>();
    }
    public static Node copy(Node n){

        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream cout = null;
            cout = new ObjectOutputStream(bout);
            cout.writeObject(n);
            byte[] bytes = bout.toByteArray();
            ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
            ObjectInputStream cin = new ObjectInputStream(bin);
            Node clone = (Node) cin.readObject();
            clone.setName(n.getName() + "_copy");
            return clone;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public HashMap<String, Edge> getIncomingEdges() {
        return incomingEdges;
    }
    public void setIncomingEdges(HashMap<String, Edge> incomingEdges) {
        this.incomingEdges = incomingEdges;
    }
    public HashMap<String, Edge> getExitingEdges() {
        return exitingEdges;
    }
    public void setExitingEdges(HashMap<String, Edge> exitingEdges) {
        this.exitingEdges = exitingEdges;
    }

    public double getDjikstraWeight() {
        return djikstraWeight;
    }

    public void setDjikstraWeight(double djikstraWeight) {
        this.djikstraWeight = djikstraWeight;
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    public HashMap<String, Node> getTableVPCC() {
        return tableVPCC;
    }

    public void setTableVPCC(HashMap<String, Node> tableVPCC) {
        this.tableVPCC = tableVPCC;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void addEdge(String name, double metric, Node dest, Node src){
            Edge edge = new Edge(name, metric, dest, src);
            src.exitingEdges.put(name, edge);
            dest.incomingEdges.put(name, edge);
    }
    public void addLike(String name, double metric, Node dest, Node src){
        IsLikes likes = new IsLikes(name, metric, dest, src);
        src.exitingEdges.put(name, likes);
    }

    public void deleteEdge (String name){
        // when the method takes a String in argument
        if(this.exitingEdges.containsKey(name)){
            //delete
            this.exitingEdges.remove(name);
        }
        if(this.incomingEdges.containsKey(name) ){
            this.incomingEdges.remove(name);
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("w++("+ this.getName() +") = {");
        for (Map.Entry e: exitingEdges.entrySet()) {
            sb.append(e.getKey().toString());
            sb.append(e.getValue().toString());
            sb.append(" ");
        }
        sb.append("}");
        sb.append("\n");
        sb.append("w--("+ this.getName() +") = {");
        for (Map.Entry e: incomingEdges.entrySet()) {
            sb.append(e.getKey().toString());
            sb.append(e.getValue().toString());
            sb.append(" ");
        }
        sb.append("}");
        return sb.toString();
    }
}
