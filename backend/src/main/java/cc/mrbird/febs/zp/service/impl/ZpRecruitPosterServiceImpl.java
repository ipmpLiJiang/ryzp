package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zp.entity.ZpRecruitPoster;
import cc.mrbird.febs.zp.dao.ZpRecruitPosterMapper;
import cc.mrbird.febs.zp.service.IZpRecruitPosterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-01-08
 */
@Slf4j
@Service("IZpRecruitPosterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpRecruitPosterServiceImpl extends ServiceImpl<ZpRecruitPosterMapper, ZpRecruitPoster> implements IZpRecruitPosterService {


    @Override
    public IPage<ZpRecruitPoster> findZpRecruitPosters(QueryRequest request, ZpRecruitPoster zpRecruitPoster) {
        try {
            LambdaQueryWrapper<ZpRecruitPoster> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(ZpRecruitPoster::getIsDeletemark, 1);//1是未删 0是已删
            String sql = "1=1";
            if (zpRecruitPoster.getIsend() != null) {
//                queryWrapper.eq(ZpRecruitPoster::getIsend, zpRecruitPoster.getIsend());
                sql += " and isend = " + zpRecruitPoster.getIsend();
            }
            if (zpRecruitPoster.getState() != null) {
//                queryWrapper.eq(ZpRecruitPoster::getState, zpRecruitPoster.getState());
                sql += " and state = " + zpRecruitPoster.getState();
            }
            if (zpRecruitPoster.getCurrencyField() != null) {
//                queryWrapper.like(ZpRecruitPoster::getCurrencyField, zpRecruitPoster.getCurrencyField());
                sql += " and (ptit like '%" + zpRecruitPoster.getCurrencyField() + "%' or pzw like '%" + zpRecruitPoster.getCurrencyField() + "%')";
            }
            queryWrapper.apply(sql);
            Page<ZpRecruitPoster> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<ZpRecruitPoster> rpage = this.page(page, queryWrapper);
//            for(ZpRecruitPoster item : rpage.getRecords()){
//                item.setPnr("");
//            }
            return rpage;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ZpRecruitPoster> findZpRecruitPosterList(QueryRequest request, ZpRecruitPoster zpRecruitPoster) {
        try {
            Page<ZpRecruitPoster> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findZpRecruitPoster(page, zpRecruitPoster);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public String createZpRecruitPoster(ZpRecruitPoster zpRecruitPoster) {
        zpRecruitPoster.setId(UUID.randomUUID().toString());
        zpRecruitPoster.setCreateTime(new Date());
        zpRecruitPoster.setIsend(0);
        zpRecruitPoster.setState(zpRecruitPoster.getState());
//        zpRecruitPoster.setIsDeletemark(1);
        boolean issave = this.save(zpRecruitPoster);
        if (issave) {
            return zpRecruitPoster.getId();
        }
        return null;
    }

    @Override
    @Transactional
    public void overdueUpdateRecruitPoster(ZpRecruitPoster zpRecruitPoster) {
        zpRecruitPoster.setModifyTime(new Date());
        ZpRecruitPoster update = new ZpRecruitPoster();
        update.setId(zpRecruitPoster.getId());
        update.setIsend(zpRecruitPoster.getIsend());
        this.baseMapper.updateZpRecruitPoster(update);
    }

    @Override
    @Transactional
    public void updateZpRecruitPoster(ZpRecruitPoster zpRecruitPoster) {
        zpRecruitPoster.setModifyTime(new Date());
        ZpRecruitPoster obj = this.getById(zpRecruitPoster.getId());
        if (obj.getState() == 1) {
            zpRecruitPoster.setState(1);
        }
        this.baseMapper.updateZpRecruitPoster(zpRecruitPoster);
    }

    @Override
    @Transactional
    public void deleteZpRecruitPosters(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }


}