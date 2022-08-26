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

@Excel("zp_staff_award")
@Data
@Accessors(chain = true)
public class ZpStaffAward implements Serializable , Comparable<ZpStaffAward>{

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
     * 奖项名称
     */
            @ExcelField(value ="奖项名称")
    private String jxname;

    /**
     * 名次
     */
            @ExcelField(value ="名次")
    private String mc;

    /**
     * 获奖时间
     */
            @ExcelField(value ="获奖时间")
    private Date hjdat;
    private transient String hjdatFrom;
    private transient String hjdatTo;

    /**
     * 备注
     */
            @ExcelField(value ="备注")
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

    public static final String JXNAME ="jxname" ;

    public static final String MC ="mc" ;

    public static final String HJDAT ="hjdat" ;

    public static final String GZDWJZW ="gzdwjzw" ;

    public static final String CURRENCYFIELD ="currencyField" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

@Override
public int compareTo(ZpStaffAward o) {
        if (this.getId() != null && o.getId() != null) {
        return this.getId().compareTo(o.getId());
        } else if (this.getId() != null) {
        return 1;
        } else {
        return 0;
        }
        }
}