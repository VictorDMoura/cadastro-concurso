# Cadastro de Aprovados em Concursos Públicos

Sistema web para cadastro de candidatos aprovados em concursos públicos, desenvolvido com Spring Boot e Thymeleaf.

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Data JPA**
- **Thymeleaf**
- **H2 Database**
- **Bootstrap 5**
- **Maven**

## 📋 Funcionalidades

- ✅ Cadastro de aprovados com validação de dados
- ✅ Upload de imagem (máx. 2MB, formatos: JPG, JPEG, PNG)
- ✅ Validação de campos obrigatórios
- ✅ Listagem de todos os cadastros
- ✅ Exclusão de registros
- ✅ Interface responsiva e moderna

## 🔧 Campos do Formulário

- **Nome**: obrigatório, 3-100 caracteres
- **E-mail**: obrigatório, formato válido, único
- **Telefone**: obrigatório, formato (XX) XXXXX-XXXX
- **Concursos**: obrigatório, 3-500 caracteres
- **Foto**: obrigatória, máx 2MB, formatos permitidos (JPG, JPEG, PNG)

## 🏃 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior

### Executando a aplicação

```bash
# Clone o repositório
git clone https://github.com/VictorDMoura/cadastro-concurso.git

# Entre no diretório
cd cadastro-concurso

# Execute com Maven
./mvnw spring-boot:run

# Ou compile e execute o JAR
./mvnw clean package
java -jar target/cadastro-concurso-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: `http://localhost:8080`

### Console H2

Acesse o console H2 em: `http://localhost:8080/h2-console`

- **JDBC URL**: `jdbc:h2:mem:concursodb`
- **Username**: `sa`
- **Password**: (deixe em branco)

## 📦 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/concurso/
│   │   ├── controller/      # Controllers REST
│   │   ├── exception/       # Tratamento de exceções
│   │   ├── model/          # Entidades JPA
│   │   ├── repository/     # Repositórios
│   │   ├── service/        # Lógica de negócio
│   │   └── CadastroConcursoApplication.java
│   └── resources/
│       ├── templates/      # Views Thymeleaf
│       │   ├── index.html
│       │   └── formulario.html
│       └── application.properties
```

## 📄 Licença

Este projeto está sob a licença MIT.

## 👤 Autor

Victor D Moura

---

