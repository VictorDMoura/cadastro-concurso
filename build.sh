#!/bin/bash

echo "================================"
echo "Buildando Cadastro Concurso"
echo "================================"

mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "================================"
    echo "Build Sucesso!"
    echo "================================"
    echo "JAR gerado: target/cadastro-concurso-0.0.1-SNAPSHOT.jar"
    echo ""
    echo "Para executar localmente:"
    echo "  java -jar target/cadastro-concurso-0.0.1-SNAPSHOT.jar"
    echo ""
    echo "Para executar com perfil de produção:"
    echo "  java -jar target/cadastro-concurso-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod"
    echo ""
else
    echo "Build falhou!"
    exit 1
fi
