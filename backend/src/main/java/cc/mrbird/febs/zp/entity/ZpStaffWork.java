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

@Excel("zp_staff_work")
@Data
@Accessors(chain = true)
public class ZpStaffWork implements Serializable, Comparable<ZpStaffWork> {

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
    @ExcelField(value = "工作部门")
    private String wkbm;

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

    /**
     * 备注
     */
    @TableField("remark")
    @ExcelField(value = "备注")
    private String remark;

    private transient String wkxlname;

    public static final String ID = "id";

    public static final String USERID = "userid";

    public static final String STAFFID = "staffId";

    public static final String SRTDAT = "srtdat";

    public static final String ENDDAT = "enddat";

    public static final String WKDW = "wkdw";

    public static final String WKZW = "wkzw";

    public static final String WKXL = "wkxl";

    public static final String WKBM = "wkbm";

    public static final String CURRENCYFIELD = "currencyField";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String REMARK = "remark";

    @Override
    public int compareTo(ZpStaffWork o) {
        if (this.getId() != null && o.getId() != null) {
            return this.getId().compareTo(o.getId());
        } else if (this.getId() != null) {
            return 1;
        } else {
            return 0;
        }
    }
}
