package com.advanced.trace.hellotrace;

import org.springframework.stereotype.Component;

import com.advanced.trace.TraceId;
import com.advanced.trace.TraceStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HelloTraceV2 {
	private static final String START_PREFIX = "-->";
	private static final String COMPLETE_PREFIX = "<--";
	private static final String EX_PREFIX = "<X-";
	
	public TraceStatus begin(String message) {
		TraceId traceId = new TraceId();
		Long StartTimeMs = System.currentTimeMillis();
		
		log.info("[{}] {}{}",traceId.getId(),addSpace(START_PREFIX,traceId.getLevel()),message);
		//로그 출력
		return new TraceStatus(traceId, StartTimeMs, message);
	}
	//V2에 추가
	public TraceStatus beginSync(TraceId beforeTraceId,String message) {
		//TraceId traceId = new TraceId();
		TraceId nextId = beforeTraceId.createNextId();
		
		Long StartTimeMs = System.currentTimeMillis();
		
		log.info("[{}] {}{}",nextId.getId(),addSpace(START_PREFIX,nextId.getLevel()),message);
		//로그 출력
		return new TraceStatus(nextId, StartTimeMs, message);
	}
	

	//정상 처리 됐을 때 
	public void end(TraceStatus status) {
		complete(status,null);
	}
	
	//예외가 발생했을 때 
	public void exception(TraceStatus status, Exception exception) {
		complete(status,exception);
	}
	
	private void complete(TraceStatus status,Exception e) {
		Long stopTimeMs = System.currentTimeMillis();
		Long resultTimeMs = stopTimeMs - status.getStartTimeMs();
		
		TraceId traceId = status.getTraceId();
		
		if(e == null) {
			log.info("[{}] {}{} time={}ms", 
					traceId.getId(),
					addSpace(COMPLETE_PREFIX, traceId.getLevel()), 
					status.getMessage(), 
					resultTimeMs);
		} else {
			log.info("[{}] {}{} time={}ms ex={}", 
					traceId.getId(),
					addSpace(EX_PREFIX,traceId.getLevel()), 
					status.getMessage(), 
					resultTimeMs,
					e.toString());
		}
		
	}
	
	
	private Object addSpace(String prefix, int level) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < level; i++) {
			sb.append((i == level - 1) ? "|"+prefix : "|	");
		}
		
		return sb.toString();
	}
}
