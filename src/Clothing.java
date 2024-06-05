public class Clothing extends Product{

    private String size;
    private String colour;

    public Clothing() {

    }

    public Clothing(String productId, String productName, int numberOfAvailableItems, double price,String size, String colour){
        super(productId,productName,numberOfAvailableItems,price);
        this.size=size;
        this.colour=colour;

    }

    public String getSize(){
        return size;
    }

    public void setSize(String size){
        this.size=size;
    }

    public String getColour(){
        return colour;
    }

    public void setColour(String colour){
        this.colour=colour;
    }

    @Override
    public String toString() {
        return "Clothing{" +
                "size='" + size + '\'' +
                ", colour='" + colour + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", numberOfAvailableItems=" + numberOfAvailableItems +
                ", price=" + price +
                '}';
    }

}
