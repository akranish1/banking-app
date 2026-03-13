package service.impl;

import domains.Account;
import domains.Transaction;
import domains.Type;
import repository.AccountRepository;
import repository.TransactionRepository;
import service.BankService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {
    private final AccountRepository accountRepository=new AccountRepository();
    private final TransactionRepository transactionRepository= new TransactionRepository();
    @Override
    public String openAccount(String name, String email, String accountType) {
        String customerId= UUID.randomUUID().toString();

        //change later--> 10+1=AC000011
        String accountNumber = getString();
        //fields inside Account class get these data values;
        Account a=new Account(accountNumber,accountType, (double) 0,customerId);
        //SAVE
        accountRepository.save(a);
        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll().stream().
                sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }


    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        Account account=accountRepository.findByNumber(accountNumber)
                .orElseThrow(()-> new RuntimeException("Account not found: "+ accountNumber));

        account.setBalance(account.getBalance()+amount);
        Transaction transaction=new Transaction(UUID.randomUUID().toString(), Type.DEPOSIT,account.getAccountNumber(),amount, LocalDateTime.now(),note);
        transactionRepository.add(transaction);
    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {
        Account account= accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));
        if(account.getBalance().compareTo(amount)<0)
        throw new RuntimeException("Insufficient Balance");
        account.setBalance(account.getBalance()-amount);
        Transaction transaction=new Transaction(UUID.randomUUID().toString(), Type.WITHDRAW,account.getAccountNumber(),amount, LocalDateTime.now(),note);
        transactionRepository.add(transaction);

    }

    @Override
    public void transfer(String from, String to, Double amount, String withdrawal) {

    }


    //Refactored:
    private String getString() {
        int temp= accountRepository.findAll().size()+1;
        String accountNumber=String.format("AC%06d",temp);//creates 6digit acc. no.
        return accountNumber;
    }
}
