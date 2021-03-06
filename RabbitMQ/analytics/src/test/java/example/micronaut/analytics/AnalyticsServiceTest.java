package example.micronaut.analytics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@MicronautTest 
public class AnalyticsServiceTest {

    @Inject 
    AnalyticsService analyticsService;

    @Test
    void testUpdateBookAnalyticsAndGetAnalytics() {
        Book b1 = new Book("1491950358", "Building Microservices");
        Book b2 = new Book("1680502395", "Release It!");

        analyticsService.updateBookAnalytics(b1);
        analyticsService.updateBookAnalytics(b1);
        analyticsService.updateBookAnalytics(b1);
        analyticsService.updateBookAnalytics(b2);

        List<BooksAnalytics> analytics = analyticsService.listAnalytics();
        assertEquals(2, analytics.size());

        assertEquals(3, findBookAnalytics(b1, analytics).getCount().intValue());
        assertEquals(1, findBookAnalytics(b2, analytics).getCount().intValue());
    }

    private BooksAnalytics findBookAnalytics(Book b, List<BooksAnalytics> analytics) {
        return analytics
                .stream()
                .filter(bookAnalytics -> bookAnalytics.getBookIsbn().equals(b.getIsbn()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}