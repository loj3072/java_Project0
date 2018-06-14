package edu.skku.pms;

public class RecordNotFoundException extends Exception {
	// 구현 하세요
	public RecordNotFoundException() {
		super("데이터가 없습니다");
	}	
}
