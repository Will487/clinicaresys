<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/cadastros/menuLogado.jsp"/>

<div class="container-fluid">
    <!-- Page Heading -->
    <h1 class="h3 mb-2 text-gray-800">Agendamentos</h1>
    <p class="mb-4">Gerenciamento de Agendamentos</p>
    <a href="#modalAdicionar" class="btn btn-success mb-4" data-toggle="modal"
       data-id="" onclick="setDadosModal(${0})">
        <i class="fas fa-plus fa-fw"></i> Adicionar Agendamento </a>
    <div class="card shadow">
        <div class="card-body">
            <table id="datatable" class="display">
                <thead>
                    <tr>
                        <th align="center">ID</th>
                        <th align="center">Paciente</th>
                        <th align="center">Profissional</th>
                        <th align="center">Data</th>
                        <th align="center">Horário</th>
                        <th align="center">Descrição</th>
                        <th align="center">Observação</th>
                        <th align="center">Valor do pagamento</th>
                        <th align="center">Tipo do Pagamento</th>
                        <th align="center">Alterar</th>
                        <th align="center">Excluir</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="agendamento" items="${agendamentos}">
                        <tr>
                            <td align="center">${agendamento.idAgendamento}</td>
                            <td align="center">${agendamento.paciente.idPaciente}</td>
                            <td align="center">${agendamento.profissional.idProfsaude}</td>
                            <td align="center"><fmt:formatDate value="${agendamento.dataAgendamento}" pattern="dd/MM/yyyy" /></td>
                            <td align="center"><fmt:formatDate value="${agendamento.horario}" pattern="HH:mm" /></td>
                            <td align="center">${agendamento.descricao}</td>
                            <td align="center">${agendamento.observacao}</td>
                            <td align="center">${agendamento.valor}</td>
                             <td align="center">${agendamento.pagamento.descricao}</td>
                            <td align="center">
                                <a href="#modalAdicionar" class="btn btn-success" data-toggle="modal" 
                                   onclick="setDadosModal(${agendamento.idAgendamento})">
                                    <i class="fas fa-edit fa-fw"></i> Alterar </a>
                            </td>
                            <td align="center">
                                <a href="#" onclick="deletar(${agendamento.idAgendamento})">
                                    <button class="btn btn-danger">
                                        <i class="fas fa-trash fa-fw"></i> Excluir
                                    </button></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    
    <!-- Modal para Adicionar/Alterar Agendamento -->
    <div class="modal fade" id="modalAdicionar"  tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Adicionar/Alterar Agendamento</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                
              
                <div class="modal-body">
                    <input class="form-control" type="text" name="idagendamento" id="idagendamento" value="" 
                           readonly="readonly"/>
                    <input class="form-control" type="text" name="idpaciente" id="idpaciente"
                           value="" readonly="readonly"/>
                    <input class="form-control" type="text" name="idprofissional" id="idprofissional" 
                           value="" readonly="readonly"/>

                </div>
                
                 <div class="modal-body">
                    <div class="form-line row">
                        <div class="col-sm">
                            <button type="button" href="#modalPaciente" data-toggle="modal" class="form-control btn btn-secondary">Selecionar Paciente</button>
                        </div>
                        <div class="col-sm">
                            <button type="button" href="#modalProfissional" data-toggle="modal" class="form-control btn btn-secondary">Selecionar Profissional</button>
                        </div>
                    </div>
                </div>
          
<div class="modal-body" >
    <!-- Data Agendamento e Horário Agendamento -->
    <div class="form-line row">
        <div class="col-sm-6">
            <label for="dataagendamento" class="m-t-0 header-title">Data Agendamento</label>
            <input class="form-control" type="date" name="dataagendamento" id="dataagendamento" value="${agendamento.dataAgendamento}">
        </div>
        <div class="col-sm-6">
            <label for="horario" class="m-t-0 header-title">Horário Agendamento</label>
            <input class="form-control" type="time" name="horario" id="horario" value="${agendamento.horario}">
        </div>
    </div>
</div>

<!-- Valor do Pagamento e Tipo de Pagamento -->
<div class="modal-body">
    <div class="form-line row">
        <div class="col-sm-6">
            <label for="valor">Valor do Pagamento</label>
            <input class="form-control" type="text" name="valor" id="valor" style="text-align:right;" value="<fmt:formatNumber value='${agendamento.valor}' type='currency'/>">
        </div>
        <div class="col-sm-6">
            <label for="idpagamento">Tipo de Pagamento</label>
            <select class="form-control" name="idpagamento" id="idpagamento" required>
                <option value="nulo">Selecione</option>
                <c:forEach var="tipo" items="${tipos}">
                    <option value="${tipo.idtipo}" ${agendamento.pagamento.idtipo == tipo.idtipo ? "selected" : ""}>${tipo.descricao}</option>
                </c:forEach>
            </select>
        </div>
    </div>
