package entvy.tableclass;

public class Member {
	private int memberid;
	private String name;
	private String address;
	private String phone;
	
	// 생성자 (전체 필드)
	public Member(int memberid, String name, String phone, String address) {
		this.memberid = memberid;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}
	
	// Getter
	public int getMemberid() {
        return memberid;
    }

    public String getName() {
        return name;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getAddress() {
        return address;
    }
}
