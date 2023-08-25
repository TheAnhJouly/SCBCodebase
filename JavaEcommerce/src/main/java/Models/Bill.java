package Models;

import java.util.Date;

public class Bill {
    private String customerCode;
    private String staffCode;
    private String productCode;
    private int quantity;
    private int price;
    private long total;
    private String saleDate;

    public Bill(String customerCode, String staffCode, String productCode, int quantity, int price, long total, String saleDate) {
        this.customerCode = customerCode;
        this.staffCode = staffCode;
        this.productCode = productCode;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.saleDate = saleDate;
    }

    public Bill() {
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }
    @Override
    public String toString(){
        return this.customerCode+","+this.staffCode+","+this.productCode+","+this.quantity+","+this.price+","+this.total+","+this.saleDate;
    }
}
