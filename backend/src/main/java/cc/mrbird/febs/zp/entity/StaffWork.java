package cc.mrbird.febs.zp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author viki
 * @since 2022-01-03
 */

@Data
public class StaffWork {

    /**
     * Id
     */
    @ExcelField(value = "Id")
    private String id;



    /**
     * 起始时间
     */
    @ExcelField(value = "起始时间")
    private Date srtdat;

    /**
     * 终止时间
     */
    @ExcelField(value = "终止时间")
    private Date enddat;

    /**
     * 工作单位
     */
    @ExcelField(value = "工作单位")
    private String wkdw;

    /**
     * 职务
     */
    @ExcelField(value = "职务")
    private String wkzw;

    /**
     * 工作学历
     */
    @ExcelField(value = "工作学历")
    private String wkxl;

    /**
     * 合作导师信息
     */
    @ExcelField(value = "合作导师信息")
    private String dsxx;

    /**
     * 备注
     */
    @ExcelField(value = "备注")
    private String remark;

}
