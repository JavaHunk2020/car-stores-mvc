package com.cubic.it.cars.controller;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cubic.it.cars.entity.CarEntity;


@RestController
public class CarJsonController {
	
	@Autowired
	@Qualifier("pkdataSource")
	private DataSource dataSource;
	
	@GetMapping("/tcars")
	public List<CarEntity>  showCar(Model  model) {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		String sql="select  cid as id,color,model,price,mfg,description,doe from cars_tbl";
		List<CarEntity> carLista=jdbcTemplate.query(sql, new BeanPropertyRowMapper(CarEntity.class));
		return carLista;
	}
}
