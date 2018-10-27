type expression = Mult of (expression*expression)
		  |Moins of (expression*expression)
		  |Ajout of (expression*expression)
		  |Div of (expression*expression)
		  |Valeur of int;;

type nombre = {valeur: int; expr: expression};;

let rec toStringExp = fun exp ->
  match exp with
    |Valeur v -> string_of_int v
       
    |Mult(Valeur v,Valeur w) -> (string_of_int v)^"*"^(string_of_int w)
    |Mult(Valeur v,y) -> (string_of_int v)^"*("^(toStringExp y)^")"
    |Mult(x,Valeur w) ->"("^(toStringExp x)^")*"^(string_of_int w)
    |Mult(x,y) -> "("^(toStringExp x)^")*("^(toStringExp y)^")"
       
    |Div(Valeur v,Valeur w) -> (string_of_int v)^"/"^(string_of_int w)
    |Div(Valeur v,y) -> (string_of_int v)^"/("^(toStringExp y)^")"
    |Div(x,Valeur w) ->"("^(toStringExp x)^")/"^(string_of_int w)
    |Div(x,y) -> "("^(toStringExp x)^")/("^(toStringExp y)^")"
       
    |Moins(Valeur v,Valeur w) -> (string_of_int v)^"-"^(string_of_int w)
    |Moins(Valeur v,y) -> (string_of_int v)^"-("^(toStringExp y)^")"
    |Moins(x,Valeur w) ->"("^(toStringExp x)^")-"^(string_of_int w)
    |Moins(x,y) ->  "("^(toStringExp x)^")-("^(toStringExp y)^")"
       
    |Ajout(Valeur v,Valeur w) -> (string_of_int v)^"+"^(string_of_int w)
    |Ajout(Valeur v,y) -> (string_of_int v)^"+("^(toStringExp y)^")"
    |Ajout(x,Valeur w) ->"("^(toStringExp x)^")+"^(string_of_int w)
    |Ajout(x,y) ->  "("^(toStringExp x)^")+("^(toStringExp y)^")"
;;

let nombre_of_int = fun i ->
  {valeur = i ; expr = Valeur i};;

let nombrelist_of_intlist = fun l ->
  List.map nombre_of_int l ;;

let combinaisons = fun l ->
  let lim = List.length l 
  in
  let indicesCouples =  
	let rec aux = fun n m ->
	  if n = lim then []
	  else 
	    if m = lim then aux (n+1) (n+2)
	    else (n,m)::(aux n (m+1))
	in aux 0 1
  and
      combinaisonsListeExpression = fun n m liste ->
	let rec aux = fun i l res ->
	  match i, l with
	    | i, [] -> failwith "Fatal error system"
	    | i,h::t when i = m -> List.hd res,h,(List.tl res)@t
	    | i,h::t when i<>n -> aux (i+1) t (res@[h])
	    | i,h::t -> aux (i+1) t (h::res)
	in aux 0 liste []
  in
  List.map (fun (x,y) -> combinaisonsListeExpression x y l) indicesCouples;;


let add = fun (n1,n2,l) -> {valeur = n1.valeur + n2.valeur; expr = Ajout(n1.expr, n2.expr)}::l;;

let soustraction = fun (n1,n2,l) -> 
  match n1.valeur,n2.valeur with
    |a,b when a > b -> {valeur = n1.valeur - n2.valeur; expr = Moins(n1.expr, n2.expr)}::l
    |a,b when b > a -> {valeur = n2.valeur - n1.valeur; expr = Moins(n2.expr, n1.expr)}::l
    |_ -> [];;

let multiplication = fun (n1,n2,l) -> 
  if n1.valeur = 1 || n2.valeur = 1 then[] else {valeur = n1.valeur * n2.valeur; expr = Mult(n1.expr, n2.expr)}::l;; 
combinaisons;;

let division = fun (n1,n2,l) ->
  if n1.valeur mod n2.valeur != 0 || n2.valeur = 1 then [] else {valeur = n1.valeur / n2.valeur; expr = Div(n1.expr, n2.expr)}::l 

let rec operations = fun liste ->
  let d = fun (n1,n2,l) results ->
    if n1.valeur mod n2.valeur != 0 then []
    else
      let r = n1.valeur / n2.valeur in
      if List.mem r results then [] else operations (division (n1,n2,l)) 
  in
  let m = fun (n1,n2,l) results ->
    let aux = n1.valeur * n2.valeur in
    if List.mem aux results then (d (n1,n2,l) results) 
    else operations (multiplication (n1,n2,l))@(d (n1,n2,l) (aux::results))
  in
   let s = fun (n1,n2,l) results ->
     match n1.valeur,n2.valeur with
       |a,b when a > b -> (operations (soustraction (n1,n2,l)))@(m (n1,n2,l) ((a-b)::results))
       |a,b when a < b -> (operations (soustraction (n1,n2,l)))@(m (n1,n2,l) ((b-a)::results))
       | _ -> m (n1,n2,l) results
  in
  let a = fun (n1,n2,l) ->
    (operations (add (n1,n2,l)))@(s (n1,n2,l) [n1.valeur+n2.valeur])
  in
  match liste with
    | h::[] -> [h]
    | h::t ->  List.flatten (List.map a (combinaisons liste)) 
    | [] -> [];;


