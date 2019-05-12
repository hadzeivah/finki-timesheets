CREATE TABLE projects (
  id                   SERIAL PRIMARY KEY,
  name                 VARCHAR(50) NOT NULL,
  project_number       TEXT        NOT NULL,
  partner_organisation TEXT
);


CREATE TABLE timesheets (
  id          SERIAL PRIMARY KEY,
  from_period TIMESTAMP,
  to_period   TIMESTAMP,
  project_id  SERIAL REFERENCES projects,
  member_id   SERIAL REFERENCES members
);


CREATE TABLE members
(
  id                   SERIAL PRIMARY KEY,
  first_name           VARCHAR(50) NOT NULL,
  last_name            VARCHAR(50),
  position_id          SERIAL REFERENCES position
);

ALTER TABLE members
  add embg       TEXT,
  add transaction_account TEXT;

CREATE TABLE position (
  id          SERIAL PRIMARY KEY,
  name        TEXT NOT NULL,
  description TEXT
);

CREATE TABLE projects_members (
  id         SERIAL PRIMARY KEY,
  project_id SERIAL REFERENCES projects,
  member_id  SERIAL REFERENCES members
);

CREATE TABLE items (
  id                 SERIAL PRIMARY KEY,
  timesheet_id       SERIAL REFERENCES timesheets,
  start_date         TIMESTAMP,
  end_date           TIMESTAMP,
  hours              INTEGER,
  task_description   TEXT,
  intellectual_output VARCHAR(50)
);

CREATE TABLE users (
  id         SERIAL NOT NULL,
  age        INT4,
  first_name VARCHAR(255),
  last_name  VARCHAR(255),
  password   VARCHAR(255),
  salary     INT8,
  username   VARCHAR(255),
  PRIMARY KEY (id)
);
-- user inserts
INSERT INTO users ( first_name, last_name, username, password, salary, age)
VALUES ('Alex', 'Knr', 'alex123', '$2a$04$4vwa/ugGbBVDvbWaKUVZBuJbjyQyj6tqntjSmG8q.hi97.xSdhj/2', 3456, 33);
INSERT INTO users ( first_name, last_name, username, password, salary, age)
VALUES ('Tom', 'Asr', 'tom234', '$2a$04$QED4arFwM1AtQWkR3JkQx.hXxeAk/G45NiRd3Q4ElgZdzGYCYKZOW', 7823, 23);
INSERT INTO users (first_name, last_name, username, password, salary, age)
VALUES ('Adam', 'Psr', 'adam', '$2a$04$WeT1SvJaGjmvQj34QG8VgO9qdXecKOYKEDZtCPeeIBSTxxEhazNla', 4234, 45);

-- items inserts

INSERT INTO items(id, timesheet_id, start_date, end_date, hours, task_description, intellectual_output)
VALUES (1, 1, '2019-03-02 19:07:16.464000', '2019-03-02 19:07:18.512000', 8, 'task', 'output');

INSERT INTO items(id, timesheet_id, start_date, end_date, hours, task_description, intellectual_output)
VALUES (2, 1, '2019-03-02 19:07:16.464000', '2019-03-02 19:07:18.512000', 8, 'task', 'output');

INSERT INTO items(id, timesheet_id, start_date, end_date, hours, task_description, intellectual_output)
VALUES (3, 1, '2019-03-02 19:07:16.464000', '2019-03-02 19:07:18.512000', 8, 'task', 'output');

INSERT INTO items(id, timesheet_id, start_date, end_date, hours, task_description, intellectual_output)
VALUES (4, 1, '2019-03-02 19:07:16.464000', '2019-03-02 19:07:18.512000', 8, 'task', 'output');

INSERT INTO items(id, timesheet_id, start_date, end_date, hours, task_description, intellectual_output)
VALUES (5, 1, '2019-03-02 19:07:16.464000', '2019-03-02 19:07:18.512000', 8, 'task', 'output');

select * from projects;
select * from timesheets where project_id = 1;


INSERT INTO items(id, timesheet_id, start_date, end_date, hours, task_description, intellectual_output)
VALUES (6, 2, '2019-03-02 19:07:16.464000', '2019-03-02 19:07:18.512000', 8, 'task', 'output');