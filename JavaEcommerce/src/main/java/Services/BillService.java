package Services;

import Models.Bill;
import Utils.UI;

import java.io.*;
import java.util.ArrayList;

public class BillService {
    // private static String filePath = "src/main/java/Database/hoadon.txt";
    // private static final String reportDir = "src/main/public/report/";
    private static String filePath = "E:\\Java\\JavaEcommerce\\src\\main\\java\\Database\\hoadon.txt";
    private static final String reportDir = "E:\\Java\\JavaEcommerce\\src\\main\\public\\report";
    
    public static ArrayList<Bill> genBillsList() { 
        ArrayList<Bill> billsList = new ArrayList<Bill>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;
                Bill tmp = new Bill();
                String values[] = line.split(",");
                if (values.length != 7) {
                    System.out.println("Lỗi khi đọc file ở dòng thứ " + count);
                    continue;
                }
                try {
                    int price = Integer.parseInt(values[4]);
                    int quantity = Integer.parseInt(values[3]);
                    long total = Long.parseLong(values[5]);
                    tmp.setProductCode(values[2]);
                    tmp.setCustomerCode(values[0]);
                    tmp.setStaffCode(values[1]);
                    tmp.setPrice(price);
                    tmp.setQuantity(quantity);
                    tmp.setTotal(total);
                    tmp.setSaleDate(values[6]);

                } catch (NumberFormatException e) {
                    System.out.println("Lỗi khi đọc file ở dòng thứ " + count);
                    continue;
                }
                billsList.add(tmp);
            }
            return billsList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addBillsList(ArrayList<Bill> billsList) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            for (int i = 0; i < billsList.size(); i++) {
                Bill bill = billsList.get(i);
                fw.write(bill.toString());
                fw.write(System.lineSeparator());
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi thêm sản phẩm vào file");
            return false;
        }
    }

    public static boolean ExportStatisticReport(ArrayList<Bill> billsList, String fileName) throws IOException {
        File file = new File(reportDir + fileName + ".csv");
        if (!file.exists()) {
            file.createNewFile();
        } else {
            System.out.println("| " + UI.errorString("[!] File da ton tai!"));
            return false;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("Ma khach hang,Ma nhan vien,Ma san pham,So luong,Don gia,Thanh tien,Ngay ban");
            bw.newLine();
            for (int i = 0; i < billsList.size(); i++) {
                Bill bill = billsList.get(i);
                bw.write(bill.toString());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
//    public static boolean ExportBillsData(ArrayList<Bill> billsList) {
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
//            for (int i = 0; i < billsList.size(); i++) {
//                Bill bill = billsList.get(i);
//                bw.write(bill.toString());
//                bw.newLine();
//            }
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Lỗi khi xuất dữ liệu ra file");
//            return false;
//        }
//    }
}
