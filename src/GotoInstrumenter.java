//package instrument;

import soot.*;
import soot.jimple.*;
import soot.jimple.internal.JGotoStmt;

import soot.util.*;
import java.util.*;

public class GotoInstrumenter extends BodyTransformer {
    static SootClass  counterClass;
    static SootMethod recordGoto;
//    protected static SootMethod counterV;


    static {
//  TO DO: 注册 Counter 以及 recordGoto
//  
        counterClass = Scene.v().loadClassAndSupport("Counter");
        recordGoto = counterClass.getMethodByName("recordGoto");
        Scene.v().setSootClassPath(null);



		    }

    @Override
    protected synchronized void internalTransform(Body body, String s, Map<String, String> map) {
//  TO DO: 在合适的位置插装Counter中的代码
        // body's method
		SootMethod method = body.getMethod();
        SootClass declareClass = method.getDeclaringClass();

		// debugging
		System.out.println("instrumenting class: " + declareClass.getName());

		// get body's unit as a chain
		Chain units = body.getUnits();

		// get a snapshot iterator of the unit since we are going to
		// mutate the chain when iterating over it.
		//
		Iterator stmtIt = units.snapshotIterator();

		// typical while loop for iterating over each statement
		while (stmtIt.hasNext()) {

			// cast back to a statement.
			Stmt stmt = (Stmt) stmtIt.next();

			// there are many kinds of statements, here we are only
			// interested in statements containing InvokeStatic
			// NOTE: there are two kinds of statements may contain
			// invoke expression: InvokeStmt, and AssignStmt
			if (!(stmt instanceof JGotoStmt)) {
				continue;
			}

			// now we reach the real instruction
			// call Chain.insertBefore() to insert instructions
			//
			// 1. first, make a new invoke expression
			InvokeExpr incExpr = Jimple.v().newStaticInvokeExpr(
					recordGoto.makeRef(), StringConstant.v(declareClass.getName()));
			// 2. then, make a invoke statement
			Stmt incStmt = Jimple.v().newInvokeStmt(incExpr);

			// 3. insert new statement into the chain
			// (we are mutating the unit chain).
			units.insertBefore(incStmt, stmt);
		}

    }
}


