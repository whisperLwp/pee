package com.hongedu.pems.util.page;


import java.io.Serializable;
import java.util.List;

public class Pager<T> implements Serializable {

    private static final long serialVersionUID = -8741766802354222579L;

    //每页显示多少条记录
    private int pageSize;
    //当前第几页数据
    private int currentPage;
    //一共有多少条记录
    private int totalRecord;
    //一共多少页记录
    private int totalPage;
    //要显示的数据，使用泛型
    private List<T> dataList;
    //起始索引
    private int fromIndex;

    /**
     * 
     * @param currentPage当前页，从1计数
     * @param pageSize分页数
     * @param totalRecord总记录
     */
    public Pager(int currentPage, int pageSize, int totalRecord){
        //总记录条数
        this.totalRecord = totalRecord;
        //每页显示多少条记录
        this.pageSize = pageSize;
        //获取总页数
        this.totalPage = this.totalRecord / this.pageSize;
        if (this.totalRecord % this.pageSize != 0) {
            this.totalPage += 1;
        }
        this.totalPage = this.totalPage == 0 ? 1 : totalPage;
        //当前第几页数据
        this.currentPage = this.totalPage < currentPage ? this.totalPage : currentPage;
//        this.currentPage = this.totalPage == 0 ? 0 : currentPage;
        //起始索引
        this.fromIndex = this.pageSize * (this.currentPage - 1);
    }

 /*   public Pager(int currentPage, int pageSize, List<T> sourceList){
        if (sourceList == null){
            return;
        }

        //总记录条数
        this.totalRecord = sourceList.size();
        //每页显示多少条记录
        this.pageSize = pageSize;
        //获取总页数
        this.totalPage = this.totalRecord / this.pageSize;
        if (this.totalRecord % this.pageSize != 0) {
            this.totalPage += 1;
        }

        //当前第几页数据
        this.currentPage = this.totalPage < currentPage ? this.totalPage : currentPage;

        //起始索引
        int fromIndex = this.pageSize * (this.currentPage - 1);
        //结束索引
        int toIndex =this.pageSize * this.currentPage > this.totalRecord ?  this.totalRecord :  this.pageSize * this.currentPage;

        this.dataList = sourceList.subList(fromIndex, toIndex);
    }*/

    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getTotalRecord() {
        return totalRecord;
    }
    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public List<T> getDataList() {
        return dataList;
    }
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

	public int getFromIndex() {
		return fromIndex;
	}

	public void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}



}