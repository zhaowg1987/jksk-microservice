package com.jksk.orgcreditratingpro.groupscore.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jksk.commonutil.DateUtil;
import com.jksk.commonutil.RedisUtil;
import com.jksk.entity.constants.CommonConstants;
import com.jksk.entity.constants.RedisKeys;
import com.jksk.entity.exception.JkskException;
import com.jksk.entity.orgcreditrating.*;
import com.jksk.orgcreditratingpro.groupscore.dao.GroupScoreDao;
import com.jksk.orgcreditratingpro.groupscore.service.GroupScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author
 * @create 2019-07-04
 **/
@Slf4j
@Service
public class GroupScoreServiceImpl implements GroupScoreService {

    @Autowired
    private GroupScoreDao groupScoreDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public PageInfo<GroupScore> selectGroupScoreList(GroupScoreQ groupScore)  throws JkskException {
        //开始分页
        PageHelper.startPage(groupScore.getPageNum(),groupScore.getPageSize());
        //查询操作
        List<GroupScore> list= groupScoreDao.selectGroupScoreList(groupScore);

        return new PageInfo<GroupScore>(list);
    }

    /**
     * 取字典值
     * @return
     */
    @Override
    public List<GroupScoreWordbook> selectGroupScoreWordbookList() {
        // 先从redis中取值，如果没有，再从数据库中取值
        List<GroupScoreWordbook> dataList = (List<GroupScoreWordbook>) redisUtil.get(RedisKeys.GROUP_SCORE_WORKBOOK_LIST_KEY);
        if(null == dataList) {
            synchronized (this) {
                dataList = groupScoreDao.selectGroupScoreWordbookList();
                redisUtil.set(RedisKeys.GROUP_SCORE_WORKBOOK_LIST_KEY,dataList);
                // 新建一个map存储
                Map<Long,GroupScoreWordbook> dataMap = new HashMap<>();
                for (GroupScoreWordbook g : dataList) {
                    dataMap.put(g.getId(),g);
                }
                // 后期方便直接读取
                redisUtil.set(RedisKeys.GROUP_SCORE_WORKBOOK_MAP_KEY,dataMap);
            }
        }
        return dataList;
    }

    @Override
    public GroupScoreSourceRadios selectGroupScoreRadio() {
        GroupScoreSourceRadios groupScoreSourceRadios = new GroupScoreSourceRadios();
        List<GroupScoreWordbook> dataList = selectGroupScoreWordbookList();
        /*机构资料  14	group_info	机构资料 */
        groupScoreSourceRadios.setGroupInfoRadios(getDataListByParentId(14L,dataList));
        /*机构三方核查 15	group_three_check	机构三方核查 */
        groupScoreSourceRadios.setGroupThirdCheckRadios(getDataListByParentId(15L,dataList));
        /*机构网络查询 16	group_net_check	机构网络查询*/
        groupScoreSourceRadios.setGroupNetworkCheckRadios(getDataListByParentId(16L,dataList));
        /*历史数据分析 17	history_data_ass    历史数据分析*/
        groupScoreSourceRadios.setGroupHistoryDataRadios(getDataListByParentId(17L,dataList));
        /* IT（校招）18	it_college_find	IT（校招）*/
        groupScoreSourceRadios.setGroupItSchoolRadios(getDataListByParentId(18L,dataList));
        /* IT（社招）19	it_society_find	IT（社招）*/
        groupScoreSourceRadios.setGroupItSocietyRadios(getDataListByParentId(19L,dataList));
        /*证书 20	certificate	证书*/
        groupScoreSourceRadios.setGroupCertificateRadios(getDataListByParentId(20L,dataList));
        /* 学历（网教） 21	education_net_teach	学历（网教）*/
        groupScoreSourceRadios.setGroupEducationNetworkRadios(getDataListByParentId(21L,dataList));
        /*学历（自考）22	education_self_teach	学历（自考）*/
        groupScoreSourceRadios.setGroupEducationSelfTaughtRadios(getDataListByParentId(22L,dataList));
        /*学历（成考）23	education_examination	学历（成考）*/
        groupScoreSourceRadios.setGroupEducationAdultRadios(getDataListByParentId(23L,dataList));
        /*学历（电大）24	education_tv	学历（电大）*/
        groupScoreSourceRadios.setGroupEducationOpenRadios(getDataListByParentId(24L,dataList));
        /*K12 25	K12	K12*/
        groupScoreSourceRadios.setGroupK12Radios(getDataListByParentId(25L,dataList));
        return groupScoreSourceRadios;
    }

    /**
     * 根据父ID获取列表
     * @param parentId
     * @param dataList
     * @return
     */
    public List<GroupScoreWordbook> getDataListByParentId(Long parentId,List<GroupScoreWordbook> dataList) {
        List<GroupScoreWordbook> list = new ArrayList<>();
        for (GroupScoreWordbook groupScoreWordbook : dataList) {
            if(parentId == groupScoreWordbook.getParent_id()) {
                groupScoreWordbook.setChildren(getDataListByParentId(groupScoreWordbook.getId(),dataList));
                list.add(groupScoreWordbook);
            }
        }
        return list;
    }

    /**
     * 获取字典值
     * @return
     */
    private Map<Long,GroupScoreWordbook> getMapGroupScoreWordbookMap() {
        Map<Long,GroupScoreWordbook> dataMap = (Map<Long,GroupScoreWordbook>) redisUtil.get(RedisKeys.GROUP_SCORE_WORKBOOK_MAP_KEY);
        // 从数据库中获取
        if(null == dataMap) {
            List<GroupScoreWordbook> dataList = groupScoreDao.selectGroupScoreWordbookList();
            for (GroupScoreWordbook groupScoreWordbook : dataList) {
                dataMap.put(groupScoreWordbook.getId(),groupScoreWordbook);
            }
            redisUtil.set(RedisKeys.GROUP_SCORE_WORKBOOK_MAP_KEY,dataMap);
        }
        return dataMap;
    }



