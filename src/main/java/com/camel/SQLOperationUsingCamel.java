package com.camel;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

import java.util.Set;

public class SQLOperationUsingCamel {

    public static void main(String[] args) throws Exception {

        // Mysql data source
        MysqlDataSource dataSource = new MysqlDataSource();
        // localhost:3306 - default port for mysql
        // camel - database created by you
        dataSource.setURL("jdbc:mysql://localhost:3306/camel");
        // replace "root@localhost" with your name
        dataSource.setUser("root@localhost");
        // replace "your-password" with your sql password
        dataSource.setPassword("your-password");

        SimpleRegistry registry = new SimpleRegistry();
        registry.bind("myDataSource", dataSource);

        // Creation of Camel Context.
        CamelContext context = new DefaultCamelContext(registry);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                // Source URL
                from("direct:start")
                        // Destination URL
                        .to("jdbc:myDataSource")
                        .bean(new QueryResultHandler(), "printResult");
            }

            // Method implementation not required in this use-case
            @Override
            public Set<String> updateRoutesToCamelContext(CamelContext context) throws Exception {
                return null;
            }
        });

        // Staring the camel route
        context.start();

        ProducerTemplate producerTemplate = context.createProducerTemplate();
        // 'customer' - Table name
        producerTemplate.sendBody("direct:start", "select * from customer");
    }
}
