-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Aug 18, 2026 at 02:39 PM
-- Server version: 8.0.35
-- PHP Version: 8.2.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `microlearning_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `community_comments`
--

CREATE TABLE `community_comments` (
  `comment_id` int NOT NULL,
  `post_id` int NOT NULL,
  `user_id` int NOT NULL,
  `comment_content` varchar(500) COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `community_comments`
--

INSERT INTO `community_comments` (`comment_id`, `post_id`, `user_id`, `comment_content`, `created_at`) VALUES
(1, 1, 1, 'Great Test!:)', '2026-08-01 17:45:04'),
(2, 1, 1, 'any comment?', '2026-08-01 17:45:25'),
(3, 1, 3, 'Amazing test!', '2026-08-02 13:57:39'),
(4, 1, 4, 'New here', '2026-08-03 09:41:03'),
(5, 2, 4, 'I am also beginner. Same here. Need some advice', '2026-08-03 09:41:58'),
(6, 3, 6, 'Hi!', '2026-08-06 10:51:39'),
(7, 3, 7, 'HI:)', '2026-08-06 11:16:59'),
(8, 1, 8, 'glad to connect!', '2026-08-09 10:03:18');

-- --------------------------------------------------------

--
-- Table structure for table `community_opportunities`
--

CREATE TABLE `community_opportunities` (
  `opportunity_id` int NOT NULL,
  `user_id` int NOT NULL,
  `opportunity_type` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(150) COLLATE utf8mb4_general_ci NOT NULL,
  `description` text COLLATE utf8mb4_general_ci NOT NULL,
  `location` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `event_date` datetime DEFAULT NULL,
  `contact_information` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `community_opportunities`
--

INSERT INTO `community_opportunities` (`opportunity_id`, `user_id`, `opportunity_type`, `title`, `description`, `location`, `event_date`, `contact_information`, `created_at`) VALUES
(1, 1, 'Project Collaboration', 'Test project', 'Test', 'online', '2026-07-17 19:41:00', 'example@example.com', '2026-07-17 19:41:14'),
(2, 3, 'Community Event', 'Craft Night', 'Enjoy the music and good vibes. Let\'s do a craft night. We will make digital Vision Board together!', 'online TEAMS meeting', '2026-08-05 19:30:00', 'anna@test.com', '2026-08-02 14:28:13'),
(3, 7, 'Study Group', 'How to earn money', 'How to earn money?', 'online', NULL, 'nan@test.com', '2026-08-06 11:15:28');

-- --------------------------------------------------------

--
-- Table structure for table `community_posts`
--

CREATE TABLE `community_posts` (
  `post_id` int NOT NULL,
  `user_id` int NOT NULL,
  `title` varchar(150) COLLATE utf8mb4_general_ci NOT NULL,
  `content` text COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `community_posts`
--

INSERT INTO `community_posts` (`post_id`, `user_id`, `title`, `content`, `created_at`) VALUES
(1, 1, 'Test Post', 'Test Test Test', '2026-07-01 13:29:29'),
(2, 3, 'My first Post', 'Hello!I am new here. Can anyone help me get started?', '2026-08-02 13:57:02'),
(3, 4, 'Just say hi and connect!', 'Just say hi and connect!', '2026-08-03 09:40:46'),
(4, 6, 'My first post', 'hello I+1! I am here:)', '2026-08-06 10:51:13');

-- --------------------------------------------------------

--
-- Table structure for table `learning_progress`
--

CREATE TABLE `learning_progress` (
  `progress_id` int NOT NULL,
  `user_id` int NOT NULL,
  `module_id` int NOT NULL,
  `progress_status` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'not_started',
  `completed_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `learning_progress`
--

