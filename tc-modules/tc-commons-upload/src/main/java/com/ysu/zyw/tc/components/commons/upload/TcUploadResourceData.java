package com.ysu.zyw.tc.components.commons.upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TcUploadResourceData is a metadata of upload resources
 *
 * @author yaowu.zhang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TcUploadResourceData {

    private String folder;

    private String name;

    private String extension;

}
