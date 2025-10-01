package entvy.dto;

public class Sale {
    private int saleNo;
    private int custNo;
    private int pCost;
    private int amount;
    private int price;
    private String pCode;

    public Sale(int saleNo, int custNo, int pCost, int amount, int price, String pCode) {
        this.saleNo = saleNo;
        this.custNo = custNo;
        this.pCost = pCost;
        this.amount = amount;
        this.price = price;
        this.pCode = pCode;
    }

    public int getSaleNo() { return saleNo; }
    public int getCustNo() { return custNo; }
    public int getPCost() { return pCost; }
    public int getAmount() { return amount; }
    public int getPrice() { return price; }
    public String getPCode() { return pCode; }
}