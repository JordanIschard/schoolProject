program example ;

type
    Zarb = record
                taille : integer;
                indexcourant : integer;
                liste : array [-4..6] of integer;
             end;
end

var b : Zarb;
var tmp : integer

(* Retourne l'index de l'élément que l'on veut dans la liste *)
procedure getindex(var liste : array [-4..6] of integer, taille : integer, elem : integer, var index : integer) ;
    var tmp : integer

    begin
        index := -1;

        for tmp := -4 to taille-4 do
            begin
                if(elem = liste[tmp]) then
                    begin
                        index := tmp
                    end
             end
    end;



begin
    b.taille := 10;
    tmp := -8;

    (* On choisit une position pour mettre l'entier 1 dans la liste *)
    while( tmp < -4 || tmp >= b.taille-4 ) do
        begin
            read(tmp)
        end;

     b.liste[tmp] := 1;

     getindex(b.liste, b.taille, 1, b.indexcourant);

     write(b.indexcourant)

end.