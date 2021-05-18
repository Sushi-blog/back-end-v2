package com.sushiblog.backendv2.error.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
@Getter
@AllArgsConstructor
public enum ErrorCode { //대충 쓰면 나중에 오류날 때 어떤 오류인지 확인하기 어렵다.
    FORMAT_INCORRECT(400,"String format is incorrect"),

    INVALID_TOKEN(400, "Invalid Token"),
    EXPIRED_TOKEN(401, "Token is Expired"),
    UNAUTHENTICATED(401, "UnAuthenticated"),
    NOT_ACCESSIBLE(401, "Check the token"),

    POST_NOT_FOUND(404, "Post Not Found"),
    USER_NOT_FOUND(404, "User Not Found"),
    CATEGORY_NOT_FOUND(404, "Category Not Found"),
    IMAGE_NOT_FOUND(404, "Image Not Found"),

    ALREADY_EXIST(409, "Already exists");


    private final int status;
    private final String message;
}
