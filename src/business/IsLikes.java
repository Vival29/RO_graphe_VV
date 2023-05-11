package business;

public class IsLikes extends Edge {

    public IsLikes(String name) {
        super(name);
    }

    public IsLikes(String name, Integer metric, Node dest, Node src) {
        super(name, metric, dest, src);
    }
}
