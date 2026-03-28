package com.example.robot.dto;

import com.example.robot.model.dto.Meta;
import com.example.robot.model.enums.Action;

public record CommandRequest(
        Action command,
        Meta meta
) {
}
