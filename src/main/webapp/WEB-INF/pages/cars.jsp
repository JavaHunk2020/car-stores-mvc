<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "hero" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Cars</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js">
	</script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
	
	<style>
.zoom {
  transition: transform .2s; /* Animation */
  margin: 0 auto;
  height: 120px;
}

.zoom:hover {
  transform: scale(1.5); /* (150% zoom - Note: if the zoom is too large, it will go outside of the viewport) */
}
</style>

<style type="text/css">
.semere {
	display: block;
	margin-left: auto;
	margin-right: auto;
}
</style>


</head>
<body>
	<header style="background-color: #03a9f4; height: 30px;">
	   <b>!!!!!!!!!!!!!!!!!!!!!!!!!!!</b> 
	   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

	 </header>
	<div  class="container">
		<b style="color:black;">CARs PAGE</b> 
	     <hr style="border-top: 5px solid rgba(103,58,183,1);"/>
	      <h3>${message }</h3>
		<img id="studentImage"  src="${pageContext.request.contextPath}/img/bmw-z4.png" class="semere"  style="height: 100px;display: inline;">
	<img id="studentImage"  src="${pageContext.request.contextPath}/img/bmw-z4.png" class="semere"  style="height: 100px;display: inline;">
	<img id="studentImage"  src="${pageContext.request.contextPath}/img/bmw-z4.png" class="semere"  style="height: 100px;display: inline;">
	
	 <table class="table table-bordered">
    <thead>
      <tr>
        <th>Cid</th>
        <th>Color</th>
        <th>Model</th>
        <th>Price</th>
        <th>Mfg</th>
        <th>Photo</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
    <hero:forEach  items="${carLista}"  var="item">
      <tr>
        <td>${item.id}</td>
        <td>${item.color}</td>
        <td>${item.model}</td>
          <td>${item.price}</td>
        <td>${item.mfg}</td>
        <td>
         <img src="${pageContext.request.contextPath}/loadImage?rid=${item.id}"  class="zoom" >
          <img src="${pageContext.request.contextPath}/img/edit.png"/>
        </td>
        <td>
        <img src="${pageContext.request.contextPath}/img/edit.png"/>
        <img src="${pageContext.request.contextPath}/img/delete.png" style="height: 45px;"/>
        </td>
      </tr>
      </hero:forEach>
   
    </tbody>
  </table>
	
	</div>



</body>
</html>