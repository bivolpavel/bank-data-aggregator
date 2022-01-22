package com.paj.psd2.aggregator.client.generated.api;

import com.paj.psd2.aggregator.client.generated.ApiClient;

import com.paj.psd2.aggregator.client.generated.model.AccountsResponse;
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

@Component("com.paj.psd2.aggregator.client.generated.api.AccountDetailsApi")
public class AccountDetailsApi {
    private ApiClient apiClient;

    public AccountDetailsApi() {
        this(new ApiClient());
    }

    @Autowired
    public AccountDetailsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Account Details endpoint
     * Provides all granted accounts and will provide account details for the list of accounts. If there are no granted accounts at all, then it will return a 404.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorised
     * <p><b>404</b> - Not Found or Unauthorised
     * <p><b>500</b> - Internal Server Error
     * @param xRequestID ID of the individual request determined by the initiating party (TPP) as UUID
     * @param authorization Bearer Token. Represents Customer Access Token. The process to obtain the Customer Access Access Token can be found under Documentation tab. Refer to Authentication section of the Documentation page.
     * @param signature A signature of the request by the TPP on application level. The process of signing a request is described in &#x27;Signing HTTP Messages&#x27; RFC draft version 07 (https://tools.ietf.org/html/draft-cavage-http-signatures-07). The request signature needs to be sent in the &#x27;Signature&#x27; HTTP header as described in the RFC. Additional requirements from ING:   - The only allowed algorithm is RSA-SHA256   - The key size for the used RSA key pair has to be at least 2048 bit   - The keyId is the clientId of your application originating from the ING Developer Portal   - We require the following headers for every request: \&quot;(request-target) Date Digest X-Request-ID\&quot;. The request-target is a combination of the HTTP action verb and the request URI path.
     * @param digest The \&quot;Digest\&quot; Header header as defined in [RFC3230] contains a Hash of the message body. The only hash algorithms that may be used to calculate the Digest within the context of this specification are SHA-256 and SHA-512 as defined in [RFC5843].
     * @param date Standard http header element for date and time. HTTP headers are represented as RFC 7231 Full Dates.
     * @return AccountsResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public AccountsResponse v3AccountsGet(UUID xRequestID, String authorization, String signature, String digest, OffsetDateTime date) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'xRequestID' is set
        if (xRequestID == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xRequestID' when calling v3AccountsGet");
        }
        // verify the required parameter 'authorization' is set
        if (authorization == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'authorization' when calling v3AccountsGet");
        }
        // verify the required parameter 'signature' is set
        if (signature == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'signature' when calling v3AccountsGet");
        }
        // verify the required parameter 'digest' is set
        if (digest == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'digest' when calling v3AccountsGet");
        }
        // verify the required parameter 'date' is set
        if (date == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'date' when calling v3AccountsGet");
        }
        String path = UriComponentsBuilder.fromPath("/v3/accounts").build().toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
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

        ParameterizedTypeReference<AccountsResponse> returnType = new ParameterizedTypeReference<AccountsResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
