package com.code.fetcher;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {

  private BigDecimal amount;
  private LocalDate date;

  Transaction(final BigDecimal amount, final LocalDate date) {
    this.amount = amount;
    this.date= date;
  }

  public BigDecimal getAmount() {
    return this.amount;
  }

  public LocalDate getDate() {
    return this.date;
  }
}