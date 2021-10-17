CREATE TABLE user_jwt (
  id BIGINT NOT NULL,
  token VARCHAR(255),
  refresh_token VARCHAR(255),
  user_id BIGINT,
  is_valid BOOLEAN,
  CONSTRAINT pk_userjwt PRIMARY KEY (id)
);

ALTER TABLE user_jwt ADD CONSTRAINT FK_USERJWT_ON_USER FOREIGN KEY (user_id) REFERENCES public."user" (id);