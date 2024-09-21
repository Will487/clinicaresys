<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="iso-8859-1" %>
<jsp:include page="/header.jsp"/>
<%--<jsp:include page="/menu.jsp"/>--%>

<div class="container-fluid">
    <h1 class="h3 mb-2 text-gray-800">Tipos de Pagamento</h1>
    <p class="mb-4">Formulário de Cadastro de Tipos de Pagamento</p>
    
    <a class="btn btn-success mb-4" href="${pageContext.request.contextPath}/TipoListar">
        <i class="fas fa-sticky-note"></i>
        <strong>Voltar</strong>
    </a>
    <div class="row justify-content-center"> <!-- Alteração aqui -->
        <div class="col-lg-6">  <!-- Alteração aqui -->
            <div class="card shadow mb-4 mx-auto">  <!-- Alteração aqui -->
                <div class="card-body">
                    <div class="form-group">
                        <label>Id</label>
                        <input class="form-control" type="text" name="idTipo" id="idtipo" value="${tipo.idtipo}" readonly="readonly"/>
                    </div>
                    <div class="form-group">
                        
                        <label>Descricao</label>
                        <input class="form-control" type="text" name="descricao" id="descricao" value="${tipo.descricao}" size="100" maxlength="100"/>
                    </div>
                    <div class="form-group">
                       
                            
                        
                        
                        <label>Desconto</label>
                        <input class="form-control" type="text" name="valordesconto" id="valordesconto" value="${tipo.valordesconto}%" size="100" maxlength="100"/>
                    </div>
                    <div class="form-group">
                        
                       <label>Taxa</label>
                        <input class="form-control" type="text" name="valortaxa" id="valortaxa" value="${tipo.valortaxa}%" size="100" maxlength="100"/>
                    </div>
                    <div class="form-group">
                        
                      
                  
                    </div>
                            <div class="form-group">
                                <button class="btn btn-success" type="submit" id="submit" onclick="validarCampos()">
                                    Salvar Documento
                                </button>
                            </div>        
                    </div>
                </div>
            </div>
        </div>                    
    </div>
<style type="text/css">
    .inputfile{
        width: 0.1px;
        height: 0.1px;
        opacity: 0;
        overflow: hidden;
        position: absolute;
        z-index: -1;
    }
    .inputfile:focus + label{
        outline: 1px dotted #000;
        outline: -webkit-focus-ring-color auto 5px;
    }
    .inputfile + label * {
        pointer-events: none;
    }
    .borda{
        position: relative;
        margin: 0 20px 30px 0;
        padding: 10px;
        border: 1px solid #e1e1e1;
        border-radius: 3px;
        background: #fff;
        -webkit-box-shadow: 0px 0px 3px rgba(0,0,0,0,06);
        -moz-box-shadow: 0px 0px 3px rgba(0,0,0,0,06);
        box-shadow: 0px 0px 3px rgba(0,0,0,0,06);
    }
</style>

<script>
    $(document).ready(function (){
        console.log("entrei no ready do documento");
        $("#valordesconto").maskMoney({
            prefix: '',
            suffix: '',
            allowZero: false,
            allowNegative: true,
            allowEmpty: false,
            doubleClickSelection: true,
            selectAllOnFocus: true,
            thousands: '.',
            decimal: ',',
            precision: 2,
            affixesStay: true,
            bringCareAtEndOnFocus: true
        })
        $("#valortaxa").maskMoney({
            prefix: '',
            suffix: '',
            allowZero: false,
            allowNegative: true,
            allowEmpty: false,
            doubleClickSelection: true,
            selectAllOnFocus: true,
            thousands: '.',
            decimal: ',',
            precision: 2,
            affixesStay: true,
            bringCareAtEndOnFocus: true
        })
    })
    
    function validarCampos(){
        console.log("entrei na validação de campos");
        if(document.getElementById("descricao").value == ''){
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Verifique a descrição da despesa',
                showConfimButton: false,
                timer: 5000
            })
            $("#descricao").focus();
        }else if(document.getElementById("valordesconto").value == ''){
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Verifique o Valor da despesa',
                showConfimButton: false,
                timer: 5000
            })
            $("#valordesconto").focus();
            
            }else if(document.getElementById("valortaxa").value == ''){
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Verifique o Valor da despesa',
                showConfimButton: false,
                timer: 5000
            })
            $("#valortaxa").focus();
        }else{
            gravarDados();
        }
    }
    
    function gravarDados(){
        console.log("Gravando Dados...");
        console.log("montar ajax")
        $.ajax({
            type: 'post',
            url: 'TipoCadastrar',
            data: {
                idtipo: $('#idtipo').val(),
                descricao: $('#descricao').val(),
                valordesconto: $('#valordesconto').val(),
                valortaxa: $('#valortaxa').val()

            },
            success:
                    function(data){
                        console.log("resposta servlet->");
                        console.log(data);
                        if(data==1){
                            Swal.fire({
                                position: 'center',
                                icon: 'success',
                                title: 'Sucesso',
                                text: 'Pagamento gravada com Sucesso',
                                showConfimButton: false,
                                timer: 5000
                            })
                        }else{
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Erro',
                                text: 'Não foi possível gravar o Pagamento',
                                showConfimButton: false,
                                timer: 5000
                            })
                        }
                        window.location.href = "${pageContext.request.contextPath}/TipoListar";
                    },
            error:
                function (data){
                    console.log("deu bosta");
                    window.location.href = "${pageContext.request.contextPath}/TipoListar";
                }
        });
    }
    
</script>
<%@include file ="/footer.jsp" %>         