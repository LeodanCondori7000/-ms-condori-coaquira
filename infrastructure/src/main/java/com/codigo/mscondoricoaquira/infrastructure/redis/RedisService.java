package com.codigo.mscondoricoaquira.infrastructure.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;
    public void saveInRedis(String key, String value, int exp){
        stringRedisTemplate.opsForValue().set(key, value);
        stringRedisTemplate.expire(key, exp, TimeUnit.MINUTES);
    }
    public String getFromRedis(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
    public void deleteKey(String key){
        stringRedisTemplate.delete(key);
    }

    public Set<String> getKeysByPattern(String pattern) {
        /*Cursor<String> cursor = stringRedisTemplate.keys(pattern).scan();
        Set<String> keys = new HashSet<>();
        while (cursor.hasNext()) {
            keys.add(cursor.next());
        }
        return keys;*/
        ScanOptions options = ScanOptions.scanOptions().match(pattern).build();
        try (Cursor<String> cursor = stringRedisTemplate.scan(options)) {
            Set<String> keys = new HashSet<>();
            while (cursor.hasNext()) {
                keys.add(cursor.next());
            }
            return keys;
        } catch (Exception e) {
            // Log the exception
            //logger.error("Error occurred while scanning Redis keys: {}", e.getMessage());

            // You can choose to throw a RuntimeException or return a default value
            throw new RuntimeException("Error occurred while scanning Redis keys", e);

        }


    }
}
