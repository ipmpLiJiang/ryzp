package cc.mrbird.febs.zp.controller;

import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.zp.entity.ZpStaffManageView;
import cc.mrbird.febs.zp.service.IZpStaffManageViewService;
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
@RequestMapping("zpStaffManageView")

public class ZpStaffManageViewController extends BaseController {

    private String message;

    @Autowired
    IZpStaffManageViewService iZpStaffManageViewService;

    /**
     * 分页查询数据
     *
     * @param request 分页信息
     * @return
     */
    @GetMapping
    @RequiresPermissions("zpStaffManageView:view")
    public Map<String, Object> List(QueryRequest request, ZpStaffManageView zpStaffManageView) {
        User currentUser = FebsUtil.getCurrentUser();
        return getDataTable(this.iZpStaffManageViewService.findZpPosterStaffViews(request, zpStaffManageView));
    }

}
