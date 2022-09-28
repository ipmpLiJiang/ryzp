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
public class StaffProject  {

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
     * 项目名称
     */
    @ExcelField(value = "项目名称")
    private String projectname;

    /**
     * 开始日期
     */
    @ExcelField(value = "开始日期")
    private Date srtdat;

    /**
     * 结束日期
     */
    @ExcelField(value = "结束日期")
    private Date enddat;

    /**
     * 项目类别/来源
     */
    @ExcelField(value = "项目类别/来源")
    private String xbly;

    /**
     * 经费(万)
     */
    @ExcelField(value = "经费(万)")
    private String jf;

    /**
     * 本人排名
     */
    @ExcelField(value = "本人排名")
    private String brpm;

}
