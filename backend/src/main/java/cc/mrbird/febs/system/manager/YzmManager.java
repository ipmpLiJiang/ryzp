package cc.mrbird.febs.system.manager;

import org.springframework.stereotype.Service;

/**
 * 封装一些和 登录相关的业务操作
 */
@Service
public class YzmManager {

//    @Autowired
//    private CacheService cacheService;


    /**
     * 通过类型和Key值获取ComConfiguremanage信息
     *
     * @param ctType 类型
     * @param con 键
     * @return ComConfigure信息
     */
//    public List<ComConfiguremanage> getConfigures(int ctType,String con) {
//        return FebsUtil.selectCacheByTemplate(
//                () -> this.cacheService.getConfigures(ctType,con),
//                () -> this.iComConfiguremanageService.getConfigLists(ctType));
//    }

    /**
     * 将ComConfiguremanage相关信息添加到 Redis缓存中
     *
     * @param ctType 类型
     * @param con 键
     */
//    public void loadgetConfigureCache(int ctType,String con) throws Exception {
        // 缓存ComConfigure
//        this.cacheService.saveConfigures(ctType,con);
//    }

    /**
     * 将ComConfiguremanage相关信息添加到 Redis缓存中
     *
     * @param ctType 类型
     * @param con 键
     */
//    public void saveConfigureCache(int ctType,String con) throws Exception {
        // 缓存ComConfigure
//        this.cacheService.saveConfigures(ctType,con);
//    }

}
