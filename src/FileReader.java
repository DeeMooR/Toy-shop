import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class FileReader<T> implements Callable<ArrayList<T>> {
    private String file;

    public FileReader(String file) {
        this.file = file;
    }

    @Override
    public ArrayList<T> call() {
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
}