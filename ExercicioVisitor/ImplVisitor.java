package ExercicioVisitor;

interface RelatorioVisitor {
    void visit(RelatorioA A);
    void visit(RelatorioB B);
}

interface ElementoRelatorio {
    void accept(RelatorioVisitor visitor);
}

class RelatorioA implements ElementoRelatorio {
    @Override
    public void accept(RelatorioVisitor visitor) {
        visitor.visit(this);
    }
}

class RelatorioB implements ElementoRelatorio {
    @Override
    public void accept(RelatorioVisitor visitor) {
        visitor.visit(this);
    }
}

class PdfVisitor implements RelatorioVisitor {
    @Override
    public void visit(RelatorioA A) {
        System.out.println("Relatorio A emitido (PDF)");
    }

    @Override
    public void visit(RelatorioB B) {
        System.out.println("Relatorio B emitido (PDF)");
    }
}

class HtmlVisitor implements RelatorioVisitor {
    @Override
    public void visit(RelatorioA A) {
        System.out.println("Relatorio A emitido (HTML)");
    }

    @Override
    public void visit(RelatorioB B) {
        System.out.println("Relatorio B emitido (HTML)");
    }
}

class XmlVisitor implements RelatorioVisitor {
    @Override
    public void visit(RelatorioA A) {
        System.out.println("Relatorio A emitido (XML)");
    }

    @Override
    public void visit(RelatorioB B) {
        System.out.println("Relatorio B emitido (XML)");
    }
}

class CsvVisitor implements RelatorioVisitor {
    @Override
    public void visit(RelatorioA A) {
        System.out.println("Relatorio A emitido (CSV)");
    }

    @Override
    public void visit(RelatorioB B) {
        System.out.println("Relatorio B emitido (CSV)");
    }
}

public class ImplVisitor {
    static void main(String[] args) {
        ElementoRelatorio[] relatorios = { new RelatorioA(), new RelatorioB() };

        RelatorioVisitor pdf = new PdfVisitor();
        RelatorioVisitor html = new HtmlVisitor();
        RelatorioVisitor xml = new XmlVisitor();
        RelatorioVisitor csv = new CsvVisitor();

        System.out.println("--------- Executando ---------");

        for (ElementoRelatorio r : relatorios) {
            r.accept(pdf);
            r.accept(html);
            r.accept(xml);
            r.accept(csv);
            System.out.println("-----------------------------");
        }
    }
}