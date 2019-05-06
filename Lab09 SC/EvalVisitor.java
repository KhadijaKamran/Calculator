
import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends LabeledExprBaseVisitor<Integer> {
    // variable value pair and memory for the calculator
    Map<String, Integer> memory = new HashMap<String, Integer>();

    // ID '=' expr NEWLINE is the format
    @Override
    public Integer visitAssign(LabeledExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();  // ithe left side of equal operator is id
        int value = visit(ctx.expr());   // expression on right side of equal operator is calculated and stored in memory
        memory.put(id, value);           
        return value;
    }

    // expr NEWLINE format
    @Override
    public Integer visitPrintExpr(LabeledExprParser.PrintExprContext ctx) {
        Integer value = visit(ctx.expr()); // next child expression is evaluated
        System.out.println(value);         // The result is printed
        return 0;                          
    }

    // INT 
    @Override
    public Integer visitInt(LabeledExprParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    // ID
    @Override
    public Integer visitId(LabeledExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if ( memory.containsKey(id) ) return memory.get(id);
        return 0;
    }

    // expr op=('*'|'/') expr 
    @Override
    public Integer visitMulDiv(LabeledExprParser.MulDivContext ctx) {
        int left = visit(ctx.expr(0));  // value of left subexpression
        int right = visit(ctx.expr(1)); // value of right subexpression
        if ( ctx.op.getType() == LabeledExprParser.MUL ) return left * right;
        return left / right; 
    }

    // expr op=('+'|'-') expr
    @Override
    public Integer visitAddSub(LabeledExprParser.AddSubContext ctx) {
        int left = visit(ctx.expr(0));  // value of left subexpression
        int right = visit(ctx.expr(1)); // value of right subexpression
        if ( ctx.op.getType() == LabeledExprParser.ADD ) return left + right;
        return left - right; 
    }

    // '(' expr ')' 
    @Override
    public Integer visitParens(LabeledExprParser.ParensContext ctx) {
        return visit(ctx.expr()); // value of child expression is returned
    }
}
