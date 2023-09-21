import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class User extends Person implements Serializable {
    private int id;
    private String name;
    private ArrayList<BasketItem> arrbasket = new ArrayList<>();

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setBasket(int id_toy, int id_size, int id_material) {
        BasketItem basketItem = new BasketItem(id_toy, id_size, id_material);
        arrbasket.add(basketItem);
    }
    public void addToyToBasket(ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        int length, num, num2, num3;
        System.out.println("Выберите игрушку: ");
        showToys(arrtoys);
        length = arrtoys.size();
        num = Check.checkNumber(0, length - 1);

        System.out.println("Выберите размер игрушки: ");
        num2 = selectToyOption(arrsizes);
        System.out.println("Выберите материал игрушки: ");
        num3 = selectToyOption(arrmaterials);

        int id_toy = arrtoys.get(num).getId();
        setBasket(id_toy, num2, num3);
    }
    public int selectToyOption(ArrayList<? extends SizeMaterialCost> arr) {
        int[] q = {0};
        arr.forEach(item -> {
            System.out.println((q[0]++) + ". " + item.getName() + "  x" + item.getCost() + "$");
        });
        int length = arr.size();
        return Check.checkNumber(0, length - 1);
    }
    public void deleteToyFromBasket(ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        int num, length;
        System.out.println("Выберите какую игрушку удалить: ");
        showBasket(arrtoys, arrsizes, arrmaterials);
        length = getBasketLength();
        num = Check.checkNumber(0, length - 1);
        deleteBasketElement(num);
    }

    public void showBasket(ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        int[] index = {0};
        if(arrbasket.size() == 0) {
            System.out.println("\tкорзина пуста");
            return;
        }
        totalCost(arrtoys, arrsizes, arrmaterials);
        arrbasket.stream().forEach(item -> displayBasketOneItem(item, index, arrtoys, arrsizes, arrmaterials));
        totalCost(arrtoys, arrsizes, arrmaterials);
    }
    public void displayBasketOneItem(BasketItem item, int[] i, ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        int id_size = item.getIdSize();
        int id_material = item.getIdMaterial();

        int id_toy = getIdToyByIdToyBasket(arrtoys, item.getIdToy());
        String name = arrtoys.get(id_toy).getName();
        int cost = arrtoys.get(id_toy).getCost();

        double increase = arrsizes.get(id_size).getCost() * arrmaterials.get(id_material).getCost();
        System.out.println("\t" + i[0] + ". " + name + ", " + arrsizes.get(id_size).getName() + ", " + arrmaterials.get(id_material).getName() + ", " + Math.round(cost*increase * 10.0)/10.0 + "$");
        i[0]++;
    }
    public int getIdToyByIdToyBasket(ArrayList<Toy> arrtoys, int idToyBasket) {
        try {
            boolean error = true;
            for (Toy item: arrtoys) {
                if (idToyBasket == item.getId()) {
                    return item.getId();
                }
            }
            if (error) throw new MyException("Ошибка. В корзине есть товар которого не существует.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }

    public void showBasketSortIncrease(ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        int[] index = {0};
        List<BasketItem> sortedBasket = arrbasket.stream().sorted((left, right) -> basketSort(arrtoys, arrsizes, arrmaterials, left, right)).collect(Collectors.toList());
        synchronized (this) {
            System.out.println("По возрастанию цены:");
            sortedBasket.stream().forEach(item -> displayBasketOneItem(item, index, arrtoys, arrsizes, arrmaterials));
        }
    }
    public void showBasketSortDecrease(ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        int[] index = {0};
        List<BasketItem> sortedBasket = arrbasket.stream().sorted((left, right) -> basketSort(arrtoys, arrsizes, arrmaterials, right, left)).collect(Collectors.toList());
        synchronized (this) {
            System.out.println("По убыванию цены:");
            sortedBasket.stream().forEach(item -> displayBasketOneItem(item, index, arrtoys, arrsizes, arrmaterials));
        }
    }
    public int basketSort(ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials, BasketItem left, BasketItem right) {
        int costL = 0, costR = 0;
        for (Toy item: arrtoys) {
            if (left.getIdToy() == item.getId()) costL = item.getCost();
            if (right.getIdToy() == item.getId()) costR = item.getCost();
        }
        double increaseL = arrsizes.get(left.getIdSize()).getCost() * arrmaterials.get(left.getIdMaterial()).getCost();
        double increaseR = arrsizes.get(right.getIdSize()).getCost() * arrmaterials.get(right.getIdMaterial()).getCost();
        return Double.compare(costL*increaseL, costR*increaseR);
    }

    public boolean checkBasketName(int id_toy) {
        return arrbasket.stream().anyMatch(item -> id_toy == item.getIdToy());
    }
    public int getBasketLength() {
        return (int) arrbasket.stream().count();
    }
    public void deleteBasket() {
        arrbasket.clear();
    }
    public void deleteBasketElement(int num) {
        arrbasket.remove(num);
    }
    public void deleteBasketName(int id_toy) {
        ArrayList<BasketItem> itemsDelete = new ArrayList<>();
        arrbasket.forEach(item -> {
            if (id_toy == item.getIdToy()) {
                itemsDelete.add(item);
            }
        });
        arrbasket.removeAll(itemsDelete);
    }

    public void totalCost(ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        double totalCost = arrbasket.stream().mapToDouble(item -> {
            int id_toy = item.getIdToy();
            double increase = arrsizes.get(item.getIdSize()).getCost() * arrmaterials.get(item.getIdMaterial()).getCost();
            int cost = 0;
            for (Toy item2: arrtoys) {
                if (id_toy == item2.getId()) cost = item2.getCost();
            }
            return cost * increase;
        }).sum();
        System.out.println("\tИтого: " + Math.round(totalCost * 10.0)/10.0 + "$");
    }

    public void displayBasketLimitItem(int num, ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        int[] index = {0};
        System.out.println("Первые " + num + " товара в корзине:");
        arrbasket.stream().limit(num).forEach(item -> displayBasketOneItem(item, index, arrtoys, arrsizes, arrmaterials));
    }
    public void displayBasketSize(String str, ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        int[] index = {0};
        arrbasket.stream().filter(el -> str.equals(arrsizes.get(el.getIdSize()).getName())).forEach(item -> displayBasketOneItem(item, index, arrtoys, arrsizes, arrmaterials));
    }
    public void displayBasketGrouping(ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        Map<String, List<BasketItem>> map = arrbasket.stream().collect(Collectors.groupingBy(el -> arrsizes.get(el.getIdSize()).getName()));
        for (Map.Entry<String, List<BasketItem>> entry : map.entrySet()) {
            int[] i = {0};
            System.out.println(entry.getKey() + ":");
            entry.getValue().stream().forEach(el -> displayBasketOneItem(el, i, arrtoys, arrsizes, arrmaterials));
        }
    }
}