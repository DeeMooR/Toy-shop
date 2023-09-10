import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int k, exit, num, num2, num3, p, length, userId = 0;
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
            k = check.checkNumber(0, 2);

            switch (k) {
                case 1:
                    while (true) {
                        System.out.println("Админ \n\t1 - Пользователи \n\t2 - Игрушки \n\t3 - Добавить игрушку \n\t4 - Удалить игрушку \n\t5 - Загрузить данные \n\t6 - Сохранить изменения \n\t0 - Выход в меню");
                        k = check.checkNumber(0, 6);
                        switch (k) {
                            case 1:
                                System.out.println("Все пользователи: ");
                                if(arrusers.size() == 0) System.out.println("пусто");
                                for (User item : arrusers) {
                                    System.out.println(item.getId() + ". " + item.getName());
                                    item.showBasket(arrtoys, arrsizes, arrmaterials);
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
                                    int id;
                                    if (arrtoys.size() > 0) id = arrtoys.get(arrtoys.size() - 1).getId() + 1;
                                    else id = 0;
                                    toy = new Toy(id, str, num);
                                    arrtoys.add(toy);
                                    System.out.println("Игрушка успешно добавлена.");
                                } else {
                                    System.err.println("Такая игрушка уже добавлена.");
                                }
                                break;
                            case 4:
                                System.out.println("Выберите какую игрушку удалить: ");
                                showToys(arrtoys);
                                length = arrtoys.size();
                                num = check.checkNumber(0, length - 1);
                                bool = false;

                                for (User item: arrusers) {
                                    bool = item.checkBasketName(arrtoys.get(num).getId());
                                    if (bool) {
                                        System.out.println("Данная игрушка лежит в корзине пользователя. \nВсё равно удалить игрушку? \n\t1. Да \n\t2. Отмена");
                                        num2 = check.checkNumber(1, 2);
                                        if (num2 == 1) {
                                            for (User item2: arrusers) {
                                                item2.deleteBasketName(arrtoys.get(num).getId());
                                            }
                                            arrtoys.remove(num);
                                            System.out.println("Игрушка удалена из общего списка и корзин пользователей.");
                                        }
                                        break;
                                    }
                                }
                                if (!bool) {
                                    arrtoys.remove(num);
                                    System.out.println("Игрушка успешно удалена.");
                                }

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
                        int id;
                        if (arrusers.size() > 0) id = arrusers.get(arrusers.size() - 1).getId() + 1;
                        else id = 0;
                        User user = new User(id, str);
                        arrusers.add(user);
                        System.out.println("Пользователь зарегистирован.");
                    }
                    for (User item : arrusers) {
                        if (str.equals(item.getName())) userId = item.getId();
                    }

                    while (true) {
                        System.out.println("User: " + arrusers.get(userId).getName() + "\n\t1 - Корзина \n\t2 - Добавить игрушку в корзину \n\t3 - Удалить игрушку из корзины \n\t4 - Очистить корзину \n\t0 - Выход в меню");
                        k = check.checkNumber(0, 4);
                        switch (k) {
                            case 1:
                                System.out.println("Корзина");
                                arrusers.get(userId).showBasket(arrtoys, arrsizes, arrmaterials);
                                break;
                            case 2:
                                System.out.println("Выберите игрушку: ");
                                showToys(arrtoys);
                                length = arrtoys.size();
                                num = check.checkNumber(0, length - 1);

                                System.out.println("Выберите размер игрушки: ");
                                p = 0;
                                for (SizeCost item : arrsizes) {
                                    System.out.println(p++ + ". " + item.getSize() + "  x" + item.getCost() + "$");
                                }
                                length = arrsizes.size();
                                num2 = check.checkNumber(0, length - 1);

                                System.out.println("Выберите материал игрушки: ");
                                p = 0;
                                for (MaterialCost item : arrmaterials) {
                                    System.out.println(p++ + ". " + item.getMaterial() + "  x" + item.getCost() + "$");
                                }
                                length = arrmaterials.size();
                                num3 = check.checkNumber(0, length - 1);

                                int id_toy = arrtoys.get(num).getId();
                                arrusers.get(userId).setBasket(id_toy, num2, num3);
                                System.out.println("Игрушка была добавлена в корзину.");
                                break;
                            case 3:
                                if(arrusers.get(userId).getBasketLength() == 0) {
                                    System.out.println("корзина пуста");
                                } else {
                                    System.out.println("Выберите какую игрушку удалить: ");
                                    arrusers.get(userId).showBasket(arrtoys, arrsizes, arrmaterials);
                                    length = arrusers.get(userId).getBasketLength();
                                    num = check.checkNumber(0, length - 1);
                                    arrusers.get(userId).deleteBasketElement(num);
                                    System.out.println("Игрушка была удалена из корзины.");
                                }
                                break;
                            case 4:
                                if(arrusers.get(userId).getBasketLength() == 0) {
                                    System.out.println("корзина пуста");
                                } else {
                                    arrusers.get(userId).deleteBasket();
                                    System.out.println("Корзина очищена.");
                                }
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
        int i = 0;
        for (Toy item : arrtoys) {
            System.out.println(i++ + ". " + item.getName() + " - " + item.getCost() + "$");
        }
    }
}