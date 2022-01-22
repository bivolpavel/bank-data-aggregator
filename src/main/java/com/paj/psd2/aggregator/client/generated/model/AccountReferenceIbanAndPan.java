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
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Reference to an account by either the Primary Account Number (PAN) or the IBAN.
 */
@Schema(description = "Reference to an account by either the Primary Account Number (PAN) or the IBAN.")
public class AccountReferenceIbanAndPan {

  @JsonProperty("iban")

  private String iban = null;

  @JsonProperty("maskedPan")

  private String maskedPan = null;

  @JsonProperty("currency")

  private String currency = null;
  public AccountReferenceIbanAndPan iban(String iban) {
    this.iban = iban;
    return this;
  }

  

  /**
  * This is the IBAN of the account belonging to the id
  * @return iban
  **/
  @Schema(example = "NL69INGB0123456789", description = "This is the IBAN of the account belonging to the id")
  public String getIban() {
    return iban;
  }
  public void setIban(String iban) {
    this.iban = iban;
  }
  public AccountReferenceIbanAndPan maskedPan(String maskedPan) {
    this.maskedPan = maskedPan;
    return this;
  }

  

  /**
  * Masked Primary Account Number
  * @return maskedPan
  **/
  @Schema(example = "1234*****6789", description = "Masked Primary Account Number")
  public String getMaskedPan() {
    return maskedPan;
  }
  public void setMaskedPan(String maskedPan) {
    this.maskedPan = maskedPan;
  }
  public AccountReferenceIbanAndPan currency(String currency) {
    this.currency = currency;
    return this;
  }

  

  /**
  * 3 Letter ISO Currency Code: ISO 4217 code
  * @return currency
  **/
  @Schema(example = "EUR", description = "3 Letter ISO Currency Code: ISO 4217 code")
  public String getCurrency() {
    return currency;
  }
  public void setCurrency(String currency) {
    this.currency = currency;
  }
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountReferenceIbanAndPan accountReferenceIbanAndPan = (AccountReferenceIbanAndPan) o;
    return Objects.equals(this.iban, accountReferenceIbanAndPan.iban) &&
        Objects.equals(this.maskedPan, accountReferenceIbanAndPan.maskedPan) &&
        Objects.equals(this.currency, accountReferenceIbanAndPan.currency);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(iban, maskedPan, currency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccountReferenceIbanAndPan {\n");
    
    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
    sb.append("    maskedPan: ").append(toIndentedString(maskedPan)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
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
