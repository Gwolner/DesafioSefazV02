# IVIA - Desafio Sefaz

> Projeto desenvolvido durante o desafio proposto pela Sefaz como parte da seleção para Desenvolvedor Java Júnior pela IVIA.

## Ferramentas e configurações

[Apache Netbeans IDE 12.0 (LTS)](http://netbeans.apache.org/download/index.html) como ambiente de desenvolvimento da aplicação;
[Apache Maven](https://maven.apache.org/) integrado ao Netbeans para ontrole de dependências, compilação e execução do projeto;
[HSQLDB](http://hsqldb.org/) adotado como banco de dados relacional SQL.

## Requisitos obrigatório

• Utilizar a Plataforma – Java EE; <br>
• Estruturar a aplicação em camadas; <br>
• Uso de banco de dados relacional/SQL; (preferencialmente HSQLDB ou H2) <br>
• Processo de build utilizando Maven; <br>
• Persistência utilizando JDBC ou JPA; <br> 
• Utilizar no mínimo Java 8; <br>
• Utilizar na interface JSF/Primefaces ou JSP com jQuery e Ajax; <br>
• Disponibilizar o código em repositório Git online (Gitlab ou Github); <br>
• O que você NÃO pode utilizar: Spring / Angular. <br>

*Todos os requisitos obrigatórios foram utilizados e respeitados.*

### Uso da plataforma Java EE ✔

O projeto adota JavaEE e isto pode ser visualizado nas dependências do arquivo pom.xml, além dos próprios recurssos Java EE presentes no projeto como Servlets e JSP. Os servlets presentes neste projeto fazem uso dos métodos **doPut** (atualizar) e **doDelete** (excluir) além dos usuais **doGet** (buscar) e **doPost** (persistir).

```xml
<dependency>
    <groupId>javax</groupId>
    <artifactId>javaee-web-api</artifactId>
    <version>7.0</version>
    <scope>provided</scope>
</dependency>
```

### Estruturar a aplicação em camadas ✔

O projeto foi estruturado com base no Design Patterns MVC e DAO de modo a permitir que cada componente da aplicação tenha suas responsabilidades bem definidas, permitindo assim uma maior flexibilidades para mudanças no projeto, bem como na sua manutenção. 

### Uso de banco de dados relacional/SQL (preferencialmente HSQLDB ou H2) ✔

Nesta etapa eu optei por utilizar o banco HSQLDB por dois motívos: sair do convencional MySQL e aprender algo novo. Gostaria de registrar que esta parte do desafio é interessante, pois essa decisão define o tratamento que será dado ao BD em todo o projeto. Por exemplo, o HSQLDB permite apenas uma conexão por vez quando criado no modo Standalone e isto significa que é preciso saber lidar constantemente com a gestão do banco (conectar, consultar, desconectar) e alterações no projeto seguido de testes.

Abaixo, a dependência do HSQLDB presente no arquivo pom.xml.
```xml
<dependency>
    <groupId>org.hsqldb</groupId>
    <artifactId>hsqldb</artifactId>
    <version>2.5.1</version>
    <scope>compile</scope>
</dependency> 
```

### Processo de build utilizando Maven ✔

Todo o projeto foi desenvolvido utilizando Maven. 
Abaixo, um snippet do conteúdo Maven presente no arquivo pom.xml, adicionado automaticamente após criação do projeto no Netbeans.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.1</version>
    <configuration>
        <source>1.7</source>
        <target>1.7</target>
        <compilerArguments>
            <endorseddirs>${endorsed.dir}</endorseddirs>
        </compilerArguments>
    </configuration>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-war-plugin</artifactId>
    <version>2.3</version>
    <configuration>
        <failOnMissingWebXml>false</failOnMissingWebXml>
    </configuration>
</plugin>
```

### Persistência utilizando JDBC ou JPA ✔

Para persistência no HSQLDB utilizai o JDBC juntamente com o Design Patterns Singleton. Desta forma, o modo como a aplicação lida com as conexões fica mais flúida, tendo em vista que o próprio HSQLDB so aceita uma conexão por vez.

### Utilizar no mínimo Java 8 ✔

A versão do java utilizada foi a 1.8.0_201. 

### Utilizar na interface JSF/Primefaces ou JSP com jQuery e Ajax ✔

Optei o uso do JSP para construção da View por ter mais afinidade com ela do que JSF. Fiz uso do JQuery para lidar com o comportamento de ocultar/exibir formulários e para o AJAX, optei pelo fetch para realizar as requisições assíncronas com os métodos doPut e doDelete dos servlets. Para complementar, fiz uso de mais JS além do fetch e utilizei CSS para definir o layout e as cores nas telas.

### Disponibilizar o código em repositório Git online (Gitlab ou Github) ✔

Aplicação disponível **neste** repositório.

### O que você NÃO pode utilizar: Spring / Angular ✔

Esta regra foi respeitada e nenhum dos dois foram utilizados.





