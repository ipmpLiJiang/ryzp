package cc.mrbird.febs.zp.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.zp.entity.*;
import cc.mrbird.febs.zp.service.IZpStaffInfoService;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.rmi.server.ExportException;
import java.util.*;

/**
 * @author viki
 * @since 2022-01-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("zpStaffInfo")

public class ZpStaffInfoController extends BaseController {

    private String message;
    @Autowired
    IZpStaffInfoService iZpStaffInfoService;

    @GetMapping("list")
    @RequiresPermissions("zpStaffInfo:view")
    public Map<String, Object> List(QueryRequest request, ZpStaffInfo zpStaffInfo) {
        return getDataTable(this.iZpStaffInfoService.findZpStaffInfos(request, zpStaffInfo));
    }

    @GetMapping
    @RequiresPermissions("zpStaffInfo:view")
    public FebsResponse infoDetail() {
        ModelMap map = new ModelMap();
        int success = 0;
        StaffInfo staffInfo = new StaffInfo();
        try {
            User currentUser = FebsUtil.getCurrentUser();
            ZpStaffInfo zpStaffInfo = this.iZpStaffInfoService.findZpStaffInfoByUserId(currentUser.getUserId());
            staffInfo = this.iZpStaffInfoService.getStaffInfo(zpStaffInfo);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", staffInfo);
        return new FebsResponse().data(map);
    }

    @GetMapping("checkIdnumber/{idnumber}")
    public boolean checkIdnumber(@NotBlank(message = "{required}") @PathVariable String idnumber) {
        return this.iZpStaffInfoService.findByIdnumber(idnumber) == null;
    }

    @GetMapping("checkTel/{tel}")
    public boolean checkTel(@NotBlank(message = "{required}") @PathVariable String tel) {
        return this.iZpStaffInfoService.findByTel(tel) == null;
    }

    @GetMapping("staffInfoView")
    public FebsResponse staffInfoViews(String staffId) {
        ModelMap map = new ModelMap();
        int success = 0;
        StaffInfo staffInfo = new StaffInfo();
        try {
            User currentUser = FebsUtil.getCurrentUser();
            ZpStaffInfo zpStaffInfo = this.iZpStaffInfoService.findZpStaffInfoById(staffId);
            if(zpStaffInfo!=null) {
                staffInfo = this.iZpStaffInfoService.getStaffInfo(zpStaffInfo);
                success = 1;
            } else {
                message = "未查询到有效数据.";
            }
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", staffInfo);
        return new FebsResponse().data(map);
    }

    @GetMapping("getId")
    @RequiresPermissions("zpStaffInfo:update")
    public FebsResponse getIdS() {
        ModelMap map = new ModelMap();
        int success = 0;
        String id = null;
        try {
            id = UUID.randomUUID().toString();
            success = 1;
        } catch (Exception e) {
            message = "创建失败.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        map.put("data", id);
        return new FebsResponse().data(map);
    }

    @GetMapping("del")
    @RequiresPermissions("zpStaffInfo:update")
    public FebsResponse getDelId(String id, String type) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            this.iZpStaffInfoService.deleteStaffMx(id, type);
            success = 1;
        } catch (Exception e) {
            message = type + "删除失败.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @PutMapping("saveStaffInfo")
    @RequiresPermissions("zpStaffInfo:update")
    public FebsResponse saveStaffInfos(String data) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            JSONObject staffInfoJson = JSONObject.parseObject(data);
            StaffInfo staffInfo = JSON.toJavaObject(staffInfoJson, StaffInfo.class);
            this.iZpStaffInfoService.updateStaffInfo(staffInfo);
            success = 1;
        } catch (Exception e) {
            message = "保存失败.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @PutMapping("updateStaffIdTel")
    public FebsResponse updateStaffIdTels(String id, String idnumber, String tel) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            this.iZpStaffInfoService.updateStaffIdTel(id,idnumber,tel);
            success = 1;
        } catch (Exception e) {
            message = "保存失败.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @PutMapping("applyState")
    @RequiresPermissions("zpStaffInfo:apply")
    public FebsResponse staffApplyUpdateState(String applyId,Integer state) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            boolean isupdate = this.iZpStaffInfoService.applyStateUpdate(applyId,state);
            if(isupdate) {
                success = 1;
            }
        } catch (Exception e) {
            message = "更新状态成功.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @PutMapping("applyStates")
    @RequiresPermissions("zpStaffInfo:apply")
    public FebsResponse staffApplyUpdateStates(String ids,Integer state) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            boolean isupdate =this.iZpStaffInfoService.applyStateUpdates(arr_ids,state);
            if(isupdate) {
                success = 1;
            }
        } catch (Exception e) {
            message = "更新状态成功.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @GetMapping("findStaffEducation")
    @RequiresPermissions("zpStaffInfo:view")
    public FebsResponse findStaffEducation(String staffId) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<StaffEducation> educationList = new ArrayList<>();
        try {
//            User currentUser = FebsUtil.getCurrentUser();
            educationList = this.iZpStaffInfoService.getEducationList(staffId);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", educationList);
        return new FebsResponse().data(map);
    }

    @GetMapping("findStaffWork")
    @RequiresPermissions("zpStaffInfo:view")
    public FebsResponse findStaffWork(String staffId) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<StaffWork> workList = new ArrayList<>();
        try {
//            User currentUser = FebsUtil.getCurrentUser();
            workList = this.iZpStaffInfoService.getWorkList(staffId);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", workList);
        return new FebsResponse().data(map);
    }

    @GetMapping("findStaffEssay")
    @RequiresPermissions("zpStaffInfo:view")
    public FebsResponse findStaffEssay(String staffId) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<StaffEssay> essayList = new ArrayList<>();
        try {
//            User currentUser = FebsUtil.getCurrentUser();
            essayList = this.iZpStaffInfoService.getEssayList(staffId);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", essayList);
        return new FebsResponse().data(map);
    }

    @GetMapping("findStaffProject")
    @RequiresPermissions("zpStaffInfo:view")
    public FebsResponse findStaffProject(String staffId) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<StaffProject> projectList = new ArrayList<>();
        try {
//            User currentUser = FebsUtil.getCurrentUser();
            projectList = this.iZpStaffInfoService.getProjectList(staffId);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", projectList);
        return new FebsResponse().data(map);
    }

    @GetMapping("findStaffFamily")
    @RequiresPermissions("zpStaffInfo:view")
    public FebsResponse findStaffFamily(String staffId) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<StaffFamily> familyList = new ArrayList<>();
        try {
//            User currentUser = FebsUtil.getCurrentUser();
            familyList = this.iZpStaffInfoService.getFamilyList(staffId);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", familyList);
        return new FebsResponse().data(map);
    }

    @GetMapping("findStaffAward")
    @RequiresPermissions("zpStaffInfo:view")
    public FebsResponse findStaffAward(String staffId) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<StaffAward> awardList = new ArrayList<>();
        try {
//            User currentUser = FebsUtil.getCurrentUser();
            awardList = this.iZpStaffInfoService.getAwardList(staffId);
            success = 1;
        } catch (Exception e) {
            message = "查询失败.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", awardList);
        return new FebsResponse().data(map);
    }

    @Log("新增/修改")
    @PostMapping("editZpStaffFamily")
    public void editZpStaffFamily(@Valid StaffFamily staffFamily) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();

            this.iZpStaffInfoService.editZpStaffFamily(staffFamily,currentUser);
        } catch (Exception e) {
            message = "修改数据失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增/修改")
    @PostMapping("editZpStaffEducation")
    public void editZpStaffEducation(@Valid StaffEducation staffEducation) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();

            this.iZpStaffInfoService.editZpStaffEducation(staffEducation,currentUser);
        } catch (Exception e) {
            message = "修改数据失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增/修改")
    @PostMapping("editZpStaffWork")
    public void editZpStaffWork(@Valid StaffWork staffWork) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();

            this.iZpStaffInfoService.editZpStaffWork(staffWork,currentUser);
        } catch (Exception e) {
            message = "修改数据失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增/修改")
    @PostMapping("editZpStaffProject")
    public void editZpStaffProject(@Valid StaffProject staffProject) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();

            this.iZpStaffInfoService.editZpStaffProject(staffProject,currentUser);
        } catch (Exception e) {
            message = "修改数据失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增/修改")
    @PostMapping("editZpStaffEssay")
    public void editZpStaffEssay(@Valid StaffEssay staffEssay) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();

            this.iZpStaffInfoService.editZpStaffEssay(staffEssay,currentUser);
        } catch (Exception e) {
            message = "修改数据失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增/修改")
    @PostMapping("editZpStaffAward")
    public void editZpStaffAward(@Valid StaffAward staffAward) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();

            this.iZpStaffInfoService.editZpStaffAward(staffAward,currentUser);
        } catch (Exception e) {
            message = "修改数据失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 添加
     *
     * @param zpStaffInfo
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("zpStaffInfo:add")
    public void addZpStaffInfo(@Valid ZpStaffInfo zpStaffInfo) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            zpStaffInfo.setUserid(currentUser.getUserId());
            this.iZpStaffInfoService.createZpStaffInfo(zpStaffInfo);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param zpStaffInfo
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("zpStaffInfo:update")
    public void updateZpStaffInfo(@Valid ZpStaffInfo zpStaffInfo) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
//      zpStaffInfo.setModifyUserId(currentUser.getUserId());
            this.iZpStaffInfoService.updateZpStaffInfo(zpStaffInfo);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("zpStaffInfo:delete")
    public void deleteZpStaffInfos(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iZpStaffInfoService.deleteZpStaffInfos(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("zpStaffInfo:export")
    public void export(QueryRequest request, ZpStaffInfo zpStaffInfo, HttpServletResponse response) throws FebsException {
        try {
            List<ZpStaffInfo> zpStaffInfos = this.iZpStaffInfoService.findZpStaffInfos(request, zpStaffInfo).getRecords();
            ExcelKit.$Export(ZpStaffInfo.class, response).downXlsx(zpStaffInfos, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ZpStaffInfo detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ZpStaffInfo zpStaffInfo = this.iZpStaffInfoService.getById(id);
        return zpStaffInfo;
    }
}
