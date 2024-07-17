# MESSAGING REDIS

## About
This is a simple application print a message in terminal using instance of Redis.

You can configure what message do you like to print in main method, where is "Hello World".

To run this application is necessary to have a Docker installed.

Use the command `docker-compose up` to initiate the instance. And `docker-compose down` to kill the instance.

## Lessons Learned
[RedisMessageListenerContainer](https://docs.spring.io/spring-data/data-redis/docs/current/api/org/springframework/data/redis/listener/RedisMessageListenerContainer.html)

[RedisConnectionFactory](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/connection/RedisConnectionFactory.html)

[StringRedisTemplate](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/StringRedisTemplate.html)