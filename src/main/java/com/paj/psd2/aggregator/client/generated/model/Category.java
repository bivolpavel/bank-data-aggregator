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
import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The value for the category property indicates the importance of the error that is returned and MUST be one of the values ERROR   - Indicates that operation has been completed with errors. WARNING - Indicates that operation has been successfully completed but there is/are warnings about the operation
 */
public enum Category {
  WARNING("WARNING"),
  ERROR("ERROR");

  private String value;

  Category(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static Category fromValue(String text) {
    for (Category b : Category.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
