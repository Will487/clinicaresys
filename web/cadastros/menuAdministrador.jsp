<jsp:include page="menuHeader.jsp"/>
  <h6 class="collapse-header">Cadastros Primarios:</h6>
        <a class="collapse-item" href="${pageContext.request.contextPath}/EstadoListar">Estado</a>
        <a class="collapse-item" href="${pageContext.request.contextPath}/CidadeListar">Cidade</a>
        <a class="collapse-item" href="${pageContext.request.contextPath}/DespesaListar">Despesa</a>
        <a class="collapse-item" href="${pageContext.request.contextPath}/AdministradorListar">Administrador</a>
        <a class="collapse-item" href="${pageContext.request.contextPath}/PacienteListar">Paciente</a>
        <a class="collapse-item" href="${pageContext.request.contextPath}/ProfsaudeListar">Profissional de Saúde</a>
        <a class="collapse-item" href ="${pageContext.request.contextPath}/TipoListar">Tipo de Pagamento</a>
        <a class="collapse-item" href ="${pageContext.request.contextPath}/AgendamentoListar">Agendamento</a>
   <jsp:include page="menuFooter.jsp"/>