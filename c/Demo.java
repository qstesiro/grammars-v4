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
    }

    // static class Listener extends CBaseListener {

    //     Stack<String> stack = new Stack<String>();

    //     @Override
    //     public void enterCompilationUnit(CParser.CompilationUnitContext context) {
    //         stack.push("compilationUnit");
    //     }

    //     @Override
    //     public void exitCompilationUnit(CParser.CompilationUnitContext context) {
    //         stack.pop();
    //     }

    //     // ---

    //     @Override
    //     public void enterFunctionDefinition(CParser.FunctionDefinitionContext context) {
    //         stack.push("functionDefinition");
    //     }

    //     @Override
    //     public void exitFunctionDefinition(CParser.FunctionDefinitionContext context) {
    //         stack.pop();
    //     }

    //     @Override
    //     public void enterParameterDeclaration(CParser.ParameterDeclarationContext context) {
    //         stack.push("parameterDeclaration");
    //     }

    //     @Override
    //     public void exitParameterDeclaration(CParser.ParameterDeclarationContext context) {
    //         stack.pop();
    //     }

    //     @Override
    //     public void enterDirectDeclarator(CParser.DirectDeclaratorContext context) {
    //         if (!stack.empty() && stack.peek().equals("functionDefinition")) {
    //             TerminalNode identifier = context.Identifier();
    //             if (identifier != null) {
    //                 System.out.printf("function define: %s\n", identifier.getText());
    //             }
    //         }
    //     }

    //     @Override
    //     public void exitDirectDeclarator(CParser.DirectDeclaratorContext context) {

    //     }

    //     // ---

    //     @Override
    //     public void enterExpression(CParser.ExpressionContext context) {
    //         stack.push("expression");
    //     }

    //     @Override
    //     public void exitExpression(CParser.ExpressionContext context) {
    //         stack.pop();
    //     }

    //     @Override
    //     public void enterArgumentExpressionList(CParser.ArgumentExpressionListContext context) {
    //         stack.push("argumentExpressionList");
    //     }

    //     @Override
    //     public void exitArgumentExpressionList(CParser.ArgumentExpressionListContext context) {
    //         stack.pop();
    //     }

    //     @Override
    //     public void enterPrimaryExpression(CParser.PrimaryExpressionContext context) {
    //         if (!stack.empty() && stack.peek().equals("expression")) {
    //             TerminalNode identifier = context.Identifier();
    //             if (identifier != null) {
    //                 System.out.printf("function invoke: %s\n", identifier.getText());
    //             }
    //         }
    //     }

    //     @Override
    //     public void exitPrimaryExpression(CParser.PrimaryExpressionContext context) {

    //     }
    // }
}
