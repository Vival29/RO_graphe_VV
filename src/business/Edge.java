package business;
public class Edge {
    protected String name;
    protected double metric;
    protected Node dest;
    protected Node src;
    public Edge(String name) {
        this.name = name;
    }
    public Edge(String name, double metric, Node dest, Node src) {
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

    public double getMetric() {
        return metric;
    }

    public void setMetric(double metric) {
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
        sb.append("(" + this.src.getName() + ", " + this.dest.getName() + ", " + this.metric + ")" );
        return sb.toString();
    }
}
