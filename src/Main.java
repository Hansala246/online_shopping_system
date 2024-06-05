
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<User> users = new ArrayList<>();
    public static User currentUser;

    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println(" Welcome to the Online Shopping System");
        System.out.println("=======================================\n");
        loadUsers();
        systemInit();
    }

    public static void systemInit() {
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;

        loop:
        while (true) {
            System.out.println("Press 1 to Login , Press 2 to Register , Press 0 to Exit");
            System.out.print("Enter Option :");
            char option = scanner.next().charAt(0);
            switch (option) {
                case '1':
                    scanner.nextLine();
                    System.out.print("Enter Username :");
                    username = scanner.nextLine();
                    System.out.print("Enter Password :");
                    password = scanner.nextLine();


                    if (username.equals("manager")) {
                        if (password.equals("manager1234")) {
                            new WestminsterShoppingManager();
                            return;
                        } else {
                            userInvalidFunction();
                            break;
                        }
                    } else if (findUser(username, password)) {
                        SwingUtilities.invokeLater(() -> {
                            new WestministerClient().setVisible(true);
                        });
                        return;
                    } else {
                        userInvalidFunction();
                        break;
                    }

                case '2':
                    try {
                        register();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case '0':
                    System.out.println("System is exiting...");
                    System.out.println("Have a nice day !");
                    return;
                default:
                    System.out.println("Invalid Option");
                    systemInit();
                    break;
            }
        }

    }

    private static void register() throws IOException {
        String userFile = "src/res/systemUsers.txt";

        File file = new File(userFile);
        FileOutputStream fOut = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(fOut);

        Scanner scanner = new Scanner(System.in);
        String username;
        String password;

        System.out.print("Enter Username :");
        username = scanner.nextLine();

        for (User user : users) {
            if (user.getUserName().equals(username)) {
                System.out.println("Username already exists");
                return;
            }
        }

        System.out.print("Enter Password :");
        password = scanner.nextLine();

        User newUser = new User(username, password, 0);
        objOut.writeObject(newUser);
        System.out.println("User Registered Successfully..");
        loadUsers();
    }

    private static void loadUsers() {
        String userFile = "src/res/systemUsers.txt";

        try (FileInputStream fIn = new FileInputStream(userFile);
             ObjectInputStream objIn = new ObjectInputStream(fIn)) {


            users.clear();

            while (true) {
                try {
                    User user = (User) objIn.readObject();
                    users.add(user);
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Class not found");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
    }

    private static boolean findUser(String username, String password) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                if (user.getPassword().equals(password)) {
                    currentUser = user;
                    return true;
                }
            }
        }
        return false;
    }


    public static void userInvalidFunction() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Invalid username or password");
        System.out.println("Press Any Key to Try Again or Press 0 to Exit..");

        if (scanner.next().charAt(0) == '0') {
            System.out.println("System is exiting...");
            System.out.println("Have a nice day !");
        } else {
            systemInit();
        }
    }



}

