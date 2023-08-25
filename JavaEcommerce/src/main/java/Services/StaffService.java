package Services;

import Models.*;
import Models.Staff;
import Utils.Gender;
import Utils.Md5;
import Utils.UI;

import java.io.*;
import java.util.ArrayList;

public class StaffService {

    private static ArrayList<Staff> staffList;
    private static String filePath = "E:\\Java\\JavaEcommerce\\src\\main\\java\\Database\\nhanvien.txt";
    private static final String reportDir = "E:\\Java\\JavaEcommerce\\src\\main\\public\\report";
    // private static String filePath = "src/main/java/Database/nhanvien.txt";
    // private static final String reportDir = "src/main/public/report/";

    public StaffService() {
       if(staffList == null) staffList = genStaffsList();
    }

    public boolean checkValidUser(String staffCode, String password){
        for(int i=0;i<staffList.size();i++){
            if(staffList.get(i).getCode().equals(staffCode)){
                String storedHash = staffList.get(i).getPassword();
                String inputHash = Md5.hash(password);
                if(storedHash.equals(inputHash)){
                    return true;
                }
                else{
                    System.out.println("| "+ UI.errorString("[i] Sai mật khẩu!"));
                    return false;
                }
            }
        }
        System.out.println("| "+ UI.errorString("[i] Không tìm thấy nhân viên tương ứng với mã đã nhập!"));
        return false;
    }

    public boolean addStaff(Staff staff){
        String hash = Md5.hash(staff.getPassword());
        staff.setPassword(hash);
        staffList.add(staff);
        return StaffService.addStaffData(staff);
    }

    public boolean editStaff(Staff staff){
        int i;
        for(i=0; i<staffList.size();i++){
            Staff st = staffList.get(i);
            if(st.getCode().equals(staff.getCode())){
                if(!staff.getPassword().equals(st.getPassword())){
                    String hash = Md5.hash(staff.getPassword());
                    staff.setPassword(hash);
                }
                break;
            };
        }
        staffList.set(i,staff);
        return StaffService.ExportStaffsData(staffList);
    }

    public boolean deleteStaff(Staff staff){
        staffList.remove(staff);
        return StaffService.ExportStaffsData(staffList);
    }

    public Staff findByCode(String code){
        for(int i=0; i<staffList.size();i++){
            Staff staff = staffList.get(i);
            if(staff.getCode().equals(code)){
                return staff;
            };
        }
        return null;
    }

    public Staff findByEmail(String email){
        for(int i=0; i<staffList.size();i++){
            Staff staff = staffList.get(i);
            if(staff.getEmail().equals(email)){
                return staff;
            };
        }
        return null;
    }

    public Staff findByPhone(String phone){
        for(int i=0; i<staffList.size();i++){
            Staff staff = staffList.get(i);
            if(staff.getPhoneNumber().equals(phone)){
                return staff;
            };
        }
        return null;
    }

    public ArrayList<Staff> getAll(){
        return staffList;
    }


    public static ArrayList<Staff> genStaffsList(){
        ArrayList<Staff> staffsList = new ArrayList<Staff>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                count ++;
                Staff tmp = new Staff();
                String values[] = line.split(",");
                if(values.length!=9){
                    System.out.println("Lỗi khi đọc file ở dòng thứ "+count);
                    continue;
                }
                try {
                    int status = Integer.parseInt(values[8]);
                    tmp.setCode(values[0]);
                    tmp.setName(values[1]);
                    tmp.setPhoneNumber(values[2]);
                    tmp.setEmail(values[3]);
                    tmp.setPassword(values[4]);
                    tmp.setAddress(values[5]);
                    tmp.setGender(Gender.valueOf(values[6]));
                    tmp.setGroup(values[7]);
                    tmp.setStatus(status);

                } catch (NumberFormatException e) {
                    System.out.println("Lỗi khi đọc file ở dòng thứ " + count);
                    continue;
                }
                staffsList.add(tmp);
            }
            return staffsList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addStaffData(Staff staff){
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(staff.toString());
            fw.write(System.lineSeparator());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi thêm sản phẩm vào file");
            return false;
        }
    }

    public static boolean ExportStaffsData(ArrayList<Staff> staffsList){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for(int i=0;i<staffsList.size();i++) {
                Staff staff = staffsList.get(i);
                bw.write(staff.toString());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi xuất dữ liệu ra file");
            return false;
        }
    }

    public static boolean ExportStatisticReport(ArrayList<StaffRevenue> list, String fileName) throws IOException {
        File file = new File(reportDir+fileName+".csv");
        if (!file.exists()) {
            file.createNewFile();
        }else {
            System.out.println("| " + UI.errorString("[!] File da ton tai!"));
            return false;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("Ma nhan vien,Ten nhan vien,Doanh thu");
            bw.newLine();
            for (int i = 0; i < list.size(); i++) {
                StaffRevenue tmp = list.get(i);
                bw.write(tmp.toString());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
