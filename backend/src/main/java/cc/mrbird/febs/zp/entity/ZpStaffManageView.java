package cc.mrbird.febs.zp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

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
public class ZpStaffManageView {

    /**
     * Id
     */
    @ExcelField(value = "Id")
    private String id;

    /**
     * userid
     */
    @ExcelField(value = "userid")
    private Integer userid;

    /**
     * 账号
     */
    @ExcelField(value = "账号")
    private String username;

    /**
     * 姓名
     */
    @ExcelField(value = "姓名")
    private String ryname;

    /**
     * 性别
     */
    @ExcelField(value = "性别")
    private Integer sex;

    /**
     * 出生年月
     */
    @ExcelField(value = "出生年月")
    private Date csdat;

    /**
     * 出生年月
     */
    @ExcelField(value = "出生年月")
    private String csdats;

    /**
     * 身份证号
     */
    @ExcelField(value = "身份证号")
    private String idnumber;

    /**
     * 电子邮件
     */
    @ExcelField(value = "电子邮件")
    private String email;

    /**
     * 电话号码
     */
    @ExcelField(value = "电话号码")
    private String tel;

    /**
     * 人员状态
     */
    @ExcelField(value = "人员状态")
    private Integer issub;

    /**
     * 登录状态
     */
    @ExcelField(value = "登录状态")
    private String status;

    /**
     * by
     */
    @TableField("currencyField")
    @ExcelField(value = "by")
    private String currencyField;
}
