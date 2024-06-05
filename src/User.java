import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String password;
    private int orderedCount=0;

    public User() {

    }

    public User(String userName, String password, int orderedCount) {
        this.userName = userName;
        this.password = password;
        this.orderedCount = orderedCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOrderedCount() {
        return orderedCount;
    }

    public void setOrderedCount(int orderedCount) {
        this.orderedCount = orderedCount;
    }
}
