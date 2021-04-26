package fil.sr2;


public class Main {
    public static void main( String[] args ) {
        Agent ag = new Agent();
        try {
            ag.addServer("ubuntu", "ftp.ubuntu.com", 21);
            ag.addServer("free", "ftp.free.fr", 21);
            System.out.println(ag.listServers());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
