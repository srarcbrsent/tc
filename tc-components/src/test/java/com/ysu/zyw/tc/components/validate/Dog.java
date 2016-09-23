package com.ysu.zyw.tc.components.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Dog {

    private int id;

    private Pig pig;

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pig {

        private int id;

        private Dig dig;

        @Data
        @Accessors(chain = true)
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Dig {

            private int id;

        }

    }

}
