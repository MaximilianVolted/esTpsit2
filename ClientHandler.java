import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {

                //Simulo un'operazione sul server
                // ad esempio una lettura su database
                if ("leggi db".equalsIgnoreCase(inputLine))
                    Thread.sleep(4000);

                System.out.println("Client: " + inputLine);
                out.println("ECHO: " + inputLine);
                if ("exit".equalsIgnoreCase(inputLine))
                    break;

            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
