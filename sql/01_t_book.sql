create table t_book(
  id int not null,
  author varchar2(100) not null,
  title varchar2(100) not null,
  description varchar2(255),
  pub_year number(4) not null,
  constraint t_book_pk primary key(id)
);

create sequence s_book;
