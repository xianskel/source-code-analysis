package Lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class VoidFunctionInConstructor {

	public static void search(String filePath) {
		File file = new File(filePath);
//		File file = new File("src/main/resources/source-code/Gobbledegook/src");

		TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(file);

		CombinedTypeSolver combinedSolver = new CombinedTypeSolver();
		combinedSolver.add(new ReflectionTypeSolver());
		combinedSolver.add(javaParserTypeSolver);

		try {
			listFilesForFolder(file, combinedSolver);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void listFilesForFolder(File folder, CombinedTypeSolver combinedSolver) throws IOException {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry, combinedSolver);
			} else {
				if (fileEntry.getName().endsWith(".java")) {
					recursiveFunc(fileEntry, combinedSolver);
				}
			}
		}

	}

	public static void recursiveFunc(File file, TypeSolver typeSolver) throws FileNotFoundException {
		JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
		StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

		CompilationUnit cu = StaticJavaParser.parse(file);
		
		VoidVisitor<?> constructorDecVisitor = new ConstructorDecVisitor();
		constructorDecVisitor.visit(cu, null);
	
	}
	
	
	static class ConstructorDecVisitor extends VoidVisitorAdapter<Void> {
		public void visit(ConstructorDeclaration cons, Void arg) {
			super.visit(cons, arg);
									
			cons.getBody().getChildNodes().stream()
				.filter(node -> node instanceof ExpressionStmt)
				.map(node -> (ExpressionStmt) node)
				.filter(node -> node.getExpression() instanceof MethodCallExpr)
				.map(node -> (MethodCallExpr) node.getExpression())
				.filter(node -> node.resolve().getReturnType().isVoid())
				.forEach(method -> {
					System.out.println("Method: " + method.getNameAsString() + " is called in constructor of: " + cons.resolve().getClassName());
				});
		}
	}
}
