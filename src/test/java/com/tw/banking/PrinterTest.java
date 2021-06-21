package com.tw.banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PrinterTest {
    @InjectMocks
    private Printer printer;
    @Mock
    private Console console;

    @Test
    void should_use_console_print_when_print_given_transactions() {
        // given
        int amount = 0;
        String date = "21/06/2021";

        Transaction transaction = new Transaction(date, amount);
        List<Transaction> transactions = Collections.singletonList(transaction);

        // when
        printer.print(transactions);

        // then
        Assertions.assertAll(
                () -> verify(console, times(1)).printLine(Printer.STATEMENT_HEADER),
                () -> verify(console, times(1)).printLine("21/06/2021 | 0 | 0")
        );
    }

    @Test
    void should_use_console_print_transactions_summary_when_print_given_transactions() {
        // given
        Transaction transaction1 = new Transaction("20/06/2021", 5);
        Transaction transaction2 = new Transaction("21/06/2021", 100);
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        // when
        printer.print(transactions);

        // then
        Assertions.assertAll(
                () -> verify(console, times(1)).printLine(Printer.STATEMENT_HEADER),
                () -> verify(console, times(1)).printLine("20/06/2021 | 5 | 5"),
                () -> verify(console, times(1)).printLine("21/06/2021 | 100 | 105")
        );
    }

    @Test
    void should_use_console_print_transactions_summary_order_by_date_when_print_given_transactions() {
        // given
        Transaction transaction1 = new Transaction("19/06/2021", 5);
        Transaction transaction2 = new Transaction("20/06/2021", -5);
        Transaction transaction3 = new Transaction("21/06/2021", 100);
        List<Transaction> transactions = Arrays.asList(transaction3, transaction1, transaction2);

        // when
        printer.print(transactions);

        // then
        InOrder inOrder = Mockito.inOrder(console);
        Assertions.assertAll(
                () -> inOrder.verify(console, times(1)).printLine(Printer.STATEMENT_HEADER),
                () -> inOrder.verify(console, times(1)).printLine("21/06/2021 | 100 | 100"),
                () -> inOrder.verify(console, times(1)).printLine("20/06/2021 | -5 | 0"),
                () -> inOrder.verify(console, times(1)).printLine("19/06/2021 | 5 | 5")
        );
    }
}