import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class QuoteUDPClient {
    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("localhost");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter GET or exit: ");
            String msg = sc.nextLine();

            byte[] sendData = msg.getBytes();
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, address, 8080);
            socket.send(sendPacket);

            if (msg.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break; 
            }

            byte[] buffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);

            String reply = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server reply: " + reply);
        }

        socket.close();
    }
}
