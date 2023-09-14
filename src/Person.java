import java.util.ArrayList;

public abstract class Person {
    public void showToys(ArrayList<Toy> arrtoys) {
        if(arrtoys.size() == 0) System.out.println("пусто");
        int[] q = {0};
        arrtoys.forEach (item -> {
            System.out.println(q[0]++ + ". " + item.getName() + " - " + item.getCost() + "$");
        });
    }
}
