package app.main.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "parts")
public class Part {

    @PrimaryKey(autoGenerate = true)
    private int PartID;

    private String partName;

    private double price;
    private int productID;

    public Part(int partID, String partName, double price, int productID) {
        PartID = partID;
        this.partName = partName;
        this.price = price;
        this.productID = productID;
    }

    public int getPartID() {
        return PartID;
    }

    public void setPartID(int partID) {
        PartID = partID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
