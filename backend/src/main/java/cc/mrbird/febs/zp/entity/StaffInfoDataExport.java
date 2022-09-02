package cc.mrbird.febs.zp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author viki
 * @since 2022-01-03
 */

@Excel("StaffInfoDataExport")
@Data
public class StaffInfoDataExport {

    /**
     * 姓名
     */
    @ExcelField(value = "姓名")
    private String ryname;

    /**
     * 性别
     */
    @ExcelField(value = "性别")
    private String sexName;

    /**
     * 出生年月
     */
    @ExcelField(value = "出生年月")
    private String csdats;

    /**
     * 健康状态
     */
    @ExcelField(value = "健康状态")
    private String jkzt;

    /**
     * 籍贯
     */
    @ExcelField(value = "籍贯")
    private String zhrjg;

    /**
     * 现工作国家地区
     */
    @ExcelField(value = "现工作国家地区")
    private String ingzgjdq;

    /**
     * 现任单位
     */
    @ExcelField(value = "现任单位")
    private String indw;

    /**
     * 现任职务
     */
    @ExcelField(value = "现任职务")
    private String inzw;

    /**
     * 专业方向
     */
    @ExcelField(value = "专业方向")
    private String zhrzyfx;


    /**
     * 最高学历
     */
    @TableField("zgxl")
    @ExcelField(value = "最高学历")
    private String zgxl;

    /**
     * 最高学历学校
     */
    @TableField("zgxxName")
    @ExcelField(value = "学校名称")
    private String zgxxName;

    /**
     * 是否医师资格证
     */
    @ExcelField(value = "是否医师资格证")
    private String isyszgzName;

    /**
     * 意向学科1
     */
    @ExcelField(value = "意向学科1")
    private String zyks1;

    /**
     * 意向学科2、3
     */
    @ExcelField(value = "意向学科2、3")
    private String zyks2;

    /**
     * 邮箱
     */
    @ExcelField(value = "邮箱")
    private String email;

    /**
     * 联系电话
     */
    @TableField("tel")
    @ExcelField(value = "联系电话")
    private String tel;

    /**
     * 微信号
     */
    @TableField("wechatNo")
    @ExcelField(value = "微信号")
    private String wechatNo;


    /**
     * 家庭成员1
     */
    @ExcelField(value = "家庭成员1")
    private String fl1;

    /**
     * 家庭成员2
     */
    @ExcelField(value = "家庭成员2")
    private String fl2;

    /**
     * 家庭成员3
     */
    @ExcelField(value = "家庭成员3")
    private String fl3;

    /**
     * 家庭成员4
     */
    @ExcelField(value = "家庭成员4")
    private String fl4;

    /**
     * 家庭成员5
     */
    @ExcelField(value = "家庭成员5")
    private String fl5;

    /**
     * 教育经历1
     */
    @ExcelField(value = "教育经历1")
    private String ed1;

    /**
     * 教育经历2
     */
    @ExcelField(value = "教育经历2")
    private String ed2;

    /**
     * 教育经历3
     */
    @ExcelField(value = "教育经历3")
    private String ed3;

    /**
     * 教育经历4
     */
    @ExcelField(value = "教育经历4")
    private String ed4;

    /**
     * 教育经历5
     */
    @ExcelField(value = "教育经历5")
    private String ed5;

    /**
     * 工作经历1
     */
    @ExcelField(value = "工作经历1")
    private String wk1;

    /**
     * 工作经历2
     */
    @ExcelField(value = "工作经历2")
    private String wk2;

    /**
     * 工作经历3
     */
    @ExcelField(value = "工作经历3")
    private String wk3;

    /**
     * 工作经历4
     */
    @ExcelField(value = "工作经历4")
    private String wk4;

    /**
     * 工作经历5
     */
    @ExcelField(value = "工作经历5")
    private String wk5;

    /**
     * 项目信息1
     */
    @ExcelField(value = "项目信息1")
    private String pj1;

    /**
     * 项目信息2
     */
    @ExcelField(value = "项目信息2")
    private String pj2;

    /**
     * 项目信息3
     */
    @ExcelField(value = "项目信息")
    private String pj3;

    /**
     * 项目信息4
     */
    @ExcelField(value = "项目信息4")
    private String pj4;

    /**
     * 项目信息5
     */
    @ExcelField(value = "项目信息5")
    private String pj5;

    /**
     * 文章信息1
     */
    @ExcelField(value = "文章信息1")
    private String ey1;

    /**
     * 文章信息2
     */
    @ExcelField(value = "文章信息2")
    private String ey2;

    /**
     * 文章信息3
     */
    @ExcelField(value = "文章信息3")
    private String ey3;

    /**
     * 文章信息4
     */
    @ExcelField(value = "文章信息4")
    private String ey4;

    /**
     * 文章信息5
     */
    @ExcelField(value = "文章信息5")
    private String ey5;

    /**
     * 获奖情况1
     */
    @ExcelField(value = "获奖情况1")
    private String aw1;

    /**
     * 获奖情况2
     */
    @ExcelField(value = "获奖情况2")
    private String aw2;

    /**
     * 获奖情况3
     */
    @ExcelField(value = "获奖情况3")
    private String aw3;

    /**
     * 获奖情况4
     */
    @ExcelField(value = "获奖情况4")
    private String aw4;

    /**
     * 获奖情况5
     */
    @ExcelField(value = "获奖情况5")
    private String aw5;

}
