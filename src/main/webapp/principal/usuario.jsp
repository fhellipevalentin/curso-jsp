<%@ page import="java.util.Objects" %>
<%@ page import="model.ModelLogin" %><%--
  Created by IntelliJ IDEA.
  User: fhell
  Date: 12/03/2024
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>
	<!-- Pre-loader start -->
	<jsp:include page="theme-loader.jsp"></jsp:include>
	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">
			<jsp:include page="navbar.jsp"></jsp:include>
			<jsp:include page="navbar-main_menu.jsp"></jsp:include>


			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">
					<div class="pcoded-content">
						<!-- Page-header start -->
						<jsp:include page="page-header.jsp"></jsp:include>
						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">
										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h4 class="sub-title">Cad. Usuário</h4>

														<form class="form-material"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post" id="formUser">

															<input type="hidden" name="acao" id="acao" value="">

															<div class="form-group form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" readonly="readonly"
																	value="${modelLogin.id}"> <span
																	class="form-bar"></span> <label class="float-label">ID:</label>
															</div>

															<div class="form-group form-static-label">
																<input type="text" name="nome" id="nome"
																	class="form-control" required="required"
																	value="${modelLogin.nome}"> 
																<span class="form-bar"></span> <label 
																	class="float-label">Nome:</label>
															</div>

															<div class="form-group form-static-label">
																<input type="email" name="email" id="email"
																	class="form-control" required="required"
																	autocomplete="false" value="${modelLogin.email}">
																<span class="form-bar"></span> <label
																	class="float-label">E-mail:</label>
															</div>
															<div class="form-group form-static-label">
																<select class="form-control" name="perfil">
																	<option value="" >Selecione..</option>
																	<option value="ADMIN" <%

																		ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");

																		if (modelLogin != null && modelLogin.getPerfil().equals("ADMIN")) {
																		out.print(" ");
																			out.print("selected=\"selected\"");
																		out.print(" ");
																	} %> >Admin</option>
																	<option value="SECRETARIA" <%

																		if (modelLogin != null && modelLogin.getPerfil().equals("SECRETARIA")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																		out.print(" ");
																	} %> >Secretaria</option>
																	<option value="TESTE" <%

																		if (modelLogin != null && modelLogin.getPerfil().equals("TESTE"))
																			{out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																	} %>>Teste</option>
																</select>
															<span class="form-bar"></span>
																<label class="float-label">Perfil</label>
															</div>
															<div class="form-group form-static-label">
																<input type="text" name="login" id="login"
																	class="form-control" required="required"
																	autocomplete="false" value="${modelLogin.login}">
																<span class="form-bar"></span> <label
																	class="float-label">Login:</label>
															</div>
															<div class="form-group form-static-label">
																<input type="password" name="senha" id="senha"
																	class="form-control" required="required"
																	autocomplete="false" value="${modelLogin.senha}">
																<span class="form-bar"></span> <label
																	class="float-label">Senha:</label>
															</div>

															<button type="button"
																class="btn btn-primary waves-effect waves-light"
																onclick="limparForm();">Novo</button>
															<button class="btn btn-success waves-effect waves-light">Salvar</button>
															<button type="button"
																class="btn btn-info waves-effect waves-light"
																onclick="criarDeleteComAjax()">Excluir</button>
															<button type="button" class="btn btn-secondary"
																data-toggle="modal" data-target="#modalUsuario">Pesquisar</button>
														</form>

													</div>
												</div>
											</div>
										</div>
										<span id="msg"></span>
									</div>
									<div style="height: 300px; overflow: scroll;">
										<table class="table" id="tabelaResultadosview">
											<thead>
											<tr>
												<th scope="col">#</th>
												<th scope="col">Nome</th>
												<th scope="col">Ver</th>
											</tr>
											</thead>
											<tbody>
												<c:forEach items='${modelLogins}' var='ml'>
													<tr>
														<td><c:out value="${ml.id}"></c:out></td>
														<td><c:out value="${ml.nome}"></c:out></td>
														<td><a href="<%=request.getContextPath()%>/ServletUsuarioController?acao=pesquisarEditar&id=${ml.id}" class="btn btn-success" type="button">Ver</a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>

									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="js-include.jsp"></jsp:include>

	<!-- Modal -->
	<div class="modal fade" id="modalUsuario" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Pesquisar Usuário</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="input-group mb-3">
						<input type="text" class="form-control"
							placeholder="Usuario"
							aria-label="nome" aria-describedby="basic-addon2"
							id="nomePesquisar">
						<div class="input-group-append">
							<button class="btn btn-outline-secondary" type="button" onclick="pesquisarUsuario()">Pesquisar</button>
						</div>
					</div>
					<div style="height: 300px; overflow: scroll;">
						<table class="table" id="tabelaResultados">
							<thead>
								<tr>
									<th scope="col">#</th>
									<th scope="col">Nome</th>
									<th scope="col">Ver</th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
						</table>
					</div>
					<span id="totalRegistros"></span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">

		function verEditar(id) {

			const urlAction = document.getElementById('formUser').action;
			window.location.href = urlAction + '?acao=pesquisarEditar&id=' + id;

		}

		function pesquisarUsuario() {

			const nomePesquisar = document.getElementById('nomePesquisar').value;

			if(nomePesquisar != null && nomePesquisar !== '' && nomePesquisar.trim() !== '') {

				const urlAction = document.getElementById('formUser').action;
				$.ajax({
					
					method : "get",
					url : urlAction,
					data : "nomePesquisar=" + nomePesquisar + "&acao=pesquisarUserAjax",
					success : function(response) {

						const json = JSON.parse(response);
						console.log()
						$('#tabelaResultados > tbody > tr').remove(); // jquery - remove pesquisa feita anteriormente
						
						for (let p=0; p < json.length; p++) {
							$('#tabelaResultados > tbody').append('<tr> <td>'+json[p].id+'</td><td>'+json[p].nome+'</td><td><button type="button" class="btn btn-info" onclick="verEditar('+json[p].id+')" >Ver</button></td></tr>')
						}
						
						document.getElementById('totalRegistros').textContent = 'Resultados: ' + json.length;					}
				}).fail(
						function(xhr, status, errorThrown) {
							alert('Erro ao pesquisar usuários por nome: '
									+ xhr.responseText)
						})
			}
		}
	
		function criarDeleteComAjax() {
			if (confirm('Confirma a exclusão')) {
				const urlAction = document.getElementById('formUser').action;
				const idUser = document.getElementById('id').value;

				$.ajax({
					method : "get",
					url : urlAction,
					data : "id=" + idUser + "&acao=deletarajax",
					success : function(response) {
						limparForm();
						// alert(response);
						document.getElementById('msg').textContent = response;
					}
				}).fail(
						function(xhr, status, errorThrown) {
							alert('Erro ao deletar usuários por id: '
									+ xhr.responseText)
						})

			}
		}

		function limparForm() {
			const elementos = document.getElementById("formUser").elements; // retorna os elementos html dentro do form
			for (let p = 0; p < elementos.length; p++) {
				elementos[p].value = '';
			}
		}

		function criarDelete() {
			if (confirm('Confirma a exclusão')) {
				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();
			}
		}
	</script>
</body>
</html>
