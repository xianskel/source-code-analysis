package Lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class CamelSnakeCase {

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
		
		VoidVisitor<?> camelCaseClassVisitor = new CamelCaseClassVisitor();
		camelCaseClassVisitor.visit(cu, null);
		
		VoidVisitor<?> camelCaseMethodVisitor = new CamelCaseMethodVisitor();
		camelCaseMethodVisitor.visit(cu, null);
		
		VoidVisitor<?> camelCaseVarVisitor = new CamelCaseVarVisitor();
		camelCaseVarVisitor.visit(cu, null);
	}
	
	
	static class CamelCaseClassVisitor extends VoidVisitorAdapter<Void> {
		public void visit(ClassOrInterfaceDeclaration dec, Void arg) {
			super.visit(dec, arg);
			
			nameCheck(dec.getNameAsString());
		}
	}
	
	static class CamelCaseMethodVisitor extends VoidVisitorAdapter<Void> {
		public void visit(MethodDeclaration dec, Void arg) {
			super.visit(dec, arg);
			
			nameCheck(dec.getNameAsString());
		}
	}
	
	static class CamelCaseVarVisitor extends VoidVisitorAdapter<Void> {
		public void visit(VariableDeclarator dec, Void arg) {
			super.visit(dec, arg);
			
			nameCheck(dec.getNameAsString());	
		}
	}
	
	static void nameCheck(String name) {
		String camelPattern = "(^[a-z])([a-z]|[A-Z0-9])*";
		String snakePattern = "(^[a-z])([a-z]|[-])*";
		
		if(!name.matches(camelPattern) && !name.matches(snakePattern)) {
			System.out.println(name);
		}
	}

}
