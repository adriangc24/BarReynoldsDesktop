-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-11-2019 a las 17:08:57
-- Versión del servidor: 10.4.8-MariaDB
-- Versión de PHP: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `barreynolds`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cambrer`
--

CREATE TABLE `cambrer` (
  `ID` int(50) NOT NULL,
  `Nom_Cambrer` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cambrer`
--

INSERT INTO `cambrer` (`ID`, `Nom_Cambrer`) VALUES
(1, 'Sergio'),
(2, 'Adrian'),
(3, 'Javier');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `ID` int(50) NOT NULL,
  `Nom_Categoria` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`ID`, `Nom_Categoria`) VALUES
(1, 'begudes'),
(2, 'tapes'),
(3, 'plats'),
(4, 'entrepans');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comanda`
--

CREATE TABLE `comanda` (
  `ID` int(100) NOT NULL,
  `ID_Cambrer` int(100) NOT NULL,
  `hora_creacio` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `comanda`
--

INSERT INTO `comanda` (`ID`, `ID_Cambrer`, `hora_creacio`) VALUES
(1, 1, '2019-11-15 17:02:32'),
(3, 3, '2019-11-15 17:02:32'),
(4, 2, '2019-11-15 17:02:32');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detall_factura`
--

CREATE TABLE `detall_factura` (
  `ID` int(255) NOT NULL,
  `Nom_Prod` text NOT NULL,
  `Preu Prod` double(10,2) NOT NULL,
  `Quantitat` int(255) NOT NULL,
  `ID_Factura` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `detall_factura`
--

INSERT INTO `detall_factura` (`ID`, `Nom_Prod`, `Preu Prod`, `Quantitat`, `ID_Factura`) VALUES
(1, 'Coca-Cola Classica', 1.99, 1, 1),
(2, 'Coca-Cola Zero', 1.99, 3, 1),
(3, 'Pernil Serra', 1.00, 1, 1),
(4, 'Truita Espanyola', 1.00, 2, 1),
(5, 'Pintxo', 4.50, 3, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `ID` int(255) NOT NULL,
  `Preu Final` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`ID`, `Preu Final`) VALUES
(1, 24.46);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productes`
--

CREATE TABLE `productes` (
  `ID` int(100) NOT NULL,
  `Nom_Producte` text NOT NULL,
  `Preu` double(10,2) NOT NULL,
  `Descripcio` text NOT NULL,
  `ID_Categoria` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productes`
--

INSERT INTO `productes` (`ID`, `Nom_Producte`, `Preu`, `Descripcio`, `ID_Categoria`) VALUES
(2, 'Coca-Cola', 1.99, 'Coca-Cola Classica', 1),
(3, 'Coca-Cola Zero', 1.99, 'Coca-Cola sense sucres afegits', 1),
(4, 'Fanta Taronja', 1.99, 'Fanta de taronja classica', 1),
(5, 'Fanta Llimona', 1.99, 'Fanta de llimona classica', 1),
(6, 'Sprite', 2.25, 'Gaseosa sabor llimona', 1),
(7, 'Aquarius', 2.25, 'Aquarius classica', 1),
(8, 'Cafe amb llet', 1.50, 'Cafe amb llet', 1),
(9, 'Cafe express', 1.50, 'Cafe express de maquina', 1),
(10, 'Pernil Serra', 1.00, 'Tapa de pernil serra', 2),
(11, 'Truita espanyola', 1.00, 'Tapa de truita espanyola', 2),
(12, 'Olives', 1.00, 'Tapa de olives farcides amb anxova', 2),
(13, 'Croquetes', 1.00, 'Tapa de croquetes de pernil', 2),
(14, 'Pop gallec', 1.50, 'Tapa de pop gallec', 2),
(15, 'Pa amb tomaquet', 1.00, 'Tapa de pa amb tomaquet i oli', 2),
(16, 'Patates Braves', 4.50, 'Plat de patates braves amb salsa brava', 3),
(17, 'Paella', 5.00, 'Paella tradicional valenciana', 3),
(18, 'Pintxo', 4.50, 'Plat de pintxos de carn de porc marinada', 3),
(19, 'Lluç al forn', 5.50, 'Plat de lluç al forn amb patates de guarnicio', 3),
(20, 'Pizza Hawaiana', 6.50, 'Pizza amb pernil de york i pinya', 3),
(21, 'Pizza Reynolds', 6.50, 'Pizza de la casa. Mozzarella, carbasso, ceba, pebrot vermell, olives negres, orenga', 3),
(22, 'Entrepa de llom i formatge (calent)', 2.50, 'Entrepa de llom de porc amb formatge', 4),
(23, 'Entrepa de pernil serra', 2.50, 'Entrepa de pernil serra', 4),
(24, 'Vegetal de pollastre', 2.50, 'Entrepa vegetal de pollastre', 4),
(25, 'Vegetal de tonyina', 2.50, 'Entrepa vegetal de tonyina', 4),
(26, 'Entrepa de pernil de york', 2.50, 'Entrepa de pernil de york i formatge', 4),
(27, 'Entrepa de truita espanyola', 3.00, 'Entrepa de truita espanyola', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `relacio_comanda_producte`
--

CREATE TABLE `relacio_comanda_producte` (
  `ID` int(255) NOT NULL,
  `ID_Comanda` int(255) NOT NULL,
  `ID_Producte` int(255) NOT NULL,
  `Quantitat` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `relacio_comanda_producte`
--

INSERT INTO `relacio_comanda_producte` (`ID`, `ID_Comanda`, `ID_Producte`, `Quantitat`) VALUES
(1, 1, 2, 3),
(2, 1, 12, 1),
(3, 1, 13, 2),
(4, 1, 14, 1),
(5, 1, 16, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `taula_mestra_configuracio`
--

CREATE TABLE `taula_mestra_configuracio` (
  `IP` text NOT NULL,
  `Nom_PDA` int(100) NOT NULL,
  `NumeroTaules` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cambrer`
--
ALTER TABLE `cambrer`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `comanda`
--
ALTER TABLE `comanda`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_cambrer_comanda` (`ID_Cambrer`);

--
-- Indices de la tabla `detall_factura`
--
ALTER TABLE `detall_factura`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_factura_detall` (`ID_Factura`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `productes`
--
ALTER TABLE `productes`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_id_categoria` (`ID_Categoria`);

--
-- Indices de la tabla `relacio_comanda_producte`
--
ALTER TABLE `relacio_comanda_producte`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_comanda_rel` (`ID_Comanda`),
  ADD KEY `fk_producte_rel` (`ID_Producte`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cambrer`
--
ALTER TABLE `cambrer`
  MODIFY `ID` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `ID` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `comanda`
--
ALTER TABLE `comanda`
  MODIFY `ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `detall_factura`
--
ALTER TABLE `detall_factura`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `productes`
--
ALTER TABLE `productes`
  MODIFY `ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de la tabla `relacio_comanda_producte`
--
ALTER TABLE `relacio_comanda_producte`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `comanda`
--
ALTER TABLE `comanda`
  ADD CONSTRAINT `fk_cambrer_comanda` FOREIGN KEY (`ID_Cambrer`) REFERENCES `cambrer` (`ID`);

--
-- Filtros para la tabla `detall_factura`
--
ALTER TABLE `detall_factura`
  ADD CONSTRAINT `fk_factura_detall` FOREIGN KEY (`ID_Factura`) REFERENCES `factura` (`ID`);

--
-- Filtros para la tabla `productes`
--
ALTER TABLE `productes`
  ADD CONSTRAINT `fk_id_categoria` FOREIGN KEY (`ID_Categoria`) REFERENCES `categoria` (`ID`);

--
-- Filtros para la tabla `relacio_comanda_producte`
--
ALTER TABLE `relacio_comanda_producte`
  ADD CONSTRAINT `fk_comanda_rel` FOREIGN KEY (`ID_Comanda`) REFERENCES `comanda` (`ID`),
  ADD CONSTRAINT `fk_producte_rel` FOREIGN KEY (`ID_Producte`) REFERENCES `productes` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
