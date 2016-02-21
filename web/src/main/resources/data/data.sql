insert into language (code, name, status)
  values ('en', 'English', 'production');
insert into category (slug, name, size, language_Id)
  values ('music', 'Music', 15, 'en');
insert into category (slug, name, size, language_Id)
  values ('sports', 'Sport', 15, 'en');
insert into category (slug, name, size, language_Id)
  values ('politics', 'Politic', 15, 'en');
insert into person (id, name, screen_Name, profile_Image_Url, url, is_Protected, followers_Count, friends_Count, favourites_Count, lang_Id, category_Id)
  values (237448180, 'Devrim Yasar', 'devrimyasar', 'http://pbs.twimg.com/profile_images/378800000863144334/uZWGPcJz_normal.jpeg', 'http://t.co/btPeg0c2dr', 'false', 1890, 225, 115, 'en', 'music');
insert into person (id, name, screen_Name, profile_Image_Url, url, is_Protected, followers_Count, friends_Count, favourites_Count, lang_Id, category_Id)
  values (237448181, 'Aykut Akin', 'aykutakin', 'http://pbs.twimg.com/profile_images/378800000863144334/uZWGPcJz_normal.jpeg', 'http://t.co/btPeg0c2dr', 'false', 1890, 225, 115, 'en', 'music');
insert into person (id, name, screen_Name, profile_Image_Url, url, is_Protected, followers_Count, friends_Count, favourites_Count, lang_Id, category_Id)
  values (237448182, 'Can Bulut Bayburt', 'canbulutb', 'http://pbs.twimg.com/profile_images/378800000863144334/uZWGPcJz_normal.jpeg', 'http://t.co/btPeg0c2dr', 'false', 1890, 225, 115, 'en', 'music');
insert into person (id, name, screen_Name, profile_Image_Url, url, is_Protected, followers_Count, friends_Count, favourites_Count, lang_Id, category_Id)
  values (237448183, 'Cansu Baysu', 'cansub', 'http://pbs.twimg.com/profile_images/378800000863144334/uZWGPcJz_normal.jpeg', 'http://t.co/btPeg0c2dr', 'false', 1890, 225, 115, 'en', 'music');
insert into person (id, name, screen_Name, profile_Image_Url, url, is_Protected, followers_Count, friends_Count, favourites_Count, lang_Id, category_Id)
  values (237448184, 'Juergen Hoeller', 'juergen', 'http://pbs.twimg.com/profile_images/378800000863144334/uZWGPcJz_normal.jpeg', 'http://t.co/btPeg0c2dr', 'false', 1890, 225, 115, 'en', 'music');
insert into person (id, name, screen_Name, profile_Image_Url, url, is_Protected, followers_Count, friends_Count, favourites_Count, lang_Id, category_Id)
  values (237448185, 'Steve Jobs', 'steve', 'http://pbs.twimg.com/profile_images/378800000863144334/uZWGPcJz_normal.jpeg', 'http://t.co/btPeg0c2dr', 'false', 1890, 225, 115, 'en', 'music');
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (1, null, 'tweet1', 100, 200, 'en', 'music', 237448180);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (2, null, 'tweet2', 100, 200, 'en', 'music', 237448180);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (3, null, 'tweet3', 100, 200, 'en', 'music', 237448180);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (4, null, 'tweet4', 100, 200, 'en', 'music', 237448180);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (5, null, 'tweet5', 100, 200, 'en', 'music', 237448181);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (6, null, 'tweet6', 100, 200, 'en', 'music', 237448181);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (7, null, 'tweet7', 100, 200, 'en', 'music', 237448181);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (8, null, 'tweet8', 100, 200, 'en', 'music', 237448182);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (9, null, 'tweet9', 100, 200, 'en', 'music', 237448182);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (10, null, 'tweet10', 100, 200, 'en', 'music', 237448182);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (11, null, 'tweet11', 100, 200, 'en', 'music', 237448182);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (12, null, 'tweet12', 100, 200, 'en', 'music', 237448182);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (13, null, 'tweet13', 100, 200, 'en', 'music', 237448183);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, lang_Id, category_Id, person_Id)
  values (14, null, 'tweet14', 100, 200, 'en', 'music', 237448183);
commit;