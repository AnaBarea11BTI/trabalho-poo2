🖨️ Sistema de Controle de Impressora Fiscal
(README com explicação detalhada do código, principalmente a parte da DLL e JNA)

Este projeto implementa um sistema de controle para impressoras fiscais usando Java e a biblioteca JNA (Java Native Access).
O JNA permite que o Java converse diretamente com uma DLL, ou seja, com funções nativas escritas em C.

O sistema funciona totalmente via terminal, oferecendo funções como impressões, abertura de gaveta e emissão de sinal sonoro.

🎯 OBJETIVO DESTE README

Este README foi reescrito especialmente para:

✔️ explicar com detalhes como o código se conecta à DLL
✔️ explicar como o JNA funciona no seu projeto
✔️ mostrar como cada etapa do código conversa com a impressora
✔️ explicar como a DLL é carregada e usada até o fim da execução

🛠️ Tecnologias Utilizadas

Java 11+

JNA (Java Native Access) — para acesso à DLL

DLL da impressora: E1_Impressora01.dll

Scanner (entrada pelo teclado)

🔌 COMO FUNCIONA O ACESSO À DLL

Esta é a parte mais importante do sistema.

📥 1. O download da DLL

A DLL não é baixada automaticamente pelo programa.
Você precisa colocar manualmente a DLL no computador — normalmente fornecida pelo fabricante.

No seu código, a DLL está localizada neste caminho:

"C:\\Users\\Usuario\\Desktop\\UNASP\\UNASP\\Java-Aluno ensino-medio\\Java-Aluno EM\\Java-Aluno EM\\E1_Impressora01.dll"


⚠️ Esse caminho é fixo.
Se a DLL for movida, o programa para de funcionar.
O ideal seria usar "./lib/E1_Impressora01.dll", mas seu código está funcionando assim.

🧩 2. Como o JNA carrega a DLL

No topo do seu código existe esta interface:

public interface ImpressoraDLL extends Library {


Essa interface declara todos os métodos nativos presentes na DLL.

E aqui acontece a magia:

ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
        "C:\\Users\\Usuario\\Desktop\\UNASP\\UNASP\\Java-Aluno ensino-medio\\Java-Aluno EM\\Java-Aluno EM\\E1_Impressora01.dll",
        ImpressoraDLL.class
);

O que essa linha faz:

Carrega a DLL na memória

Associa cada método Java a um método real da DLL

Cria um objeto pronto para uso chamado INSTANCE

Daqui em diante, tudo que você chama assim:

ImpressoraDLL.INSTANCE.ImpressaoTexto(...)


na verdade está chamando uma função C dentro da DLL.

📞 3. Mapeamento dos métodos da DLL

Na interface, você declarou métodos como:

int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);
int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);
int ImprimeXMLSAT(String dados, int param);


Cada método:

existe na DLL

retorna um código (0 = sucesso, outros números = erro)

segue exatamente a mesma assinatura do C

Se a assinatura estiver errada → o programa trava.

🔗 4. Como o Java chama cada função da DLL
🔌 Exemplo — Abrir Conexão
int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);


A DLL tenta abrir a porta USB/serial indicada.

Se o retorno for:

0 → conexão aberta

outro número → erro

🖨️ Exemplo — Imprimir Texto
int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto("Teste de impressao", 1, 4, 0);


A DLL recebe:

texto

posição

estilo

tamanho

E imprime na impressora sem que o Java precise saber como isso funciona.

📄 Exemplo — Imprimir XML SAT
int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT("path=C:\\...\\XMLSAT.xml", 0);


Esse método manda o caminho do XML para a DLL, e ela interpreta o conteúdo.

📚 EXPLICAÇÃO DO FUNCIONAMENTO DO PROGRAMA (INÍCIO → FIM)

Aqui está uma explicação completa da execução do sistema:

1️⃣ O programa inicia

O main() exibe o menu em loop infinito.

2️⃣ O usuário escolhe uma opção

Exemplo:

2 - Abrir Conexao

3️⃣ Se for abrir conexão → chama a DLL
ImpressoraDLL.INSTANCE.AbreConexaoImpressora(...)


