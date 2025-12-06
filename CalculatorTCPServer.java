import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorTCPServer {
    public static void main(String[] args) throws IOException,Exception {

        ServerSocket serverSocket = new ServerSocket(9090);
        System.out.println("Server running on port 9090");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while(true) {
                try {

                    out.println("Connected to server. Enter math expression like x + y(-,/,*,+) or type 'exit':");
                    String expression = in.readLine();
                    if(expression.equals("exit")) {
                        out.println("Goodbye!");
                        System.out.println("Client disconnected!");
                        break;
                    }
                    String[] parts = expression.split(" ");
                    if (parts.length != 3)
                        throw new Exception("invalid expression");
                    double num1 = Double.parseDouble(parts[0]);
                    double num2 = Double.parseDouble(parts[2]);
                    String op = parts[1];
                    double result = 0;
                    switch (op) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            if (num2 == 0) {
                                throw new Exception("Division by zero!");
                            }
                            result = num1 / num2;
                            break;
                        default:
                            throw new Exception("Invalid expression!");
                    }
                    System.out.println("Server received: " + op);
                    out.println(parts[0] + " " + parts[1] + " " + parts[2] + " = " + result);
                } catch (Exception e) {
                    out.println("ERROR: " + e.getMessage());
                    System.out.println("Error sent to client: " + e.getMessage());
                }
            }
            socket.close();
            System.out.println("Waiting for new clients...\n");

        }
    }
    }