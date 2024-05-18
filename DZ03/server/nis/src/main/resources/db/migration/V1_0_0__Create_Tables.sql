CREATE TABLE  EstateType (
    name        NVARCHAR(100)   PRIMARY KEY,
    description NVARCHAR(2000)  NULL
);

CREATE TABLE Town (
    id       BIGINT        IDENTITY PRIMARY KEY,
    name     NVARCHAR(100) NOT NULL,
    postCode NVARCHAR(20)  NOT NULL,
    region   NVARCHAR(100),
    country  NVARCHAR(100) NOT NULL
);

CREATE TABLE EstateOwner (
    id        BIGINT        IDENTITY PRIMARY KEY,
    name      NVARCHAR(100) NOT NULL,
    surname   NVARCHAR(100) NOT NULL,
    birthDate DATETIME2,
    address   NVARCHAR(200),
    email     NVARCHAR(100),
    townId    BIGINT        NOT NULL,

    CONSTRAINT  FK_Owner_Town FOREIGN KEY (townId) REFERENCES Town (id) ON DELETE  CASCADE
);

CREATE TABLE Estate(
    id          BIGINT IDENTITY  PRIMARY KEY,
    price       FLOAT NOT NULL,
    address     NVARCHAR(200)    NOT NULL,
    area        INT              NOT NULL,
    description NVARCHAR(2000),
    townId      BIGINT           NOT NULL,
    ownerId     BIGINT           NOT NULL,
    estateType  NVARCHAR(100)    NOT NULL,

    CONSTRAINT  FK_Estate_EstateType FOREIGN KEY (estateType) REFERENCES EstateType (name) ON DELETE  CASCADE,
    CONSTRAINT  FK_Estate_Town FOREIGN KEY (townId) REFERENCES Town (id) ON DELETE  CASCADE,
    CONSTRAINT  FK_Estate_EstateOwner FOREIGN KEY (ownerId) REFERENCES EstateOwner (id)
);