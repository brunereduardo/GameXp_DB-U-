-- SCRIPT PARA AS 5 CONSULTAS ESCOLHIDAS :

--1 Deve ser possível consultar quantos assessores estavam asseroando um grupo de imprensa durante em um espaço específico da Game-Xp 
SELECT  A.ESPACO,COUNT(FUN_A.ASSESSOR)
FROM COMUNICA FUN_A JOIN COBRE IMP 
    ON (FUN_A .IMPRENSA = IMP.IMPRENSA)
    JOIN ACONTECE A 
    ON (A.ID = IMP.ID)
GROUP BY A.ESPACO

--2  
SELECT NOME, ESTADO, TIPO, SEXO , COUNT(*), AVG(CACHEE) AS MED 
FROM COLABORADORES 
GROUP BY ESTADO, TIPO, SEXO
HAVING MED BETWEEN 30 AND 40
ORDER BY NOME;

--3 Deve ser possível consultar onde cada funcionário deve estar em uma determinada data e hora. 
-- Ex seguraca:
SELECT ESPACO, DATA, HORAS
FROM FUNCIONARIOS F JOIN CUIDA S
    ON (F.CREDENCIAL = S.SEGURANCA)
    JOIN ACONTECE A
    ON (S.ID = A.ID)
GROUP BY F.CPF
ORDER BY ESPACO 

--4 Deve ser possível consultar as Atrações que vão ocorrer bem como Local onde ocorrÃO, tipo de atração, data e horário 
SELECT TIPO, ESPACO,LOCAL, DATA, HORAS
FROM ACONTECE H JOIN ATRACAO A
    ON(H.ID = A.ID)
WHERE DATA > SYSDATE

--Consultar os seguintes funcionários staff disponíveis:
SELECT F.CREDENCIAL, F.NOME
FROM ADMINSTRA A , FUNCIONARIOS F
WHERE ( (F.FUNCAO = 'STAFF') AND (F.CREDENCIAL != A.STAFF) )
ORDER BY F.NOME;

-- FIM DO SCRIPT DE CONSULTAS


-- CONSULTA NA INTERFACE
-- QUAIS ATRAÇÕES ESTÃO OCORRENDO, DADO O DIA,  E COMO RESULTADO MOSTRAR AO USUARIO HORA, ESPAÇO, LOCAL E O TIPO DA ATRAÇÃO :
SELECT DISTINCT A.TIPO, H.HORAS, H.ESPACO, H.LOCAL
FROM AGENDA S JOIN ACONTECE H
    ON( ( S.DATA = '12/11/2019') AND (S.DATA = H.DATA))
    JOIN ATRACAO A
    ON(H.ID = A.ID)
ORDER BY H.HORAS


 


