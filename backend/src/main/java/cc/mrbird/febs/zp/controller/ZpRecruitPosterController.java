package cc.mrbird.febs.zp.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zp.service.IZpRecruitPosterService;
import cc.mrbird.febs.zp.entity.ZpRecruitPoster;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
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
import java.util.ArrayList;
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
@RequestMapping("zpRecruitPoster")

public class ZpRecruitPosterController extends BaseController {

    private String message;
    @Autowired
    public IZpRecruitPosterService iZpRecruitPosterService;


    /**
     * 分页查询数据
     *
     * @param request         分页信息
     * @param zpRecruitPoster 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("zpRecruitPoster:view")
    public Map<String, Object> List(QueryRequest request, ZpRecruitPoster zpRecruitPoster) {
        return getDataTable(this.iZpRecruitPosterService.findZpRecruitPosters(request, zpRecruitPoster));
    }

    @GetMapping("recruitPosterView")
    public Map<String, Object> List1(QueryRequest request, ZpRecruitPoster zpRecruitPoster) {
        return getDataTable(this.iZpRecruitPosterService.findZpRecruitPosterList(request, zpRecruitPoster));
    }

    /**
     * 添加
     *
     * @param zpRecruitPoster
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("zpRecruitPoster:add")
    public FebsResponse addZpRecruitPoster(@Valid ZpRecruitPoster zpRecruitPoster) throws FebsException {
        ModelMap map = new ModelMap();
        int success = 0;
        String id = null;
        try {
            User currentUser = FebsUtil.getCurrentUser();
//        zpRecruitPoster.setCreateUserId(currentUser.getUserId());
            zpRecruitPoster.setUserid(currentUser.getUserId());
            zpRecruitPoster.setCrtdat(new Date());
            id = this.iZpRecruitPosterService.createZpRecruitPoster(zpRecruitPoster);
            if(id != null) {
                success = 1;
            } else {
                message = "新增/按钮失败";
            }
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
        map.put("success", success);
        map.put("message", message);
        map.put("data", id);
        return new FebsResponse().data(map);
    }

    /**
     * 修改
     *
     * @param zpRecruitPoster
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("zpRecruitPoster:update")
    public FebsResponse updateZpRecruitPoster(@Valid ZpRecruitPoster zpRecruitPoster) throws FebsException {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            User currentUser = FebsUtil.getCurrentUser();
//      zpRecruitPoster.setModifyUserId(currentUser.getUserId());
            this.iZpRecruitPosterService.updateZpRecruitPoster(zpRecruitPoster);
            success = 1;
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @Log("过期")
    @PostMapping("updateState")
    @RequiresPermissions("zpRecruitPoster:update")
    public FebsResponse overdueUpdateRecruitPoster(@Valid ZpRecruitPoster recruitPoster) throws FebsException {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
//            User currentUser = FebsUtil.getCurrentUser();
            this.iZpRecruitPosterService.overdueUpdateRecruitPoster(recruitPoster);
            success = 1;
        } catch (Exception e) {
            message = "更改过期失败.";
            log.error(message, e);
            throw new FebsException(message);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("zpRecruitPoster:delete")
    public void deleteZpRecruitPosters(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iZpRecruitPosterService.deleteZpRecruitPosters(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("zpRecruitPoster:export")
    public void export(QueryRequest request, ZpRecruitPoster zpRecruitPoster, HttpServletResponse response) throws FebsException {
        try {
            List<ZpRecruitPoster> zpRecruitPosters = this.iZpRecruitPosterService.findZpRecruitPosters(request, zpRecruitPoster).getRecords();
            ExcelKit.$Export(ZpRecruitPoster.class, response).downXlsx(zpRecruitPosters, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ZpRecruitPoster detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ZpRecruitPoster zpRecruitPoster = this.iZpRecruitPosterService.getById(id);
        return zpRecruitPoster;
    }
}