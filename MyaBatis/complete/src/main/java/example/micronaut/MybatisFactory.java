package example.micronaut;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Factory 
public class MybatisFactory {

    private final DataSource dataSource; 

    public MybatisFactory(DataSource dataSource) {
        this.dataSource = dataSource; 
    }

    @Singleton 
    public SqlSessionFactory sqlSessionFactory() {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        Environment environment = new Environment("dev", transactionFactory, dataSource); 
        Configuration configuration = new Configuration(environment);
        configuration.addMappers("example.micronaut"); 

        return new SqlSessionFactoryBuilder().build(configuration); 
    }

}