package Controllers;

import Models.Product;
import Output.MenuOutput;
import Output.ProductOutput;
import Services.ProductService;
import Utils.Color;
import Utils.UI;

import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {
    private ArrayList<Product> productsList;
    private Scanner input;

    public ProductController() {
        input = new Scanner(System.in);
        productsList = null;
    }

    public ProductController(Scanner input) {
        this.input = input;
        productsList = null;
    }

    public void init() {
        this.productsList = ProductService.genProductsList();
    }

    public boolean checkInit() {
        return this.productsList.size() != 0;
    }

    public int checkExistedCode(String code) {
        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getProductCode().equals(code)) {
                return i;
            }
        }
        return -1;
    }

    public Product getProduct(int i) {
        return this.productsList.get(i);
    }

    ArrayList<Product> seachByCode(String code) {
        ArrayList<Product> result = new ArrayList<Product>();
        for (int i = 0; i < productsList.size(); i++) {
            Product product = this.productsList.get(i);
            if (product.getProductCode().equals(code)) {
                result.add(product);
            }
            ;
        }
        return result;
    }

    public void exportData() {
        ProductService.ExportProductsData(this.productsList);
    }

    public ArrayList<Product> findOutStock(int limit) {
        ArrayList<Product> result = new ArrayList<Product>();
        for (int i = 0; i < productsList.size(); i++) {
            Product product = this.productsList.get(i);
            if (product.getQuantity() < limit) {
                result.add(product);
            }
            ;
        }
        return result;
    }

    public ArrayList<Product> findInventory(int limit) {
        ArrayList<Product> result = new ArrayList<Product>();
        for (int i = 0; i < productsList.size(); i++) {
            Product product = this.productsList.get(i);
            if (product.getQuantity() > limit) {
                result.add(product);
            }
            ;
        }
        return result;
    }

    /*Hàm 'inputProduct' phục vụ cho 2 function Thêm(addProduct) và Sửa(editProduct), có nhiệm vụ lấy Input từ màn hình Console
     * Hàm nhận parameter là 'product' để phân biệt giữa Thêm và Sửa
     * Nếu 'product' bằng null (tức Thêm), hàm sẽ kiểm tra đầu vào tương ứng như trong yêu cầu của đề bài
     * Nếu 'product' khác null (tức Sửa), hàm sẽ có thêm 1 chức năng nữa là "Nhấn Enter khi không muốn thay đổi giá trị đang nhập",
     * khi đó hàm sẽ lấy giá trị của product cũ tương ứng với giá trị đang nhập.
     * */
    Product inputProduct(Product product) {
        int check = -1;
        String productCode = "";
        String name = "";
        int quantity = 0, price = 0;
        //Thêm mã sản phẩm
        if (product == null)
            do {
                System.out.print("| Nhập mã sản phẩm: ");
                productCode = input.nextLine();
                if (productCode.equals("")) {
                        System.out.println("| " + UI.errorString("[!] Mã không hợp lệ!"));
                }
                check = checkExistedCode(productCode);
                if (check != -1) {
                    System.out.println("| " + UI.errorString("[!] Mã sản phẩm đã tồn tại!"));
                }
            } while (check != -1 || productCode.equals(""));
        else productCode = product.getProductCode();
        //Thêm tên sản phẩm
        do {
            System.out.print("| Nhập tên sản phẩm: ");
            name = input.nextLine();
            if (name.equals("")) {
                if (product != null) {
                    name = product.getName();
                    break;
                } else
                    System.out.println("| " + UI.errorString("[!] Tên không hợp lệ!"));
            }
        } while (name.equals(""));

        //Thêm số lượng sản phẩm
        do {
            System.out.print("| Nhập số lượng: ");
            String tmp = input.nextLine();
            if (tmp.equals("") && product != null) {
                quantity = product.getQuantity();
                break;
            }
            try {
                quantity = Integer.parseInt(tmp);
            } catch (NumberFormatException e) {
                quantity = 0;
            }
            if (quantity <= 0) {
                System.out.println("| " + UI.errorString("[!] Số lượng sản phẩm phải lớn hơn 0"));
            }
        } while (quantity <= 0);

        //Thêm giá sản phẩm
        do {
            System.out.print("| Nhập giá sản phẩm: ");
            String tmp = input.nextLine();
            if (tmp.equals("") && product != null) {
                price = product.getPrice();
                break;
            }
            try {
                price = Integer.parseInt(tmp);
            } catch (NumberFormatException e) {
                price = 0;
            }
            if (price <= 0) {
                System.out.println("| " + UI.errorString("[!] Giá sản phẩm phải lớn hơn 0"));
            }
        } while (price <= 0);
        Product newProduct = new Product(productCode, name, quantity, price);
        return newProduct;
    }

    public void addProduct() {

        System.out.println(UI.bold("THÊM SẢN PHẨM"));
        Product product = inputProduct(null);
        this.productsList.add(product);
        boolean check = ProductService.addProduct(product);
        if (check) {
            System.out.println("| " + UI.successString("[OK] Thêm sản phẩm thành công!"));
        }
    }

    public void viewProductList() {
        System.out.println(UI.bold("XEM DANH SÁCH SẢN PHẨM"));
        ProductOutput.productTable(this.productsList);
    }

    public void editProduct() {
        System.out.println(UI.bold("SỬA SẢN PHẨM"));
        String productCode;
        int check = -1;
        do {
            System.out.print("| Nhập mã sản phẩm (Hoặc enter để hủy): ");
            productCode = input.nextLine();
            if (productCode.equals("")) return;
            check = checkExistedCode(productCode);
            if (check == -1) {
                System.out.println("| " + UI.errorString("[!] Mã sản phẩm không tồn tại!"));
            }
        } while (check == -1);
        Product product = this.productsList.get(check); 
        ProductOutput.productTable(product);
        System.out.println("| " + UI.infoString("[i] Nhấn Enter nếu không muốn thay đổi giá trị cần nhập"));
        Product newProduct = inputProduct(product);
        this.productsList.set(check, newProduct);
        boolean test = ProductService.ExportProductsData(productsList);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Sửa sản phẩm thành công!"));
        }
    }

    public void deleteProduct() {
        System.out.println(UI.bold("XÓA SẢN PHẨM"));
        String productCode;
        int check = -1;
        do {
            System.out.print("| Nhập mã sản phẩm (Hoặc enter để hủy): ");
            productCode = input.nextLine();
            if (productCode.equals("")) return;
            check = checkExistedCode(productCode);
            if (check == -1) {
                System.out.println("| " + UI.errorString("[!] Mã sản phẩm không tồn tại!"));
            }
        } while (check == -1);
        productsList.remove(check);
        boolean test = ProductService.ExportProductsData(productsList);
        if (test) {
            System.out.println("| " + UI.successString("[OK] Xóa sản phẩm thành công!"));
        }
    }


    //Hàm này có thể search fulltext
    public void searchProduct() {
        System.out.println(UI.bold("TÌM THEO MÃ SẢN PHẨM"));
        String productCode;
        do {
            System.out.print("| Nhập mã sản phẩm: ");
            productCode = input.nextLine();
            if (productCode.equals("")) {
                System.out.println("| " + UI.errorString("[!] Mã không hợp lệ!"));
            }
        } while (productCode.equals(""));

        ArrayList<Product> result = seachByCode(productCode);
        if (result.size() == 0) {
            System.out.println("| " + UI.italic("[->] Không tìm thấy sản phẩm nào!"));
        } else {
            ProductOutput.productTable(result);
        }
    }

    public int showProductManagementMenu() {
        String title = "Quản lý sản phẩm";
        String[] items = {
                "Xem danh sách sản phẩm.",
                "Tìm kiếm sản phẩm.",
                "Thêm mới sản phẩm.",
                "Sửa thông tin sản phẩm.",
                "Xóa thông tin sản phẩm.",
                "Thoát."
        };
        int choice = MenuOutput.showMenu(title, items, input);
        return choice;
    }

    public void productManage() {
        int choice;
        do {
            choice = showProductManagementMenu();
            switch (choice) {
                case 1:
                    this.viewProductList();
                    break;
                case 2:
                    this.searchProduct();
                    break;
                case 3:
                    this.addProduct();
                    break;
                case 4:
                    this.editProduct();
                    break;
                case 5:
                    this.deleteProduct();
                    break;
                case 6:
                    break;
                default:
                    break;
            }
        } while (choice != 6);
    }
}
