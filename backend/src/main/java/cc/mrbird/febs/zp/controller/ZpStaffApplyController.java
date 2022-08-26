package cc.mrbird.febs.zp.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zp.service.IZpStaffApplyService;
import cc.mrbird.febs.zp.entity.ZpStaffApply;

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
import java.util.List;
import java.util.Map;

/**
 * @author viki
 * @since 2022-01-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("zpStaffApply")

public class ZpStaffApplyController extends BaseController {

    private String message;
    @Autowired
    public IZpStaffApplyService iZpStaffApplyService;


    /**
     * 分页查询数据
     *
     * @param request      分页信息
     * @param zpStaffApply 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("zpStaffApply:view")
    public Map<String, Object> List(QueryRequest request, ZpStaffApply zpStaffApply) {
        return getDataTable(this.iZpStaffApplyService.findZpStaffApplys(request, zpStaffApply));
    }

    /**
     * 添加
     *
     * @param zpStaffApply
     * @return
     */
//    @Log("新增/按钮")
//    @PostMapping
//    @RequiresPermissions("zpStaffApply:add")
//    public void addZpStaffApply(@Valid ZpStaffApply zpStaffApply) throws FebsException {
//        try {
//            User currentUser = FebsUtil.getCurrentUser();
//            zpStaffApply.setUserid(currentUser.getUserId());
//            this.iZpStaffApplyService.createZpStaffApply(zpStaffApply);
//        } catch (Exception e) {
//            message = "新增/按钮失败";
//            log.error(message, e);
//            throw new FebsException(message);
//        }
//    }

    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("zpStaffApply:add")
    public FebsResponse addZpStaffApply(@Valid ZpStaffApply zpStaffApply) throws FebsException {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            User currentUser = FebsUtil.getCurrentUser();
            zpStaffApply.setUserid(currentUser.getUserId());
            message = this.iZpStaffApplyService.staffApplyPoster(zpStaffApply);
            if(message.equals("ok")) {
                success = 1;
            } else{
                success = 0;
            }
        } catch (Exception e) {
            message = "招聘信息申请失败.";
            log.error(message, e);
            throw new FebsException(message);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    /**
     * 修改
     *
     * @param zpStaffApply
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("zpStaffApply:update")
    public void updateZpStaffApply(@Valid ZpStaffApply zpStaffApply) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
//      zpStaffApply.setModifyUserId(currentUser.getUserId());
            this.iZpStaffApplyService.updateZpStaffApply(zpStaffApply);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("zpStaffApply:delete")
    public void deleteZpStaffApplys(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iZpStaffApplyService.deleteZpStaffApplys(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("zpStaffApply:export")
    public void export(QueryRequest request, ZpStaffApply zpStaffApply, HttpServletResponse response) throws FebsException {
        try {
            List<ZpStaffApply> zpStaffApplys = this.iZpStaffApplyService.findZpStaffApplys(request, zpStaffApply).getRecords();
            ExcelKit.$Export(ZpStaffApply.class, response).downXlsx(zpStaffApplys, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ZpStaffApply detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ZpStaffApply zpStaffApply = this.iZpStaffApplyService.getById(id);
        return zpStaffApply;
    }
}