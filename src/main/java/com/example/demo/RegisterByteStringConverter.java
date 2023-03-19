package com.example.demo;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import com.google.protobuf.ByteString;

@Component
@ConfigurationPropertiesBinding
public class RegisterByteStringConverter {
	
	public static final Logger logger = LoggerFactory.getLogger(RegisterByteStringConverter.class);

	public RegisterByteStringConverter() {
		((DefaultConversionService) DefaultConversionService.getSharedInstance())
				.addConverter(new ByteStringConverter());
	}

}

class ByteStringConverter implements Converter<ByteString, String> {
	@Override
	public String convert(ByteString source) {
		RegisterByteStringConverter.logger.debug("Entering ByteString Conversion {}", source.toString(StandardCharsets.UTF_8));
		return source.toString(StandardCharsets.UTF_8);
	}
}
