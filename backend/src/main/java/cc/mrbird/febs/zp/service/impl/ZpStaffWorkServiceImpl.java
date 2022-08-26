package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zp.entity.ZpStaffInfo;
import cc.mrbird.febs.zp.entity.ZpStaffWork;
import cc.mrbird.febs.zp.dao.ZpStaffWorkMapper;
import cc.mrbird.febs.zp.service.IZpStaffWorkService;
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
@Service("IZpStaffWorkService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpStaffWorkServiceImpl extends ServiceImpl<ZpStaffWorkMapper, ZpStaffWork> implements IZpStaffWorkService {


    @Override
    public IPage<ZpStaffWork> findZpStaffWorks(QueryRequest request, ZpStaffWork zpStaffWork) {
        try {
            LambdaQueryWrapper<ZpStaffWork> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(ZpStaffWork::getIsDeletemark, 1);//1是未删 0是已删


            Page<ZpStaffWork> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ZpStaffWork> findZpStaffWorkList(QueryRequest request, ZpStaffWork zpStaffWork) {
        try {
            Page<ZpStaffWork> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findZpStaffWork(page, zpStaffWork);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createZpStaffWork(ZpStaffWork zpStaffWork) {
        zpStaffWork.setId(UUID.randomUUID().toString());
//        zpStaffWork.setCreateTime(new Date());
//        zpStaffWork.setIsDeletemark(1);
        this.save(zpStaffWork);
    }

    @Override
    @Transactional
    public void updateZpStaffWork(ZpStaffWork zpStaffWork) {
//        zpStaffWork.setModifyTime(new Date());
        this.baseMapper.updateZpStaffWork(zpStaffWork);
    }

    @Override
    @Transactional
    public void deleteZpStaffWorks(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public ZpStaffWork saveInitStaffWork(ZpStaffInfo zpStaffInfo, Date thisDate) {
        ZpStaffWork work = new ZpStaffWork();
        work.setId(UUID.randomUUID().toString());
        work.setUserid(zpStaffInfo.getUserid());
        work.setStaffId(zpStaffInfo.getId());
        work.setCreateTime(thisDate);
        this.save(work);
        return work;
    }

    @Override
    public List<ZpStaffWork> findWorkByPosterId(String posterId, Integer state, List<String> idList) {
        return this.baseMapper.findWorkByPosterIdAndSs(posterId, state, idList);
    }

}