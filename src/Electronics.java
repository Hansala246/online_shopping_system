public class Electronics extends Product{

    private String brandName;
    private String warrantyPeriod;

    public Electronics() {

    }

    public Electronics(String productId, String productName, int numberOfAvailableItems, double price,String brandName, String warrantyPeriod) {
        super(productId,productName,numberOfAvailableItems,price);
        this.brandName = brandName;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {
        return "Electronics{" +
                "brandName='" + brandName + '\'' +
                ", warrantyPeriod='" + warrantyPeriod + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", numberOfAvailableItems=" + numberOfAvailableItems +
                ", price=" + price +
                '}';
    }
}
