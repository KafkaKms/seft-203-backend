ALTER TABLE task
ALTER COLUMN user_id TYPE bigint USING user_id::bigint;

ALTER TABLE task
ADD CONSTRAINT FK_TASK_ON_USER FOREIGN KEY (user_id) references "user" (id);
