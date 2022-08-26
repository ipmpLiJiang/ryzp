package cc.mrbird.febs.zp.service;

import cc.mrbird.febs.zp.entity.ZpStaffEducation;
import cc.mrbird.febs.zp.entity.ZpStaffInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2022-01-03
 */
public interface IZpStaffEducationService extends IService<ZpStaffEducation> {

        IPage<ZpStaffEducation> findZpStaffEducations(QueryRequest request, ZpStaffEducation zpStaffEducation);

        IPage<ZpStaffEducation> findZpStaffEducationList(QueryRequest request, ZpStaffEducation zpStaffEducation);

        void createZpStaffEducation(ZpStaffEducation zpStaffEducation);

        void updateZpStaffEducation(ZpStaffEducation zpStaffEducation);

        void deleteZpStaffEducations(String[]Ids);

        ZpStaffEducation saveInitStaffEducation(ZpStaffInfo zpStaffInfo, Date thisDate);

        List<ZpStaffEducation> findEducationByPosterId(String posterId, Integer state, List<String> idList);
        }
