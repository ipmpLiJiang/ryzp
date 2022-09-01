package cc.mrbird.febs.zp.entity;

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

@Excel("zp_staff_essay")
@Data
@Accessors(chain = true)
public class ZpStaffEssay implements Serializable, Comparable<ZpStaffEssay> {

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
     * 文章名称
     */
    @ExcelField(value = "文章名称")
    private String wzname;

    /**
     * 本人排名
     */
    @ExcelField(value = "本人排名")
    private String brpm;

    /**
     * 刊物级别
     */
    @ExcelField(value = "刊物级别")
    private String kwjb;


    /**
     * 出版时间
     */
    @ExcelField(value = "出版时间")
    private Date cbdat;
    private transient String cbdatFrom;
    private transient String cbdatTo;

    /**
     * 发布状态
     */
    @ExcelField(value = "发布状态")
    private String fbzt;

    /**
     * 出版刊物
     */
    @ExcelField(value = "出版刊物")
    private String cbkw;

    /**
     * 刊号
     */
    @ExcelField(value = "刊号")
    private String cbkh;

    /**
     * 影响因子
     */
    @ExcelField(value = "影响因子")
    private String yxyz;

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

    private transient String fbztname;
    private transient String kwjbname;
    private transient String brpmname;

    public static final String ID = "id";

    public static final String USERID = "userid";

    public static final String STAFFID = "staffId";

    public static final String WZNAME = "wzname";

    public static final String BRPM = "brpm";

    public static final String KWJB = "kwjb";

    public static final String CBDAT = "cbdat";

    public static final String FBZT = "fbzt";

    public static final String CBKW = "cbkw";

    public static final String CBKH = "cbkh";

    public static final String YXYZ = "yxyz";

    public static final String CURRENCYFIELD = "currencyField";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

    @Override
    public int compareTo(ZpStaffEssay o) {
        if (this.getId() != null && o.getId() != null) {
            return this.getId().compareTo(o.getId());
        } else if (this.getId() != null) {
            return 1;
        } else {
            return 0;
        }
    }
}
