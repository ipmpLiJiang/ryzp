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
public class StaffFamily  {


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
     * 称谓
     */
    @ExcelField(value = "称谓")
    private String wcname;

    /**
     * 姓名
     */
    @ExcelField(value = "姓名")
    private String xmname;

    /**
     * 政治面貌
     */
    @ExcelField(value = "政治面貌")
    private String zzmm;

    private String zzmmname;

    /**
     * 出生年月
     */
    @ExcelField(value = "出生年月")
    private Date csdat;

    /**
     * 出生年月s
     */
    @ExcelField(value = "出生年月s")
    private String csdats;

    /**
     * 工作单位及职务
     */
    @ExcelField(value = "工作单位及职务")
    private String gzdwjzw;




}
