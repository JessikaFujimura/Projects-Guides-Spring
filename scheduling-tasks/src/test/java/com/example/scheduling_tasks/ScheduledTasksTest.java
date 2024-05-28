package com.example.scheduling_tasks;

import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ScheduledTasksTest {

    @SpyBean
    ScheduledTasks scheduledTasks;
    
    @Test
    public void testReportCurrentTime(){
        await().atMost(Durations.TEN_SECONDS).untilAsserted(() ->
        {verify(scheduledTasks, atLeast(2)).reportCurrentTime();});
    }
    
    @Test
    public void testReportCurrentDate(){
        await().atMost(Durations.TWO_SECONDS).untilAsserted(() ->
        {verify(scheduledTasks, atLeast(1)).reportCurrentDate();});
    }
}
