package kr.co.hs.common.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import kr.co.hs.common.dto.PageDTO;

public class PageTag extends TagSupport {

	private static final long serialVersionUID = -240249619261555015L;
	private String script = "goPage";
	private PageDTO page;

	@Override
	public int doStartTag() throws JspException {
		

        if (page == null) {
            return SKIP_BODY;
        }

        String content = paging( page.getTotal(), page.getLimit(), page.getNavi(), page.getPage() , script);
        try {
			pageContext.getOut().println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return SKIP_BODY;
	}


	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public PageDTO getPage() {
		return page;
	}

	public void setPage(PageDTO page) {
		this.page = page;
	}

	private String paging(int totalCnt, int rowRange, int pageRange, int curPage, String script) {

		if( script.isEmpty() )
			script = "goPage";

		String path = SecuritySession.getCurrentRequest().getContextPath();
		if( path.equals("/") )
			path = "";

		StringBuffer sb = new StringBuffer();

		if( totalCnt == 0 )	return "";

		long pageCnt = totalCnt % rowRange;

		if( pageCnt == 0 )
			pageCnt = totalCnt / rowRange;
		else
			pageCnt = totalCnt / rowRange + 1L;

		long totalPage = pageCnt;

		long rangeCnt = curPage / pageRange;
		if( curPage % pageRange == 0 )
			rangeCnt = curPage / pageRange -1L;

		sb.append("<ul id=\"paging\" class=\"pagination float-right\">\n");

		long firstPage = curPage - pageRange;
		if( firstPage > 0 ) {
			sb.append("<div class=\"numPN paginate_button page-item \">");
			sb.append("<a href=\"javascript:"+script+"('1');\" class=\"page-link\" val='1'>&#171;</a></div>");
		}

		if( curPage > 1 ) {
			sb.append("<div class=\"numPN over left paginate_button page-item \">");
			sb.append("<a href=\"javascript:"+script+"('").append(curPage-1).append("');\" class=\"page-link\" val='1'>&#60;</a></div>");
		}

		for( long i = rangeCnt * pageRange + 1L ; i < (rangeCnt + 1L) * pageRange + 1L ; i++) {

			if( i == curPage )
				sb.append("<div class=\"Present paginate_button page-item active\"><a class=\"hand page-link\">").append(i).append("</a></div>");
			else if( i > 10)
				sb.append("<div class=\"dubble paginate_button page-item \"><a class=\"page-link\" href=\"javascript:"+script+"('").append(i).append("');\" val='").append(i).append("'>").append(i).append("</a></div>");
			else
				sb.append("<div class=\"paginate_button page-item \"><a class=\"page-link\" href=\"javascript:"+script+"('").append(i).append("');\" val='").append(i).append("'>").append(i).append("</a></div>");

			if( i == pageCnt )
				break;
		}

		long page = (rangeCnt+1L) * pageRange + 1L;
		if( page > pageCnt )	page = pageCnt;


		if( curPage >= 1 && totalCnt > 1 && curPage != pageCnt ) {
			sb.append("<div class=\"numPN over right paginate_button page-item \">");
			sb.append("<a href=\"javascript:"+script+"('").append(curPage+1).append("');\" class=\"page-link\" val='1'>&#62;</a></div>");
		}

		if( totalPage < pageCnt ) {
			sb.append("<div class=\"numPN paginate_button page-item \">");
			sb.append("<a href=\"javascript:"+script+"('").append(totalPage).append("');\" class=\"page-link\" val='1'>&#171;</a></div>");
		}

		sb.append("\n</ul>");
		return sb.toString();
	}



}

