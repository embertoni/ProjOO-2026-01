interface Notificacao {
    void enviar(String mensagem);
}

class NotificacaoEmail implements Notificacao {
    public void enviar(String msg) {
        System.out.println("Email - Servidor " + ConfiguracaoGlobal.INSTANCIA.getServidorEnvio() + ": " + msg);
    }
}

class NotificacaoPush implements Notificacao {
    public void enviar(String msg) {
        System.out.println("PUSH - Notificacao: " + msg);
    }
}

class ApiExternaSMS {
    public void send(String payload) {
        System.out.println("SMS (API Externa) - Enviando: " + payload);
    }
}

class AdaptadorSMSExterno implements Notificacao {
    private ApiExternaSMS apiExterna;

    public AdaptadorSMSExterno() {
        this.apiExterna = new ApiExternaSMS();
    }

    @Override
    public void enviar(String mensagem) {
        apiExterna.send(mensagem);
    }
}

class NotificacaoProxy implements Notificacao {
    private Notificacao notificacaoReal;
    private int tentativasRealizadas = 0;
    private boolean usuarioTemPermissao = true;

    public NotificacaoProxy(Notificacao notificacaoReal) {
        this.notificacaoReal = notificacaoReal;
    }

    @Override
    public void enviar(String mensagem) {
        System.out.println("\n[PROXY LOG] Interceptando requisicao de envio...");

        if (!usuarioTemPermissao) {
            System.err.println("PROXY AVISO - Acesso negado. Usuario sem permissao para enviar notificacoes.");
            return;
        }

        int maxTentativas = ConfiguracaoGlobal.INSTANCIA.getMaxTentativas();
        if (tentativasRealizadas >= maxTentativas) {
            System.err.println("PROXY AVISO - Falha no envio. Limite maximo de " + maxTentativas + " tentativas atingido.");
            return;
        }

        tentativasRealizadas++;
        System.out.println("PROXY LOG - Validação OK (Tentativa " + tentativasRealizadas + "/" + maxTentativas + "). Encaminhando...");
        
        notificacaoReal.enviar(mensagem);
    }
}

enum ConfiguracaoGlobal {
    INSTANCIA;
    private String nomeApp = "Sistema Notificacoes";
    private String servidorEnvio = "smtp.unifesp.br";
    private int maxTentativas = 3;

    public String getNomeApp() { return nomeApp; }
    public String getServidorEnvio() { return servidorEnvio; }
    public int getMaxTentativas() { return maxTentativas; }
}

class NotificacaoFactory {
    public static Notificacao criarNotificacao(String tipo) {
        if (tipo == null) return null;
        
        Notificacao notificacaoBase;
        
        switch (tipo.toLowerCase()) {
            case "email" -> notificacaoBase = new NotificacaoEmail();
            case "sms" -> notificacaoBase = new AdaptadorSMSExterno();
            case "push" -> notificacaoBase = new NotificacaoPush();
            default -> throw new IllegalArgumentException("Tipo invalido: " + tipo);
        }
        
        return new NotificacaoProxy(notificacaoBase);
    }
}

class Main {
    public static void main(String[] args) {
        ConfiguracaoGlobal config = ConfiguracaoGlobal.INSTANCIA;
        
        System.out.println("--- " + config.getNomeApp().toUpperCase() + " ---");
        System.out.println("Configuracao Singleton ativa no servidor: " + config.getServidorEnvio());
        System.out.println("------------------------------------------");

        try {
            Notificacao n1 = NotificacaoFactory.criarNotificacao("email");
            n1.enviar("Sua nota foi publicada no sistema.");

            Notificacao n2 = NotificacaoFactory.criarNotificacao("sms");
            n2.enviar("Alerta de seguranca: login detectado.");

            Notificacao n3 = NotificacaoFactory.criarNotificacao("push");
            n3.enviar("Mensagem 1");
            n3.enviar("Mensagem 2");
            n3.enviar("Mensagem 3");
            n3.enviar("Mensagem 4 (Esta deve ser bloqueada pelo Proxy)"); 

        } catch (Exception e) {
            System.err.println("Erro na execucao: " + e.getMessage());
        }
    }
}