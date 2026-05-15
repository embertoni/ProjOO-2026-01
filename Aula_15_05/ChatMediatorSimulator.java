package Aula_15_05;

import java.util.ArrayList;
import java.util.List;

interface ChatMediator {
    void sendMessage(String msg, User user);
    void addUser(User user);
}

abstract class User {
    protected ChatMediator mediator;
    protected String name;

    public User(ChatMediator med, String name) {
        this.mediator = med;
        this.name = name;
    }

    public abstract void send(String msg);
    public abstract void receive(String msg);
}

class ChatMediatorImpl implements ChatMediator {
    private List<User> users;

    public ChatMediatorImpl() {
        this.users = new ArrayList<>();
    }

    @Override
    public void addUser(User user) {
        this.users.add(user);
    }

    @Override
    public void sendMessage(String msg, User user) {
        for (User u : this.users) {
            if (u != user) {
                u.receive(msg);
            }
        }
    }
}

class ChatUser extends User {
    public ChatUser(ChatMediator med, String name) {
        super(med, name);
    }

    @Override
    public void send(String msg) {
        System.out.println(this.name + " enviando mensagem:");
        System.out.println(this.name + ": Enviando -> " + msg);
        mediator.sendMessage(msg, this);
    }

    @Override
    public void receive(String msg) {
        System.out.println(this.name + ": Recebeu <- " + msg);
    }
}

public class ChatMediatorSimulator {
    public static void main(String[] args) {
        ChatMediator mediator = new ChatMediatorImpl();

        User user1 = new ChatUser(mediator, "Lina");
        User user2 = new ChatUser(mediator, "Bruno");
        User user3 = new ChatUser(mediator, "Renato");
        User user4 = new ChatUser(mediator, "Francisca");

        mediator.addUser(user1);
        mediator.addUser(user2);
        mediator.addUser(user3);
        mediator.addUser(user4);

        user1.send("Olá pessoal, tudo bem?");
        System.out.println();
        user3.send("Oi! Tudo bem sim, e com você?.");
    }
}