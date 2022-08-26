package cc.mrbird.febs.zp.service;

import cc.mrbird.febs.zp.entity.ZpStaffEssay;
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
public interface IZpStaffEssayService extends IService<ZpStaffEssay> {

        IPage<ZpStaffEssay> findZpStaffEssays(QueryRequest request, ZpStaffEssay zpStaffEssay);

        IPage<ZpStaffEssay> findZpStaffEssayList(QueryRequest request, ZpStaffEssay zpStaffEssay);

        void createZpStaffEssay(ZpStaffEssay zpStaffEssay);

        void updateZpStaffEssay(ZpStaffEssay zpStaffEssay);

        void deleteZpStaffEssays(String[]Ids);

        ZpStaffEssay saveInitStaffEssay(ZpStaffInfo zpStaffInfo, Date thisDate);

        List<ZpStaffEssay> findEssayByPosterId(String posterId, Integer state, List<String> idList);
        }
