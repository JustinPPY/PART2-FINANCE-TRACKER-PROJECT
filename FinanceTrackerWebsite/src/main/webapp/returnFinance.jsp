<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link
	href="https://fonts.googleapis.com/css?family=Raleway:100,200,400,500,600"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">


<title>Finance</title>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-secondary navbar-dark">
		<div class="container-fluid">
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a class="nav-link" routerLink="/home/employee"
						routerLinkActive="active" hidden>Employee</a></li>

				</ul>
				<div class="navbar-nav ml-auto">
					<div class="collapse navbar-collapse" id="navbarNavDarkDropdown">
						<div class="btn-group">
							<a class="nav-link dropdown-toggle" name="profile"
								href="javascript:void(0)" role="button" data-toggle="dropdown">
								<b style="padding-left: 5px">${name} ${surname}</b>

							</a>

							<ul class="dropdown-menu dropdown-menu-right">
								<li><a class="dropdown-item"
									href="../ProfileServlet/edit?iduser=<c:out value='${listFinance.iduser}'/>">Profile</a>

								</li>
								<li><div class="dropdown-divider"></div></li>
								<li>
									<form>
										<a name="logout" class="dropdown-item" href="../LoginRegisterServlet/logout">Logout </a>

									</form>

								</li>

							</ul>
						</div>


					</div>
				</div>
			</div>
		</div>
	</nav>
	<div class="main-part">
		<div class="cpanel">
			<div class="icon-part">
				<i class="fa fa-money" aria-hidden="true"></i><br> <small>Monthly
					Income</small>
				<p>
					$
					<c:out value="${listFinance.income}" />
				</p>
			</div>
		</div>
		<div class="cpanel cpanel-green">
			<div class="icon-part">
				<i class="fa fa-percent" aria-hidden="true"></i><br> <small>
					Percentage of income to savings</small>
				<p>
					<c:out value="${listFinance.saving}%" />
				</p>
			</div>
		</div>
		<div class="cpanel cpanel-orange">
			<div class="icon-part">
				<i class="fa fa-university" aria-hidden="true"></i> <br> <small>Ideal
					Monthly Saving</small>
				<p>
					$
					<c:out value="${listFinance.saving/100 * listFinance.income}" />
				</p>
			</div>
		</div>
	</div>
	<div class="row">
		<div style="margin: auto; padding-bottom: 10px; padding-top: 20px">
			<a class="btn btn-success"
				href="/FinanceTrackerWebsite/ReturnFinanceServlet/edit?id=<c:out value='${listFinance.id}'/>"
				class="btn btnsuccess">Edit Finance Details</a>
		</div>
	</div>


</body>
<div class="row" style="padding-bottom: 10px; padding-top: 20px">
	<div class="container">
		<h3 class="text-center">Expenditure</h3>


		<br>




		<div style="padding-bottom: 10px; padding-top: 10px">

			<a class="btn btn-success"
				href="/FinanceTrackerWebsite/Expenditure/add?idfinance=<c:out value='${listFinance.id}'/>"
				class="btn btnsuccess">Add Expenditure</a>
		</div>
		<table class="table">
			<thead style="color: white; background-color: #3B619C">
				<tr>

					<th>Type</th>
					<th>Cost</th>
					<th>Date</th>
					<th>Action</th>

				</tr>
			</thead>
			<!-- Pass in the list of users receive via the Servlet’s response to a loop-->
			<tbody>
				<c:set var="total" value="${0}" />

				<c:forEach var="expenditure" items="${expenditure}">
					<!-- For each user in the database, display their information accordingly -->
					<tr>

						<td><c:out value="${expenditure.type}" /></td>
						<td><c:out value="${expenditure.amount}" /></td>
						<fmt:parseDate pattern="yyyy-MM-dd" value="${expenditure.date}"
							var="parsedDate" />
						<td><fmt:formatDate value="${parsedDate}"
								pattern="yyyy-MM-dd" /></td>
						<!-- For each user in the database, Edit/Delete buttons which invokes the edit/delete functions -->
						<td><a
							href="editexpenditure?id=<c:out value='${expenditure.id}'/>">Edit</a>
							<a href="deleteexpenditure?id=<c:out value='${expenditure.id}'/>">Delete</a>
						</td>
						<c:set var="total" value="${total + expenditure.amount}" />
					</tr>
				</c:forEach>
			</tbody>
			<tfoot style="color: white; background-color: #EABC36">
				<tr>
					<td>Sum</td>
					<td colspan=3>${total}</td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
</body>
<style type="text/css">
body {
	background: #eee;
	font-family: 'Raleway', sans-serif;
}

.main-part {
	width: 80%;
	margin: 0 auto;
	text-align: center;
	padding: 0px 5px;
}

.cpanel {
	width: 32%;
	display: inline-block;
	background-color: #34495E;
	color: #fff;
	margin-top: 50px;
}

.icon-part i {
	font-size: 30px;
	padding: 10px;
	border: 1px solid #fff;
	border-radius: 50%;
	margin-top: -25px;
	margin-bottom: 10px;
	background-color: #34495E;
}

.icon-part p {
	margin: 0px;
	font-size: 20px;
	padding-bottom: 10px;
}

.card-content-part {
	background-color: #2F4254;
	padding: 5px 0px;
}

.cpanel .card-content-part:hover {
	background-color: #5a5a5a;
	cursor: pointer;
}

.card-content-part a {
	color: #fff;
	text-decoration: none;
}

.cpanel-green .icon-part, .cpanel-green .icon-part i {
	background-color: #16A085;
}

.cpanel-green .card-content-part {
	background-color: #149077;
}

.cpanel-orange .icon-part, .cpanel-orange .icon-part i {
	background-color: #F39C12;
}

.cpanel-orange .card-content-part {
	background-color: #DA8C10;
}

.cpanel-blue .icon-part, .cpanel-blue .icon-part i {
	background-color: #2980B9;
}

.cpanel-blue .card-content-part {
	background-color: #2573A6;
}

.cpanel-red .icon-part, .cpanel-red .icon-part i {
	background-color: #E74C3C;
}

.cpanel-red .card-content-part {
	background-color: #CF4436;
}

.cpanel-skyblue .icon-part, .cpanel-skyblue .icon-part i {
	background-color: #8E44AD;
}

.cpanel-skyblue .card-content-part {
	background-color: #803D9B;
}
</style>
</html>