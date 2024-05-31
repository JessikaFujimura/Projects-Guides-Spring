package hello;

import org.joda.time.LocalTime;

public class HelloWorld{
    public static void main(String[] args) {
        LocalTime localTime = new LocalTime();
        System.out.println("The current local time is "+ localTime);
        
        Greeter greeter = new Greeter();
        System.out.println(greeter.sayHello());
    }
}
