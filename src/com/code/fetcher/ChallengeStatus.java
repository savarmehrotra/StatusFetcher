package com.code.fetcher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This Class is Responsible to Get the Challenge Status For the particular date given a list of transactions.
 *
 * Things to Note :
 * 1) Margins / Overdraft are allowed and the Net Account Balance can go become Negative.
 *    If not then taken care by caller function passing transactions list
 *
 * 2) This Only Gives the Current Date's Challenge Status. Not the case where in a spane of 100 days, someone has cleared the
 * challenge even once.
 *
 * */
public class ChallengeStatus {

  public ChallengeStatus() {}

  public String getCurrentStatus(final List<Transaction> transactions, final LocalDate currentDate,
      int streakThreshold, final BigDecimal amountThreshold) {

    // Initialize
    BigDecimal currNetAmount = new BigDecimal("0.0");
    int currStreakCount = 0;
    Map<LocalDate, BigDecimal> dayWiseNetTransactions = getDayWiseNetTransactionsBeforeDate(transactions, currentDate);
    LocalDate firstStreakDate = LocalDate.MIN;

    // Iterate over Net Transactions Before or Equal to The Given Current Date
    for (Map.Entry<LocalDate, BigDecimal> transaction : dayWiseNetTransactions.entrySet()) {

      currNetAmount = currNetAmount.add(transaction.getValue());
      if (currNetAmount.compareTo(amountThreshold) >= 0) {
        currStreakCount++;
        // Log First Latest Streak Date
        if (currStreakCount == 1)
          firstStreakDate = transaction.getKey();
      } else {
        // Reset Streak Count
        currStreakCount = 0;
      }
    }

    // Calculating Latest Streak Count
    if (currNetAmount.compareTo(amountThreshold) >= 0) {
      currStreakCount = currentDate.getDayOfYear() - firstStreakDate.getDayOfYear() + 1;
    }

    if (currStreakCount >= streakThreshold)
      return ChallengeStatusResult.WIN.toString();

    return ChallengeStatusResult.ONGOING.toString();
  }

  private Map<LocalDate, BigDecimal> getDayWiseNetTransactionsBeforeDate(
      final List<Transaction> transactions, final LocalDate currentDate) {

    Map<LocalDate, BigDecimal> dayWiseNetTransactionsMap = new TreeMap<>();

    for (Transaction transaction : transactions) {
      if (currentDate.compareTo(transaction.getDate()) >= 0) {
        dayWiseNetTransactionsMap.put(
            transaction.getDate(),
            dayWiseNetTransactionsMap
                .getOrDefault(transaction.getDate(), new BigDecimal("0.0"))
                .add(transaction.getAmount()));
      }
    }
    return dayWiseNetTransactionsMap;
  }
}