package com.cubic.it.cars.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cubic.it.cars.dao.CarDao;
import com.cubic.it.cars.entity.CarEntity;

//Model /
@Controller
public class CarController {
	
	@Autowired
	@Qualifier("pkdataSource")
	private DataSource dataSource;
	
	@Autowired
	private CarDao carDao;
	
  //	/loadImage ? rid =122
	@GetMapping("/loadImage")
	public void loadImage(@RequestParam int rid,HttpServletResponse response) throws IOException {
		byte[] imaga=carDao.loadImage(rid);
		response.setContentType("img/png");
		ServletOutputStream outputStream=response.getOutputStream();
		if(imaga!=null) {
			outputStream.write(imaga);
		}else {
			outputStream.write(new byte[] {});
		}
		outputStream.flush(); //pusing the response
	}
	
	
	
	@GetMapping("/cars")
	public String showCar(Model  model) {
		List<CarEntity> carLista=carDao.findAll();
		model.addAttribute("carLista",carLista);
		return "cars";
	}
	
	
	@GetMapping("/addCar")
	public String addCarPage() {
		return "add-car";
	}
	
	@PostMapping("/updatePhoto")
	public String updatePhotoOnly(@ModelAttribute CarEntity carEntity) throws IOException {
		carDao.updatePhoto(carEntity);
		return "redirect:/cars";
	}
	
	@PostMapping("/addCar")
	public String createCar(@ModelAttribute CarEntity carEntity,Model  model) throws IOException {
		carDao.save(carEntity);
		model.addAttribute("message","car is uploaded successfully@!!!!");
		return "add-car";
	}

}
