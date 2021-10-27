program example ;

var n : integer;
var b : boolean

(* On choisit un nombre entre 51 et 54 et le case affiche notre choix *)
begin
    n := -1;
    while( n <= 50 || n >= 55 ) do
        begin
            read(n)
        end;

    case n of
        51 : write(51);
        52 : write(52);
        53 : write(53);
        54 : write(54);
    end
end.