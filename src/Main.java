import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int k, exit, num, num2, num3, i, length, userId = 0;
        String str;
        Boolean bool;

        Check check = new Check();
        ArrayList<User> arrusers = new ArrayList<>();
        ArrayList<Toy> arrtoys = new ArrayList<>();

        Toy toy = new Toy(0, "Мяч", 12);
        arrtoys.add(toy);
        toy = new Toy(1, "Кукла", 24);
        arrtoys.add(toy);
        toy = new Toy(2, "Самолёт", 36);
        arrtoys.add(toy);
        toy = new Toy(3, "Машина", 48);
        arrtoys.add(toy);

        ArrayList<SizeCost> arrsizes = new ArrayList<>();
        arrsizes.add(new SizeCost("S", 1.0));
        arrsizes.add(new SizeCost("M", 1.2));
        arrsizes.add(new SizeCost("L", 1.4));
        arrsizes.add(new SizeCost("XL", 1.6));

        ArrayList<MaterialCost> arrmaterials = new ArrayList<>();
        arrmaterials.add(new MaterialCost("Пластик", 1.0));
        arrmaterials.add(new MaterialCost("Плюшевая", 1.2));
        arrmaterials.add(new MaterialCost("Металл", 1.4));

        while(true) {
            exit = 0;
            System.out.println("Меню: \n\t1 - Админ \n\t2 - Пользователь \n\t0 - Выход из программы");
            k = check.checkNumber(2);

            switch (k) {
                case 1:
                    while (true) {
                        System.out.println("Админ \n\t1 - Пользователи \n\t2 - Игрушки \n\t3 - Добавить игрушку \n\t4 - Удалить игрушку \n\t5 - Загрузить данные \n\t6 - Сохранить изменения \n\t0 - Выход в меню");
                        k = check.checkNumber(6);
                        switch (k) {
                            case 1:
                                System.out.println("Все пользователи: ");
                                if(arrusers.size() == 0) System.out.println("пусто");
                                for (User user : arrusers) {
                                    System.out.println(user.getId() + ". " + user.getName());
                                    arrusers.get(user.getId()).showBasket(arrtoys, arrsizes, arrmaterials);
                                }
                                break;
                            case 2:
                                System.out.println("Все игрушки: ");
                                showToys(arrtoys);
                                break;
                            case 3:
                                System.out.println("Введите название игрушки: ");
                                str = scan.nextLine();
                                bool = true;
                                for (Toy item : arrtoys) {
                                    if (str.equals(item.getName())) bool = false;
                                }
                                if (bool) {
                                    System.out.println("Введите стоимость игрушки: ");
                                    num = check.checkInt();
                                    int id = arrtoys.size();
                                    toy = new Toy(id, str, num);
                                    arrtoys.add(toy);
                                } else {
                                    System.err.println("Такая игрушка уже добавлена.");
                                }
                                break;
                            case 4:
                                System.out.println("Выберите какую игрушку удалить: ");
                                showToys(arrtoys);
                                length = arrtoys.size();
                                num = check.checkNumber(length - 1);
                                // Удалять и у пользователей
                                arrtoys.remove(num);
                                // Сообщение об успешном удалении
                                break;
                            case 5:

                                break;
                            case 6:

                                break;
                            case 0: exit = 1;
                                break;
                        }
                        if (exit == 1) break;
                    }
                    break;
                case 2:
                    System.out.println("Введите логин: ");
                    str = check.checkString();
                    bool = true;
                    for (User item : arrusers) {
                        if (str.equals(item.getName())) bool = false;
                    }
                    if (bool) {
                        int id = arrusers.size();
                        User user = new User(id, str);
                        arrusers.add(user);
                        System.out.println("Пользователь зарегистирован.");
                    }
                    for (User user : arrusers) {
                        if (str.equals(user.getName())) userId = user.getId();
                    }

                    while (true) {
                        System.out.println("User: " + arrusers.get(userId).getName() + "\n\t1 - Корзина \n\t2 - Добавить игрушку в корзину \n\t3 - Удалить игрушку из корзины \n\t4 - Очистить корзину \n\t0 - Выход в меню");
                        k = check.checkNumber(4);
                        switch (k) {
                            case 1:
                                System.out.println("Корзина");
                                arrusers.get(userId).showBasket(arrtoys, arrsizes, arrmaterials);
                                break;
                            case 2:
                                System.out.println("Выберите игрушку: ");
                                showToys(arrtoys);
                                length = arrtoys.size();
                                num = check.checkNumber(length - 1);

                                System.out.println("Выберите размер игрушки: ");
                                i = 0;
                                for (SizeCost item : arrsizes) {
                                    System.out.println(i++ + ". " + item.getSize() + "  x" + item.getCost() + "$");
                                }
                                length = arrsizes.size();
                                num2 = check.checkNumber(length - 1);

                                System.out.println("Выберите материал игрушки: ");
                                i = 0;
                                for (MaterialCost item : arrmaterials) {
                                    System.out.println(i++ + ". " + item.getMaterial() + "  x" + item.getCost() + "$");
                                }
                                length = arrmaterials.size();
                                num3 = check.checkNumber(length - 1);

                                arrusers.get(userId).setBasket(num, num2, num3);
                                break;
                            case 3:
                                if(arrusers.get(userId).getBasketLength() == 0) {
                                    System.out.println("корзина пуста");
                                } else {
                                    System.out.println("Выберите какую игрушку удалить: ");
                                    arrusers.get(userId).showBasket(arrtoys, arrsizes, arrmaterials);
                                    length = arrusers.get(userId).getBasketLength();
                                    num = check.checkNumber(length - 1);
                                    arrusers.get(userId).deleteBasketElement(num);
                                }
                                break;
                            case 4:
                                arrusers.get(userId).deleteBasket();
                                break;
                            case 0: exit = 1;
                                break;
                        }
                        if (exit == 1) break;
                    }
                    break;
                case 0: return;
            }
        }
    }

    public static void showToys(ArrayList<Toy> arrtoys) {
        if(arrtoys.size() == 0) System.out.println("пусто");
        for (Toy toy : arrtoys) {
            System.out.println(toy.getId() + ". " + toy.getName() + " - " + toy.getCost() + "$");
        }
    }
}