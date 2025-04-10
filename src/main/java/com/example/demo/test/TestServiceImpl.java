package com.example.demo.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService{
	
	/*
	 * インターフェース名で宣言する理由
	   実装クラスはインターフェースの抽象メソッドを実装しないとコンパイルエラーが発生する
	   上記の仕様からインターフェースの抽象メソッドは必ずどのクラスにも存在することが保証される
	   新規に実装クラスを作成しても修正の必要のないインターフェースを呼び出す(ポリモーフィズム)
	*/
	private final TestDao dao;
	
	@Autowired 
	public TestServiceImpl(TestDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<Test> getAll() {
		return dao.getAll();
	}
}
