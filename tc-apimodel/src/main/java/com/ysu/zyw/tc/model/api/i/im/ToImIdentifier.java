package com.ysu.zyw.tc.model.api.i.im;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ToImIdentifier {

    private String receiverAccountId;

    private String receiverRegionId;

}
