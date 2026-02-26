# Cálculo Previdenciário (SELIC) - Desktop Swing

## Execução rápida

### Via terminal (sempre confiável)
```bash
java -jar target/calculo-previdenciario-1.0-SNAPSHOT.jar
```

### Via duplo clique no Windows
Se `java -jar ...` funciona e o duplo clique no `.jar` **não** funciona, o problema normalmente é a associação de arquivo do Windows (o `.jar` abrindo com Java errado/antigo, ou com launcher de terceiros).

## Opção recomendada para distribuição no Windows: `.exe`

Este projeto possui um perfil Maven (`windows-exe`) que gera um executável `.exe` com Launch4j.

### Pré-requisitos
- Java 11+ instalado
- Maven instalado

### Gerar o `.exe`
No **Windows**, execute:
```bash
mvn clean package -Pwindows-exe
```

Saída esperada:
- `target/calculo-previdenciario.exe`

Esse arquivo abre por duplo clique sem depender da associação de `.jar` do Windows.

## Corrigindo associação do `.jar` (alternativa)
Se preferir abrir o `.jar` por duplo clique:
1. Reassocie `.jar` para abrir com `javaw.exe` da instalação correta do Java.
2. Teste com:
   - `java -version`
   - `where java`
3. Garanta que não há múltiplos launchers conflitantes (Oracle/OpenJDK antigos, IDE launchers etc).
