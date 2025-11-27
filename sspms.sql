-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 27, 2025 at 07:58 PM
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
-- Database: `sspms`
--

-- --------------------------------------------------------

--
-- Table structure for table `audit_logs`
--

CREATE TABLE `audit_logs` (
  `log_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `table_name` varchar(50) DEFAULT NULL,
  `record_id` int(11) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `details` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `clerks`
--

CREATE TABLE `clerks` (
  `clerk_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `department` varchar(100) DEFAULT NULL,
  `employee_id` varchar(50) DEFAULT NULL,
  `hire_date` date DEFAULT NULL,
  `shift_time` varchar(50) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `clerks`
--

INSERT INTO `clerks` (`clerk_id`, `user_id`, `department`, `employee_id`, `hire_date`, `shift_time`, `created_at`) VALUES
(1, 3, 'Operations', NULL, NULL, NULL, '2025-11-26 04:18:22'),
(2, 6, 'Operations', NULL, NULL, NULL, '2025-11-26 23:47:42'),
(3, 9, 'Operations', NULL, NULL, NULL, '2025-11-27 08:03:30');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `account_balance` decimal(10,2) DEFAULT 0.00,
  `membership_tier` varchar(50) DEFAULT 'Standard',
  `total_shipments` int(11) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customer_id`, `user_id`, `company_name`, `account_balance`, `membership_tier`, `total_shipments`, `created_at`) VALUES
(2, 2, NULL, 0.00, 'Standard', 0, '2025-11-25 20:10:29'),
(3, 4, NULL, 0.00, 'Standard', 0, '2025-11-26 12:46:36'),
(4, 5, NULL, 0.00, 'Standard', 0, '2025-11-26 13:43:03');

-- --------------------------------------------------------

--
-- Table structure for table `drivers`
--

CREATE TABLE `drivers` (
  `driver_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `license_number` varchar(50) DEFAULT NULL,
  `license_expiry` date DEFAULT NULL,
  `vehicle_id` int(11) DEFAULT NULL,
  `total_deliveries` int(11) DEFAULT 0,
  `rating` decimal(3,2) DEFAULT 0.00,
  `status` enum('Active','Inactive','On Leave') DEFAULT 'Active',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `drivers`
--

INSERT INTO `drivers` (`driver_id`, `user_id`, `license_number`, `license_expiry`, `vehicle_id`, `total_deliveries`, `rating`, `status`, `created_at`) VALUES
(1, 7, NULL, NULL, NULL, 0, 0.00, 'Active', '2025-11-27 03:23:09'),
(2, 8, NULL, NULL, NULL, 0, 0.00, 'Active', '2025-11-27 04:28:46');

-- --------------------------------------------------------

--
-- Table structure for table `invoices`
--

CREATE TABLE `invoices` (
  `invoice_id` int(11) NOT NULL,
  `shipment_id` int(11) NOT NULL,
  `tracking_number` varchar(100) DEFAULT NULL,
  `customer_id` int(11) NOT NULL,
  `invoice_date` datetime DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `subtotal` decimal(10,2) DEFAULT NULL,
  `tax` decimal(10,2) DEFAULT NULL,
  `discount` decimal(10,2) DEFAULT NULL,
  `surcharge` decimal(10,2) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `status` varchar(50) DEFAULT 'Unpaid',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invoices`
--

INSERT INTO `invoices` (`invoice_id`, `shipment_id`, `tracking_number`, `customer_id`, `invoice_date`, `due_date`, `subtotal`, `tax`, `discount`, `surcharge`, `total`, `status`, `created_at`) VALUES
(5, 5, 'SHIP1764161263047', 3, '2025-11-26 07:47:43', NULL, 45.00, 4.50, 0.00, 0.00, 49.50, 'Unpaid', '2025-11-26 12:47:43'),
(6, 10, 'SHIP1764164631098', 4, '2025-11-26 08:43:51', NULL, 35.00, 3.50, 0.00, 0.00, 38.50, 'Unpaid', '2025-11-26 13:43:51'),
(7, 11, 'SHIP1764164693345', 4, '2025-11-26 08:44:53', NULL, 47.50, 4.75, 0.00, 0.00, 52.25, 'Paid', '2025-11-27 15:51:51'),
(8, 12, 'SHIP1764259834141', 4, '2025-11-27 11:10:34', NULL, 75.00, 7.50, 0.00, 0.00, 82.50, 'Paid', '2025-11-27 18:09:54');

-- --------------------------------------------------------

--
-- Table structure for table `managers`
--

CREATE TABLE `managers` (
  `manager_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `department` varchar(100) DEFAULT NULL,
  `employee_id` varchar(50) DEFAULT NULL,
  `hire_date` date DEFAULT NULL,
  `permissions` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `payment_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `payment_method` varchar(50) DEFAULT NULL,
  `payment_date` datetime DEFAULT NULL,
  `reference_number` varchar(100) DEFAULT NULL,
  `status` varchar(50) DEFAULT 'Completed'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`payment_id`, `invoice_id`, `amount`, `payment_method`, `payment_date`, `reference_number`, `status`) VALUES
(9, 7, 52.25, 'Card', '2025-11-27 10:51:51', NULL, 'Completed'),
(10, 8, 82.50, 'Cash', '2025-11-27 13:09:54', NULL, 'Completed');

-- --------------------------------------------------------

--
-- Table structure for table `recipients`
--

CREATE TABLE `recipients` (
  `recipient_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `zone` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `shipments`
--

CREATE TABLE `shipments` (
  `shipment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `sender_info` varchar(255) DEFAULT NULL,
  `recipient_info` varchar(255) DEFAULT NULL,
  `weight` decimal(10,2) DEFAULT NULL,
  `dimensions` varchar(100) DEFAULT NULL,
  `package_type` varchar(50) DEFAULT NULL,
  `zone` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT 'Pending',
  `tracking_number` varchar(100) DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT NULL,
  `payment_status` varchar(50) DEFAULT 'Unpaid',
  `payment_method` varchar(50) DEFAULT NULL,
  `vehicle_id` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `expected_delivery_date` date DEFAULT NULL,
  `actual_delivery_date` date DEFAULT NULL,
  `delivery_notes` text DEFAULT NULL,
  `recipient_name` varchar(255) DEFAULT NULL,
  `recipient_phone` varchar(20) DEFAULT NULL,
  `recipient_email` varchar(255) DEFAULT NULL,
  `surcharge` decimal(10,2) DEFAULT 0.00,
  `discount` decimal(10,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shipments`
--

INSERT INTO `shipments` (`shipment_id`, `user_id`, `sender_info`, `recipient_info`, `weight`, `dimensions`, `package_type`, `zone`, `status`, `tracking_number`, `cost`, `payment_status`, `payment_method`, `vehicle_id`, `address`, `created_at`, `expected_delivery_date`, `actual_delivery_date`, `delivery_notes`, `recipient_name`, `recipient_phone`, `recipient_email`, `surcharge`, `discount`) VALUES
(5, 4, 'Asher Maxwell | 7355273', 'Jack | 62773567', 3.00, '8.5', 'Express', 1, 'Cancelled', 'SHIP1764161263047', 45.00, 'Unpaid', NULL, 2, 'Over the horizon', '2025-11-26 12:47:43', NULL, NULL, NULL, NULL, NULL, NULL, 0.00, 0.00),
(10, 5, 'Hannah | 876-123-4567', 'Asher | 8767717389', 1.00, '1.0', 'Express', 1, 'Delivered', 'SHIP1764164631098', 35.00, 'Paid', NULL, NULL, 'Place', '2025-11-26 13:43:51', NULL, NULL, NULL, NULL, NULL, NULL, 0.00, 0.00),
(11, 5, 'Hannah | 876-123-4567', 'Jack | 52616627', 2.50, '1.0', 'Fragile', 2, 'Shipped', 'SHIP1764164693345', 47.50, 'Paid', 'Card', NULL, 'Somewhere', '2025-11-26 13:44:53', NULL, NULL, NULL, NULL, NULL, NULL, 0.00, 0.00),
(12, 5, 'Hannah | 8761234567', 'Asher Maxwell | 8767717389', 3.00, '3.0', 'Express', 4, 'Shipped', 'SHIP1764259834141', 75.00, 'Paid', 'Cash', NULL, 'Places', '2025-11-27 16:10:34', NULL, NULL, NULL, NULL, NULL, NULL, 0.00, 0.00);

-- --------------------------------------------------------

--
-- Table structure for table `shipment_assignments`
--

CREATE TABLE `shipment_assignments` (
  `assignment_id` int(11) NOT NULL,
  `tracking_number` varchar(50) NOT NULL,
  `driver_id` int(11) DEFAULT NULL,
  `vehicle_id` int(11) DEFAULT NULL,
  `route` varchar(100) DEFAULT NULL,
  `assigned_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shipment_assignments`
--

INSERT INTO `shipment_assignments` (`assignment_id`, `tracking_number`, `driver_id`, `vehicle_id`, `route`, `assigned_at`, `updated_at`) VALUES
(2, 'SHIP1764164693345', 2, 6, 'Route A - North', '2025-11-27 05:10:08', '2025-11-27 08:10:43'),
(5, 'SHIP1764164631098', 1, 3, ' Select Route ', '2025-11-27 05:41:40', '2025-11-27 08:08:00'),
(9, 'SHIP1764161263047', NULL, NULL, ' Select Route ', '2025-11-27 05:45:19', '2025-11-27 05:45:19');

-- --------------------------------------------------------

--
-- Table structure for table `shipment_logistics`
--

CREATE TABLE `shipment_logistics` (
  `logistics_id` int(11) NOT NULL,
  `tracking_number` varchar(255) NOT NULL,
  `driver_id` int(11) DEFAULT NULL,
  `assigned_route` varchar(255) DEFAULT NULL,
  `assignment_timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shipment_logistics`
--

INSERT INTO `shipment_logistics` (`logistics_id`, `tracking_number`, `driver_id`, `assigned_route`, `assignment_timestamp`) VALUES
(1, 'SHIP1764164693345', 1, '', '2025-11-27 03:52:42'),
(5, 'SHIP1764164631098', NULL, 'East Route', '2025-11-27 04:02:14'),
(6, 'SHIP1764161263047', 1, 'East Route', '2025-11-27 04:09:04');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `address` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('Customer','Clerk','Driver','Manager') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `is_active` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `email`, `phone`, `address`, `password`, `role`, `created_at`, `updated_at`, `is_active`) VALUES
(1, 'benjamin', 'Ben@rocketuniverse.com', '876-309-9787', '45 star valley rd malibu LA', 'ben10', 'Customer', '2025-11-21 21:44:47', '2025-11-25 19:39:51', 1),
(2, 'jack', 'test@gmail.com', '5244236', 'places', '123', 'Customer', '2025-11-25 20:10:29', '2025-11-25 20:10:29', 1),
(3, 'Josiah', 'maxwell@gmail.com', '7355617', 'Places', '12345', 'Clerk', '2025-11-26 04:18:22', '2025-11-26 04:18:22', 1),
(4, 'Asher', 'asher@gmail.com', '7355273', 'Where', '12345', 'Customer', '2025-11-26 12:46:36', '2025-11-26 12:46:36', 1),
(5, 'Hannah', 'hannah@gmail.com', '876-123-4567', 'Place', 'han123', 'Customer', '2025-11-26 13:43:03', '2025-11-26 13:43:03', 1),
(6, 'Asher Maxwell', 'ashermaxwell@gmail.com', '8767717389', 'Place', '12345', 'Clerk', '2025-11-26 23:47:42', '2025-11-26 23:47:42', 1),
(7, 'Bob', 'bob@driver.smartship.com', '18765362453', 'Onion Walk', '12345', 'Driver', '2025-11-27 03:23:09', '2025-11-27 03:23:09', 1),
(8, 'Adrian', 'adrian@driver.smartship.com', '1876636372', 'Chucky Town', '12345', 'Driver', '2025-11-27 04:28:46', '2025-11-27 04:28:46', 1),
(9, 'Test User', 'testuser@clerk.smartship.com', '8767717389', 'Somewhere', '12345', 'Clerk', '2025-11-27 08:03:30', '2025-11-27 08:03:30', 1);

-- --------------------------------------------------------

--
-- Table structure for table `vehicles`
--

CREATE TABLE `vehicles` (
  `vehicle_id` int(11) NOT NULL,
  `driver_id` int(11) DEFAULT NULL,
  `vehicle_type` varchar(100) DEFAULT NULL,
  `license_plate` varchar(50) DEFAULT NULL,
  `capacity` decimal(10,2) DEFAULT NULL,
  `status` varchar(50) DEFAULT 'Available',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `current_weight` decimal(10,2) DEFAULT 0.00,
  `current_item_count` int(11) DEFAULT 0,
  `last_maintenance_date` date DEFAULT NULL,
  `next_maintenance_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vehicles`
--

INSERT INTO `vehicles` (`vehicle_id`, `driver_id`, `vehicle_type`, `license_plate`, `capacity`, `status`, `created_at`, `current_weight`, `current_item_count`, `last_maintenance_date`, `next_maintenance_date`) VALUES
(1, 1, 'Van', 'JM-VAN-001', 1500.00, 'Available', '2025-11-27 03:42:39', 0.00, 0, '2023-10-01', '2024-04-01'),
(2, 2, 'Truck', 'JM-TRK-505', 5000.00, 'Available', '2025-11-27 03:42:39', 0.00, 0, '2023-11-15', '2024-05-15'),
(3, 3, 'Motorcycle', 'JM-BIKE-99', 50.00, 'Available', '2025-11-27 03:42:39', 0.00, 0, '2023-12-01', '2024-03-01'),
(4, 4, 'Van', 'JM-VAN-002', 1500.00, 'In Transit', '2025-11-27 03:42:39', 450.50, 12, '2023-09-20', '2024-03-20'),
(5, 5, 'Truck', 'JM-TRK-888', 8000.00, 'Maintenance', '2025-11-27 03:42:39', 0.00, 0, '2023-08-10', '2024-02-10'),
(6, 6, 'Electric Van', 'JM-EV-007', 1200.00, 'Available', '2025-11-27 03:42:39', 0.00, 0, '2023-12-20', '2024-06-20');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_schedules`
--

CREATE TABLE `vehicle_schedules` (
  `schedule_id` int(11) NOT NULL,
  `vehicle_id` int(11) NOT NULL,
  `scheduled_date` date DEFAULT NULL,
  `departure_time` time DEFAULT NULL,
  `expected_return_time` time DEFAULT NULL,
  `route_description` varchar(255) DEFAULT NULL,
  `status` varchar(50) DEFAULT 'Scheduled'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `audit_logs`
--
ALTER TABLE `audit_logs`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `clerks`
--
ALTER TABLE `clerks`
  ADD PRIMARY KEY (`clerk_id`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD KEY `idx_clerk_user_id` (`user_id`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customer_id`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD KEY `idx_customer_user_id` (`user_id`);

--
-- Indexes for table `drivers`
--
ALTER TABLE `drivers`
  ADD PRIMARY KEY (`driver_id`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD UNIQUE KEY `license_number` (`license_number`),
  ADD KEY `idx_driver_user_id` (`user_id`);

--
-- Indexes for table `invoices`
--
ALTER TABLE `invoices`
  ADD PRIMARY KEY (`invoice_id`),
  ADD UNIQUE KEY `shipment_id` (`shipment_id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `idx_invoice_tracking` (`tracking_number`);

--
-- Indexes for table `managers`
--
ALTER TABLE `managers`
  ADD PRIMARY KEY (`manager_id`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD KEY `idx_manager_user_id` (`user_id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `invoice_id` (`invoice_id`);

--
-- Indexes for table `recipients`
--
ALTER TABLE `recipients`
  ADD PRIMARY KEY (`recipient_id`);

--
-- Indexes for table `shipments`
--
ALTER TABLE `shipments`
  ADD PRIMARY KEY (`shipment_id`),
  ADD UNIQUE KEY `tracking_number` (`tracking_number`),
  ADD KEY `idx_shipment_user_id` (`user_id`),
  ADD KEY `idx_shipment_tracking` (`tracking_number`);

--
-- Indexes for table `shipment_assignments`
--
ALTER TABLE `shipment_assignments`
  ADD PRIMARY KEY (`assignment_id`),
  ADD UNIQUE KEY `unique_tracking` (`tracking_number`),
  ADD KEY `driver_id` (`driver_id`),
  ADD KEY `vehicle_id` (`vehicle_id`);

--
-- Indexes for table `shipment_logistics`
--
ALTER TABLE `shipment_logistics`
  ADD PRIMARY KEY (`logistics_id`),
  ADD UNIQUE KEY `tracking_number` (`tracking_number`),
  ADD KEY `driver_id` (`driver_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `vehicles`
--
ALTER TABLE `vehicles`
  ADD PRIMARY KEY (`vehicle_id`),
  ADD UNIQUE KEY `license_plate` (`license_plate`),
  ADD KEY `driver_id` (`driver_id`);

--
-- Indexes for table `vehicle_schedules`
--
ALTER TABLE `vehicle_schedules`
  ADD PRIMARY KEY (`schedule_id`),
  ADD KEY `vehicle_id` (`vehicle_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `audit_logs`
--
ALTER TABLE `audit_logs`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `clerks`
--
ALTER TABLE `clerks`
  MODIFY `clerk_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `drivers`
--
ALTER TABLE `drivers`
  MODIFY `driver_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `invoices`
--
ALTER TABLE `invoices`
  MODIFY `invoice_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `managers`
--
ALTER TABLE `managers`
  MODIFY `manager_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `payment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `recipients`
--
ALTER TABLE `recipients`
  MODIFY `recipient_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `shipments`
--
ALTER TABLE `shipments`
  MODIFY `shipment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `shipment_assignments`
--
ALTER TABLE `shipment_assignments`
  MODIFY `assignment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `shipment_logistics`
--
ALTER TABLE `shipment_logistics`
  MODIFY `logistics_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `vehicles`
--
ALTER TABLE `vehicles`
  MODIFY `vehicle_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `vehicle_schedules`
--
ALTER TABLE `vehicle_schedules`
  MODIFY `schedule_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `audit_logs`
--
ALTER TABLE `audit_logs`
  ADD CONSTRAINT `audit_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `clerks`
--
ALTER TABLE `clerks`
  ADD CONSTRAINT `clerks_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `customers`
--
ALTER TABLE `customers`
  ADD CONSTRAINT `customers_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `shipment_assignments`
--
ALTER TABLE `shipment_assignments`
  ADD CONSTRAINT `shipment_assignments_ibfk_1` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`driver_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `shipment_assignments_ibfk_2` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`vehicle_id`) ON DELETE SET NULL;

--
-- Constraints for table `shipment_logistics`
--
ALTER TABLE `shipment_logistics`
  ADD CONSTRAINT `shipment_logistics_ibfk_1` FOREIGN KEY (`tracking_number`) REFERENCES `shipments` (`tracking_number`),
  ADD CONSTRAINT `shipment_logistics_ibfk_2` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`driver_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
