package cc.mrbird.febs.zp.service;

import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.zp.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author viki
 * @since 2022-01-03
 */
public interface IZpStaffInfoService extends IService<ZpStaffInfo> {

    IPage<ZpStaffInfo> findZpStaffInfos(QueryRequest request, ZpStaffInfo zpStaffInfo);

    IPage<ZpStaffInfo> findZpStaffInfoList(QueryRequest request, ZpStaffInfo zpStaffInfo);

    ZpStaffInfo findZpStaffInfoByUserId(Integer userId);

    ZpStaffInfo findZpStaffInfoByXmAndIdNumber(String ryName,String idNumber,String email);

    ZpStaffInfo findZpStaffInfoById(String Id);

    void createZpStaffInfo(ZpStaffInfo zpStaffInfo);

    void updateZpStaffInfo(ZpStaffInfo zpStaffInfo);

    void deleteZpStaffInfos(String[] Ids);

    StaffInfo getStaffInfo(ZpStaffInfo zpStaffInfo);

    void updateStaffInfo(StaffInfo staffInfo) throws ParseException;

    String createStaffMx(String staffId, String type);

    void deleteStaffMx(String id, String type);

    ZpStaffInfo initStaff(User user,String idnumber);

    ZpStaffInfo findZpStaffInfoByUserId(long userId);

    List<StaffEducation> getEducationList(String staffId);
    List<StaffWork> getWorkList(String staffId);
    List<StaffEssay> getEssayList(String staffId);


    boolean applyStateUpdate(String applyId, int state);

    boolean applyStateUpdates(String[] ids, int state);

    List<ZpStaffInfo> findStaffByPosterId(String posterId,
                                               Integer state,
                                               List<String> idList);

    ZpStaffInfo findByIdnumber(String idnumber);
}