(* Partie 2 *)

let plus_pres = fun cible n1 n2 ->
  let r1 = abs( n1.valeur - cible) and
      r2 = abs( n2.valeur - cible) in
  if r1 > r2 then n2 else n1;;

let transform = List.map (fun n -> {valeur = n; expr = Valeur n});;

let meilleure_approximation = fun l cible ->
  let appro = plus_pres cible in
  let transform = List.map (fun n -> {valeur = n; expr = Valeur n}) in
  let getBest = fun l ->
    let rec aux = fun l res ->
      match l with
	| [] -> res
	|h::t -> aux t ( appro h res)
    in
    aux (List.tl l) (List.hd l)
  in
  match l with
    |[] -> failwith "Liste vide"
    |_ -> getBest (operations (transform l));;


(*Partie 3*)

  
let rec saisieCible()=
  print_string("Donner un entier cible:");
  let c = read_line() in
    try(int_of_string(c))
    with _ ->
      begin
	print_string("Ce n'est pas un entier! Recommencez.\n");
	saisieCible();
      end
;;
      

      
let saisieInteractive() =
  let rec aux = fun n res ->
    if n<=0 then
      begin
	print_string("Saisie finie \n");
	res
      end
    else
      begin
	print_string("Pour partir appuyer sur une autre touche \n");
	print_string("Donner un entier:");
	let c = read_line() in
	try ((aux (n-1) [int_of_string (c)]@res))
	with _ ->
	  begin
	    print_string "Saisie quittée!";
	    print_newline();
	    res
	  end
      end
  in
  aux 6 []
;;
    

let partie1()  = 
  print_string("Vous avez choisie la partie 1 \"Toutes les combinaisons\"\n");
  let combi = [nombrelist_of_intlist (saisieInteractive())]
  in
  let rec aux = fun l res ->
    match l with
    |[] -> res
    |h::t ->aux t (operations h::res)
  in
  let rec affichage = fun  l ->
    match l with
    |[] -> print_newline()
    |h::t ->
       begin
	 print_string("une valeur ="^(string_of_int h.valeur)^
			 " correspondant à l'expression \" "
		      ^(toStringExp h.expr)^" \"\n");
	 affichage t
       end
  in affichage (List.sort (fun x y -> x.valeur-y.valeur) (List.flatten (aux combi [])));;

let partie2() =
  print_string("Vous avez choisie la partie 2 \"Meilleure approximation\"\n");
  let cible = saisieCible() in
  let liste = saisieInteractive() in
  let resultat = meilleure_approximation liste cible in
  begin
    print_string("La meilleure approximation est "
		 ^(string_of_int resultat.valeur)^
		    " et son expression est \""
		 ^(toStringExp resultat.expr)^"\"\n\n");
      
    print_newline()
  end
;;

let partie3() =
  print_string("Vous avez choisie la partie 3 " 
	       ^"\"Formule qui s'évalue en le nombre recherché\"\n");
  let cible = saisieCible() in
  let liste = saisieInteractive() in
  let resultat = meilleure_approximation liste cible in
  begin
    if resultat.valeur=cible
    then
      begin
	print_newline();
	print_string("L'expression trouvée est \""
		     ^(toStringExp resultat.expr)^"\"\n\n")
      end
    else
      begin
	print_newline();
	print_string("Erreur! Le programme n'a pas"
		     ^"trouvé d'expression correspondante à l'entier cible\n")
      end
  end
;;

let rec menu() =
  print_string("Que voulez-vous faire ?\n");
  print_string("Si vous voulez lancer la partie 1 tapez 1\n");
  print_string("Si vous voulez lancer la partie 2 tapez 2\n");
  print_string("Si vous voulez lancer la partie 3 tapez 3\n");
  print_string("Si vous voulez stopper tapez q\n");
  let choix = read_line() in
  begin
    if(choix<>"q")
    then
      begin
	if (choix="1")
	then   partie1 ()
	else
	  begin
	    if (choix="2")  
	    then partie2 ()
	    else
	      begin
		if (choix="3")
		then partie3 () 
		else print_string("Erreur de saisie!\n")
	      end
	  end;
	menu()
      end
    else
      print_string("Au revoir !\n")
  end
;;


let main() =
  print_string("Bonjour! Vous venez de lancer le programme du Projet Ocaml.\n");
  menu()
;;

main();;
