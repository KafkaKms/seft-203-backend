CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE app_version
(
    id      BIGINT NOT NULL,
    name    VARCHAR(255),
    version VARCHAR(255),
    CONSTRAINT pk_appversion PRIMARY KEY (id)
);

CREATE TABLE config
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_config PRIMARY KEY (id)
);

CREATE TABLE contact
(
    id          BIGINT      NOT NULL,
    first_name  VARCHAR(30) NOT NULL,
    last_name   VARCHAR(50) NOT NULL,
    title       VARCHAR(30) NOT NULL,
    department  VARCHAR(30),
    project     VARCHAR(30),
    avatar      VARCHAR(255),
    employee_id INTEGER     NOT NULL,
    CONSTRAINT pk_contact PRIMARY KEY (id)
);

CREATE TABLE dashboard
(
    id          BIGINT NOT NULL,
    user_id     BIGINT,
    title       VARCHAR(20),
    layout_type VARCHAR(20),
    CONSTRAINT pk_dashboard PRIMARY KEY (id)
);

CREATE TABLE task
(
    id           BIGINT NOT NULL,
    task         VARCHAR(255),
    is_completed BOOLEAN,
    user_id      VARCHAR(20),
    CONSTRAINT pk_task PRIMARY KEY (id)
);

CREATE TABLE "user"
(
    id        BIGINT      NOT NULL,
    username  VARCHAR(20) NOT NULL,
    email     VARCHAR(50),
    password  VARCHAR(20) NOT NULL,
    full_name VARCHAR(255),
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE widget
(
    id           BIGINT      NOT NULL,
    title        VARCHAR(50) NOT NULL,
    widget_type  VARCHAR(20),
    min_width    INTEGER,
    min_height   INTEGER,
    dashboard_id BIGINT,
    CONSTRAINT pk_widget PRIMARY KEY (id)
);

CREATE TABLE widget_config
(
    config_id BIGINT NOT NULL,
    widget_id BIGINT NOT NULL,
    CONSTRAINT pk_widget_config PRIMARY KEY (config_id, widget_id)
);

ALTER TABLE dashboard
    ADD CONSTRAINT FK_DASHBOARD_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);

ALTER TABLE widget
    ADD CONSTRAINT FK_WIDGET_ON_DASHBOARD FOREIGN KEY (dashboard_id) REFERENCES dashboard (id);

ALTER TABLE widget_config
    ADD CONSTRAINT fk_widcon_on_config FOREIGN KEY (config_id) REFERENCES config (id);

ALTER TABLE widget_config
    ADD CONSTRAINT fk_widcon_on_widget FOREIGN KEY (widget_id) REFERENCES widget (id);