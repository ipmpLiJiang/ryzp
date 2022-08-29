package cc.mrbird.febs.zp.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zp.entity.ZpStaffInfo;
import cc.mrbird.febs.zp.entity.ZpStaffProject;
import cc.mrbird.febs.zp.dao.ZpStaffProjectMapper;
import cc.mrbird.febs.zp.service.IZpStaffProjectService;
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
 * @since 2022-08-18
 */
@Slf4j
@Service("IZpStaffProjectService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZpStaffProjectServiceImpl extends ServiceImpl<ZpStaffProjectMapper, ZpStaffProject> implements IZpStaffProjectService {


    @Override
    public IPage<ZpStaffProject> findZpStaffProjects(QueryRequest request, ZpStaffProject zpStaffProject) {
        try {
            LambdaQueryWrapper<ZpStaffProject> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(ZpStaffProject::getIsDeletemark, 1);//1是未删 0是已删


            Page<ZpStaffProject> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ZpStaffProject> findZpStaffProjectList(QueryRequest request, ZpStaffProject zpStaffProject) {
        try {
            Page<ZpStaffProject> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findZpStaffProject(page, zpStaffProject);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createZpStaffProject(ZpStaffProject zpStaffProject) {
        zpStaffProject.setId(UUID.randomUUID().toString());
        zpStaffProject.setCreateTime(new Date());
//        zpStaffProject.setIsDeletemark(1);
        this.save(zpStaffProject);
    }

    @Override
    @Transactional
    public ZpStaffProject saveInitStaffProject(ZpStaffInfo zpStaffInfo, Date thisDate) {
        ZpStaffProject project = new ZpStaffProject();
        project.setId(UUID.randomUUID().toString());
        project.setUserid(zpStaffInfo.getUserid());
        project.setStaffId(zpStaffInfo.getId());
        project.setCreateTime(thisDate);
        this.save(project);
        return project;
    }

    @Override
    @Transactional
    public void updateZpStaffProject(ZpStaffProject zpStaffProject) {
        zpStaffProject.setModifyTime(new Date());
        this.baseMapper.updateZpStaffProject(zpStaffProject);
    }

    @Override
    @Transactional
    public void deleteZpStaffProjects(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }


}
