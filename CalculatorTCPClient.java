import java.io.*;
import java.net.Socket;

public class CalculatorTCPClient {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 9090);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String serverMessage;

        while ((serverMessage = in.readLine()) != null) {
            System.out.println(serverMessage);

            String userInput = stdIn.readLine();
            out.println(userInput);

            System.out.println(in.readLine());
        }
    }
}
