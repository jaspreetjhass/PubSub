package com.example.demo.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.models.B2BServiceRequest;
import com.example.demo.models.B2BServiceResponse;

@FeignClient(name = "B2BServiceClient", url = "${b2bservice.baseUrl}",configuration = CommonFeignConfig.class)
public interface B2BServiceClient {

	@PostMapping(value = "/b2bservice/fetchMtndetails")
	public B2BServiceResponse getServiceDetails(@RequestBody B2BServiceRequest b2bServiceRequest);

}
