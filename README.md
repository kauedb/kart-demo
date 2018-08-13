# Kart-demo

### Para gerar o jar
1. ```cd $PROJECT_HOME/kart-demo```
2. ```./gradlew clean build```

### Para iniciar o aplicativo kart-demo
1. ```cd $PROJECT_HOME/kart-demo/build/libs```
2. ```java -jar  kart-demo-0.0.1-SNAPSHOT.jar```

### Para listar os comandos
1. ```shell:> help```

### Exemplo de comandos
1. best lap: Mostrar a melhor volta de um piloto da corrida

    Ex: ```best lap --code 011```
2. champion diff: Mostrar o tempo que o piloto chegou após o vencedor

    Ex: ```champion diff --code 011```
3. duration: Mostrar a duração da corrida

    Ex: ```duration --in SECONDS```
4. pilot velocity: Mostrar a velocidade média do piloto

    Ex: ```pilot velocity --code 011```
5. positions: Mostrar as posições dos pilotos da corrida

    Ex: ```positions```
6. race: Criar corrida a partir do log

    Ex: ```race --log race.log```