    @Transactional
    @Override
    public GroupScore saveGroupScore(GroupScoreSource groupScoreSource) throws Exception {
        // 计算分值与等级
        Map<Long,GroupScoreWordbook> radiosMap = getMapGroupScoreWordbookMap();
        // 获取所有等级
        Field[] fields = groupScoreSource.getClass().getDeclaredFields();

        Object obj = groupScoreSource.getClass().newInstance();

        int other_score = 0;
        // IT
        int it_score = 0;
        // 证书
        int certificate_score = 0;
        // 学历
        int education_score = 0;
        // 是否包含D选项。
        boolean contain_d = false;
        for (int i = 0; i < fields.length; i++) {
            //得到属性
            Field field = fields[i];
            //设置是否允许访问，不是修改原来的访问权限修饰词。
            field.setAccessible(true);
            if("id".equals(field.getName()) || "group_score_id".equals(field.getName())
                || "it_proportion".equals(field.getName()) || "certificate_proportion".equals(field.getName())
                || "education_proportion".equals(field.getName()) || "group_name".equals(field.getName())
                || "serialVersionUID".equals(field.getName())) {
                continue;
            }
            Object obj2 = field.get(groupScoreSource);
            if(null == obj2) {
                continue;
            }
            GroupScoreWordbook scoreWordbook = radiosMap.get(String.valueOf(obj2));
            if(null == scoreWordbook) {
                continue;
            }
            log.info("获取对象值:{}",scoreWordbook.toString());
            if("D".equals(scoreWordbook.getScore())) {
                contain_d = true;
                continue;
            }
            if(18 == scoreWordbook.getParent_id() || 19 == scoreWordbook.getParent_id()) {
                it_score += Integer.valueOf(scoreWordbook.getScore());
            } else if(20 == scoreWordbook.getParent_id()) {
                certificate_score += Integer.valueOf(scoreWordbook.getScore());
            } else if(21 == scoreWordbook.getParent_id() || 22 == scoreWordbook.getParent_id()
                || 23 == scoreWordbook.getParent_id() || 24 == scoreWordbook.getParent_id()) {
                education_score += Integer.valueOf(scoreWordbook.getScore());
            } else {
                other_score += Integer.valueOf(scoreWordbook.getScore());
            }
        }

        if(null != groupScoreSource.getIt_proportion()) {
            it_score = groupScoreSource.getIt_proportion()
                    .multiply(BigDecimal.valueOf(it_score)).setScale(0, BigDecimal.ROUND_UP).intValue();
        }
        if(null != groupScoreSource.getCertificate_proportion()) {
            certificate_score = groupScoreSource.getCertificate_proportion()
                    .multiply(BigDecimal.valueOf(certificate_score)).setScale(0,BigDecimal.ROUND_UP).intValue();
        }
        if(null != groupScoreSource.getEducation_proportion()) {
            education_score = groupScoreSource.getEducation_proportion()
                    .multiply(BigDecimal.valueOf(education_score)).setScale(0,BigDecimal.ROUND_UP).intValue();
        }

        int all_score = other_score + it_score + certificate_score + education_score;
        // String level = getLevelByScore(all_score);
        // 构造数据
        GroupScore groupScore = new GroupScore();
        // 机构名称
        groupScore.setGroupName(groupScoreSource.getGroup_name());
        // 系统评分
        groupScore.setSysScore(all_score);
        // 机构等级
        // groupScore.setGroupLevel(level);
        groupScore.setCreateTime(DateUtil.getNowDateTime());
        groupScore.setUpdateTime(DateUtil.getNowDateTime());
        // 是否包含D选项
        if(contain_d) {
            groupScore.setContainD(CommonConstants.CONSTANT_YES);
        } else {
            groupScore.setContainD(CommonConstants.CONSTANT_NO);
        }
        // 调用接口保存数据
        if(null != groupScoreSource.getId()
                && null != groupScoreSource.getGroup_score_id()) {
            groupScore.setId(groupScoreSource.getGroup_score_id());
            groupScoreDao.updateGroupScore(groupScore);
            groupScoreDao.updateGroupScoreSource(groupScoreSource);
        } else {
            groupScoreDao.insertGroupScore(groupScore);
            // 设置机构ID
            groupScoreSource.setGroup_score_id(groupScore.getId());
            groupScoreDao.insertGroupScoreSource(groupScoreSource);
        }
        groupScore.setGroupScoreSourceId(groupScoreSource.getId());
        return groupScore;
    }

    @Transactional
    @Override
    public void updateGroupScore(GroupScore groupScore) throws JkskException {
        groupScoreDao.updateGroupScore(groupScore);
    }


    @Override
    public GroupScoreSource selectGroupScoreSourceById(Long id) {
        return groupScoreDao.selectGroupScoreSourceById(id);
    }

    @Override
    public GroupScoreSource selectGroupScoreSourceByGroupId(Long groupId) {
        return groupScoreDao.selectGroupScoreSourceByGroupId(groupId);
    }

    @Override
    public GroupScore selectGroupScoreById(Long id) {
        return groupScoreDao.selectGroupScoreById(id);
    }

    @Override
    public GroupScore selectGroupScoreByName(String groupName) {
        return groupScoreDao.selectGroupScoreByName(groupName);
    }

}
