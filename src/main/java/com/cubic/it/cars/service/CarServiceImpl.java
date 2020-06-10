package com.cubic.it.cars.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubic.it.cars.controller.vo.CarDTO;
import com.cubic.it.cars.controller.vo.UserDTO;
import com.cubic.it.cars.dao.CarDao;
import com.cubic.it.cars.entity.CarEntity;
import com.cubic.it.cars.entity.UserEntity;

@Service
public class CarServiceImpl  implements CarService{
	
	@Autowired
	private CarDao carDao;
	
	
	@Override
	public UserDTO validateUser(String username,String password) {
		UserEntity userEntity=carDao.validateUser(username, password);
		if(userEntity!=null) {
			UserDTO userDTO=new UserDTO();	
			BeanUtils.copyProperties(userEntity, userDTO);
			return userDTO;
		}else {
			return null;
		}
	}

	@Override
	public byte[] fetchImage(int rid) {
		return carDao.loadImage(rid);
	}

	@Override
	public List<CarDTO> findAll() {
		List<CarEntity>  list=carDao.findAll();
		List<CarDTO> carDTOs=new ArrayList<>();
		for(CarEntity entity: list) {
			CarDTO carDTO=new CarDTO();
			BeanUtils.copyProperties(entity, carDTO);
			carDTOs.add(carDTO);
		}
		return carDTOs;
	}

	@Override
	public void updatePhoto(CarDTO carDTO) throws IOException {
		CarEntity carEntity=new CarEntity();
		BeanUtils.copyProperties(carDTO, carEntity);
		carDao.updatePhoto(carEntity);
	}

	@Override
	public void save(CarDTO carDTO) throws IOException {
		CarEntity carEntity=new CarEntity();
		BeanUtils.copyProperties(carDTO, carEntity);
		carDao.save(carEntity);
	}

	@Override
	public int findCount() {
		return carDao.findAllCount();
	}

	@Override
	public List<CarDTO> findByPage(int startPage, int pageSize) {
		List<CarEntity>  list=carDao.findByPage(startPage,pageSize);
		List<CarDTO> carDTOs=new ArrayList<>();
		for(CarEntity entity: list) {
			CarDTO carDTO=new CarDTO();
			BeanUtils.copyProperties(entity, carDTO);
			carDTOs.add(carDTO);
		}
		return carDTOs;
	}

}
