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
 * @since 2022-01-08
 */

@Data
public class ZpRecruitPosterView {

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
     * 发布人
     */
    @ExcelField(value = "发布人")
    private String fbr;

    /**
     * 来源
     */
    @ExcelField(value = "来源")
    private String ly;

    /**
     * 发布日期
     */
    @ExcelField(value = "发布日期")
    private Date srtdat;

    /**
     * 结束日期
     */
    @ExcelField(value = "结束日期")
    private Date enddat;

    /**
     * 创建日期
     */
    @ExcelField(value = "创建日期")
    private Date crtdat;

    /**
     * 标题
     */
    @ExcelField(value = "标题")
    private String ptit;

    /**
     * 内容
     */
    @ExcelField(value = "内容")
    private String pnr;

    /**
     * 职务
     */
    @ExcelField(value = "职务")
    private String pzw;

    /**
     * 状态
     */
    @ExcelField(value = "状态")
    private Integer state;

    /**
     * 是否结束
     */
    @ExcelField(value = "是否结束")
    private Integer isend;

    /**
     * 备用
     */
    @ExcelField(value = "备用")
    private String currencyField;


    /**
     * 收到简历
     */
    @ExcelField(value = "收到简历")
    private Integer allNum;

    /**
     * 未处理简历
     */
    @ExcelField(value = "未处理简历")
    private Integer wclNum;

    /**
     * 已查看
     */
    @ExcelField(value = "通过简历")
    private Integer ckNum;

    /**
     * 通过简历
     */
    @ExcelField(value = "通过简历")
    private Integer tgNum;

    /**
     * 拒绝简历
     */
    @ExcelField(value = "拒绝简历")
    private Integer yjjNum;

    /**
     * 大于当前时间
     */
    @ExcelField(value = "大于当前时间")
    private Integer isdate;

}