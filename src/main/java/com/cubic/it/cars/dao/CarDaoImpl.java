package com.cubic.it.cars.dao;

import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cubic.it.cars.entity.CarEntity;
import com.cubic.it.cars.entity.UserEntity;

@Repository
public class CarDaoImpl  implements CarDao {
	
	@Autowired
	@Qualifier("pkdataSource")
	private DataSource dataSource;
	
	@Override
	public UserEntity validateUser(String username,String password) {
			JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
			String sql="select uid,userid,password,name,email,mobile,salutation,image,createdate,role from users_tbl where userid=? and password= ?";	
			List<UserEntity> list=jdbcTemplate.query(sql, new Object[] {username,password},new BeanPropertyRowMapper(UserEntity.class));
			if(list.size()==1) {
				return list.get(0);
			}else {
				return null;
			}
	}
	
	@Override
	public byte[] loadImage(int rid) {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		String sql="select  photo from cars_tbl where cid = "+rid;
		byte[] imaga=jdbcTemplate.queryForObject(sql, byte[].class);
		return imaga;
	}
	
	
	@Override
	public int findAllCount() {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		String sql="select  count(*) from cars_tbl";
		int count=jdbcTemplate.queryForObject(sql, Integer.class);
		return count;
	}
	
	
	@Override
	public List<CarEntity> findByPage(int startPage,int pageSize) { //1 , 4   = 1,2,3,4
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
				//0 ,5
				//5,5
				//10,5
 	   String sql = "select  cid as id,color,model,price,mfg,description,doe from cars_tbl order by cid desc limit "+(startPage-1)+","+pageSize;
		List<CarEntity> carLista=jdbcTemplate.query(sql, new BeanPropertyRowMapper(CarEntity.class));
		return carLista;
	}
	
	@Override
	public List<CarEntity> findAll() {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		String sql="select  cid as id,color,model,price,mfg,description,doe from cars_tbl";
		List<CarEntity> carLista=jdbcTemplate.query(sql, new BeanPropertyRowMapper(CarEntity.class));
		return carLista;
	}
	
	@Override
	public void updatePhoto(CarEntity carEntity) throws IOException {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		
		if(carEntity.getPhoto()!=null && carEntity.getPhoto().getBytes().length>0) {
			String sql="update  cars_tbl set photo=? where cid=?";
			LobHandler lobHandler = new DefaultLobHandler();
			SqlLobValue lobValue=new SqlLobValue(carEntity.getPhoto().getBytes(),lobHandler);
			jdbcTemplate.update(sql,new Object[] {lobValue,carEntity.getId()},new int[] {Types.BLOB,
					Types.INTEGER});
		}
		
		String sql2="insert into cars_price_tbl(cid,price,doe) values(?,?,?)";
		jdbcTemplate.update(sql2,new Object[] {carEntity.getId(),carEntity.getPrice()+"$",new Timestamp(new Date().getTime())});
	
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

		String sqlmaxcid="select max(cid) from cars_tbl";
		int cid=jdbcTemplate.queryForObject(sqlmaxcid, Integer.class);
		
		String sql2="insert into cars_price_tbl(cid,price,doe) values(?,?,?)";
		jdbcTemplate.update(sql2,new Object[] {cid,carEntity.getPrice()+"$",new Timestamp(new Date().getTime())});
		
	}
	

}
