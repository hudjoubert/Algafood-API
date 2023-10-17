insert into kitchen (name) values ('Tailandesa');
insert into kitchen (name) values ('Indiana');
INSERT INTO  kitchen  (name) VALUES  ('Brasileira');

insert into restaurant (name, tax_shipping, kitchen) values ('Thai Gourmet', 10, 1);
insert into restaurant (name, tax_shipping, kitchen) values ('Thai Delivery', 9.50, 1);
insert into restaurant (name, tax_shipping, kitchen) values ('Tuk Tuk Comida Indiana', 15, 2);
insert into restaurant (name, tax_shipping, kitchen) values ('Feijoada', 30, 3);

insert into state (name) values ('Minas Gerais');
insert into state (name) values ('São Paulo');
insert into state (name) values ('Ceará');

insert into city (name, state_id) values ('Uberlândia', 1);
insert into city (name, state_id) values ('Belo Horizonte', 1);
insert into city (name, state_id) values ('São Paulo', 2);
insert into city (name, state_id) values ('Campinas', 2);
insert into city (name, state_id) values ('Fortaleza', 3);

insert into payment_method (description) values ('Cartão de crédito');
insert into payment_method (description) values ('Cartão de débito');
insert into payment_method (description) values ('Dinheiro');

insert into permission (name, description) values ('CONSULTAR_COZINHAS', 'Permite consultar kitchens');
insert into permission (name, description) values ('EDITAR_COZINHAS', 'Permite editar kitchens');
