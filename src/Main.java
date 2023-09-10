import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int k, exit, num, num2, num3, length, userId = -1;
        int[] q = {0};
        String str;
        Boolean bool, isSave = true;

        ArrayList<User> arrusers = new ArrayList<>();
        ArrayList<Toy> arrtoys = new ArrayList<>();
        ArrayList<SizeCost> arrsizes = FileToArr("sizes.txt", SizeCost.class);
        ArrayList<MaterialCost> arrmaterials = FileToArr("materials.txt", MaterialCost.class);

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
                                String fStr = scan.nextLine();
                                bool = arrtoys.stream().anyMatch(item -> fStr.equals(item.getName()));
                                if (!bool) {
                                    System.out.println("Введите стоимость игрушки: ");
                                    num = Check.checkInt();
                                    int id;
                                    if (arrtoys.size() > 0) id = arrtoys.get(arrtoys.size() - 1).getId() + 1;
                                    else id = 0;
                                    Toy toy = new Toy(id, fStr, num);
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
                                if(arrtoys.size() == 0) break;
                                length = arrtoys.size();
                                num = Check.checkNumber(-1, length - 1);
                                bool = false;


                                for (User item: arrusers) {
                                    bool = item.checkBasketName(arrtoys.get(num).getId());
                                    if (bool) {
                                        System.out.println("Данная игрушка лежит в корзине пользователя. \nВсё равно удалить игрушку? \n\t1. Да \n\t2. Отмена");
                                        num2 = Check.checkNumber(1, 2);
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
                                    num = Check.checkNumber(1, 2);
                                    if (num == 2) break;
                                }
                                arrusers = FileToArr("users.txt", User.class);
                                arrtoys = FileToArr("toys.txt", Toy.class);
                                System.out.println("Данные из файлов загружены.");
                                // Нельзя сделать добавление данных из файла к текущим, так как нарушаются id товаров
                                break;
                            case 6:
                                isSave = true;
                                ArrToFile(arrusers, "users.txt");
                                ArrToFile(arrtoys, "toys.txt");
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
                        int id;
                        if (arrusers.size() > 0) id = arrusers.get(arrusers.size() - 1).getId() + 1;
                        else id = 0;
                        User user = new User(id, fStr);
                        arrusers.add(user);
                        System.out.println("Пользователь зарегистирован.");
                    }
                    for (User item : arrusers) {
                        if (fStr.equals(item.getName())) userId = item.getId();
                    }


                    while (true) {
                        System.out.println("User: " + arrusers.get(userId).getName() + "\n\t1 - Корзина \n\t2 - Отсортировать корзину \n\t3 - Добавить игрушку в корзину \n\t4 - Удалить игрушку из корзины \n\t5 - Очистить корзину \n\t0 - Выход в меню");
                        k = Check.checkNumber(0, 5);
                        switch (k) {
                            case 1:
                                System.out.println("Корзина");
                                arrusers.get(userId).showBasket(arrtoys, arrsizes, arrmaterials);
                                arrusers.get(userId).totalCost(arrtoys, arrsizes, arrmaterials);
                                break;
                            case 2:
                                isSave = false;
                                arrusers.get(userId).showBasketSort(arrtoys, arrsizes, arrmaterials);
                                break;
                            case 3:
                                isSave = false;
                                System.out.println("Выберите игрушку: ");
                                showToys(arrtoys);
                                length = arrtoys.size();
                                num = Check.checkNumber(0, length - 1);

                                System.out.println("Выберите размер игрушки: ");
                                q[0] = 0;
                                arrsizes.forEach(item -> {
                                    System.out.println((q[0]++) + ". " + item.getSize() + "  x" + item.getCost() + "$");
                                });
                                length = arrsizes.size();
                                num2 = Check.checkNumber(0, length - 1);

                                System.out.println("Выберите материал игрушки: ");
                                q[0] = 0;
                                arrmaterials.forEach(item -> {
                                    System.out.println((q[0]++) + ". " + item.getMaterial() + "  x" + item.getCost() + "$");
                                });
                                length = arrmaterials.size();
                                num3 = Check.checkNumber(0, length - 1);

                                int id_toy = arrtoys.get(num).getId();
                                arrusers.get(userId).setBasket(id_toy, num2, num3);
                                System.out.println("Игрушка была добавлена в корзину.");
                                break;
                            case 4:
                                isSave = false;
                                if(arrusers.get(userId).getBasketLength() == 0) {
                                    System.out.println("корзина пуста");
                                } else {
                                    System.out.println("Выберите какую игрушку удалить: ");
                                    arrusers.get(userId).showBasket(arrtoys, arrsizes, arrmaterials);
                                    length = arrusers.get(userId).getBasketLength();
                                    num = Check.checkNumber(0, length - 1);
                                    arrusers.get(userId).deleteBasketElement(num);
                                    System.out.println("Игрушка была удалена из корзины.");
                                }
                                break;
                            case 5:
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
                        num = Check.checkNumber(1, 2);
                        if (num == 1) {
                            ArrToFile(arrusers, "users.txt");
                            ArrToFile(arrtoys, "toys.txt");
                            System.out.println("Данные успешно сохранены.");
                        }
                    }
                    return;
                }
            }
        }
    }

    public static void showToys(ArrayList<Toy> arrtoys) {
        if(arrtoys.size() == 0) System.out.println("пусто");
        int[] q = {0};
        arrtoys.forEach (item -> {
            System.out.println(q[0]++ + ". " + item.getName() + " - " + item.getCost() + "$");
        });
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