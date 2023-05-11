package business;

import java.io.Serializable;

public class Edge implements Serializable {
    protected String name;
    protected Integer metric;
    protected Node dest;
    protected Node src;
    public Edge(String name) {
        this.name = name;
    }
    public Edge(String name, Integer metric, Node dest, Node src) {
        this.name = name;
        this.metric = metric;
        this.dest = dest;
        this.src = src;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMetric() {
        return metric;
    }

    public void setMetric(Integer metric) {
        this.metric = metric;
    }
    public Node getSrc() {
        return src;
    }

    public void setSrc(Node src) {
        this.src = src;
    }
    public Node getDest() {
        return dest;
    }

    public void setDest(Node dest) {
        this.dest = dest;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName());
        return sb.toString();
    }
    /*@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(" + this.src.getName() + ", " + this.dest.getName() + ", " + this.metric + ")" );
        return sb.toString();
    }*/
}
