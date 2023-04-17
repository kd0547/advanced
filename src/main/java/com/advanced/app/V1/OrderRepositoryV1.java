package com.advanced.app.V1;

import org.springframework.stereotype.Repository;

import com.advanced.trace.TraceStatus;
import com.advanced.trace.hellotrace.HelloTraceV1;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {
	
	private final HelloTraceV1 trace;
	
	
	/*
	 * 상품을 저장하는 로직
	 * */
	public void save(String itemId) {
		//문제가 있으면 Exception 발생
		
		TraceStatus status = null;
		try {
			status = trace.begin("OrderRepository.save()");
			
			if(itemId.equals("ex")) {
				throw new IllegalStateException("예외 발생");
				
			}
			sleep(1000);
			trace.end(status);
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;//예외를 꼭 다시 던져주어야 한다.
		}
	}
	
	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
