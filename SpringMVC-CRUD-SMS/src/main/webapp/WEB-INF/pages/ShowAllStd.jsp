<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student List</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha384-G9x4wD0rTCjx7lf6xbo8aHovfnAg+3+XrFgcb/lu1z7fm0avss+2ta3tsWSO9n1t"
	crossorigin="anonymous"></script>


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>


<!-- jQuery -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<!-- Bootstrap JS -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


</head>
<body>
	<div class="modal fade" id="deletePopup" tabindex="-1"
		aria-labelledby="deletePopupLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="deletePopupLabel">Deleting
						Student...</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">Sure to delete?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancel</button>
					<form action="delete" method="post" name="form">
						<input type="hidden" name="idtodelete" value="">
						<button type="button" class="btn btn-primary"
							id="confirmDeleteBtn" onclick="confirmDelete()">Ok</button>
					</form>
				</div>
			</div>
		</div>
	</div>


	<c:choose>
		<c:when test="${stdid ne null and stdid ne 0}">
			<c:if test="${successmsg ne null and not empty successmsg}">
				<div class="alert alert-success" role="alert">
					<c:out value='${successmsg}' />
				</div>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="${failuremsg ne null and not empty failuremsg}">
				<div class="alert alert-danger" role="alert">
					<c:out value='${failuremsg}' />
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${updatecount ne null and updatecount ne 0}">
			<c:if test="${updated ne null and not empty updated}">
				<div class="alert alert-success" role="alert">
					<c:out value='${updated}' />
				</div>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="${notupdated ne null and not empty notupdated}">
				<div class="alert alert-danger" role="alert">
					<c:out value='${notupdated}' />
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${deletecount ne null and deletecount ne 0}">
			<c:if test="${deleted ne null and not empty deleted}">
				<div class="alert alert-success" role="alert">
					<c:out value='${deleted}' />
				</div>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="${notdeleted ne null and not empty notdeleted}">
				<div class="alert alert-danger" role="alert">
					<c:out value='${notdeleted}' />
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>


	<table
		class="table table-hover caption-top table-bordered border-secondary">
		<caption>List of Student</caption>

		<thead>
			<tr>
				<td colspan="6">
					<div class="d-flex justify-content-end">
						<a class="btn btn-primary me-2" onclick="updateStudent()" href="#"
							role="button">Update</a> <a class="btn btn-primary"
							onclick="deleteStudent()" href="#" role="button">Delete</a> <a
							class="btn btn-primary me-2" href="getpdf" role="button">Download</a>

					</div>
				</td>
			</tr>
			<tr>
				<th></th>
				<th>ID</th>
				<th>Name</th>
				<th>Email</th>
				<th>Department</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty studentList}">
					<tr>
						<td colspan="6">No data found</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr></tr>
					<c:forEach items="${studentList}" var="student">
						<tr>
							<td align="center"><input type="checkbox"
								class="student-checkbox" value="${student.id}"
								style="align: middle" onclick="handleCheckboxClick(this)"></td>
							<td>${student.id}</td>
							<td>${student.name}</td>
							<td>${student.email}</td>
							<td>${student.dept}</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</body>


<script>
	function handleCheckboxClick(clickedCheckbox) {
		var checkboxes = document.querySelectorAll('.student-checkbox');
		checkboxes.forEach(function(checkbox) {
			if (checkbox !== clickedCheckbox) {
				checkbox.checked = false;
			}
		});
	}

	function deleteStudent() {
		//var checkedCheckbox = document
		//		.querySelector('.student-checkbox:checked');
		var checkedCheckbox = $('.student-checkbox:checked');

		if (checkedCheckbox.length > 0) {
			$('#deletePopup').modal('show');

		} else {
			console.log("Please select one checkbox!");
		}
	}

	function confirmDelete() {
		var checkedCheckbox = document
				.querySelector('.student-checkbox:checked');
		if (checkedCheckbox) {
			document.form.idtodelete.value = checkedCheckbox.value;
			document.form.submit();
		}
	}

	function updateStudent() {
		var checkedCheckbox = document
				.querySelector('.student-checkbox:checked');
		if (checkedCheckbox) {
			var updateUrl = 'getStd?id=' + checkedCheckbox.value;
			window.location.href = updateUrl;
		} else {
			console.log("Please select one checkbox!");
		}
	}
</script>
</html>