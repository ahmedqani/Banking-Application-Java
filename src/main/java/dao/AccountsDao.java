package dao;

import model.Accounts;

import java.util.List;

public interface AccountsDao {

    List<Accounts> getAllAccounts();
    List<Accounts> getUserAccounts(String accountUser);
    void updateAccountBalance(Accounts accounts) throws Exception;
    void updateSecondaryOwner(Accounts s_user) throws Exception;
    void activateAccount(Accounts accountToActivate) throws Exception;


}
