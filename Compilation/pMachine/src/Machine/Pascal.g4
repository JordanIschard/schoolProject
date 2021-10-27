grammar Pascal;


@header {
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
}

@parser::members {
private Program prog = new Program();

/**
* Lit le programme dans un fichier et le convertit en instruction
*
* @return Le programme vérifié
* @throws SyntaxErrorException
*/
public  Program lire() throws SyntaxErrorException {

    program();

	if (this._syntaxErrors > 0 || prog.HasError()) {
	    prog.printErr();
	    printSymbolTable(this.prog.getSymbolTables());
		throw new SyntaxErrorException();
	}else
	    return prog;
}
}

// structure principal d'un programme pascal
program: 'program' ID ';' block '.' { prog.addPCode(new HLT()); };


block: typedefs vars
{
    INC inc = new INC(prog.getSizeSymbolsTable());
    BRN brn = new BRN();
    prog.addPCode(Arrays.asList(new Instruction[]{inc, brn}));
    int tmp = prog.getSizeSymbolsTable();
}
x = defprocs
{
    inc.increment($x.size - tmp);
    brn.setJump(prog.getNbrInstruction());
} insts;





// structure descriptive d'une succession de procédure
defprocs returns[int size]: { $size = prog.getSizeSymbolsTable(); } (x = defproc[$size] { $size += $x.size; } ';')*;


defproc[int shift] returns[Procedure proc,int size] : 'procedure' ID
{
$proc = new Procedure(prog.getNbrInstruction()+1,$shift);
    prog.putInTable($ID.text,$proc,PROCEDURE);
    prog.downLevel($ID.text);
}
('(' defparams ')' | '()') ';' typedefs vars
{
    BRN brn = new BRN();
    prog.addPCode(brn);
    int tmp = prog.getSizeSymbolsTable();
}
x = defprocs insts
{
    prog.addPCode(new RET(prog.getNbrParam($ID.text)));
    brn.setJump(prog.getNbrInstruction());
    $size = prog.getSizeSymbolsTable() + $x.size - tmp;
    prog.upLevel();
};


defparams : defparam (',' defparam)*;


defparam : { Parameter param = new Parameter(); }
           ( ( 'var' ID ':' type { param.setReference(true); } )
           | ( ID ':' type { param.setReference(false); } ) )
{
    param.setType($type.t);
    prog.putInTable($ID.text,param,PARAMETER);
};




// structure descriptive d'une succession de définitions de types
typedefs : ('type' typedef 'end')*;

typedef : (x = TYPE '=' type { prog.putInTable($x.text,$type.t,TYPEPERSO);} ';')+ ;





// structure descriptive d'une succession de variables
vars : ('var' var (';' 'var' var)*)?;

var : x = ID { List<String> variables = new ArrayList<String>(){{ add($x.text); }}; }
 (',' y = ID { variables.add($y.text); } )* ':' type
 {
    for(String variable : variables){
        prog.putInTable(variable,$type.t,VARIABLE);
    }

 };

type returns[Type t]:
      'integer' { $t= new Entier(); }

    | 'boolean' { $t = new Boolean(); }

    | 'array' {int begin = 0;} '['(b = integer '..' {begin = $b.val;prog.removeHead();})? e = integer']' 'of' x = type { prog.removeHead();$t = new Tableau(begin,$e.val,$x.t);}

    | 'record' { List<Pair<String,Type>> champs = new ArrayList<Pair<String,Type>>(); }
      ( champ { champs.add(new Pair<String,Type>($champ.info1,$champ.info2)); })+
      'end'
       {
            try{
                $t = new Record(champs);
            }catch(VariableAlReadyUsedException e){
                prog.addError(e.getMessage());
            }
       }

    | TYPE { $t = prog.getData($TYPE.text,TYPEPERSO).getType(); };


champ returns[String info1,Type info2] : ID ':' type ';' { $info1 = $ID.text; $info2 = $type.t; };





// structure pour une suite d'instructions
insts : 'begin' inst (';' inst)* 'end';

inst : insts | affect | ifcond | read | write | forDo | whileDo | switchCase | repeatUntil | callFunc;





// gestion des booléens
cond : condterm | x = boolunop '('condterm')' { prog.addPCode($x.op); };


condterm : condfact ( x = boolbiop condfact { prog.addPCode($x.op); })?;


condfact :
       bool
     | expr x = relop expr { prog.addPCode($x.op); }
     | y = adresse
{
    if(prog.isBoolean($y.t)){
        prog.addPCode(new LDV($y.t.size()));
    }else{
        prog.addError($y.t+" is not a boolean.");
    }

}
     | '('cond')';



bool : TRUE { prog.addPCode(new LDI(1)); } | FALSE { prog.addPCode(new LDI(0)); };


boolunop returns[Instruction op] : '!' { $op = new NOT(); };


boolbiop returns[Instruction op] : '&&' { $op = new AND(); } | '||' { $op = new OR(); };


relop returns[Instruction op] :
        '=' { $op = new EQL(); }
      | '<>' { $op = new NEQ(); }
      | '<' { $op = new LSS(); }
      | '>' { $op = new GTR(); }
      | '<=' { $op = new LEQ(); }
      | '>=' { $op = new GEQ(); };







// structure pour l'affectation
affect : x=adresse ':=' ({prog.isBoolean($x.t)}? cond | expr) { prog.addPCode(new STO($x.t.size())); };





// structure pour la conditionnelle
ifcond : { BZE bze = new BZE(); BRN brn = new BRN();  }
         'if' cond { prog.addPCode(bze); }
         'then' inst { prog.addPCode(brn); bze.setJump(prog.getNbrInstruction()); }
         ('else' inst )? { brn.setJump(prog.getNbrInstruction()); };


