package com.example.demo.feign.clients;

import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.exceptions.AdminToolException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class CustomErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder errorDecoder = new Default();
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomErrorDecoder.class);

	@Override
	public Exception decode(String methodKey, Response response) {
		LOGGER.trace("Enter into ErrorDecoder.decode method with paramters : {},{}", methodKey, response);
		String message = null;
		Reader reader = null;

		switch (response.status()) {

		case 400:
			LOGGER.info("bad request");
			break;
		case 404:
			LOGGER.info("not found");
			break;
		case 500:
			LOGGER.info("internal server error");
			break;
		default:
			break;

		}

		try {
			// Easy way to read the stream and get a String object
			String result = CharStreams.toString(reader);
			// use a Jackson ObjectMapper to convert the Json String into a
			// Pojo
			ObjectMapper mapper = new ObjectMapper();
			// just in case you missed an attribute in the Pojo
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			// init the Pojo
			ExceptionMessage exceptionMessage = mapper.readValue(result, ExceptionMessage.class);

			message = exceptionMessage.message;
			LOGGER.trace("Error is : {}", message);
		} catch (IOException e) {

			throw new AdminToolException("exception occur while decoding feign client error");
		} finally {

			try {

				if (reader != null)
					reader.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		LOGGER.trace("Exit from ErrorDecoder.decode method with output : {}", errorDecoder.decode(methodKey, response));
		return errorDecoder.decode(methodKey, response);
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class ExceptionMessage {

		private String timestamp;
		private int status;
		private String error;
		private String message;
		private String path;

	}

}
