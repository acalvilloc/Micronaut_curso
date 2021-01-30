package example.micronaut.books;

import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("micronaut") 
public interface AnalyticsClient {

    @Binding("analytics") 
    void updateAnalytics(Book book); 
}