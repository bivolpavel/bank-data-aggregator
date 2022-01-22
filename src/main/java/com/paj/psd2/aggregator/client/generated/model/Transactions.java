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
import com.paj.psd2.aggregator.client.generated.model.LinksNext;
import com.paj.psd2.aggregator.client.generated.model.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * Transactions
 */

public class Transactions {

  @JsonProperty("booked")

  private List<Transaction> booked = new ArrayList<>();

  @JsonProperty("pending")

  private List<Transaction> pending = new ArrayList<>();

  @JsonProperty("info")

  private List<Transaction> info = new ArrayList<>();

  @JsonProperty("_links")

  private LinksNext _links = null;
  public Transactions booked(List<Transaction> booked) {
    this.booked = booked;
    return this;
  }

  public Transactions addBookedItem(Transaction bookedItem) {
    this.booked.add(bookedItem);
    return this;
  }

  /**
  * Get booked
  * @return booked
  **/
  @Schema(required = true, description = "")
  public List<Transaction> getBooked() {
    return booked;
  }
  public void setBooked(List<Transaction> booked) {
    this.booked = booked;
  }
  public Transactions pending(List<Transaction> pending) {
    this.pending = pending;
    return this;
  }

  public Transactions addPendingItem(Transaction pendingItem) {
    this.pending.add(pendingItem);
    return this;
  }

  /**
  * Get pending
  * @return pending
  **/
  @Schema(required = true, description = "")
  public List<Transaction> getPending() {
    return pending;
  }
  public void setPending(List<Transaction> pending) {
    this.pending = pending;
  }
  public Transactions info(List<Transaction> info) {
    this.info = info;
    return this;
  }

  public Transactions addInfoItem(Transaction infoItem) {
    this.info.add(infoItem);
    return this;
  }

  /**
  * Get info
  * @return info
  **/
  @Schema(required = true, description = "")
  public List<Transaction> getInfo() {
    return info;
  }
  public void setInfo(List<Transaction> info) {
    this.info = info;
  }
  public Transactions _links(LinksNext _links) {
    this._links = _links;
    return this;
  }

  

  /**
  * Get _links
  * @return _links
  **/
  @Schema(description = "")
  public LinksNext getLinks() {
    return _links;
  }
  public void setLinks(LinksNext _links) {
    this._links = _links;
  }
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transactions transactions = (Transactions) o;
    return Objects.equals(this.booked, transactions.booked) &&
        Objects.equals(this.pending, transactions.pending) &&
        Objects.equals(this.info, transactions.info) &&
        Objects.equals(this._links, transactions._links);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(booked, pending, info, _links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transactions {\n");
    
    sb.append("    booked: ").append(toIndentedString(booked)).append("\n");
    sb.append("    pending: ").append(toIndentedString(pending)).append("\n");
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
    sb.append("    _links: ").append(toIndentedString(_links)).append("\n");
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
