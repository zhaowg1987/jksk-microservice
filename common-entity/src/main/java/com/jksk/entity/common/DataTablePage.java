package com.jksk.entity.common;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2017-11-29
 **/
public class DataTablePage<T> {

    /**
     * 从PageInfo转换成数据
     * @param
     */
    public static DataTablePage responseData(DataTablePage dataTable, List dataList,Long recordsTotal) {
        dataTable.setsEcho(dataTable.getsEcho());
        // 如果查询数据为空。
        if(null == dataList) {
            dataList = new ArrayList<>();
        }
        dataTable.setData(dataList);
        dataTable.setRecordsTotal(recordsTotal);
        dataTable.setRecordsFiltered(recordsTotal);
        return dataTable;
    }

    public DataTablePage(HttpServletRequest request,String error){
        this.error = error;
        initData(request);
        this.setsEcho(this.getsEcho());
        this.setData(new ArrayList<>());
        this.setRecordsTotal(0L);
        this.setRecordsFiltered(0L);
    }

    public DataTablePage(HttpServletRequest request) {
        initData(request);
    }

    private void initData(HttpServletRequest request) {
        // sEcho 点击次数
        String sEcho = request.getParameter("sEcho");
        // iDisplayStart 开始数
        String iDisplayStart = request.getParameter("iDisplayStart");
        // 获取前端传递过来的每页数据量
        String displayLength = request.getParameter("iDisplayLength");
        //
        if(StringUtils.isNotEmpty(sEcho)) {
            this.setsEcho(Integer.parseInt(sEcho));
        } else {
            this.setsEcho(0);
        }
        // 每页数据量数
        if(StringUtils.isNotEmpty(displayLength)) {
            this.setiDisplayLength(Integer.parseInt(displayLength));
        } else {
            // 一般不应该走到这里面，如果进来，就说明有问题啦。
            this.setiDisplayLength(20);
        }
        if(StringUtils.isNotEmpty(iDisplayStart)) {
            this.setPageNum(Integer.parseInt(iDisplayStart)/this.getiDisplayLength() + 1);
        } else {
            this.setPageNum(1);
        }
    }

    /**
     * 当前页码值
     */
    private Integer pageNum;

    /**
     * 前端传递过来的每页数据量大小
     */
    private Integer iDisplayLength;

    /**
     * 必要。上面提到了，Datatables发送的draw是多少那么服务器就返回多少。
     * 这里注意，作者出于安全的考虑，强烈要求把这个转换为整形，
     * 即数字后再返回，而不是纯粹的接受然后返回，这是 为了防止跨站脚本（XSS）攻击。
     */
    private Integer sEcho;

    /**
     * 必要。即没有过滤的记录数（数据库里总共记录数）
     */
    private Long recordsTotal;

    /**
     * 必要。过滤后的记录数（如果有接收到前台的过滤条件，则返回的是过滤后的记录数）
     */
    private Long recordsFiltered;

    /**
     * 必要。表中中需要显示的数据。
     * 这是一个对象数组，也可以只是数组，区别在于 纯数组前台就不需要用 columns绑定数据，会自动按照顺序去显示 ，
     * 而对象数组则需要使用 columns绑定数据才能正常显示。
     * 注意这个 data的名称可以由 ajaxOption ajax不定时一讲 的 ajax.dataSrcOption ajax.dataSrc 1不定时一讲
     * ajax.dataSrc 2不定时一讲 控制
     */
    private List<T> data;

    /**
     * 可选。你可以定义一个错误来描述服务器出了问题后的友好提示
     */
    private String error;

    public Integer getsEcho() {
        return sEcho;
    }

    public void setsEcho(Integer sEcho) {
        this.sEcho = sEcho;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(Integer iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
