package com.yykj.system.entity;

/**
 * 分页使用的对象
 * @author Rex
 *
 */
public class Pager {

	private Integer pageIndex;//当前页码
	
	private Integer pageSize;//每页记录数
	
	private Integer totalCount;//总记录数
	
	private Integer totalPages;//总页数
	
	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Pager() {
		// TODO Auto-generated constructor stub
	}
	
	
}
