class TV {
    public void ligar() { 
        System.out.println("TV ligada."); 
    }
    public void desligar() { 
        System.out.println("TV desligada."); 
    }
}

class Projetor {
    public void ligar() { 
        System.out.println("Projetor ligado."); 
    }
    public void desligar() { 
        System.out.println("Projetor desligado."); 
    }
    public void modoWidescreen() { 
        System.out.println("Projetor configurado para Widescreen."); 
    }
}

class Receiver {
    public void ligar() { 
        System.out.println("Receiver ligado."); 
    }
    public void desligar() { 
        System.out.println("Receiver desligado."); 
    }
    public void definirVolume(int nivel) { 
        System.out.println("Volume do receiver ajustado para " + nivel + "."); 
    }
}

class PlayerMidia {
    public void ligar() { 
        System.out.println("Player de Mídia ligado."); 
    }
    public void desligar() { 
        System.out.println("Player de Mídia desligado."); 

    }
    public void reproduzir(String midia) { 
        System.out.println("Reproduzindo: " + midia); 
    }
}

class SistemaSom {
    public void ligar() { 
        System.out.println("Sistema de Som ligado."); 
    }
    public void desligar() { 
        System.out.println("Sistema de Som desligado."); 
    }
    public void ativarSurround() { 
        System.out.println("Som Surround 5.1 ativado."); 
    }
}

class LuzAmbiente {
    public void escurecer() { 
        System.out.println("Luzes escurecidas para o modo cinema."); 
    }
    public void acender() { 
        System.out.println("Luzes acesas."); 
    }
}

class HomeTheaterFacade {
    private TV tv;
    private Projetor projetor;
    private Receiver receiver;
    private PlayerMidia playerMidia;
    private SistemaSom sistemaSom;
    private LuzAmbiente luzAmbiente;

    public HomeTheaterFacade(TV tv, Projetor projetor, Receiver receiver, 
                             PlayerMidia playerMidia, SistemaSom sistemaSom, LuzAmbiente luzAmbiente) {
        this.tv = tv;
        this.projetor = projetor;
        this.receiver = receiver;
        this.playerMidia = playerMidia;
        this.sistemaSom = sistemaSom;
        this.luzAmbiente = luzAmbiente;
    }

    public void assistirFilme(String filme) {
        System.out.println("\n--- Preparando o ambiente para Assistir Filme ---");
        luzAmbiente.escurecer();
        projetor.ligar();
        projetor.modoWidescreen();
        receiver.ligar();
        receiver.definirVolume(50);
        sistemaSom.ligar();
        sistemaSom.ativarSurround();
        playerMidia.ligar();
        playerMidia.reproduzir(filme);
    }

    public void ouvirMusica(String musica) {
        System.out.println("\n--- Preparando o ambiente para Ouvir Música ---");
        luzAmbiente.acender();
        receiver.ligar();
        receiver.definirVolume(30);
        sistemaSom.ligar();
        playerMidia.ligar();
        playerMidia.reproduzir(musica);
    }

    public void desligarTudo() {
        System.out.println("\n--- Desligando todo o Home Theater ---");
        playerMidia.desligar();
        sistemaSom.desligar();
        receiver.desligar();
        projetor.desligar();
        tv.desligar();
        luzAmbiente.acender(); 
    }
}

class FacadeHomeTheater {
    public static void main(String[] args) {
        TV tv = new TV();
        Projetor projetor = new Projetor();
        Receiver receiver = new Receiver();
        PlayerMidia player = new PlayerMidia();
        SistemaSom som = new SistemaSom();
        LuzAmbiente luz = new LuzAmbiente();

        HomeTheaterFacade homeTheater = new HomeTheaterFacade(tv, projetor, receiver, player, som, luz);

        homeTheater.assistirFilme("O Enigma do Outro Lado");
        homeTheater.ouvirMusica("Trilha Sonora Original");
        homeTheater.desligarTudo();
    }
}