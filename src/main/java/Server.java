import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    public static final int PORT = 8989;

    private final Gson gson;

    private final ProcessWordService processWordService;

    public Server(Gson gson,
                  ProcessWordService processWordService) {
        this.gson = gson;
        this.processWordService = processWordService;
    }

    public void start() {
        System.out.println("Starting the Server...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started...");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream())) {
                    String words = in.readLine();
                    System.out.println("Searching is starting...");
                    List<PageEntry> result = processWordService.processing(words);
                    String json = gson.toJson(result);
                    System.out.println("The count of results: " + (result == null ? 0 : result.size()));
                    out.println(json);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
