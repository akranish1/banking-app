package app;

import service.BankService;
import service.impl.BankServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        BankService bankService=new BankServiceImpl();
        System.out.println("Welcome to console Bank");

        boolean running=true;
        while(running) {
            System.out.println("""
                    1) Open Account
                    2) Deposit
                    3) Withdraw
                    4) Transfer
                    5) Account Statement
                    6) List Accounts
                    7) Search Accounts by Customer Name
                    0) Exit
                    """);
            System.out.print("CHOOSE: ");
            String choice=sc.nextLine().trim();
            System.out.println("CHOICE: "+choice);

            switch (choice){
                case "1"-> openAccount(sc, bankService);
                case "2"-> deposit(sc,bankService);
                case "3"-> withdraw(sc,bankService);
                case "4"-> transfer(sc,bankService);
                case "5"-> statement(sc,bankService);
                case "6"-> listAccounts(sc, bankService);
                case "7"-> searchAccounts(sc);
                case "0" ->running=false;
            }
        }

    }

    private static void openAccount(Scanner sc,BankService bankService) {
        System.out.println("Customer name: ");
        String name=sc.nextLine().trim();
        System.out.println("Customer email: ");
        String email =sc.nextLine().trim();
        System.out.println("Account Type (SAVINGS/CURRENT): ");
        String type=sc.nextLine().trim();
        System.out.println("Initial deposit (optional, blank for 0): ");
        String amountStr=sc.nextLine().trim();
        Double initial=Double.valueOf(amountStr);
        String accountNumber=bankService.openAccount(name,email,type);
        if(initial>0)
            bankService.deposit(accountNumber,initial,"Initial Deposit");
        System.out.println("Account opened: "+accountNumber);
    }

    private static void deposit(Scanner sc, BankService bankService) {
        System.out.println("Account number: ");
        String accountNumber=sc.nextLine().trim();
        System.out.println("Amount: ");
        Double amount=Double.valueOf(sc.nextLine().trim());
        bankService.deposit(accountNumber,amount,"Deposits");
        System.out.println("Deposited Successfully");

    }

    private static void withdraw(Scanner sc,BankService bankService) {
        System.out.println("Account number: ");
        String accountNumber=sc.nextLine().trim();
        System.out.println("Amount: ");
        Double amount=Double.valueOf(sc.nextLine().trim());
        bankService.withdraw(accountNumber,amount,"Withdrawal");
        System.out.println("Withdrawn Successfully");
    }

    private static void transfer(Scanner sc,BankService bankService) {
        System.out.println("From Account: ");
        String from=sc.nextLine().trim();
        System.out.println("To Account: ");
        String to=sc.nextLine().trim();

        System.out.println("Amount: ");
        Double amount=Double.valueOf(sc.nextLine().trim());
        bankService.transfer(from,to,amount,"Withdrawal");
    }

    private static void statement(Scanner sc, BankService bankService) {
        System.out.println("Account number: ");
        String accountNumber=sc.nextLine().trim();

    }

    private static void listAccounts(Scanner sc, BankService bankService) {
        bankService.listAccounts().forEach(a ->{
            System.out.println(a.getAccountNumber()+" | "+ a.getAccountType()+" | "
            +a.getBalance());
        });
    }

    private static void searchAccounts(Scanner sc) {
    }
}
