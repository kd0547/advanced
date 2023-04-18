package com.advanced.app.V3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advanced.trace.TraceStatus;
import com.advanced.trace.hellotrace.HelloTraceV1;
import com.advanced.trace.hellotrace.HelloTraceV2;
import com.advanced.trace.logtrace.LogTrace;

import lombok.RequiredArgsConstructor;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class OrderControllerV3 {
	
	private final OrderServiceV3 orderService;
	private final LogTrace trace;
	
	@GetMapping("/v3/request")
	public String request(String itemId) {
		
		TraceStatus status = null;
		try {
			status = trace.begin("OrderController.request()");
			
			orderService.orderItem(itemId);
			
			trace.end(status);
			return "ok";
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;//예외를 꼭 다시 던져주어야 한다.
		}
		
	}
}
