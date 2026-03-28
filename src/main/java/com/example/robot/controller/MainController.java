package com.example.robot.controller;

import com.example.robot.configuration.Congifuration;
import com.example.robot.dto.CommandRequest;
import com.example.robot.dto.ResponseMsg;
import com.example.robot.model.dto.Meta;
import com.example.robot.model.enums.Action;
import com.example.robot.model.enums.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {

    private final Congifuration config;
    private final int errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private final int successCode = 200;

    public MainController(Congifuration config) {
        this.config = config;
    }

    @PostMapping("execute")
    public ResponseEntity<ResponseMsg> execute(@RequestBody CommandRequest request) {

        try {
            boolean isOnTable = config.robot.getIsOnTable();
            if (isOnTable) {
                if (request.command() == Action.PLACE) {
                    ResponseMsg resp = new ResponseMsg(errorCode, "Cannnot execut PLACE command bec Robot is already on table");
                    return ResponseEntity.ok(resp);
                } else if (request.command() == Action.MOVE) {
                    HashMap<String, Integer> newCod = move(config.robot.getFace(), config.robot.getCoordinatorX(), config.robot.getCoordinatorY());
                    config.robot.setCoordinatorX(newCod.get("x"));
                    config.robot.setCoordinatorY(newCod.get("y"));

                    ResponseMsg resp = new ResponseMsg(successCode, "Move success");
                    return ResponseEntity.ok(resp);
                } else if (request.command() == Action.LEFT) {
                    Direction newD = rotateDirection(config.robot.getFace(), Action.LEFT);
                    if (newD == null) {
                        ResponseMsg resp = new ResponseMsg(errorCode, "Cannot rotate left");
                        return ResponseEntity.ok(resp);
                    }

                    config.robot.setFace(newD);

                    ResponseMsg resp = new ResponseMsg(successCode, "Rotate left success");
                    return ResponseEntity.ok(resp);
                } else if (request.command() == Action.RIGHT) {
                    Direction newD = rotateDirection(config.robot.getFace(), Action.RIGHT);
                    if (newD == null) {
                        ResponseMsg resp = new ResponseMsg(errorCode, "Cannot rotate left");
                        return ResponseEntity.ok(resp);
                    }

                    config.robot.setFace(newD);

                    ResponseMsg resp = new ResponseMsg(successCode, "Rotate right success");
                    return ResponseEntity.ok(resp);
                } else if (request.command() == Action.REPORT) {
                    String detail = String.format("Output: %d,%d,%s", config.robot.getCoordinatorX(), config.robot.getCoordinatorY(), config.robot.getFace());
                    ResponseMsg resp = new ResponseMsg(successCode, "Robot is already on table", detail);
                    return ResponseEntity.ok(resp);
                } else {
                    ResponseMsg resp = new ResponseMsg(errorCode, "Command action is not supported");
                    return ResponseEntity.ok(resp);
                }

            } else {
                if (request.command() == Action.PLACE) {

                    Meta meta = request.meta();

                    if (meta.coordinatorX() >= config.table.getMaximumCoordinatorX()) {
                        ResponseMsg resp = new ResponseMsg(errorCode, "X is out of range");
                        return ResponseEntity.ok(resp);
                    }

                    if (meta.coordinatorY() >= config.table.getMaximumCoordinatorY()) {
                        ResponseMsg resp = new ResponseMsg(errorCode, "Y is out of range");
                        return ResponseEntity.ok(resp);
                    }

                    config.robot.setIsOnTable(true);
                    config.robot.setCoordinatorX(meta.coordinatorX());
                    config.robot.setCoordinatorY(meta.coordinatorY());
                    config.robot.setFace(meta.face());

                    ResponseMsg resp = new ResponseMsg(successCode, "success");
                    return ResponseEntity.ok(resp);
                } else {
                    ResponseMsg resp = new ResponseMsg(errorCode, "First command should be PLACE");
                    return ResponseEntity.ok(resp);
                }
            }
        }
        catch (Exception e) {
            ResponseMsg resp = new ResponseMsg(errorCode, e.getMessage());
            return ResponseEntity.ok(resp);
        }
    }

    private Direction rotateDirection(Direction curDirection, Action action) {
        if ( curDirection == Direction.NORTH ) {
            if ( action == Action.LEFT ) {
                return Direction.WEST;
            }
            else if ( action == Action.RIGHT ) {
                return Direction.EAST;
            }
            else{
                return null;
            }
        }
        else if ( curDirection == Direction.SOUTH ) {
            if ( action == Action.LEFT ) {
                return Direction.EAST;
            }
            else if ( action == Action.RIGHT ) {
                return Direction.WEST;
            }
            else{
                return null;
            }
        }
        else if ( curDirection == Direction.EAST ) {
            if ( action == Action.LEFT ) {
                return Direction.NORTH;
            }
            else if ( action == Action.RIGHT ) {
                return Direction.SOUTH;
            }
            else{
                return null;
            }
        }
        else if ( curDirection == Direction.WEST ) {
            if ( action == Action.LEFT ) {
                return Direction.SOUTH;
            }
            else if ( action == Action.RIGHT ) {
                return Direction.NORTH;
            }
            else{
                return null;
            }
        }
        return null;
    }

    private HashMap<String, Integer> move(Direction curDirection, int curCoordinatorX, int curCoordinatorY) {
        if ( curDirection == Direction.NORTH ) {
            int newX = curCoordinatorX;
            int newY = curCoordinatorY + 1;

            validateCoordinator(newX, newY);

            return new HashMap<>(Map.of(
                    "x", newX,
                    "y", newY
            ));
        }
        else if ( curDirection == Direction.SOUTH ) {
            int newX = curCoordinatorX;
            int newY = curCoordinatorY - 1;
            validateCoordinator(newX, newY);

            return new HashMap<>(Map.of(
                    "x", newX,
                    "y", newY
            ));
        }
        else if ( curDirection == Direction.EAST ) {
            int newX = curCoordinatorX+1;
            int newY = curCoordinatorY;
            validateCoordinator(newX, newY);

            return new HashMap<>(Map.of(
                    "x", newX,
                    "y", newY
            ));
        }
        else if ( curDirection == Direction.WEST ) {
            int newX = curCoordinatorX-1;
            int newY = curCoordinatorY;
            validateCoordinator(newX, newY);

            return new HashMap<>(Map.of(
                    "x", newX,
                    "y", newY
            ));
        }
        return null;
    }

    private void validateCoordinator(int newX, int newY){
        boolean isXOutOfRange = config.table.isXOutOfRange(newX);
        boolean isYOutOfRange = config.table.isYOutOfRange(newY);

        if ( isXOutOfRange ) {
            throw new RuntimeException("Can't move bec Coordinator X is out of range");
        }
        if ( isYOutOfRange ) {
            throw new RuntimeException("Can't move bec Coordinator Y is out of range");
        }
    }
}
