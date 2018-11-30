insert promotion values(null, 'Promocao 1', CURDATE(), CURDATE()+1);
insert coupon values(null, 'Cupom 1 da promocao 1', 10, '1022112233');
insert promotion_coupons values(1,1);
insert promotion values(null, 'Promocao 2', CURDATE()+1, CURDATE()+2);
insert coupon values(null, 'Cupom 2 da promocao 2', 20, '20221122332');
insert promotion_coupons values(2,2);
insert store values(null, 'Rua Teste 1', 'Cidade teste1', 0, 0, 'Loja 1', 0, 'PI');
insert store_promotion_list values(1,1);
