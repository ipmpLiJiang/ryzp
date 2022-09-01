package cc.mrbird.febs.export.pdf;

import cc.mrbird.febs.zp.entity.StaffEducation;
import cc.mrbird.febs.zp.entity.StaffEssay;
import cc.mrbird.febs.zp.entity.StaffInfo;
import cc.mrbird.febs.zp.entity.StaffWork;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author lijiang
 * @createDate 2020/11/5
 */
public class StaffInfoPdf {
    //    private Color black = new Color(0, 0, 0); // 黑色
//    private Color red = new Color(255, 0, 0); // 红色
//    private Color blue = new Color(0, 0, 255); // 蓝色
    BaseColor black = BaseColor.BLACK;
    BaseColor red = BaseColor.RED;
    BaseColor blue = BaseColor.BLUE;
    private int bold = Font.BOLD; // 粗体
    private int normal = Font.NORMAL; // 正常字体
    private int italic = Font.ITALIC; // 斜体
    private int boldItalic = Font.BOLDITALIC; // 粗斜体
    private float setting = 100; // 首行缩进参数

    public Document createDoc(String filename) throws Exception {
        // 新建document对象
        // 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        // 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        return document;
    }

    public Image writeImg(String imgPath) throws Exception {
        Image img = Image.getInstance(imgPath); // 控制图片大小
        img.scaleAbsolute(100, 100);
        return img;
    }

    public boolean checkFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static Paragraph convertParToChinese(String text, int fontsize, int fontStyle, BaseColor color)
            throws Exception {
        BaseFont baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font fontChinese = new Font(baseFontChinese, fontsize, fontStyle, color);
        Paragraph graph = new Paragraph(text, fontChinese);
        return graph;
    }

    public Chunk convertChunkByChinese(String text, int fontsize, int fontStyle, BaseColor color) throws Exception {
        BaseFont baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font fontChinese = new Font(baseFontChinese, fontsize, fontStyle, color);
        Chunk chunk = new Chunk(text, fontChinese);
        return chunk;
    }


