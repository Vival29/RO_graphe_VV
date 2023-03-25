package business;

public class IsLikes extends Edge {

    public IsLikes(String name) {
        super(name);
    }

    public IsLikes(String name, double metric, Node dest, Node src) {
        super(name, metric, dest, src);
    }
}
