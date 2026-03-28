package com.example.robot.model.dto;

import com.example.robot.model.enums.Direction;

public record Meta (

        Integer coordinatorX,
        Integer coordinatorY,

        Direction face
){
}
