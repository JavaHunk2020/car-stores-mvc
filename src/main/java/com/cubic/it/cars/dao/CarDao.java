package com.cubic.it.cars.dao;

import java.io.IOException;
import java.util.List;

import com.cubic.it.cars.entity.CarEntity;

public interface CarDao {
	byte[] loadImage(int rid);
	List<CarEntity> findAll();
	void updatePhoto(CarEntity carEntity) throws IOException;
	void save(CarEntity carEntity) throws IOException;
	int findAllCount();
	List<CarEntity> findByPage(int startPage, int pageSize);
}
