package app.main.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "parts")
public class Part {

    @PrimaryKey(autoGenerate = true)
    private int partID;

    private String partName;

    private int productID;

    private String date;

    public Part(int partID, String partName, int productID, String date) {
        this.partID = partID;
        this.partName = partName;
        this.productID = productID;
        this.date = date;
    }


    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }


    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
