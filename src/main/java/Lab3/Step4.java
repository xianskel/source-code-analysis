package Lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class Step4 {

	public static void main(String[] args) {
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
		List<ClassOrInterfaceDeclaration> classDeclarations = new ArrayList<ClassOrInterfaceDeclaration>();
		classDeclarations = cu.findAll(ClassOrInterfaceDeclaration.class);
		
		classDeclarations.forEach(classDec -> {
			try {
				if(classDec.getParentNode().get() instanceof ClassOrInterfaceDeclaration) {

			         System.out.println(classDec.getNameAsString());

			    }
			} catch (UnsolvedSymbolException e) {
				System.out.println("UnsolvedSymbolException " + classDec.toString() + " in file: " + file.getName());
			}
		});
		
	}

}
