package entvy.dto;

public class Usage {
    private int uNo;
    private int mNo;
    private String uDate;
    private int uTime;
    private int cost;

    public Usage(int uNo, int mNo, String uDate, int uTime, int cost) {
        this.uNo = uNo;
        this.mNo = mNo;
        this.uDate = uDate;
        this.uTime = uTime;
        this.cost = cost;
    }

    public int getUNo() { return uNo; }
    public int getMNo() { return mNo; }
    public String getUDate() { return uDate; }
    public int getUTime() { return uTime; }
    public int getCost() { return cost; }
}