</div>

<!-- Observação -->
<div class="modal-body">
    <label for="observacao">Observação</label>
    <textarea class="form-control" name="observacao" id="observacao" rows="4"></textarea>
</div>

<!-- Descrição -->
<div class="modal-body">
    <label for="descricao">Descrição</label>
    <textarea class="form-control" name="descricao" id="descricao" rows="4"></textarea>
</div>


                        

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                        <button type="button" class="btn btn-success" onclick="validarCampos()">Salvar</button>
                    </div>
                </div>
            </div>
        </div>        
    </div>


    <div class="modal fade" id="modalPaciente" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Buscar Paciente por Nome</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                
                <div class="modal-body">
                    <label>Nome do Paciente</label>
                    <input oninput ="BuscarPacientePorNome()" class="form-control" type="text" name="nomePaciente" id="nomePaciente"/>
                </div>

                <div class="form-group">
                    <div class="card shadow">
                        <div class="card-body">
                            <table id="datatable" class="display">
                                <thead>
                                   <tr>
                                        <th align="left">ID</th>
                                        <th align="left">Nome</th>
                                  </tr>
                                </thead>
                                <tbody id="pacienteTableBody">
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>       



    <div class="modal fade" id="modalProfissional" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Buscar Profissional por Nome</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                
                <div class="modal-body">
                    <label>Nome do Profissional</label>
                    <input oninput ="BuscarProfissionalPorNome()" class="form-control" type="text" name="nomeProfissional" id="nomeProfissional"/>
                </div>

                <div class="form-group">
                    <div class="card shadow">
                        <div class="card-body">
                            <table id="datatable" class="display">
                                <thead>
                                    <tr>
                                        <th align="left">ID</th>
                                        <th align="left">Nome</th>
                                    </tr>
                                </thead>
                                <tbody id="profissionalTableBody">
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>  
</div>

<script>
$(document).ready(function() {
    $("#valor").maskMoney({
        prefix: 'R$',
        suffix: '',
        allowZero: false,
        allowNegative: true,
        allowEmpty: false,
        doubleClickSelection: true,
        selectAllOnFocus: true,
        thousands: '.',
        decimal: ",",
        precision: 2,
        affixesStay: true,
        bringCareAtEndOnFocus: true
    })

    $('#datatable').DataTable({
        "oLanguage": {
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

function limparDadosModal(){
    $('#idagendamento').val("0");
    $('#idpaciente').val("");
    $('#idprofissional').val("");
    $('#idpagamento').val("");
    $('#dataagendamento').val("");
    $('#horario').val("");
    $('#descricao').val("");
    $('#observacao').val("");
    $('#valor').val("");
    var foto = document.getElementById("foto");
    foto.src = "";
}

function setDadosModal(idAgendamento) {

    limparDadosModal();
    console.log("1");
    if (idAgendamento != "0"){
        $.getJSON('AgendamentoCarregar', {idagendamento: idAgendamento}, function(respostaServlet){
            console.log(respostaServlet);
            $('#idagendamento').val(respostaServlet.idAgendamento);
            $('#idpaciente').val(respostaServlet.paciente.idPaciente);
            $('#idprofissional').val(respostaServlet.profissional.idProfsaude);
            $('#idpagamento').val(respostaServlet.pagamento.idtipo);
            $('#dataagendamento').val(respostaServlet.dataAgendamento);
            console.log(respostaServlet.horario);
            $('#horario').val(respostaServlet.horario);
            $('#descricao').val(respostaServlet.descricao);
            $('#observacao').val(respostaServlet.observacao);
            $('#valor').val(respostaServlet.valor);
        });
    }
}

function deletar(idAgendamento){
    Swal.fire({
        title: 'Você tem certeza?',
        text: "Você deseja realmente excluir este agendamento?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sim',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type: 'post',
                url: 'AgendamentoExcluir',
                data: { idagendamento: idAgendamento },
                success:
                    function(data){
                        if(data == 1){
                            Swal.fire({
                                position: 'center',
                                icon: 'success',
                                title: 'Sucesso',
                                text: 'Agendamento excluído com sucesso!',
                                showConfirmButton: false,
                                timer: 2000
                            })
                        } else {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Erro',
                                text: 'Não foi possível excluir o agendamento!',
                                showConfirmButton: false,
                                timer: 2000
                            })
                        }
                        window.location.href = "${pageContext.request.contextPath}/AgendamentoListar";
                    },
                error:
                    function(data){
                        window.location.href = "${pageContext.request.contextPath}/AgendamentoListar";
                    }
            });
        }
    });
}

