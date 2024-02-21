package app.main.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "products")
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int productID;

    private String productName;

    private String hotel;
    private String startDate;
    private String endDate;



    public Product(int productID, String productName, String hotel, String startDate, String endDate) {
        this.productID = productID;
        this.productName = productName;
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return this.productName;
    }
}
