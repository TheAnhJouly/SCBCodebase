package Output;

import Utils.Color;
import Utils.UI;

import java.util.Scanner;

public class MenuOutput {
    public static int showMenu(String title, String[] items, Scanner input) {
        int maxLength = title.length();
        for (int i = 0; i < items.length; i++) {
            if (items[i].length() + 4 > maxLength) maxLength = items[i].length() + 4; // cộng với 4 (đối với khoảng trắng cho dấu ngoặc và dấu ngoặc kép)
        }

        String line = "";
        for (int i = 0; i < maxLength + 2; i++) {
            line += "-";
        }
        line = UI.changeColor(Color.YELLOW, "+" + line + "+%n");
        String format = UI.changeColor(Color.YELLOW, "| %-" + maxLength + "s |%n");
        System.out.println();
        if (!title.equals("")) {
            System.out.format(line);
            System.out.format(format, title.toUpperCase());
        }
        System.out.format(line);
        for (int i = 0; i < items.length; i++) {
            System.out.format(format, (i + 1) + ". " + items[i]);
        }
        System.out.format(line);
        int choice;
        do {
            System.out.print("| Lựa chọn của bạn: ");
            String tmp = input.nextLine();
            try {
                choice = Integer.parseInt(tmp);
            } catch (NumberFormatException e) {
                choice = 0;
            }
            if (choice <= 0 || choice > items.length) {
                System.out.println("| " + UI.errorString("[!] Lựa chọn không hợp lệ!"));
            }
        } while (choice <= 0 || choice > items.length);
        System.out.println();
        return choice;
    }
}