INSERT INTO `learning_progress` (`progress_id`, `user_id`, `module_id`, `progress_status`, `completed_at`, `updated_at`) VALUES
(1, 1, 1, 'completed', '2026-07-06 16:17:32', '2026-07-06 16:17:32'),
(3, 1, 2, 'completed', '2026-07-06 16:17:41', '2026-07-06 16:17:41'),
(4, 3, 3, 'completed', '2026-08-02 13:52:50', '2026-08-02 13:52:50'),
(5, 3, 2, 'completed', '2026-08-02 13:55:15', '2026-08-02 13:55:15'),
(6, 4, 1, 'completed', '2026-08-03 09:29:16', '2026-08-03 09:29:16'),
(7, 4, 2, 'completed', '2026-08-03 09:30:03', '2026-08-03 09:30:03'),
(8, 4, 3, 'completed', '2026-08-03 09:31:48', '2026-08-03 09:31:48'),
(9, 6, 2, 'completed', '2026-08-06 10:52:49', '2026-08-06 10:52:49'),
(10, 7, 1, 'completed', '2026-08-06 11:10:10', '2026-08-06 11:10:10'),
(11, 8, 3, 'completed', '2026-08-09 09:29:20', '2026-08-09 09:29:20'),
(12, 1, 3, 'completed', '2026-08-09 11:15:40', '2026-08-09 11:15:40'),
(13, 9, 1, 'completed', '2026-08-10 14:06:39', '2026-08-10 14:06:39'),
(14, 9, 2, 'completed', '2026-08-10 14:06:52', '2026-08-10 14:06:52'),
(15, 9, 3, 'completed', '2026-08-10 14:08:56', '2026-08-10 14:08:56');

-- --------------------------------------------------------

--
-- Table structure for table `modules`
--

