package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.com.controller.DataTypeHelpers;
import cc.mrbird.febs.export.excel.ExportExcelUtils;
import cc.mrbird.febs.com.controller.ImportExcelUtils;
import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.properties.FebsProperties;
import cc.mrbird.febs.common.utils.MD5Util;
import cc.mrbird.febs.system.domain.*;
import cc.mrbird.febs.system.service.RoleService;
import cc.mrbird.febs.system.service.UserConfigService;
import cc.mrbird.febs.system.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    private String message;

    @Autowired
    private UserService userService;
    @Autowired
    private UserConfigService userConfigService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private FebsProperties febsProperties;

    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userService.findByName(username) == null;
    }

    @GetMapping("/{username}")
    public User detail(@NotBlank(message = "{required}") @PathVariable String username) {
        User user = this.userService.findByName(username);
        //??????????????????????????????????????????????????????roleId????????????
        List<Role> roles = roleService.findUserRole(username);
        List<Long> roleIds = roles.stream().map(role -> role.getRoleId()).collect(Collectors.toList());
        String roleIdStr = StringUtils.join(roleIds.toArray(new Long[roleIds.size()]), ",");
        user.setRoleId(roleIdStr);
        return user;
    }

    @GetMapping
    @RequiresPermissions("user:view")
    public Map<String, Object> userList(QueryRequest queryRequest, User user) {
        return getDataTable(userService.findUserDetail(user, queryRequest));
    }

    @Log("????????????")
    @PostMapping
    @RequiresPermissions("user:add")
    public void addUser(@Valid User user) throws FebsException {
        try {
            this.userService.createUser(user);
        } catch (Exception e) {
            message = "??????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("findUserList")
    public FebsResponse findUserLists(User user) {
        List<User> list = new ArrayList<>();
        try {
            list = this.userService.findUserList(user,1);

        } catch (Exception e) {
            log.error("??????????????????", e);
        }
        return new FebsResponse().data(list);
    }

    @PostMapping("importUser")
    @RequiresPermissions("user:import")
    public FebsResponse importUsers(@RequestParam MultipartFile file) {
        int success = 0;
        ModelMap map = new ModelMap();
        if (file.isEmpty()) {
            message = "?????????";
        } else {
            try {
                File getFile = DataTypeHelpers.multipartFileToFile(file);
                List<Object[]> obj = ImportExcelUtils.importExcelBySheetIndex(getFile, 0, 0, 0);
                List<UserRolesImport> list = new ArrayList<>();
                List<String> strRoleList = new ArrayList<>();
                List<String> strDeptList = new ArrayList<>();
                String strError = "";
                if (obj.size() > 1) {
                    for (int i = 1; i < obj.size(); i++) {
                        UserRolesImport userRole = new UserRolesImport();
                        String userName = DataTypeHelpers.importTernaryOperate(obj.get(i), 0);
                        String name = DataTypeHelpers.importTernaryOperate(obj.get(i), 1);
                        String password = DataTypeHelpers.importTernaryOperate(obj.get(i), 2);
                        String sex = DataTypeHelpers.importTernaryOperate(obj.get(i), 3);
                        String email = DataTypeHelpers.importTernaryOperate(obj.get(i), 4);
                        String tel = DataTypeHelpers.importTernaryOperate(obj.get(i), 5);
                        String deptName = DataTypeHelpers.importTernaryOperate(obj.get(i), 6);
                        String roleName = DataTypeHelpers.importTernaryOperate(obj.get(i), 7);

                        userRole.setUserName(userName);
                        userRole.setXmname(name);
                        userRole.setPassword(password);
                        userRole.setSex(sex);
                        userRole.setEmail(email);
                        userRole.setTel(tel);
                        userRole.setDeptName(deptName);
                        userRole.setRoleName(roleName);
                        if (!DataTypeHelpers.isNullOrEmpty(roleName)) {
                            if (!strRoleList.stream().anyMatch(task -> task.equals(roleName))) {
                                strRoleList.add(roleName);
                            }
                        } else {
                            strError = "??????????????????";
                            break;
                        }
                        if (!DataTypeHelpers.isNullOrEmpty(deptName)) {
                            if (!strDeptList.stream().anyMatch(task -> task.equals(deptName))) {
                                strDeptList.add(deptName);
                            }
                        } else {
                            strError = "??????????????????";
                            break;
                        }
                        list.add(userRole);
                    }
                }
                if (strError.equals("")) {
                    String msg = this.userService.importUserRoles(list, strRoleList, strDeptList);
                    if (msg.equals("roleError")) {
                        message = "???????????????";
                    } else if (msg.equals("deptError")) {
                        message = "???????????????";
                    } else {
                        message = "??????????????????";
                        success = 1;
                    }
                } else {
                    message = strError;
                }

            } catch (Exception e) {
                message = "??????????????????";
                log.error(message, e);
            }
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);

    }

    @PostMapping("exportUser")
    @RequiresPermissions("user:import")
    public void export1(QueryRequest request, HttpServletResponse response) throws FebsException {
        try {
            String filePath = febsProperties.getUploadPath(); // ??????????????????
            ExportExcelUtils.exportExcel(response, UserRolesImport.class, null, "????????????");
        } catch (Exception e) {
            message = "??????Excel??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("????????????")
    @PutMapping
    @RequiresPermissions("user:update")
    public void updateUser(@Valid User user) throws FebsException {
        try {
            this.userService.updateUser(user);
        } catch (Exception e) {
            message = "??????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("????????????")
    @DeleteMapping("/{userIds}")
    @RequiresPermissions("user:delete")
    public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws FebsException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.deleteUsers(ids);
        } catch (Exception e) {
            message = "??????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("??????????????????")
    @DeleteMapping("/deleteUserStaff/{userId}")
    @RequiresPermissions("user:userStaff")
    public void deleteUserStaffs(@NotBlank(message = "{required}") @PathVariable String userId) throws FebsException {
        try {
            this.userService.deleteUserStaffs(userId);
        } catch (Exception e) {
            message = "??????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping("profile")
    public void updateProfile(@Valid User user) throws FebsException {
        try {
            this.userService.updateProfile(user);
        } catch (Exception e) {
            message = "????????????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping("avatar")
    public void updateAvatar(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String avatar) throws FebsException {
        try {
            this.userService.updateAvatar(username, avatar);
        } catch (Exception e) {
            message = "??????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping("userconfig")
    public void updateUserConfig(@Valid UserConfig userConfig) throws FebsException {
        try {
            this.userConfigService.update(userConfig);
        } catch (Exception e) {
            message = "???????????????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("password/check")
    public boolean checkPassword(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) {
        String encryptPassword = MD5Util.encrypt(username, password);
        User user = userService.findByName(username);
        if (user != null)
            return StringUtils.equals(user.getPassword(), encryptPassword);
        else
            return false;
    }

    @PutMapping("password")
    public void updatePassword(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) throws FebsException {
        try {
            userService.updatePassword(username, password);
        } catch (Exception e) {
            message = "??????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping("password/reset")
    @RequiresPermissions("user:reset")
    public void resetPassword(@NotBlank(message = "{required}") String usernames,String pwd) throws FebsException {
        try {
            String[] usernameArr = usernames.split(StringPool.COMMA);
            this.userService.resetPassword(usernameArr,pwd);
        } catch (Exception e) {
            message = "????????????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping("userPassword/reset")
    @RequiresPermissions("user:userStaff")
    public void resetUserPassword(@NotBlank(message = "{required}") String username,String pwd) throws FebsException {
        try {
            String[] usernameArr = new String[] { username };
            this.userService.resetPassword(usernameArr,pwd);
        } catch (Exception e) {
            message = "????????????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping("status/reset")
    @RequiresPermissions("user:userStaff")
    public void updateStatus(@NotBlank(message = "{required}") String username, String status) throws FebsException {
        try {
            this.userService.updateStatus(username,status);
        } catch (Exception e) {
            message = "????????????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("user:export")
    public void export(QueryRequest queryRequest, User user, HttpServletResponse response) throws FebsException {
        try {
            List<User> users = this.userService.findUserDetail(user, queryRequest).getRecords();
            ExcelKit.$Export(User.class, response).downXlsx(users, false);
        } catch (Exception e) {
            message = "??????Excel??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
