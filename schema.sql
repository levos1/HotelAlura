Create Table hotel_alura.Reservas(
	Id INT AUTO_INCREMENT,
    FechaEntrada date,
    FechaSalida date,
    Valor decimal,
    FormaPago varchar(20),
	Constraint PK_Reserva PRIMARY KEY(Id)

);


CREATE TABLE hotel_alura.Huespedes(
	Id INT AUTO_INCREMENT,
    Nombre varchar(100),
    Apellido varchar(100),
    FechaDeNacimiento date,
    Nacionalidad char(20),
    Telefono varchar(15),
    IdReserva INT,
    Constraint PK_Huesped PRIMARY KEY(Id),
    Constraint FK Foreign Key(IdReserva) References Reservas(Id) ON DELETE CASCADE
);