--Create position table
CREATE TABLE position (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(255) NOT NULL,
    created_date DATE NOT NULL
);

--Create comment table
CREATE TABLE comment (
    id UUID PRIMARY KEY,
    author VARCHAR(255) NOT NULL,
    comment TEXT NOT NULL,
    created_datetime TIMESTAMP NOT NULL,
    candidate_id UUID REFERENCES candidates(id) ON DELETE CASCADE
);

--Create candidate_position table
CREATE TABLE candidate_position (
    candidate_id UUID NOT NULL REFERENCES candidates(id) ON DELETE CASCADE,
    position_id UUID NOT NULL REFERENCES position(id) ON DELETE CASCADE,
    PRIMARY KEY (candidate_id, position_id)
);