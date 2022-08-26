package cc.mrbird.febs.zp.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.zp.entity.ZpRecruitPosterView;
import cc.mrbird.febs.zp.service.IZpRecruitPosterViewService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author viki
 * @since 2022-01-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("zpRecruitPosterView")

public class ZpRecruitPosterViewController extends BaseController {

    private String message;
    @Autowired
    public IZpRecruitPosterViewService iZpRecruitPosterViewService;


    /**
     * 分页查询数据
     *
     * @param request             分页信息
     * @param zpRecruitPosterView 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("zpRecruitPosterView:view")
    public Map<String, Object> List(QueryRequest request, ZpRecruitPosterView zpRecruitPosterView) {
        return getDataTable(this.iZpRecruitPosterViewService.findZpRecruitPosterViews(request, zpRecruitPosterView));
    }

    @GetMapping("recruitPosterOverdueView")
    @RequiresPermissions("zpRecruitPosterView:view")
    public Map<String, Object> List1(QueryRequest request, ZpRecruitPosterView zpRecruitPosterView) {
        return getDataTable(this.iZpRecruitPosterViewService.findZpRecruitPosterOverdueViews(request, zpRecruitPosterView));
    }

}