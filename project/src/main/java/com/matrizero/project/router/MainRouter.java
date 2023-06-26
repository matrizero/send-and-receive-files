package com.matrizero.project.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;

@Component
public class MainRouter extends RouteBuilder {

    private String path = "\\collection";
    @Override
    public void configure() throws Exception {

        from("file://folder-input"+path+"?delete=true&include=.*\\.txt")
                .routeId("myFileRoute")
                .log("Received file: ${file:name}")
                .log("Header: ${header.CamelFileName} - Path: ${header.CamelFilePath}}")
                .transform().body(String.class, b -> b.toUpperCase())
                .bean(MyBean.class, "process")
                .to("file://folder-output"+path);

    }

    @Component
    class MyBean {
        public void process(String message) {
            System.out.println("Processed message: " + message + " | Time now is:" + LocalDateTime.now());
        }
    }
}


