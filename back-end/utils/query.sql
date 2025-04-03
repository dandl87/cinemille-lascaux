CREATE TABLE IF NOT EXISTS rooms (
    id BIGSERIAL NOT NULL,
    name VARCHAR(250) NOT NULL,
    seats INT NOT NULL,
    imax BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS movies (
     id BIGSERIAL NOT NULL,
     title VARCHAR(250) NOT NULL,
     director VARCHAR(250) NOT NULL,
     year VARCHAR(4) NOT NULL,
     duration INT NOT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS weeks (
      id BIGSERIAL NOT NULL,
      start DATE NOT NULL,
      ending DATE NOT NULL,
      PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS screenings (
      id BIGSERIAL NOT NULL,
      room BIGSERIAL NOT NULL ,
      movie BIGSERIAL NOT NULL,
      week BIGSERIAL NOT NULL, 
      PRIMARY KEY (id),
      CONSTRAINT fk_room FOREIGN KEY (room) REFERENCES rooms(id),
      CONSTRAINT fk_movie FOREIGN KEY (movie) REFERENCES movies(id),
      CONSTRAINT fk_week FOREIGN KEY (week) REFERENCES weeks(id)
); 

