package Lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Parser {
	
	static ArrayList<String> varNames = new ArrayList<String>();

	public static void main(String[] args) throws FileNotFoundException {
		File sourceCode = new File("src/main/resources/source-code");
		JavaFileDetection(sourceCode);
		printAll(varNames);
		printLessThanTwo(varNames);
	}
	
	static void JavaFileDetection(File folder) throws FileNotFoundException {
		for(final File file: folder.listFiles()) {
			if (file.isDirectory()) {
				JavaFileDetection(file);
			} else {
				if(file.getName().endsWith(".java")) {
					CompilationUnit cu = StaticJavaParser.parse(file);
					VoidVisitor<?> varVisitor = new VarParser();
					varVisitor.visit(cu, null);
				}
			}
		}
	}
	

	static class VarParser extends VoidVisitorAdapter<Void> {
		public void visit(SimpleName sn, Void arg) {
			if(sn.getParentNode().get() instanceof VariableDeclarator) {
				super.visit(sn, arg);
				varNames.add(sn.asString());
			}
		}
	}
	
	static void printAll(List<String> names) {
		System.out.println("All Var Names");
		System.out.println("-----------------");
		for (String name: names) {
			System.out.println(name);
		}
		System.out.println("-----------------");
	}
	
	static void printLessThanTwo(List<String> names) {
		System.out.println("Var Names With 2 or Less Alpha Characters");
		System.out.println("-----------------");
		for (String name: names) {
			if(name.replaceAll("[^A-Za-z]", "").length() <= 2) {
				System.out.println(name);
			}
		}
		System.out.println("-----------------");
	}
}

