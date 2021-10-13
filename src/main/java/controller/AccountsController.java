package controller;

import dao.AccountsDao;
import dao.DAOUtilities;
import dao.TransactionDao;
import dao.UserDao;
import exceptions.TransactionGeneralException;
import logging.Logging;
import model.Accounts;
import model.Transaction;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class AccountsController {
    //Call DAO method
    AccountsDao dao = DAOUtilities.getAccountDao();
    TransactionDao daoTransaction = DAOUtilities.getTransactionDao();


    public Accounts saveAccount(Accounts accountToSave) throws Exception {
        dao.saveAccount(accountToSave);
        Logging.logger.info("A new " + accountToSave.getAccount_type()+
                " Account was Created for User: " + accountToSave.getP_account_owner());
        return accountToSave;
    }

    public List<Accounts> getAllAccounts(){
        return dao.getAllAccounts();
    }
    public List<Accounts> getUserAccounts(String accountOwner){
        return dao.getUserAccounts(accountOwner);
    }

    public Accounts getAccountById(long id){
        return dao.getAccountById(id);
    }
    public void updateSecondaryOwner(Accounts s_user) throws Exception {
        dao.updateSecondaryOwner(s_user);
    }
    public void activateAccount(Accounts accountToActivate) throws Exception {
        dao.activateAccount(accountToActivate);
        Logging.logger.info("The " +accountToActivate.getAccount_type()+
                " Account by the User " + accountToActivate.getP_account_owner() + " Has been Activated");
    }

    public void updateAccountBalance(Accounts accountTo, Accounts accountFrom, Transaction transactionToSave){

        try {
            long amount = transactionToSave.getAmount();
            if (transactionToSave.getTransactionType().equalsIgnoreCase("withdraw")
                    || transactionToSave.getTransactionType().equalsIgnoreCase("transfer")){
                if ((accountFrom.getBalance() - amount) >= 0){
                    daoTransaction.saveTransaction(transactionToSave);
                    accountTo.setBalance(accountTo.getBalance()+amount);
                    accountFrom.setBalance(accountFrom.getBalance()-amount);
                    dao.updateAccountBalance(accountFrom);
                    dao.updateAccountBalance(accountTo);
                    Logging.logger.info("User "+accountFrom.getP_account_owner()+ " Has successfully "+transactionToSave.getTransactionType()+
                            "ed the following amount: "+amount);
                    System.out.printf("you have successfully %s -ed to %s \n", transactionToSave.getTransactionType(),accountTo.getP_account_owner());
                }else {
                    throw new TransactionGeneralException("Please make sure your Balance is enough to cover the Transaction");
                }
            }
            if (transactionToSave.getTransactionType().equalsIgnoreCase("DEPOSIT")){
                daoTransaction.saveTransaction(transactionToSave);
                accountFrom.setBalance(accountFrom.getBalance()+amount);
                dao.updateAccountBalance(accountFrom);
                Logging.logger.info("User "+accountFrom.getP_account_owner()+ " Has successfully "+transactionToSave.getTransactionType()+
                        "ed the following amount: "+amount + " To user: "+accountTo.getP_account_owner());
                System.out.printf("You have Successfully deposited %d \n", amount);

            }
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            System.out.println("ID is already in use");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem creating the Transaction at this time");
        }
    }

}
