package kr.co.hs.common.dto;

import java.util.Locale;

import kr.co.hs.common.util.MessageUtil;
import lombok.Data;

@Data
public class MsgDTO {
	private Integer code = 9;
	private String msg = null;
	private Object key = null;

	public MsgDTO() {
		this(new Locale("ko"));
	}
	
	public MsgDTO(Locale _locale) {
		msg = MessageUtil.getMessage("successfully.processed", _locale);
	}
	
	public MsgDTO(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
