package com.example.robot.controller;

import com.example.robot.configuration.Congifuration;
import com.example.robot.model.enums.Direction;
import com.example.robot.util.MvcUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainControllerBddTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Congifuration config;

    @BeforeEach
    void beforeEach() {
        config.init();
    }

    @AfterEach
    void afterEach() {
        config.cleanup();
    }

    @Test
    @Order(1)
    void given_first_time_when_place_command_then_success() throws Exception {
        var url = "/execute";
        var content = """
                {
                    "command": "PLACE",
                    "meta": {
                        "coordinator_x": 0,
                        "coordinator_y": 0,
                        "face": "NORTH"
                    }
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(200, jsonNode.get("code").asInt());
    }

    @Test
    @Order(2)
    void given_first_time_when_move_command_then_fail() throws Exception {
        var url = "/execute";
        var content = """
                {
                    "command": "MOVE"
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(500, jsonNode.get("code").asInt());
    }

    @Test
    @Order(3)
    void given_first_time_when_place_command_x_out_of_range_then_fail() throws Exception {
        var url = "/execute";
        var content = """
                {
                    "command": "PLACE",
                    "meta": {
                        "coordinator_x": 5,
                        "coordinator_y": 0,
                        "face": "NORTH"
                    }
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(500, jsonNode.get("code").asInt());
    }

    @Test
    @Order(4)
    void given_first_time_when_place_command_y_out_of_range_then_fail() throws Exception {
        var url = "/execute";
        var content = """
                {
                    "command": "PLACE",
                    "meta": {
                        "coordinator_x": 0,
                        "coordinator_y": 5,
                        "face": "NORTH"
                    }
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(500, jsonNode.get("code").asInt());
    }


    @Test
    @Order(5)
    void given_second_time_when_place_command_then_fail() throws Exception {

        config.robot.setIsOnTable(true);
        config.robot.setCoordinatorX(2);
        config.robot.setCoordinatorY(2);
        config.robot.setFace(Direction.NORTH);


        var url = "/execute";
        var content = """
                {
                    "command": "PLACE",
                    "meta": {
                        "coordinator_x": 1,
                        "coordinator_y": 1,
                        "face": "WEST"
                    }
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(500, jsonNode.get("code").asInt());
    }

    @Test
    @Order(6)
    void given_correct_coordinator_when_move_north_then_success() throws Exception {

        config.robot.setIsOnTable(true);
        config.robot.setCoordinatorX(2);
        config.robot.setCoordinatorY(2);
        config.robot.setFace(Direction.NORTH);

        var url = "/execute";
        var content = """
                {
                    "command": "MOVE"
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(200, jsonNode.get("code").asInt());

        assertEquals(3,config.robot.getCoordinatorY() );
        assertEquals(2,config.robot.getCoordinatorX() );
    }

    @Test
    @Order(7)
    void given_correct_coordinator_when_move_south_then_success() throws Exception {

        config.robot.setIsOnTable(true);
        config.robot.setCoordinatorX(2);
        config.robot.setCoordinatorY(2);
        config.robot.setFace(Direction.SOUTH);

        var url = "/execute";
        var content = """
                {
                    "command": "MOVE"
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(200, jsonNode.get("code").asInt());

        assertEquals(1,config.robot.getCoordinatorY() );
        assertEquals(2,config.robot.getCoordinatorX() );
    }

    @Test
    @Order(8)
    void given_correct_coordinator_when_move_west_then_success() throws Exception {

        config.robot.setIsOnTable(true);
        config.robot.setCoordinatorX(2);
        config.robot.setCoordinatorY(2);
        config.robot.setFace(Direction.WEST);

        var url = "/execute";
        var content = """
                {
                    "command": "MOVE"
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(200, jsonNode.get("code").asInt());

        assertEquals(2,config.robot.getCoordinatorY() );
        assertEquals(1,config.robot.getCoordinatorX() );
    }

    @Test
    @Order(9)
    void given_correct_coordinator_when_move_east_then_success() throws Exception {

        config.robot.setIsOnTable(true);
        config.robot.setCoordinatorX(2);
        config.robot.setCoordinatorY(2);
        config.robot.setFace(Direction.EAST);

        var url = "/execute";
        var content = """
                {
                    "command": "MOVE"
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(200, jsonNode.get("code").asInt());

        assertEquals(2,config.robot.getCoordinatorY() );
        assertEquals(3,config.robot.getCoordinatorX() );
    }

    @Test
    @Order(10)
    void given_correct_coordinator_when_rotate_left_then_success() throws Exception {

        config.robot.setIsOnTable(true);
        config.robot.setCoordinatorX(2);
        config.robot.setCoordinatorY(2);
        config.robot.setFace(Direction.NORTH);

        var url = "/execute";
        var content = """
                {
                    "command": "LEFT"
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(200, jsonNode.get("code").asInt());

        assertEquals(Direction.WEST,config.robot.getFace() );
    }

    @Test
    @Order(11)
    void given_correct_coordinator_when_rotate_right_then_success() throws Exception {

        config.robot.setIsOnTable(true);
        config.robot.setCoordinatorX(2);
        config.robot.setCoordinatorY(2);
        config.robot.setFace(Direction.NORTH);

        var url = "/execute";
        var content = """
                {
                    "command": "RIGHT"
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(200, jsonNode.get("code").asInt());

        assertEquals(Direction.EAST,config.robot.getFace() );
    }


    @Test
    @Order(12)
    void given_origin_coordinator_when_move_left_then_out_of_range_success() throws Exception {

        config.robot.setIsOnTable(true);
        config.robot.setCoordinatorX(0);
        config.robot.setCoordinatorY(0);
        config.robot.setFace(Direction.WEST);

        var url = "/execute";
        var content = """
                {
                    "command": "MOVE"
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(500, jsonNode.get("code").asInt());
    }

    @Test
    @Order(13)
    void given_origin_coordinator_when_move_down_then_out_of_range_success() throws Exception {

        config.robot.setIsOnTable(true);
        config.robot.setCoordinatorX(0);
        config.robot.setCoordinatorY(0);
        config.robot.setFace(Direction.SOUTH);

        var url = "/execute";
        var content = """
                {
                    "command": "MOVE"
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(500, jsonNode.get("code").asInt());
    }

    @Test
    @Order(14)
    void given_origin_coordinator_when_move_right_then_out_of_range_success() throws Exception {

        config.robot.setIsOnTable(true);
        config.robot.setCoordinatorX(4);
        config.robot.setCoordinatorY(4);
        config.robot.setFace(Direction.EAST);

        var url = "/execute";
        var content = """
                {
                    "command": "MOVE"
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(500, jsonNode.get("code").asInt());
    }

    @Test
    @Order(15)
    void given_origin_coordinator_when_move_north_then_out_of_range_success() throws Exception {

        config.robot.setIsOnTable(true);
        config.robot.setCoordinatorX(4);
        config.robot.setCoordinatorY(4);
        config.robot.setFace(Direction.NORTH);

        var url = "/execute";
        var content = """
                {
                    "command": "MOVE"
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(500, jsonNode.get("code").asInt());
    }




    @Test
    @Order(99)
    void given_correct_coordinator_when_report_then_success() throws Exception {

        config.robot.setIsOnTable(true);
        config.robot.setCoordinatorX(2);
        config.robot.setCoordinatorY(2);
        config.robot.setFace(Direction.NORTH);

        String detail = String.format("Output: %d,%d,%s", config.robot.getCoordinatorX(), config.robot.getCoordinatorY(), config.robot.getFace());

        var url = "/execute";
        var content = """
                {
                    "command": "REPORT"
                }
                """;

        var jsonNode = MvcUtil.postData(mvc, url, content);
        assertEquals(200, jsonNode.get("code").asInt());
        assertEquals(detail, jsonNode.get("detail").asText() );
    }

}