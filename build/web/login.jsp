<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>

<!DOCTYPE html>
<html lang="br">
    <head>
        <meta charset="UTF-8">
        <title>Login</title>

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/sb-admin-2.min.css" rel="stylesheet">
    </head>
    <body  style="background-color: #4edfc7; background-image: linear-gradient(f9faf5, #4edfd3 10%, #22be44 100%); background-size: cover;">


        <div class="container">
            <div class="row justify-content-center">
                <div class="col-xl-10 col-lg-12 col-md-9">
                    <div class="card o-hidden border-0 shadow-lg my-5">
                        <div class="card-body p-0">
                            <div class="row">

                                <div class="col-lg-6 d-none d-lg-block">
                                    <img src="https://i.ibb.co/sRDxR4C/szzzzzzzzzzzzz.png" 
                                         alt="CliniCareSys Logo" 
                                         class="img-fluid w-100 h-100" 
                                         style="object-fit: cover;">
                                </div>



                                <div class="col-lg-6">
                                    <div class="p-5">
                                        <div class="text-center">
                                            <h1 class="h4 text-gray-900 mb-4">CliniCareSys Login!</h1>
                                        </div>

                                        <!-- Aqui começa o seu formulário de login -->
                                        <div class="form-group">
                                            <input type="text" id="login" name="login" required="" placeholder="Coloque seu login"
                                                   onblur="BuscarUsuariosPorNome()" class="form-control form-control-user">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" required="" name="senha" id="senha" placeholder="Coloque sua senha" class="form-control form-control-user">
                                        </div>
                                        <div class="form-group">
                                            <select name="tipo" id="tipo" tabindex="3" class="form-control">
                                                <option value="">Selecione</option>
                                            </select>
                                        </div>
                                       <button id="submit" class="btn btn-primary btn-user btn-block" style="background-color: #01413c; color: white; padding: 10px 20px; border: none; border-radius: 5px; font-size: 16px; cursor: pointer;">Logar</button>

                                        <div id="erro"></div>

                                        <!-- Aqui termina o seu formulário de login -->

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                console.log("entrei função");
                $("#submit").on("click", function () {
                    console.log("entrei click submit");
                    if ($('#login').val() === "") {
                        $("#login").focus();
                        $("#erro").html("<div>Por favor, preencher o campo usuário.</div>").show();
                        tempo();
                        return false;
                    }
                    if ($('#senha').val() === "") {
                        $("#senha").focus();
                        $("#erro").html("<div>Por favor, preencher o campo senha.</div>").show();
                        tempo();
                        return false;
                    }
                    $("#submit").prop("disabled", true);
                    $("#submit").html('<i class="fa fa-spinner" aria-hidden="true"></i> Aguarde...');

                    $.ajax({
                        type: 'post',
                        url: 'UsuarioLogar',
                        data: {
                            acao: "login",
                            login: $('#login').val(),
                            senha: $('#senha').val(),
                            tipo: $('#tipo').val()
                        },
                        success:
                                function (data) {
                                    if (data == 'ok') {
                                        window.location.href = "${pageContext.request.contextPath}/cadastros/homeLogado.jsp";
                                    } else {
                                        $('#submit').removeAttr('disabled');
                                        $("#submit").html('Entrar');
                                        $("#wrapper_error").html("<div class='alert alert-danger'>Usuário ou senha incorreto.</div>").show();
                                        tempo();
                                    }
                                },
                        error:
                                function (data) {
                                    RefreshTable();
                                }
                    });
                });

                function tempo() {
                    setTimeout(function () {
                        $("#wrapper_error").hide();
                    }, 3000); // 3 segundos
                }
            });

            function BuscarUsuariosPorNome()
            {
                usuario = '';
                console.log("entrei na function");
                $('#tipo').empty(); //..limpa select de tipo de usuario.
                loginUsuario = $('#login').val();
                console.log(loginUsuario);
                if (loginUsuario != 'null')
                {
                    console.log("vai rodar o ajax");
                    //console.log(idEst);
                    url = "UsuarioBuscarPorLogin?loginusuario=" + loginUsuario;
                    //console.log(url);
                    $.getJSON(url, function (result) {
                        //alert(result);
                        $.each(result, function (index, value) {
                            $('#tipo').append(
                                    '<option id="usuario_' + value.idUsuario
                                    + '"value="' + value.tipo + '">'
                                    + value.tipo + '</option>'
                                    );
                            if (usuario !== '') {
                                $('#usuario_' + usuario).prop({selected: true});
                            } else {
                                $('#usuario_').prop({selected: true});
                            }

                        });
                        console.log("montou o select");
                    }
                    ).fail(function (obj, textStatus, error) {
                        alert('Erro do servidor: ' + textStatus + ', ' + error);
                    });
                }
            }
        </script>
        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="js/sb-admin-2.min.js"></script>


    </body>
</html>
