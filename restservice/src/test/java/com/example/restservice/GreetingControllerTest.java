package com.example.restservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GreetingControllerTest {
    
    private static GreetingController greetingController;
    
    public GreetingControllerTest(){
        this.greetingController = new GreetingController();
    }
    
    @Test
    public void testGreetingWhenNameIsNotNull(){
        Greeting message = greetingController.greeting("User");
        Assertions.assertNotNull(message);
        Assertions.assertEquals("Hello, User", message.content());
    }
}
