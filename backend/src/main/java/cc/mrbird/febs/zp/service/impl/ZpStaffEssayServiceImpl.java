package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zp.entity.ZpStaffEssay;
import cc.mrbird.febs.zp.dao.ZpStaffEssayMapper;
import cc.mrbird.febs.zp.entity.ZpStaffInfo;
import cc.mrbird.febs.zp.service.IZpStaffEssayService;
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
 * @since 2022-01-03
 */
@Slf4j
@Service("IZpStaffEssayService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpStaffEssayServiceImpl extends ServiceImpl<ZpStaffEssayMapper, ZpStaffEssay> implements IZpStaffEssayService {


    @Override
    public IPage<ZpStaffEssay> findZpStaffEssays(QueryRequest request, ZpStaffEssay zpStaffEssay) {
        try {
            LambdaQueryWrapper<ZpStaffEssay> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(ZpStaffEssay::getIsDeletemark, 1);//1是未删 0是已删


            Page<ZpStaffEssay> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ZpStaffEssay> findZpStaffEssayList(QueryRequest request, ZpStaffEssay zpStaffEssay) {
        try {
            Page<ZpStaffEssay> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findZpStaffEssay(page, zpStaffEssay);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createZpStaffEssay(ZpStaffEssay zpStaffEssay) {
        zpStaffEssay.setId(UUID.randomUUID().toString());
//        zpStaffEssay.setCreateTime(new Date());
//        zpStaffEssay.setIsDeletemark(1);
        this.save(zpStaffEssay);
    }

    @Override
    @Transactional
    public void updateZpStaffEssay(ZpStaffEssay zpStaffEssay) {
//        zpStaffEssay.setModifyTime(new Date());
        this.baseMapper.updateZpStaffEssay(zpStaffEssay);
    }

    @Override
    @Transactional
    public void deleteZpStaffEssays(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public ZpStaffEssay saveInitStaffEssay(ZpStaffInfo zpStaffInfo, Date thisDate) {
        ZpStaffEssay essay = new ZpStaffEssay();
        essay.setId(UUID.randomUUID().toString());
        essay.setUserid(zpStaffInfo.getUserid());
        essay.setStaffId(zpStaffInfo.getId());
        essay.setCreateTime(thisDate);
        this.save(essay);
        return essay;
    }

    @Override
    public List<ZpStaffEssay> findEssayByPosterId(String posterId, Integer state, List<String> idList) {
        return this.baseMapper.findEssayByPosterIdAndSs(posterId, state, idList);
    }

}