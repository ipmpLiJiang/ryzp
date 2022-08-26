package cc.mrbird.febs.zp.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zp.service.IZpStaffAwardService;
import cc.mrbird.febs.zp.entity.ZpStaffAward;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2022-08-18
 */
@Slf4j
@Validated
@RestController
@RequestMapping("zpStaffAward")

public class ZpStaffAwardController extends BaseController {

    private String message;
    @Autowired
    public IZpStaffAwardService iZpStaffAwardService;


    /**
     * 分页查询数据
     *
     * @param request      分页信息
     * @param zpStaffAward 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("zpStaffAward:view")
    public Map<String, Object> List(QueryRequest request, ZpStaffAward zpStaffAward) {
        return getDataTable(this.iZpStaffAwardService.findZpStaffAwards(request, zpStaffAward));
    }

    /**
     * 添加
     *
     * @param zpStaffAward
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("zpStaffAward:add")
    public void addZpStaffAward(@Valid ZpStaffAward zpStaffAward) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
//        zpStaffAward.setCreateUserId(currentUser.getUserId());
            this.iZpStaffAwardService.createZpStaffAward(zpStaffAward);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param zpStaffAward
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("zpStaffAward:update")
    public void updateZpStaffAward(@Valid ZpStaffAward zpStaffAward) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
//      zpStaffAward.setModifyUserId(currentUser.getUserId());
            this.iZpStaffAwardService.updateZpStaffAward(zpStaffAward);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("zpStaffAward:delete")
    public void deleteZpStaffAwards(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iZpStaffAwardService.deleteZpStaffAwards(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("zpStaffAward:export")
    public void export(QueryRequest request, ZpStaffAward zpStaffAward, HttpServletResponse response) throws FebsException {
        try {
            List<ZpStaffAward> zpStaffAwards = this.iZpStaffAwardService.findZpStaffAwards(request, zpStaffAward).getRecords();
            ExcelKit.$Export(ZpStaffAward.class, response).downXlsx(zpStaffAwards, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ZpStaffAward detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ZpStaffAward zpStaffAward = this.iZpStaffAwardService.getById(id);
        return zpStaffAward;
    }
}
