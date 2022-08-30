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
public class StaffEducation  {

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
     * 学历/学位
     */
    @ExcelField(value = "学历/学位")
    private String xlxw;

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
     * 院校
     */
    @ExcelField(value = "院校")
    private String yxname;

    /**
     * 所学专业
     */
    @ExcelField(value = "所学专业")
    private String sxzy;

    /**
     * 研究方向
     */
    @ExcelField(value = "研究方向")
    private String yjfx;

    /**
     * 导师信息
     */
    @ExcelField(value = "导师信息")
    private String dsxx;


}
