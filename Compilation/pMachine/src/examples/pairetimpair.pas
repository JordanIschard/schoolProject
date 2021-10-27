program example ;

var i : integer;
var ispair : boolean;
var alpha : array [6] of integer

(* Pour les index pair on écrit 1 sinon 2 *)
begin
    ispair := true;
    for i := 0 to 6 do
        begin
            if (ispair) then
                begin
                    alpha[i] := 1
                end
            else
                begin
                    alpha[i] := 2
                end;
            ispair := !(ispair)
        end;

    for i := 0 to 6 do
        begin
            write(alpha[i])
        end
end.