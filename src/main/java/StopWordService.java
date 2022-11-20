import java.io.*;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StopWordService {

    private final Set<String> stopWords = new HashSet<>();

    public StopWordService() throws IOException {
        File stopFile = new File("stop-ru.txt");
        try (BufferedReader reader = Files.newBufferedReader(stopFile.toPath())) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line);
            }
        }
        System.out.println("stopWords count: " + stopWords.size());
    }

    public List<String> removeStopWord(List<String> words) { // words -все возможные слова от пользователя
        // пробежаться по списку words
        // для каждого слова из words проверить, что stopWords не содержит это слово
        // Если содержит, то игнорируем слово
        // Если нет - учитываем
        return words.stream()
                .filter(word -> !stopWords.contains(word))
                .collect(Collectors.toList());
    }
}
