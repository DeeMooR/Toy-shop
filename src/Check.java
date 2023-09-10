import java.util.InputMismatchException;
import java.util.Scanner;

public class Check {
    public static int checkNumber(int min, int max) {
        Scanner scan = new Scanner(System.in);
        int a;
        while (true) {
            try {
                a = scan.nextInt();
                scan.nextLine();
                if (a < min || a > max) {
                    throw new MyException("Такого варианта нет.");
                }
                break;
            } catch (MyException e) {
                System.err.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.err.println("Недопустимое значение.");
                scan.next();
            }
        }
        return a;
    }

    public static int checkInt() {
        Scanner scan = new Scanner(System.in);
        int a;
        while (true) {
            try {
                a = scan.nextInt();
                scan.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.err.println("Недопустимое значение.");
                scan.next();
            }
        }
        return a;
    }

    public static String checkString() {
        Scanner scan = new Scanner(System.in);
        String b;
        while (true) {
            try {
                b = scan.nextLine();
                String[] words = b.split(" ");
                if (words.length > 1) {
                    throw new MyException("Вы ввели больше одного слова.");
                }
                break;
            } catch (MyException e) {
                System.err.println(e.getMessage());
            }
        }
        return b;
    }
}
