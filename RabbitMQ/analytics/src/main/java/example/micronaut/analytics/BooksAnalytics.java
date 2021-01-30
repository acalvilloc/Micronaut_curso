package example.micronaut.analytics;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class BooksAnalytics {

    private String bookIsbn;
    private Long count;

    public BooksAnalytics() {
    }

    public BooksAnalytics(String bookIsbn, Long count) {
        this.bookIsbn = bookIsbn;
        this.count = count;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}