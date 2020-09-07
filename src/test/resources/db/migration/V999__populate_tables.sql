INSERT INTO CLIENT (NAME, BIRTH_DATE)
VALUES ('TED', '1985-03-26'),
       ('ROBIN', '1989-08-10'),
       ('LILY', '1986-03-26'),
       ('MARSHAL', '1983-03-26'),
       ('BARNEY', '1982-03-26'),
       ('VICTORIA', '1988-03-26'),
       ('CARL', '1980-03-26'),
       ('STEPHANIE', '1990-03-26');

INSERT INTO PRODUCT (SKU, NAME, DESCRIPTION, PRICE, QUANTITY)
VALUES ('DELLNOTEI5', 'Inspiron 15 3000 Laptop',
        'Seamless PC/smartphone integration: Access multiple devices without dividing your attention—Dell Mobile Connect pairs your iOS or Android smartphone with your laptop.', 300.00, 0),
       ('APPLENOTEI5', 'MacBook Air',
        'With a resolution of 2560-by-1600 for over 4 million pixels, the results are positively jaw dropping. Images take on a new level of detail and realism. Text is sharp and clear. And True Tone technology automatically adjusts the white point of the display to match the color temperature of your environment — making web pages and email look as natural as the printed page. With millions of colors, everything you see is rich and vibrant.',
        900.00, 60);

INSERT INTO ORDER (ID_CLIENT, PURCHASE_VALUE, PURCHASE_DATE)
VALUES (1, 1500, '2020-06-09');