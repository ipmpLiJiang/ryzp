package cc.mrbird.febs.zp.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.zp.entity.QuertTab;
import cc.mrbird.febs.zp.entity.StaffInfoDataExport;
import cc.mrbird.febs.zp.entity.ZpPosterStaffView;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author viki
 * @since 2022-01-08
 */
public interface IZpPosterStaffViewService extends IService<ZpPosterStaffView> {

    IPage<ZpPosterStaffView> findZpPosterStaffViews(QueryRequest request, ZpPosterStaffView zpPosterStaffView,List<QuertTab> quertTabList);

    IPage<ZpPosterStaffView> findZpPosterStaffLists(QueryRequest request, ZpPosterStaffView zpPosterStaffView,List<QuertTab> quertTabList);

    List<StaffInfoDataExport> excelData(String posterId, Integer state, List<String> idList);

    void SubSmsData(String posterId, Integer applyState, String sendContent, List<String> idList);
}
