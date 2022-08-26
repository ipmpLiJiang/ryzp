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

@Excel("zp_staff_family")
@Data
@Accessors(chain = true)
public class ZpStaffFamily implements Serializable , Comparable<ZpStaffFamily>{

private static final long serialVersionUID=1L;

    /**
     * Id
     */
                                @ExcelField(value ="Id")
    private String id;

    /**
     * 创建人
     */
            @ExcelField(value ="创建人")
    private Long userid;

    /**
     * staffId
     */
    @TableField("staffId")
            @ExcelField(value ="staffId")
    private String staffId;

    /**
     * 称谓
     */
            @ExcelField(value ="称谓")
    private String wcname;

    /**
     * 姓名
     */
            @ExcelField(value ="姓名")
    private String xmname;

    /**
     * 政治面貌
     */
            @ExcelField(value ="政治面貌")
    private String zzmm;

    /**
     * 出生年月
     */
            @ExcelField(value ="出生年月")
    private Date csdat;
    private transient String csdatFrom;
    private transient String csdatTo;

    /**
     * 工作单位及职务
     */
            @ExcelField(value ="工作单位及职务")
    private String gzdwjzw;

    /**
     * by
     */
    @TableField("currencyField")
            @ExcelField(value ="by")
    private String currencyField;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
            @ExcelField(value ="修改时间")
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
            @ExcelField(value ="创建时间")
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;



    public static final String ID ="id" ;

    public static final String USERID ="userid" ;

    public static final String STAFFID ="staffId" ;

    public static final String WCNAME ="wcname" ;

    public static final String XMNAME ="xmname" ;

    public static final String ZZMM ="zzmm" ;

    public static final String CSDAT ="csdat" ;

    public static final String GZDWJZW ="gzdwjzw" ;

    public static final String CURRENCYFIELD ="currencyField" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

@Override
public int compareTo(ZpStaffFamily o) {
        if (this.getId() != null && o.getId() != null) {
        return this.getId().compareTo(o.getId());
        } else if (this.getId() != null) {
        return 1;
        } else {
        return 0;
        }
        }
}