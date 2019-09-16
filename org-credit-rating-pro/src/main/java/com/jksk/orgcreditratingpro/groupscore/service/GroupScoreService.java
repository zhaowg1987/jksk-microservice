package com.jksk.orgcreditratingpro.groupscore.service;

import com.github.pagehelper.PageInfo;
import com.jksk.entity.exception.JkskException;
import com.jksk.entity.orgcreditrating.*;

import java.util.List;

/**
 * @Author
 * @create 2019-07-04
 **/
public interface GroupScoreService {

    PageInfo<GroupScore> selectGroupScoreList(GroupScoreQ groupScore) throws JkskException;

    List<GroupScoreWordbook> selectGroupScoreWordbookList();

    GroupScoreSourceRadios selectGroupScoreRadio();

    GroupScore saveGroupScore(GroupScoreSource groupScoreSource) throws Exception;

    void updateGroupScore(GroupScore groupScore) throws JkskException;

    GroupScoreSource selectGroupScoreSourceById(Long id);

    GroupScoreSource selectGroupScoreSourceByGroupId(Long groupId);

    GroupScore selectGroupScoreById(Long id);

    GroupScore selectGroupScoreByName(String groupName);
}
