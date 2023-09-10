import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int k, exit, num, num2, num3, p, length, userId = 0;
        String str;
        Boolean bool, isSave = true;

        Check check = new Check();
        ArrayList<User> arrusers = new ArrayList<>();
        ArrayList<Toy> arrtoys = new ArrayList<>();
        ArrayList<SizeCost> arrsizes = FileToArr("sizes.txt", SizeCost.class);
        ArrayList<MaterialCost> arrmaterials = FileToArr("materials.txt", MaterialCost.class);

        while(true) {
            exit = 0;
            System.out.println("Меню: \n\t1 - Админ \n\t2 - Пользователь \n\t0 - Выход из программы");
            k = check.checkNumber(0, 2);

            switch (k) {
                case 1:
                    while (true) {
                        System.out.println("Админ \n\t1 - Пользователи \n\t2 - Игрушки \n\t3 - Добавить игрушку \n\t4 - Удалить игрушку \n\t5 - Загрузить данные из файла \n\t6 - Сохранить данные в файл \n\t0 - Выход в меню");
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
                                isSave = false;
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
                                    Toy toy = new Toy(id, str, num);
                                    arrtoys.add(toy);
                                    System.out.println("Игрушка успешно добавлена.");
                                } else {
                                    System.err.println("Такая игрушка уже добавлена.");
                                }
                                break;
                            case 4:
                                isSave = false;
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
                                if (arrtoys.size() > 0 || arrusers.size() > 0) {
                                    System.out.println("Данные будут заменены на данные из файлов. \n\t1. Да \n\t2. Отмена");
                                    num = check.checkNumber(1, 2);
                                    if (num == 2) break;
                                }
                                arrusers = FileToArr("users.txt", User.class);
                                arrtoys = FileToArr("toys.txt", Toy.class);
                                System.out.println("Данные из файла загружены.");
                                // Нельзя сделать добавление данных из файла к текущим, так как нарушаются id товаров
                                break;
                            case 6:
                                isSave = true;
                                ArrToFile(arrusers, "users.txt");
                                ArrToFile(arrtoys, "toys.txt");
                                System.out.println("Данные успещно сохранены.");
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
                        isSave = false;
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
                                isSave = false;
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
                                isSave = false;
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
                                isSave = false;
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
                case 0: {
                    if (!isSave) {
                        System.out.println("Данные НЕ сохранены. \n\t1. Сохранить и выйти \n\t2. Выйти");
                        num = check.checkNumber(1, 2);
                        if (num == 1) {
                            ArrToFile(arrusers, "users.txt");
                            ArrToFile(arrtoys, "toys.txt");
                            System.out.println("Данные успещно сохранены.");
                        }
                    }
                    return;
                }
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

    public static <T> ArrayList<T> FileToArr(String file, Class<T> cl) {
        ArrayList<T> newarr = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            newarr = (ArrayList<T>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
        return newarr;
    }
    public static <T> void ArrToFile(ArrayList<T> arr, String file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(arr);
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}