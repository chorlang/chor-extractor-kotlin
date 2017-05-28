package ast.sp.nodes.expr;


public enum Operand {
    EQUAL("=="),
    NOTEQUAL("!="),
    AND("&&"),
    OR("||");

    private String text;

    Operand(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static Operand fromString(String text) {
        for (Operand b : Operand.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}

