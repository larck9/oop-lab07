package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Assertions;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private static double DEPOSIT_AMOUNT=100;
    private static double FIXED_FEE=5;
    private static double TRANSACT_FEE=0.1;
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi= new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(this.mRossi,0.0); 
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0,bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        bankAccount.deposit(bankAccount.getAccountHolder().getUserID(), DEPOSIT_AMOUNT);
        assertEquals(DEPOSIT_AMOUNT,bankAccount.getBalance());
        assertEquals(1,bankAccount.getTransactionsCount());
        bankAccount.chargeManagementFees(bankAccount.getAccountHolder().getUserID());
        assertEquals(DEPOSIT_AMOUNT-FIXED_FEE-TRANSACT_FEE,bankAccount.getBalance());
        assertEquals(0,bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
       try{
        bankAccount.withdraw(bankAccount.getAccountHolder().getUserID(),-DEPOSIT_AMOUNT);
        fail("cannot withdraw a negative amount an exception should be thrown");
       }catch(IllegalArgumentException e){
            assertEquals(0.0, bankAccount.getBalance());
            assertEquals(0, bankAccount.getTransactionsCount());
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
            assertEquals("Cannot withdraw a negative amount",e.getMessage());
       }
        
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try{
            bankAccount.withdraw(bankAccount.getAccountHolder().getUserID(),DEPOSIT_AMOUNT);
            Assertions.fail("cannot withdraw a bigger amount than the balance available, an exception should be thrown");
           }catch(IllegalArgumentException e){
                assertEquals(0.0, bankAccount.getBalance());
                assertEquals(0, bankAccount.getTransactionsCount());
                assertNotNull(e.getMessage());
                assertFalse(e.getMessage().isBlank());
                assertEquals("Insufficient balance",e.getMessage());
           }
    }
}