CREATE TABLE `modules` (
  `module_id` int NOT NULL,
  `title` varchar(150) COLLATE utf8mb4_general_ci NOT NULL,
  `description` text COLLATE utf8mb4_general_ci NOT NULL,
  `category` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `duration` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `achievement_label` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `modules`
--

INSERT INTO `modules` (`module_id`, `title`, `description`, `category`, `duration`, `achievement_label`, `created_at`) VALUES
(1, 'Java Basics - Introduction', 'Learn basic programming concepts such as instructions, variables, and simple logic.', 'Programming', '1.5min', 'Java Starter', '2026-07-06 15:37:50'),
(2, 'Cyber Security Basics - Introduction', 'Learn how to protect accounts, recognise phishing, and build safer online habits.', 'Cyber Security', '54 s', 'Cyber Safety', '2026-07-06 15:37:50'),
(3, 'Java Basics – My First Program', 'Create your first Java program and learn how to print “Hello World” to the screen.', 'Programming', '44 s', 'Hello World Creator', '2026-07-14 15:51:10');

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

CREATE TABLE `notes` (
  `note_id` int NOT NULL,
  `user_id` int NOT NULL,
  `module_id` int DEFAULT NULL,
  `title` varchar(150) COLLATE utf8mb4_general_ci NOT NULL,
  `content` text COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `notes`
--

INSERT INTO `notes` (`note_id`, `user_id`, `module_id`, `title`, `content`, `created_at`) VALUES
(1, 1, NULL, 'Test Note', 'TEST TEST TEST', '2026-07-01 13:05:54'),
(2, 1, NULL, 'how to start CS', 'Recent I just start to my journey in CS but not sure where to start.', '2026-07-09 09:27:03'),
(3, 1, 1, '1st notes for Java intro', 'I learn the basic idea of java', '2026-07-21 15:47:50'),
(4, 3, NULL, 'My first note', 'This website looks really cool!', '2026-08-02 13:51:12'),
(5, 3, 2, 'Great Introduction', 'The video had very good points.', '2026-08-02 13:59:32'),
(6, 4, 3, 'Try java myself', 'It is great I can try it myself and made the first project!', '2026-08-03 09:31:29'),
(7, 7, NULL, 'First note', 'This is my first note', '2026-08-06 11:11:57'),
(8, 8, 3, 'what I learnt from java', 'new journey!', '2026-08-09 09:29:06'),
(9, 1, 3, 'my first program', 'Review the video later', '2026-08-09 11:16:19');

-- --------------------------------------------------------

--
-- Table structure for table `shared_knowledge`
--

CREATE TABLE `shared_knowledge` (
  `knowledge_id` int NOT NULL,
  `user_id` int NOT NULL,
  `title` varchar(120) COLLATE utf8mb4_general_ci NOT NULL,
  `category` varchar(60) COLLATE utf8mb4_general_ci NOT NULL,
  `experience_type` varchar(60) COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(300) COLLATE utf8mb4_general_ci NOT NULL,
  `content` text COLLATE utf8mb4_general_ci NOT NULL,
  `key_takeaway` text COLLATE utf8mb4_general_ci,
  `reading_time` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `image_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `video_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shared_knowledge`
--

INSERT INTO `shared_knowledge` (`knowledge_id`, `user_id`, `title`, `category`, `experience_type`, `description`, `content`, `key_takeaway`, `reading_time`, `image_url`, `video_url`, `created_at`) VALUES
(1, 1, 'How to start your project', 'Study Skills', 'Study tip', 'How to start your project', 'How to start your project', 'project', '3 min', NULL, NULL, '2026-07-17 18:48:21'),
(2, 4, 'Make the first step', 'Career', 'Personal experience', 'I realise we all should make the first step!', 'I always curious about computer but there are too many things to worry how to start. It feels good to know something new and try it myself.', 'Be fearless and just do it!', '3 min', NULL, NULL, '2026-08-03 09:38:10');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `email`, `password`, `created_at`) VALUES
(1, 'example', 'example@example.com', '123456', '2026-07-01 12:17:58'),
(3, 'Anna', 'anna@test.com', 'TEAM', '2026-08-02 13:49:27'),
(4, 'Hong', 'Hong@test.com', '2468', '2026-08-03 09:25:18'),
(5, 'example02', 'example02@example.com', '12345678', '2026-08-04 19:41:14'),
(6, 'YYY', 'yyy@test.com', 'testtest', '2026-08-06 10:44:45'),
(7, 'NAN', 'NAN@test.com', 'richwoman', '2026-08-06 11:07:45'),
(8, 'WEN', 'wen@test.com', 'wenniubi', '2026-08-09 09:25:22'),
(9, 'MD', 'MD@test.com', '12uceuce.', '2026-08-10 14:04:48');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `community_comments`
--
ALTER TABLE `community_comments`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `fk_comment_post` (`post_id`),
  ADD KEY `fk_comment_user` (`user_id`);

--
-- Indexes for table `community_opportunities`
--
ALTER TABLE `community_opportunities`
  ADD PRIMARY KEY (`opportunity_id`),
  ADD KEY `fk_opportunity_user` (`user_id`);

--
-- Indexes for table `community_posts`
--
ALTER TABLE `community_posts`
  ADD PRIMARY KEY (`post_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `learning_progress`
--
ALTER TABLE `learning_progress`
  ADD PRIMARY KEY (`progress_id`),
  ADD UNIQUE KEY `user_id` (`user_id`,`module_id`),
  ADD KEY `module_id` (`module_id`);

--
-- Indexes for table `modules`
--
ALTER TABLE `modules`
  ADD PRIMARY KEY (`module_id`);

--
-- Indexes for table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`note_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `fk_notes_module` (`module_id`);

--
-- Indexes for table `shared_knowledge`
--
ALTER TABLE `shared_knowledge`
  ADD PRIMARY KEY (`knowledge_id`),
  ADD KEY `fk_shared_knowledge_user` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `community_comments`
--
ALTER TABLE `community_comments`
  MODIFY `comment_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `community_opportunities`
--
ALTER TABLE `community_opportunities`
  MODIFY `opportunity_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `community_posts`
--
ALTER TABLE `community_posts`
  MODIFY `post_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `learning_progress`
--
ALTER TABLE `learning_progress`
  MODIFY `progress_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `modules`
--
ALTER TABLE `modules`
  MODIFY `module_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `notes`
--
ALTER TABLE `notes`
  MODIFY `note_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `shared_knowledge`
--
ALTER TABLE `shared_knowledge`
  MODIFY `knowledge_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `community_comments`
--
ALTER TABLE `community_comments`
  ADD CONSTRAINT `fk_comment_post` FOREIGN KEY (`post_id`) REFERENCES `community_posts` (`post_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_comment_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `community_opportunities`
--
ALTER TABLE `community_opportunities`
  ADD CONSTRAINT `fk_opportunity_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `community_posts`
--
ALTER TABLE `community_posts`
  ADD CONSTRAINT `community_posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `learning_progress`
--
ALTER TABLE `learning_progress`
  ADD CONSTRAINT `learning_progress_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `learning_progress_ibfk_2` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`);

--
-- Constraints for table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `fk_notes_module` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `shared_knowledge`
--
ALTER TABLE `shared_knowledge`
  ADD CONSTRAINT `fk_shared_knowledge_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
