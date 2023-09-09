import java.util.ArrayList;

public class Toys {
    private ArrayList<Toy> arrtoys = new ArrayList<>();

    public void setToy(String name, int cost) {
        int id = arrtoys.size();
        Toy toy = new Toy(id, name, cost);
        arrtoys.add(toy);
    }

    public void showToys() {
        if(arrtoys.size() == 0) System.out.println("пусто");
        for (Toy toy : arrtoys) {
            System.out.println(toy.getId() + ". " + toy.getName() + " - " + toy.getCost() + "$");
        }
    }

    public String getToyName(int id) {
        return arrtoys.get(id).getName();
    }
    public int getToyCost(int id) {
        return arrtoys.get(id).getCost();
    }

    public boolean checkToyName(String str) {
        for (Toy toy : arrtoys) {
            if (str.equals(toy.getName())) return false;
        }
        return true;
    }

    public void deleteToy(int id_toy) {
        arrtoys.remove(id_toy);
    }

    public int getToysLength() {
        return arrtoys.size();
    }
}
