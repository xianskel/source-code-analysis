package Lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class Step3 {

	public static void main(String[] args) {
		File file = new File("src/main/resources/source-code/Astar");
		
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
		List<MethodCallExpr> methodCalls = new ArrayList<MethodCallExpr>();
		methodCalls = cu.findAll(MethodCallExpr.class);
		
		methodCalls.forEach(methodCall -> {
			try {
//				ResolvedType resolvedType = methodCall.resolve().getReturnType();
//				
//				String methodeCallee = methodCall.resolve().getClassName();
//				System.out.println(methodCall.findAncestor(MethodDeclaration.class).get().getName() + " in Class: "
//						+ methodCall.findAncestor(ClassOrInterfaceDeclaration.class).get().getName());
//				System.out.println("Calls");
//				System.out.println(methodCall.toString() + " Type is: " + resolvedType.describe() + " in Class: "
//						+ methodeCallee);
//				System.out.println("**********");
				
				//For printing CSV
				System.out.println(methodCall.findAncestor(MethodDeclaration.class).get().getName() + " ; "
						+ methodCall.getName());
				
			} catch (UnsolvedSymbolException e) {
				System.out.println("UnsolvedSymbolException " + methodCall.toString() + " in file: " + file.getName());
			}
		});
		
	}

}
