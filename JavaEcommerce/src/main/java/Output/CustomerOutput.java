package Output;

import Models.Customer;
import Models.Product;

import java.util.ArrayList;
import java.util.List;

public class CustomerOutput {
    public static void customerTable(List<Customer> customersList) {
        String format = "| %-15s | %-30s | %-15s | %-20s | %-30s |%n";
        System.out.format("+-----------------+--------------------------------+-----------------+----------------------+--------------------------------+%n");
        System.out.format("| Ma khach hang   | Ten                            | So dien thoai   |         Email        |        Dia chi                 |%n");
        System.out.format("+-----------------+--------------------------------+-----------------+----------------------+--------------------------------+%n");
        for (int i = 0; i < customersList.size(); i++) {
            Customer customer = customersList.get(i);
            String name = customer.getName();
            if (name.length() > 30) name = name.substring(0, 27) + "...";
            System.out.format(format, customer.getCode(), name, customer.getPhoneNumber(), customer.getEmail(), customer.getAddress());
        }
        System.out.format("+-----------------+--------------------------------+-----------------+----------------------+--------------------------------+%n");
    }

    public static void customerTable(Customer customer) {
        String format = "| %-15s | %-30s | %-15s | %-20s | %-30s |%n";
        System.out.format("+-----------------+--------------------------------+-----------------+----------------------+--------------------------------+%n");
        System.out.format("| Ma khach hang   | Ten                            | So dien thoai   |         Email        |        Dia chi                 |%n");
        System.out.format("+-----------------+--------------------------------+-----------------+----------------------+--------------------------------+%n");
        String name = customer.getName();
        if (name.length() > 30) name = name.substring(0, 27) + "...";
        System.out.format(format, customer.getCode(), name, customer.getPhoneNumber(), customer.getEmail(), customer.getAddress());
        System.out.format("+-----------------+--------------------------------+-----------------+----------------------+--------------------------------+%n");
    }
}
