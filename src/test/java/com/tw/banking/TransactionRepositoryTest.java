package com.tw.banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryTest {
    @InjectMocks
    private TransactionRepository transactionRepository;
    @Mock
    private Clock clock;

    @Test
    void should_add_deposit_transaction_when_add_deposit() {
        // given
        int amount = 100;
        String today = "21/06/2021";
        when(clock.todayAsString()).thenReturn(today);

        // when
        transactionRepository.addDeposit(amount);

        // then
        List<Transaction> result = transactionRepository.allTransactions();
        Assertions.assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(today, result.get(0).date()),
                () -> assertEquals(amount, result.get(0).amount())

        );
    }

    @Test
    void should_add_withdraw_transaction_when_add_withdraw() {
        // given
        int amount = 100;
        int expectAmount = -100;
        String today = "21/06/2021";
        when(clock.todayAsString()).thenReturn(today);

        // when
        transactionRepository.addWithdraw(amount);

        // then
        List<Transaction> result = transactionRepository.allTransactions();
        Assertions.assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(today, result.get(0).date()),
                () -> assertEquals(expectAmount, result.get(0).amount())

        );
    }

    @Test
    void should_un_support_add_transaction_to_returned_transactions_when_get_all_transactions() {
        // given
        int amount = 100;
        String today = "21/06/2021";

        // when
        List<Transaction> transactions = transactionRepository.allTransactions();

        // then
        assertThrows(UnsupportedOperationException.class, () -> transactions.add(new Transaction(today, amount)));
    }
}