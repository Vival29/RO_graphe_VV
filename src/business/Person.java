package business;


import java.util.Date;
import java.util.Map;

public class Person extends Node {
    private Date birthday;
    private String city;

    public Person(String name) {
        super(name);
    }
    public Person(String name, String city){
        super(name);
        this.city = city;
    }

    public void addFriend(String name, int metric, Person destFriend, Person sourceFriend) {
        IsFriend friendship = new IsFriend(name, metric, destFriend, sourceFriend);
        sourceFriend.exitingEdges.put(name, friendship);
        destFriend.incomingEdges.put(name, friendship);
    }
    public void listenToWebSite(String name, int metric, WebSite destSite, Person srcPers){
        IsListeningTo listen = new IsListeningTo(name, metric, destSite, srcPers);
        srcPers.exitingEdges.put(name, listen);
        destSite.incomingEdges.put(name, listen);
    }
    public void addRecipe(String name, int metric, Recipe destRecipe, Person srcPers){
        IsCooking cooking = new IsCooking(name,metric,destRecipe,srcPers);
        srcPers.exitingEdges.put(name, cooking);
        destRecipe.incomingEdges.put(name, cooking);
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName() + " est ami avec :");
        for (Map.Entry e: exitingEdges.entrySet()) {
            sb.append(e.getValue().toString() + ",");
            sb.append(" ");
        }
        return sb.toString();
    }
}
