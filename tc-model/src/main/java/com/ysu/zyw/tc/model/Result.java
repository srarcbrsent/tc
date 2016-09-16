package com.ysu.zyw.tc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private int code;

    private String description;

    private T body;

    public static abstract class R {

        public int SUCCESS = 0;

        public int UNAUTHORIZED = 401;

        public int FORBIDDEN = 403;

        public int NOT_FOUND = 404;

        public int CONFLICT = 409;

        public int DEPRECATED = 415;

        public int SERVER_ERROR = 500;

    }

}
