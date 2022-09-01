package cc.mrbird.febs.zp.entity;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 *
 * </p>
 *
 * @author viki
 * @since 2022-01-03
 */

@Excel("zp_staff_education")
@Data
@Accessors(chain = true)
public class ZpStaffEducation implements Serializable, Comparable<ZpStaffEducation> {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @ExcelField(value = "Id")
    private String id;

    /**
     * 创建人
     */
    @ExcelField(value = "创建人")
    private Long userid;

    /**
     * staffId
     */
    @TableField("staffId")
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
    private transient String srtdatFrom;
    private transient String srtdatTo;

    /**
     * 终止时间
     */
    @ExcelField(value = "终止时间")
    private Date enddat;
    private transient String enddatFrom;
    private transient String enddatTo;

    /**
     * 院校
     */
    @ExcelField(value = "院校")
    private String yxname;

    /**
     * 学科专业1
     */
    @ExcelField(value = "学科专业1")
    private String xkzy1;

    /**
     * 学科专业2
     */
    @ExcelField(value = "学科专业2")
    private String xkzy2;

    /**
     * 学科类型
     */
    @ExcelField(value = "学科类型")
    private String xklx;

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

    /**
     * by
     */
    @TableField("currencyField")
    @ExcelField(value = "by")
    private String currencyField;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间")
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间")
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    private transient String xlxwname;

    private transient String xklxname;

    public static final String ID = "id";

    public static final String USERID = "userid";

    public static final String STAFFID = "staffId";

    public static final String XLXW = "xlxw";

    public static final String SRTDAT = "srtdat";

    public static final String ENDDAT = "enddat";

    public static final String YXNAME = "yxname";

    public static final String XKZY1 = "xkzy1";

    public static final String XKZY2 = "xkzy2";

    public static final String XKLX = "xklx";

    public static final String YJFX = "yjfx";

    public static final String DSXX = "dsxx";

    public static final String CURRENCYFIELD = "currencyField";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

    @Override
    public int compareTo(ZpStaffEducation o) {
        if (this.getId() != null && o.getId() != null) {
            return this.getId().compareTo(o.getId());
        } else if (this.getId() != null) {
            return 1;
        } else {
            return 0;
        }
    }
}
