# 🖨️ Sistema de Controle de Impressora Fiscal

Este sistema foi desenvolvido em **Java** e utiliza o **JNA (Java Native Access)** para conversar diretamente com um arquivo especial chamado **DLL**, que é o responsável por controlar a impressora fiscal.

Ele funciona totalmente pelo **terminal (a tela preta)** e permite fazer impressões, abrir gavetas e emitir sons.  
O objetivo é ser um sistema simples, direto e educativo.

---

## 📋 Funcionalidades

A seguir, tudo o que o sistema consegue fazer:

### 🖨️ Funções de Impressão
- **Imprimir textos comuns** (como recibos)
- **Imprimir QR Codes**
- **Imprimir códigos de barras**
- **Imprimir um arquivo XML SAT**
- **Imprimir um arquivo XML de cancelamento SAT**

Esses XMLs são arquivos que a impressora fiscal entende para realizar uma venda ou cancelar uma venda.

---

### 🎛️ Controles da Impressora
- **Abrir a gaveta padrão**  
  (quando você quer abrir a gaveta de dinheiro ligada à impressora)
- **Abrir a gaveta Elgin**  
  (para impressoras Elgin que têm abertura especial)
- **Emitir sinal sonoro**  
  (um "bip" da impressora)

---

### 🔧 Conexão da Impressora
- O sistema permite **configurar** como a impressora está conectada  
- Você pode **abrir a conexão** para começar a usar  
- E pode **fechar a conexão** quando terminar

---

## 🛠️ Tecnologias Utilizadas

- **Java 11 ou superior**
- **JNA (Java Native Access)** — responsável por acessar a DLL
- **DLL:** `E1_Impressora01.dll` — arquivo que controla a impressora
- **Scanner (entrada pelo teclado)** — para ler o que o usuário digita

---

## 📁 Arquivos Necessários

Esses são os arquivos usados no sistema:

| Arquivo | Para que serve |
|--------|----------------|
| **E1_Impressora01.dll** | É o arquivo principal que permite o Java conversar com a impressora |
| **XMLSAT.xml** | Arquivo para imprimir uma venda SAT |
| **CANC_SAT.xml** | Arquivo para imprimir um cancelamento SAT |

---

## 📂 Estrutura Sugerida do Projeto

Para manter tudo organizado, você pode deixar os arquivos assim:

projeto/
├── src/
│ └── Main.java
├── lib/
│ ├── jna.jar
│ └── E1_Impressora01.dll
└── xml/
├── XMLSAT.xml
└── CANC_SAT.xml

yaml
Copiar código

Cada pasta tem seu propósito:  
- **src** → onde fica o código  
- **lib** → onde ficam as bibliotecas  
- **xml** → onde ficam os arquivos XML  

---

## 🚀 Como Executar

Aqui está o passo a passo para rodar o programa.

### 1. Compilar o código
Abra o terminal dentro da pasta do projeto e escreva:

javac -cp "jna.jar" Main.java

r
Copiar código

Isso vai transformar o código em algo que o Java consegue executar.

### 2. Rodar o programa

java -cp ".;jna.jar" Main

yaml
Copiar código

Pronto! O programa vai abrir no terminal e você verá o menu.

---

## 📟 Menu do Programa

É por aqui que você escolhe o que o sistema vai fazer:

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

arduino
Copiar código

Você só precisa digitar o número da opção e apertar **ENTER**.

---

## 🔧 Métodos da DLL utilizados

Essas são as funções internas que conversam diretamente com a impressora:

```java
int ConfiguraModeloImpressora(int modelo);
int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);
int FechaConexaoImpressora();

int ImpressaoTexto(String texto, int posicao, int estilo, int tamanho);
int ImpressaoQRCode(String dados, int tamanho, int nivel);
int ImpressaoCodigoBarras(String dados, int tipo, int altura, int largura);

int ImprimirXMLSAT(String caminho);
int ImprimirXMLCancelamentoSAT(String caminho);

int AbreGaveta();
int AbreGavetaElgin();
int AcionaSinalSonoro(int qtde, int intensidade, int duracao);
```
Você não precisa decorar esses métodos, o programa já usa todos automaticamente.


🧩 Solução de Problemas

Aqui estão os erros mais comuns e como resolver.

❗ DLL não encontrada

Verifique se escolheu o arquivo certo quando o sistema pedir

Confira se a DLL é compatível com seu Windows (32 ou 64 bits)

❗ Erros ao imprimir XML

O caminho do arquivo pode estar errado

O XML pode estar com defeito

O arquivo pode não ser aceito pela impressora

❗ Impressora não responde

A porta configurada pode estar errada

O cabo USB pode estar solto

Outro programa pode estar usando a impressora no mesmo momento

👥 Desenvolvedores

Ana Luiza Barea

Beatriz Firmado

Giovanna Totte

Guilherme Totte

📜 Licença
Este projeto foi desenvolvido apenas para fins educacionais.
A DLL utilizada pertence ao fabricante da impressora.

⭐ Se este projeto te ajudou, considere deixar uma estrela no repositório!
