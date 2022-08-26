package cc.mrbird.febs.zp.service;

import cc.mrbird.febs.zp.entity.ZpRecruitPoster;
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
public interface IZpRecruitPosterService extends IService<ZpRecruitPoster> {

        IPage<ZpRecruitPoster> findZpRecruitPosters(QueryRequest request, ZpRecruitPoster zpRecruitPoster);

        IPage<ZpRecruitPoster> findZpRecruitPosterList(QueryRequest request, ZpRecruitPoster zpRecruitPoster);

        String createZpRecruitPoster(ZpRecruitPoster zpRecruitPoster);

        void updateZpRecruitPoster(ZpRecruitPoster zpRecruitPoster);

        void deleteZpRecruitPosters(String[]Ids);

        void overdueUpdateRecruitPoster(ZpRecruitPoster zpRecruitPoster);
        }
