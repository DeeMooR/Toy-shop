import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int k, exit, num, num2, num3, i, length;
        String str;
        Boolean bool;

        Check check = new Check();
        Toys toys = new Toys();
        Users users = new Users();

        toys.setToy("Мяч", 12);
        toys.setToy("Кукла", 24);
        toys.setToy("Самолёт", 36);
        toys.setToy("Машина", 48);

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
                                users.showUsers(toys, arrsizes, arrmaterials);
                                break;
                            case 2:
                                System.out.println("Все игрушки: ");
                                toys.showToys();
                                break;
                            case 3:
                                System.out.println("Введите название игрушки: ");
                                str = scan.nextLine();
                                bool = toys.checkToyName(str);
                                if (bool) {
                                    System.out.println("Введите стоимость игрушки: ");
                                    num = check.checkInt();
                                    toys.setToy(str, num);
                                } else {
                                    System.err.println("Такая игрушка уже добавлена.");
                                }
                                break;
                            case 4:
                                System.out.println("Выберите какую игрушку удалить: ");
                                toys.showToys();
                                length = toys.getToysLength();
                                num = check.checkNumber(length - 1);
                                // Удалять и у пользователей
                                toys.deleteToy(num);
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
                    bool = users.checkUserName(str);
                    if (bool) {
                        users.setUser(str);
                        System.out.println("Пользователь зарегистирован.");
                    }
                    int userId = users.getUserId(str);
                    while (true) {
                        System.out.println("User: " + users.getUserName(userId) + "\n\t1 - Корзина \n\t2 - Добавить игрушку в корзину \n\t3 - Удалить игрушку из корзины \n\t4 - Очистить корзину \n\t0 - Выход в меню");
                        k = check.checkNumber(4);
                        switch (k) {
                            case 1:
                                System.out.println("Корзина");
                                users.showUserBasket(toys, userId, arrsizes, arrmaterials);
                                break;
                            case 2:
                                System.out.println("Выберите игрушку: ");
                                toys.showToys();
                                length = toys.getToysLength();
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

                                users.setUserBasket(userId, num, num2, num3);
                                break;
                            case 3:
                                System.out.println("Выберите какую игрушку удалить: ");
                                users.showUserBasket(toys, userId, arrsizes, arrmaterials);
                                length = users.getUserBasketLength(userId);
                                num = check.checkNumber(length - 1);
                                users.deleteUserBasketElement(userId, num);
                                break;
                            case 4:
                                users.deleteUserBasket(userId);
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
}