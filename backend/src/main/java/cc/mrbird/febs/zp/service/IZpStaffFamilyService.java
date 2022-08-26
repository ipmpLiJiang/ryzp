package cc.mrbird.febs.zp.service;

import cc.mrbird.febs.zp.entity.ZpStaffFamily;
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
 * @since 2022-08-18
 */
public interface IZpStaffFamilyService extends IService<ZpStaffFamily> {

        IPage<ZpStaffFamily> findZpStaffFamilys(QueryRequest request, ZpStaffFamily zpStaffFamily);

        IPage<ZpStaffFamily> findZpStaffFamilyList(QueryRequest request, ZpStaffFamily zpStaffFamily);

        void createZpStaffFamily(ZpStaffFamily zpStaffFamily);

        void updateZpStaffFamily(ZpStaffFamily zpStaffFamily);

        void deleteZpStaffFamilys(String[]Ids);
        }
