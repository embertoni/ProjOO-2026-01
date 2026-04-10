interface Notificacao {
    void enviar(String mensagem);
}

class NotificacaoEmail implements Notificacao {
    public void enviar(String msg) {
        System.out.println("Email - Servidor " + ConfiguracaoGlobal.INSTANCIA.getServidorEnvio() + ": " + msg);
    }
}

class NotificacaoSMS implements Notificacao {
    public void enviar(String msg) {
        System.out.println("SMS - Enviando: " + msg);
    }
}

class NotificacaoPush implements Notificacao {
    public void enviar(String msg) {
        System.out.println("PUSH - Notificacao: " + msg);
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
        return switch (tipo.toLowerCase()) {
            case "email" -> new NotificacaoEmail();
            case "sms" -> new NotificacaoSMS();
            case "push" -> new NotificacaoPush();
            default -> throw new IllegalArgumentException("Tipo invalido: " + tipo);
        };
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
            n3.enviar("Voce tem uma nova mensagem na plataforma.");

            System.out.println("\n--- Validacao de Padroes ---");
            ConfiguracaoGlobal checagem = ConfiguracaoGlobal.INSTANCIA;
            System.out.println("Mesma instancia de configuracao? " + (config == checagem));
            
        } catch (Exception e) {
            System.err.println("Erro na execucao: " + e.getMessage());
        }
    }
}
