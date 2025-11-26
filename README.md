# 🖨️ Sistema de Controle de Impressora Fiscal

Este sistema foi desenvolvido em Java e utiliza o JNA (Java Native Access) para conversar diretamente com um arquivo especial chamado DLL, que é o responsável por controlar a impressora fiscal.

A DLL contém todas as funções nativas que realmente enviam comandos para a impressora: abrir conexão, imprimir, abrir gaveta, etc.
O papel do Java é somente chamar essas funções.

O sistema funciona totalmente pelo terminal (a tela preta) e permite fazer impressões, abrir gavetas e emitir sons.
O objetivo é ser um sistema simples, direto e educativo.

---

## 📋 Funcionalidades

A seguir, tudo o que o sistema consegue fazer:

## 🖨️ Funções de Impressão

### 🖨️ Funções de Impressão
- **Imprimir textos comuns** (como recibos)
- **Imprimir QR Codes**
- **Imprimir códigos de barras**
- **Imprimir um arquivo XML SAT**
- **Imprimir um arquivo XML de cancelamento SAT**

Esses XMLs são arquivos que a impressora fiscal entende para realizar uma venda ou cancelar uma venda.

---
### 🎛️ Controles da Impressora

- Abrir a gaveta padrão

- Abrir a gaveta Elgin

- Emitir sinal sonoro

---

### 🔧 Conexão da Impressora

O sistema permite configurar a forma de conexão com a impressora

Abrir a conexão

Fechar a conexão

---

### 🛠️ Tecnologias Utilizadas

Java 11 ou superior

JNA (Java Native Access) — responsável por acessar a DLL

DLL: E1_Impressora01.dll — arquivo que controla a impressora

Scanner (entrada pelo teclado) — para ler o que o usuário digita

--- 

## 🔌 COMO O CÓDIGO USA A DLL

Essa é a parte mais importante do sistema. Aqui está o que o seu código faz por trás dos panos.

### 📥 1. Carregando a DLL através do JNA

Logo no início do código existe a criação de uma interface:

public interface ImpressoraDLL extends Library {


Essa interface representa a DLL dentro do Java.

E a DLL é carregada assim:

ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
    "C:\\...\\E1_Impressora01.dll",
    ImpressoraDLL.class
);

O que acontece aqui:

O JNA carrega a DLL na memória

Lê os métodos dentro dela

Cria o objeto INSTANCE
→ Esse objeto permite chamar funções da DLL como se fosse Java

Exemplo:

ImpressoraDLL.INSTANCE.ImpressaoTexto(...);


Isso chama diretamente código nativo.

### 🔗 2. Mapeando os métodos da DLL

Dentro da interface você tem vários métodos como:

int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);
int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);
int ImprimeXMLSAT(String dados, int param);


Esses métodos precisam ter exatamente a mesma assinatura dos métodos na DLL original.
Se estiver diferente → o programa trava.

### 📞 3. Como o Java chama a DLL no código

O Java NÃO imprime nada sozinho.
Ele apenas pede para a DLL fazer.

Exemplo: abrir conexão
int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);

Exemplo: imprimir texto
ImpressoraDLL.INSTANCE.ImpressaoTexto("Teste de impressao", 1, 4, 0);

Exemplo: imprimir XML SAT
ImpressoraDLL.INSTANCE.ImprimeXMLSAT("path=C:\\...\\XMLSAT.xml", 0);

### 🔁 4. Fluxo completo do uso da DLL durante o programa

Usuário configura a conexão

O sistema chama a DLL para abrir a conexão

O usuário escolhe o tipo de impressão

O Java repassa o comando para a DLL

texto

QR Code

código de barras

XML SAT

XML Cancelamento

A DLL faz:

enviar comandos de impressão

movimentar cabeçote

abrir gaveta

emitir som

O Java mostra o resultado no terminal

Quando o usuário sai, o Java chama:

FechaConexaoImpressora();

---

## 📁 Arquivos Necessários
Arquivo	Para que serve
E1_Impressora01.dll	É o arquivo principal que permite o Java conversar com a impressora
XMLSAT.xml	Arquivo para imprimir uma venda SAT
CANC_SAT.xml	Arquivo para imprimir um cancelamento SAT

---
## 📂 Estrutura Sugerida do Projeto

Para manter tudo organizado, você pode deixar os arquivos assim:

projeto/
├── src/
│   └── Main.java
├── lib/
│   ├── jna.jar
│   └── E1_Impressora01.dll
└── xml/
    ├── XMLSAT.xml
    └── CANC_SAT.xml


Cada pasta tem seu propósito:

src → onde fica o código

lib → bibliotecas externas

xml → arquivos XML para impressão

---

## 🚀 Como Executar
1. Compilar o código
javac -cp "jna.jar" Main.java

2. Rodar o programa
java -cp ".;jna.jar" Main

## 📟 Menu do Programa
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


Você só precisa digitar o número desejado.


---

## 🔧 Métodos da DLL utilizados
int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);
int FechaConexaoImpressora();

int ImpressaoTexto(String texto, int posicao, int estilo, int tamanho);
int ImpressaoQRCode(String dados, int tamanho, int nivel);
int ImpressaoCodigoBarras(String dados, int tipo, int altura, int largura);

int ImprimeXMLSAT(String caminho);
int ImprimeXMLCancelamentoSAT(String caminho);

int AbreGaveta();
int AbreGavetaElgin();
int AcionaSinalSonoro(int qtde, int intensidade, int duracao);


---

## 🧩 Solução de Problemas
❗ DLL não encontrada

Caminho errado

Arquitetura errada (32/64 bits)

❗ Impressora não responde

Porta incorreta

Cabo USB desconectado

❗ XML não imprime

Caminho precisa começar com path=

XML mal formatado


---

## 👥 Desenvolvedores

Ana Luiza Barea
Beatriz Firmado
Giovanna Totte
Guilherme Totte


---

## 📜 Licença

Este projeto foi desenvolvido apenas para fins educacionais.
A DLL utilizada pertence ao fabricante da impressora.

---

## ⭐ Se este projeto te ajudou, deixe uma estrela no repositório!
