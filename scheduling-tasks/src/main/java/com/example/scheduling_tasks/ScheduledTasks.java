package com.example.scheduling_tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTasks {
    
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime(){
        log.info("The time is now {}", LocalDateTime.now().toLocalTime());
    }
    
    @Scheduled(initialDelay = 1000)
    public void reportCurrentDate(){
        log.info("The date today is {}", LocalDateTime.now().toLocalDate());
    }
    
}
