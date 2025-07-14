// ChatClient.java
import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Connected to the chat server");

        new ReadThread(socket).start();
        new WriteThread(socket).start();
    }
}

class ReadThread extends Thread {
    private BufferedReader in;

    public ReadThread(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        try {
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }
        } catch (IOException ex) {
            System.out.println("Error reading from server");
        }
    }
}

class WriteThread extends Thread {
    private PrintWriter out;
    private BufferedReader console;

    public WriteThread(Socket socket) throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);
        console = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        try {
            String input;
            while ((input = console.readLine()) != null) {
                out.println(input);
            }
        } catch (IOException ex) {
            System.out.println("Error writing to server");
        }
    }
}
