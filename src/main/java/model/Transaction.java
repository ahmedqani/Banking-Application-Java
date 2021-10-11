package model;

public class Transaction {
    private long id;
    private long amount;
    private String fromUserName;
    private TransactionType transactionType;
    private String toUserName;

    public Transaction() {
    }

    public Transaction(long amount, String fromUserName, TransactionType transactionType) {
        this.fromUserName = fromUserName;
        this.transactionType = transactionType;
        this.toUserName = "ATM";
        this.amount = amount;
    }

    public Transaction(long amount,String fromUserName, TransactionType transactionType, String toUserName) {
        this.fromUserName = fromUserName;
        this.transactionType = transactionType;
        this.toUserName = toUserName;
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getTransactionType() {
        if (transactionType == TransactionType.TRANSFER){
            return "TRANSFER";
        }
        if (transactionType == TransactionType.DEPOSIT){
            return "DEPOSIT";
        }else {
            return "WITHDRAW";
        }
    }

    public void setTransactionType(String transactionType) {
        if (transactionType.equalsIgnoreCase("transfer")){
            this.transactionType = TransactionType.TRANSFER ;
        }if (transactionType.equalsIgnoreCase("deposit")){
            this.transactionType = TransactionType.DEPOSIT ;
        }else {
            this.transactionType = TransactionType.WITHDRAW;
        }

    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", fromUserName='" + fromUserName + '\'' +
                ", transactionType=" + transactionType +
                ", toUserName='" + toUserName + '\'' +
                '}';
    }
}
