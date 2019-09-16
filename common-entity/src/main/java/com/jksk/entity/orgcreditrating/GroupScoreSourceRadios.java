package com.jksk.entity.orgcreditrating;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author
 * @create 2019-07-05
 **/
@Data
public class GroupScoreSourceRadios implements Serializable {
    private static final long serialVersionUID = -3947555093213456743L;

    /*机构资料*/
    private List<GroupScoreWordbook> groupInfoRadios;
    /*机构三方核查*/
    private List<GroupScoreWordbook> groupThirdCheckRadios;
    /*机构网络查询*/
    private List<GroupScoreWordbook> groupNetworkCheckRadios;
    /*历史数据分析*/
    private List<GroupScoreWordbook> groupHistoryDataRadios;
    /* IT（校招）*/
    private List<GroupScoreWordbook> groupItSchoolRadios;
    /* IT（社招）*/
    private List<GroupScoreWordbook> groupItSocietyRadios;
    /*证书*/
    private List<GroupScoreWordbook> groupCertificateRadios;
    /* 学历（网教）*/
    private List<GroupScoreWordbook> groupEducationNetworkRadios;
    /*学历（自考）*/
    private List<GroupScoreWordbook> groupEducationSelfTaughtRadios;
    /*学历（成考）*/
    private List<GroupScoreWordbook> groupEducationAdultRadios;
    /*学历（电大）*/
    private List<GroupScoreWordbook> groupEducationOpenRadios;
    /*K12*/
    private List<GroupScoreWordbook> groupK12Radios;
}
