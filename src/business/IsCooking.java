package business;

public class IsCooking extends Edge{
    public IsCooking(String name) {
        super(name);
    }

    public IsCooking(String name, Integer metric, Recipe dest, Person src) {
        super(name, metric, dest, src);
    }
}