function validarCampos() {
    if ($('#dataagendamento').val() == '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Verifique a data do Agendamento!',
            showConfirmButton: true,
            timer: 2000
        });
        $('#dataagendamento').focus();
    } else if ($('#horario').val() == '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Verifique o horário do Agendamento!',
            showConfirmButton: true,
            timer: 2000
        });
        $('#horario').focus();
    } else if ($('#idpaciente').val() == '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Selecione um paciente para o Agendamento!',
            showConfirmButton: true,
            timer: 2000
        });
        $('#idpaciente').focus();
    } else if ($('#idprofissional').val() == '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Selecione um profissional para o Agendamento!',
            showConfirmButton: true,
            timer: 2000
        });
        $('#idprofissional').focus();
    } else if ($('#idpagamento').val() == '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Selecione um tipo de pagamento para o Agendamento!',
            showConfirmButton: true,
            timer: 2000
        });
        $('#idpagamento').focus();
    } else if ($('#valor').val() == '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Verifique o valor do Agendamento!',
            showConfirmButton: true,
            timer: 2000
        });
        $('#valor').focus();
    } else {
        gravarDados();
    }
}


function gravarDados() {
    $.ajax({
        type: 'post',
        url: 'AgendamentoCadastrar',
        data: {
            idagendamento: $('#idagendamento').val(),
            idpaciente: $('#idpaciente').val(),
            idprofissional: $('#idprofissional').unmask().val(),
            idpagamento: $('#idpagamento').val(),
            dataagendamento: $('#dataagendamento').val(),
            horario: $("#horario").val(),
            descricao: $("#descricao").val(),
            observacao: $("#observacao").val(),
            valor: $("#valor").val(),
        },
        success:
            function (data) {
                console.log("reposta servlet->");
                console.log(data);
                if (data == 1) {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Sucesso',
                        text: 'Agendamento gravado com sucesso!',
                        showConfirmButton: true,
                        timer: 10000
                    }).then(function(){
                        window.location.href = "${pageContext.request.contextPath}/AgendamentoListar";
                    })
                } else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Erro',
                        text: 'Não foi possível gravar o agendamento!',
                        showConfirmButton: true,
                        timer: 10000
                    }).then(function(){
                        window.location.href = "${pageContext.request.contextPath}/AgendamentoListar";
                    })
                }
            },
        error:
            function (data) {
                window.location.href = "${pageContext.request.contextPath}/AgendamentoListar";
            }
    });
}



function uploadFile() {
    var target = document.getElementById("foto");
    var file = document.querySelector("input[type='file']").files[0];
    if (file) {
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = function() {
            target.src = reader.result;
        };
    } else {
        target.src = "";
    }
}

function BuscarPacientePorNome() {
    nomePaciente = $('#nomePaciente').val();
    if (nomePaciente != 'null') {
        var url = "PacienteBuscarPorNome?nomePaciente=" + nomePaciente;
        $.getJSON(url, function (result) {
            $('#pacienteTableBody').empty();

            $.each(result, function (index, value) {
                var pacienteRow = '<tr id="paciente_' + value.idPaciente + '">' +
                    '<td>' + value.idPaciente + '</td>' +
                    '<td>' + value.nome + '</td>' +
                    '<td><button type="button" href="#modalPaciente" data-dismiss="modal" class="btn btn-secondary" onclick="selecionarPaciente(' + value.idPaciente + ')">Selecionar</button></td>' +
                    '</tr>';

                $('#pacienteTableBody').append(pacienteRow);
            });
        }).fail(function (obj, textStatus, error) {
            alert('Erro do servidor: ' + textStatus + ', ' + error);
        });
    }
}

function selecionarPaciente(idPaciente) {
    $('#idpaciente').val(idPaciente);
}

function BuscarProfissionalPorNome() {
    nomeProfissional = $('#nomeProfissional').val();
    if (nomeProfissional != 'null') {
        var url = "ProfissionalBuscarPorNome?nomeProfissional=" + nomeProfissional;
        $.getJSON(url, function (result) {
            $('#profissionalTableBody').empty();

            $.each(result, function (index, value) {
                var profissionalRow = '<tr id="profissional_' + value.idProfsaude + '">' +
                    '<td>' + value.idProfsaude + '</td>' +
                    '<td>' + value.nome + '</td>' +
                    '<td><button type="button" href="#modalProfissional" data-dismiss="modal" class="btn btn-secondary" onclick="selecionarProfissional(' + value.idProfsaude + ')">Selecionar</button></td>' +
                    '</tr>';

                $('#profissionalTableBody').append(profissionalRow);
            });
        }).fail(function (obj, textStatus, error) {
            alert('Erro do servidor: ' + textStatus + ', ' + error);
        });
    }
}

function selecionarProfissional(idProfissional) {
    $('#idprofissional').val(idProfissional);
}

</script>



<%@include file="/footer.jsp"%>
