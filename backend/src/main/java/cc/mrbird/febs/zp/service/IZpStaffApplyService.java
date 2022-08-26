package cc.mrbird.febs.zp.service;

import cc.mrbird.febs.zp.entity.ZpStaffApply;
import com.baomidou.mybatisplus.extension.service.IService;
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
 * @since 2022-01-08
 */
public interface IZpStaffApplyService extends IService<ZpStaffApply> {

        IPage<ZpStaffApply> findZpStaffApplys(QueryRequest request, ZpStaffApply zpStaffApply);

        IPage<ZpStaffApply> findZpStaffApplyList(QueryRequest request, ZpStaffApply zpStaffApply);

        void createZpStaffApply(ZpStaffApply zpStaffApply);

        void updateZpStaffApply(ZpStaffApply zpStaffApply);

        void deleteZpStaffApplys(String[]Ids);

        String staffApplyPoster(ZpStaffApply zpStaffApply);
        }
