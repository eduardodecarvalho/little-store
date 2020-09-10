INSERT INTO CLIENT (ID, NAME, BIRTH_DATE)
VALUES (1, 'TED', '1985-03-26'),
       (2, 'ROBIN', '1989-08-10'),
       (3, 'LILY', '1986-03-26'),
       (4, 'MARSHAL', '1983-03-26'),
       (5, 'BARNEY', '1982-03-26'),
       (6, 'VICTORIA', '1988-03-26'),
       (7, 'CARL', '1980-03-26'),
       (8, 'STEPHANIE', '1990-03-26');

INSERT INTO PRODUCT (ID, SKU, NAME, DESCRIPTION, PRICE, QUANTITY)
VALUES (1, 'DELLNOTEI5', 'Inspiron 15 3000 Laptop',
        'Seamless PC/smartphone integration: Access multiple devices without dividing your attention—Dell Mobile Connect pairs your iOS or Android smartphone with your laptop.',
        300.00, 70),
       (2, 'APPLENOTEI5', 'MacBook Air',
        'With a resolution of 2560-by-1600 for over 4 million pixels, the results are positively jaw dropping. Images take on a new level of detail and realism. Text is sharp and clear. And True Tone technology automatically adjusts the white point of the display to match the color temperature of your environment — making web pages and email look as natural as the printed page. With millions of colors, everything you see is rich and vibrant.',
        900.00, 60);

INSERT INTO `CLIENT_ORDER` (ID, ID_CLIENT, PURCHASE_VALUE, PURCHASE_DATE)
VALUES (1, 1, 900, '2020-01-01'),
       (2, 2, 1500, '2020-05-05');

INSERT INTO ORDERED_ITEM (ID, QUANTITY, ID_PRODUCT, ID_CLIENT_ORDER)
VALUES (1, 69, 1, 1),
       (2, 1, 1, 2),
       (3, 2, 2, 2);