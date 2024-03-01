<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<title>Curso JSP</title>
	<style>
		h5 {
			color: #842029;
			background-color: #f8d7da;
			border-color: #f5c2c7;
			text-align: center;
		}
	</style>
</head>
<body>
	<section class="vh-100" style="background-color: #508bfc;">
		<div class="container py-5 h-100">
			<div class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-12 col-md-8 col-lg-6 col-xl-5">
					<div class="card shadow-2-strong" style="border-radius: 1rem;">
						<div class="card-body p-5">

							<h3 class="mb-5 text-center">Bem vindo ao curso de JSP</h3>

							<form action= "ServletLogin" method="post" class="needs-validation" novalidate>
								<input type="hidden" value="<%= request.getParameter("url") %>" name="url">
								<div class="form-outline mb-4">
									<label class="form-label" for="typeEmailX-2">Login *</label>
									<input class="form-control form-control-lg" id="typeEmailX-2" name="login" type=text required="required">
									<div class="invalid-feedback">
										*Campo Obrigatório
									</div>
								</div>
								<div class="form-outline mb-4">
									<label class="form-label" for="typePasswordX-2" >Senha *</label>
									<input type="password" id="typePasswordX-2" class="form-control form-control-lg" name="senha" required="required">
									<div class="invalid-feedback">
										*Campo Obrigatório
									</div>
								</div>
								<h5>${msg}</h5>
								<button type="submit" class="btn btn-primary btn-lg">Entrar</button>
							</form>
							</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
	<script type="text/javascript">
		// Fetch all the forms we want to apply custom Bootstrap validation styles to
		(function () {
			'use strict'

			// Fetch all the forms we want to apply custom Bootstrap validation styles to
			var forms = document.querySelectorAll('.needs-validation')

			// Loop over them and prevent submission
			Array.prototype.slice.call(forms)
					.forEach(function (form) {
						form.addEventListener('submit', function (event) {
							if (!form.checkValidity()) {
								event.preventDefault()
								event.stopPropagation()
							}

							form.classList.add('was-validated')
						}, false)
					})
		})()

	</script>
</body>
</html>