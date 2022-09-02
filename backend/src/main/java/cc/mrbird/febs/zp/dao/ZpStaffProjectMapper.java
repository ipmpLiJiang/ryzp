package cc.mrbird.febs.zp.dao;

import cc.mrbird.febs.zp.entity.ZpStaffProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-08-18
 */
public interface ZpStaffProjectMapper extends BaseMapper<ZpStaffProject> {
    void updateZpStaffProject(ZpStaffProject zpStaffProject);

    IPage<ZpStaffProject> findZpStaffProject(Page page, @Param("zpStaffProject") ZpStaffProject zpStaffProject);

    List<ZpStaffProject> findProjectByPosterIdAndSs(@Param("posterId") String posterId,
                                                        @Param("state") Integer state,
                                                        @Param("idList") List<String> idList);
}
