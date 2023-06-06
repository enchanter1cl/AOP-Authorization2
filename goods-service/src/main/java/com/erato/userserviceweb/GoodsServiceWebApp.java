package com.erato.userserviceweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class GoodsServiceWebApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(GoodsServiceWebApp.class, args);

        System.out.println( "Hello World!" );
    }
}
