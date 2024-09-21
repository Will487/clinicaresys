<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="utf-8" %>
<jsp:include page="/header.jsp"/>
<jsp:include page="/cadastros/menuLogado.jsp"/>

<div class="container-fluid">
    <h1 class="h3 mb-2 text-gray-800">Tipos de Pagamento</h1>
    
    
    <a class="btn btn-success mb-4" href="${pageContext.request.contextPath}/TipoNovo">
        <i class="fas fa-sticky-note"></i>
        <strong>Novo</strong>
    </a>
    
    <div class="card shadow">
        <div class="card-body">
            <table id="datatable" class="table display">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Descrição</th> 
                        <th>ValorDesconto</th>
                        <th>ValorTaxa</th>
                        <th>Excluir</th>
                        <th>Alterar</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="tipo" items="${tipos}">
                        <tr>
                            <td>${tipo.idtipo}</td>
                            <td>${tipo.descricao}</td>
                            <td>${tipo.valordesconto}%</td>
                            <td>${tipo.valortaxa}%</td>
                            <td>
                                <a href="#" id="deletar" title="Excluir" onclick="deletar(${tipo.idtipo})">
                                    <button class="btn btn-danger">Excluir</button>
                                </a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/TipoCarregar?idtipo=${tipo.idtipo}">
                                    <button class="btn btn-warning">Alterar</button>
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
    $(document).ready(function(){
        console.log('entrei ready');
        $('#datatable').DataTable({
            "language": {
                "sProcessing": "Processando...",
                "sLengthMenu": "Mostrar _MENU_ registros",
                "sZeroRecords": "Nenhum registro encontrado.",
                "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                "sInfoEmpty": "Mostrando de 0 até 0 de 0 registros",
                "sInfoFiltered": "",
                "sInfoPostFix": "",
                "sSearch": "Buscar:",
                "sUrl": "",
                "oPaginate": {
                    "sFirst": "Primeiro",
                    "sPrevious": "Anterior",
                    "sNext": "Seguinte",
                    "sLast": "Último"
                }
            }
        });
    });
    
    function deletar(codigo){
        var id = codigo;
        console.log(codigo);
        Swal.fire({
            title: 'Você tem certeza?',
            text: "Você não poderá recuperar os dados após essa operação",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sim, apague o Tipo pagamento',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed){
                $.ajax({
                    type: 'post',
                    url: '${pageContext.request.contextPath}/TipoExcluir',
                    data:{
                        idTipo: id
                    },
                    success: function(data){
                        if(data==1){
                            Swal.fire({
                                position: 'top-end',
                                icon: 'success',
                                title: 'Sucesso',
                                text: 'Pagamento excluído com sucesso',
                                showConfirmButton: false,
                                timer: 2000
                            })
                        }else{
                            Swal.fire({
                                position: 'top-end',
                                icon: 'error',
                                title: 'Erro',
                                text: 'Não foi possível excluir o Pagamento',
                                showConfirmButton: false,
                                timer: 2000
                            })
                        }
                        window.location.href="${pageContext.request.contextPath}/TipoListar";
                    },
                    error: function(data){
                        window.location.href="${pageContext.request.contextPath}/TipoListar";
                    }
                });
            }
        });
    }
</script>
<%@include file="/footer.jsp" %>