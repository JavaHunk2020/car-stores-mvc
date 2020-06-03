package com.cubic.it.cars.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cubic.it.cars.entity.CarEntity;

//Model /
@Controller
public class CarController {
	
	@Autowired
	@Qualifier("pkdataSource")
	private DataSource dataSource;
	
  //	/loadImage ? rid =122
	@GetMapping("/loadImage")
	public void loadImage(@RequestParam int rid,HttpServletResponse response) throws IOException {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		String sql="select  photo from cars_tbl where cid = "+rid;
		byte[] imaga=jdbcTemplate.queryForObject(sql, byte[].class);
		response.setContentType("img/png");
		ServletOutputStream outputStream=response.getOutputStream();
		if(imaga!=null) {
			//writting image into response body
			outputStream.write(imaga);
		}else {
			outputStream.write(new byte[] {});
		}
		outputStream.flush(); //pusing the response
	}
	
	
	
	@GetMapping("/cars")
	public String showCar(Model  model) {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		String sql="select  cid as id,color,model,price,mfg,description,doe from cars_tbl";
		List<CarEntity> carLista=jdbcTemplate.query(sql, new BeanPropertyRowMapper(CarEntity.class));
		model.addAttribute("carLista",carLista);
		return "cars";
	}
	
	
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
		
		Object[] data= {carEntity.getColor(),carEntity.getModel(),carEntity.getPrice(),carEntity.getMfg(),
				lobValue,carEntity.getDescription(),new Timestamp(new Date().getTime())};
		String sql="insert into cars_tbl(color,model,price,mfg,photo,description,doe) values(?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,data,
				new int[] {Types.VARCHAR, Types.INTEGER,Types.DOUBLE,
						Types.VARCHAR,Types.BLOB,Types.VARCHAR,Types.TIMESTAMP});

		model.addAttribute("message","car is uploaded successfully@!!!!");
		return "add-car";
	}
	
	
	
	

}
