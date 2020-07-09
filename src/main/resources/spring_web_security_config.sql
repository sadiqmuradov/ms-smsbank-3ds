-- Creating users table
CREATE TABLE ms_smsbank_users (
	username VARCHAR2(100) NOT NULL PRIMARY KEY,
	password VARCHAR2(500) NOT NULL,
	enabled NUMBER(1) NOT NULL CHECK (enabled in (0, 1))
);

-- Creating authorities table
CREATE TABLE ms_smsbank_authorities (
	username VARCHAR2(100) NOT NULL,
	authority VARCHAR2(100) NOT NULL,
	constraint fk_authorities_users foreign key(username) references ms_smsbank_users(username),
    constraint ms_smsbank_unique UNIQUE (username, authority)
);

-- Inserting service users into users table
insert into ms_smsbank_users values('MOBILE_BANK', '$2a$10$9A2cnscTJHBVuY/3tXZ7NeyaZth9ST7ruZw96wHL7wgyOK4/9RIXO', 1);
insert into ms_smsbank_users values('PASHA_BANK', '$2a$10$zq6swkzV8vDALzR4NzRlbO2dLv5Sn8ftohKr7cj/fOPiyhwUKLCgq', 1);

-- Inserting assigned authorities of service users into authorities table
insert into ms_smsbank_authorities values('MOBILE_BANK', 'ROLE_USER');
insert into ms_smsbank_authorities values('PASHA_BANK', 'ROLE_USER');

commit;
