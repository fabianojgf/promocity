insert users values('armando', 'armando', true, null);
update users
set password='$2a$10$m5hVTpkyjUc8d5vKiNqKAOlLywOe11nw.QIo1dxh7DiUvMLg4VOMy'
where id=1;
insert authorities values('armando', 'USER', null);
