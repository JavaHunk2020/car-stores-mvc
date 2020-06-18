package com.cubic.it.cars.dao;

import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cubic.it.cars.entity.CarEntity;
import com.cubic.it.cars.entity.CarPriceEntity;
import com.cubic.it.cars.entity.UserEntity;


//<tx:annotation-driven proxy-target-class="true"	transaction-manager="transactionManager" />
@Repository
//below is mandatory 
@Transactional
public class CarDaoImpl  implements CarDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	private Session getSession(){
        return sessionFactory.getCurrentSession();
   }

	
	@Override
	public UserEntity validateUser(String username,String password) {
		TypedQuery<UserEntity> query=getSession().createQuery("from UserEntity pt where pt.userid=:pusername and pt.password=:ppassword"); //HQL
		query.setParameter("pusername", username);
		query.setParameter("ppassword", password);
		UserEntity  profileEntity=null;
		 try {
			 profileEntity=query.getSingleResult();
		 }catch (Exception e) {
			 //e.printStackTrace();
		}
		return profileEntity;
	}
	
	@Override
	public byte[] loadImage(int rid) {
		CarEntity carEntity=this.getSession().get(CarEntity.class, rid);
		return carEntity.getImage();
	}
	
	
	@Override
	public int findAllCount() {
		Query query=this.getSession().createQuery("from CarEntity");
		List<CarEntity> listCars= query.list();
		return listCars.size();
	}
	
	
	@Override
	public List<CarEntity> findByPage(int startPage,int pageSize) { //1 , 4   = 1,2,3,4
	/*	JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
				//0 ,5
				//5,5
				//10,5
 	   String sql = "select  cid as id,color,model,price,mfg,description,doe from cars_tbl order by cid desc limit "+(startPage-1)+","+pageSize;
		List<CarEntity> carLista=jdbcTemplate.query(sql, new BeanPropertyRowMapper(CarEntity.class));*/
		return null;
	}
	
	@Override
	public List<CarEntity> findAll() {
		Query query=this.getSession().createQuery("from CarEntity");
		return  query.list();
	}
	
	@Override
	public void updatePhoto(CarEntity carEntity) throws IOException {
		
		if(carEntity.getPhoto()!=null && carEntity.getPhoto().getBytes().length>0) {
			CarEntity scarEntity=this.getSession().get(CarEntity.class,carEntity.getId());
			scarEntity.setImage(carEntity.getPhoto().getBytes());
		}
		
		CarPriceEntity carPriceEntity=new CarPriceEntity();
		carPriceEntity.setCid(carEntity.getId());
		carPriceEntity.setPrice(carEntity.getPrice()+"$");
		carPriceEntity.setDoe(new Timestamp(new Date().getTime()));
		this.getSession().save(carPriceEntity);
	
	}
	
	@Override
	@Transactional
	public void save(CarEntity carEntity) throws IOException {
		
		byte[] image=null;
		if(carEntity.getPhoto()!=null) {
		     image=carEntity.getPhoto().getBytes();
		     carEntity.setFilename(carEntity.getPhoto().getName());
		}
		carEntity.setImage(image);
		carEntity.setDoe(new Timestamp(new Date().getTime()));
		carEntity.setDom(new Timestamp(new Date().getTime()));
		int currentPrimary=(Integer)this.getSession().save(carEntity);
		
		CarPriceEntity carPriceEntity=new CarPriceEntity();
		carPriceEntity.setCid(currentPrimary);
		carPriceEntity.setPrice(carEntity.getPrice()+"$");
		carPriceEntity.setDoe(new Timestamp(new Date().getTime()));
		this.getSession().save(carPriceEntity);
	}
	

}
