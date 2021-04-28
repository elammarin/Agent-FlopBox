package fil.sr2;


import java.util.Scanner;

public class Main {
    public static void main( String[] args ) {
        Agent ag = new Agent();

        while (true) {
            try {
                Scanner sc= new Scanner(System.in); //System.in is a standard input stream
                System.out.print("Enter 'a' to add a server, enter 'd' to Download all servers registered into Flopbox Platform. \n" +
                        "Enter q to quit. \n" +
                        "Enter a command: ");
                String str= sc.nextLine();              //reads string
                switch (str) {
                    case "a":
                        System.out.println("Enter alias:");
                        String alias = sc.nextLine();
                        System.out.println("Enter host:");
                        String host = sc.nextLine();
                        System.out.println("Enter port:");
                        int port = Integer.valueOf(sc.nextLine());
                        ag.addServer(alias, host, port);
                        break;
                    case "d":
                        String[] listServers = ag.listServers().split("\n");
                        for (String serverName : listServers){
                            ag.downloadServer(serverName+"/", "/");
                        }
                        break;
                    case "q":
                        System.exit(0);
                        break;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
