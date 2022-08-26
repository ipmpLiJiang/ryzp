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
     * JCR分区
     */
    @ExcelField(value = "JCR分区")
    private String jcrfq;


}