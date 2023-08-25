package Output;

import Models.Bill;

import java.util.ArrayList;

public class BillOutput {
    public static void billTable(ArrayList<Bill> billsList) {
        String format = "| %-15s | %-15s | %-15s | %-10d | %-10d | %-15d | %-12s |%n";
        System.out.format("+-----------------+-----------------+-----------------+------------+------------+-----------------+--------------+%n");
        System.out.format("| Ma Khach hang   | Ma Nhan Vien    | Ma San Pham     | So Luong   | Don Gia    | Thanh Tien      | Ngay ban     |%n");
        System.out.format("+-----------------+-----------------+-----------------+------------+------------+-----------------+--------------+%n");
        for (int i = 0; i < billsList.size(); i++) {
            Bill bill = billsList.get(i);
            System.out.format(format, bill.getCustomerCode(), bill.getStaffCode(), bill.getProductCode(), bill.getQuantity(), bill.getPrice(), bill.getTotal(), bill.getSaleDate());
        }
        System.out.format("+-----------------+-----------------+-----------------+------------+------------+-----------------+--------------+%n");
    }
}
