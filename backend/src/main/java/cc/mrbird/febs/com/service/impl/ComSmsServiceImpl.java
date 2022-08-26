package cc.mrbird.febs.com.service.impl;

import cc.mrbird.febs.cn.webxml.sms.SmsService;
import cc.mrbird.febs.cn.webxml.sms.SmsServicePortType;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.properties.FebsProperties;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.com.entity.ComSms;
import cc.mrbird.febs.com.dao.ComSmsMapper;
import cc.mrbird.febs.com.service.IComSmsService;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-13
 */
@Slf4j
@Service("IComSmsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ComSmsServiceImpl extends ServiceImpl<ComSmsMapper, ComSms> implements IComSmsService {

    @Autowired
    FebsProperties febsProperties;

    @Override
    public IPage<ComSms> findComSmss(QueryRequest request, ComSms comSms) {
        try {
            LambdaQueryWrapper<ComSms> queryWrapper = new LambdaQueryWrapper<>();
            String sql = "1=1";
            if (comSms.getApplyDateStr() != null) {
//                queryWrapper.eq(ComSms::getApplyDateStr,comSms.getApplyDateStr());
                sql += " and applyDateStr = '" + comSms.getApplyDateStr() + "'";
            }
            if (comSms.getRefTableId() != null) {
                sql += " and refTableId = '" + comSms.getRefTableId() + "'";
            }
            if (comSms.getAreaType() != null) {
//                queryWrapper.eq(ComSms::getAreaType, comSms.getAreaType());
                sql += " and areaType = " + comSms.getAreaType();
            }
            if (comSms.getTypeno() != null) {
//                queryWrapper.eq(ComSms::getTypeno, comSms.getTypeno());
                sql += " and typeno = " + comSms.getTypeno();
            }
            if (comSms.getSendType() != null) {
//                queryWrapper.eq(ComSms::getSendType, comSms.getSendType());
                sql += " and sendType = " + comSms.getSendType();
            }
            if (comSms.getState() != null) {
//                queryWrapper.eq(ComSms::getState, comSms.getState());
                sql += " and state = " + comSms.getState();
            }
            if (comSms.getComments() != null && !comSms.getComments().equals("")) {
                sql += " and (sendcode = '" + comSms.getComments() + "' or sendname = '" + comSms.getComments() + "' or mobile = '" + comSms.getComments() + "')";
            }
//            queryWrapper.eq(ComSms::getIsDeletemark, 1);//1是未删 0是已删
            queryWrapper.apply(sql);
            Page<ComSms> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ComSms> findComSmsList(QueryRequest request, ComSms comSms) {
        try {
            Page<ComSms> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findComSms(page, comSms);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    public List<ComSms> findLmdSmsList(ComSms comSms) {
        List<ComSms> list = new ArrayList<>();
        LambdaQueryWrapper<ComSms> wrapper = new LambdaQueryWrapper<>();
        if (comSms.getApplyDateStr() != null) {
            wrapper.eq(ComSms::getApplyDateStr, comSms.getApplyDateStr());
        }
        if (comSms.getAreaType() != null) {
            wrapper.eq(ComSms::getAreaType, comSms.getAreaType());
        }
        if (comSms.getSendType() != null) {
            wrapper.eq(ComSms::getSendType, comSms.getSendType());
        }
        if (comSms.getState() != null) {
            wrapper.eq(ComSms::getState, comSms.getState());
        }
        if (comSms.getSendcode() != null) {
            wrapper.eq(ComSms::getSendcode, comSms.getSendcode());
        }
        if (comSms.getSendname() != null) {
            wrapper.eq(ComSms::getSendname, comSms.getSendname());
        }
        if (comSms.getMobile() != null) {
            wrapper.eq(ComSms::getMobile, comSms.getMobile());
        }
        if (comSms.getTypeno() != null) {
            wrapper.eq(ComSms::getTypeno, comSms.getTypeno());
        }

        wrapper.eq(ComSms::getIsDeletemark, 1);
        list = this.list(wrapper);
        return list;
    }

    @Override
    public List<ComSms> findSmsTopLists(ComSms comSms) {
        return this.baseMapper.findSmsTopList(comSms);
    }

    @Override
    public List<ComSms> findSmsList(ComSms comSms) {
        LambdaQueryWrapper<ComSms> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ComSms::getIsDeletemark, 1);
        if (comSms.getSendcode() != null) {
            wrapper.eq(ComSms::getSendcode, comSms.getSendcode());
        }
        if (comSms.getMobile() != null) {
            wrapper.eq(ComSms::getMobile, comSms.getMobile());
        }
        if (comSms.getAreaType() != null) {
            wrapper.eq(ComSms::getAreaType, comSms.getAreaType());
        }
        return this.list(wrapper);
    }

    @Override
    @Transactional
    public void createComSms(ComSms comSms) {
        comSms.setId(UUID.randomUUID().toString());
        comSms.setCreateTime(new Date());
        comSms.setIsDeletemark(1);
        this.save(comSms);
    }

    @Override
    @Transactional
    public void updateComSms(ComSms comSms) {
        comSms.setModifyTime(new Date());
        this.baseMapper.updateComSms(comSms);
    }

    @Override
    @Transactional
    public void deleteComSmss(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);

    }

    private String sendMsg(String mobiles, String sendContent) {
        String sms = "";
        try {
            SmsService smsService = new SmsService();
            SmsServicePortType ssp = smsService.getSmsServiceHttpPort();

//            String in0 = febsProperties.getIn0();
//            String in1 = febsProperties.getIn1();
//            String in2 = febsProperties.getIn2();
//            String in3 = febsProperties.getIn3();
//            String in4 = mobiles;
//            String in5 = sendContent;
//            sms = ssp.service(in0, in1, in2, in3, in4, in5);

        } catch (Exception e) {
            sms = e.getMessage();
        }

        return sms;
    }

    @Override
    @Transactional
    public void sendSms(int sendType, int areaType) {
        String msg = "";
        ComSms comSms = new ComSms();
        comSms.setState(ComSms.STATE_0);
        comSms.setSendType(sendType);
        comSms.setAreaType(areaType);
        List<ComSms> list = this.baseMapper.findSmsTopList(comSms);
        int nOpenSms = 2;//febsProperties.getOpenSms();
        boolean isOpenSms = nOpenSms == 1 ? true : false;
        if (list.size() > 0 && isOpenSms) {
            List<ComSms> sendList = new ArrayList<>();
            ComSms t1 = list.get(0);
            sendList = list.stream().filter(s -> s.getSendType() == t1.getSendType() && s.getSendcontent().equals(t1.getSendcontent())).collect(Collectors.toList());
            String mobiles = "";
            List<String> mobList = new ArrayList<>();
            if (sendList.size() > 0) {
                List<ComSms> updateList = new ArrayList<>();
                for (ComSms item : sendList) {
                    ComSms updateSms = new ComSms();
                    updateSms.setId(item.getId());
                    updateSms.setState(ComSms.STATE_1);
                    updateSms.setModifyTime(new Date());
                    updateList.add(updateSms);

                    if (mobList.stream().filter(s -> s.equals(item.getMobile())).count() == 0) {
                        if (Validator.isMobile(item.getMobile())) {
                            if (mobiles.equals("")) {
                                mobiles = item.getMobile();
                            } else {
                                mobiles += "," + item.getMobile();
                            }
                            mobList.add(item.getMobile());
                        }
                    }
                }
                msg = sendMsg(mobiles, t1.getSendcontent());

                if (msg.equals("0")) {
                    this.updateBatchById(updateList);
                    log.info("短信sendSms 发送成功.");
                } else {
                    //0 0/3 21-23 1/1 * ? //0 0/3 * * * ?
                    log.error("发短信Error", msg);
                }
            }
        }

    }

    @Override
    @Transactional
    public String sendSms(ComSms comSms, List<ComSms> list) {
        String msg = "";
        if (list == null) {
            list = this.baseMapper.findSmsTopList(comSms);
        }
        int nOpenSms = 2;//febsProperties.getOpenSms();
        boolean isOpenSms = nOpenSms == 1 ? true : false;
        if (list.size() > 0 && isOpenSms) {
            List<ComSms> sendList = new ArrayList<>();
            ComSms t1 = list.get(0);
            sendList = list.stream().filter(s -> s.getSendType() == t1.getSendType() && s.getSendcontent().equals(t1.getSendcontent())).collect(Collectors.toList());
            String mobiles = "";
            List<String> mobList = new ArrayList<>();
            if (sendList.size() > 0) {
                List<ComSms> updateList = new ArrayList<>();
                for (ComSms item : sendList) {
                    ComSms updateSms = new ComSms();
                    updateSms.setId(item.getId());
                    updateSms.setState(ComSms.STATE_1);
                    updateSms.setModifyTime(new Date());
                    updateList.add(updateSms);

                    if (mobList.stream().filter(s -> s.equals(item.getMobile())).count() == 0) {
                        if (Validator.isMobile(item.getMobile())) {
                            if (mobiles.equals("")) {
                                mobiles = item.getMobile();
                            } else {
                                mobiles += "," + item.getMobile();
                            }
                            mobList.add(item.getMobile());
                        }
                    }
                }
                msg = sendMsg(mobiles, t1.getSendcontent());

                if (msg.equals("0")) {
                    this.updateBatchById(updateList);
                    msg = "ok";
                    log.info("短信sendSms list发送成功.");
                } else {
                    //0 0/3 21-23 1/1 * ? //0 0/3 * * * ?
                    log.error("发短信Error", msg);
                }
            }
        } else {
            msg = "未找到数据或发送短信未开启.";
        }
        return msg;
    }


}