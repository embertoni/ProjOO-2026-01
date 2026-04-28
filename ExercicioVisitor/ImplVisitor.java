package ExercicioVisitor;

interface RelatorioVisitor {
    void visit(RelatorioVendas vendas);
    void visit(RelatorioEstoque estoque);
}

interface ElementoRelatorio {
    void accept(RelatorioVisitor visitor);
}

class RelatorioVendas implements ElementoRelatorio {
    @Override
    public void accept(RelatorioVisitor visitor) {
        visitor.visit(this);
    }
}

class RelatorioEstoque implements ElementoRelatorio {
    @Override
    public void accept(RelatorioVisitor visitor) {
        visitor.visit(this);
    }
}

class PdfVisitor implements RelatorioVisitor {
    @Override
    public void visit(RelatorioVendas vendas) {
        System.out.println("Relatorio de Vendas emitido (PDF)");
    }

    @Override
    public void visit(RelatorioEstoque estoque) {
        System.out.println("Relatorio de Estoque emitido (PDF)");
    }
}

class HtmlVisitor implements RelatorioVisitor {
    @Override
    public void visit(RelatorioVendas vendas) {
        System.out.println("Relatorio de Vendas emitido (HTML)");
    }

    @Override
    public void visit(RelatorioEstoque estoque) {
        System.out.println("Relatorio de Estoque emitido (HTML)");
    }
}

class JsonVisitor implements RelatorioVisitor {
    @Override
    public void visit(RelatorioVendas vendas) {
        System.out.println("Relatorio de Vendas emitido (JSON)");
    }

    @Override
    public void visit(RelatorioEstoque estoque) {
        System.out.println("Relatorio de Estoque emitido (JSON)");
    }
}

class CsvVisitor implements RelatorioVisitor {
    @Override
    public void visit(RelatorioVendas vendas) {
        System.out.println("Relatorio de Vendas emitido (CSV)");
    }

    @Override
    public void visit(RelatorioEstoque estoque) {
        System.out.println("Relatorio de Estoque emitido (CSV)");
    }
}

public class ImplVisitor {
    static void main(String[] args) {
        ElementoRelatorio[] relatorios = { new RelatorioVendas(), new RelatorioEstoque() };

        RelatorioVisitor pdf = new PdfVisitor();
        RelatorioVisitor html = new HtmlVisitor();
        RelatorioVisitor json = new JsonVisitor();
        RelatorioVisitor csv = new CsvVisitor();

        System.out.println("--------- Executando ---------");

        for (ElementoRelatorio r : relatorios) {
            r.accept(pdf);
            r.accept(html);
            r.accept(json);
            r.accept(csv);
            System.out.println("-----------------------------");
        }
    }
}