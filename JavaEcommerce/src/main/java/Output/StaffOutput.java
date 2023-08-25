package Output;

import Models.Product;
import Models.Staff;
import Models.StaffRevenue;

import java.util.ArrayList;

public class StaffOutput {
    public static void staffTable(ArrayList<Staff> staffsList) {
        String format = "| %-15s | %-20s | %-15s | %-20s | %-20s | %-10s | %-10s | %-10d |%n";
        System.out.format("+-----------------+----------------------+-----------------+----------------------+----------------------+------------+------------+------------+%n");
        System.out.format("| Ma Nhan vien    | Ten                  | So dien thoai   | Email                | Dia chi              | Gioi tinh  | Nhom       | Trang thai |%n");
        System.out.format("+-----------------+----------------------+-----------------+----------------------+----------------------+------------+------------+------------+%n");
        for (int i = 0; i < staffsList.size(); i++) {
            Staff staff = staffsList.get(i);
            String name = staff.getName();
            if (name.length() > 20) name = name.substring(0, 17) + "...";
            String address = staff.getAddress();
            if (address.length() > 20) address = address.substring(0, 17) + "...";
            String email = staff.getEmail();
            if (email.length() > 20) email = email.substring(0, 17) + "...";

            System.out.format(format, staff.getCode(), name, staff.getPhoneNumber(), email,address,staff.getGender().name(),staff.getGroup(),staff.getStatus());
        }
        System.out.format("+-----------------+----------------------+-----------------+----------------------+----------------------+------------+------------+------------+%n");
    }

    public static void staffTable(Staff staff) {
        String format = "| %-15s | %-20s | %-15s | %-20s | %-20s | %-10s | %-10s | %-10d |%n";
        System.out.format("+-----------------+----------------------+-----------------+----------------------+----------------------+------------+------------+------------+%n");
        System.out.format("| Ma Nhan vien    | Ten                  | So dien thoai   | Email                | Dia chi              | Gioi tinh  | Nhom       | Trang thai |%n");
        System.out.format("+-----------------+----------------------+-----------------+----------------------+----------------------+------------+------------+------------+%n");
        String name = staff.getName();
        if (name.length() > 20) name = name.substring(0, 17) + "...";
        String address = staff.getAddress();
        if (address.length() > 20) address = address.substring(0, 17) + "...";
        String email = staff.getEmail();
        if (email.length() > 20) email = email.substring(0, 17) + "...";
        System.out.format(format, staff.getCode(), name, staff.getPhoneNumber(), email,address,staff.getGender().name(),staff.getGroup(),staff.getStatus());
        System.out.format("+-----------------+----------------------+-----------------+----------------------+----------------------+------------+------------+------------+%n");
    }


    public static void staffRevenueTable(ArrayList<StaffRevenue> staff){
            String format = "| %-15s | %-30s | %-20d |%n";
            System.out.format("+-----------------+--------------------------------+----------------------+%n");
            System.out.format("| Ma Nhan Vien    | Ten                            | Doanh thu            |%n");
            System.out.format("+-----------------+--------------------------------+----------------------+%n");
            for (int i = 0; i < staff.size(); i++) {
                StaffRevenue st = staff.get(i);
                String name = st.getStaffName();
                if (name.length() > 30) name = name.substring(0, 27) + "...";
                System.out.format(format, st.getStaffCode(), name, st.getRevenue());
            }
            System.out.format("+-----------------+--------------------------------+----------------------+%n");
    }
}