    public void writeStaffPdf(String fileName, String mergeFileName, List<String> mergeAddPdfList, String imgUrl, StaffInfo staffInfo) throws Exception {
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        FileOutputStream out = new FileOutputStream(fileName);
        PdfWriter.getInstance(document, out);

        document.open(); // 文档里写入
        BaseFont baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

        Font fontTitle = new Font(baseFontChinese, 10, normal, black);
        Font fontValue = new Font(baseFontChinese, 9, normal, black);

        Float contentHeightStaff = 28f;
        Float contentHeightStaff2 = 50f;
        Float faddingSize = 5f;

        int numColumns = 25;
        int totalWidth = 540;
        int[] setWids = new int[numColumns];
        PdfPTable table = null;
        PdfPCell cell;

        String titleCover_1 = "人才招聘信息登记表";
        Font fontCover1 = new Font(baseFontChinese, 16, normal, black);
        String titleCover = " ";
        Font fontCover = new Font(baseFontChinese, 10, normal, black);
        String titleCover_jbxx = "基本信息";
        Font titleFontCover = new Font(baseFontChinese, 12, normal, black);

        //region 第一页
        document.newPage();
        table = new PdfPTable(numColumns);

        //table总Width宽度
        table.setTotalWidth(totalWidth);
        //设置总Width宽度 生效
        table.setLockedWidth(true);
        //列布局
        for (int i = 0; i < numColumns; i++) {
            setWids[i] = 1;
        }
        table.setWidths(setWids);

        //协和医院高层次人才招聘信息登记表
        cell = new PdfPCell(new Phrase(titleCover_1, fontCover1));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);
        // 空一行
        cell = new PdfPCell(new Phrase(titleCover, fontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);
        // 基本信息Brief introduction
        cell = new PdfPCell(new Phrase(titleCover_jbxx, titleFontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);
        // 空一行
        cell = new PdfPCell(new Phrase(titleCover, fontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        //region 基本信息Brief introduction
        String title1_1_1 = "姓名";
        String title1_1_2 = "性别";
        String title1_2_1 = "出生日期";
        String title1_2_2 = "身份证号";
        String title1_3_1 = "籍贯";
        String title1_3_2 = "健康状态";
        String title1_4_1 = "身高cm";
        String title1_4_2 = "体重kg";
        String title1_5_1 = "血型";
        String title1_5_2 = "民族";
        String title1_5_3 = "是否服从调剂";
        String title1_6_1 = "政治面貌";
        String title1_6_2 = "婚姻状况";
        String title1_6_3 = "子女个数";
        String title1_7_1 = "第一志愿科室";
        String title1_7_2 = "第二志愿科室";
        String title1_7_3 = "最高学历";
        String title1_8_1 = "外语水平";
        String title1_8_2 = "外语水平分数";
        String title1_8_3 = "计算机水平";
        String title1_9_1 = "电子邮箱";
        String title1_9_2 = "手机号码";
        String title1_9_3 = "微信号码";
        String title1_10_1 = "家庭住址";
        String title1_10_2 = "户籍地址";
        String title1_10_3 = "现居地址";
        String title1_11_1 = "紧急联系人";
        String title1_11_2 = "联系人号码";
        String title1_11_3 = "是否医师资格证";
        String title1_12_1 = "职业类型";
        String title1_12_2 = "毕业时是否取得住院医师规范化培训合格证";
        String title1_12_3 = "硕士阶段是否四证合一";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String value1_1_1 = staffInfo.getRyname();
        String value1_1_2 = staffInfo.getSex() == null || staffInfo.getSex() == 0 ? "男" : "女";
        String value1_2_1 = dateFormat.format(staffInfo.getCsdat());
        String value1_2_2 = staffInfo.getIdnumber();
        String value1_3_1 = staffInfo.getZhrjg();
        String value1_3_2 = staffInfo.getJkzt();
        String value1_4_1 = staffInfo.getZhrsg() == null ? "" : staffInfo.getZhrsg().toString();
        String value1_4_2 = staffInfo.getZhrtz() == null ? "" : staffInfo.getZhrtz().toString();
        String value1_5_1 = staffInfo.getZhrxx();
        String value1_5_2 = staffInfo.getZhrmzName();
        String value1_5_3 = staffInfo.getIsfcdj() == 0 ? "否" : "是";
        String value1_6_1 = staffInfo.getZzmmName();
        String value1_6_2 = staffInfo.getHyztName();
        String value1_6_3 = staffInfo.getZngs() == null ? "" : staffInfo.getZngs().toString();
        String value1_7_1 = staffInfo.getZyks1();
        String value1_7_2 = staffInfo.getZyks2();
        String value1_7_3 = staffInfo.getZgxl();
        String value1_8_1 = staffInfo.getWyspName();
        String value1_8_2 = staffInfo.getWyspfs() == null ? "" : staffInfo.getWyspfs().toString();
        String value1_8_3 = staffInfo.getJsjspName();
        String value1_9_1 = staffInfo.getEmail();
        String value1_9_2 = staffInfo.getTel();
        String value1_9_3 = staffInfo.getWechatNo();
        String value1_10_1 = staffInfo.getJtzz();
        String value1_10_2 = staffInfo.getHjdz();
        String value1_10_3 = staffInfo.getXjdz();
        String value1_11_1 = staffInfo.getJjlxr();
        String value1_11_2 = staffInfo.getLxrtel();
        String value1_11_3 = staffInfo.getIsyszgz() == 0 ? "否" : "是";
        String value1_12_1 = staffInfo.getZylxName();
        String value1_12_2 = staffInfo.getIsbysqdzyys() == 0 ? "否" : "是";
        String value1_12_3 = staffInfo.getIsssjdszhy() == 0 ? "否" : "是";

        //列一
        //姓名
        cell = new PdfPCell(new Phrase(title1_1_1, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_1_1
        cell = new PdfPCell(new Phrase(value1_1_1, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(6);
        table.addCell(cell);
        //性别
        cell = new PdfPCell(new Phrase(title1_1_2, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_1_2
        cell = new PdfPCell(new Phrase(value1_1_2, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(8);
        table.addCell(cell);

        Image zjImage = loadingPicture(imgUrl);
        cell.setPhrase(new Paragraph(new Chunk(zjImage, 14, -48)));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        cell.setRowspan(4);
        table.addCell(cell);
        //列二
        //出生年月
        cell = new PdfPCell(new Phrase(title1_2_1, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_2_1
        cell = new PdfPCell(new Phrase(value1_2_1, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(6);
        table.addCell(cell);
        //身份证号码
        cell = new PdfPCell(new Phrase(title1_2_2, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_2_2
        cell = new PdfPCell(new Phrase(value1_2_2, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(8);
        table.addCell(cell);

        //列三
        //籍贯
        cell = new PdfPCell(new Phrase(title1_3_1, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_3_1
        cell = new PdfPCell(new Phrase(value1_3_1, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(6);
        table.addCell(cell);
        //健康状态
        cell = new PdfPCell(new Phrase(title1_3_2, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_3_2
        cell = new PdfPCell(new Phrase(value1_3_2, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(8);
        table.addCell(cell);
        //列四
        //身高cm
        cell = new PdfPCell(new Phrase(title1_4_1, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_4_1
        cell = new PdfPCell(new Phrase(value1_4_1, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(6);
        table.addCell(cell);
        //体重
        cell = new PdfPCell(new Phrase(title1_4_2, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_4_2
        cell = new PdfPCell(new Phrase(value1_4_2, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(8);
        table.addCell(cell);
        //列五
        //血型
        cell = new PdfPCell(new Phrase(title1_5_1, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_5_1
        cell = new PdfPCell(new Phrase(value1_5_1, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(6);
        table.addCell(cell);
        //民族
        cell = new PdfPCell(new Phrase(title1_5_2, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_5_2
        cell = new PdfPCell(new Phrase(value1_5_2, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);
        //是否服从调剂
        cell = new PdfPCell(new Phrase(title1_5_3, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_5_3
        cell = new PdfPCell(new Phrase(value1_5_3, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);
        //列六
        //政治面貌
        cell = new PdfPCell(new Phrase(title1_6_1, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_6_1
        cell = new PdfPCell(new Phrase(value1_6_1, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(6);
        table.addCell(cell);
        //婚姻状况
        cell = new PdfPCell(new Phrase(title1_6_2, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_6_2
        cell = new PdfPCell(new Phrase(value1_6_2, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);
        //子女个数
        cell = new PdfPCell(new Phrase(title1_6_3, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_6_3
        cell = new PdfPCell(new Phrase(value1_6_3, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);
        //列七
        //第一志愿科室
        cell = new PdfPCell(new Phrase(title1_7_1, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_7_1
        cell = new PdfPCell(new Phrase(value1_7_1, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(6);
        table.addCell(cell);
        //第二志愿科室
        cell = new PdfPCell(new Phrase(title1_7_2, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_7_2
        cell = new PdfPCell(new Phrase(value1_7_2, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);
        //最高学历
        cell = new PdfPCell(new Phrase(title1_7_3, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_7_3
        cell = new PdfPCell(new Phrase(value1_7_3, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);

        //列八
        //外语水平
        cell = new PdfPCell(new Phrase(title1_8_1, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_8_1
        cell = new PdfPCell(new Phrase(value1_8_1, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(6);
        table.addCell(cell);
        //外语水平分数
        cell = new PdfPCell(new Phrase(title1_8_2, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_8_2
        cell = new PdfPCell(new Phrase(value1_8_2, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);
        //计算机水平
        cell = new PdfPCell(new Phrase(title1_8_3, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_8_3
        cell = new PdfPCell(new Phrase(value1_8_3, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);

        //列九
        //电子邮箱
        cell = new PdfPCell(new Phrase(title1_9_1, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_9_1
        cell = new PdfPCell(new Phrase(value1_9_1, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(6);
        table.addCell(cell);
        //手机号码
        cell = new PdfPCell(new Phrase(title1_9_2, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_9_2
        cell = new PdfPCell(new Phrase(value1_9_2, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);
        //微信号码
        cell = new PdfPCell(new Phrase(title1_9_3, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_9_3
        cell = new PdfPCell(new Phrase(value1_9_3, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);

        //列十
        //家庭住址
        cell = new PdfPCell(new Phrase(title1_10_1, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_10_1
        cell = new PdfPCell(new Phrase(value1_10_1, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(6);
        table.addCell(cell);
        //户籍地址
        cell = new PdfPCell(new Phrase(title1_10_2, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_10_2
        cell = new PdfPCell(new Phrase(value1_10_2, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);
        //现居地址
        cell = new PdfPCell(new Phrase(title1_10_3, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_10_3
        cell = new PdfPCell(new Phrase(value1_10_3, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);

        //列十一
        //紧急联系人
        cell = new PdfPCell(new Phrase(title1_11_1, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_11_1
        cell = new PdfPCell(new Phrase(value1_11_1, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(6);
        table.addCell(cell);
        //联系人号码
        cell = new PdfPCell(new Phrase(title1_11_2, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_11_2
        cell = new PdfPCell(new Phrase(value1_11_2, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);
        //是否医师资格证
        cell = new PdfPCell(new Phrase(title1_11_3, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_11_3
        cell = new PdfPCell(new Phrase(value1_11_3, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);

        //列十二
        //职业类型
        cell = new PdfPCell(new Phrase(title1_12_1, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff2);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_12_1
        cell = new PdfPCell(new Phrase(value1_12_1, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff2);
        cell.setColspan(6);
        table.addCell(cell);
        //毕业时是否取得住院医师规范化培训合格证
        cell = new PdfPCell(new Phrase(title1_12_2, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff2);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_12_2
        cell = new PdfPCell(new Phrase(value1_12_2, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff2);
        cell.setColspan(5);
        table.addCell(cell);
        //硕士阶段是否四证合一
        cell = new PdfPCell(new Phrase(title1_12_3, fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff2);
        cell.setColspan(3);
        table.addCell(cell);
        //value1_12_3
        cell = new PdfPCell(new Phrase(value1_12_3, fontValue));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff2);
        cell.setColspan(5);
        table.addCell(cell);
        //endregion

        // 空一行
        cell = new PdfPCell(new Phrase(titleCover, fontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        //region 教育经历
        String titleCover_jyjl = "教育经历";
        // 教育经历
        cell = new PdfPCell(new Phrase(titleCover_jyjl, titleFontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        // 空一行
        cell = new PdfPCell(new Phrase(titleCover, fontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        //学历/学位
        cell = new PdfPCell(new Phrase("学历/学位", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);

        //起始时间
        cell = new PdfPCell(new Phrase("起始时间", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);

        //终止时间
        cell = new PdfPCell(new Phrase("终止时间", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);

        //院校
        cell = new PdfPCell(new Phrase("院校", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);
        //学科专业1
        cell = new PdfPCell(new Phrase("学科专业1", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(4);
        table.addCell(cell);
        //研究方向
        cell = new PdfPCell(new Phrase("研究方向", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(4);
        table.addCell(cell);
        //导师信息
        cell = new PdfPCell(new Phrase("导师信息", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);

        if (staffInfo.getEducations() != null) {
            for (StaffEducation item : staffInfo.getEducations()) {
                //学历/学位
                cell = new PdfPCell(new Phrase(item.getXlxw(), fontValue));
                cell.setPadding(faddingSize);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //起始时间
                cell = new PdfPCell(new Phrase(item.getSrtdat() == null ? "" : dateFormat.format(item.getSrtdat()), fontValue));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //终止时间
                cell = new PdfPCell(new Phrase(item.getEnddat() == null ? "" : dateFormat.format(item.getEnddat()), fontValue));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //院校
                cell = new PdfPCell(new Phrase(item.getYxname(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(5);
                table.addCell(cell);
                //学科专业1
                cell = new PdfPCell(new Phrase(item.getXkzy1(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(4);
                table.addCell(cell);
                //研究方向
                cell = new PdfPCell(new Phrase(item.getYjfx(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(4);
                table.addCell(cell);
                //导师信息
                cell = new PdfPCell(new Phrase(item.getDsxx(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);
            }
        }
        //endregion

        // 空一行
        cell = new PdfPCell(new Phrase(titleCover, fontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        //region 工作经历
        String titleCover_gzjl = "工作经历";
        cell = new PdfPCell(new Phrase(titleCover_gzjl, titleFontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        // 空一行
        cell = new PdfPCell(new Phrase(titleCover, fontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        //起始时间
        cell = new PdfPCell(new Phrase("起始时间", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);

        //终止时间
        cell = new PdfPCell(new Phrase("终止时间", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);

        //工作单位
        cell = new PdfPCell(new Phrase("工作单位", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(5);
        table.addCell(cell);

        //职务
        cell = new PdfPCell(new Phrase("职务", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(4);
        table.addCell(cell);
        //国家/地区
        cell = new PdfPCell(new Phrase("国家/地区", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(4);
        table.addCell(cell);
        //工作部门
        cell = new PdfPCell(new Phrase("工作部门", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //备注
        cell = new PdfPCell(new Phrase("备注", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        if (staffInfo.getWorks() != null) {
            for (StaffWork item : staffInfo.getWorks()) {
                //起始时间
                cell = new PdfPCell(new Phrase(item.getSrtdat() == null ? "" : dateFormat.format(item.getSrtdat()), fontValue));
                cell.setPadding(faddingSize);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //终止时间
                cell = new PdfPCell(new Phrase(item.getEnddat() == null ? "" : dateFormat.format(item.getEnddat()), fontValue));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //工作单位
                cell = new PdfPCell(new Phrase(item.getWkdw(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(5);
                table.addCell(cell);

                //职务
                cell = new PdfPCell(new Phrase(item.getWkzw(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(4);
                table.addCell(cell);
                //工作学历
                cell = new PdfPCell(new Phrase(item.getWkxl(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(4);
                table.addCell(cell);
                //工作部门
                cell = new PdfPCell(new Phrase(item.getWkbm(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);
                //备注
                cell = new PdfPCell(new Phrase(item.getRemark(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);
            }
        }
        //endregion

        // 空一行
        cell = new PdfPCell(new Phrase(titleCover, fontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        //region 文章信息
        String titleCover_zzlw = "文章信息";
        cell = new PdfPCell(new Phrase(titleCover_zzlw, titleFontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        // 空一行
        cell = new PdfPCell(new Phrase(titleCover, fontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        //文章名称
        cell = new PdfPCell(new Phrase("文章名称", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(4);
        table.addCell(cell);

        //本人排名
        cell = new PdfPCell(new Phrase("本人排名", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);

        //刊物级别
        cell = new PdfPCell(new Phrase("刊物级别", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);

        //出版时间
        cell = new PdfPCell(new Phrase("出版时间", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //发布状态
        cell = new PdfPCell(new Phrase("发布状态", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //出版刊物
        cell = new PdfPCell(new Phrase("出版刊物", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //无此字段
        cell = new PdfPCell(new Phrase("无此字段", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);
        //他引次数
        cell = new PdfPCell(new Phrase("他引次数", fontTitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(contentHeightStaff);
        cell.setColspan(3);
        table.addCell(cell);

        if (staffInfo.getEssays() != null) {
            for (StaffEssay item : staffInfo.getEssays()) {
                //文章名称
                cell = new PdfPCell(new Phrase(item.getWzname(), fontValue));
                cell.setPadding(faddingSize);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(4);
                table.addCell(cell);

                //本人排名
                cell = new PdfPCell(new Phrase(item.getBrpm(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //刊物级别
                cell = new PdfPCell(new Phrase(item.getKwjb(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //出版时间
                cell = new PdfPCell(new Phrase(item.getCbdat().toString(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);
                //发布状态
                cell = new PdfPCell(new Phrase(item.getFbzt(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);
                //出版刊物
                cell = new PdfPCell(new Phrase(item.getCbkw(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);
                //无此字段
                cell = new PdfPCell(new Phrase("", fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);
                //出版刊号
                cell = new PdfPCell(new Phrase(item.getCbkh(), fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                // cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);
            }
        }
        //endregion

        // 空一行
        cell = new PdfPCell(new Phrase(titleCover, fontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        //主要研究领域，学术研究成果
        String titleCover_lyxs = "主要研究领域，学术研究成果";
        cell = new PdfPCell(new Phrase(titleCover_lyxs, titleFontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        // 空一行
        cell = new PdfPCell(new Phrase(titleCover, fontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(staffInfo.getZxhjqk() + "\n ", fontValue));
        cell.setPadding(faddingSize);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(numColumns);
        table.addCell(cell);

        // 空一行
        cell = new PdfPCell(new Phrase(titleCover, fontCover));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setColspan(numColumns);
        table.addCell(cell);

        //其他
        if (staffInfo.getComments() != null && !staffInfo.getComments().equals("")) {
            //其他
            String titleCover_qt = "其他";
            cell = new PdfPCell(new Phrase(titleCover_qt, titleFontCover));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setColspan(numColumns);
            table.addCell(cell);

            // 空一行
            cell = new PdfPCell(new Phrase(titleCover, fontCover));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setColspan(numColumns);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(staffInfo.getComments() + "\n ", fontValue));
            cell.setPadding(faddingSize);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(numColumns);
            table.addCell(cell);
        }

        //如何控制分页展示table，显得紧凑些？在add到document之前添加跨页设置
        table.setSplitLate(false);//跨页处理
        table.setSplitRows(true);
        document.add(table);
        //endregion
        out.flush();
        document.close();
        out.close();

        if (mergeAddPdfList.size() > 1) {
            mergePdfFiles(mergeAddPdfList, mergeFileName);
        }
        //region 水印和页码
        /*
        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        PdfReader reader = new PdfReader(fileName);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outWatermarkFileName));
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            if (i >= 3) {
                // 文字水印
                PdfContentByte over2 = stamp.getOverContent(i);
                over2.beginText();
                // 设置颜色 默认为蓝色
                over2.setColorFill(BaseColor.BLACK);
                // 设置字体字号
                over2.setFontAndSize(bf, 12);
                // 设置起始位置
                over2.setTextMatrix(100, 20);
                over2.showTextAligned(Element.ALIGN_CENTER, "" + (i - 2), 295, 40, 0);
                over2.endText();
            }
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.5f);// 设置透明度为0.3

            // 文字水印
            PdfContentByte over = stamp.getOverContent(i);
            over.beginText();
            // 设置颜色 默认为蓝色
            over.setColorFill(BaseColor.LIGHT_GRAY);
            // 设置字体字号
            over.setFontAndSize(bf, 240);
            // 设置起始位置
            over.setTextMatrix(100, 200);
            over.setGState(gs);

            //over.showTextAligned(Element.ALIGN_CENTER, "武汉协和医院！", 170 + 150, 280, 30);

            over.showTextAligned(Element.ALIGN_CENTER, watermarkName, 170 + 180, 370, 45);
            over.endText();
        }
        stamp.close();
        reader.close();
        */
        //endregion
    }

    private void mergePdfFiles(List<String> fileList, String savePath) {
        Document document = null;
        try {
            document = new Document(new PdfReader(fileList.get(0)).getPageSize(1));
            PdfCopy pdfCopy = new PdfCopy(document, new FileOutputStream(savePath));
            document.open();
            for (int i = 0; i < fileList.size(); ++i) {
                PdfReader pdfReader = new PdfReader(fileList.get(i));
                int numberOfPages = pdfReader.getNumberOfPages();
                for (int j = 1; j <= numberOfPages; ++j) {
                    document.newPage();
                    PdfImportedPage importedPage = pdfCopy.getImportedPage(pdfReader, j);
                    pdfCopy.addPage(importedPage);
                }
                pdfReader.close();
            }
            pdfCopy.flush();
            pdfCopy.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    // 查询图片组装image
    private Image loadingPicture(String picUrl) throws BadElementException, IOException {
        File file = new File(picUrl);
        byte[] by = File2byte(file);
        Image image = Image.getInstance(by);
        image.scaleAbsolute(75, 100);// 调整图片大小(宽度 高度)
        return image;
    }

    private byte[] File2byte(File tradeFile) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /*
    public static void main(String[] args) throws Exception {
        StaffInfoPdf pdfDemo = new StaffInfoPdf();
        ArrayList<String> mergeAddPdfList = new ArrayList<>();
        mergeAddPdfList.add("D:\\demo-Staff.pdf");
        mergeAddPdfList.add("D:\\123.pdf");
        mergeAddPdfList.add("D:\\33333.pdf");
        //region 数据
        StaffInfo info = new StaffInfo();
        info.setRyname("zhangsan");
        info.setSex(0);
        info.setCsdat(new Date());
        info.setZhrgj("中国");
        info.setZhrjg("湖北省武汉市汉阳区降低红衣奥术大师大所sad阿萨德按时");
        info.setIngzgjdq("AAAAAAAAAAAAAAAAAAAAA");
        info.setIndw("BBBBBBBBBBBBBBBBBBBBBB");
        info.setInzw("CCCCCCCCCCCCCCCCCCCCC");
        info.setZhrzyfx("CCCS撒大飒飒发生纠纷雷克萨沙发沙发撒");
        info.setZgxl("本科");
        info.setIszyyszz(1);
        info.setZyks1("红红火火dddddddd");
        info.setZyks2("kkkkkkkakakkakakakakaaakjajajajajjajajaj");
        info.setEmail("289732000ss@qq.com");
        info.setTel("1398333333333");
        info.setWechatNo("1233311111111");
        info.setYjlyxscg("2020年如同一列伤痕累累的列车在时代的轨道上留下了深深的痕迹，每个人都身在其中，病疫之际，各国同舟共济，众志成城，人类命运共同体的含义尽显其中。得见今日破冰之下，春暖花开。2015年7月31日下午，亿万中国观众屏住呼吸，期待着2022冬奥会举办地结果的揭晓，当国际奥委会主席巴赫宣布“北京”时，神州大地翘首以待的亿万中国人沸腾了！如同14年前北京获得2008年奥运会承包权时一样，中国人民在这一刻都沉浸在忘我的兴奋状态之中。" +
                "现如今，北京2022年冬奥会的筹备工作正在有序进行中。作为担当民族复兴大任的时代新人，我们秉承“让冬季运动融入亿万民众，让奥林匹克点亮青年梦想”的理念，普及冬奥知识，弘扬奥运精神，展示冬奥魅力，以微薄之力，以赤诚之心，助力冬奥！让我们一起携手走进冬奥会的现场，看今日中国之姿，看冬奥会华灯璀璨。" +
                "冬奥会全称为“冬季奥林匹克运动会”，是世界上规模最大的冬季综合性运动会。自1924年开始第一届，截至2018年共举办了23届。而2022年的第24届冬奥会，北京将承办所有冰上项目。这座有着三千多年历史的古都，也将成为奥运史上首个举办过夏季和冬季奥运会的城市！" +
                "北京冬奥会将成为世界冰雪运动发展的里程碑。因为它是在疫情之下开展的，这不仅展现了人类对更高更快更强的追求，还体现了人类不畏艰难，团结一心，克服困难的勇气和信心。" +
                "冰雪舞动，随阳光一刹。让每个身影在山巅潇洒。冰雪舞动，在赛道一滑。让每个梦想都能勇往直达。冬奥会上，运动员拥抱冰雪，与冰雪融为一体，他们奋力追梦，放飞自我，实现自我，为国争光。在冬奥之中，冰场之上，他们是有着伟大奥运精神的追梦人。" +
                "展望2022年，作为一名大学生，我的愿望是争做环保卫士，共创绿色冬奥，为冬奥会尽微薄之力。明年北京的冬奥会，我们每个中国人都是东道主。我虽然不是冰雪运动员，但我心系冬奥。从今天起，我要热爱冰雪运动，保护生态环境，学好外语，为外国友人提供帮助，传播中国文化，讲好中国故事。" +
                "“可爱的我相约可爱的你，激情的约会，点亮爱的出发地……”让我们与冬奥会的吉祥物——冰墩墩一起相约北京，相约冬奥！愿三亿人参与冰雪运动从愿景走向现实。片片飞雪透征衣，点点心曲长精神。江山万里不染尘，冰雪运动看我们。激情与梦想同行，冬奥与你我同在。让我们以冰雪传情，热盼冬奥，拥抱冰雪。" +
                "2022年，强大中国蓄势待发，预祝北京冬奥会圆满成功！");
        info.setIssbhwyq(true);
        info.setIswrcxm(true);
        info.setIsxsdtr(true);
        info.setIsyyrcxm(true);
        List<StaffEducation> educationList  = new ArrayList<>();
        StaffEducation se = new StaffEducation();
        se.setXlxw("本科");
        se.setSrtdat(new Date());
        se.setEnddat(new Date());
        se.setYxname("快点快点快滴滴滴点看快递");
        se.setSxzy("啊啊啊啊啊奥多多多多");
        se.setGjdq("外国nage地方");
        se.setDsxx("阿萨德卡萨丁");
        educationList.add(se);

        StaffEducation se1 = new StaffEducation();
        se1.setXlxw("本科1");
        se1.setSrtdat(new Date());
        se1.setEnddat(new Date());
        se1.setYxname("快点快点快滴滴滴点看快递1");
        se1.setSxzy("啊啊啊啊啊奥多多多多1");
        se1.setGjdq("外国nage地方1");
        se1.setDsxx("阿萨德卡萨丁1");
        educationList.add(se1);

        StaffEducation se2= new StaffEducation();
        se2.setXlxw("本科1");
        se2.setSrtdat(new Date());
        se2.setEnddat(new Date());
        se2.setYxname("快点快点快滴滴滴点看快递1");
        se2.setSxzy("啊啊啊啊啊奥多多多多1");
        se2.setGjdq("外国nage地方1");
        se2.setDsxx("阿萨德卡萨丁1");
        educationList.add(se2);

        info.setEducations(educationList);

        List<StaffWork> workList  = new ArrayList<>();
        StaffWork work = new StaffWork();
        work.setSrtdat(new Date());
        work.setEnddat(new Date());
        work.setWkdw("打快点ddddddd快点快是是是点");
        work.setWkzw("ddddsss顶顶顶顶s");
        work.setWkgjdq("ddlslslsowoow时刻开始看");
        work.setDsxx("阿萨德卡萨丁");
        workList.add(work);

        StaffWork work1 = new StaffWork();
        work1.setSrtdat(new Date());
        work1.setEnddat(new Date());
        work1.setWkdw("打快点ddddddd快点快是是是点");
        work1.setWkzw("ddddsss顶顶顶顶s");
        work1.setWkgjdq("ddlslslsowoow时刻开始看");
        work1.setDsxx("阿萨德卡萨丁");
        workList.add(work1);

        StaffWork work2 = new StaffWork();
        work2.setSrtdat(new Date());
        work2.setEnddat(new Date());
        work2.setWkdw("打快点ddddddd快点快是是是点");
        work2.setWkzw("ddddsss顶顶顶顶s");
        work2.setWkgjdq("ddlslslsowoow时刻开始看");
        work2.setDsxx("阿萨德卡萨丁");
        workList.add(work2);

        info.setWorks(workList);

        List<StaffEssay> essatList  = new ArrayList<>();
        StaffEssay essay = new StaffEssay();
        essay.setLwlzmc("达拉斯马拉卡可视对讲安会计师");
        essay.setZzname("撒旦教阿卡丽涉及到");
        essay.setFbqk("asd;las电风扇啊阿萨德asdsad ");
        essay.setFbcbny("暗示大家看年");
        essay.setSlqk("asda阿萨德sdas ds dd");
        essay.setYxyz("沙拉酱埃里克世纪大劫案");
        essay.setJcrfq("啥课大赛科技的");
        essay.setTycs("asdas滴滴滴");
        essatList.add(essay);

        StaffEssay essay1 = new StaffEssay();
        essay1.setLwlzmc("达拉斯马拉卡可视对讲安会计师");
        essay1.setZzname("撒旦教阿卡丽涉及到");
        essay1.setFbqk("asd;las电风扇啊阿萨德asdsad ");
        essay1.setFbcbny("暗示大家看年");
        essay1.setSlqk("asda阿萨德sdas ds dd");
        essay1.setYxyz("沙拉酱埃里克世纪大劫案");
        essay1.setJcrfq("啥课大赛科技的");
        essay1.setTycs("asdas滴滴滴");
        essatList.add(essay1);

        StaffEssay essay2 = new StaffEssay();
        essay2.setLwlzmc("达拉斯马拉卡可视对讲安会计师");
        essay2.setZzname("撒旦教阿卡丽涉及到");
        essay2.setFbqk("asd;las电风扇啊阿萨德asdsad ");
        essay2.setFbcbny("暗示大家看年");
        essay2.setSlqk("asda阿萨德sdas ds dd");
        essay2.setYxyz("沙拉酱埃里克世纪大劫案");
        essay2.setJcrfq("啥课大赛科技的");
        essay2.setTycs("asdas滴滴滴");
        essatList.add(essay2);

        info.setEssays(essatList);
        //endregion
        pdfDemo.writeStaffPdf(mergeAddPdfList.get(0), "D:\\demo-Staff1.pdf", mergeAddPdfList, "C:\\Users\\dajiang\\Desktop\\img\\1.jpg", info);

    }
    */

}
