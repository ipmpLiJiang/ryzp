package cc.mrbird.febs.com.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lijiang
 * @createDate 2020/8/7
 */
@Data
public class InUploadFile implements Serializable {
    private String id;//外键Id
    private String refTab;//外键表
    private String refType;//外键表
    private String serName;
    private String suffix;
    private String fileName;
}
