import java.io.Serializable;

public class MaterialCost implements Serializable {
    private String material;
    private double cost;

    public MaterialCost(String material, double cost) {
        this.material = material;
        this.cost = cost;
    }

    public String getMaterial() {
        return material;
    }
    public double getCost() {
        return cost;
    }
}
