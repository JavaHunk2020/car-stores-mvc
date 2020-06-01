package com.cubic.it.cars.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cubic.it.cars.entity.CarEntity;

//Model /
@Controller
public class CarController {
	
	@GetMapping("/addCar")
	public String addCarPage() {
		return "add-car";
	}
	
	@PostMapping("/addCar")
	public String createCar(HttpServletRequest req) {
		String color=req.getParameter("color");
		String model=req.getParameter("model");
		String price=req.getParameter("price");
		String mfg=req.getParameter("mfg");
		String photo=req.getParameter("photo");
		CarEntity carEntity=new CarEntity();
		carEntity.setColor(color);
		carEntity.setDescription("NA");
		carEntity.setDoe(new Timestamp(new Date().getTime()));
		carEntity.setDom(new Timestamp(new Date().getTime()));
		carEntity.setMfg(mfg);
		carEntity.setPrice(Double.parseDouble(price));
		carEntity.setModel(Integer.parseInt(model));
		carEntity.setPhoto(new byte[] {});
		System.out.println(carEntity);
		req.setAttribute("message","car is uploaded successfully@!!!!");
		return "add-car";
	}
	
	
	
	

}
