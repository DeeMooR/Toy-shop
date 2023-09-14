import java.io.Serializable;

public class MaterialCost implements SizeMaterialCost, Serializable {
    private String material;
    private double cost;

    public MaterialCost(String material, double cost) {
        this.material = material;
        this.cost = cost;
    }

    public String getName() {
        return material;
    }
    public double getCost() {
        return cost;
    }
}
