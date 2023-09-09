public class Toy {
    private int id;
    private String name;
    private int cost;

    public Toy(int id, String name, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getCost() {
        return cost;
    }
}
