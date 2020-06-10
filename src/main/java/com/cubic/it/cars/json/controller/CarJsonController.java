package com.cubic.it.cars.json.controller;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cubic.it.cars.dao.CarDao;
import com.cubic.it.cars.entity.CarEntity;


@RestController
@RequestMapping("/v3")
public class CarJsonController {
	
	@Autowired
	private CarDao carDao;
	
	@Autowired
	@Qualifier("pkdataSource")
	private DataSource dataSource;
	
	//http://localhost:9999/car-stores-mvc/v3/cars/1
	@DeleteMapping("/cars/{cid}")
	public List<CarEntity>  deleteCar(@PathVariable int cid) {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		String sql="delete from cars_tbl where cid = "+cid;
		jdbcTemplate.update(sql);
		
		//show all remaining cars after deleting it
		String fsql="select  cid as id,color,model,price,mfg,description,doe from cars_tbl";
		List<CarEntity> carLista=jdbcTemplate.query(fsql, new BeanPropertyRowMapper(CarEntity.class));
		return carLista;
		
	}
	
   //	/http://localhost:9999/car-stores-mvc/v3/cars/1
	@GetMapping("/cars/{cid}")
	public CarEntity  showCar(@PathVariable int cid) {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		String sql="select  cid as id,color,model,price,mfg,description,doe from cars_tbl where cid = "+cid;
		CarEntity carEntity=(CarEntity)jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(CarEntity.class));
		return carEntity;
	}
	
	//@RequestBody - it reads json data from request body and converts into Java Object
	//@ModelAttribute - it reads data from form parameter and converts into Java Object
	@PostMapping("/cars")
	public List<CarEntity> createCar(@RequestBody CarEntity carEntity) throws IOException {
		carDao.save(carEntity);
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		String sql="select  cid as id,color,model,price,mfg,description,doe from cars_tbl";
		List<CarEntity> carLista=jdbcTemplate.query(sql, new BeanPropertyRowMapper(CarEntity.class));
		return carLista;
	}
	
	
	@GetMapping("/cars")
	public List<CarEntity>  showCar(Model  model) {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		String sql="select  cid as id,color,model,price,mfg,description,doe from cars_tbl";
		List<CarEntity> carLista=jdbcTemplate.query(sql, new BeanPropertyRowMapper(CarEntity.class));
		return carLista;
	}
}
