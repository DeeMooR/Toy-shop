import java.io.Serializable;

public class SizeCost implements Serializable {
    private String size;
    private double cost;

    public SizeCost(String size, double cost) {
        this.size = size;
        this.cost = cost;
    }

    public String getSize() {
        return size;
    }
    public double getCost() {
        return cost;
    }
}
