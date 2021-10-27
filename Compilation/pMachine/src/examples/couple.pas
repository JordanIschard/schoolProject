program example ;

type
    Couple = record
                a : integer;
                b : integer;
             end;
end
var n : integer;
var b : integer;
var couplea : Couple;
var coupleb : Couple

(* Démonstration des types via des couples *)
begin
    n := -1;
    while( n < 0) do
        begin
            read(n)
        end;

     b := -1;
     while( b < 0) do
        begin
            read(b)
         end;

    couplea.a := n;
    repeat
        begin
            couplea.b := couplea.b + 1
        end
     until couplea.a = couplea.b ;


     coupleb := couplea;
     coupleb.b := b;

     write(couplea.b);
     write(coupleb.b)
end.