Se sucesso → variável conexaoAberta = true.

4️⃣ Ao chamar qualquer impressão

O programa faz:

Verifica se a conexão está aberta

Chama a função correspondente da DLL

Exibe sucesso ou erro

Opcionalmente corta o papel depois da impressão

5️⃣ A DLL executa a função na impressora

Tudo que é complexo (protocolo USB, comandos ESC/POS, etc.) a DLL faz automaticamente.

O Java não imprime nada — ele apenas envia comandos via DLL.

6️⃣ Quando o usuário escolhe "0"
FechaConexaoImpressora();


A DLL fecha a porta USB/serial e libera os recursos da impressora.

O programa encerra.

📁 Estrutura Recomendada do Projeto
projeto/
├── src/
│   └── Main.java
├── lib/
│   ├── jna.jar
│   └── E1_Impressora01.dll
└── xml/
    ├── XMLSAT.xml
    └── CANC_SAT.xml

▶️ Como Executar

Compile:

javac -cp "jna.jar" Main.java


Execute:

java -cp ".;jna.jar" Main

🧪 Testando a Impressora

Abra conexão → envie um comando → veja se imprime.

🛑 Erros Comuns Explicados
❗ DLL não encontrada

Caminho errado ou DLL incompatível (32/64 bits).

❗ Impressão não funciona

Conexão configurada errado.

❗ XML não imprime

Caminho precisa começar com "path=".

👥 Desenvolvedores

Ana Luiza Barea
Beatriz Firmado
Giovanna Totte
Guilherme Totte

📜 Licença

Uso educacional. A DLL pertence ao fabricante.

✔️ Quer que eu gere a versão Markdown formatada automaticamente?

Posso gerar um arquivo README.md pronto, totalmente formatado, com emojis, tabelas e links. Basta pedir:

👉 "Gerar README.md final"

Você disse:
SIM
O ChatGPT disse:

Aqui está o README.md FINAL, totalmente formatado em Markdown, organizado, profissional e pronto para colocar no GitHub.
Inclui toda a explicação detalhada da DLL, JNA, carregamento, funcionamento interno e fluxo completo do programa, conforme você pediu.

Você pode copiar EXATAMENTE este conteúdo e salvar como:

📄 README.md

🖨️ Sistema de Controle de Impressora Fiscal
(Documentação completa com explicação detalhada da DLL e JNA)

Este projeto implementa um sistema de controle para impressoras fiscais usando Java + JNA (Java Native Access) para acessar funções nativas presentes em uma DLL fornecida pelo fabricante da impressora.
Ele permite enviar comandos como imprimir textos, QR Codes, XML SAT, além de abrir gavetas e emitir sinais sonoros.

O sistema funciona totalmente no terminal e tem como objetivo ser didático, simples e funcional.

📌 Objetivo da Documentação

Este README foi preparado especialmente para:

✔️ Explicar como a DLL é usada pelo código
✔️ Mostrar como o JNA carrega e conecta a DLL ao Java
✔️ Demonstrar o fluxo completo do programa
✔️ Ajudar novos usuários a entenderem como a impressora fiscal está sendo controlada

🧰 Tecnologias utilizadas

Java 11 ou superior

JNA (Java Native Access)

DLL do fabricante: E1_Impressora01.dll

Scanner (Java) — entrada de dados do usuário

📂 Estrutura recomendada do projeto
projeto/
├── src/
│   └── Main.java
├── lib/
│   ├── jna.jar
│   └── E1_Impressora01.dll
└── xml/
    ├── XMLSAT.xml
    └── CANC_SAT.xml

🔌 Funcionamento da DLL + JNA (explicação completa)

Esta é a parte mais importante do sistema.

📥 1. A DLL

A DLL não é baixada automaticamente.
Você deve colocá-la manualmente na pasta do projeto.

No código, o caminho usado é:

"C:\\Users\\Usuario\\Desktop\\UNASP\\UNASP\\Java-Aluno ensino-medio\\Java-Aluno EM\\Java-Aluno EM\\E1_Impressora01.dll"


⚠️ A DLL só funciona se estiver exatamente nesse caminho.
Recomenda-se mover para ./lib/ e ajustar o caminho no código.

