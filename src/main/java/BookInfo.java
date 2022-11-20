import java.util.Objects;

public class BookInfo {

    private final String pdfName;

    private final int page;

    public BookInfo(String pdfName, int page) {
        this.pdfName = pdfName;
        this.page = page;
    }

    public String getPdfName() {
        return pdfName;
    }

    public int getPage() {
        return page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookInfo that = (BookInfo) o;

        if (page != that.page) return false;
        return Objects.equals(pdfName, that.pdfName);
    }

    @Override
    public int hashCode() {
        int result = pdfName != null ? pdfName.hashCode() : 0;
        result = 31 * result + page;
        return result;
    }
}
