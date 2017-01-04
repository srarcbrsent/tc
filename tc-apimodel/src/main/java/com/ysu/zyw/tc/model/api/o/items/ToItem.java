package com.ysu.zyw.tc.model.api.o.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ToItem implements Serializable {

    private static final long serialVersionUID = -7010965435773174319L;

    private String id;

    private String shopId;

    private String title;

    private String description;

    private Long price;

    private Integer stock;

    private Integer salesVolume;

    private Integer favVolume;

    private Integer commentsVolume;

    private List<ToItemCover> covers;

    private List<ToItemAttr> attrs;

    private List<ToItemDetail> details;

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ToItemCover implements Serializable {

        private static final long serialVersionUID = -373752254041045218L;

        private String id;

        private String cover;

    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ToItemAttr implements Serializable {

        private static final long serialVersionUID = -1933245947728814866L;

        private String id;

        private String name;

        private String value;

    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ToItemDetail implements Serializable {

        private static final long serialVersionUID = -780799281741617251L;

        private String id;

        private String detail;

    }

}
