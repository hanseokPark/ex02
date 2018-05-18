package com.dgit.domain;

public class PageMaker {
	private int totalCount;//게시물 전체 갯수
	private int startPage; //페이지 번호의 시작번호
	private int endPage;
	private boolean prev;
	private boolean next;
	
	private int displayPageNum = 10; //보여 지는 페이지 번호의 갯수
	private Criteria cri; //현재 페이지 번호를 알아야 페이지 번호의 시작을 알수있음
	
	private void calcData(){
		// 현재 페이지의 끝 번호를 구한다.
		// ex) 15/10 => Math.seil(1.5)->2*10 ->20
		endPage = (int)(Math.ceil(cri.getPage()/ (double) displayPageNum) * displayPageNum);
		
		// 현재 페이지의 시작번호를 구한다.
		// ex) 20-10+1= 11
		startPage = (endPage - displayPageNum) + 1;
		
		// 전체 게시물이 151이고 현재 페이지가 15일때, 마지막 end는 16로 나타나야 한다.
		// Math.ceil(151/10) = 16
		int tempEndPage = (int)(Math.ceil(totalCount / (double)cri.getPerPageNum()));
		
		if(endPage > tempEndPage){
			endPage = tempEndPage;
		}
		
		prev = (startPage == 1) ? false : true;
		next = (endPage * cri.getPerPageNum() >= totalCount) ? false : true;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	public Criteria getCri() {
		return cri;
	}
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	@Override
	public String toString() {
		return "PageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev="
				+ prev + ", next=" + next + ", displayPageNum=" + displayPageNum + ", cri=" + cri + "]";
	}
	
	
	
}
