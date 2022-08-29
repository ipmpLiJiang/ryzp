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
 * @since 2022-08-18
 */

@Excel("zp_staff_project")
@Data
@Accessors(chain = true)
public class ZpStaffProject implements Serializable, Comparable<ZpStaffProject> {

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
     * 项目名称
     */
    @ExcelField(value = "项目名称")
    private String projectname;

    /**
     * 开始日期
     */
    @ExcelField(value = "开始日期")
    private Date srtdat;
    private transient String srtdatFrom;
    private transient String srtdatTo;

    /**
     * 结束日期
     */
    @ExcelField(value = "结束日期")
    private Date enddat;
    private transient String enddatFrom;
    private transient String enddatTo;

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

    public static final String PROJECTNAME = "projectname";

    public static final String SRTDAT = "srtdat";

    public static final String ENDDAT = "enddat";

    public static final String XBLY = "xbly";

    public static final String JF = "jf";

    public static final String BRPM = "brpm";

    public static final String CURRENCYFIELD = "currencyField";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

    @Override
    public int compareTo(ZpStaffProject o) {
        if (this.getId() != null && o.getId() != null) {
            return this.getId().compareTo(o.getId());
        } else if (this.getId() != null) {
            return 1;
        } else {
            return 0;
        }
    }
}
