package edu.skku.pms;

public class DuplicateException extends Exception {
	public DuplicateException(){
		super("데이터가 중복되었습니다.");
	}
}
