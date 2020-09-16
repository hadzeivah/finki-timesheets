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
    id          bigserial PRIMARY KEY,
    position_id bigint not null,
    project_id  bigint not null,
    salary      bigint,
    FOREIGN KEY (position_id) REFERENCES positions (id),
    FOREIGN KEY (project_id) REFERENCES projects (id)
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


INSERT INTO users (id, first_name, last_name, username, password)
VALUES (1, 'Hristina', 'Hadjieva', 'hadjieva.hristina', '$2a$04$4vwa/ugGbBVDvbWaKUVZBuJbjyQyj6tqntjSmG8q.hi97.xSdhj/2');

INSERT INTO users (id, first_name, last_name, username, password)
VALUES (2, 'Ivan', 'Chorbev', 'chorbev.ivan', '$2a$04$4vwa/ugGbBVDvbWaKUVZBuJbjyQyj6tqntjSmG8q.hi97.xSdhj/2');

INSERT INTO public.positions (description, name)
VALUES ('MANAGER', 'MANAGER');
INSERT INTO public.positions (description, name)
VALUES ('TEACHER', 'TEACHER');
INSERT INTO public.positions (description, name)
VALUES ('TRAINER', 'TRAINER');
INSERT INTO public.positions (description, name)
VALUES ('RESEARCHER', 'RESEARCHER');
INSERT INTO public.positions (description, name)
VALUES ('TECHNICIAN', 'TECHNICIAN');
INSERT INTO public.positions (description, name)
VALUES ('ADMINISTRATIVE', 'ADMINISTRATIVE');

INSERT INTO public.university (id, dean, name)
VALUES (1, 'Вон Проф. д-р Иван Чорбев', 'Факултет за информатички науки и компјутерско инженерство - ФИНКИ');


CREATE OR REPLACE VIEW report_total_by_intellectual_output AS
select row_number() OVER ()    AS id,
       t.project_id,
       p.name                  as project_name,
       intellectual_output,
       sum(hours * salary) / 8 as total
from items
         join timesheets t on items.timesheet_id = t.id
         join projects p on t.project_id = p.id
         join project_position pp on pp.id = t.project_position
GROUP BY t.project_id, p.name, intellectual_output, salary;



create table roles
(
    id   bigint not null
        constraint role_pkey
            primary key,
    name varchar(255)
);



INSERT INTO roles (id, name)
VALUES (1, 'ADMIN');
INSERT INTO roles (id, name)
VALUES (2, 'USER');

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id)
VALUES (2, 2);


INSERT INTO public.work_packages (id, name)
VALUES (DEFAULT, 'WP1');

INSERT INTO public.work_packages (id, name)
VALUES (DEFAULT, 'WP2');

INSERT INTO public.work_packages (id, name)
VALUES (DEFAULT, 'WP3');

INSERT INTO public.work_packages (id, name)
VALUES (DEFAULT, 'WP3');

INSERT INTO public.work_packages (id, name)
VALUES (DEFAULT, 'WP4');

INSERT INTO public.work_packages (id, name)
VALUES (DEFAULT, 'WP5');


INSERT INTO public.tasks (id, description, work_package_id)
VALUES (DEFAULT, '1.2 Developing an action plan', 1);
INSERT INTO public.tasks (id, description, work_package_id)
VALUES (DEFAULT, '1.3 Analysing the content of international educational master programs in Computer Linguistics', 1);
INSERT INTO public.tasks (id, description, work_package_id)
VALUES (DEFAULT,
        '1.4 Teaching and instructing CA academic staff in syntactic, morphological and semantic structure of the English language',
        1);
INSERT INTO public.tasks (id, description, work_package_id)
VALUES (DEFAULT, '1.5 Purchasing equipment for administrative and teaching issues', 1);
INSERT INTO public.tasks (id, description, work_package_id)
VALUES (DEFAULT, '1.1 Mobility to EU university for the kick off meeting', 1)



INSERT INTO public.outputs (id, description, work_package_id)
VALUES (DEFAULT, '1.1 Action plan on developing Interdisciplinary program.', 1);
INSERT INTO public.outputs (id, description, work_package_id)
VALUES (DEFAULT, '1.2 Analysed international master programs.', 1);
INSERT INTO public.outputs (id, description, work_package_id)
VALUES (DEFAULT, '1.3 Competence building in syntactic, morphological and semantic knowledge of English language', 1);
INSERT INTO public.outputs (id, description, work_package_id)
VALUES (DEFAULT, '1.4 Equipment for administrative and teaching issues.', 1);



CREATE OR REPLACE VIEW report_total_by_intellectual_output AS
select row_number() OVER () AS id, member_id, t.project_id, sum(hours * salary) / 8 as total
from items
         join timesheets t on items.timesheet_id = t.id
         join projects p on t.project_id = p.id
         join project_position pp on pp.id = t.project_position
GROUP BY t.member_id, t.project_id, p.name, salary;

create or replace view working_hours_summary_by_member(id, date, member_id, hours) as
SELECT row_number() OVER () AS id,
       date,
       member_id,
       sum(hours)
from timesheets
         join items i on timesheets.id = i.timesheet_id
GROUP BY member_id, date;
