import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    ArrayList<Clothing> systemClothingList;
    ArrayList<Electronics> systemElectronicsList;

    public WestminsterShoppingManager() {
        systemClothingList = new ArrayList<>();
        systemElectronicsList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        loadDataIntoTheSystem(systemElectronicsList, 'E');
        loadDataIntoTheSystem(systemClothingList, 'C');

        System.out.println("================================");
        System.out.println("  Westminster Shopping Manager");
        System.out.println("================================\n\n");

        while (true) {
            System.out.println("[1] Add a new Product");
            System.out.println("[2] Delete a Product");
            System.out.println("[3] Print the list of the Products");
            System.out.println("[4] Save in a File");
            System.out.println("Press 0 to exit..\n");

            System.out.print("Select an Option >");
            char option = scanner.next().charAt(0);
            switch (option) {
                case '1':
                    addANewProduct();
                    System.out.println("\n\n");
                    break;
                case '2':
                    deleteAProduct();
                    System.out.println("\n\n");
                    break;
                case '3':
                    printTheListOfTheProducts();
                    System.out.println("\n\n");
                    break;
                case '4':
                    try {
                        saveInAFile(systemElectronicsList, 'E');
                        saveInAFile(systemClothingList, 'C');
                        System.out.println("Successfully saved the file..!");
                        System.out.println("\n\n");
                    } catch (IOException e) {
                        System.out.println("An error occurred while saving the file");
                    }
                    break;
                case '0':
                    System.out.println("\n\nSystem is exiting...");
                    System.out.println("Have a nice day !");
                    return;
            }
        }

    }


    @Override
    public void addANewProduct() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter Product Type(Clothing-C | Electronics-E) :");
            char type = scanner.next().charAt(0);
            scanner.nextLine();
            switch (type) {
                case 'C':
                case 'c':
                    addProduct(systemClothingList, type);
                    break;
                case 'E':
                case 'e':
                    addProduct(systemElectronicsList, type);
                    break;
                default:
                    System.out.println("Invalid Input..");
                    continue;
            }
            System.out.print("Do you want to add another product (Y/N) :");
            char addNewChoice = scanner.next().charAt(0);
            if (addNewChoice != 'Y' && addNewChoice != 'y') {
                break;
            }
        }
    }

    private <T extends Product> void addProduct(ArrayList<T> list, char type) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter details of your " + (type == 'C' || type == 'c' ? "Clothing" : "Electronic") + " Product..");

        System.out.print("Product ID :");
        String productId = scanner.nextLine();
        if (searchProduct(productId, list, type) != null) {
            System.out.println("Product ID already exists..!");
            return;
        }
        System.out.print("Product Name :");
        String productName = scanner.nextLine();

        int numberOfAvailableItems;
        double price;
        try {
            System.out.print("Number of Available Items :");
            numberOfAvailableItems = scanner.nextInt();
            System.out.print("Price :");
            price = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input type detected..!");
            return;
        }
        scanner.nextLine();

        if (type == 'C' || type == 'c') {
            System.out.print("Size :");
            String size = scanner.nextLine();
            System.out.print("color :");
            String colour = scanner.nextLine();
            Clothing clothing = new Clothing(productId, productName, numberOfAvailableItems, price, size, colour);
            list.add((T) clothing);
        } else {
            System.out.print("Brand :");
            String brandName = scanner.nextLine();
            System.out.print("Warranty :");
            String warrantyPeriod = scanner.nextLine();
            Electronics electronic = new Electronics(productId, productName, numberOfAvailableItems, price, brandName, warrantyPeriod);
            list.add((T) electronic);
        }
        System.out.println("Product is successfully added..!");
    }


    @Override
    public void deleteAProduct() {
        System.out.print("Enter the Product ID :");
        Scanner scanner = new Scanner(System.in);
        String productId = scanner.nextLine();
        Product product = null;
        char type = ' ';

        if (searchProduct(productId, systemClothingList,'C') != null) {
            product = (Product) searchProduct(productId, systemClothingList,'C');
            type = 'C';
        } else if (searchProduct(productId, systemElectronicsList,'E') != null) {
            product = (Product) searchProduct(productId, systemElectronicsList,'E');
            type = 'E';
        }

        if (product != null) {
            deleteProduct(product, type);
        } else {
            System.out.println("\nProduct not found..!");
            System.out.print("Do you want to try again (Y/N) :");
            char choice = scanner.next().charAt(0);
            if (choice == 'Y' || choice == 'y') {
                deleteAProduct();
            }
        }
    }

    private void deleteProduct(Product product, char type) {
        System.out.println("\n\nDETAILS OF THE PRODUCT");
        System.out.println("======================");
        System.out.println("Product Type : " + (type == 'C' ? "Clothing" : "Electronics"));
        System.out.println("Product ID :" + product.getProductId());
        System.out.println("Product Name :" + product.getProductName());
        System.out.println("Number of Available Items :" + product.getNumberOfAvailableItems());
        System.out.println("Price :" + product.getPrice());

        if (type == 'C') {
            Clothing clothing = (Clothing) product;
            System.out.println("Size :" + clothing.getSize());
            System.out.println("color :" + clothing.getColour());
            systemClothingList.remove(clothing);
        } else {
            Electronics electronic = (Electronics) product;
            System.out.println("Brand :" + electronic.getBrandName());
            System.out.println("Warranty :" + electronic.getWarrantyPeriod());
            systemElectronicsList.remove(electronic);
        }

        try {
            if (type == 'C') saveInAFile(systemClothingList, type);
            else saveInAFile(systemElectronicsList, type);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("\nProduct is successfully deleted..!");
        System.out.println("Total number of products in the System :" + (systemElectronicsList.size() + systemClothingList.size()));
    }


    <T> Object searchProduct(String productId, ArrayList<T> list, char productType) {
        for (T product : list) {
            if (productType == 'E') {
                if (((Electronics) product).getProductId().equals(productId)) {
                    return product;
                }
            } else if (productType == 'C') {
                if (((Clothing) product).getProductId().equals(productId)) {
                    return product;
                }
            }
        }
        return null;
    }


    @Override
    public void printTheListOfTheProducts() {
        ArrayList<Electronics> sortedElectronicsList = new ArrayList<>(systemElectronicsList);
        ArrayList<Clothing> sortedClothingList = new ArrayList<>(systemClothingList);

        sortedElectronicsList.sort(Comparator.comparing(Electronics::getProductId));
        sortedClothingList.sort(Comparator.comparing(Clothing::getProductId));


        while (true) {
            System.out.println("ELECTRONIC PRODUCT LIST");
            System.out.println("=========================================================================================");
            System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s%n", "Product ID", "Product Name", "Avail Items", "Price", "Brand", "Warranty");
            for (Electronics e : sortedElectronicsList) {
                System.out.printf("%-15s %-15s %-15d %-15.2f %-15s %-15s%n", e.getProductId(), e.getProductName(), e.getNumberOfAvailableItems(), e.getPrice(), e.getBrandName(), e.getWarrantyPeriod());
            }

            System.out.println("\n\nCLOTHING PRODUCT LIST");
            System.out.println("=========================================================================================");
            System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s%n", "Product ID", "Product Name", "Avail Items", "Price", "Size", "Color");
            for (Clothing c : sortedClothingList) {
                System.out.printf("%-15s %-15s %-15d %-15.2f %-15s %-15s%n", c.getProductId(), c.getProductName(), c.getNumberOfAvailableItems(), c.getPrice(), c.getSize(), c.getColour());
            }


            System.out.println("\n\nPress 0 to go back..");
            Scanner scanner = new Scanner(System.in);
            if (scanner.next().charAt(0) == '0') {
                break;
            }
        }
    }

    @Override
    public  <T extends Product> void saveInAFile(ArrayList<T> list, char productType) throws IOException {
        String electronicProductFilePath = "src/res/systemElectronicProducts.txt";
        String clothingProductFilePath = "src/res/systemClothingProducts.txt";

        File file = new File(productType == 'E' ? electronicProductFilePath : clothingProductFilePath);
        FileOutputStream fOut = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(fOut);

        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T product = it.next();
            objOut.writeObject(product);
        }
    }

    public static <T extends Product> void loadDataIntoTheSystem(ArrayList<T> list, char productType) {
        String electronicProductFilePath = "src/res/systemElectronicProducts.txt";
        String clothingProductFilePath = "src/res/systemClothingProducts.txt";


        try (FileInputStream fIn = new FileInputStream(productType == 'E' ? electronicProductFilePath : clothingProductFilePath);
             ObjectInputStream objIn = new ObjectInputStream(fIn)) {


            list.clear();

            while (true) {
                try {
                    T product = (T) objIn.readObject();
                    list.add(product);
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


}
