import java.io.IOException;
import java.util.*;

public class ProcessWordService {

    private final SearchEngine searchEngine;

    private final StopWordService stopWordService;

    public ProcessWordService(SearchEngine searchEngine, StopWordService stopWordService) throws IOException {
        this.searchEngine = searchEngine;
        this.stopWordService = stopWordService;
    }

    public List<PageEntry> processing(String query) {
        Map<BookInfo, Integer> allResults = new HashMap<>();
        //засплитим слова
        var words = query.split("\\P{IsAlphabetic}+");
        List<String> filteredWords = stopWordService.removeStopWord(List.of(words));
        //каждое найдем на странице с пом поисковика
        for (var word : filteredWords) {
            List<PageEntry> results = searchEngine.search(word);
            for (var pageEntry : results) {
                BookInfo key = new BookInfo(pageEntry.getPdfName(), pageEntry.getPage());
                Integer value = allResults.getOrDefault(key, 0);
                value += pageEntry.getCount();
                allResults.put(key, value);
            }
        }
        Queue<PageEntry> queue = new PriorityQueue<>();
        for (Map.Entry<BookInfo, Integer> entry : allResults.entrySet()) {
            BookInfo key = entry.getKey();
            Integer count = entry.getValue();
            PageEntry pageEntry = new PageEntry(key.getPdfName(), key.getPage(), count);
            queue.offer(pageEntry);
        }
        return new ArrayList<>(queue);
    }
}

