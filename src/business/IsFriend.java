package business;

public class IsFriend extends Edge {
    public IsFriend(String name) {
        super(name);
    }

    public IsFriend(String name, Integer metric, Person dest, Person src) {
        super(name, metric, dest, src);
    }
}
