package entvy.dto;

public class ShopMember {
    private int custNo;
    private String custName;
    private String phone;
    private String address;
    private String joinDate;
    private String grade;
    private String city;

    public ShopMember(int custNo, String custName, String phone, String address, String joinDate, String grade, String city) {
        this.custNo = custNo;
        this.custName = custName;
        this.phone = phone;
        this.address = address;
        this.joinDate = joinDate;
        this.grade = grade;
        this.city = city;
    }

    public int getCustNo() { return custNo; }
    public String getCustName() { return custName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getJoinDate() { return joinDate; }
    public String getGrade() { return grade; }
    public String getCity() { return city; }
}