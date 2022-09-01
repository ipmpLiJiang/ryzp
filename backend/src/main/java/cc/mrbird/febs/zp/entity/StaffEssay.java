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
 * @since 2022-01-03
 */

@Data
public class StaffEssay {


    /**
     * Id
     */
    @ExcelField(value = "Id")
    private String id;

    /**
     * staffId
     */
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


    private String fbztname;
    private String kwjbname;
    private String brpmname;
}
