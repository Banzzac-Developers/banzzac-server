package banzzac.utill;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;


@Data
public class Paging {

	private int limit; // 보여줄 게시글 갯수
	private int prevBlock, currentPage, nextBlock; // 이전 블록 페이지 번호, 현재 페이지 , 다음 블록 페이지 번호
	private int minPage,maxPage; // 최소 페이지, 최대 페이지 
	private int totRows, totPage; //총 데이터 크기 , 최대 페이지 번호
	private int block; // 보여줄 페이지 블록 갯수 ==> 1~10, 11~20, 21~30
	private int searchNo; // 데이터 베이스에서 검색할 시작 번호
	private boolean isNextBtn; // 다음 버튼이 있어야 하는가?
	
	
	public Paging(HttpServletRequest request, int totRows, int limit) {
		this.limit = limit;
		this.block = 10;
		this.currentPage = 1;
		if(request.getParameter("page")!= null) {
			this.currentPage = Integer.parseInt(request.getParameter("page"));
		}
		//페이지 번호가 0보다 작거나, 널이거나 하면 1로 셋팅
		if(this.currentPage <= 0) {
			this.currentPage = 1;
		}
		
		// 디비 limit 검색용
		this.searchNo = (this.currentPage -1 ) * limit;
		
		this.totRows = totRows;
		// 전체 데이터 갯수 나머지가 0이 아니면 남은 데이터가 있으므로 + 1 
		this.totPage = this.totRows % limit == 0 ? this.totRows/limit : this.totRows/limit +1;
		//			   (현재 페이지 번호 - 1)/보여줄 페이지 블록 갯수 * 보여줄 페이지 블록 갯수 + 1
		this.minPage = (currentPage -1) / block * block +1;
		this.maxPage = minPage + block -1;
		
		
		this.isNextBtn = true;
		
		//최대 페이지가 totPage보다 클경우 totPage로 설정
		if(this.maxPage > totPage) {
			this.maxPage = totPage;
			//paging.currentPage < paging.totPage 이랬던 조건을 아래로 바꿈 넥스트 버튼 유무 
			this.isNextBtn = false;
		}
		
		
		this.prevBlock = minPage-1;
		this.nextBlock = maxPage+1;
		
		//최대 페이지 번호보다 다음 버튼이 더 클경우 최대 페이지 번호로 이동
		if(totPage < nextBlock) {
			nextBlock = totPage;
		}
		
		
	}

}
