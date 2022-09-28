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
 * @since 2022-08-18
 */

@Data
public class StaffAward {

    /**
     * Id
     */
    @ExcelField(value = "Id")
    private String id;

    /**
     * staffId
     */
    @ExcelField(value = "staffId")
    private String staffId;

    /**
     * 奖项名称
     */
    @ExcelField(value = "奖项名称")
    private String jxname;

    /**
     * 名次
     */
    @ExcelField(value = "名次")
    private String mc;

    /**
     * 获奖时间
     */
    @ExcelField(value = "获奖时间")
    private Date hjdat;

    /**
     * 备注
     */
    @ExcelField(value = "备注")
    private String remark;

}
