import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class QuoteUDPServer {
    public static void main(String[] args) throws IOException, Exception {
        String[] quotes = {
                "Believe you can and you're halfway there.",
                "The only way to do great work is to love what you do.",
                "Success is not final, failure is not fatal.",
                "Dream big. Start small. Act now.",
                "Every moment is a fresh beginning.",
                "Discipline is choosing what you want most over what you want now.",
                "You miss 100% of the shots you don’t take.",
                "The future depends on what you do today.",
                "Don’t watch the clock; do what it does. Keep going.",
                "Small steps every day lead to big results."
        };

        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server running on port 8080");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                try {

                    out.println("Type 'GET' for quote or 'exit' to quit");
                    String expression = in.readLine();
                    if (expression.equals("exit")) {
                        out.println("Goodbye!");
                        System.out.println("Client disconnected!");
                        break;
                    }
                    if (expression.equals("GET")) {
                        int randindex = (int) (Math.random() * quotes.length);
                        String quote = quotes[randindex];
                        out.println(quote);
                    }
                    else {
                        out.println("ERROR: message must be 'GET' or 'exit'");
                    }
                }catch (Exception e) {
                    out.println("ERROR: " + e.getMessage());
                }
            }
            socket.close();
            System.out.println("Waiting for new clients...\n");
        }
    }
}