<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix= "fmt" uri= "http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType= "text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/cadastros/menuLogado.jsp"/>

<div class="container-fluid">
    <!-- Page Heading -->
    <h1 class="h3 mb-2 text-gray-800">Estados</h1>
    <p class="mb-4">Planilha de Registros</p>
    
    <a class="btn btn-success mb-4" href="${pageContext.request.contextPath}/EstadoNovo">
        <i class="fas fa-sticky-note"></i>
        <strong>Novo</strong>
    </a>
    
    <div class="card shadow">
        <div class="card-body">
            <table id="datatable" class="display">
                <thead>
                    <tr>
                        <th align="left">ID</th>
                        <th align="left">Nome</th>
                        <th align="left">Silga</th>
                        <th Align="left">Excluir</th>
                        <th Align="left">Alterar</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="estado" items="${estados}"> 
                        <tr>
                            <td align="right">${estado.idEstado}</td>
                            <td align="left">${estado.nomeEstado}</td>
                            <td align="left">${estado.siglaEstado}</td>
                            <td align="center">
                                <a href="#" id="deletar" title="Excluir" onclick="deletar(${estado.idEstado})">
                                        <button class="btn btn-group-lg btn-danger"/>Excluir</button></a>
                            </td>                        
                            <td align="center">
                                <a href="${pageContext.request.contextPath}/EstadoCarregar?idEstado=${estado.idEstado}">
                                   <button class="btn btn-group-lg btn-success">Alterar</button>
                                </a>
                            </td>             
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>      
    <script>
    $(document).ready(function() {
            console.log('entrei ready');
            //Carregamos a datatable
            //$("#datatable").DataTable({});
            $('#datatable').DataTable({
                "oLanguage": {
                    "sProcessing": "Processando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "Nenhum registro encontrado.",
                    "sInfo": "Mostrando de _START_ at� _END_ de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando de 0 at� 0 de 0 registros",
                    "sInfoFiltered": "",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "oPaginate": {
                        "sFirst": "Primeiro",
                        "sPrevious": "Anterior",
                        "sNext": "Seguinte",
                        "sLast": "�ltimo"
                    }
                }
            });
        });
    
     function deletar(codigo) {
         console.log("Entrei em fun��o deletar");
                            var id = codigo;
                            console.log(codigo);
                            Swal.fire({
                                title: 'Voc� tem certeza?',
                                text: "Voc� n�o pode recuperar depois!",
                                icon: 'warning',
                                showCancelButton: true,
                                confirmButtonColor: '#3085d6',
                                cancelButtonColor: '#d33',
                                confirmButtonText: 'Sim, apague o estado!',
                                cancelButtonText: 'Cancelar'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    $.ajax({
                                        type: 'post',
                                        url: '${pageContext.request.contextPath}/EstadoExcluir',
                                        data: {
                                            idEstado: id
                                        },
                                        success:
                                            function (data) {
                                                if (data == 1) {
                                                    Swal.fire({
                                                        position: 'center',
                                                        icon: 'success',
                                                        title: 'Sucesso',
                                                        text: 'Estado exclu�do com sucesso!',
                                                        showConfirmButton: false,
                                                        timer: 2000
                                                    }).then(function(){
                                                        window.location.href = "${pageContext.request.contextPath}/EstadoListar";
                                                    })
                                                }
                                                else if (data == 2) {
                                                    Swal.fire({
                                                        position: 'center',
                                                        icon: 'warning',
                                                        title: 'Aten��o',
                                                        text: 'H� cidades vinculadas a esse estado! N�o � poss�vel exclu�-lo!',
                                                        showConfirmButton: false,
                                                        timer: 3500
                                                    }).then(function(){
                                                        window.location.href = "${pageContext.request.contextPath}/EstadoListar";
                                                    })
                                                }
                                                else {
                                                    Swal.fire({
                                                        position: 'center',
                                                        icon: 'error',
                                                        title: 'Erro',
                                                        text: 'N�o foi poss�vel excluir o estado!',
                                                        showConfirmButton: false,
                                                        timer: 2000
                                                    }).then(function(){
                                                        window.location.href = "${pageContext.request.contextPath}/EstadoListar";
                                                    })
                                                }
                                            },
                                        error:
                                            function (data) {
                                                window.location.href = "${pageContext.request.contextPath}/EstadoListar";
                                            }
                                    });
                                };
                            });
                        }
                </script>
 <%@include file="/footer.jsp"%>