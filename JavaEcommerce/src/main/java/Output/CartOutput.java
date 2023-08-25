package Output;

import Models.Bill;
import Models.CartItem;
import Models.Product;

import java.util.ArrayList;

public class CartOutput {
    public static void cartTable(ArrayList<CartItem> cart) {
        String format = "| %-15s | %-40s | %-10d | %-10d |%n";
        String totalFormat = "| %-58s | %-23d |%n";
        long total=0;
        System.out.format("+-----------------+------------------------------------------+------------+------------+%n");
        System.out.format("| Ma San Pham     | Ten                                      | Don Gia    | So Luong   |%n");
        System.out.format("+-----------------+------------------------------------------+------------+------------+%n");
        for (int i = 0; i < cart.size(); i++) {
            CartItem cartItem = cart.get(i);
            String name = cartItem.getProduct().getName();
            if (name.length() > 40) name = name.substring(0, 37) + "...";
            total += (cartItem.getQuantity()*cartItem.getProduct().getPrice());
            System.out.format(format, cartItem.getProduct().getProductCode(), name, cartItem.getProduct().getPrice(), cartItem.getQuantity());
        }
        System.out.format("+-----------------+------------------------------------------+------------+------------+%n");
        System.out.format(totalFormat,"Tổng giá trị đơn hàng",total);
        System.out.format("+------------------------------------------------------------+-------------------------+%n");
    }

    public static void billTable(ArrayList<CartItem> cart) {
        long tmp = 0;
        for (int j = 0; j < cart.size(); j++) {
            tmp += cart.get(j).getQuantity() * cart.get(j).getProduct().getPrice();
        }
        String format = "| %-15s | %-40s | %-10d | %-10d | %-15d | %-15s |%n";
        System.out.format("+-----------------+------------------------------------------+------------+------------+-----------------+-----------------+%n");
        System.out.format("| Ma san pham     | Ten san pham                             | So Luong   | Don Gia    | Thanh Tien      | Tong tien       |%n");
        System.out.format("+-----------------+------------------------------------------+------------+------------+-----------------+-----------------+%n");
        for (int i = 0; i < cart.size(); i++) {
            CartItem cartItem = cart.get(i);
            String name = cartItem.getProduct().getName();
            if (name.length() > 40) name = name.substring(0, 37) + "...";
            String totalForAll;
            if (i == 0) {
                totalForAll = tmp + "";
            } else totalForAll = "";
            System.out.format(format, cartItem.getProduct().getProductCode(), name, cartItem.getQuantity(), cartItem.getProduct().getPrice(), cartItem.getQuantity() * cartItem.getProduct().getPrice(), totalForAll);
        }
        System.out.format("+-----------------+------------------------------------------+------------+------------+-----------------+-----------------+%n");
    }
}
