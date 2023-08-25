package Controllers;

import Models.Customer;
import Output.CustomerOutput;
import Output.MenuOutput;
import Services.CustomerService;
import Utils.UI;

import java.util.Scanner;

import Utils.Utils;

public class CustomerController {
    private CustomerService customerService = new CustomerService();
    private Scanner input;

    public CustomerController() {
        this.input = new Scanner(System.in);
    }

    public CustomerController(Scanner input) {
        this.input = input;
    }

    Customer inputCustomer(Customer customer) {
        String customerCode;
        Customer tmp = null;
        if (customer == null)
            do {
                System.out.print("| Nhập mã khách hàng: "); 
                customerCode = input.nextLine();
                if (customerCode.equals("")) {
                    System.out.println("| " + UI.errorString("[!] Mã không hợp lệ!"));
                }
                tmp = this.customerService.findByCode(customerCode);
                if (tmp != null) {
                    System.out.println("| " + UI.errorString("[!] Mã khách hàng đã tồn tại!"));
                }
            } while (tmp != null || customerCode.equals(""));
        else customerCode = customer.getCode();
        return inputCustomer(customerCode, customer);
    }

    public Customer inputCustomer(String customerCode, Customer customer) {
        boolean check;
        Customer tmp = null;
        String name, phoneNumber, email, address;
        do {
            System.out.print("| Nhập tên khách hàng: ");
            name = input.nextLine();
            if (name.equals("") && customer != null) {
                name = customer.getName();
                break;
            }
            check = Utils.checkValidName(name);

            if (!check) System.out.println("| " + UI.errorString("[!] Tên không hợp lệ!"));

        } while (!check);

        do {
            System.out.print("| Nhập số điện thoai: ");
            phoneNumber = input.nextLine();
            if (phoneNumber.equals("") && customer != null) {
                phoneNumber = customer.getPhoneNumber();
                break;
            }
            check = Utils.checkValidPhoneNumber(phoneNumber);
            if (!check) {
                System.out.println("| " + UI.errorString("[!] SDT không hợp lệ!"));
            } else {
                tmp = this.customerService.findByPhone(phoneNumber);
                if (tmp != null) {
                    System.out.println("| " + UI.errorString("[!] SDT đã tồn tại!"));
                }
            }
        } while (tmp != null || !check);

        do {
            System.out.print("| Nhập email: ");
            email = input.nextLine();
            if (email.equals("") && customer != null) {
                email = customer.getEmail();
                break;
            }
            check = Utils.checkValidEmail(email);
            if (!check) System.out.println("| " + UI.errorString("[!] Email không hợp lệ!"));
            else {
                tmp = this.customerService.findByEmail(email);
                if (tmp != null) {
                    System.out.println("| " + UI.errorString("[!] Email đã tồn tại!"));
                }
            }
        } while (tmp != null || !check);

        System.out.println("| " + UI.infoString("[i] Địa chỉ vui lòng không chứa dấu phẩy."));
        do {
            System.out.print("| Nhập địa chỉ: ");
            address = input.nextLine();
            if (address.equals("")) {
                if (customer != null) {
                    address = customer.getAddress();
                    break;
                } else
                    System.out.println("| " + UI.errorString("[!] Địa chỉ không hợp lệ!"));
            }
            if (address.contains(",")) System.out.println("| " + UI.errorString("[!] Địa chỉ không hợp lệ!"));
        } while (address.equals("") || address.contains(","));

        Customer newCustomer = new Customer(customerCode, name, phoneNumber, email, address);
        return newCustomer;
    }

