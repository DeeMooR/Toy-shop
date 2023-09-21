import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends Person {
    Scanner scan = new Scanner(System.in);

    public void showUsers(ArrayList<User> arrusers, ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        if(arrusers.size() == 0) System.out.println("пусто");
        for (User item : arrusers) {
            System.out.println(item.getId() + ". " + item.getName());
            item.showBasket(arrtoys, arrsizes, arrmaterials);
        }
    }

    public void addToy(ArrayList<Toy> arrtoys) {
        System.out.println("Введите название игрушки: ");
        String fStr = scan.nextLine();
        Boolean bool = arrtoys.stream().anyMatch(item -> fStr.equals(item.getName()));
        if (!bool) {
            System.out.println("Введите стоимость игрушки: ");
            int num = Check.checkInt();
            int id = 0;
            if (arrtoys.size() > 0) id = arrtoys.get(arrtoys.size() - 1).getId() + 1;
            Toy toy = new Toy(id, fStr, num);
            arrtoys.add(toy);
            System.out.println("Игрушка успешно добавлена.");
        } else {
            System.err.println("Такая игрушка уже добавлена.");
        }
    }

    public void deleteToy(ArrayList<User> arrusers, ArrayList<Toy> arrtoys) {
        int num, length;
        Boolean bool = false;
        System.out.println("Выберите какую игрушку удалить: ");
        showToys(arrtoys);
        if (arrtoys.size() == 0) return;
        length = arrtoys.size();
        num = Check.checkNumber(-1, length - 1);

        for (User item: arrusers) {
            bool = item.checkBasketName(arrtoys.get(num).getId());
            if (bool) {
                deleteToyFromBasket(arrusers, arrtoys);
                break;
            }
        }
        if (!bool) {
            arrtoys.remove(num);
            System.out.println("Игрушка успешно удалена.");
        }
    }
    public void deleteToyFromBasket(ArrayList<User> arrusers, ArrayList<Toy> arrtoys) {
        System.out.println("Данная игрушка лежит в корзине пользователя. \nВсё равно удалить игрушку? \n\t1. Да \n\t2. Отмена");
        int num = Check.checkNumber(1, 2);
        if (num == 1) {
            for (User item2: arrusers) {
                item2.deleteBasketName(arrtoys.get(num).getId());
            }
            arrtoys.remove(num);
            System.out.println("Игрушка удалена из общего списка и корзин пользователей.");
        }
    }
}
