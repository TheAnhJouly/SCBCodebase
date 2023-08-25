package Services;
import Models.Product;
import Utils.UI;

import java.io.*;
import java.util.ArrayList;

public class ProductService  {
    // private static String filePath = "src/main/java/Database/sanpham.txt";
    // private static final String reportDir = "src/main/public/report/";
    private static String filePath = "E:\\Java\\JavaEcommerce\\src\\main\\java\\Database\\sanpham.txt";
    private static final String reportDir = "E:\\Java\\JavaEcommerce\\src\\main\\public\\report";
   public static ArrayList<Product> genProductsList(){
       ArrayList<Product> productsList = new ArrayList<Product>();
       try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
           String line;
           int count = 0;
           while ((line = br.readLine()) != null) {
               count ++;
               Product tmp = new Product();
               String values[] = line.split(",");
               if(values.length!=4){
                   System.out.println("Lỗi khi đọc file ở dòng thứ "+count);
                   continue;
               }
               try {
                   int price = Integer.parseInt(values[3]);
                   int quantity = Integer.parseInt(values[2]);
                   tmp.setProductCode(values[0]);
                   tmp.setName(values[1]);
                   tmp.setPrice(price);
                   tmp.setQuantity(quantity);

               } catch (NumberFormatException e) {
                   System.out.println("Lỗi khi đọc file ở dòng thứ " + count);
                   continue;
               }
               productsList.add(tmp);
           }
           return productsList;
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

   public static boolean addProduct(Product product){
       try (FileWriter fw = new FileWriter(filePath, true)) {
           fw.write(product.toString());
           fw.write(System.lineSeparator());
           return true;
       } catch (IOException e) {
           e.printStackTrace();
           System.out.println("Lỗi khi thêm sản phẩm vào file");
           return false;
       }
   }

   public static boolean ExportProductsData(ArrayList<Product> productsList){
       try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
           for(int i=0;i<productsList.size();i++) {
               Product product = productsList.get(i);
               bw.write(product.toString());
               bw.newLine();
           }
           return true;
       } catch (IOException e) {
           e.printStackTrace();
           System.out.println("Lỗi khi xuất dữ liệu ra file");
           return false;
       }
   }

    public static boolean ExportStatisticReport(ArrayList<Product> productsList, String fileName) throws IOException {
        File file = new File(reportDir+fileName+".csv");
        if (!file.exists()) {
            file.createNewFile();
        }else {
            System.out.println("| " + UI.errorString("[!] File da ton tai!"));
            return false;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("Ma san pham,Ten san pham,So luong,Don gia");
            bw.newLine();
            for (int i = 0; i < productsList.size(); i++) {
                Product product = productsList.get(i);
                bw.write(product.toString());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
