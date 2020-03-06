package Lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserFieldDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class InstantiaedPublicFields {

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
		
		VoidVisitor<?> objectCreationVisitor = new ObjectCreationVisitor();
		objectCreationVisitor.visit(cu, null);
	
	}
	
	
	static class ObjectCreationVisitor extends VoidVisitorAdapter<Void> {
		public void visit(ObjectCreationExpr exp, Void arg) {
			super.visit(exp, arg);
						
			try {
				exp.getType().resolve().getDeclaredFields()
				.stream()
				.filter(node -> node instanceof JavaParserFieldDeclaration)
				.filter(field -> field.accessSpecifier() == AccessSpecifier.PUBLIC)
				.forEach(field -> {
					System.out.println(field.getName() + " is a public method in " + exp.getType());
				});
			} catch (UnsolvedSymbolException e) {
				System.out.println("UnsolvedSymbolException: ");
			}
						

		}
	}
}
