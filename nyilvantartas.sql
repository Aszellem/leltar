-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2019. Feb 25. 12:35
-- Kiszolgáló verziója: 10.1.30-MariaDB
-- PHP verzió: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `nyilvantartas`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `eszkozok`
--

CREATE TABLE `eszkozok` (
  `eszkozid` int(5) NOT NULL,
  `nev` varchar(50) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `jellemzok` varchar(255) COLLATE utf8mb4_hungarian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `eszkozok`
--

INSERT INTO `eszkozok` (`eszkozid`, `nev`, `jellemzok`) VALUES
(1, 'HP asztali számítógép', ''),
(2, 'HP mini számítógép', 'i3, 4GB, 128GB SSD'),
(3, 'HP 22 monitor', ''),
(4, 'Kyocera nyomtató', ''),
(5, 'HP 1020 nyomtató', '');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `leltar`
--

CREATE TABLE `leltar` (
  `leltarid` int(11) NOT NULL,
  `teremid` int(4) NOT NULL,
  `eszkozid` int(5) NOT NULL,
  `ar` decimal(6,0) DEFAULT NULL,
  `ev` int(11) DEFAULT NULL,
  `megjegyzes` varchar(255) COLLATE utf8mb4_hungarian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `leltar`
--

INSERT INTO `leltar` (`leltarid`, `teremid`, `eszkozid`, `ar`, `ev`, `megjegyzes`) VALUES
(1, 4, 1, '100000', 2016, 't1'),
(2, 1, 1, '100000', 2016, 'tanári'),
(3, 1, 1, NULL, NULL, 't3'),
(4, 1, 2, NULL, NULL, 't4'),
(7, 5, 5, NULL, NULL, NULL),
(9, 5, 3, NULL, NULL, NULL),
(11, 1, 4, NULL, NULL, 't5'),
(12, 1, 2, NULL, NULL, 't44');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `termek`
--

CREATE TABLE `termek` (
  `teremid` int(4) NOT NULL,
  `terem` varchar(4) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `felhasznalas` varchar(50) COLLATE utf8mb4_hungarian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `termek`
--

INSERT INTO `termek` (`teremid`, `terem`, `felhasznalas`) VALUES
(1, '104', 'Informatika terem'),
(2, '123', 'Informatika tanárii'),
(4, '136', NULL),
(5, 'M22', 'Informatika terem'),
(9, '127', 'Informatika terem');

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `eszkozok`
--
ALTER TABLE `eszkozok`
  ADD PRIMARY KEY (`eszkozid`),
  ADD UNIQUE KEY `nev` (`nev`);

--
-- A tábla indexei `leltar`
--
ALTER TABLE `leltar`
  ADD PRIMARY KEY (`leltarid`),
  ADD KEY `eszkozid` (`eszkozid`),
  ADD KEY `teremid` (`teremid`);

--
-- A tábla indexei `termek`
--
ALTER TABLE `termek`
  ADD PRIMARY KEY (`teremid`),
  ADD UNIQUE KEY `teremszam` (`terem`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `eszkozok`
--
ALTER TABLE `eszkozok`
  MODIFY `eszkozid` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT a táblához `leltar`
--
ALTER TABLE `leltar`
  MODIFY `leltarid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT a táblához `termek`
--
ALTER TABLE `termek`
  MODIFY `teremid` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `leltar`
--
ALTER TABLE `leltar`
  ADD CONSTRAINT `leltar_ibfk_1` FOREIGN KEY (`teremid`) REFERENCES `termek` (`teremid`),
  ADD CONSTRAINT `leltar_ibfk_2` FOREIGN KEY (`eszkozid`) REFERENCES `eszkozok` (`eszkozid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
