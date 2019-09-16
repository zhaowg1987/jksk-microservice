package com.jksk.orgcreditratingpro.groupscore.dao;

import com.jksk.entity.orgcreditrating.GroupScore;
import com.jksk.entity.orgcreditrating.GroupScoreQ;
import com.jksk.entity.orgcreditrating.GroupScoreSource;
import com.jksk.entity.orgcreditrating.GroupScoreWordbook;

import java.util.List;

/**
 * @Author
 * @create 2019-07-04
 **/
public interface GroupScoreDao {

    /*查询机构列表*/
    List<GroupScore> selectGroupScoreList(GroupScoreQ groupScore);

    /*查询全部数据*/
    List<GroupScoreWordbook> selectGroupScoreWordbookList();

    GroupScore selectGroupScoreById(Long id);

    GroupScoreSource selectGroupScoreSourceById(Long id);

    GroupScoreSource selectGroupScoreSourceByGroupId(Long groupId);

    void insertGroupScore(GroupScore groupScore);

    void insertGroupScoreSource(GroupScoreSource groupScoreSource);

    void updateGroupScore(GroupScore groupScore);

    void updateGroupScoreSource(GroupScoreSource groupScoreSource);

    GroupScore selectGroupScoreByName(String groupName);
}
