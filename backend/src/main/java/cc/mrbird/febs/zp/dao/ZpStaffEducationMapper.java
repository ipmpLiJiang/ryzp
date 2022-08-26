package cc.mrbird.febs.zp.dao;

import cc.mrbird.febs.zp.entity.ZpStaffEducation;
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
 * @since 2022-01-03
 */
public interface ZpStaffEducationMapper extends BaseMapper<ZpStaffEducation> {
    void updateZpStaffEducation(ZpStaffEducation zpStaffEducation);

    IPage<ZpStaffEducation> findZpStaffEducation(Page page, @Param("zpStaffEducation") ZpStaffEducation zpStaffEducation);

        List<ZpStaffEducation> findEducationByPosterIdAndSs(@Param("posterId") String posterId,
                                                                 @Param("state") Integer state,
                                                                 @Param("idList") List<String> idList);
}
