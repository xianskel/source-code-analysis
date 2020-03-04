package Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class TwoTryBlockSolver {

	public static void search(String filePath) {
//		File file = new File(filePath);
		File file = new File("src/main/resources/source-code/jhotdraw/JHotDraw7/src/main/java");

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
		
		VoidVisitor<?> tryStatementVisitor = new TryStatementVisitor();
		tryStatementVisitor.visit(cu, null);
	}
	
	
	static class TryStatementVisitor extends VoidVisitorAdapter<Void> {
		public void visit(ClassOrInterfaceDeclaration dec, Void arg) {
			super.visit(dec, arg);

			if(dec.findAll(TryStmt.class).size() > 2) {
				System.out.println(dec.getNameAsString());
			}
		}
	}

}
