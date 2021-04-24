package fil.sr2;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class Server {

        public static final String BASE_URI = "http://localhost:8080/flopboxApp/";
        String port;
        String host;
        String alias;

        public Server(String host, String port, String alias) {
            this.host = host;
            this.port = port;
            this.alias = alias;

        }

        public void add() throws Exception {

            CloseableHttpClient httpClient = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder(BASE_URI);
            builder.setParameter("address", this.host).setParameter("port", this.port);
            HttpPost request = new HttpPost(builder.build());
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }
        }
    }
