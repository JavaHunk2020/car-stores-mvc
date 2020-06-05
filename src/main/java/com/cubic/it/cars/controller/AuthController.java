package com.cubic.it.cars.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cubic.it.cars.entity.UserEntity;
import com.cubic.it.utils.SQLConnUtil;


/**
 * 
 * @author Lutfullah
 *
 */
@Controller
public class AuthController {
	
	public AuthController() {
		System.out.println("(@*@&@&Hahahahahahahahha");
	}
	
	
	@GetMapping({"/dasha"})
	public String dashaBoard() {
		return "dasha";
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		//killing the session
		session.invalidate();
		return "login";
	}
	
	@GetMapping({"/auth","/"})
	public String authPage() {
		return "login";
	}
	
	@PostMapping("/auth")
	public String authPagePost(HttpServletRequest req,HttpSession session) {
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		UserEntity entity=null;
		String next="review";
		try {
			Connection connection=SQLConnUtil.getConnection();
			String sql="select uid,userid,password,name,email,mobile,salutation,image,createdate,role from users_tbl where userid=? and password= ?";
			//compiling the query
			PreparedStatement pstmt=connection.prepareStatement(sql);
			pstmt.setString(1,username);
			pstmt.setString(2,password);
			ResultSet rs=pstmt.executeQuery();
			//uid,userid,password,name,email,mobile,salutation,image,createdate 
			if(rs.next())  {
				   entity=new UserEntity();
				   int uid=rs.getInt(1);
				   String userid=rs.getString(2);
				   String dpassword=rs.getString(3);
				   String name=rs.getString(4);
				   String email=rs.getString(5);
				   BigDecimal mobile=rs.getBigDecimal(6);
				   String salutation=rs.getString(7);
				   String image=rs.getString(8);
				   Timestamp doe=rs.getTimestamp(9);
				   String role=rs.getString(10);
				   
				   entity.setUid(uid);
				   entity.setUserid(userid);
				   entity.setPassword(dpassword);
				   entity.setName(name);
				   entity.setEmail(email);
				   entity.setMobile(mobile.longValue()+"");
				   entity.setImage(image);
				   entity.setSalutation(salutation);
				   entity.setCreateDate(doe);
				   entity.setRole(role);
				   req.setAttribute("pdata", entity);
				   //Why I am adding this data into session
				   session.setAttribute("userData", entity);
				   //Here we are adding this data to show on review page
				   //Creatign session scope for the user
				   //CTR-SHIFT+O
			}	 else {
				 req.setAttribute("message", "Sorry usename and password are not correct!");
				next="login";
			}
		}catch (Exception e) {
				e.printStackTrace();
		}
		return next;
	}

}
