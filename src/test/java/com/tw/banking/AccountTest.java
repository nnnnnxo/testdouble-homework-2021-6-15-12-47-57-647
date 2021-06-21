package com.tw.banking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountTest {
    @InjectMocks private Account account;
    @Mock private TransactionRepository transactionRepository;

    @Test
    void should_add_deposit_transaction_when_deposit() {
        // given
        int amount = 100;

        // when
        account.deposit(100);

        // then
        verify(transactionRepository, times(1)).addDeposit(amount);
    }

    @Test
    void should_add_withdraw_transaction_when_withdraw() {
        // given
        int amount = 100;

        // when
        account.withdraw(100);

        // then
        verify(transactionRepository, times(1)).addWithdraw(amount);
    }
}