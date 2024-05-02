-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 31, 2024 at 08:07 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `conversationguide2`
--

-- --------------------------------------------------------

--
-- Table structure for table `translations`
--

CREATE TABLE `translations` (
  `id` int(11) NOT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `translation` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `translations`
--

INSERT INTO `translations` (`id`, `option_name`, `language`, `translation`) VALUES
(1, 'bal', 'bal', '67'),
(2, 'baba', 'bubu', 'babu'),
(3, 'Domnule333/Doamna', 'Engleza', 'Mr./Mrs.'),
(4, 'Buna ziua!', 'Spaniola', '¡Hola!'),
(5, 'Buna ziua!', 'Italiana', 'Ciao!'),
(6, 'La revedere!', 'Engleza', 'Goodbye!'),
(7, 'La revedere!', 'Franceza', 'Au revoir !'),
(8, 'La revedere!', 'Germana', 'Auf Wiedersehen!'),
(9, 'La revedere!', 'Spaniola', '¡Adiós!'),
(10, 'La revedere!', 'Italiana', 'Arrivederci!'),
(11, 'Cat costa <produsul>?', 'Engleza', 'How much does <product> cost?'),
(12, 'Cat costa <produsul>?', 'Franceza', 'Combien coûte <produit> ?'),
(13, 'Cat costa <produsul>?', 'Germana', 'Wie viel kostet <Produkt>?'),
(14, 'Cat costa <produsul>?', 'Spaniola', '¿Cuánto cuesta <producto>?'),
(15, 'Cat costa <produsul>?', 'Italiana', 'Quanto costa <prodotto>?'),
(16, 'Aveti sa imi dati rest la <valoare bancnota>?', 'Engleza', 'Will you give me change for <note value>?'),
(17, 'Aveti sa imi dati rest la <valoare bancnota>?', 'Franceza', 'Pouvez-vous me rendre la monnaie pour <valeur du billet> ?'),
(18, 'Aveti sa imi dati rest la <valoare bancnota>?', 'Germana', 'Können Sie mir Wechselgeld für <Notenwert> geben?'),
(19, 'Aveti sa imi dati rest la <valoare bancnota>?', 'Spaniola', '¿Me da cambio de <valor del billete>?'),
(20, 'Aveti sa imi dati rest la <valoare bancnota>?', 'Italiana', 'Mi dà il resto per <valore della banconota>?'),
(21, 'Cum ajung la <destinatie>?', 'Engleza', 'How do I get to <destination>?'),
(22, '44', '55', '66'),
(23, 'Cum ajung la <destinatie>?', 'Germana', 'Wie komme ich nach <Ziel>?'),
(24, 'Cum ajung la <destinatie>?', 'Spaniola', '¿Cómo puedo llegar a <destino>?'),
(25, 'Cum ajung la <destinatie>?', 'Italiana', 'Come faccio ad arrivare a <destinazione>?'),
(26, 'Cat este ceasul?', 'Engleza', 'What time is it?'),
(27, 'Cat este ceasul?', 'Franceza', 'Quelle heure est-il ?'),
(163, 'Imi pare sada', 'sscc', 'Encantado de fdgfdsf.');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `translations`
--
ALTER TABLE `translations`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `translations`
--
ALTER TABLE `translations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=165;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
