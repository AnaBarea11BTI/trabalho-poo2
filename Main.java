
import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;


public class Main {


    public interface ImpressoraDLL extends Library {


        ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                "C:\\Users\\Usuario\\Desktop\\UNASP\\UNASP\\Java-Aluno ensino-medio\\Java-Aluno EM\\Java-Aluno EM\\E1_Impressora01.dll",
                ImpressoraDLL.class
        );


        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);
        int FechaConexaoImpressora();
        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);
        int Corte(int avanco);
        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);
        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);
        int AvancaPapel(int linhas);
        int StatusImpressora(int param);
        int AbreGavetaElgin();
        int AbreGaveta(int pino, int ti, int tf);
        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);
        int ModoPagina();
        int LimpaBufferModoPagina();
        int ImprimeModoPagina();
        int ModoPadrao();
        int PosicaoImpressaoHorizontal(int posicao);
        int PosicaoImpressaoVertical(int posicao);
        int ImprimeXMLSAT(String dados, int param);
        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    }

    private static boolean conexaoAberta = false;
    private static int tipo;
    private static String modelo;
    private static String conexao;
    private static int parametro;
    private static final Scanner scanner = new Scanner(System.in);

    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }



    public static void configurarConexao() {
        if (!conexaoAberta) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Digite o tipo de conexão (ex: 1 para USB, 2 para serial, etc.): ");
            int tipo = scanner.nextInt();

            System.out.println("Digite o modelo da impressora (ex: i9):");
            String modelo = scanner.nextLine();
            scanner.nextLine();

            System.out.println("Digite a conexão (ex: USB): ");
            String conexao = scanner.nextLine();

            System.out.println("Digite o parâmetro (ex: 0): ");
            int parametro = scanner.nextInt();

            System.out.println("Conexão feita com sucesso.");


        } else {
            System.out.println("Conexão já configurada. Pronta para uso.");
        }
    }




    public static void abrirConexao() {
        if (!conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso.");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão aberta.");
        }
    }



    public static void fecharConexao() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
            if (retorno == 0) {
                conexaoAberta = false;
                System.out.println("Conexão fechada com sucesso.");
            } else {
                System.out.println("Erro ao fechar conexão. Código: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão aberta.");
        }
    }



    public static void impressaoTexto() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto("Teste de impressao", 1, 4, 0);

            if (retorno == 0) {
                System.out.println("Impressão realizada com sucesso!");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão aberta.");
        }
    }


    public static void impressaoQRCode() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode("Teste de impressao", 6, 4);

            if (retorno == 0) {
                System.out.println("Impressão realizada com sucesso!");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão aberta.");
        }
    }


    public static void impressaoCodigoBarras() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A012345678912", 100, 2, 3);

            if (retorno == 0) {
                System.out.println("Impressão realizada com sucesso!");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão aberta.");
        }
    }



    public static void ImprimeXMLSAT() {
        if (conexaoAberta) {

            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT("path=C:\\Users\\Usuario\\Desktop\\UNASP\\UNASP\\Java-Aluno ensino-medio\\Java-Aluno EM\\Java-Aluno EM\\XMLSAT.xml", 0);
            if (retorno == 0) {
                System.out.println("Impressão XMLSAT realizada com sucesso!");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão aberta.");
        }
    }


    public static void ImprimeXMLCancelamentoSAT() {
        if (conexaoAberta) {

            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT("C:\\Users\\Usuario\\Desktop\\UNASP\\UNASP\\Java-Aluno ensino-medio\\Java-Aluno EM\\Java-Aluno EM\\CANC_SAT.xml",  "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==", 0);
            if (retorno == 0) {
                System.out.println("Impressão XMLSAT realizada com sucesso!");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão aberta.");
        }
    }



    public static void abreGavetaElgin() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreGavetaElgin();
            if (retorno == 0) {
                System.out.println("Gaveta Elgin aberta! ");
            } else {
                System.out.println("Erro ao abrir gaveta Elgin. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão aberta.");
        }
    }



    public static void abreGaveta() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreGaveta(1, 5, 10);

            if (retorno == 0) {
                System.out.println("Gaveta aberta! ");
            } else {
                System.out.println("Erro ao abrir gaveta. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão aberta.");
        }
    }



    public static void sinalSonoro() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.SinalSonoro(4, 5, 5);
            if (retorno == 0) {
                System.out.println("Sinal sonoro emitido!");
            } else {
                System.out.println("Erro ao emitir sinal sonoro. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão aberta.");
        }
    }



    public static void main(String[] args) {

        // menu principal //

        while (true) {
            System.out.println("\n*************************************************");
            System.out.println("**************** MENU IMPRESSORA ****************");
            System.out.println("*************************************************\n");
            System.out.println("1  - Configurar Conexao");
            System.out.println("2  - Abrir Conexao");
            System.out.println("3  - Impressao Texto");
            System.out.println("4  - Impressao QRCode");
            System.out.println("5  - Impressao Cod Barras");
            System.out.println("6  - Impressao XML SAT");
            System.out.println("7  - Impressao XML Canc SAT");
            System.out.println("8  - Abrir Gaveta Elgin");
            System.out.println("9  - Abrir Gaveta");
            System.out.println("10 - Sinal Sonoro");
            System.out.println("0  - Fechar Conexao e Sair");

            String escolha = capturarEntrada("\nDigite a opção desejada: ");

            switch (escolha) {

                case "1":
                    configurarConexao();
                    break;
                case "2":
                    abrirConexao();
                    break;
                case "3":
                    impressaoTexto();
                    ImpressoraDLL.INSTANCE.Corte(5);
                    break;
                case "4":
                    impressaoQRCode();
                    ImpressoraDLL.INSTANCE.Corte(5);
                    break;
                case "5":
                    impressaoCodigoBarras();
                    ImpressoraDLL.INSTANCE.Corte(5);
                    break;
                case "6":
                    ImprimeXMLSAT();
                    ImpressoraDLL.INSTANCE.Corte(5);
                    break;
                case "7":
                    ImprimeXMLCancelamentoSAT();
                    ImpressoraDLL.INSTANCE.Corte(5);
                    break;
                case "8":
                    abreGavetaElgin();
                    break;
                case "9":
                    abreGaveta();
                    break;
                case "10":
                    sinalSonoro();
                    break;
                case "0":
                    fecharConexao();
                    System.out.println("Programa encerrado.");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static String lerArquivoComoString(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        byte[] data = fis.readAllBytes();
        fis.close();
        return new String(data, StandardCharsets.UTF_8);
    }
}