package model;

public class Accounts {
    private long account_id;
    private AccountType account_type;
    private boolean sharedAccount;
    private long balance;
    private String p_account_owner;
    private String s_account_owner;
    private boolean isActive;

    public Accounts() {
    }

    public Accounts(AccountType account_type, boolean sharedAccount, long balance, String p_account_owner, String s_account_owner, boolean isActive) {
        this.account_type = account_type;
        this.sharedAccount = sharedAccount;
        this.balance = balance;
        this.p_account_owner = p_account_owner;
        this.s_account_owner = s_account_owner;
        this.isActive = isActive;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public String isActive() {
        return this.isActive?"TRUE":"FALSE";

    }

    public void setActive(String active) {
        if (active.equalsIgnoreCase("TRUE")){
            this.isActive = true;
        }
        if (active.equalsIgnoreCase("FALSE")){
            this.isActive = false;
        }

    }

    public AccountType getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
       if (account_type.equalsIgnoreCase("CHECKIN")){
           this.account_type = AccountType.CHECKING;
       }
        if (account_type.equalsIgnoreCase("SAVING")) {
            this.account_type = AccountType.SAVING;
        }
    }

    public String isSharedAccount() {
        return sharedAccount?"TRUE":"FALSE";
    }

    public void setSharedAccount(String shared) {
        if (shared.equalsIgnoreCase("TRUE")){
            this.sharedAccount = true;
        }
        if (shared.equalsIgnoreCase("FALSE")){
            this.sharedAccount = false;
        }
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getP_account_owner() {
        return p_account_owner;
    }

    public void setP_account_owner(String p_account_owner) {
        this.p_account_owner = p_account_owner;
    }

    public String getS_account_owner() {
        return s_account_owner;
    }

    public void setS_account_owner(String s_account_owner) {
        this.s_account_owner = s_account_owner;
    }
    public boolean isAccountIsActive(){
        return this.isActive;
    }
    @Override
    public String toString() {
        return "Accounts{" +
                "account_id=" + account_id +
                ", account_type=" + account_type +
                ", sharedAccount=" + sharedAccount +
                ", balance=" + balance +
                ", p_account_owner='" + p_account_owner + '\'' +
                ", s_account_owner='" + s_account_owner + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
