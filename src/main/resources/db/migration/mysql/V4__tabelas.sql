-- usuarios
ALTER TABLE usuarios
ADD COLUMN confirmado boolean not null default false;
