package com.example.robot.dto;

public record ResponseMsg (
        int code,
        String message,
        String detail

        ){
        public ResponseMsg(int code, String message) {
            this(code, message, null);
        }
}
