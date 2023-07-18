create table activity_history
(
    activity_id        integer      not null auto_increment,
    activity_timestamp datetime(6)  not null,
    activity_type      varchar(255) not null,
    comment_id         integer,
    parent_comment_id  integer,
    post_id            integer,
    primary_user_id    integer      not null,
    secondary_user_id  integer,
    primary key (activity_id)
) engine = InnoDB;
create table bans
(
    ban_id          integer     not null auto_increment,
    date_banned     datetime(6) not null,
    expiration_date date        not null,
    expired         bit,
    commandment_nr  integer     not null,
    mod_id          integer     not null,
    user_id         integer     not null,
    primary key (ban_id)
) engine = InnoDB;
create table categories
(
    category_id   integer     not null auto_increment,
    category_name varchar(21) not null,
    primary key (category_id)
) engine = InnoDB;
create table commandments
(
    commandment_nr      integer      not null auto_increment,
    commandment_rule    varchar(100) not null,
    punishment_duration varchar(20)  not null,
    primary key (commandment_nr)
) engine = InnoDB;
create table comment_ratings
(
    rating_id  integer not null auto_increment,
    weight     integer not null,
    comment_id integer not null,
    user_id    integer not null,
    primary key (rating_id)
) engine = InnoDB;
create table comments
(
    comment_id        integer     not null auto_increment,
    comment_body      varchar(255),
    comment_edited    bit         not null,
    date_commented    datetime(6) not null,
    deleted           bit         not null,
    depth             integer     not null,
    explicit          bit,
    parent_comment_id integer,
    comment_author    integer     not null,
    post_id           integer     not null,
    primary key (comment_id)
) engine = InnoDB;
create table media
(
    media_id      integer not null auto_increment,
    deleted       bit,
    external      bit,
    media_address varchar(255),
    media_name    varchar(255),
    media_size    integer,
    media_type    varchar(255),
    primary key (media_id)
) engine = InnoDB;
create table post_ratings
(
    rating_id integer not null auto_increment,
    weight    integer not null,
    post_id   integer not null,
    user_id   integer not null,
    primary key (rating_id)
) engine = InnoDB;
create table posts
(
    post_id     integer      not null auto_increment,
    date_posted datetime(6)  not null,
    deleted     bit,
    explicit    bit,
    post_edited bit          not null,
    post_title  varchar(140) not null,
    post_url    varchar(40),
    category_id integer      not null,
    group_id    integer      not null,
    post_author integer      not null,
    post_media  integer,
    primary key (post_id)
) engine = InnoDB;
create table sorting_groups
(
    group_id   integer     not null auto_increment,
    group_name varchar(15) not null,
    group_rank integer     not null,
    primary key (group_id)
) engine = InnoDB;
create table user_relations
(
    relation_id       integer      not null auto_increment,
    relation_type     varchar(255) not null,
    primary_user_id   integer      not null,
    secondary_user_id integer      not null,
    primary key (relation_id)
) engine = InnoDB;
create table users
(
    user_id         integer not null auto_increment,
    date_registered datetime(6),
    deleted         bit,
    email           varchar(50),
    gender          char(1),
    honourable_user bit,
    is_banned       bit,
    password        varchar(255),
    user_rating     integer,
    user_role       varchar(255),
    username        varchar(21),
    profile_picture integer,
    primary key (user_id)
) engine = InnoDB;
alter table categories
    add constraint unique_category_name unique (category_name);
alter table commandments
    add constraint unique_commandment_rule unique (commandment_rule);
alter table comment_ratings
    add constraint unique_user_comment_combination unique (comment_id, user_id);
alter table media
    add constraint unique_media_name unique (media_name);
alter table post_ratings
    add constraint unique_user_post_combination unique (post_id, user_id);
alter table sorting_groups
    add constraint unique_sorting_group_name unique (group_name);
alter table user_relations
    add constraint unique_users_relation_combination unique (primary_user_id, secondary_user_id);
alter table users
    add constraint unique_username unique (username);
alter table users
    add constraint unique_user_email unique (email);
alter table activity_history
    add constraint FKpjsr9cxh56ckxjx8kmga4ryo1 foreign key (comment_id) references comments (comment_id);
alter table activity_history
    add constraint FKs94iwsekndc0y9rxvcfs2oi13 foreign key (parent_comment_id) references comments (comment_id);
alter table activity_history
    add constraint FKp85av01hcasx021h5hh1xt7be foreign key (post_id) references posts (post_id);
alter table activity_history
    add constraint FKnfdm32t9g7wwfrc7s790o5wba foreign key (primary_user_id) references users (user_id);
alter table activity_history
    add constraint FKrek3tdgvulxpspmpgaco9t375 foreign key (secondary_user_id) references users (user_id);
alter table bans
    add constraint FK16f81qbc1djteowi6eya9k896 foreign key (commandment_nr) references commandments (commandment_nr);
alter table bans
    add constraint FK8t85i5xd8439ifx7weg8vvfxi foreign key (mod_id) references users (user_id);
alter table bans
    add constraint FKdshp5tj95xpg7ikybc4teb68q foreign key (user_id) references users (user_id);
alter table comment_ratings
    add constraint FKldvx2c8km1jjttr7i1ilpet2a foreign key (comment_id) references comments (comment_id);
alter table comment_ratings
    add constraint FKk74w6k9pb8y1fmy6rxvb1f4oe foreign key (user_id) references users (user_id);
alter table comments
    add constraint FKlq67r7fdfjpatgjsd96igmmf2 foreign key (comment_author) references users (user_id);
alter table comments
    add constraint FKh4c7lvsc298whoyd4w9ta25cr foreign key (post_id) references posts (post_id);
alter table post_ratings
    add constraint FK332yhncbdhwpctdmaeshvab7f foreign key (post_id) references posts (post_id);
alter table post_ratings
    add constraint FK33xf0loprjs7s7y2s6c3hhryc foreign key (user_id) references users (user_id);
alter table posts
    add constraint FKijnwr3brs8vaosl80jg9rp7uc foreign key (category_id) references categories (category_id);
alter table posts
    add constraint FKa1ixrwogdbpujbstt77aq72dj foreign key (group_id) references sorting_groups (group_id);
alter table posts
    add constraint FK5cmn0u2gbs1odm27i1idkh417 foreign key (post_author) references users (user_id);
alter table posts
    add constraint FKegycjb5ahyn6beuh66g4idu4w foreign key (post_media) references media (media_id);
alter table user_relations
    add constraint FK1m04fsot83sw2ft888pur6hff foreign key (primary_user_id) references users (user_id);
alter table user_relations
    add constraint FK6q19lpes9usunfm6c1ax0gra0 foreign key (secondary_user_id) references users (user_id);
alter table users
    add constraint FK1pydgvfup6y3srrvpvlxivr76 foreign key (profile_picture) references media (media_id);
