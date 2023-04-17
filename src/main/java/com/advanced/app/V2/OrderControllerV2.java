package com.advanced.app.V2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advanced.trace.TraceStatus;
import com.advanced.trace.hellotrace.HelloTraceV1;
import com.advanced.trace.hellotrace.HelloTraceV2;

import lombok.RequiredArgsConstructor;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class OrderControllerV2 {
	
	private final OrderServiceV2 orderService;
	private final HelloTraceV2 trace;
	
	@GetMapping("/v2/request")
	public String request(String itemId) {
		/*
		 	//원했던 결과 
			status = trace.begin("OrderController.request()");
			orderService.orderItem(itemId);
			trace.end(status);
			return "ok";
		 */
		
		/*
		 * 실제 결과
		 */
		TraceStatus status = null;
		try {
			status = trace.begin("OrderController.request()");
			
			orderService.orderItem(status.getTraceId(),itemId);
			
			trace.end(status);
			return "ok";
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;//예외를 꼭 다시 던져주어야 한다.
		}
		
	}
}
