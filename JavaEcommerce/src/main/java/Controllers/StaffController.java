package Controllers;

import Models.Staff;
import Output.MenuOutput;
import Output.StaffOutput;
import Services.StaffService;
import Utils.Gender;
import Utils.UI;
import Utils.Utils;

import java.util.Scanner;

public class StaffController {
    private StaffService staffService = new StaffService();
    private Scanner input;

    public StaffController() {
        this.input = new Scanner(System.in);
    }

    public StaffController(Scanner input) {
        this.input = input;
    }

    public String login() {
        System.out.println(UI.bold("ĐĂNG NHẬP"));
        String code = "";
        String password = "";
        do {
            System.out.print("| Nhập mã : ");
            code = input.nextLine();
            if (code.equals("")) {
                System.out.println("| " + UI.errorString("[!] Mã không hợp lệ!"));
            }
        } while (code.equals(""));
        do {
            System.out.print("| Nhập mật khẩu : ");
            password = input.nextLine();
            if (password.equals("")) {
                System.out.println("| " + UI.errorString("[!] Mật khẩu không hợp lệ!"));
            }
        } while (password.equals(""));
        boolean test = this.staffService.checkValidUser(code, password);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Đăng nhập thành công!"));
            return code;
        } else {
            return "";
        }
    }

    Staff inputStaff(Staff staff) {
        boolean check;
        String staffCode, name, phoneNumber, email, password, address, group;
        Gender gender = Gender.Khac;
        int status;
        Staff tmp = null;
        if (staff == null)
            do {
                System.out.print("| Nhập mã nhân viên: ");
                staffCode = input.nextLine();
                if (staffCode.equals("")) {
                    System.out.println("| " + UI.errorString("[!] Mã không hợp lệ!"));
                }
                tmp = this.staffService.findByCode(staffCode);
                if (tmp != null) {
                    System.out.println("| " + UI.errorString("[!] Mã nhân viên đã tồn tại!"));
                }
            } while (tmp != null || staffCode.equals(""));
        else staffCode = staff.getCode();
        do {
            System.out.print("| Nhập tên nhân viên: ");
            name = input.nextLine();
            if (name.equals("") && staff != null) {
                name = staff.getName();
                break;
            }
            check = Utils.checkValidName(name);

            if (!check) System.out.println("| " + UI.errorString("[!] Tên không hợp lệ!"));

        } while (!check);

        do {
            System.out.print("| Nhập số điện thoai: ");
            phoneNumber = input.nextLine();
            if (phoneNumber.equals("") && staff != null) {
                phoneNumber = staff.getPhoneNumber();
                break;
            }
            check = Utils.checkValidPhoneNumber(phoneNumber);
            if (!check){
                System.out.println("| " + UI.errorString("[!] SDT không hợp lệ!"));
            }else {
                tmp = this.staffService.findByPhone(phoneNumber);
                if (tmp != null) {
                    System.out.println("| " + UI.errorString("[!] SDT đã tồn tại!"));
                }
            }
        } while (tmp != null || !check);

        do {
            System.out.print("| Nhập email: ");
            email = input.nextLine();
            if (email.equals("") && staff != null) {
                email = staff.getEmail();
                break;
            }
            check = Utils.checkValidEmail(email);
            if (!check) System.out.println("| " + UI.errorString("[!] Email không hợp lệ!"));
            else{
                tmp = this.staffService.findByEmail(email);
                if (tmp != null) {
                    System.out.println("| " + UI.errorString("[!] Email đã tồn tại!"));
                }
            }
        } while (tmp != null || !check);

        do {
            System.out.print("| Nhập mật khẩu: ");
            password = input.nextLine();
            if (password.equals("")) {
                if (staff != null) {
                    password = staff.getPassword();
                    break;
                } else
                    System.out.println("| " + UI.errorString("[!] Mật khẩu không hợp lệ!"));
            }
        } while (password.equals(""));
        System.out.println("| " + UI.infoString("[i] Địa chỉ vui lòng không chứa dấu phẩy."));
        do {
            System.out.print("| Nhập địa chỉ: ");
            address = input.nextLine();
            if (address.equals("")) {
                if (staff != null) {
                    address = staff.getAddress();
                    break;
                } else
                    System.out.println("| " + UI.errorString("[!] Địa chỉ không hợp lệ!"));
            }
            if (address.contains(",")) System.out.println("| " + UI.errorString("[!] Địa chỉ không hợp lệ!"));
        } while (address.equals("") || address.contains(","));

        System.out.println("| " + UI.infoString("[i] 1 cho Nam, 2 cho Nu va 3 cho Khac"));
        int gen;
        do {
            System.out.print("| Nhập giới tính: ");
            String t = input.nextLine();
            if (t.equals("") && staff != null) {
                gender = staff.getGender();
                break;
            }
            try {
                gen = Integer.parseInt(t);
            } catch (NumberFormatException e) {
                gen = 0;
            }
            if (gen < 1 || gen > 3) {
                System.out.println("| " + UI.errorString("[!] Giá trị không hợp lệ"));
            } else {
                switch (gen) {
                    case 1:
                        gender = Gender.Nam;
                        break;
                    case 2:
                        gender = Gender.Nu;
                        break;
                    case 3:
                        gender = Gender.Khac;
                        break;
                    default:
                        break;
                }
            }
        } while (gen < 1 || gen > 3);

        do {
            System.out.print("| Nhập tên nhóm: ");
            group = input.nextLine();
            if (group.equals("")) {
                if (staff != null) {
                    group = staff.getGroup();
                    break;
                } else
                    System.out.println("| " + UI.errorString("[!] Tên nhóm không hợp lệ!"));
            }
        } while (group.equals(""));

        System.out.println("| " + UI.infoString("[i] 1 để active tài khoản, 0 để hủy active tài khoản"));
        do {
            System.out.print("| Nhập trạng thái: ");
            String t = input.nextLine();
            if (t.equals("") && staff != null) {
                status = staff.getStatus();
                break;
            }
            try {
                status = Integer.parseInt(t);
            } catch (NumberFormatException e) {
                status = -1;
            }
            if (status < 0 || status > 1) {
                System.out.println("| " + UI.errorString("[!] Giá trị không hợp lệ"));
            }
        } while (status < 0 || status > 1);

        Staff newStaff = new Staff(staffCode, name, phoneNumber, email, password, address, gender, group, status);
        return newStaff;
    }

    public void addStaff() {
        System.out.println(UI.bold("THÊM NHÂN VIÊN"));
        Staff staff = inputStaff(null);
        boolean test = this.staffService.addStaff(staff);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Thêm nhân viên thành công!"));
        }
    }

    public void editStaff() {
        System.out.println(UI.bold("SỬA NHÂN VIÊN"));
        String staffCode;
        Staff tmp;
        do {
            System.out.print("| Nhập mã nhân viên (Hoặc enter để hủy): ");
            staffCode = input.nextLine();
            if (staffCode.equals("")) return;
            tmp = this.staffService.findByCode(staffCode);
            if (tmp == null) {
                System.out.println("| " + UI.errorString("[!] Mã nhân viên không tồn tại!"));
            }
        } while (tmp == null);
        StaffOutput.staffTable(tmp);
        System.out.println("| " + UI.infoString("[i] Nhấn Enter nếu không muốn thay đổi giá trị cần nhập"));
        Staff newStaff = inputStaff(tmp);
        boolean test = this.staffService.editStaff(newStaff);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Sửa nhân viên thành công!"));
        }
    }

    public void deleteStaff() {
        System.out.println(UI.bold("XÓA NHÂN VIÊN"));
        String staffCode;
        Staff tmp;
        do {
            System.out.print("| Nhập mã nhân viên (Hoặc enter để hủy): ");
            staffCode = input.nextLine();
            if (staffCode.equals("")) return;
            tmp = this.staffService.findByCode(staffCode);
            if (tmp == null) {
                System.out.println("| " + UI.errorString("[!] Mã nhân viên không tồn tại!"));
            }
        } while (tmp == null);
        boolean test = this.staffService.deleteStaff(tmp);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Xóa nhân viên thành công!"));
        }
    }

    public void viewStaffList() {
        StaffOutput.staffTable(this.staffService.getAll());
    }

    public void findByCode() {
        String staffCode;
        do {
            System.out.print("| Nhập mã nhân viên: ");
            staffCode = input.nextLine();
            if (staffCode.equals("")) {
                System.out.println("| " + UI.errorString("[!] Mã không hợp lệ!"));
            }
        } while (staffCode.equals(""));
        Staff tmp = this.staffService.findByCode(staffCode);
        if (tmp == null) {
            System.out.println("| " + UI.italic("[->] Không tìm thấy nhân viên nào!"));
        } else {
            StaffOutput.staffTable(tmp);
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
        Staff tmp = this.staffService.findByEmail(email);
        if (tmp == null) {
            System.out.println("| " + UI.italic("[->] Không tìm thấy nhân viên nào!"));
        } else {
            StaffOutput.staffTable(tmp);
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
        Staff tmp = this.staffService.findByPhone(phoneNumber);
        if (tmp == null) {
            System.out.println("| " + UI.italic("[->] Không tìm thấy nhân viên nào!"));
        } else {
            StaffOutput.staffTable(tmp);
        }
    }

    public int showStaffManagementMenu() {
        String title = "Quản lý nhân viên";
        String[] items = {
                "Xem danh sách nhân viên.",
                "Tìm kiếm theo mã nhân viên.",
                "Tìm kiếm theo sđt.",
                "Tìm kiếm theo email.",
                "Thêm mới nhân viên.",
                "Sửa thông tin nhân viên.",
                "Xóa thông tin nhân viên.",
                "Thoát."
        };
        int choice = MenuOutput.showMenu(title, items, input);
        return choice;
    }

    public void staffManager() {
        int choice;
        do {
            choice = showStaffManagementMenu();
            switch (choice) {
                case 1:
                    this.viewStaffList();
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
                    this.addStaff();
                    break;
                case 6:
                    this.editStaff();
                    break;
                case 7:
                    this.deleteStaff();
                    break;
                case 8:
                    break;
            }
        } while (choice != 8);
    }
}
