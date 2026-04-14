interface Bebida {
    String getDescricao();
    double getCusto();
}

class CafeExpresso implements Bebida {
    @Override
    public String getDescricao() {
        return "Café Expresso";
    }

    @Override
    public double getCusto() {
        return 5.00;
    }
}

class Cappuccino implements Bebida {
    @Override
    public String getDescricao() {
        return "Cappuccino";
    }

    @Override
    public double getCusto() {
        return 7.50;
    }
}

class Cha implements Bebida {
    @Override
    public String getDescricao() {
        return "Chá";
    }

    @Override
    public double getCusto() {
        return 4.00;
    }
}

abstract class AdicionalDecorator implements Bebida {
    protected Bebida bebidaDecorada;

    public AdicionalDecorator(Bebida bebida) {
        this.bebidaDecorada = bebida;
    }

    @Override
    public String getDescricao() {
        return bebidaDecorada.getDescricao();
    }

    @Override
    public double getCusto() {
        return bebidaDecorada.getCusto();
    }
}

class Leite extends AdicionalDecorator {
    public Leite(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + ", Leite";
    }

    @Override
    public double getCusto() {
        return super.getCusto() + 1.50;
    }
}

class Chantilly extends AdicionalDecorator {
    public Chantilly(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + ", Chantilly";
    }

    @Override
    public double getCusto() {
        return super.getCusto() + 2.00;
    }
}

class Canela extends AdicionalDecorator {
    public Canela(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + ", Canela";
    }

    @Override
    public double getCusto() {
        return super.getCusto() + 0.50;
    }
}

class CaldaChocolate extends AdicionalDecorator {
    public CaldaChocolate(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + ", Calda de Chocolate";
    }

    @Override
    public double getCusto() {
        return super.getCusto() + 2.50;
    }
}

public class DecoratorCafeteria {
    public static void main(String[] args) {
        
        System.out.println("=== Sistema de Cafeteria (Padrão Decorator) ===\n");

        Bebida pedido1 = new CafeExpresso();
        System.out.println("Pedido 1: " + pedido1.getDescricao());
        System.out.println("Custo: R$ " + String.format("%.2f", pedido1.getCusto()) + "\n");

        Bebida pedido2 = new Cappuccino();
        pedido2 = new Canela(pedido2);
        pedido2 = new Chantilly(pedido2);
        
        System.out.println("Pedido 2: " + pedido2.getDescricao());
        System.out.println("Custo: R$ " + String.format("%.2f", pedido2.getCusto()) + "\n");

        Bebida pedido3 = new Cha();
        pedido3 = new Leite(pedido3);
        pedido3 = new CaldaChocolate(pedido3);
        pedido3 = new Chantilly(pedido3);
        
        System.out.println("Pedido 3: " + pedido3.getDescricao());
        System.out.println("Custo: R$ " + String.format("%.2f", pedido3.getCusto()) + "\n");
    }
}