package com.dedeler.tweetguess.config;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.sql.SQLException;

/**
 * Created by Aykut on 20.02.2016.
 */
@Configuration
public class WebConfig implements ServletContextInitializer {

    @Value("${h2.server.start}")
    private Boolean h2ServerStart;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if(h2ServerStart) {
            initH2TCPServer();
        }
    }

    @Bean(initMethod="start", destroyMethod="stop")
    public Server initH2TCPServer() {
        Server server = null;
        try {
            server = Server.createTcpServer("-tcpAllowOthers");
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return server;
    }

}
