import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int k, exit, num;
        Boolean bool, isSave = true;

        Admin admin = new Admin();
        ArrayList<User> arrusers = new ArrayList<>();
        ArrayList<Toy> arrtoys = new ArrayList<>();
        ArrayList<SizeCost> arrsizes = Admin.FileToArr("sizes.txt", SizeCost.class);
        ArrayList<MaterialCost> arrmaterials = Admin.FileToArr("materials.txt", MaterialCost.class);

        while(true) {
            exit = 0;
            System.out.println("Меню: \n\t1 - Админ \n\t2 - Пользователь \n\t0 - Выход из программы");
            k = Check.checkNumber(0, 2);

            switch (k) {
                case 1:
                    while (true) {
                        System.out.println("Админ \n\t1 - Пользователи \n\t2 - Игрушки \n\t3 - Добавить игрушку \n\t4 - Удалить игрушку \n\t5 - Загрузить данные из файла \n\t6 - Сохранить данные в файл \n\t0 - Выход в меню");
                        k = Check.checkNumber(0, 6);
                        switch (k) {
                            case 1:
                                System.out.println("Все пользователи: ");
                                admin.showUsers(arrusers, arrtoys, arrsizes, arrmaterials);
                                break;
                            case 2:
                                System.out.println("Все игрушки: ");
                                admin.showToys(arrtoys);
                                break;
                            case 3:
                                isSave = false;
                                admin.addToy(arrtoys);
                                break;
                            case 4:
                                isSave = false;
                                admin.deleteToy(arrusers, arrtoys);
                                break;
                            case 5:
                                if (arrtoys.size() > 0 || arrusers.size() > 0) {
                                    System.out.println("Данные будут заменены на данные из файлов. \n\t1. Да \n\t2. Отмена");
                                    num = Check.checkNumber(1, 2);
                                    if (num == 2) break;
                                }
                                arrusers = admin.FileToArr("users.txt", User.class);
                                arrtoys = admin.FileToArr("toys.txt", Toy.class);
                                System.out.println("Данные из файлов загружены.");
                                break;
                            case 6:
                                isSave = true;
                                admin.ArrToFile(arrusers, "users.txt");
                                admin.ArrToFile(arrtoys, "toys.txt");
                                System.out.println("Данные успешно сохранены.");
                                break;
                            case 0: exit = 1;
                                break;
                        }
                        if (exit == 1) break;
                    }
                    break;
                case 2:
                    System.out.println("Введите логин: ");
                    String fStr = Check.checkString();
                    bool = arrusers.stream().anyMatch(item -> fStr.equals(item.getName()));
                    if (!bool) {
                        isSave = false;
                        newUser(arrusers, fStr);
                    }
                    User user = getUserObject(arrusers, fStr);

                    while (true) {
                        System.out.println("User: " + user.getName() + "\n\t1 - Корзина \n\t2 - Отсортировать корзину \n\t3 - Добавить игрушку в корзину \n\t4 - Удалить игрушку из корзины \n\t5 - Очистить корзину \n\t0 - Выход в меню");
                        k = Check.checkNumber(0, 5);
                        switch (k) {
                            case 1:
                                System.out.println("Корзина");
                                user.showBasket(arrtoys, arrsizes, arrmaterials);
                                break;
                            case 2:
                                isSave = false;
                                user.showBasketSort(arrtoys, arrsizes, arrmaterials);
                                System.out.println("Корзина отсортирована по возрастанию цены.");
                                break;
                            case 3:
                                isSave = false;
                                user.addToyToBasket(arrtoys, arrsizes, arrmaterials);
                                System.out.println("Игрушка была добавлена в корзину.");
                                break;
                            case 4:
                                isSave = false;
                                if(user.getBasketLength() == 0) {
                                    System.out.println("корзина пуста");
                                } else {
                                    user.deleteToyFromBasket(arrtoys, arrsizes, arrmaterials);
                                    System.out.println("Игрушка была удалена из корзины.");
                                }
                                break;
                            case 5:
                                isSave = false;
                                if(user.getBasketLength() == 0) {
                                    System.out.println("корзина пуста");
                                } else {
                                    user.deleteBasket();
                                    System.out.println("Корзина очищена.");
                                }
                                break;
                            case 0: exit = 1;
                                break;
                        }
                        if (exit == 1) break;
                    }
                    break;
                case 0: {
                    if (!isSave) {
                        System.out.println("Данные НЕ сохранены. \n\t1. Сохранить и выйти \n\t2. Выйти");
                        num = Check.checkNumber(1, 2);
                        if (num == 1) {
                            Admin.ArrToFile(arrusers, "users.txt");
                            Admin.ArrToFile(arrtoys, "toys.txt");
                            System.out.println("Данные успешно сохранены.");
                        }
                    }
                    return;
                }
            }
        }
    }

    public static void newUser(ArrayList<User> arrusers, String str) {
        int id = 0;
        if (arrusers.size() > 0) id = arrusers.get(arrusers.size() - 1).getId() + 1;
        User user = new User(id, str);
        arrusers.add(user);
        System.out.println("Пользователь зарегистирован.");
    }

    public static User getUserObject(ArrayList<User> arrusers, String str) {
        for (User item : arrusers) {
            if (str.equals(item.getName())) {
                return arrusers.get(item.getId());
            }
        }
        return null;
    }
}