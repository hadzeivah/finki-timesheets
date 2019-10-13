create table if not exists members
(
    id                  bigserial PRIMARY KEY,
    embg                varchar(255),
    first_name          varchar(255),
    last_name           varchar(255),
    transaction_account varchar(255),
    is_deleted          boolean default false,
    created_at          timestamp,
    updated_at          timestamp
);

create table if not exists positions
(
    id          bigint PRIMARY KEY,
    description varchar(255),
    name        varchar(255)
);

create table if not exists university
(
    id   bigserial PRIMARY KEY,
    dean varchar(255),
    name varchar(255)
);
INSERT INTO university (id, dean, name)
VALUES (1, 'Вон Проф. д-р Иван Чорбев', 'Факултет за информатички науки и компјутерско инженерство - ФИНКИ');

create table projects
(
    id               bigserial not null
        constraint projects_pkey
            primary key,
    end_date         timestamp,
    name             varchar(255),
    project_number   varchar(255),
    start_date       timestamp,
    university_id    bigint
        constraint projects_university_id_fkey
            references university,
    estimated_budget bigint,
    is_deleted       boolean default false,
    created_at       timestamp,
    updated_at       timestamp
);

create table if not exists timesheets
(
    id          bigserial PRIMARY KEY,
    from_period timestamp,
    to_period   timestamp,
    member_id   bigint references members,
    project_id  bigint references projects
);


create table timesheets
(
    id          bigserial not null
        constraint timesheets_pkey
            primary key,
    member_id   bigint
        constraint fk_members
            references members,
    position_id bigint
        constraint fk_project_position
            references project_position (position_id),
    project_id  bigint
        constraint fk_projects
            references projects,
    is_deleted  boolean default false,
    created_at  timestamp,
    updated_at  timestamp
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
INSERT INTO users (first_name, last_name, username, password)
VALUES ('Hristina', 'Hadjieva', 'Hristina Hadjieva', '$2a$04$4vwa/ugGbBVDvbWaKUVZBuJbjyQyj6tqntjSmG8q.hi97.xSdhj/2');


INSERT INTO positions (description, name)
VALUES ('MANAGER', 'MANAGER');
INSERT INTO positions (description, name)
VALUES ('TEACHER', 'TEACHER');
INSERT INTO positions (description, name)
VALUES ('TRAINER', 'TRAINER');
INSERT INTO positions (description, name)
VALUES ('RESEARCHER', 'RESEARCHER');
INSERT INTO positions (description, name)
VALUES ('TECHNICIAN', 'TECHNICIAN');
INSERT INTO positions (description, name)
VALUES ('ADMINISTRATIVE', 'ADMINISTRATIVE');

create table project_position
(
    id          bigserial not null,
    position_id bigint    not null,
    project_id  bigint    not null,
    salary      bigint,
    FOREIGN KEY (position_id) REFERENCES positions (id),
    FOREIGN KEY (project_id) REFERENCES projects (id),
    constraint project_position_pkey
        primary key (position_id, project_id)
);


create table project_position
(
    position_id bigint  not null
        constraint uk_constraint
            unique
        constraint fk_positions
            references positions,
    project_id  bigint  not null
        constraint fk_projects
            references projects,
    salary      integer not null,
    constraint project_position_pkey
        primary key (position_id, project_id)
);

create table holidays
(
    id   bigserial not null,
    date timestamp,
    name text
);



INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-01-01T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-01-07T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-01-06T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-01-19T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-04-26T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-04-29T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-05-01T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-05-24T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-06-04T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-06-14T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-07-02T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-07-28T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-09-08T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-10-11T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-11-23T18:30:00.000Z', null);
INSERT INTO holidays (id, date, name)
VALUES (DEFAULT, '2019-12-08T18:30:00.000Z', null);