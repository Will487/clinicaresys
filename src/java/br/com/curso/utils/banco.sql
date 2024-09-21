Create table estado(
idestado serial primary key,
nomeestado varchar(100) not null,
siglaestado varchar(2) not null
);

insert into estado (nomeestado, siglaestado) 
values ('São paulo', 'SP');

create table cidade(
    idcidade serial primary key,
    nomecidade varchar(100) not null,
    situacao varchar(1) not null,
    idestado int not null,
    constraint fk_estado foreign key (idestado) references estado(idestado)

);

insert into cidade (nomecidade, situacao, idestado) values ('Fernandópolis','A', 1);
insert into cidade (nomecidade, situacao, idestado) values ('Jales','A', 1);

create table despesa(
    iddespesa serial primary key,
    descricao varchar(100) not null,
    datadocumento date not null,
    valordespesa numeric(15,2) not null,
    valorpago numeric(15,2),
    situacao varchar(1) not null,
    imagemdocumento text
);
 
select * from despesa;

create table pessoa (
    idpessoa serial primary key,
    nome varchar(100) not null,
    cpfcnpj varchar(14) not null unique,
    datanascimento date,
    idcidade int,
    login varchar(20),
    senha varchar(20),
    foto text,
    constraint fk_cidade foreign key (idcidade) references cidade
);

insert into pessoa (nome, cpfcnpj, datanascimento, idcidade, login, senha, foto) values('adm', '42745947001', '01-01-2020', 1, 'adm', '123',null);

create table administrador(
    idadministrador serial primary key,
    idpessoa int unique,
    situacao varchar(1),
    permitelogin varchar(1),
    constraint fk_administrador_pessoa foreign key (idpessoa) references pessoa
);

insert into administrador (idpessoa,situacao,permitelogin)values(1,'A','S');

create table paciente(
    idpaciente serial primary key,
    idpessoa int unique,
    carteirinha varchar(100),
    situacao varchar(1),
    permitelogin varchar(1),
    constraint fk_paciente_pessoa foreign key (idpessoa) references pessoa
);
-- Primeiro crie a tabela tipodepagamento
create table tipodepagamento(
    idtipo serial primary key,
    descricao varchar(100) not null,
    valordesconto numeric(10,2) not null,
    valortaxa numeric(10,2) not null
);


create table profsaude(
    idprofsaude serial primary key,
    idpessoa int unique,
    cartprof varchar(100),
    situacao varchar(1),
    permitelogin varchar(1),
    constraint fk_profsaude_pessoa foreign key (idpessoa) references pessoa
);

create or replace view usuario as
select p.idpessoa, p.nome, p.cpfcnpj, p.login, p.senha, c.idpaciente as id, 'Paciente' as tipo from pessoa p, paciente c
where c.idpessoa = p.idpessoa and c.situacao = 'A' and c.permitelogin = 'S'
union 
select p.idpessoa, p.nome, p.cpfcnpj, p.login, p.senha, f.idprofsaude as id, 'Profsaude' as tipo from pessoa p, profsaude f where f.idpessoa = p.idpessoa and f.situacao = 'A' and f.permitelogin = 'S' 
union
select p.idpessoa, p.nome, p.cpfcnpj, p.login, p.senha, a.idadministrador as id, 'Administrador' as tipo from pessoa p, administrador a where a.idpessoa = p.idpessoa and a.situacao = 'A' and a.permitelogin = 'S'

CREATE TABLE agendamento(
    idagendamento SERIAL PRIMARY KEY,
    idpaciente INT NOT NULL,
    idprofissional INT NOT NULL,
    idtipo INT NOT NULL,
    dataagendamento DATE NOT NULL,
    horario TIME NOT NULL,
    descricao TEXT,
    observacao TEXT,
    valor NUMERIC(10,2) NOT NULL,
    CONSTRAINT fk_agendamento_paciente FOREIGN KEY (idpaciente) REFERENCES paciente(idpaciente),
    CONSTRAINT fk_agendamento_profissional FOREIGN KEY (idprofissional) REFERENCES profsaude(idprofsaude),
    CONSTRAINT fk_agendamento_tipo FOREIGN KEY (idtipo) REFERENCES tipodepagamento(idtipo)
);
