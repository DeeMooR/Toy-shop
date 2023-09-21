import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int k, exit, num;
        Boolean bool, isSave = true;

        Admin admin = new Admin();
        ArrayList<User> arrusers = new ArrayList<>();
        ArrayList<Toy> arrtoys = new ArrayList<>();

        ArrayList<?>[] result = readFilesParallel("sizes.txt", "materials.txt");
        ArrayList<SizeCost> arrsizes = (ArrayList<SizeCost>) result[0];
        ArrayList<MaterialCost> arrmaterials = (ArrayList<MaterialCost>) result[1];

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
                                result = readFilesParallel("users.txt", "toys.txt");
                                arrusers = (ArrayList<User>) result[0];
                                arrtoys = (ArrayList<Toy>) result[1];
                                System.out.println("Данные из файлов загружены.");
                                break;
                            case 6:
                                isSave = true;
                                writeFilesParallel("users.txt", arrusers, "toys.txt", arrtoys);
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
                                basketSortParallel(user, arrtoys, arrsizes, arrmaterials);
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
                            writeFilesParallel("users.txt", arrusers, "toys.txt", arrtoys);
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

    public static <One, Two> ArrayList<?>[] readFilesParallel(String fileName1, String fileName2) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<ArrayList<One>> future1 = executorService.submit(new FileReader<>(fileName1));
        Future<ArrayList<Two>> future2 = executorService.submit(new FileReader<>(fileName2));
        executorService.shutdown();

        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
            ArrayList<One> list1 = future1.get();
            ArrayList<Two> list2 = future2.get();
            ArrayList<?>[] resultList = new ArrayList[]{list1, list2};
            return resultList;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFilesParallel(String file1, ArrayList<?> list1, String file2, ArrayList<?> list2) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new FileWriter<>(file1, list1));
        executorService.submit(new FileWriter<>(file2, list2));
        executorService.shutdown();

        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void basketSortParallel(User user, ArrayList<Toy> arrtoys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        if(user.getBasketLength() == 0) {
            System.out.println("\tкорзина пуста");
            return;
        }
        ArrayList<Toy> localArrToys = arrtoys;
        ArrayList<SizeCost> localArrSizes = arrsizes;
        ArrayList<MaterialCost> localArrMaterials = arrmaterials;
        Thread thread1 = new Thread(() -> user.showBasketSortIncrease(localArrToys, localArrSizes, localArrMaterials));
        Thread thread2 = new Thread(() -> user.showBasketSortDecrease(localArrToys, localArrSizes, localArrMaterials));

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}