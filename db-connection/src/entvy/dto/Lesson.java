package entvy.dto;

public class Lesson {
    private int lNo;
    private int mNo;
    private String coach;
    private String startDate;
    private int fee;

    public Lesson(int lNo, int mNo, String coach, String startDate, int fee) {
        this.lNo = lNo;
        this.mNo = mNo;
        this.coach = coach;
        this.startDate = startDate;
        this.fee = fee;
    }

    public int getLNo() { return lNo; }
    public int getMNo() { return mNo; }
    public String getCoach() { return coach; }
    public String getStartDate() { return startDate; }
    public int getFee() { return fee; }
}