package Models;

import Utils.Gender;

public class Staff {
    private String code;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String address;
    private Gender gender;
    private String group;
    private int status;

    public Staff() {
    }

    public Staff(String code, String name, String phoneNumber, String email, String password, String address, Gender gender, String group, int status) {
        this.code = code;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.group = group;
        this.status = status;
    }

    public Staff(String code, String name, String phoneNumber, String email, String password, String address, Gender gender, String group) {
        this.code = code;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.group = group;
        this.status = 1;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return code+","+name+","+phoneNumber+","+email+","+password+","+address+","+gender.name()+","+group+","+status;
    }
}
