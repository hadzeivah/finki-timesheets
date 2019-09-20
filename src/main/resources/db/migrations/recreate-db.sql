create table if not exists members
(
    id                  bigserial PRIMARY KEY,
    embg                varchar(255),
    first_name          varchar(255),
    last_name           varchar(255),
    position_type       varchar(255),
    transaction_account varchar(255)
);

create table if not exists positions
(
    id          bigserial PRIMARY KEY,
    description varchar(255),
    name        varchar(255),
    salary      integer
);

create table if not exists university
(
    id   bigserial PRIMARY KEY,
    dean varchar(255),
    name varchar(255)
);

create table if not exists projects
(
    id                   bigserial PRIMARY KEY,
    end_date             timestamp,
    name                 varchar(255),
    partner_organisation varchar(255),
    project_number       varchar(255),
    start_date           timestamp,
    university_id        bigint,
    FOREIGN KEY (university_id) REFERENCES university (id)
);

create table if not exists project_member
(
    id         bigserial,
    member_id  bigint references members,
    project_id bigint references projects,
    constraint project_member_pkey
        primary key (member_id, project_id)
);

create table if not exists timesheets
(
    id          bigserial PRIMARY KEY ,
    from_period timestamp,
    to_period   timestamp,
    member_id   bigint references members,
    project_id  bigint references projects
);

create table if not exists items
(
    id                  bigserial PRIMARY KEY,
    end_date            timestamp,
    hours               integer,
    intellectual_output varchar(255),
    start_date          timestamp,
    task_description    varchar(255),
    timesheet_id        bigint,
    FOREIGN KEY (timesheet_id) REFERENCES timesheets (id)

);

create table if not exists users
(
    id         bigserial PRIMARY KEY,
    age        integer,
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    username   varchar(255)
);

-- user inserts
INSERT INTO users ( first_name, last_name, username, password, age)
VALUES ('Alex', 'Knr', 'alex123', '$2a$04$4vwa/ugGbBVDvbWaKUVZBuJbjyQyj6tqntjSmG8q.hi97.xSdhj/2',33);
INSERT INTO users ( first_name, last_name, username, password, age)
VALUES ('Tom', 'Asr', 'tom234', '$2a$04$QED4arFwM1AtQWkR3JkQx.hXxeAk/G45NiRd3Q4ElgZdzGYCYKZOW', 23);
INSERT INTO users (first_name, last_name, username, password, age)
VALUES ('Adam', 'Psr', 'adam', '$2a$04$WeT1SvJaGjmvQj34QG8VgO9qdXecKOYKEDZtCPeeIBSTxxEhazNla', 45);

-- university inserts
INSERT INTO university (id, dean, name) VALUES (1, 'Иван Чорбев', 'Факултет за информатички науки и компјутерско инженерство - ФИНКИ');

-- positions inserts
INSERT INTO positions (id, description, name, salary) VALUES (1, 'MANAGER', 'MANAGER', 70);
INSERT INTO positions (id, description, name, salary) VALUES (2, 'TEACHER', 'TEACHER', 70);
INSERT INTO positions (id, description, name, salary) VALUES (3, 'TRAINER', 'TRAINER', 70);
INSERT INTO positions (id, description, name, salary) VALUES (4, 'RESEARCHER', 'RESEARCHER', 70);
INSERT INTO positions (id, description, name, salary) VALUES (5, 'TECHNICIAN', 'TECHNICIAN', 70);
INSERT INTO positions (id, description, name, salary) VALUES (6, 'ADMINISTRATIVE', 'ADMINISTRATIVE', 70);

create table project_position
(
    id bigserial not null,
    position_id bigint not null,
    project_id  bigint not null,
    salary bigint,
    FOREIGN KEY (position_id) REFERENCES positions (id),
    FOREIGN KEY (project_id) REFERENCES projects (id),
    constraint project_position_pkey
        primary key (position_id, project_id)
);