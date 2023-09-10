import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private ArrayList<BasketItem> arrbasket = new ArrayList<>();

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setBasket(int id_toy, int id_size, int id_material) {
        BasketItem basketItem = new BasketItem(id_toy, id_size, id_material);
        arrbasket.add(basketItem);
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void showBasket(ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        int i = 0;
        if(arrbasket.size() == 0) System.out.println("\tкорзина пуста");
        for (BasketItem item : arrbasket) {
            int id_toy = item.getIdToy();
            int id_size = item.getIdSize();
            int id_material = item.getIdMaterial();

            String name = arrtoys.get(id_toy).getName();
            int cost = arrtoys.get(id_toy).getCost();

            double increase = arrsizes.get(id_size).getCost() * arrmaterials.get(id_material).getCost();
            System.out.println("\t" + i++ + ". " + name + ", " + arrsizes.get(id_size).getSize() + ", " + arrmaterials.get(id_material).getMaterial() + ", " + Math.round(cost*increase * 10.0)/10.0 + "$");
        }
    }

    public boolean checkBasketName(int id_toy) {
        for (BasketItem item : arrbasket) {
            if(id_toy == item.getIdToy()) return true;
        }
        return false;
    }

    public int getBasketLength() {
        return arrbasket.size();
    }
    public void deleteBasket() {
        arrbasket.clear();
    }
    public void deleteBasketElement(int num) {
        arrbasket.remove(num);
    }
    public void deleteBasketName(int id_toy) {
        for (BasketItem item : arrbasket) {
            if(id_toy == item.getIdToy()) arrbasket.remove(item);
        }
    }
}
