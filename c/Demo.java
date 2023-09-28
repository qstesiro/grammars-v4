import java.util.Stack;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Demo {

    public static void main(String[] args) {
        try {
            new Demo().execute(args[0]);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    void execute(String path) throws Exception {
        new ParseTreeWalker().walk(
            new Listener(),
            new CParser(
                new CommonTokenStream(
                    new CLexer(
                        CharStreams.fromFileName(path)
                    )
                )
            ).compilationUnit()
        );
    }

    static class Listener extends CBaseListener {

        @Override
        public void enterDirectDeclarator(CParser.DirectDeclaratorContext context) {
            CParser.DirectDeclaratorContext directDeclarator = context.directDeclarator();
            TerminalNode leftParen = context.LeftParen();
            TerminalNode rightParen = context.RightParen();
            if (directDeclarator != null && leftParen != null && rightParen != null) {
                TerminalNode identifier = directDeclarator.Identifier();
                if (identifier != null) {
                    System.out.printf("function define: %s\n", identifier.getText());
                }
            }
        }

        @Override
        public void enterPostfixExpression(CParser.PostfixExpressionContext context) {
            CParser.PrimaryExpressionContext primaryExpression = context.primaryExpression();
            List<TerminalNode> leftParen = context.LeftParen();
            List<TerminalNode> rightParen = context.RightParen();
            if (primaryExpression != null && leftParen.size() > 0 && rightParen.size() > 0) {
                TerminalNode identifier = primaryExpression.Identifier();
                if (identifier != null) {
                    System.out.printf("function invoke: %s\n", identifier.getText());
                }
            }
        }

        @Override
        public void enterStructOrUnionSpecifier(CParser.StructOrUnionSpecifierContext context) {
            CParser.StructOrUnionContext structOrUnion = context.structOrUnion();
            TerminalNode identifier = context.Identifier();
            if (structOrUnion != null && identifier != null) {
                if (context.LeftBrace() != null && context.RightBrace() != null) {
                    System.out.printf("type define: %s\n", identifier.getText());
                } else {
                    System.out.printf("type use: %s\n", identifier.getText());
                }
            }
        }
    }
}
