package cc.mrbird.febs.zp.controller;

import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.zp.entity.ZpStaffApplyPosterView;
import cc.mrbird.febs.zp.entity.ZpStaffInfo;
import cc.mrbird.febs.zp.service.IZpStaffApplyPosterViewService;
import cc.mrbird.febs.zp.service.IZpStaffInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author viki
 * @since 2022-01-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("zpStaffApplyPosterView")

public class ZpStaffApplyPosterViewController extends BaseController {

    private String message;
    @Autowired
    public IZpStaffApplyPosterViewService iZpStaffApplyPosterViewService;

    /**
     * 分页查询数据
     *
     * @param request                分页信息
     * @return
     */
    @GetMapping
    @RequiresPermissions("zpStaffApplyPosterView:view")
    public Map<String, Object> List(QueryRequest request,String currencyField) {
        User currentUser = FebsUtil.getCurrentUser();
        return getDataTable(this.iZpStaffApplyPosterViewService.findStaffApplyPosterViews(request,currencyField, currentUser));
    }

    @GetMapping("myApplyPosterList")
    @RequiresPermissions("zpStaffApplyPosterView:view")
    public Map<String, Object> List1(QueryRequest request,String currencyField) {
        User currentUser = FebsUtil.getCurrentUser();
        return getDataTable(this.iZpStaffApplyPosterViewService.findMyStaffApplyPosterViews(request,currencyField, currentUser));
    }

}