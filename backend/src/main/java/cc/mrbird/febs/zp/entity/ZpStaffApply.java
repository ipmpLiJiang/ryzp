package cc.mrbird.febs.zp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
 * @since 2022-01-08
 */

@Excel("zp_staff_apply")
@Data
@Accessors(chain = true)
public class ZpStaffApply implements Serializable, Comparable<ZpStaffApply> {

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
     * posterId
     */
    @TableField("posterId")
    @ExcelField(value = "posterId")
    private String posterId;

    /**
     * 状态
     */
    @ExcelField(value = "状态")
    private Integer state;

    /**
     * 创建日期
     */
    @ExcelField(value = "创建日期")
    private Date crtdat;
    private transient String crtdatFrom;
    private transient String crtdatTo;

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


    public static final String ID = "id";

    public static final String USERID = "userid";

    public static final String STAFFID = "staffId";

    public static final String POSTERID = "posterId";

    public static final String STATE = "state";

    public static final String CRTDAT = "crtdat";

    public static final String CURRENCYFIELD = "currencyField";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

    @Override
    public int compareTo(ZpStaffApply o) {
        if (this.getId() != null && o.getId() != null) {
            return this.getId().compareTo(o.getId());
        } else if (this.getId() != null) {
            return 1;
        } else {
            return 0;
        }
    }
}