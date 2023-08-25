import Controllers.*;
import Output.MenuOutput;
import Utils.UI;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static int showMainMenu(Scanner input) {
        String title = "JAVA ECOMMERCE";
        String[] items = {
                "Quản lý khách hàng",
                "Quản lý nhân viên",
                "Quản lý sản phẩm",
                "Quản lý bán hàng",
                "Báo cáo thống kê",
                "Đăng xuất"
        };
        int choice = MenuOutput.showMenu(title, items, input);
        return choice;
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        int choice;
        boolean isContinue = true;
        CustomerController customerController = new CustomerController(input);
        ProductController controller = new ProductController();
        controller.init();
        BillController billController = new BillController();
        billController.init();
        StaffController staffController = new StaffController();
        StatisticController statisticController = new StatisticController(billController, controller, input);
        String staffCode = "";
        do {
            do {
                staffCode = staffController.login();
            } while (staffCode == "");
            do {
                choice = showMainMenu(input);
                switch (choice) {
                    case 1:
                        customerController.customerManage();
                        break;
                    case 2:
                        staffController.staffManager();
                        break;
                    case 3:
                        controller.productManage();
                        break;
                    case 4:
                        System.out.println(UI.bold("QUẢN LÝ BÁN HÀNG"));
                        String customerCode = customerController.upsertCustomer();
                        SaleController saleController = new SaleController(customerCode, staffCode, billController, controller, input);
                        saleController.saleManager();
                        break;
                    case 5:
                        statisticController.statisticManage();
                        break;
                    case 6:
                        System.out.println("| " + UI.infoString("[i] Nhấn Enter để Đồng ý và phím bất kì để Từ chối"));
                        System.out.print("| " + UI.bold("Bạn có muốn tiếp tục không?: "));
                        String ch = input.nextLine();
                        if (!ch.equals("")) {
                            System.out.println("| " + UI.successString("Từ chối"));
                            isContinue = false;
                        } else {
                            isContinue = true;
                            System.out.println();
                        }
                        break;

                }
            } while (choice != 6);
        } while (isContinue);
    }
}