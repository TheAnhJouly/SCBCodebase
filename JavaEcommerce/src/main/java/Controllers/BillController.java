package Controllers;

import Models.Bill;
import Services.BillService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BillController {
    private ArrayList<Bill> billsList;

    public BillController() {
        billsList = new ArrayList<Bill>();
    }

    public void init(){
        this.billsList = BillService.genBillsList();
    }

    public boolean checkInit(){
        return this.billsList.size()!=0;
    }

    public void addNewBill(Bill bill){
        this.billsList.add(bill);
    }

    public static Bill createBill(String customerCode, String staffCode, String productCode, int quantity, int price) {
        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String saleDate = dateFormat.format(currentDate);
        long total = price * quantity;
        return new Bill(customerCode, staffCode, productCode, quantity, price, total, saleDate);
    }

    public ArrayList<Bill> getDailyBill(String saleDate){
        ArrayList<Bill> result = new ArrayList<Bill>();
        for(int i=0;i<billsList.size();i++){
            if(billsList.get(i).getSaleDate().equals(saleDate)){
                result.add(billsList.get(i));
            }
        }
        return result;
    }

    public ArrayList<Bill> getMonthlyBill(int month){
        ArrayList<Bill> result = new ArrayList<Bill>();
        for(int i=0;i<billsList.size();i++){
            String date = billsList.get(i).getSaleDate();
            String monthString = date.substring(3, 5);
            int m = Integer.parseInt(monthString);
            if(m==month){
                result.add(billsList.get(i));
            }
        }
        return result;
    }

    public Map<String, Long> getStaffRevenueMap(){
        Map<String, Long> totalRevenueByStaff = new HashMap<>();
        for (Bill invoice : this.billsList) {
            String staffCode = invoice.getStaffCode();
            long revenue = invoice.getTotal();

            if (totalRevenueByStaff.containsKey(staffCode)) {
                totalRevenueByStaff.put(staffCode, totalRevenueByStaff.get(staffCode) + revenue);
            } else {
                totalRevenueByStaff.put(staffCode, revenue);
            }
        }
        return totalRevenueByStaff;
    }
}
