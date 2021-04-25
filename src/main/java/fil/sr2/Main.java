package fil.sr2;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Scanner;

public class Main {
    public static void main( String[] args ) {
        Agent ag = new Agent();
        try {
            ag.addServer("ubuntu", "ftp.ubuntu.com", 21);
            System.out.println(ag.addServer("free", "ftp.free.fr", 21));
            System.out.println(ag.listServers());
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet r = new HttpGet("http://localhost:8080/flopboxApp/servers");
            CloseableHttpResponse response = httpClient.execute(r);
            Scanner sc = new Scanner(response.getEntity().getContent());
            while(sc.hasNext()) {
                System.out.println(sc.nextLine());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
