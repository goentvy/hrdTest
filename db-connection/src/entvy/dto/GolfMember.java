package entvy.dto;

public class GolfMember {
    private int mNo;
    private String mName;
    private String phone;
    private String joinDate;
    private String grade;

    public GolfMember(int mNo, String mName, String phone, String joinDate, String grade) {
        this.mNo = mNo;
        this.mName = mName;
        this.phone = phone;
        this.joinDate = joinDate;
        this.grade = grade;
    }

    public int getMNo() { return mNo; }
    public String getMName() { return mName; }
    public String getPhone() { return phone; }
    public String getJoinDate() { return joinDate; }
    public String getGrade() { return grade; }
}