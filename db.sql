-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-11-2019 a las 02:02:42
-- Versión del servidor: 10.1.35-MariaDB
-- Versión de PHP: 7.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bakery`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `charcuteria`
--

CREATE TABLE `charcuteria` (
  `codigo` varchar(25) NOT NULL,
  `producto` varchar(30) NOT NULL,
  `marca` varchar(40) NOT NULL,
  `peso` double NOT NULL,
  `costo` double NOT NULL,
  `precio` double NOT NULL,
  `costot` double NOT NULL,
  `preciot` double NOT NULL,
  `img` mediumblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `charcuteria`
--

INSERT INTO `charcuteria` (`codigo`, `producto`, `marca`, `peso`, `costo`, `precio`, `costot`, `preciot`, `img`) VALUES
('01', 's', 's', 0, 0, 0, 0, 0, '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compras`
--

CREATE TABLE `compras` (
  `itm` int(15) NOT NULL,
  `codigo` varchar(100) NOT NULL,
  `tipo` varchar(100) NOT NULL,
  `productos` varchar(600) NOT NULL,
  `cantidad` varchar(100) NOT NULL,
  `precio` varchar(100) NOT NULL,
  `precio t` varchar(100) NOT NULL,
  `total` varchar(100) NOT NULL,
  `abono` varchar(45) NOT NULL,
  `fecha` date NOT NULL,
  `cod_pro` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consumo interno`
--

CREATE TABLE `consumo interno` (
  `codigo` varchar(25) NOT NULL,
  `producto` varchar(60) NOT NULL,
  `cantidad` int(15) NOT NULL,
  `costo` double NOT NULL,
  `costo total` double NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `galletas`
--

CREATE TABLE `galletas` (
  `codigo` varchar(25) NOT NULL,
  `producto` varchar(30) NOT NULL,
  `cantidad` double NOT NULL DEFAULT '0',
  `costo` double NOT NULL DEFAULT '0',
  `costot` double NOT NULL DEFAULT '0',
  `precio` double NOT NULL DEFAULT '0',
  `preciot` double NOT NULL DEFAULT '0',
  `img` mediumblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `galletas`
--

INSERT INTO `galletas` (`codigo`, `producto`, `cantidad`, `costo`, `costot`, `precio`, `preciot`, `img`) VALUES
('001', 'aa', 0, 0, 0, 0, 0, ''),
('1', 'a', 0, 0, 0, 0, 0, ''),
('2', 'a', 0, 0, 0, 0, 0, ''),
('3', 'b', 0, 0, 0, 0, 0, ''),
('6', '6', 0, 0, 0, 0, 0, ''),
('7', 'c', 0, 0, 0, 0, 0, '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `local`
--

CREATE TABLE `local` (
  `codigo` varchar(25) NOT NULL,
  `producto` varchar(30) NOT NULL,
  `cantidad` double NOT NULL,
  `costo` double NOT NULL,
  `costot` double NOT NULL,
  `precio` double NOT NULL,
  `preciot` double NOT NULL,
  `img` mediumblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `local`
--

INSERT INTO `local` (`codigo`, `producto`, `cantidad`, `costo`, `costot`, `precio`, `preciot`, `img`) VALUES
('a', 'b', 0, 0, 0, 0, 0, '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `materia prima`
--

CREATE TABLE `materia prima` (
  `codigo` varchar(25) NOT NULL,
  `producto` varchar(30) NOT NULL,
  `marca` varchar(40) NOT NULL,
  `unidades` double NOT NULL,
  `costo` double NOT NULL,
  `costot` double NOT NULL,
  `img` mediumblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `materia prima`
--

INSERT INTO `materia prima` (`codigo`, `producto`, `marca`, `unidades`, `costo`, `costot`, `img`) VALUES
('', '', '', 0, 0, 0, ''),
('01', 'b', '12', 0, 0, 0, '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nombre`
--

CREATE TABLE `nombre` (
  `empresa` varchar(45) NOT NULL,
  `img` mediumblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perdidas`
--

CREATE TABLE `perdidas` (
  `codigo` varchar(50) NOT NULL,
  `producto` varchar(60) NOT NULL,
  `cantidad` int(15) NOT NULL,
  `costo` double NOT NULL,
  `causa` varchar(45) NOT NULL,
  `costot` double NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `produccion`
--

CREATE TABLE `produccion` (
  `codigo` varchar(25) NOT NULL,
  `producto` varchar(30) NOT NULL,
  `ingredientes` varchar(800) NOT NULL,
  `resultado` double NOT NULL,
  `costo unitario` double NOT NULL,
  `costo total` double NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `codigo` varchar(40) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `rif` varchar(25) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `ciudad` varchar(25) NOT NULL,
  `email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `pass` varchar(128) NOT NULL,
  `tipo` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `pass`, `tipo`) VALUES
(1, 'admin', '$2y$10$DYOVy9GXvC2IKh8UlGDgNerZM4fWl5v4ySpaptQVyhf7ktSEA5or.', 'admin');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `codigo` varchar(100) NOT NULL,
  `tipo` varchar(100) NOT NULL,
  `productos` varchar(600) NOT NULL,
  `cantidad` varchar(100) NOT NULL,
  `precio` varchar(100) NOT NULL,
  `precio t` varchar(100) NOT NULL,
  `total` varchar(100) NOT NULL,
  `tipo de pago` varchar(50) NOT NULL,
  `cliente` varchar(50) NOT NULL,
  `ci` varchar(30) NOT NULL,
  `referencia` varchar(50) NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `viveres`
--

CREATE TABLE `viveres` (
  `codigo` varchar(25) NOT NULL,
  `producto` varchar(30) NOT NULL,
  `marca` varchar(40) NOT NULL,
  `cantidad` double NOT NULL,
  `costo` double NOT NULL,
  `precio` double NOT NULL,
  `costot` double NOT NULL,
  `preciot` double NOT NULL,
  `img` mediumblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `viveres`
--

INSERT INTO `viveres` (`codigo`, `producto`, `marca`, `cantidad`, `costo`, `precio`, `costot`, `preciot`, `img`) VALUES
('2', '6', 'b', 0, 0, 0, 0, 0, '');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `charcuteria`
--
ALTER TABLE `charcuteria`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `compras`
--
ALTER TABLE `compras`
  ADD KEY `fk_pro` (`cod_pro`);

--
-- Indices de la tabla `galletas`
--
ALTER TABLE `galletas`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `local`
--
ALTER TABLE `local`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `materia prima`
--
ALTER TABLE `materia prima`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `viveres`
--
ALTER TABLE `viveres`
  ADD PRIMARY KEY (`codigo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `compras`
--
ALTER TABLE `compras`
  ADD CONSTRAINT `fk_pro` FOREIGN KEY (`cod_pro`) REFERENCES `proveedores` (`codigo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
