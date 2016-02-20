insert into language(code, name, status) values ('en', 'English', 'Active');
insert into category(slug, name, size, language_id) values ('music', 'Music', 10, 'en');
insert into category(slug, name, size, language_id) values ('sport', 'Sports', 10, 'en');
commit;