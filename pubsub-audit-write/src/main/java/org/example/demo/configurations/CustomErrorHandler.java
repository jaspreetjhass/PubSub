package org.example.demo.configurations;

import org.springframework.util.ErrorHandler;

public class CustomErrorHandler implements ErrorHandler {

	@Override
	public void handleError(Throwable t) {

		System.out.println("enter into exception handler");

	}

}
