package Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import Lab4.FourArgumentsSolver.TryStatementVisitor;

public class PublicStaticSolver {

	public static void search(String filePath) {
		File file = new File(filePath);
//		File file = new File("src/main/resources/source-code/jhotdraw/JHotDraw7/src/main/java");

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
		
		VoidVisitor<?> publicStaticCallerVisitor = new PublicStaticCallerVisitor();
		publicStaticCallerVisitor.visit(cu, null);

	}
	
	static class PublicStaticCallerVisitor extends VoidVisitorAdapter<Void> {
		public void visit(MethodCallExpr methodCall, Void arg) {
			super.visit(methodCall, arg);

			try {
				Boolean callerInAst = methodCall.resolve().toAst().isPresent();
				Boolean callerIsPublic = (methodCall.resolve().accessSpecifier() == AccessSpecifier.PUBLIC);
				Boolean callerIsStatic = methodCall.resolve().isStatic();
				
				if(!callerInAst || !callerIsPublic || !callerIsStatic) {
					return;
				}
				
				Boolean calleeInAst = methodCall.findAncestor(MethodDeclaration.class).get().resolve().toAst().isPresent();

				if (calleeInAst) {
					String methodeCallee = methodCall.resolve().getClassName();
					System.out.println(methodCall.getNameAsString() + " in Class: " + methodeCallee);
					System.out.println("**********");
				}

			} catch (UnsolvedSymbolException e) {
				System.out.println(
						"UnsolvedSymbolException " + methodCall.getNameAsString());
			} catch (NoSuchElementException e) {
				System.out.println("Unable to resolve element " + methodCall.getNameAsString());
			} catch (Exception e) {

			}
		}
	}

}
