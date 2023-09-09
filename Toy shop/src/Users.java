import java.util.ArrayList;

public class Users {
    private ArrayList<User> arrusers = new ArrayList<>();

    public void setUser(String login) {
        int id = arrusers.size();
        User user = new User(id, login);
        arrusers.add(user);
    }

    public void setUserBasket(int id_user, int id_toy, int id_size, int id_material) {
        arrusers.get(id_user).setBasket(id_toy, id_size, id_material);
    }

    public int getUserId(String str) {
        for (User user : arrusers) {
            if (str.equals(user.getName())) return user.getId();
        }
        return -1;
    }

    public String getUserName(int id) {
        return arrusers.get(id).getName();
    }

    public void showUsers(Toys toys, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        if(arrusers.size() == 0) System.out.println("пусто");
        for (User user : arrusers) {
            System.out.println(user.getId() + ". " + user.getName());
            showUserBasket(toys, user.getId(), arrsizes, arrmaterials);
        }
    }
    public void showUserBasket(Toys toys, int id_user, ArrayList<SizeCost> arrsizes, ArrayList<MaterialCost> arrmaterials) {
        arrusers.get(id_user).showBasket(toys, arrsizes, arrmaterials);
    }

    public boolean checkUserName(String str) {
        for (User user : arrusers) {
            if (str.equals(user.getName())) return false;
        }
        return true;
    }

    public int getUserBasketLength(int id_user) {
        return arrusers.get(id_user).getBasketLength();
    }
    public void deleteUserBasket(int id_user) {
        arrusers.get(id_user).deleteBasket();
    }
    public void deleteUserBasketElement(int id_user, int num) {
        arrusers.get(id_user).deleteBasketElement(num);
    }
}