package com.roms.library.datatype.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

public class JsonDateTimeSerializer extends JsonSerializer<LocalDateTime> 
{

	private static final String DATETIME_FORMAT = ("yyyy-MM-dd HH:mm:ss");
		  
    @Override
    public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider provider) 
    		throws IOException {

    	String formattedDate = DateTimeFormat.forPattern(DATETIME_FORMAT).print(value);

    	generator.writeString(formattedDate);
    }
    
}
