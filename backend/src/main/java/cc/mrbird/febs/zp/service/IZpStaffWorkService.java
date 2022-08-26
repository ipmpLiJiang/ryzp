package cc.mrbird.febs.zp.service;

import cc.mrbird.febs.zp.entity.ZpStaffInfo;
import cc.mrbird.febs.zp.entity.ZpStaffWork;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author viki
 * @since 2022-01-03
 */
public interface IZpStaffWorkService extends IService<ZpStaffWork> {

    IPage<ZpStaffWork> findZpStaffWorks(QueryRequest request, ZpStaffWork zpStaffWork);

    IPage<ZpStaffWork> findZpStaffWorkList(QueryRequest request, ZpStaffWork zpStaffWork);

    void createZpStaffWork(ZpStaffWork zpStaffWork);

    void updateZpStaffWork(ZpStaffWork zpStaffWork);

    void deleteZpStaffWorks(String[] Ids);

    ZpStaffWork saveInitStaffWork(ZpStaffInfo zpStaffInfo, Date thisDate);

    List<ZpStaffWork> findWorkByPosterId(String posterId, Integer state, List<String> idList);
}
