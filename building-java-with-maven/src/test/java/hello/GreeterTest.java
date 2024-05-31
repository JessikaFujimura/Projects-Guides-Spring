package hello;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;

public class GreeterTest {
    
    private Greeter greeter = new Greeter();
    
    @Test
    public void greeterSayHello(){
        Assert.assertThat(greeter.sayHello(), containsString("Hello World!"));
    }
}
