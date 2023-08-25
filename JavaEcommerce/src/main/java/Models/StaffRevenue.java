package Models;

public class StaffRevenue {
    private String staffCode;
    private String staffName;
    private long revenue; // kiểu dữ liệu long chứa các số nguyên giá trị dương hoặc âm có độ dài lên đến 64 bit

    public StaffRevenue() {
    }

    public StaffRevenue(String staffCode, String staffName, long revenue) {
        this.staffCode = staffCode;
        this.staffName = staffName;
        this.revenue = revenue;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString(){
        return staffCode+","+staffName+","+revenue;
    }
}
