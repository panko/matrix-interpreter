# matrix-interpreter
Mátrixokat feldolgozó nyelv, ANTLR-el megvalósítva.

# Típusok
- racionális számok (kétegészhányadosa)
- mátrixok (az 1x1-es mátrixok tekinthetők számoknak, és a számok tekinthetők 1x1-es mátrixoknak).

# Műveletek
- A szokásos aritmetikai operátorok számokon: +, −, /, ∗, hatvány, (, ). Logikai operátorok: <, >, <=, >=, ==, ! =, a 0 érték hamis, minden más érték igaz.
- Mátrix műveletek (ha a műveletek elvégezhetők): mátrixok összeadása, kivonása, szorzása, inverzképzés, determininás meghatározás, transzponálás, skalárral való szorzás.
- Gauss-elimináció.
- Egyenletrendszerek megoldása. 
- Sajátérték, sajátvektor.
- For, while, if-then-else eszközök.
- Változók/kifejezések értékeinek beolvasása/kiírása a standard inputról/outputra. 
- Változók és blokkok (hatáskörkezelés).
- Saját függvények és eljárások.

# Fordítás, futtatás

```$ mvn package```  
```$ java -jar target/fordprogfel8-1.0-jar-with-dependencies.jar```
