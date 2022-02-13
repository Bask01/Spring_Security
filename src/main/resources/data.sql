INSERT INTO users (email, encryptedpassword, enabled)
VALUES ('bask@sheridancollege.ca', '$2a$10$OpARYXO2pG2fqEU8H77A/eY5fZuWCaLUuVD.u37ArgpgC7YYCJIIS', 1); 
INSERT INTO users (email, encryptedpassword, enabled)
VALUES ('bar@foo.com', '$2a$10$yXffyCCwmKEO74Tok1eiRehnkrkjqerlFdYNLUjwwaRHm5xOub1P.', 1);


INSERT INTO roles (rolename)
VALUES ('ROLE_USER');
INSERT INTO roles (rolename)
VALUES ('ROLE_GUEST');
  

INSERT INTO user_role (userid, roleid)
VALUES (1, 1); 
INSERT INTO user_role (userid, roleid)
VALUES (2, 2);
