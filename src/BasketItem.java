import java.io.Serializable;
import java.util.ArrayList;

public class BasketItem implements Serializable {
    private int id_toy;
    private int id_size;
    private int id_material;

    public BasketItem(int id_toy, int id_size, int id_material) {
        this.id_toy = id_toy;
        this.id_size = id_size;
        this.id_material = id_material;
    }

    public int getIdToy() {
        return id_toy;
    }
    public int getIdSize() {
        return id_size;
    }
    public int getIdMaterial() {
        return id_material;
    }
}
