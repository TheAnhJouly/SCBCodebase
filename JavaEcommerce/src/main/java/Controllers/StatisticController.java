package Controllers;

import Models.Bill;
import Models.Product;
import Models.Staff;
import Models.StaffRevenue;
import Output.BillOutput;
import Output.MenuOutput;
import Output.ProductOutput;
import Output.StaffOutput;
import Services.BillService;
import Services.ProductService;
import Services.StaffService;
import Utils.UI;
import Utils.Utils;

import java.io.IOException;
import java.sql.SQLType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StatisticController {
    private BillController billController;
    private ProductController productController;

    private StaffService staffService;

    private Scanner input;

    public StatisticController() {
        input = new Scanner(System.in);
    }

    public StatisticController(BillController billController, ProductController productController, Scanner input) {
        this.billController = billController;
        this.productController = productController;
        this.staffService = new StaffService();
        this.input = input;
    }

    String checkGetReport(){
        String fileName;
        System.out.println("|");
        System.out.println("| " + UI.infoString("[i] Nhấn Enter để Đồng ý và phím bất kì để Từ chối"));
        System.out.print("| Bạn có muốn xuất ra file báo cáo không?: ");
        String choice = input.nextLine();
        if(!choice.equals("")){
            System.out.println("| " + UI.successString("Từ chối"));
            return "";
        }
        boolean check = false;
        do {
            System.out.print("| Mời nhập tên file báo cáo: ");
            fileName = input.nextLine();
            check = Utils.checkValidFileName(fileName);
            if(!check) System.out.println("| " + UI.errorString("[!] Tên file không hợp lệ!"));
        }while (!check);
        return fileName;
    }

    public int showStatisticMenu() {
        String title = "Báo cáo thống kê";
        String[] items = {
                "Thống kê hóa đơn theo ngày",
                "Thống kê hóa đơn theo tháng",
                "Thống kê doanh thu nhân viên",
                "Thống kê sản phẩm sắp hết hàng",
                "Thống kê sản phẩm tồn kho",
                "Thoát"
        };
        int choice = MenuOutput.showMenu(title, items, input);
        return choice;
    }

    public void dailyStatistic() throws IOException {
        System.out.println(UI.bold("THỐNG KÊ HÓA ĐƠN THEO NGÀY"));
        String saleDate;
        boolean check = false;
        System.out.println("| " + UI.infoString("[i] Nhập ngày theo định dạng sau: dd/MM/yyyy"));
        do {

            System.out.print("| Nhập ngày bán: ");
            saleDate = input.nextLine();
            check = Utils.checkValidDate(saleDate, "dd/MM/yyyy");
            if (!check) {
                System.out.println("| " + UI.errorString("[!] Ngày không hợp lệ!"));
            }
        } while (!check);
        ArrayList<Bill> result = this.billController.getDailyBill(saleDate);
        if (result.size() == 0) {
            System.out.println("| " + UI.italic("[->] Không tìm thấy hóa đơn nào!"));
        } else {
            BillOutput.billTable(result);
        }
        String fileName = checkGetReport();
        if(fileName.equals("")) return;
        boolean test = BillService.ExportStatisticReport(result,fileName);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Xuất file thành công!"));
        }
    }

    public void monthlyStatistic() throws IOException {
        System.out.println(UI.bold("THỐNG KÊ HÓA ĐƠN THEO THÁNG"));
        int month;
        boolean check = false;
        do {
            System.out.print("| Nhập tháng: ");
            String tmp = input.nextLine();
            try {
                month = Integer.parseInt(tmp);
            } catch (NumberFormatException e) {
                month = 0;
            }
            check = Utils.checkValidMonth(month);
        } while (!check);
        ArrayList<Bill> result = this.billController.getMonthlyBill(month);
        if (result.size() == 0) {
            System.out.println("| " + UI.italic("[->] Không tìm thấy hóa đơn nào!"));
        } else {
            BillOutput.billTable(result);
        }
        String fileName = checkGetReport();
        if(fileName.equals("")) return;
        boolean test = BillService.ExportStatisticReport(result,fileName);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Xuất file thành công!"));
        }
    }

    public void outStockStatistic() throws IOException {
        System.out.println(UI.bold("THỐNG KÊ SẢN PHẨM SẮP HẾT HÀNG"));
        ArrayList<Product> result = this.productController.findOutStock(10);
        if (result.size() == 0) {
            System.out.println("| " + UI.italic("[->] Không tìm thấy sản phẩm nào!"));
        } else {
            ProductOutput.productTable(result);
        }
        String fileName = checkGetReport();
        if(fileName.equals("")) return;
        boolean test = ProductService.ExportStatisticReport(result,fileName);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Xuất file thành công!"));
        }
    }

    public void inventoryStatistic() throws IOException {
        System.out.println(UI.bold("THỐNG KÊ SẢN PHẨM TỒN KHO NHIỀU"));
        ArrayList<Product> result = this.productController.findInventory(100);
        if (result.size() == 0) {
            System.out.println("| " + UI.italic("[->] Không tìm thấy sản phẩm nào!"));
        } else {
            ProductOutput.productTable(result);
        }
        String fileName = checkGetReport();
        if(fileName.equals("")) return;
        boolean test = ProductService.ExportStatisticReport(result,fileName);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Xuất file thành công!"));
        }
    }

    public void staffRevenueStatistic() throws IOException {
        ArrayList<StaffRevenue> result = new ArrayList<StaffRevenue>();
        System.out.println(UI.bold("THỐNG KÊ DOANH THU NHÂN VIÊN"));
        Map<String, Long> totalRevenueByStaff = this.billController.getStaffRevenueMap();
        for(Staff staff : this.staffService.getAll()){
            long totalRevenue = 0;
            String staffCode = staff.getCode();
            if (totalRevenueByStaff.containsKey(staffCode)) {
               totalRevenue = totalRevenueByStaff.get(staffCode);
            }
            StaffRevenue tmp = new StaffRevenue(staffCode,staff.getName(),totalRevenue);
            result.add(tmp);
        }
        StaffOutput.staffRevenueTable(result);
        String fileName = checkGetReport();
        if(fileName.equals("")) return;
        boolean test = StaffService.ExportStatisticReport(result,fileName);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Xuất file thành công!"));
        }
    }


    public void statisticManage() throws IOException {
        if (!this.productController.checkInit()) this.productController.init();
        if (!this.billController.checkInit()) this.billController.init();
        int choice;
        do {
            choice = showStatisticMenu();
            switch (choice) {
                case 1:
                    dailyStatistic();
                    break;
                case 2:
                    monthlyStatistic();
                    break;
                case 3:
                    staffRevenueStatistic();
                    break;
                case 4:
                    outStockStatistic();
                    break;
                case 5:
                    inventoryStatistic();
                    break;
                case 6:
                    break;

            }
        } while (choice != 6);
    }
}
