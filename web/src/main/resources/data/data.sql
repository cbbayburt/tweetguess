insert into language (code, name, status)
  values ('en', 'English', 'production');
insert into language (code, name, status)
  values ('tr', 'Turkish', 'production');
insert into language (code, name, status)
  values ('fr', 'French', 'production');
insert into category (id, slug, name, size, language_Id)
  values (1, 'music', 'Music', 15, 'en');
insert into category (id, slug, name, size, language_Id)
  values (2, 'sports', 'Sport', 15, 'en');
insert into category (id, slug, name, size, language_Id)
  values (3, 'politics', 'Politic', 15, 'en');
insert into category (id, slug, name, size, language_Id)
  values (4, 'musicf', 'Music', 15, 'fr');
insert into category (id, slug, name, size, language_Id)
  values (5, 'sportsf', 'Sport', 15, 'fr');
insert into category (id, slug, name, size, language_Id)
  values (6, 'politics', 'Politic', 15, 'fr');
insert into category (id, slug, name, size, language_Id)
  values (7, 'music', 'Muzik', 15, 'tr');
insert into category (id, slug, name, size, language_Id)
  values (8, 'sports', 'Spor', 15, 'tr');
insert into category (id, slug, name, size, language_Id)
  values (9, 'politicst', 'Politika', 15, 'tr');
insert into person (id, name, screen_Name, profile_imageurl, url, is_Protected, followers_Count, friends_Count, favourites_Count, language_Id, category_Id)
  values (237448180, 'Devrim Yasar', 'devrimyasar', 'http://pbs.twimg.com/profile_images/378800000863144334/uZWGPcJz_normal.jpeg', 'http://t.co/btPeg0c2dr', 'false', 1890, 225, 115, 'en', 1);
insert into person (id, name, screen_Name, profile_imageurl, url, is_Protected, followers_Count, friends_Count, favourites_Count, language_Id, category_Id)
  values (237448181, 'Aykut Akin', 'aykutakin', 'http://pbs.twimg.com/profile_images/378800000863144334/uZWGPcJz_normal.jpeg', 'http://t.co/btPeg0c2dr', 'false', 1890, 225, 115, 'en', 1);
insert into person (id, name, screen_Name, profile_imageurl, url, is_Protected, followers_Count, friends_Count, favourites_Count, language_Id, category_Id)
  values (237448182, 'Can Bulut Bayburt', 'canbulutb', 'http://pbs.twimg.com/profile_images/378800000863144334/uZWGPcJz_normal.jpeg', 'http://t.co/btPeg0c2dr', 'false', 1890, 225, 115, 'en', 1);
insert into person (id, name, screen_Name, profile_imageurl, url, is_Protected, followers_Count, friends_Count, favourites_Count, language_Id, category_Id)
  values (237448183, 'Cansu Baysu', 'cansub', 'http://pbs.twimg.com/profile_images/378800000863144334/uZWGPcJz_normal.jpeg', 'http://t.co/btPeg0c2dr', 'false', 1890, 225, 115, 'en', 1);
insert into person (id, name, screen_Name, profile_imageurl, url, is_Protected, followers_Count, friends_Count, favourites_Count, language_Id, category_Id)
  values (237448184, 'Juergen Hoeller', 'juergen', 'http://pbs.twimg.com/profile_images/378800000863144334/uZWGPcJz_normal.jpeg', 'http://t.co/btPeg0c2dr', 'false', 1890, 225, 115, 'en', 1);
insert into person (id, name, screen_Name, profile_imageurl, url, is_Protected, followers_Count, friends_Count, favourites_Count, language_Id, category_Id)
  values (237448185, 'Steve Jobs', 'steve', 'http://pbs.twimg.com/profile_images/378800000863144334/uZWGPcJz_normal.jpeg', 'http://t.co/btPeg0c2dr', 'false', 1890, 225, 115, 'en', 1);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (1, null, 'tweet1', 100, 200, 'en', 1, 237448180, true);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (2, null, 'tweet2', 100, 200, 'en', 1, 237448180, true);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (3, null, 'tweet3', 100, 200, 'en', 1, 237448180, true);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (4, null, 'tweet4', 100, 200, 'en', 1, 237448180, true);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (5, null, 'tweet5', 100, 200, 'en', 1, 237448181, true);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (6, null, 'tweet6', 100, 200, 'en', 1, 237448181, true);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (7, null, 'tweet7', 100, 200, 'en', 1, 237448181, true);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (8, null, 'tweet8', 100, 200, 'en', 1, 237448182, true);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (9, null, 'tweet9', 100, 200, 'en', 1, 237448182, true);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (10, null, 'tweet10', 100, 200, 'en', 1, 237448182, true);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (11, null, 'tweet11', 100, 200, 'en', 1, 237448182, true);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (12, null, 'tweet12', 100, 200, 'en', 1, 237448182, true);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (13, null, 'tweet13', 100, 200, 'en', 1, 237448183, true);
insert into tweet (id, created_At, text, favorite_Count, retweet_Count, language_Id, category_Id, person_Id, valid)
  values (14, null, 'tweet14', 100, 200, 'en', 1, 237448183, true);

commit;