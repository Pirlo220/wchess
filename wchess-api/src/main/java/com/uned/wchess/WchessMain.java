package com.uned.wchess;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ConfigurationProperties
@ComponentScan
public class WchessMain {

	public static void main(String[] args) {
		System.getProperties().put( "server.port", 8181 );  //8181 port is set here
		SpringApplication.run(WchessMain.class, args);
	}

}
