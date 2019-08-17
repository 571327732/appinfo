/**
 * 
 */
package cn.appsys.tools;

/**
 * @author 57132 分页工具
 */
public class PageSupport {

	// 总记录数
	private int totalCount = 0;
	// 页大小
	private int pageSize = 0;
	// 当前页
	private int currentPageNo = 1;
	// 总页数
	private int totalPageCount = 1;
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		if(totalCount>0){
			this.totalCount=totalCount;
			this.setTotalPageCount();
		}	
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount() {
		if(this.totalCount % this.pageSize!=0){
			this.totalPageCount=this.totalCount/this.pageSize+1;
		}else if(this.totalCount% this.pageSize==0){
			this.totalPageCount=this.totalCount/this.pageSize;
		}else{
			this.totalPageCount=0;
		}
	}

}
