package com.paj.psd2.aggregator.client.generated.api;

import com.paj.psd2.aggregator.client.generated.ApiClient;

import com.paj.psd2.aggregator.client.generated.model.CardTransactionsResponse;
import java.time.OffsetDateTime;
import com.paj.psd2.aggregator.client.generated.model.TPPMessages;
import java.util.UUID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.danlucian.psd2.ing.time.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@Component("com.paj.psd2.aggregator.client.generated.api.CardAccountTransactionsApi")
public class CardAccountTransactionsApi {
    private ApiClient apiClient;

    public CardAccountTransactionsApi() {
        this(new ApiClient());
    }

    @Autowired
    public CardAccountTransactionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Card Account Transactions endpoint
     * Provides transaction information for a given card account.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorised
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found or Unauthorised
     * <p><b>500</b> - Internal Server Error
     * @param accountId Account ID represented by an UUID
     * @param xRequestID ID of the individual request determined by the initiating party (TPP) as UUID
     * @param authorization Bearer Token. Represents Customer Access Token. The process to obtain the Customer Access Access Token can be found under Documentation tab. Refer to Authentication section of the Documentation page.
     * @param signature A signature of the request by the TPP on application level. The process of signing a request is described in &#x27;Signing HTTP Messages&#x27; RFC draft version 07 (https://tools.ietf.org/html/draft-cavage-http-signatures-07). The request signature needs to be sent in the &#x27;Signature&#x27; HTTP header as described in the RFC. Additional requirements from ING:   - The only allowed algorithm is RSA-SHA256   - The key size for the used RSA key pair has to be at least 2048 bit   - The keyId is the clientId of your application originating from the ING Developer Portal   - We require the following headers for every request: \&quot;(request-target) Date Digest X-Request-ID\&quot;. The request-target is a combination of the HTTP action verb and the request URI path.
     * @param digest The \&quot;Digest\&quot; Header header as defined in [RFC3230] contains a Hash of the message body. The only hash algorithms that may be used to calculate the Digest within the context of this specification are SHA-256 and SHA-512 as defined in [RFC5843].
     * @param date Standard http header element for date and time. HTTP headers are represented as RFC 7231 Full Dates.
     * @param dateFrom Transactions will be returned that have been executed since this date and (optionally) time. The date-time must be provided in UTC ISO 8601 Date Time format: YYYY-MM-DDThh:mm:ss.sTZD and always in UTC timezone. This must be made explicit by providing value Z (for Zulu) as time zone designator. If not provided, a default date and time will be applied which is determined as follows:   * If dateFrom and dateTo are both not provided, dateFrom is defaulted to today’s date + 00:00:00 in the UTC timezone   * If dateFrom is not provided and dateTo is provided with a date in the past, dateFrom is defaulted to the date provided in dateTo + 00:00:00 in the UTC timezone   * If dateFrom is not provided and dateTo is provided with a date equal to or after today’s date, dateFrom is defaulted to today’s date provided in dateTo + 00:00:00 in the UTC timezone   * If dateFrom is provided with a date but without a time, the time is defaulted to 00:00:00 in the UTC timezone   * If dateFrom is provided with a date time in the future, the request is rejected
     * @param dateTo Transactions will be returned that have been executed until this date and (optionally) time. The date-time must be provided in UTC ISO 8601 Date Time format: YYYY-MM-DDThh:mm:ss.sTZD and always in UTC timezone. This must be made explicit by providing value Z (for Zulu) as time zone designator. If not provided, a default date and time will be applied which is determined as follows:   * If dateTo and dateFrom are both not provided, dateTo is defaulted to today’s date time + current time in the UTC timezone   * If dateTo is not provided and dateFrom is provided with a date in the past, dateTo is defaulted to the date provided in dateFrom + 23:59:59 in the UTC timezone   * If dateTo is not provided and dateFrom is provided with today’s date, dateTo is defaulted to today’s date + current time in the UTC timezone   * If dateTo is provided with a date in the past, the time is defaulted to 23:59:59 in the UTC timezone   * If dateTo is provided with a date equal to or after today’s date, the time is defaulted to current time in the UTC timezone
     * @param limit Optional parameter to request the maximum number of transactions on the page, the next page of results can be requested via the next link
     * @return CardTransactionsResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CardTransactionsResponse v1CardAccountsAccountIdTransactionsGet(UUID accountId, UUID xRequestID, String authorization, String signature, String digest, OffsetDateTime date, String dateFrom, String dateTo, Integer limit) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'accountId' is set
        if (accountId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'accountId' when calling v1CardAccountsAccountIdTransactionsGet");
        }
        // verify the required parameter 'xRequestID' is set
        if (xRequestID == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xRequestID' when calling v1CardAccountsAccountIdTransactionsGet");
        }
        // verify the required parameter 'authorization' is set
        if (authorization == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'authorization' when calling v1CardAccountsAccountIdTransactionsGet");
        }
        // verify the required parameter 'signature' is set
        if (signature == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'signature' when calling v1CardAccountsAccountIdTransactionsGet");
        }
        // verify the required parameter 'digest' is set
        if (digest == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'digest' when calling v1CardAccountsAccountIdTransactionsGet");
        }
        // verify the required parameter 'date' is set
        if (date == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'date' when calling v1CardAccountsAccountIdTransactionsGet");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("account-id", accountId);
        String path = UriComponentsBuilder.fromPath("/v1/card-accounts/{account-id}/transactions").buildAndExpand(uriVariables).toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dateFrom", dateFrom));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dateTo", dateTo));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        if (xRequestID != null)
            headerParams.add("X-Request-ID", apiClient.parameterToString(xRequestID));
        if (authorization != null)
            headerParams.add("Authorization", apiClient.parameterToString(authorization));
        if (signature != null)
            headerParams.add("Signature", apiClient.parameterToString(signature));
        if (digest != null)
            headerParams.add("Digest", apiClient.parameterToString(digest));
        if (date != null)
            headerParams.add("Date", DateUtil.getCurrentDateAsString());

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "ING-Access-Code-Security" };

        ParameterizedTypeReference<CardTransactionsResponse> returnType = new ParameterizedTypeReference<CardTransactionsResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
