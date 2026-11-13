BEGIN;

-- =========================================
-- 1. INGREDIENTES
-- =========================================

INSERT INTO ingrediente (descricao) VALUES
-- Ingredientes comuns
('Farinha de trigo'),
('Açúcar'),
('Ovos'),
('Manteiga'),
('Leite'),
('Fermento químico'),
('Essência de baunilha'),

-- Ingredientes específicos: baunilha
('Açúcar de confeiteiro'),
('Leite condensado'),

-- Ingredientes específicos: brigadeiro
('Chocolate em pó'),
('Chocolate granulado'),
('Creme de leite'),

-- Ingredientes específicos: banana
('Banana'),
('Canela em pó'),

-- Ingredientes específicos: morango com chocolate
('Morango'),
('Chocolate meio amargo'),
('Creme de chocolate');


-- =========================================
-- 2. CUPCAKES
-- =========================================

INSERT INTO cupcake (
    nome,
    estoque,
    descricao,
    preco_unitario,
    nota_media
) VALUES
      (
          'Cupcake de Baunilha',
          20,
          'Massa leve e macia de baunilha com cobertura cremosa.',
          8.50,
          null
      ),
      (
          'Cupcake de Chocolate',
          20,
          'Massa leve e macia de chocolate com cobertura cremosa.',
          10.00,
          null
      ),
      (
          'Cupcake de Brigadeiro',
          15,
          'Massa de chocolate com cobertura de brigadeiro e granulado.',
          12.00,
          null
      ),
      (
          'Cupcake de Banana',
          18,
          'Massa fofinha de banana com toque de canela.',
          9.50,
          null
      ),
      (
          'Cupcake de Morango com Chocolate',
          12,
          'Massa de chocolate com cobertura cremosa e morangos.',
          14.50,
          null
      );


-- =========================================
-- 3. CUPCAKE_IMAGE
-- Substitua os caminhos pelas imagens reais.
-- =========================================

INSERT INTO cupcake_image (
    id_cupcake,
    caminho,
    is_thumbnail,
    ordem
)
SELECT id, '/images/cupcakes/baunilha.jpg', TRUE, 0
FROM cupcake
WHERE nome = 'Cupcake de Baunilha';

INSERT INTO cupcake_image (
    id_cupcake,
    caminho,
    is_thumbnail,
    ordem
)
SELECT id, '/images/cupcakes/brigadeiro.jpg', TRUE, 0
FROM cupcake
WHERE nome = 'Cupcake de Brigadeiro';

INSERT INTO cupcake_image (
    id_cupcake,
    caminho,
    is_thumbnail,
    ordem
)
SELECT id, '/images/cupcakes/banana.png', TRUE, 0
FROM cupcake
WHERE nome = 'Cupcake de Banana';

INSERT INTO cupcake_image (
    id_cupcake,
    caminho,
    is_thumbnail,
    ordem
)
SELECT id, '/images/cupcakes/banana-2.jfif', FALSE, 1
FROM cupcake
WHERE nome = 'Cupcake de Banana';

INSERT INTO cupcake_image (
    id_cupcake,
    caminho,
    is_thumbnail,
    ordem
)
SELECT id, '/images/cupcakes/banana-3.jfif', FALSE, 2
FROM cupcake
WHERE nome = 'Cupcake de Banana';

INSERT INTO cupcake_image (
    id_cupcake,
    caminho,
    is_thumbnail,
    ordem
)
SELECT id, '/images/cupcakes/morango-chocolate.jpg', TRUE, 0
FROM cupcake
WHERE nome = 'Cupcake de Morango com Chocolate';

INSERT INTO cupcake_image (
    id_cupcake,
    caminho,
    is_thumbnail,
    ordem
)
SELECT id, '/images/cupcakes/morango-chocolate-2.jfif', FALSE, 1
FROM cupcake
WHERE nome = 'Cupcake de Morango com Chocolate';

INSERT INTO cupcake_image (
    id_cupcake,
    caminho,
    is_thumbnail,
    ordem
)
SELECT id, '/images/cupcakes/chocolate.webp', TRUE, 0
FROM cupcake
WHERE nome = 'Cupcake de Chocolate';

INSERT INTO cupcake_image (
    id_cupcake,
    caminho,
    is_thumbnail,
    ordem
)
SELECT id, '/images/cupcakes/chocolate-2.png', FALSE, 1
FROM cupcake
WHERE nome = 'Cupcake de Chocolate';


-- =========================================
-- 4. CUPCAKE_INGREDIENTES
-- =========================================

-- Baunilha
INSERT INTO cupcake_ingredientes (id_cupcake, id_ingrediente)
SELECT c.id, i.id
FROM cupcake c
         CROSS JOIN ingrediente i
WHERE c.nome = 'Cupcake de Baunilha'
  AND i.descricao IN (
                      'Farinha de trigo',
                      'Açúcar',
                      'Ovos',
                      'Manteiga',
                      'Leite',
                      'Fermento químico',
                      'Essência de baunilha',
                      'Açúcar de confeiteiro',
                      'Leite condensado'
    );

-- Brigadeiro
INSERT INTO cupcake_ingredientes (id_cupcake, id_ingrediente)
SELECT c.id, i.id
FROM cupcake c
         CROSS JOIN ingrediente i
WHERE c.nome = 'Cupcake de Brigadeiro'
  AND i.descricao IN (
                      'Farinha de trigo',
                      'Açúcar',
                      'Ovos',
                      'Manteiga',
                      'Leite',
                      'Fermento químico',
                      'Chocolate em pó',
                      'Chocolate granulado',
                      'Leite condensado',
                      'Creme de leite'
    );

-- Banana
INSERT INTO cupcake_ingredientes (id_cupcake, id_ingrediente)
SELECT c.id, i.id
FROM cupcake c
         CROSS JOIN ingrediente i
WHERE c.nome = 'Cupcake de Banana'
  AND i.descricao IN (
                      'Farinha de trigo',
                      'Açúcar',
                      'Ovos',
                      'Manteiga',
                      'Leite',
                      'Fermento químico',
                      'Banana',
                      'Canela em pó'
    );

-- Morango com chocolate
INSERT INTO cupcake_ingredientes (id_cupcake, id_ingrediente)
SELECT c.id, i.id
FROM cupcake c
         CROSS JOIN ingrediente i
WHERE c.nome = 'Cupcake de Morango com Chocolate'
  AND i.descricao IN (
                      'Farinha de trigo',
                      'Açúcar',
                      'Ovos',
                      'Manteiga',
                      'Leite',
                      'Fermento químico',
                      'Chocolate em pó',
                      'Morango',
                      'Chocolate meio amargo',
                      'Creme de chocolate',
                      'Creme de leite'
    );

INSERT INTO cupcake_ingredientes (id_cupcake, id_ingrediente)
SELECT c.id, i.id
FROM cupcake c
         CROSS JOIN ingrediente i
WHERE c.nome = 'Cupcake de Chocolate'
  AND i.descricao IN (
                      'Farinha de trigo',
                      'Açúcar',
                      'Ovos',
                      'Manteiga',
                      'Leite',
                      'Fermento químico',
                      'Chocolate em pó',
                      'Chocolate meio amargo',
                      'Creme de chocolate',
                      'Creme de leite'
    );

COMMIT;