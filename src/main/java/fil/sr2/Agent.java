package fil.sr2;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


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

    public String listServer(String serverName, String path) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder(BASE_URI+serverName+"list"+path);
        return GetRequestAsString(httpClient, builder);
    }

    private int type(String elem){
        if(elem.startsWith("-")) return 0;
        if(elem.startsWith("d")) return 1;
        if(elem.startsWith("l")) return 2;
        return -1;
    }

    /**
     * Return true if elem is a link
     */
    public boolean isLink(String elem){
        if (type(elem)==2) return true;
        else return false;
    }

    /**
     * Return true if elem is a directory
     */
    public boolean isDirectory(String elem) {
        if (type(elem)==1) return true;
        else return false;
    }

    /**
     * Return true if elem is a file
     */
    public boolean isFile(String elem) {
        if (type(elem)==0) return true;
        else return false;
    }

    /**
     * method to get a dir or file name
     * @param lsLine the line given by the method list
     * @return the name of the file or dir name
     **/
    protected String getFileName(String lsLine) {
        String[] splited = lsLine.split(" ");
        return splited[splited.length - 1];
    }

    protected String getDate(String lsLine) {
        String[] splited = lsLine.split("\\s+");
        return splited[5]+"-"+splited[6]+"-"+splited[7];
    }

    public String[] listAsArray(String list){
        return list.split("\n");
    }

    public void downloadServer(String serverName, String path) {
        try {
            String[] root = this.listAsArray(this.listServer(serverName, path));
            for (String elem : root) {
                System.out.println(elem);
                if (isDirectory(elem) || isLink(elem)) {
                    downloadServer(serverName, path+getFileName(elem)+"/");
                }
                else if (isFile(elem)){
                    File f = new File("/tmp/"+serverName+path+getFileName(elem));
                    String dateStr = getDate(elem);
                    SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy", Locale.ENGLISH);
                    Date date = (Date)formatter.parse(dateStr);
                    long mills = date.getTime();
                    if(f.exists() && !f.isDirectory() && f.lastModified()>=mills) {
                        // do nothing
                    }
                    else {
                        CloseableHttpClient httpClient = HttpClients.createDefault();
                        try {
                            URIBuilder builder = new URIBuilder(BASE_URI + serverName + "file" + path + getFileName(elem));
                            HttpGet request = new HttpGet(builder.build());
                            httpClient.execute(request);
                        } catch (URISyntaxException uri) {

                        }
                    }
                }
                else{
                    System.out.println("emptyDir");
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
