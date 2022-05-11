package com.code.fetcher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Main {

    public static void main(String[] args) {
        ChallengeStatus challengeStatus = new ChallengeStatus();
        List<Transaction> transactionList = new ArrayList<>();

        //Sample Sanity Test
        for(int i = 1; i < 10; ++i){
            transactionList.add(new Transaction(new BigDecimal("5000.0"), LocalDate.of(2022, 05, i)));
        }

        transactionList.add(new Transaction(new BigDecimal("-10000.0"), LocalDate.of(2022, 05,1)));
        transactionList.add(new Transaction(new BigDecimal("10000.0"), LocalDate.of(2022, 05,19)));
        assertEquals(ChallengeStatusResult.WIN.toString(),
                challengeStatus.
                        getCurrentStatus(transactionList,LocalDate.of(2022, 05, 20),
                                14, new BigDecimal("20000.0")));
    }
}