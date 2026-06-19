package com.aem.geeks.core.servlets.tododropdown;

import com.google.gson.Gson;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


//4 : this class will make a api call to the dummyjson todos api and get the list of todos. It will then convert that json response into our Java objects
// (TodosResponse and TodoItem) using Gson library. This class is used in our servlet to get the data we need to populate our dropdown options.

/*BhargavSeshadri - The servlet executes and calls this class.*/
class TodoApiClient {

    static final String TODOS_API_URL = "https://dummyjson.com/todos";

    private static final int CONNECTION_TIMEOUT_MS = 5000;
    private static final int READ_TIMEOUT_MS = 5000;

    TodosResponse fetchTodos() throws IOException {

        HttpGet httpGet = new HttpGet(TODOS_API_URL);
        httpGet.setHeader(HttpHeaders.ACCEPT, "application/json");
        httpGet.setConfig(createRequestConfig()); // Used to set our own custom timeout settings.

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {  // here we are making the request

            validateResponse(httpResponse);
            return readTodos(httpResponse);
        }
    }


    //this method creates and return a RequestConfig object containing the timeout settings for the HTTP request.
    //setting :Connect Timeout = 5000; Read Timeout    = 5000
    private RequestConfig createRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT_MS)
                .setSocketTimeout(READ_TIMEOUT_MS)
                .build();
    }

    private void validateResponse(CloseableHttpResponse httpResponse) throws IOException {
        int responseCode = httpResponse.getStatusLine().getStatusCode();
        if (responseCode != HttpStatus.SC_OK) {
            throw new IOException("Todo API returned HTTP status " + responseCode);
        }

        if (httpResponse.getEntity() == null) {
            throw new IOException("Todo API returned an empty response");
        }
    }


    /*InputStreamReader resReader = new InputStreamReader(httpResponse.getEntity().getContent())
    * Use of this: Basically when API sends the response java doesn't receives it in string format, Instead, it receives raw bytes over the network.
    * - httpResponse.getEntity() :
    *       - API Response : HTTP/1.1 200 OK
                            {                           //this is the response body
                               "id":1,
                               "todo":"Learn Java"
                            }
            - The body part is the Entity.

       - .getContent() : This method returns an InputStream that reads the raw bytes of the response body. So we are getting the raw bytes of the response body here.
       - new InputStreamReader(...) : This is a Java class that takes an InputStream (like the one we got from getContent()) and
                                      converts those raw bytes into characters (text) using a specified character encoding (like UTF-8).
                                      So we are converting the raw bytes of the response.
    * */
    private TodosResponse readTodos(CloseableHttpResponse httpResponse) throws IOException {   //important method
        try (InputStreamReader resReader = new InputStreamReader(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8)) {
            return new Gson().fromJson(resReader, TodosResponse.class);   // This line uses Gson to parse the JSON response and convert it into a TodosResponse object.
        }
    }
}
