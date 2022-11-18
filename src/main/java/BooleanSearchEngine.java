import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {

    private final Map<String, List<PageEntry>> word2PageEntries = new HashMap<>(); // ключ - слово, значение - список

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        if (pdfsDir == null || !pdfsDir.isDirectory()) {
            throw new IllegalArgumentException("Should be a directory");
        }
        for (var pdfFile : pdfsDir.listFiles()) {
            var doc = new PdfDocument(new PdfReader(pdfFile)); // объект пдф документа
            for (int i = 1; i < doc.getNumberOfPages() + 1; i++) { //пробегаемся по всем страницам
                Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
                PdfPage page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page); // текст со страницы doc.getPage(i)
                var words = text.split("\\P{IsAlphabetic}+"); //разбиваем текст со страницы на слова
                for (var word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }

                for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
                    String word = entry.getKey();
                    Integer count = entry.getValue();
                    PageEntry pageEntry = new PageEntry(pdfFile.getName(), i, count);

                    List<PageEntry> pageEntries = word2PageEntries.getOrDefault(word, new ArrayList<>());
                    pageEntries.add(pageEntry);
                    word2PageEntries.put(word, pageEntries); // => установить по ключу word конкретный список
                }
            }
        }
        word2PageEntries.values()
                .forEach(Collections::sort); // пробежаться по values map и отсортировать
    }

    @Override
    public List<PageEntry> search(String word) {
        return word2PageEntries.get(word);
    }




    
    /*
        List<PageEntry> obj;
        List<PageEntry> pageEntries = word2PageEntries.get(word);
        if (pageEntries == null) {
            obj = new ArrayList<>();
        } else {
            obj = pageEntries; // obj.add выше
        }
     */
}
