import java.io.IOException;
import java.util.ArrayList;

public interface ShoppingManager {
     void addANewProduct();
     void deleteAProduct();
     void printTheListOfTheProducts() throws IOException;
     <T extends Product> void saveInAFile(ArrayList<T> list, char productType) throws IOException;


}