    public void addCustomer() {
        System.out.println(UI.bold("THÊM KHÁCH HÀNG"));
        Customer customer = inputCustomer(null);
        boolean test = this.customerService.addCustomer(customer);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Thêm khách hàng thành công!"));
        }
    }

    public void editCustomer() {
        System.out.println(UI.bold("SỬA KHÁCH HÀNG"));
        String customerCode;
        Customer tmp;
        do {
            System.out.print("| Nhập mã khách hàng (Hoặc enter để hủy): ");
            customerCode = input.nextLine();
            if (customerCode.equals("")) return;
            tmp = this.customerService.findByCode(customerCode);
            if (tmp == null) {
                System.out.println("| " + UI.errorString("[!] Mã khách hàng không tồn tại!"));
            }
        } while (tmp == null);
        CustomerOutput.customerTable(tmp);
        System.out.println("| " + UI.infoString("[i] Nhấn Enter nếu không muốn thay đổi giá trị cần nhập"));
        Customer newCustomer = inputCustomer(tmp);
        boolean test = this.customerService.editCustomer(newCustomer);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Sửa khách hàng thành công!"));
        }
    }

    public void deleteCustomer() {
        System.out.println(UI.bold("XÓA KHÁCH HÀNG"));
        String customerCode;
        Customer tmp;
        do {
            System.out.print("| Nhập mã khách hàng (Hoặc enter để hủy): ");
            customerCode = input.nextLine();
            if (customerCode.equals("")) return;
            tmp = this.customerService.findByCode(customerCode);
            if (tmp == null) {
                System.out.println("| " + UI.errorString("[!] Mã khách hàng không tồn tại!"));
            }
        } while (tmp == null);
        boolean test = this.customerService.deleteCustomer(tmp);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Xóa khách hàng thành công!"));
        }
    }

    public void viewCustomerList() {
        CustomerOutput.customerTable(this.customerService.getAll());
    }

    public void findByCode() {
        String customerCode;
        do {
            System.out.print("| Nhập mã khách hàng: ");
            customerCode = input.nextLine();
            if (customerCode.equals("")) {
                System.out.println("| " + UI.errorString("[!] Mã không hợp lệ!"));
            }
        } while (customerCode.equals(""));
        Customer tmp = this.customerService.findByCode(customerCode);
        if (tmp == null) {
            System.out.println("| " + UI.italic("[->] Không tìm thấy khách hàng nào!"));
        } else {
            CustomerOutput.customerTable(tmp);
        }
    }

    public void findByEmail() {
        String email;
        boolean check = false;
        do {
            do {
                System.out.print("| Nhập email: ");
                email = input.nextLine();
                check = Utils.checkValidEmail(email);
                if (!check) System.out.println("| " + UI.errorString("[!] Email không hợp lệ!"));
            } while (!check);
        } while (!check);
        Customer tmp = this.customerService.findByEmail(email);
        if (tmp == null) {
            System.out.println("| " + UI.italic("[->] Không tìm thấy khách hàng nào!"));
        } else {
            CustomerOutput.customerTable(tmp);
        }
    }

    public void findByPhoneNumber() {
        String phoneNumber;
        boolean check = false;
        do {
            System.out.print("| Nhập số điện thoai: ");
            phoneNumber = input.nextLine();
            check = Utils.checkValidPhoneNumber(phoneNumber);
            if (!check) System.out.println("| " + UI.errorString("[!] SDT không hợp lệ!"));
        } while (!check);
        Customer tmp = this.customerService.findByPhone(phoneNumber);
        if (tmp == null) {
            System.out.println("| " + UI.italic("[->] Không tìm thấy khách hàng nào!"));
        } else {
            CustomerOutput.customerTable(tmp);
        }
    }

    public String upsertCustomer() {
        String code;
        do {
            System.out.print("| Nhập mã khách hàng: ");
            code = input.nextLine();
            if (code.equals("")) System.out.println("| " + UI.errorString("[!] Mã không hợp lệ"));
        } while (code.equals(""));
        Customer tmp = customerService.findByCode(code);
        if (tmp != null) return code;

        System.out.println("|");
        System.out.println("| " + UI.italic("[->] Chưa có khách hàng, tiến hành thêm khách hàng!"));
        Customer newCustomer = inputCustomer(code, null);
        boolean test = this.customerService.addCustomer(newCustomer);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Thêm khách hàng thành công!"));
        }
        return code;
    }

    public int showCustomerManagementMenu() {
        String title = "Quản lý khách hàng";
        String[] items = {
                "Xem danh sách khách hàng.",
                "Tìm kiếm theo mã khách hàng.",
                "Tìm kiếm theo sđt.",
                "Tìm kiếm theo email.",
                "Thêm mới khách hàng.",
                "Sửa thông tin khách hàng.",
                "Xóa thông tin khách hàng.",
                "Thoát."
        };
        int choice = MenuOutput.showMenu(title, items, input);
        return choice;
    }

    public void customerManage() {
        int choice;
        do {
            choice = showCustomerManagementMenu();
            switch (choice) {
                case 1:
                    this.viewCustomerList();
                    break;
                case 2:
                    this.findByCode();
                    break;
                case 3:
                    this.findByPhoneNumber();
                    break;
                case 4:
                    this.findByEmail();
                    break;
                case 5:
                    this.addCustomer();
                    break;
                case 6:
                    this.editCustomer();
                    break;
                case 7:
                    this.deleteCustomer();
                    break;
                case 8:
                    break;
            }
        } while (choice != 8);
    }

}
