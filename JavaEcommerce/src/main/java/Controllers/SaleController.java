package Controllers;

import Models.Bill;
import Models.CartItem;
import Models.Product;
import Output.CartOutput;
import Output.MenuOutput;
import Output.ProductOutput;
import Services.BillService;
import Utils.UI;

import java.util.ArrayList;
import java.util.Scanner;

public class SaleController {
    private ProductController productController;
    private BillController billController;
    private ArrayList<CartItem> cart = null;

    private String customerCode;

    private String staffCode;

    private Scanner input;

    public SaleController() {
        input = new Scanner(System.in);
        this.cart = new ArrayList<CartItem>();
    }
    public SaleController(String customerCode,String staffCode,BillController billController,ProductController productController, Scanner input) {
        this.customerCode = customerCode;
        this.staffCode = staffCode;
        this.input = input;
        this.productController = productController;
        this.billController = billController;
        cart = new ArrayList<CartItem>();
    }

    public void Sale(){
        String productCode = "";
        int tmpQuantity;
        int check = -1;
        CartItem cartItem;
        do {
            System.out.print("| Nhập mã sản phẩm (Hoặc enter để hủy): ");
            productCode = input.nextLine();
            if (productCode.equals("")) return;
            check = this.productController.checkExistedCode(productCode);
            if (check == -1) {
                System.out.println("| " + UI.errorString("[!] Mã sản phẩm không tồn tại!"));
            }
        } while (check == -1);
        Product product = this.productController.getProduct(check);
        if(product.getQuantity() <=0){
            System.out.println("| " + UI.errorString("[!] Sản phẩm đã hết hàng!"));
            return;
        }
        ProductOutput.productTable(product);
        int index = this.findInCart(productCode);
        if(index == -1) {
            cartItem = new CartItem(product);
            cart.add(cartItem);
        }else{
            cartItem = this.cart.get(index);
        }
        //Thêm số lượng sản phẩm
        do {
            System.out.print("| Nhập số lượng: ");
            String tmp = input.nextLine();
            try {
                tmpQuantity = Integer.parseInt(tmp);
            } catch (NumberFormatException e) {
                tmpQuantity = 0;
            }
            if (tmpQuantity <= 0) {
                System.out.println("| " + UI.errorString("[!] Số lượng sản phẩm phải lớn hơn 0"));
            }else if(tmpQuantity > product.getQuantity()){
                System.out.println("| " + UI.errorString("[!] Số lượng phải nhỏ hơn lương sản phẩm còn lại"));
            }
        } while (tmpQuantity <= 0 || tmpQuantity > product.getQuantity());
        cartItem.setQuantity(cartItem.getQuantity()+tmpQuantity);
        product.setQuantity(product.getQuantity()-tmpQuantity);
        System.out.println("| " + UI.successString("[OK] Thêm vào giở hàng thành công!"));
    }

    public void Cancel(){
        for (int i = 0; i < cart.size(); i++) {
            CartItem cartItem = cart.get(i);
            cartItem.getProduct().setQuantity(cartItem.getProduct().getQuantity()+cartItem.getQuantity());
        }
    }

    public void Payment(){
        if(!this.billController.checkInit()){
            billController.init();
        }
        ArrayList<Bill> billsList = new ArrayList<Bill>();
        for(int i=0;i< cart.size();i++){
            Product product = cart.get(i).getProduct();
            Bill newBill = BillController.createBill(this.customerCode,this.staffCode,product.getProductCode(),cart.get(i).getQuantity(),product.getPrice());
            billsList.add(newBill);
            billController.addNewBill(newBill);
        }
        BillService.addBillsList(billsList);
        this.productController.exportData();
        System.out.println("| " + UI.successString("[OK] Thanh toán thành công!"));
        CartOutput.billTable(cart);
    }

    public int findInCart(String code){
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getProductCode().equals(code)) {
                return i;
            }
        }
        return -1;
    }

    public void viewCart(){
        CartOutput.cartTable(this.cart);
    }

    public int showSaleManagerMenu(){
        String title = "Quản lý bán hàng";
        String[] items = {
                "Tiếp tục mua hàng.",
                "Xem giỏ hàng.",
                "Thanh toán.",
                "Hủy."
        };
        int choice = MenuOutput.showMenu(title,items, input);
        return choice;
    }

    public void saleManager(){
        if(!this.productController.checkInit()){
            this.productController.init();
        }
        System.out.println(UI.bold("QUẢN LÝ BÁN HÀNG"));
        int choice;
        if(cart == null){
            System.out.println("| " + UI.errorString("[!] Chưa khởi tạo giỏ hàng"));
            return;
        }
        Sale();
        do{
            choice = showSaleManagerMenu();
            switch (choice){
                case 1:
                    Sale();
                    break;
                case 2:
                    viewCart();
                    break;
                case 3:
                    Payment();
                    break;
                case 4:
                    Cancel();
                    break;
            }
        }while (choice != 4 && choice != 3);
    }
}
