-- Tabla de Productos (Entidad A)
CREATE TABLE Producto (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Fabricanteid INT NOT NULL,
    Nombre_Producto VARCHAR(100),
    Descripcion TEXT,
    Precio DECIMAL(10, 2),
    Stock INT,
    Categoria VARCHAR(50),
    FOREIGN KEY (Fabricanteid) REFERENCES Fabricante(ID)
);

-- Tabla de Fabricantes (Entidad B)
CREATE TABLE Fabricante (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(100),
    Direccion VARCHAR(255),
    Telefono VARCHAR(15),
    Correo VARCHAR(100),
    Tipo VARCHAR(50)
);

-- Tabla de Pedidos de Fabricantes (Entidad D_B)
CREATE TABLE Pedidos_Fabricantes (
    Fabricante INT NOT NULL,
    Pedidos INT NOT NULL,
    Nombre_Fabricante VARCHAR(100),
    PRIMARY KEY (Fabricante, Pedidos)
);

-- Tabla de Pedidos de Productos (Entidad D_A)
CREATE TABLE Pedidos_Productos (
    Producto INT NOT NULL,
    Pedidos INT NOT NULL,
    Nombre_Producto VARCHAR(100),
    PRIMARY KEY (Producto, Pedidos)
);

-- Tabla de Pedidos (Entidad D)
CREATE TABLE Pedidos (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Fecha_Pedido DATE,
    Fecha_Entrega DATE,
    Cantidad INT,
    Precio_Total DECIMAL(10, 2),
    Estado VARCHAR(50)
);

-- Tabla de Comentarios sobre Productos (Entidad C)
CREATE TABLE Comentario (
    Producto INT NOT NULL,
    Usuario VARCHAR(100),
    Comentario TEXT,
    Fecha_Comentario DATE,
    Valoracion INT,
    FOREIGN KEY (Producto) REFERENCES Producto(ID)
);