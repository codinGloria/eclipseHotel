-- Etapa 2 - Avaliação de banco de dados

-- 1. Quantos clientes temos na base?

SELECT 
	COUNT(*) AS total_clientes
FROM
	customers;

-- 2. Quantos quartos temos cadastrados?
	
SELECT 
	COUNT(*) AS total_quartos
FROM
	rooms;
	
-- 3. Quantas reservas em aberto o hotel possui no momento?
	
SELECT
	COUNT(*) AS reservas_abertas
FROM
	reservations
WHERE
	status IN ('SCHEDULED', 'IN USE');

-- 4. Quantos quartos temos vagos no momento?

SELECT
	COUNT(*) AS quartos_vagos
FROM
	rooms r
WHERE NOT EXISTS (
	SELECT 1
	FROM reservations res
	WHERE res.room_id = r.id 
	AND res.status = 'IN_USE'
);

-- 5. Quantos quartos temos ocupados no momento?

SELECT
	COUNT(*) AS quartos_ocupados
FROM
	reservations
WHERE
	status = 'IN USE';

-- 6. Quantas reservas futuras o hotel possui?

(Fiquei um pouco confusa com relação ao 'futuras', mas acredito que possa ser o seguinte)

SELECT
	COUNT(*) AS reservas_futuras
FROM
	reservations
WHERE
	checkin > CURRENT_TIMESTAMP;

-- 7. Qual o quarto mais caro do hotel?

SELECT
	*
FROM
	rooms
ORDER BY
	price DESC
LIMIT 1;

-- 8. Qual o quarto com maior histórico de cancelamentos?

SELECT 
	r.id, r.room_number, COUNT(res.id) AS qtd_cancelamentos
FROM
	rooms r
LEFT JOIN
	reservations res
ON 
	r.id = res.room_id
WHERE 
	res.status = 'CANCELED'
GROUP BY
	r.id, r.room_number
ORDER BY
	qtd_cancelamentos DESC
LIMIT 1;

-- 9. Liste todos os quartos e a quantidade de clientes que já ocuparam cada um.

SELECT
	r.id, r.room_number, COUNT(DISTINCT(res.customer_id)) AS qtd_clientes
FROM
	rooms r
LEFT JOIN
	reservations res
ON
	r.id = res.room_id
GROUP BY 
	r.id, r.room_number;

-- 10. Quais são os 3 quartos que possuem um histórico maior de ocupações?

SELECT
	r.id, r.room_number, COUNT(res.id) AS qtd_ocupacoes
FROM
	rooms r
LEFT JOIN
	reservations res
ON
	r.id = res.room_id
GROUP BY 
	r.id, r.room_number
ORDER BY 
	qtd_ocupacoes DESC
LIMIT 3;
	
-- 11. No próximo mês, o hotel fará uma promoção para os seus 10 clientes que possuírem maior histórico de reservas e você foi acionado pelo seu time para extrair esta informação do banco de dados. Quem são os 10 clientes?

SELECT
	c.id, c.name, c.email, c.phone,  COUNT(res.id) AS qtd_reservas
FROM
	customers c
LEFT JOIN
	reservations res
ON
	c.id = res.customer_id
GROUP BY 
	c.id, c.name, c.email, c.phone
ORDER BY 
	qtd_reservas DESC
LIMIT 10;