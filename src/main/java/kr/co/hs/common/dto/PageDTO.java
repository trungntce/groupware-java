package kr.co.hs.common.dto;

import lombok.Data;
import org.springframework.context.i18n.LocaleContextHolder;

@Data
public class PageDTO {

	private String id;
	protected int start = 0;
	protected int page = 1;
	protected int rows = 10;
	protected int limit = 10;
	protected int total = 0;
	protected int navi = 3;
	protected int totalPageSize;
	private String langCd = LocaleContextHolder.getLocale().toString();
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
		this.rows = limit;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
		totalPageSize = (int)Math.ceil( (double)total / getRows() );
	}
	public int getNavi() {
		return navi;
	}
	public void setNavi(int navi) {
		this.navi = navi;
	}
	public int getTotalPageSize() {
		return totalPageSize;
	}
	public void setTotalPageSize(int totalPageSize) {
		this.totalPageSize = totalPageSize;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
		this.limit = rows;
	}
	public int getStart() {
		return (page-1)*limit;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
