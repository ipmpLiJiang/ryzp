package cc.mrbird.febs.zp.service;

import cc.mrbird.febs.zp.entity.ZpStaffAward;
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
 * @since 2022-08-18
 */
public interface IZpStaffAwardService extends IService<ZpStaffAward> {

        IPage<ZpStaffAward> findZpStaffAwards(QueryRequest request, ZpStaffAward zpStaffAward);

        IPage<ZpStaffAward> findZpStaffAwardList(QueryRequest request, ZpStaffAward zpStaffAward);

        ZpStaffAward saveInitStaffAward(ZpStaffInfo zpStaffInfo, Date thisDate);

        void createZpStaffAward(ZpStaffAward zpStaffAward);

        void updateZpStaffAward(ZpStaffAward zpStaffAward);

        void deleteZpStaffAwards(String[]Ids);

        List<ZpStaffAward> findAwardByPosterId(String posterId, Integer state, List<String> idList);
        }
