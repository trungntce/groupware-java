package kr.co.hs.common.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


@Component
public class JsonDateSerializer  extends JsonSerializer<Date> {

	private static final SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
	
	@Override
	public void serialize(Date date, JsonGenerator json, SerializerProvider provider) throws IOException, JsonProcessingException {
		json.writeString( format.format(date) );
	}
	
	
}
