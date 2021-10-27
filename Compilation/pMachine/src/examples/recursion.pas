program example ;

var i : integer;
var n : integer;
var m : integer

(* on fait n * m  *)
procedure  multrec(var x : integer, n : integer, m : integer) ;
    begin
        if(m = 1) then
            begin
                x := n
            end
        else
            begin
                multrec(x,n,m-1);
                x := x + n
            end
    end;

procedure afficher(x :integer) ;
    begin
        write(x)
    end;

begin
    read(n);
    read(m);
    multrec(i,n,m);
    afficher(i)
end.