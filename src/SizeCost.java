import java.io.Serializable;

public class SizeCost implements SizeMaterialCost, Serializable {
    private String size;
    private double cost;

    public SizeCost(String size, double cost) {
        this.size = size;
        this.cost = cost;
    }

    public String getName() {
        return size;
    }
    public double getCost() {
        return cost;
    }
}
