/*
 * Account Information API
 * Create Account Information Services consuming applications that offer great added value to your customers and users. Our Account Information Service will enable secure access to all European ING online accessible payment accounts.
 *
 * OpenAPI spec version: 2.5.0
 * Contact: developerportal@ing.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.paj.psd2.aggregator.client.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.paj.psd2.aggregator.client.generated.model.AccountReferenceIban;
import com.paj.psd2.aggregator.client.generated.model.Transactions;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * TransactionsResponse
 */

public class TransactionsResponse {

  @JsonProperty("account")

  private AccountReferenceIban account = null;

  @JsonProperty("transactions")

  private Transactions transactions = null;
  public TransactionsResponse account(AccountReferenceIban account) {
    this.account = account;
    return this;
  }

  

  /**
  * Get account
  * @return account
  **/
  @Schema(description = "")
  public AccountReferenceIban getAccount() {
    return account;
  }
  public void setAccount(AccountReferenceIban account) {
    this.account = account;
  }
  public TransactionsResponse transactions(Transactions transactions) {
    this.transactions = transactions;
    return this;
  }

  

  /**
  * Get transactions
  * @return transactions
  **/
  @Schema(required = true, description = "")
  public Transactions getTransactions() {
    return transactions;
  }
  public void setTransactions(Transactions transactions) {
    this.transactions = transactions;
  }
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionsResponse transactionsResponse = (TransactionsResponse) o;
    return Objects.equals(this.account, transactionsResponse.account) &&
        Objects.equals(this.transactions, transactionsResponse.transactions);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(account, transactions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionsResponse {\n");
    
    sb.append("    account: ").append(toIndentedString(account)).append("\n");
    sb.append("    transactions: ").append(toIndentedString(transactions)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
