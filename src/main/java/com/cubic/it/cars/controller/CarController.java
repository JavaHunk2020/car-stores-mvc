package com.cubic.it.cars.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cubic.it.cars.entity.CarEntity;

//Model /
@Controller
public class CarController {
	
	@Autowired
	@Qualifier("pkdataSource")
	private DataSource dataSource;
	
	@GetMapping("/addCar")
	public String addCarPage() {
		return "add-car";
	}
	
	@PostMapping("/addCar")
	public String createCar(@ModelAttribute CarEntity carEntity,Model  model) throws IOException {
		byte[] image=carEntity.getPhoto().getBytes();
		carEntity.setImage(image);
		carEntity.setFilename(carEntity.getPhoto().getName());
		carEntity.setDoe(new Timestamp(new Date().getTime()));
		carEntity.setDom(new Timestamp(new Date().getTime()));
		System.out.println(carEntity);
		
		LobHandler lobHandler = new DefaultLobHandler();
		SqlLobValue lobValue=new SqlLobValue(carEntity.getImage(),lobHandler);
		
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		String sql="insert into cars_tbl(color,model,price,mfg,photo,description,doe) values(?,?,?,?,?,?,?)";
		Object[] data= {carEntity.getColor(),carEntity.getModel(),carEntity.getPrice(),carEntity.getMfg(),
				lobValue,carEntity.getDescription(),new Timestamp(new Date().getTime())};
		
		jdbcTemplate.update(sql,data,new int[] {Types.VARCHAR, Types.INTEGER,Types.DOUBLE,Types.VARCHAR,Types.BLOB,Types.VARCHAR,Types.TIMESTAMP});

		model.addAttribute("message","car is uploaded successfully@!!!!");
		return "add-car";
	}
	

}
