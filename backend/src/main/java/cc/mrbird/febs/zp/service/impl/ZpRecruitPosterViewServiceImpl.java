package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zp.dao.ZpRecruitPosterViewMapper;
import cc.mrbird.febs.zp.entity.ZpRecruitPosterView;
import cc.mrbird.febs.zp.entity.ZpStaffApply;
import cc.mrbird.febs.zp.service.IZpRecruitPosterViewService;
import cc.mrbird.febs.zp.service.IZpStaffApplyService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-01-08
 */
@Slf4j
@Service("IZpRecruitPosterViewService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpRecruitPosterViewServiceImpl extends ServiceImpl<ZpRecruitPosterViewMapper, ZpRecruitPosterView> implements IZpRecruitPosterViewService {

    @Autowired
    IZpStaffApplyService iZpStaffApplyService;

    @Override
    public IPage<ZpRecruitPosterView> findZpRecruitPosterViews(QueryRequest request, ZpRecruitPosterView zpRecruitPosterView) {
        try {
            Page<ZpRecruitPosterView> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<ZpRecruitPosterView> rpage = this.baseMapper.findZpRecruitPosterView(page, zpRecruitPosterView);
            List<ZpRecruitPosterView> list = rpage.getRecords();
            this.getNum(list);
            return rpage;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ZpRecruitPosterView> findZpRecruitPosterOverdueViews(QueryRequest request, ZpRecruitPosterView zpRecruitPosterView) {
        try {
            Page<ZpRecruitPosterView> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<ZpRecruitPosterView> rpage = this.baseMapper.findZpRecruitPosterOverdueView(page, zpRecruitPosterView);
            List<ZpRecruitPosterView> list = rpage.getRecords();
            this.getNum(list);
            return rpage;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    private void getNum(List<ZpRecruitPosterView> list) {
        Long wclNum = 0l;
        Long ckNum = 0l;
        Long tgNum = 0l;
        Long yjjNum = 0l;
        for (ZpRecruitPosterView item : list) {
            LambdaQueryWrapper<ZpStaffApply> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ZpStaffApply::getPosterId, item.getId());
            List<ZpStaffApply> applyList = iZpStaffApplyService.list(wrapper);
            if (applyList.size() > 0) {
                item.setAllNum(applyList.size());
                wclNum = applyList.stream().filter(s -> s.getState() == 0).count();
                ckNum = applyList.stream().filter(s -> s.getState() == 1).count();
                tgNum = applyList.stream().filter(s -> s.getState() == 6).count();
                yjjNum = applyList.stream().filter(s -> s.getState() == 2).count();
            } else {
                item.setAllNum(0);
                wclNum = 0l;
                ckNum = 0l;
                tgNum = 0l;
                yjjNum = 0l;
            }
            item.setWclNum(wclNum.intValue());
            item.setCkNum(ckNum.intValue());
            item.setTgNum(tgNum.intValue());
            item.setYjjNum(yjjNum.intValue());

        }
    }

}