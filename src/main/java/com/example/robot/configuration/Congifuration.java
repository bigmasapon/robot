package com.example.robot.configuration;

import com.example.robot.model.pojo.Robot;
import com.example.robot.model.pojo.Table;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Scope("singleton")
public class Congifuration {
        // Object นี้จะคงอยู่ใน Memory ตลอดอายุของ Server
        public Table table;
    public Robot robot;

        @PostConstruct
        public void init() {
            table = new Table();
            robot = new Robot();
        }

        @PreDestroy
        public void cleanup() {
            table = null;
            robot = null;
        }
}
