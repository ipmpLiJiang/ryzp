package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.com.entity.ComSms;
import cc.mrbird.febs.com.service.IComSmsService;
import cc.mrbird.febs.common.annotation.Limit;
import cc.mrbird.febs.common.authentication.JWTToken;
import cc.mrbird.febs.common.authentication.JWTUtil;
import cc.mrbird.febs.common.domain.ActiveUser;
import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.properties.FebsProperties;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.common.utils.*;
import cc.mrbird.febs.system.dao.LoginLogMapper;
import cc.mrbird.febs.system.domain.LoginLog;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.system.domain.UserConfig;
import cc.mrbird.febs.system.manager.UserManager;
import cc.mrbird.febs.system.service.LoginLogService;
import cc.mrbird.febs.system.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.*;

@Validated
@RestController
public class LoginController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserManager userManager;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Autowired
    private FebsProperties properties;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    IComSmsService iComSmsService;

    @PostMapping("/login")
    @Limit(key = "login", period = 60, count = 20, name = "????????????", prefix = "limit")
    public FebsResponse login(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password, HttpServletRequest request) throws Exception {
        username = StringUtils.lowerCase(username);
        password = MD5Util.encrypt(username, password);

        final String errorMessage = "????????????????????????";
        User user = this.userManager.getUser(username);

        if (user == null)
            throw new FebsException(errorMessage);
        if (!StringUtils.equals(user.getPassword(), password))
            throw new FebsException(errorMessage);
        if (User.STATUS_LOCK.equals(user.getStatus()))
            throw new FebsException("??????????????????,?????????????????????");

        // ????????????????????????
        this.userService.updateLoginTime(user);
        // ??????????????????
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        this.loginLogService.saveLoginLog(loginLog);

        String token = FebsUtil.encryptToken(JWTUtil.sign(username, password));
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(properties.getShiro().getJwtTimeOut());
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JWTToken jwtToken = new JWTToken(token, expireTimeStr);

        String userId = this.saveTokenToRedis(user, jwtToken, request);
        user.setId(userId);

        Map<String, Object> userInfo = this.generateUserInfo(jwtToken, user);
        return new FebsResponse().message("????????????").data(userInfo);
    }

    @GetMapping("index/{username}")
    public FebsResponse index(@NotBlank(message = "{required}") @PathVariable String username) {
        Map<String, Object> data = new HashMap<>();
        // ????????????????????????
        Long totalVisitCount = loginLogMapper.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = loginLogMapper.findTodayVisitCount();
        data.put("todayVisitCount", todayVisitCount);
        Long todayIp = loginLogMapper.findTodayIp();
        data.put("todayIp", todayIp);
        // ??????????????????????????????
        List<Map<String, Object>> lastSevenVisitCount = loginLogMapper.findLastSevenDaysVisitCount(null);
        data.put("lastSevenVisitCount", lastSevenVisitCount);
        User param = new User();
        param.setUsername(username);
        List<Map<String, Object>> lastSevenUserVisitCount = loginLogMapper.findLastSevenDaysVisitCount(param);
        data.put("lastSevenUserVisitCount", lastSevenUserVisitCount);
        return new FebsResponse().data(data);
    }

    @RequiresPermissions("user:online")
    @GetMapping("online")
    public FebsResponse userOnline(String username) throws Exception {
        String now = DateUtil.formatFullTime(LocalDateTime.now());
        Set<String> userOnlineStringSet = redisService.zrangeByScore(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, now, "+inf");
        List<ActiveUser> activeUsers = new ArrayList<>();
        for (String userOnlineString : userOnlineStringSet) {
            ActiveUser activeUser = mapper.readValue(userOnlineString, ActiveUser.class);
            activeUser.setToken(null);
            if (StringUtils.isNotBlank(username)) {
                if (StringUtils.equalsIgnoreCase(username, activeUser.getUsername()))
                    activeUsers.add(activeUser);
            } else {
                activeUsers.add(activeUser);
            }
        }
        return new FebsResponse().data(activeUsers);
    }

    @DeleteMapping("kickout/{id}")
    @RequiresPermissions("user:kickout")
    public void kickout(@NotBlank(message = "{required}") @PathVariable String id) throws Exception {
        String now = DateUtil.formatFullTime(LocalDateTime.now());
        Set<String> userOnlineStringSet = redisService.zrangeByScore(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, now, "+inf");
        ActiveUser kickoutUser = null;
        String kickoutUserString = "";
        for (String userOnlineString : userOnlineStringSet) {
            ActiveUser activeUser = mapper.readValue(userOnlineString, ActiveUser.class);
            if (StringUtils.equals(activeUser.getId(), id)) {
                kickoutUser = activeUser;
                kickoutUserString = userOnlineString;
            }
        }
        if (kickoutUser != null && StringUtils.isNotBlank(kickoutUserString)) {
            // ?????? zset????????????
            redisService.zrem(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, kickoutUserString);
            // ??????????????? token??????
            redisService.del(FebsConstant.TOKEN_CACHE_PREFIX + kickoutUser.getToken() + "." + kickoutUser.getIp());
        }
    }

    @GetMapping("logout/{id}")
    public void logout(@NotBlank(message = "{required}") @PathVariable String id) throws Exception {
        this.kickout(id);
    }

    @PostMapping("registt")
    public FebsResponse registt(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String xmname,
            @NotBlank(message = "{required}") String password,
            @NotBlank(message = "{required}") String idnumber,
            @NotBlank(message = "{required}") String yzm)
    {
        ModelMap map = new ModelMap();
        int success = 0;
        String msg = "";
        try {
            msg = iComSmsService.selectSmsYzm(username,yzm,ComSms.SENDTYPE_1);
            if(msg.equals("ok")) {
                this.userService.regist(username, xmname, password, idnumber);
                success = 1;
            }
        } catch (Exception e) {
            msg = "???????????????????????????.";
        }
        map.put("success", success);
        map.put("message", msg);
        return new FebsResponse().data(map);
    }

    @PostMapping("registn")
    public void registn(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String xmname,
            @NotBlank(message = "{required}") String password,
            @NotBlank(message = "{required}") String idnumber) throws Exception {
        this.userService.regist(username, xmname, password, idnumber);
    }

    @PostMapping("forgetPwdn")
    public FebsResponse forgetPwd_n(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String xmname,
            @NotBlank(message = "{required}") String newpassword,
            @NotBlank(message = "{required}") String idnumber) throws Exception {
        ModelMap map = new ModelMap();
        int success = 0;
        String msg = "";
        try {
            msg = this.userService.forgetPwd_n(username, xmname, newpassword, idnumber);
            if (msg.equals("")) {
                success = 1;
            }
        } catch (Exception e) {
            msg = "???????????????????????????.";
        }
        map.put("success", success);
        map.put("message", msg);
        return new FebsResponse().data(map);
    }

    @PostMapping("forgetPwdt")
    public FebsResponse forgetPwd_t(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String newpassword,
            @NotBlank(message = "{required}") String yzm) throws Exception {
        ModelMap map = new ModelMap();
        int success = 0;
        String msg = "";
        try {
            msg = iComSmsService.selectSmsYzm(username,yzm,ComSms.SENDTYPE_2);
            if(msg.equals("ok")) {
                msg = this.userService.forgetPwd_t(username, newpassword);
                if (msg.equals("")) {
                    success = 1;
                }
            }
        } catch (Exception e) {
            msg = "???????????????????????????.";
        }
        map.put("success", success);
        map.put("message", msg);
        return new FebsResponse().data(map);
    }

    @PostMapping("sendYzm")
    public FebsResponse sendYzm(@NotBlank(message = "{required}") String username,Integer sendtype) {
        ModelMap map = new ModelMap();
        int success = 0;
        String msg = "";
        try {
            msg = iComSmsService.sendSmsYzm(username, sendtype);
            if (msg.equals("ok")) {
                success = 1;
            }
        } catch (Exception e) {
            msg = "?????????????????????.";
        }
        map.put("success", success);
        map.put("message", msg);
        return new FebsResponse().data(map);
    }


    private String saveTokenToRedis(User user, JWTToken token, HttpServletRequest request) throws Exception {
        String ip = IPUtil.getIpAddr(request);

        // ??????????????????
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUsername(user.getUsername());
        activeUser.setIp(ip);
        activeUser.setToken(token.getToken());
        activeUser.setLoginAddress(AddressUtil.getCityInfo(ip));

        // zset ?????????????????????score ??????????????????
        this.redisService.zadd(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, Double.valueOf(token.getExipreAt()), mapper.writeValueAsString(activeUser));
        // redis ????????????????????? token???key = ?????? + ?????? token + .ip
        this.redisService.set(FebsConstant.TOKEN_CACHE_PREFIX + token.getToken() + StringPool.DOT + ip, token.getToken(), properties.getShiro().getJwtTimeOut() * 1000);

        return activeUser.getId();
    }

    /**
     * ?????????????????????????????????????????????
     * 1. token
     * 2. Vue Router
     * 3. ????????????
     * 4. ????????????
     * 5. ?????????????????????????????????
     *
     * @param token token
     * @param user  ????????????
     * @return UserInfo
     */
    private Map<String, Object> generateUserInfo(JWTToken token, User user) {
        String username = user.getUsername();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token.getToken());
        userInfo.put("exipreTime", token.getExipreAt());

        Set<String> roles = this.userManager.getUserRoles(username);
        userInfo.put("roles", roles);

        Set<String> permissions = this.userManager.getUserPermissions(username);
        userInfo.put("permissions", permissions);

        UserConfig userConfig = this.userManager.getUserConfig(String.valueOf(user.getUserId()));
        userInfo.put("config", userConfig);

        user.setPassword("it's a secret");
        userInfo.put("user", user);
        return userInfo;
    }
}
