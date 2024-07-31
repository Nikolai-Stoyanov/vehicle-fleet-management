INSERT INTO roles VALUES (1, 'ADMIN'), (2, 'USER');

INSERT INTO car_brands(id, name, description,company,status)
    VALUES  (1, 'Skoda','desc','Skoda',true),  (2, 'Dacia','desc','Dacia',true),
            (3, 'Honda','desc','Honda',true), (4, 'Ford','desc','Ford',true);

INSERT INTO car_persons (id, first_name, last_name,phone_number,status)
    VALUES  (1, 'Petar','Petrov','0889241417',true), (2, 'Asen','Asenov','0899941417',true),
            (3, 'Georgi','Vasilev','0812546417',true),(4, 'Maria','Ivanova','0888985417',true),
            (5, 'Gergana','Petrova','0889248124',true);

INSERT INTO fuels (id, name, description,status)
    VALUES  (1, 'Super Diesel','Diesel',true), (2, 'A95','Gasoline',true),
            (3, 'A98','Gasoline',true),(4, 'LPG','LPG',true),
            (5, 'Methanol','Methanol',true);

INSERT INTO fuel_suppliers (id, name, description,status)
    VALUES  (1, 'OMV','',true), (2, 'Petrol','',true),
            (3, 'Shell','',true),(4, 'ЕКО','',true),
            (5, 'LUKOIL','',true);