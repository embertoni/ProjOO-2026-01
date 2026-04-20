package PadraoFeriado;

import java.util.ArrayList;
import java.util.List;

public class ImplPadroes {
    static class ConfiguracaoSistema {
        private static ConfiguracaoSistema instancia;
        private boolean sistemaPagamentoOnline = true;

        private ConfiguracaoSistema() {}

        public static ConfiguracaoSistema getInstancia() {
            if (instancia == null) instancia = new ConfiguracaoSistema();
            return instancia;
        }

        public boolean isPagamentoOnline() { return sistemaPagamentoOnline; }
    }

    interface Bebida {
        String getDescricao();
        double getPreco();
    }

    static class CafeExpresso implements Bebida {
        public String getDescricao() { return "Cafe Expresso"; }
        public double getPreco() { return 5.0; }
    }

    static abstract class BebidaDecorator implements Bebida {
        protected Bebida bebida;
        public BebidaDecorator(Bebida bebida) { this.bebida = bebida; }
        public String getDescricao() { return bebida.getDescricao(); }
        public double getPreco() { return bebida.getPreco(); }
    }

    static class AdicionalLeite extends BebidaDecorator {
        public AdicionalLeite(Bebida bebida) { super(bebida); }
        public String getDescricao() { return bebida.getDescricao() + " + Leite"; }
        public double getPreco() { return bebida.getPreco() + 2.0; }
    }

    static class AdicionalChantilly extends BebidaDecorator {
        public AdicionalChantilly(Bebida bebida) { super(bebida); }
        public String getDescricao() { return bebida.getDescricao() + " + Chantilly"; }
        public double getPreco() { return bebida.getPreco() + 3.0; }
    }

    interface EstrategiaPagamento {
        void realizarPagamento(double valor);
    }

    static class PagamentoPix implements EstrategiaPagamento {
        public void realizarPagamento(double valor) {
            System.out.println("STRATEGY - Pagamento de R$" + valor + " processado via PIX.");
        }
    }

    static class PagamentoCartao implements EstrategiaPagamento {
        public void realizarPagamento(double valor) {
            System.out.println("STRATEGY - Pagamento de R$" + valor + " processado via Cartao de Credito.");
        }
    }

    interface ProcessadorPagamento {
        boolean processar(EstrategiaPagamento estrategia, double valor);
    }

    static class ProcessadorReal implements ProcessadorPagamento {
        public boolean processar(EstrategiaPagamento estrategia, double valor) {
            estrategia.realizarPagamento(valor);
            return true;
        }
    }

    static class ProxyPagamento implements ProcessadorPagamento {
        private ProcessadorReal processadorReal;

        public boolean processar(EstrategiaPagamento estrategia, double valor) {
            System.out.println("PROXY - Verificando disponibilidade do sistema de pagamentos...");
            if (ConfiguracaoSistema.getInstancia().isPagamentoOnline()) {
                if (processadorReal == null) processadorReal = new ProcessadorReal();
                return processadorReal.processar(estrategia, valor);
            } else {
                System.out.println("PROXY - ERRO: Sistema de pagamento offline. Pedido cancelado.");
                return false;
            }
        }
    }

    static class SistemaEstoqueAntigo {
        public void removerProduto(String nomeProduto) {
            System.out.println("SISTEMA LEGADO - Subtraindo ingrediente do banco de dados antigo: " + nomeProduto);
        }
    }

    interface ControleEstoque {
        void darBaixa(Bebida bebida);
    }

    static class AdaptadorEstoque implements ControleEstoque {
        private SistemaEstoqueAntigo estoqueAntigo = new SistemaEstoqueAntigo();

        public void darBaixa(Bebida bebida) {
            System.out.println("ADAPTER - Traduzindo pedido para o sistema antigo de estoque...");
            estoqueAntigo.removerProduto(bebida.getDescricao());
        }
    }

    interface Observador {
        void atualizar(String status);
    }

    static class PainelCliente implements Observador {
        private String nomeCliente;
        public PainelCliente(String nome) { this.nomeCliente = nome; }
        public void atualizar(String status) {
            System.out.println("OBSERVER - Status " + nomeCliente + ": " + status);
        }
    }

    static class Pedido {
        private List<Observador> observadores = new ArrayList<>();
        private String status;

        public void adicionarObservador(Observador obs) { observadores.add(obs); }
        public void setStatus(String status) {
            this.status = status;
            notificar();
        }
        private void notificar() {
            for (Observador obs : observadores) obs.atualizar(status);
        }
    }

    interface Notificador {
        void enviar(String mensagem);
    }

    static class EmailNotificador implements Notificador {
        public void enviar(String mensagem) { System.out.println("FACTORY - Enviando recibo por EMAIL: " + mensagem); }
    }

    static class AppNotificador implements Notificador {
        public void enviar(String mensagem) { System.out.println("FACTORY - Enviando recibo por PUSH NO APP: " + mensagem); }
    }

    static class NotificadorFactory {
        public static Notificador criar(String tipo) {
            if (tipo.equalsIgnoreCase("EMAIL")) return new EmailNotificador();
            if (tipo.equalsIgnoreCase("APP")) return new AppNotificador();
            throw new IllegalArgumentException("Tipo de notificacao invalido.");
        }
    }

    static class AtendenteFacade {
        private ProcessadorPagamento proxyPagamento = new ProxyPagamento();
        private ControleEstoque controleEstoque = new AdaptadorEstoque();

        public void processarPedido(Bebida bebida, EstrategiaPagamento pagamento, String tipoRecibo, Observador cliente) {
            System.out.println("\n=== FACADE - INICIANDO NOVO PEDIDO ===");
            System.out.println("Resumo: " + bebida.getDescricao() + " | Total: R$" + bebida.getPreco());

            boolean pago = proxyPagamento.processar(pagamento, bebida.getPreco());
            
            if (pago) {
                controleEstoque.darBaixa(bebida);
                Pedido pedido = new Pedido();
                pedido.adicionarObservador(cliente);
                pedido.setStatus("Preparando a bebida...");
                pedido.setStatus("Pronto para retirada!");

                Notificador notificador = NotificadorFactory.criar(tipoRecibo);
                notificador.enviar("Obrigado pela compra! Valor: R$" + bebida.getPreco());
                
                System.out.println("=== FACADE - PEDIDO CONCLUIDO COM SUCESSO ===\n");
            }
        }
    }

    public static void main(String[] args) {
        ConfiguracaoSistema.getInstancia();

        PainelCliente clienteCarlos = new PainelCliente("Carlos");

        Bebida bebidaDoCarlos = new CafeExpresso();
        bebidaDoCarlos = new AdicionalLeite(bebidaDoCarlos);
        bebidaDoCarlos = new AdicionalChantilly(bebidaDoCarlos);

        EstrategiaPagamento pagamentoCarlos = new PagamentoPix();

        AtendenteFacade sistemaCaixa = new AtendenteFacade();

        sistemaCaixa.processarPedido(bebidaDoCarlos, pagamentoCarlos, "APP", clienteCarlos);
    }
}