switchCase : 'case' { int debut = prog.getNbrInstruction(); } expr { int fin = prog.getNbrInstruction(); } 'of'
expr
{

    BZE bze = new BZE();
    prog.addPCode(Arrays.asList(new Instruction[]{new EQL(), bze}));
}
':' inst
{
    BRN brn = new BRN();
    prog.addPCode(brn);
    bze.setJump(prog.getNbrInstruction());
}
';' ( { prog.copySetInstruction(debut,fin); } expr
{

    BZE bze1 = new BZE();
    prog.addPCode(Arrays.asList(new Instruction[]{new EQL(), bze1}));
}
':' inst
{
    prog.addPCode(brn);
    bze1.setJump(prog.getNbrInstruction());
}
';')*{ brn.setJump(prog.getNbrInstruction()); } 'end';





// structure pour un appel de procédure
callFunc : x = ID
(
    { prog.hasNoParam($x.text) }? '()'

    | ('(' y = params[$x.text] ')'
      {
        int count = prog.getNbrParam($x.text);
        if($y.cpt != count){ prog.addError("user gives "+$y.cpt+" parameters but the procedure needs "+count+" parameters."); }
      })
)
{ prog.addPCode(new CAL(prog.getNbrInstruction(),prog.callProc($x.text),prog.getNbrParam($x.text))); };

params[String symbol] returns[int cpt]: { $cpt = 0; } param[$symbol,$cpt] { $cpt++; } (',' param[$symbol,$cpt] { { $cpt ++; } } )*;

param[String symbol, int cpt] : ( { prog.byValue($symbol,$cpt) }? (expr | cond) | x = adresse  );




// procédure basique : lecture d'entrée
read : 'read' '(' x=adresse ')' { prog.addPCode(new INN()); };





// procédure basique : écriture
write : 'write' '(' expr ')' { prog.addPCode(new PRN()); };





// structure pour les expressions
expr : term (x = addop term { prog.addPCode($x.op); })?;


addop returns[Instruction op]: '+' { $op = new ADD(); } | '-' { $op = new SUB(); };


term : fact (x = mulop fact { prog.addPCode($x.op); })?;


mulop returns[Instruction op]: '*' { $op = new MUL(); } | '/' { $op = new DIV(); };


fact : adresse { prog.addPCode(new LDV($adresse.t.size())); }
     | integer
     | '(' expr ')';


integer returns[Integer val]: '-' i = INT  { prog.addPCode(new LDI(- $i.int));$val = - $i.int; } | i = INT { prog.addPCode(new LDI($i.int));$val = $i.int; };





// structure pour les boucles
whileDo : 'while' { int debut = prog.getNbrInstruction(); } cond { BZE bze = new BZE(); prog.addPCode(bze); }
          'do' insts
          {
            prog.addPCode(new BRN(debut));
            bze.setJump(prog.getNbrInstruction());
          } ;


repeatUntil : 'repeat' {int begin = prog.getNbrInstruction();} inst 'until' cond
{
    BRN brn = new BRN();
    prog.addPCode(Arrays.asList(new Instruction[]{new BZE(begin), brn}));
    brn.setJump(prog.getNbrInstruction());
};


forDo : 'for' adr = adresse ':=' expr
{
   prog.addPCode(new STO(1));
   int debut = prog.getNbrInstruction();
   Instruction comp;
   Instruction op;
}
( 'to' { comp = new GTR(); op = new ADD(); } | 'downto' { comp = new LSS(); op = new SUB(); })
x = expr
{
   BZE bze = new BZE();
   prog.addPCode(Arrays.asList(new Instruction[]{new LDA($adr.ad),new LDV(1),comp,bze}));
}
'do' insts {
    prog.addPCode(Arrays.asList(new Instruction[]{new LDA($adr.ad),new LDA($adr.ad),new LDV(1),new LDI(1),op,
                                                  new STO(1),new BRN(debut)}));
    bze.setJump(prog.getNbrInstruction());
};





// gestion des adresses
adresse returns[int ad,Type t]: x=ID
{
    Data elem = prog.getData($x.text,VARIABLE,PARAMETER);
    $ad = elem.getAdr() + prog.shiftCurrentProc();

    if(elem.getType() instanceof Parameter){
        $t = ((Parameter)elem.getType()).getType();

        $ad =  prog.getPosParameter($x.text);
        prog.addPCode(new LDL($ad));

        if(elem.getType() instanceof Parameter && ((Parameter)elem.getType()).isReference())
                prog.addPCode(new LDV(1));

     }else{
        $t = elem.getType();
         prog.addPCode(new LDA($ad));
     }
}
('[' expr ']'
{
    prog.addPCode(new LDI(prog.getBeginList($x.text,$t)));
    $t = prog.getTypeElemList($x.text,$t);
    prog.addPCode(Arrays.asList(new Instruction[]{new SUB(),new ADD()}));
}
|'.' c=ID
{
    Data subElem = prog.getSubElementData($x.text,$c.text);
    $t = subElem.getType();
    prog.addPCode(Arrays.asList(new Instruction[]{new LDI(subElem.getAdr()),new ADD()}));
})*;






TRUE : 'true';
FALSE : 'false';
ID : [a-z]+;
TYPE : [A-Z][a-z]*;
INT : [1-9][0-9]*|[0];
WS : [ \r\t\n]+ -> skip;
COMMENT : '(*' .*? '*)' -> skip;