🧩 2. Carregando a DLL com JNA

A interface abaixo declara todos os métodos que existem na DLL:

public interface ImpressoraDLL extends Library {


E aqui a DLL é carregada:

ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
        "C:\\...\\E1_Impressora01.dll",
        ImpressoraDLL.class
);

O que o JNA faz aqui:

Carrega a DLL na memória

Liga cada método Java ao método real do C

Cria um objeto acessível como:

ImpressoraDLL.INSTANCE


Isso permite chamar funções da DLL como se fossem Java:

ImpressoraDLL.INSTANCE.ImpressaoTexto(...);

🔗 3. Métodos da DLL usados pelo Java

A interface mapeia todos os métodos da DLL, por exemplo:

int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);
int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);
int ImprimeXMLSAT(String dados, int param);
int AbreGavetaElgin();
int SinalSonoro(int qtd, int tempoInicio, int tempoFim);


Cada método:

retorna 0 → sucesso

retorna outro número → erro da impressora

As assinaturas devem ser idênticas à DLL C, senão o programa trava.

🧠 4. Como o programa chama a DLL internamente
🔌 Abrir conexão
ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);

🖨️ Imprimir texto
ImpressoraDLL.INSTANCE.ImpressaoTexto("Teste", 1, 4, 0);

📄 Imprimir XML SAT
ImpressoraDLL.INSTANCE.ImprimeXMLSAT("path=C:\\...\\XMLSAT.xml", 0);

💵 Abrir gaveta
ImpressoraDLL.INSTANCE.AbreGaveta(1, 5, 10);

🔊 Emitir sinal sonoro
ImpressoraDLL.INSTANCE.SinalSonoro(4, 5, 5);

📟 Fluxo Completo do Programa (início → fim)
1️⃣ Usuário abre o terminal

O main() exibe o menu em um loop infinito.

2️⃣ Usuario escolhe uma opção

Exemplo:

2 - Abrir Conexao

3️⃣ O Java chama a DLL

O método da interface JNA é executado.

4️⃣ A DLL conversa com a impressora

Ela faz:

abrir porta USB/serial,

enviar comandos ESC/POS,

interpretar XML,

emitir som, etc.

5️⃣ Java recebe o retorno

Se 0 → OK
Se erro → mostra o código no terminal.

6️⃣ Ao encerrar

A função da DLL é chamada:

FechaConexaoImpressora();


E o programa termina.

📋 Funcionalidades do Sistema
🖨️ Impressões

Texto

QR Code

Código de barras

XML SAT

XML de cancelamento SAT

🎛️ Controles

Abrir gaveta padrão

Abrir gaveta Elgin

Emitir sinal sonoro

Cortar papel

🔧 Conexão

Configurar porta/modelo

Abrir conexão

Fechar conexão

▶️ Como executar o sistema
1. Compilar
javac -cp "lib/jna.jar" src/Main.java

2. Executar
java -cp "lib/jna.jar;src" Main


(Em Linux/Mac: usar : ao invés de ;)

🧩 Menu disponível
1 - Configurar Conexao
2 - Abrir Conexao
3 - Impressao Texto
4 - Impressao QRCode
5 - Impressao Cod Barras
6 - Impressao XML SAT
7 - Impressao XML Canc SAT
8 - Abrir Gaveta Elgin
9 - Abrir Gaveta
10 - Sinal Sonoro
0 - Fechar Conexao e Sair

🛑 Solução de Problemas
❗ DLL não encontrada

O caminho está errado

A DLL foi movida

Arquitetura errada (32/64 bits)

❗ Impressora não responde

Porta incorreta

USB desconectado

Outro programa usando a impressora

❗ XML não imprime

Caminho não começa com path=

XML mal formatado

Modelo da impressora não suporta

👥 Desenvolvedores

Ana Luiza Barea

Beatriz Firmado

Giovanna Totte

Guilherme Totte

📜 Licença

Projeto acadêmico — DLL pertence ao fabricante da impressora.

⭐ Dê uma estrela ⭐

Se este projeto te ajudou, deixe uma estrela no repositório!
