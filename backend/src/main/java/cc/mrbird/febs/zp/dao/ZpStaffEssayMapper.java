package cc.mrbird.febs.zp.dao;

import cc.mrbird.febs.zp.entity.ZpStaffEssay;
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
public interface ZpStaffEssayMapper extends BaseMapper<ZpStaffEssay> {
    void updateZpStaffEssay(ZpStaffEssay zpStaffEssay);

    IPage<ZpStaffEssay> findZpStaffEssay(Page page, @Param("zpStaffEssay") ZpStaffEssay zpStaffEssay);

    List<ZpStaffEssay> findEssayByPosterIdAndSs(@Param("posterId") String posterId,
                                                     @Param("state") Integer state,
                                                     @Param("idList") List<String> idList);
}
