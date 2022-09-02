package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zp.dao.ZpPosterStaffViewMapper;
import cc.mrbird.febs.zp.entity.*;
import cc.mrbird.febs.zp.service.*;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-01-08
 */
@Slf4j
@Service("IZpPosterStaffViewService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpPosterStaffViewServiceImpl extends ServiceImpl<ZpPosterStaffViewMapper, ZpPosterStaffView> implements IZpPosterStaffViewService {

    @Autowired
    IZpStaffInfoService iZpStaffInfoService;

    @Autowired
    IZpStaffWorkService iZpStaffWorkService;

    @Autowired
    IZpStaffEducationService iZpStaffEducationService;

    @Autowired
    IZpStaffEssayService iZpStaffEssayService;

    @Autowired
    IZpStaffFamilyService iZpStaffFamilyService;

    @Autowired
    IZpStaffAwardService iZpStaffAwardService;

    @Autowired
    IZpStaffProjectService iZpStaffProjectService;

    @Override
    public IPage<ZpPosterStaffView> findZpPosterStaffViews(QueryRequest request, ZpPosterStaffView zpPosterStaffView) {
        try {
            Page<ZpPosterStaffView> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<ZpPosterStaffView> rpage = this.baseMapper.findZpPosterStaffView(page, zpPosterStaffView);
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (ZpPosterStaffView item : rpage.getRecords()) {
                if (item.getCsdat() != null) {
                    item.setCsdats(sdf.format(item.getCsdat()));
                }
            }
            return rpage;

        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public List<StaffInfoDataExport> excelData(String posterId, Integer state, List<String> idList) {
        List<StaffInfoDataExport> dataList = new ArrayList<>();
        List<ZpStaffInfo> staffList = iZpStaffInfoService.findStaffByPosterId(posterId, state, idList);

        if (staffList.size() > 0) {
            List<ZpStaffEducation> edList = iZpStaffEducationService.findEducationByPosterId(posterId, state, idList);
            List<ZpStaffEssay> eyList = iZpStaffEssayService.findEssayByPosterId(posterId, state, idList);
            List<ZpStaffWork> wkList = iZpStaffWorkService.findWorkByPosterId(posterId, state, idList);
            List<ZpStaffFamily> flList = iZpStaffFamilyService.findFamilyByPosterId(posterId, state, idList);
            List<ZpStaffProject> pjList = iZpStaffProjectService.findProjectByPosterId(posterId, state, idList);
            List<ZpStaffAward> awList = iZpStaffAwardService.findAwardByPosterId(posterId, state, idList);

            List<ZpStaffEducation> edQuery = new ArrayList<>();
            List<ZpStaffEssay> eyQuery = new ArrayList<>();
            List<ZpStaffWork> wkQuery = new ArrayList<>();
            List<ZpStaffAward> awQuery = new ArrayList<>();
            List<ZpStaffProject> pjQuery = new ArrayList<>();
            List<ZpStaffFamily> flQuery = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            for (ZpStaffInfo item : staffList) {
                StaffInfoDataExport data = new StaffInfoDataExport();
                data.setRyname(item.getRyname());
                data.setSexName(item.getSex() == 0 ? "男" : "女");
                if (item.getCsdat() != null) {
                    data.setCsdats(sdf.format(item.getCsdat()));
                }
                data.setJkzt(item.getJkzt());
                data.setZhrjg(item.getZhrjg());
                // 最高学历
//                data.setZgxl(item.getZgxl());
//                edQuery = edList.stream().filter(s -> s.getStaffId().equals(item.getId()) && s.getXlxw() != null && s.getXlxw().equals(item.getZgxl())).collect(Collectors.toList());
//                if (edQuery.size() > 0) {
//                    data.setZgxxName(edQuery.get(0).getYxname());
//                }
                data.setIsyszgzName(item.getIsyszgz() == 1 ? "是" : "否");
                data.setZyks1(item.getZyks1());
                data.setZyks1(item.getZyks2());
                data.setEmail(item.getEmail());
                data.setTel(item.getTel());
                data.setWechatNo(item.getWechatNo());
                // 教育经历
                edQuery = edList.stream().filter(s -> s.getStaffId().equals(item.getId())).collect(Collectors.toList());
                data.setEd5(this.getEdName(edQuery, 4));
                data.setEd4(this.getEdName(edQuery, 3));
                data.setEd3(this.getEdName(edQuery, 2));
                data.setEd2(this.getEdName(edQuery, 1));
                data.setEd1(this.getEdName(edQuery, 0));
                // 工作经历
                wkQuery = wkList.stream().filter(s -> s.getStaffId().equals(item.getId())).collect(Collectors.toList());
                data.setWk5(this.getWkName(wkQuery, 4));
                data.setWk4(this.getWkName(wkQuery, 3));
                data.setWk3(this.getWkName(wkQuery, 2));
                data.setWk2(this.getWkName(wkQuery, 1));
                data.setWk1(this.getWkName(wkQuery, 0));
                // 著作论文
                eyQuery = eyList.stream().filter(s -> s.getStaffId().equals(item.getId())).collect(Collectors.toList());
                data.setEy5(this.getEyName(eyQuery, 4));
                data.setEy4(this.getEyName(eyQuery, 3));
                data.setEy3(this.getEyName(eyQuery, 2));
                data.setEy2(this.getEyName(eyQuery, 1));
                data.setEy1(this.getEyName(eyQuery, 0));
                dataList.add(data);
            }
        }
        return dataList;
    }

    private String getEdName(List<ZpStaffEducation> list, int ng) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String value = "";
        String huanhang = "\n";
        if (list.size() > ng) {
            if (StrUtil.isNotBlank(list.get(ng).getYxname())) {
                value = "院       校：" + list.get(ng).getYxname();
                if (StrUtil.isNotBlank(list.get(ng).getXlxw())) {
                    value += huanhang + "学历/学位：" + list.get(ng).getXlxw();
                }
                if (list.get(ng).getSrtdat() != null && list.get(ng).getEnddat() != null) {
                    value += huanhang + "教育时间：" + sdf.format(list.get(ng).getSrtdat()) + "-" + sdf.format(list.get(ng).getEnddat());
                } else if (list.get(ng).getSrtdat() != null) {
                    value += huanhang + "教育时间：" + sdf.format(list.get(ng).getSrtdat());
                } else if (list.get(ng).getEnddat() != null) {
                    value += huanhang + "教育时间：" + sdf.format(list.get(ng).getEnddat());
                }
                if (StrUtil.isNotBlank(list.get(ng).getDsxx())) {
                    value += huanhang + "导师信息：" + list.get(ng).getDsxx();
                }
                if (StrUtil.isNotBlank(list.get(ng).getXkzy1())) {
                    value += huanhang + "学科专业1：" + list.get(ng).getXkzy1();
                }
                if (StrUtil.isNotBlank(list.get(ng).getXkzy2())) {
                    value += huanhang + "学科专业2：" + list.get(ng).getXkzy2();
                }
                if (StrUtil.isNotBlank(list.get(ng).getXklx())) {
                    value += huanhang + "学科类型：" + list.get(ng).getXklx();
                }
                if (StrUtil.isNotBlank(list.get(ng).getYjfx())) {
                    value += huanhang + "研究方向：" + list.get(ng).getYjfx();
                }
            }
        }
        return value;
    }

    private String getWkName(List<ZpStaffWork> list, int ng) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String value = "";
        String huanhang = "\n";
        if (list.size() > ng) {
            if (StrUtil.isNotBlank(list.get(ng).getWkdw())) {
                value = "工作单位：" + list.get(ng).getWkdw();
                if (list.get(ng).getSrtdat() != null && list.get(ng).getEnddat() != null) {
                    value += huanhang + "工作时间：" + sdf.format(list.get(ng).getSrtdat()) + "-" + sdf.format(list.get(ng).getEnddat());
                } else if (list.get(ng).getSrtdat() != null) {
                    value += huanhang + "工作时间：" + sdf.format(list.get(ng).getSrtdat());
                } else if (list.get(ng).getEnddat() != null) {
                    value += huanhang + "工作时间：" + sdf.format(list.get(ng).getEnddat());
                }
                if (StrUtil.isNotBlank(list.get(ng).getWkbm())) {
                    value += huanhang + "工作部门：" + list.get(ng).getWkbm();
                }
                if (StrUtil.isNotBlank(list.get(ng).getWkzw())) {
                    value += huanhang + "工作职务：" + list.get(ng).getWkzw();
                }
                if (StrUtil.isNotBlank(list.get(ng).getWkxl())) {
                    value += huanhang + "学历：" + list.get(ng).getWkxl();
                }
            }
        }
        return value;
    }

    private String getEyName(List<ZpStaffEssay> list, int ng) {
        String value = "";
        String huanhang = "\n";
        if (list.size() > ng) {
            if (StrUtil.isNotBlank(list.get(ng).getWzname())) {
                value = "文章名称：" + list.get(ng).getWzname();
                if (StrUtil.isNotBlank(list.get(ng).getBrpm())) {
                    value += huanhang + "本人排名：" + list.get(ng).getBrpm();
                }
                if (StrUtil.isNotBlank(list.get(ng).getKwjb())) {
                    value += huanhang + "刊物级别：" + list.get(ng).getKwjb();
                }
                if (list.get(ng).getCbdat() != null) {
                    value += huanhang + "出版时间：" + list.get(ng).getCbdat().toString();
                }
                if (StrUtil.isNotBlank(list.get(ng).getFbzt())) {
                    value += huanhang + "发布状态：" + list.get(ng).getFbzt();
                }
                if (StrUtil.isNotBlank(list.get(ng).getCbkw())) {
                    value += huanhang + "出版刊物：" + list.get(ng).getCbkw();
                }
                if (StrUtil.isNotBlank(list.get(ng).getCbkh())) {
                    value += huanhang + "出版刊号：" + list.get(ng).getCbkh();
                }
                if (StrUtil.isNotBlank(list.get(ng).getYxyz())) {
                    value += huanhang + "影响因子：" + list.get(ng).getYxyz();
                }
            }
        }
        return value;
    }
}
