package com.roylao;

import net.joelinn.quartz.jobstore.RedisJobStore;
import net.joelinn.quartz.jobstore.RedisJobStoreSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Autowired(required = false)
    protected RedisJobStore jobStore;

    @Autowired(required = false)
    private RedisJobStoreSchema schema;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
//        jobStore.get

        return builder.sources(Application.class);
    }
}
