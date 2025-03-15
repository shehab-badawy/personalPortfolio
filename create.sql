
    create sequence achievement_types_seq start with 1 increment by 50;

    create sequence achievements_seq start with 1 increment by 50;

    create sequence persons_seq start with 1 increment by 50;

    create sequence posts_seq start with 1 increment by 50;

    create sequence technologies_seq start with 1 increment by 50;

    create table achievement_types (
        id bigint not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table achievements (
        id bigint not null,
        type_id bigint not null,
        description varchar(255) not null,
        git_hub_link varchar(255),
        name varchar(255) not null unique,
        primary key (id)
    );

    create table achievements_technologies (
        achie_id bigint not null,
        tech_id bigint not null
    );

    create table persons (
        id bigint not null,
        name varchar(255),
        primary key (id)
    );

    create table posts (
        id bigint not null,
        person_id bigint not null,
        descreption varchar(255),
        primary key (id)
    );

    create table technologies (
        id bigint not null,
        name varchar(255),
        primary key (id)
    );

    create table visuals (
        achievement_id bigint not null,
        description varchar(255),
        visual_link varchar(255) not null,
        primary key (achievement_id, visual_link)
    );

    alter table if exists achievements 
       add constraint FKmo6m290wsq95a6y226ynky0a8 
       foreign key (type_id) 
       references achievement_types;

    alter table if exists achievements_technologies 
       add constraint FK3hrnglvwwana2bxlprow3c5hb 
       foreign key (tech_id) 
       references technologies;

    alter table if exists achievements_technologies 
       add constraint FKd5gbvd3k7e8ymnafabq6yheq7 
       foreign key (achie_id) 
       references achievements;

    alter table if exists posts 
       add constraint FKoehc0y5se21rdwbf0j2dhr2bq 
       foreign key (person_id) 
       references persons;

    alter table if exists visuals 
       add constraint FKk3ngxl41h99f4mkwnq83vcujl 
       foreign key (achievement_id) 
       references achievements;

    create sequence achievement_types_seq start with 1 increment by 50;

    create sequence achievements_seq start with 1 increment by 50;

    create sequence persons_seq start with 1 increment by 50;

    create sequence posts_seq start with 1 increment by 50;

    create sequence technologies_seq start with 1 increment by 50;

    create table achievement_types (
        id bigint not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table achievements (
        id bigint not null,
        type_id bigint not null,
        description varchar(255) not null,
        git_hub_link varchar(255),
        name varchar(255) not null unique,
        primary key (id)
    );

    create table achievements_technologies (
        achie_id bigint not null,
        tech_id bigint not null
    );

    create table persons (
        id bigint not null,
        name varchar(255),
        primary key (id)
    );

    create table posts (
        id bigint not null,
        person_id bigint not null,
        descreption varchar(255),
        primary key (id)
    );

    create table technologies (
        id bigint not null,
        name varchar(255),
        primary key (id)
    );

    create table visuals (
        achievement_id bigint not null,
        description varchar(255),
        visual_link varchar(255) not null,
        primary key (achievement_id, visual_link)
    );

    alter table if exists achievements 
       add constraint FKmo6m290wsq95a6y226ynky0a8 
       foreign key (type_id) 
       references achievement_types;

    alter table if exists achievements_technologies 
       add constraint FK3hrnglvwwana2bxlprow3c5hb 
       foreign key (tech_id) 
       references technologies;

    alter table if exists achievements_technologies 
       add constraint FKd5gbvd3k7e8ymnafabq6yheq7 
       foreign key (achie_id) 
       references achievements;

    alter table if exists posts 
       add constraint FKoehc0y5se21rdwbf0j2dhr2bq 
       foreign key (person_id) 
       references persons;

    alter table if exists visuals 
       add constraint FKk3ngxl41h99f4mkwnq83vcujl 
       foreign key (achievement_id) 
       references achievements;

    create sequence achievement_types_seq start with 1 increment by 50;

    create sequence achievements_seq start with 1 increment by 50;

    create sequence persons_seq start with 1 increment by 50;

    create sequence posts_seq start with 1 increment by 50;

    create sequence technologies_seq start with 1 increment by 50;

    create table achievement_types (
        id bigint not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table achievements (
        id bigint not null,
        type_id bigint not null,
        description varchar(255) not null,
        git_hub_link varchar(255),
        name varchar(255) not null unique,
        primary key (id)
    );

    create table achievements_technologies (
        achie_id bigint not null,
        tech_id bigint not null
    );

    create table persons (
        id bigint not null,
        name varchar(255),
        primary key (id)
    );

    create table posts (
        id bigint not null,
        person_id bigint not null,
        descreption varchar(255),
        primary key (id)
    );

    create table technologies (
        id bigint not null,
        name varchar(255),
        primary key (id)
    );

    create table visuals (
        achievement_id bigint not null,
        description varchar(255),
        visual_link varchar(255) not null,
        primary key (achievement_id, visual_link)
    );

    alter table if exists achievements 
       add constraint FKmo6m290wsq95a6y226ynky0a8 
       foreign key (type_id) 
       references achievement_types;

    alter table if exists achievements_technologies 
       add constraint FK3hrnglvwwana2bxlprow3c5hb 
       foreign key (tech_id) 
       references technologies;

    alter table if exists achievements_technologies 
       add constraint FKd5gbvd3k7e8ymnafabq6yheq7 
       foreign key (achie_id) 
       references achievements;

    alter table if exists posts 
       add constraint FKoehc0y5se21rdwbf0j2dhr2bq 
       foreign key (person_id) 
       references persons;

    alter table if exists visuals 
       add constraint FKk3ngxl41h99f4mkwnq83vcujl 
       foreign key (achievement_id) 
       references achievements;
