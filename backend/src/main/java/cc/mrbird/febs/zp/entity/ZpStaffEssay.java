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
     * 论文/论著名称
     */
    @ExcelField(value = "论文/论著名称")
    private String lwlzmc;

    /**
     * 作者姓名
     */
    @ExcelField(value = "作者姓名")
    private String zzname;

    /**
     * 发表期刊
     */
    @ExcelField(value = "发表期刊")
    private String fbqk;

    /**
     * 发表/出版年月
     */
    @ExcelField(value = "发表/出版年月")
    private String fbcbny;

    /**
     * 收录情况
     */
    @ExcelField(value = "收录情况")
    private String slqk;

    /**
     * 影响因子
     */
    @ExcelField(value = "影响因子")
    private String yxyz;

    /**
     * 他引次数
     */
    @ExcelField(value = "他引次数")
    private String tycs;

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
     * JCR分区
     */
    @TableField("jcrfq")
    @ExcelField(value = "JCR分区")
    private String jcrfq;


    public static final String ID = "id";

    public static final String USERID = "userid";

    public static final String STAFFID = "staffId";

    public static final String LWLZMC = "lwlzmc";

    public static final String ZZNAME = "zzname";

    public static final String FBQK = "fbqk";

    public static final String FBCBNY = "fbcbny";

    public static final String SLQK = "slqk";

    public static final String YXYZ = "yxyz";

    public static final String TYCS = "tycs";

    public static final String CURRENCYFIELD = "currencyField";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String JCRFQ = "jcrfq";

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