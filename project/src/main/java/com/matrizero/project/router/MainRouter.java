package com.matrizero.project.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class MainRouter extends RouteBuilder {

    private String path = "files-a\\my-files";
    @Override
    public void configure() throws Exception {

        from("file://"+path+"?delete=true")
                .routeId("jsonFileRoute")
                .log("Recebido arquivo: ${file:name}")
                .log("Header: ${header.CamelFileName} - Path: ${header.CamelFilePath}}")
                .bean(MyBean.class, "process")
                .to("file://files-b");
    }
}


@Component
class MyBean {
    public void process(String message) {
        // Lógica de processamento do Bean
        System.out.println("Mensagem processada: " + message);
    }
}