package cc.mrbird.febs.zp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import io.swagger.models.auth.In;
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

@Excel("zp_recruit_poster")
@Data
@Accessors(chain = true)
public class ZpRecruitPoster implements Serializable , Comparable<ZpRecruitPoster>{

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
     * 发布人
     */
            @ExcelField(value ="发布人")
    private String fbr;

    /**
     * 来源
     */
            @ExcelField(value ="来源")
    private String ly;

    /**
     * 发布日期
     */
            @ExcelField(value ="发布日期")
    private Date srtdat;
    private transient String srtdatFrom;
    private transient String srtdatTo;

    /**
     * 结束日期
     */
            @ExcelField(value ="结束日期")
    private Date enddat;
    private transient String enddatFrom;
    private transient String enddatTo;

    /**
     * 创建日期
     */
            @ExcelField(value ="创建日期")
    private Date crtdat;
    private transient String crtdatFrom;
    private transient String crtdatTo;

    /**
     * 标题
     */
            @ExcelField(value ="标题")
    private String ptit;

    /**
     * 内容
     */
            @ExcelField(value ="内容")
    private String pnr;

    /**
     * 职务
     */
            @ExcelField(value ="职务")
    private String pzw;

    /**
     * 状态
     */
            @ExcelField(value ="状态")
    private Integer state;

    /**
     * 是否结束
     */
            @ExcelField(value ="是否结束")
    private Integer isend;

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

    public static final String FBR ="fbr" ;

    public static final String LY ="ly" ;

    public static final String SRTDAT ="srtdat" ;

    public static final String ENDDAT ="enddat" ;

    public static final String CRTDAT ="crtdat" ;

    public static final String PTIT ="ptit" ;

    public static final String PNR ="pnr" ;

    public static final String PZW ="pzw" ;

    public static final String STATE ="state" ;

    public static final String ISEND ="isend" ;

    public static final String CURRENCYFIELD ="currencyField" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

@Override
public int compareTo(ZpRecruitPoster o) {
        if (this.getId() != null && o.getId() != null) {
        return this.getId().compareTo(o.getId());
        } else if (this.getId() != null) {
        return 1;
        } else {
        return 0;
        }
        }
}