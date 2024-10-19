CREATE DATABASE EjemploCRUDDB;

USE EjemploCRUDDB;

CREATE TABLE Alumno (
    id INT PRIMARY KEY IDENTITY(1,1),         
    nombre VARCHAR(100) NOT NULL,       
    apellido VARCHAR(100) NOT NULL,     
    dni VARCHAR(8) NOT NULL,            
    telefono VARCHAR(15)                
);

select * from Alumno;

