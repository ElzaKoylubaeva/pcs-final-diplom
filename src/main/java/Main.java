import com.google.gson.Gson;

import java.io.File;

public class Main {

    public static final int PORT = 8989;

    public static void main(String[] args) throws Exception {

        SearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        Gson gson = new Gson();
        StopWordService stopWordService = new StopWordService();
        ProcessWordService processWordService = new ProcessWordService(engine, stopWordService);
        Server server = new Server(gson, processWordService);
        server.start();

        // здесь создайте сервер, который отвечал бы на нужные запросы
        // слушать он должен порт 8989
        // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
    }
}