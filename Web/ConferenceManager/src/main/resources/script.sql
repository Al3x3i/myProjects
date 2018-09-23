-- DROP TABLE IF EXISTS room
DROP TABLE IF EXISTS conference
DROP TABLE IF EXISTS participant
DROP TABLE IF EXISTS conferenceRoom
DROP TABLE IF EXISTS conferenceParticipant

CREATE TABLE conferenceRoom( id INTEGER Primary key AUTOINCREMENT, room_name varchar(30) not null, max_size Integer not null)

CREATE TABLE conference( id INTEGER Primary key AUTOINCREMENT, conference_name varchar(50) not null, expected_participants Integer not null, room_id Integer not null, FOREIGN KEY (room_id) REFERENCES conferenceRoom(id))

CREATE TABLE participant( id INTEGER Primary key AUTOINCREMENT, first_name varchar(30) not null, second_name varchar(30) not null, conference_id Integer not null, FOREIGN KEY (conference_id) REFERENCES conference(id) ON DELETE CASCADE)
