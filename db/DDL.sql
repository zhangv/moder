DROP TABLE APP.t_ledger_entry;
CREATE TABLE APP.t_ledger_entry (
  id integer not null,
  name varchar(50),
  principal double NOT NULL,
  rate double NOT NULL,
  period varchar(20) NOT NULL,
  compound varchar(2) NOT NULL,
  startdate date NOT NULL,
  status integer NOT NULL,
  PRIMARY KEY(id) );
  