package com.lifen.utils;

import java.util.List;

/**
 * 分页结果对象
 * 
 * @author wangfan
 * @date 2017-7-24 下午4:28:59
 * @param <T>
 */
public class PageResult<T> {
	
	private int code; //状态码, 0表示成功
	
	private String msg;  //提示信息
	private Integer currentPage ;  //当前页
	private Integer pages ;  //总页数

	private long count; // 总数量, bootstrapTable是total

	private List<T> data; // 当前数据, bootstrapTable是rows

	public PageResult() {
	}


	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public PageResult(long total, List<T> rows) {
		this.count = total;
		this.data = rows;
		this.code = 0;
		this.msg = "";
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
