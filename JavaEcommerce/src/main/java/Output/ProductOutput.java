package Output;

import Models.Product;

import java.util.ArrayList;

public class ProductOutput {
    public static void productTable(ArrayList<Product> productsList) {
        String format = "| %-15s | %-40s | %-10d | %-10d |%n";
        System.out.format("+-----------------+------------------------------------------+------------+------------+%n");
        System.out.format("| Ma San Pham     | Ten                                      | So Luong   | Don Gia    |%n");
        System.out.format("+-----------------+------------------------------------------+------------+------------+%n");
        for (int i = 0; i < productsList.size(); i++) {
            Product product = productsList.get(i);
            String name = product.getName();
            if (name.length() > 40) name = name.substring(0, 37) + "...";
            System.out.format(format, product.getProductCode(), name, product.getQuantity(), product.getPrice());
        }
        System.out.format("+-----------------+------------------------------------------+------------+------------+%n");
    }

    public static void productTable(Product product) {
        String format = "| %-15s | %-40s | %-10d | %-10d |%n";
        System.out.format("+-----------------+------------------------------------------+------------+------------+%n");
        System.out.format("| Ma San Pham     | Ten                                      | So Luong   | Don Gia    |%n");
        System.out.format("+-----------------+------------------------------------------+------------+------------+%n");
        String name = product.getName();
        if (name.length() > 40) name = name.substring(0, 37) + "...";
        System.out.format(format, product.getProductCode(), name, product.getQuantity(), product.getPrice());
        System.out.format("+-----------------+------------------------------------------+------------+------------+%n");
    }


}
