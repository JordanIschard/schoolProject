// Generated from /home/jordan/Desktop/Etudes/Master/Semestre2/Compilation/Compilation/pMachine/src/Machine/Pascal.g4 by ANTLR 4.7.2
package Machine;

    import Instructions.Comparaisons.*;
    import Instructions.Internes.*;
    import Instructions.Operations.*;
    import Instructions.OpBools.*;
    import Instructions.Instruction;
    import TablesSymboles.Types.*;
	import TablesSymboles.Types.Boolean;
	import TablesSymboles.Data;
    import Exceptions.*;
    import static Machine.Program.*;
    import java.util.Arrays;
	import static Machine.Main.printSymbolTable;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PascalParser}.
 */
public interface PascalListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PascalParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(PascalParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(PascalParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(PascalParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(PascalParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#defprocs}.
	 * @param ctx the parse tree
	 */
	void enterDefprocs(PascalParser.DefprocsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#defprocs}.
	 * @param ctx the parse tree
	 */
	void exitDefprocs(PascalParser.DefprocsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#defproc}.
	 * @param ctx the parse tree
	 */
	void enterDefproc(PascalParser.DefprocContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#defproc}.
	 * @param ctx the parse tree
	 */
	void exitDefproc(PascalParser.DefprocContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#defparams}.
	 * @param ctx the parse tree
	 */
	void enterDefparams(PascalParser.DefparamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#defparams}.
	 * @param ctx the parse tree
	 */
	void exitDefparams(PascalParser.DefparamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#defparam}.
	 * @param ctx the parse tree
	 */
	void enterDefparam(PascalParser.DefparamContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#defparam}.
	 * @param ctx the parse tree
	 */
	void exitDefparam(PascalParser.DefparamContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#typedefs}.
	 * @param ctx the parse tree
	 */
	void enterTypedefs(PascalParser.TypedefsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#typedefs}.
	 * @param ctx the parse tree
	 */
	void exitTypedefs(PascalParser.TypedefsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#typedef}.
	 * @param ctx the parse tree
	 */
	void enterTypedef(PascalParser.TypedefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#typedef}.
	 * @param ctx the parse tree
	 */
	void exitTypedef(PascalParser.TypedefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#vars}.
	 * @param ctx the parse tree
	 */
	void enterVars(PascalParser.VarsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#vars}.
	 * @param ctx the parse tree
	 */
	void exitVars(PascalParser.VarsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(PascalParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(PascalParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(PascalParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(PascalParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#champ}.
	 * @param ctx the parse tree
	 */
	void enterChamp(PascalParser.ChampContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#champ}.
	 * @param ctx the parse tree
	 */
	void exitChamp(PascalParser.ChampContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#insts}.
	 * @param ctx the parse tree
	 */
	void enterInsts(PascalParser.InstsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#insts}.
	 * @param ctx the parse tree
	 */
	void exitInsts(PascalParser.InstsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterInst(PascalParser.InstContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitInst(PascalParser.InstContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCond(PascalParser.CondContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCond(PascalParser.CondContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#condterm}.
	 * @param ctx the parse tree
	 */
	void enterCondterm(PascalParser.CondtermContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#condterm}.
	 * @param ctx the parse tree
	 */
	void exitCondterm(PascalParser.CondtermContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#condfact}.
	 * @param ctx the parse tree
	 */
	void enterCondfact(PascalParser.CondfactContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#condfact}.
	 * @param ctx the parse tree
	 */
	void exitCondfact(PascalParser.CondfactContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterBool(PascalParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitBool(PascalParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#boolunop}.
	 * @param ctx the parse tree
	 */
	void enterBoolunop(PascalParser.BoolunopContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#boolunop}.
	 * @param ctx the parse tree
	 */
	void exitBoolunop(PascalParser.BoolunopContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#boolbiop}.
	 * @param ctx the parse tree
	 */
	void enterBoolbiop(PascalParser.BoolbiopContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#boolbiop}.
	 * @param ctx the parse tree
	 */
	void exitBoolbiop(PascalParser.BoolbiopContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#relop}.
	 * @param ctx the parse tree
	 */
	void enterRelop(PascalParser.RelopContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#relop}.
	 * @param ctx the parse tree
	 */
	void exitRelop(PascalParser.RelopContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#affect}.
	 * @param ctx the parse tree
	 */
	void enterAffect(PascalParser.AffectContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#affect}.
	 * @param ctx the parse tree
	 */
	void exitAffect(PascalParser.AffectContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#ifcond}.
	 * @param ctx the parse tree
	 */
	void enterIfcond(PascalParser.IfcondContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#ifcond}.
	 * @param ctx the parse tree
	 */
	void exitIfcond(PascalParser.IfcondContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#switchCase}.
	 * @param ctx the parse tree
	 */
	void enterSwitchCase(PascalParser.SwitchCaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#switchCase}.
	 * @param ctx the parse tree
	 */
	void exitSwitchCase(PascalParser.SwitchCaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#callFunc}.
	 * @param ctx the parse tree
	 */
	void enterCallFunc(PascalParser.CallFuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#callFunc}.
	 * @param ctx the parse tree
	 */
	void exitCallFunc(PascalParser.CallFuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(PascalParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(PascalParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(PascalParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(PascalParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#read}.
	 * @param ctx the parse tree
	 */
	void enterRead(PascalParser.ReadContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#read}.
	 * @param ctx the parse tree
	 */
	void exitRead(PascalParser.ReadContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#write}.
	 * @param ctx the parse tree
	 */
	void enterWrite(PascalParser.WriteContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#write}.
	 * @param ctx the parse tree
	 */
	void exitWrite(PascalParser.WriteContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(PascalParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(PascalParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#addop}.
	 * @param ctx the parse tree
	 */
	void enterAddop(PascalParser.AddopContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#addop}.
	 * @param ctx the parse tree
	 */
	void exitAddop(PascalParser.AddopContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(PascalParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(PascalParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#mulop}.
	 * @param ctx the parse tree
	 */
	void enterMulop(PascalParser.MulopContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#mulop}.
	 * @param ctx the parse tree
	 */
	void exitMulop(PascalParser.MulopContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#fact}.
	 * @param ctx the parse tree
	 */
	void enterFact(PascalParser.FactContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#fact}.
	 * @param ctx the parse tree
	 */
	void exitFact(PascalParser.FactContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(PascalParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(PascalParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#whileDo}.
	 * @param ctx the parse tree
	 */
	void enterWhileDo(PascalParser.WhileDoContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#whileDo}.
	 * @param ctx the parse tree
	 */
	void exitWhileDo(PascalParser.WhileDoContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#repeatUntil}.
	 * @param ctx the parse tree
	 */
	void enterRepeatUntil(PascalParser.RepeatUntilContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#repeatUntil}.
	 * @param ctx the parse tree
	 */
	void exitRepeatUntil(PascalParser.RepeatUntilContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#forDo}.
	 * @param ctx the parse tree
	 */
	void enterForDo(PascalParser.ForDoContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#forDo}.
	 * @param ctx the parse tree
	 */
	void exitForDo(PascalParser.ForDoContext ctx);
	/**
	 * Enter a parse tree produced by {@link PascalParser#adresse}.
	 * @param ctx the parse tree
	 */
	void enterAdresse(PascalParser.AdresseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PascalParser#adresse}.
	 * @param ctx the parse tree
	 */
	void exitAdresse(PascalParser.AdresseContext ctx);
}