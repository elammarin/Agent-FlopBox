package fil.sr2;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class Agent {

        public static final String BASE_URI = "http://localhost:8080/flopboxApp/servers/";

        public String addServer(String alias, String host, Integer port) throws Exception {

            CloseableHttpClient httpClient = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder(BASE_URI+alias);
            builder.setParameter("address", host).setParameter("port", Integer.toString(port));
            return PostRequestAsString(httpClient, builder);
        }

        public String listServers() throws Exception{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder(BASE_URI);
            return GetRequestAsString(httpClient, builder);
        }

    private String PostRequestAsString(CloseableHttpClient httpClient, URIBuilder builder) throws URISyntaxException, IOException {
        HttpPost request = new HttpPost(builder.build());
        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String result = "None";
        if (entity != null) {
            result = EntityUtils.toString(entity);
        }
        return result;
    }

    private String GetRequestAsString(CloseableHttpClient httpClient, URIBuilder builder) throws URISyntaxException, IOException {
        HttpGet request = new HttpGet(builder.build());
        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String result = "None";
        if (entity != null) {
            result = EntityUtils.toString(entity);
        }
        return result;
    }

    }
