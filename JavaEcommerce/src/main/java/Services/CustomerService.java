package Services;

import Models.Customer;
import Models.Customer;
import Utils.Gender;
import Utils.Md5;
import Utils.UI;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class CustomerService {
    // private static String filePath = "src/main/java/Database/khachhang.txt";
    private static String filePath = "E:\\Java\\JavaEcommerce\\src\\main\\java\\Database\\khachhang.txt";

    private static ArrayList<Customer> customerList;

    public CustomerService() {
        if(customerList == null) customerList = genCustomersList();
    }

    public boolean addCustomer(Customer customer){
        customerList.add(customer);
        return CustomerService.addCustomerData(customer);
    }

    public boolean editCustomer(Customer customer){
        int i;
        for(i=0; i<customerList.size();i++){
            Customer st = customerList.get(i);
            if(st.getCode().equals(customer.getCode())){
                break;
            };
        }
        customerList.set(i,customer);
        return CustomerService.ExportCustomersData(customerList);
    }

    public boolean deleteCustomer(Customer customer){
        customerList.remove(customer);
        return CustomerService.ExportCustomersData(customerList);
    }

    public Customer findByCode(String code){
        for(int i=0; i<customerList.size();i++){
            Customer customer = customerList.get(i);
            if(customer.getCode().equals(code)){
                return customer;
            };
        }
        return null;
    }

    public Customer findByEmail(String email){
        for(int i=0; i<customerList.size();i++){
            Customer customer = customerList.get(i);
            if(customer.getEmail().equals(email)){
                return customer;
            };
        }
        return null;
    }

    public Customer findByPhone(String phone){
        for(int i=0; i<customerList.size();i++){
            Customer customer = customerList.get(i);
            if(customer.getPhoneNumber().equals(phone)){
                return customer;
            };
        }
        return null;
    }

    public ArrayList<Customer> getAll(){
        return customerList;
    }


    public static ArrayList<Customer> genCustomersList(){
        ArrayList<Customer> customersList = new ArrayList<Customer>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                count ++;
                Customer tmp = new Customer();
                String values[] = line.split(",");
                if(values.length!=5){
                    System.out.println("Lỗi khi đọc file ở dòng thứ "+count);
                    continue;
                }
                try {
                    tmp.setCode(values[0]);
                    tmp.setName(values[1]);
                    tmp.setPhoneNumber(values[2]);
                    tmp.setEmail(values[3]);
                    tmp.setAddress(values[4]);

                } catch (NumberFormatException e) {
                    System.out.println("Lỗi khi đọc file ở dòng thứ " + count);
                    continue;
                }
                customersList.add(tmp);
            }
            return customersList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addCustomerData(Customer customer){
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(customer.toString());
            fw.write(System.lineSeparator());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi thêm sản phẩm vào file");
            return false;
        }
    }

    public static boolean ExportCustomersData(ArrayList<Customer> customersList){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for(int i=0;i<customersList.size();i++) {
                Customer customer = customersList.get(i);
                bw.write(customer.toString());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi xuất dữ liệu ra file");
            return false;
        }
    }
}
