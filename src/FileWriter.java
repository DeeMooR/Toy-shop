import java.io.*;
import java.util.ArrayList;

public class FileWriter<T> implements Runnable {
    private String file;
    private ArrayList<T> arr;

    public FileWriter(String file, ArrayList<T> arr) {
        this.file = file;
        this.arr = arr;
    }

    @Override
    public void run() {
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