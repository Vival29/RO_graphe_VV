package business;

public class IsListeningTo extends Edge{
    public IsListeningTo(String name) {
        super(name);
    }

    public IsListeningTo(String name, double metric, WebSite dest, Person src) {
        super(name, metric, dest, src);
    }
}
