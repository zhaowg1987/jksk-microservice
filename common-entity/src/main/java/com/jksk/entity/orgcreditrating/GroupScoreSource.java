package com.jksk.entity.orgcreditrating;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author
 * @create 2019-07-08
 **/
@Data
public class GroupScoreSource implements Serializable {
    private static final long serialVersionUID = -8225057089359364433L;

    private Long id;
    private Long group_score_id;
    // 机构名称
    private String group_name;
    private Long group_nature;
    private Long regist_money;
    private Long year_establishment;
    private Long private_qualifications;
    private Long business_model;
    private Long course_model;
    private Long course_prise;
    private Long teacher_num;
    private Long student_num;
    private Long group_year_turnover;
    private Long patent;
    private Long win_the_prize;
    private Long litigation;
    private Long executee_man;
    private Long eye_negative;
    private Long group_change;
    private Long branch_negative;
    private Long net_negative;
    private Long official_website;
    private Long baidu_negative;
    private Long credit_rank;
    private Long negative_history_info;
    private Long total_in_stock;
    private Long delay_rate;
    private Long handle_rate;
    private Long pass_rate;
    private Long school_begin;
    private Long get_job;
    private Long education_direction;
    private Long student_major;
    private Long charge_method;
    private Long training_cycle;
    private Long cooperation_model;
    private Long colleges_num;
    private Long class_model;
    private Long student_scope;
    private Long teaching_way;
    private Long entrance_standard;
    private Long promise_work;
    private Long recruit_transfer;
    private Long certificate_company;
    private Long certificate_nature;
    private Long eligibility_applicants;
    private Long work_nature;
    private Long can_control;
    private Long proxy_level_net;
    private Long exam_level;
    private Long is_oneself;
    private Long refund_standard_net;
    private Long proxy_level_self;
    private Long big_bag;
    private Long refund_standard_self;
    private Long college_test;
    private Long onself_test;
    private Long refund_standard_adult;
    private Long refund_standard_tv;
    private Long school_nature;
    private Long pay_method;
    private Long train_subject;
    private BigDecimal it_proportion;
    private BigDecimal certificate_proportion;
    private BigDecimal education_proportion;
}
