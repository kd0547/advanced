package com.advanced.app.V0;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {
	/*
	 * 상품을 저장하는 로직
	 * */
	public void save(String itemId) {
		//문제가 있으면 Exception 발생
		if(itemId.equals("ex")) {
			throw new IllegalStateException("예외 발생");
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
