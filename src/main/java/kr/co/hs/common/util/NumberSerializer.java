package kr.co.hs.common.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class NumberSerializer extends JsonSerializer<BigDecimal>{

	@Override
	public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		
		NumberFormat nf = NumberFormat.getInstance();
		
		DecimalFormat df = (DecimalFormat)nf;
		df.applyPattern("#.00");
		jgen.writeString(  df.format(value.setScale(3, BigDecimal.ROUND_HALF_UP)) );
		
	}
}
