INSERT INTO user (user_id, email, name, surname, password, status,locked,expired,enabled)
VALUES (0, 'admin@admin.com','Admin','Admin','$2a$10$6mhOFnXYWpJQ6JvPsXMsnefBeIYQOIlCHsUTIzL0qvtKYIgI038bS','ROLE_Admin', false, false, true);

INSERT INTO Token (id,token,token_creation_at, token_expire_at ,token_confirmed_at, user_id )
VALUES (0,
'tokenAdmin',
'2022-02-24 08:26:35.473174',
'2022-02-24 08:26:35.473174',
'2022-02-24 08:26:35.473174',
0);

