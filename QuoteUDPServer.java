import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

public class QuoteUDPServer {

    public static void main(String[] args) throws Exception {

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

        DatagramSocket server = new DatagramSocket(8080);
        System.out.println("UDP Quote Server running on port 8080...");

        byte[] buffer = new byte[1024];
        Random rand = new Random();

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            server.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength());

            if (message.equalsIgnoreCase("exit")) {
                System.out.println("Client requested exit.");
                continue;
            }

            if (!message.equalsIgnoreCase("GET")) {
                String error = "ERROR: message must be GET or exit";
                byte[] sendData = error.getBytes();
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length,
                                packet.getAddress(), packet.getPort());
                server.send(sendPacket);
                continue;
            }

            String quote = quotes[rand.nextInt(quotes.length)];
            byte[] sendData = quote.getBytes();

            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length,
                            packet.getAddress(), packet.getPort());

            server.send(sendPacket);

            System.out.println("Sent quote to client.");
        }
    }
}
