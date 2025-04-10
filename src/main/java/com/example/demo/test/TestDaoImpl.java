package com.example.demo.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestDaoImpl implements TestDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public TestDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	

	@Override
	public List<Test> getAll() {
		String sql = "SELECT id, name, email, contents, created FROM test";
		List<Map<String,Object>> resultList = jdbcTemplate.queryForList(sql);
		List<Test> list = new ArrayList<Test>();
		for (Map<String,Object> result :resultList) {
			Test inquiry = new Test();
			inquiry.setId((int)result.get("id"));
			inquiry.setName((String)result.get("name"));
			inquiry.setEmail((String)result.get("email"));
			inquiry.setContents((String)result.get("contents"));
			inquiry.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			list.add(inquiry);
		}
		
		return list;
	}

}
